package game;

// -------------------------------------------------------------------------
/**
 * Tests the Model class.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class ModelTest
    extends student.TestCase
{
    // ~ Fields ................................................................
    private Model model;

    // ~ Constructors / Setup ..................................................

    // ----------------------------------------------------------
    /**
     * Creates a fresh Model before each test.
     */
    public void setUp()
    {
        model = new Model();
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests startDay ensures a package is available in the queue and clears
     * any package currently being processed.
     */
    public void testStartDay()
    {
        model.startDay();
        assertTrue(model.startProcessing());
        assertTrue(model.weighPackage() > 0);

        model.startDay();
        assertEquals(-1, model.weighPackage());
    }


    // ----------------------------------------------------------
    /**
     * Tests addToQueue and checkQueue maintain a minimum of 3 unprocessed
     * packages when checked.
     */
    public void testQueueManagement()
    {
        // checkQueue adds 1 package per call until size reaches 3
        model.checkQueue();
        model.checkQueue();
        model.checkQueue();
        // Calling a 4th time should not add a 4th package
        model.checkQueue();

        assertTrue(model.startProcessing());
        assertTrue(model.startProcessing());
        assertTrue(model.startProcessing());
        assertFalse(model.startProcessing());

        model.addToQueue();
        assertTrue(model.startProcessing());
        assertFalse(model.startProcessing());
    }


    // ----------------------------------------------------------
    /**
     * Tests processing lifecycle methods when no package is active and when a
     * package is being processed, stamped, denied, or finished.
     */
    public void testPackageProcessing()
    {
        // Before starting processing
        assertFalse(model.startProcessing());
        assertEquals(-1, model.weighPackage());
        assertEquals(-1, model.measurePackage());
        assertEquals(
            "No package currently being processed",
            model.readDestinationOfPackage());
        assertEquals(
            "No package currently being processed",
            model.viewContentsOfPackage());
        assertFalse(model.addStampToPackage("Fragile"));

        // Finishing with no current package should do nothing
        model.finishProcessing();
        assertEquals(0, model.calculateScore());

        // Start processing a package and inspect it
        model.addToQueue();
        assertTrue(model.startProcessing());
        assertTrue(model.weighPackage() >= 1 && model.weighPackage() <= 30);
        assertTrue(model.measurePackage() > 0);
        assertNotNull(model.readDestinationOfPackage());
        assertNotNull(model.viewContentsOfPackage());
        assertTrue(model.addStampToPackage("Fragile"));

        // Deny discards the package without adding to processed queue
        model.denyPackage();
        assertEquals(-1, model.weighPackage());
        assertFalse(model.addToShelf(0, 0));

        // Finish processing moves the package to the processed queue
        model.addToQueue();
        assertTrue(model.startProcessing());
        assertTrue(model.addStampToPackage("Small"));
        model.finishProcessing();
        assertEquals(-1, model.weighPackage());
        assertTrue(model.addToShelf(0, 0));
    }


    // ----------------------------------------------------------
    /**
     * Tests viewShelves, viewShelf, addToShelf, and removeFromShelf across
     * empty, occupied, and out-of-bounds slots.
     */
    public void testShelfOperations()
    {
        String emptyDiagram = model.viewShelves();
        assertFalse(emptyDiagram.contains("|  x  |"));
        assertTrue(emptyDiagram.contains("|     |"));

        // Out-of-bounds upper indices
        assertEquals("Invalid shelf position", model.viewShelf(5, 0));
        assertEquals("Invalid shelf position", model.viewShelf(0, 5));
        assertFalse(model.addToShelf(5, 0));
        assertFalse(model.addToShelf(0, 5));
        assertFalse(model.removeFromShelf(5, 0));
        assertFalse(model.removeFromShelf(0, 5));

        // Empty slot operations
        assertEquals("No package exists here", model.viewShelf(1, 2));
        assertFalse(model.addToShelf(1, 2));
        assertFalse(model.removeFromShelf(1, 2));

        // Process a package and place it on shelf (1, 2)
        model.addToQueue();
        model.startProcessing();
        model.addStampToPackage("Keep Dry");
        model.finishProcessing();

        assertTrue(model.addToShelf(1, 2));
        assertEquals("Keep Dry", model.viewShelf(1, 2));
        assertTrue(model.viewShelves().contains("|  x  |"));

        // Cannot place another package on occupied slot (1, 2)
        model.addToQueue();
        model.startProcessing();
        model.finishProcessing();
        assertFalse(model.addToShelf(1, 2));

        // Place second package on (0, 0) and verify unstamped label
        assertTrue(model.addToShelf(0, 0));
        assertEquals("No stamps", model.viewShelf(0, 0));

        // Remove package from (1, 2) back to processed queue
        assertTrue(model.removeFromShelf(1, 2));
        assertEquals("No package exists here", model.viewShelf(1, 2));
        assertFalse(model.removeFromShelf(1, 2));
    }


    // ----------------------------------------------------------
    /**
     * Tests calculateScore and viewScores across multiple days and beyond the
     * 30-package daily scoring cap.
     */
    public void testScoringAndHistory()
    {
        assertEquals("No scores recorded\n", model.viewScores());

        // Day 1: 31 processed packages -> first 30 scored, 1 left over
        for (int i = 0; i < 31; i++)
        {
            model.addToQueue();
            model.startProcessing();
            model.finishProcessing();
        }
        int dayOneScore = model.calculateScore();
        assertTrue(model.viewScores().startsWith("Day 1: " + dayOneScore));

        // Day 2: the 31st package remains in processedPackages and is scored
        int dayTwoScore = model.calculateScore();
        assertTrue(dayTwoScore < 0);
        assertEquals(
            "Day 1: " + dayOneScore + "\nDay 2: " + dayTwoScore + "\n",
            model.viewScores());
    }
}
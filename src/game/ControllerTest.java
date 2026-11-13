package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

// -------------------------------------------------------------------------
/**
 * Tests the Controller class.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class ControllerTest
    extends student.TestCase
{
    // ~ Fields ................................................................
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    // ~ Constructors / Setup ..................................................

    // ----------------------------------------------------------
    /**
     * Saves the original standard streams and redirects System.out before
     * each test.
     */
    public void setUp()
    {
        originalIn = System.in;
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }


    // ----------------------------------------------------------
    /**
     * Restores the original System.in and System.out streams after each test.
     */
    public void tearDown()
    {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests leaving the game immediately from the day menu.
     */
    public void testExitGame()
    {
        System.setIn(new ByteArrayInputStream("B\n".getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(result.contains("===== HOW TO PLAY ====="));
        assertTrue(result.contains("Thanks for playing!"));
    }


    // ----------------------------------------------------------
    /**
     * Tests starting and ending a day and viewing the recorded daily score.
     */
    public void testStartAndEndDay()
    {
        System.setIn(new ByteArrayInputStream("A\nF\nB\n".getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(result.contains("No scores recorded"));
        assertTrue(result.contains("===== MAILROOM MENU ====="));
        assertTrue(result.contains("Score: 0"));
        assertTrue(result.contains("Day 1: 0"));
        assertTrue(result.contains("Thanks for playing!"));
    }


    // ----------------------------------------------------------
    /**
     * Tests inspecting package weight, volume, contents, and destination,
     * then finishing processing.
     */
    public void testInspectAndFinishPackage()
    {
        System.setIn(
            new ByteArrayInputStream("A\nA\nA\nB\nC\nD\nG\nF\nB\n".getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(result.contains("Weight in lbs: "));
        assertTrue(result.contains("Volume in in^3: "));
        assertTrue(result.contains("Contents: "));
        assertTrue(result.contains("Destination: "));
        assertTrue(result.contains("Score: "));
    }


    // ----------------------------------------------------------
    /**
     * Tests denying a package and verifying it is not added to the processed
     * queue.
     */
    public void testDenyPackage()
    {
        System.setIn(
            new ByteArrayInputStream("A\nA\nF\nD\n1\n1\nF\nB\n".getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(
            result.contains(
                "Either a package is already on that shelf or you have no processed packages to place."));
        assertTrue(result.contains("Score: 0"));
    }


    // ----------------------------------------------------------
    /**
     * Tests adding handling stamps, going back from the stamp menu, and
     * verifying the applied stamps on the shelf.
     */
    public void testAddHandlingStampsAndGoBack()
    {
        System.setIn(
            new ByteArrayInputStream(
                "A\nA\nE\nG\nE\nH\nE\nI\nE\nT\nG\nD\n1\n1\nC\n1\n1\nF\nB\n"
                    .getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(result.contains("Fragile, Flammable, Keep Dry"));
    }


    // ----------------------------------------------------------
    /**
     * Tests all remaining stamp menu options (load, size, and Virginia cities)
     * as well as attempting to add more than 10 stamps to a single package.
     */
    public void testAllStampOptionsAndMaxCapacity()
    {
        String input = "A\n"
            + "A\nE\nA\nE\nB\nE\nC\nE\nD\nE\nE\nE\nF\nE\nG\nE\nH\nE\nI\nE\nJ\n"
            + "E\nK\nG\nD\n1\n1\nC\n1\n1\n"
            + "A\nE\nK\nE\nL\nE\nM\nE\nN\nE\nO\nE\nP\nE\nQ\nE\nR\nE\nS\n"
            + "G\nD\n1\n2\nC\n1\n2\n"
            + "F\nB\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(result.contains("There is no room for any more stamps."));
        assertTrue(
            result.contains(
                "Light Load, Medium Load, Heavy Load, Small, Medium, Large, "
                    + "Fragile, Flammable, Keep Dry, Virginia Beach"));
        assertTrue(
            result.contains(
                "Chesapeake, Arlington, Richmond, Norfolk, Newport News, "
                    + "Alexandria, Hampton, Suffolk, Roanoke"));
    }


    // ----------------------------------------------------------
    /**
     * Tests viewing, placing, and removing packages on shelves, including
     * occupied slots, empty slots, and invalid integer inputs.
     */
    public void testShelfOperationsAndIntValidation()
    {
        String input = "A\n"
            + "B\n"
            + "E\n2\n3\n"
            + "C\n\nabc\n0\n6\n2\n\nxyz\n0\n6\n3\n"
            + "A\nG\n"
            + "D\n2\n3\n"
            + "B\n"
            + "A\nG\n"
            + "D\n2\n3\n"
            + "E\n2\n3\n"
            + "F\nB\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(result.contains("A package is not on that shelf."));
        assertTrue(result.contains("The entered value is not valid."));
        assertTrue(result.contains("No package exists here"));
        assertTrue(result.contains("|  x  |"));
        assertTrue(
            result.contains(
                "Either a package is already on that shelf or you have no processed packages to place."));
    }


    // ----------------------------------------------------------
    /**
     * Tests invalid menu inputs (empty, multi-character, out-of-range, and
     * numeric) as well as lowercase valid inputs across all menus.
     */
    public void testInvalidAndLowercaseMenuInputs()
    {
        String input = "\nAB\nC\n1\n"
            + "a\n"
            + "Z\n"
            + "A\n"
            + "Z\n"
            + "E\n"
            + "Z\n"
            + "T\n"
            + "G\n"
            + "f\n"
            + "b\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        new Controller();

        String result = output.toString();
        assertTrue(
            result.contains(
                "The entered value is not valid. Please enter a single letter option from the menu."));
        assertTrue(result.contains("Thanks for playing!"));
    }


    // ----------------------------------------------------------
    /**
     * Tests readIntOption directly for valid, empty, non-numeric, and
     * out-of-range inputs.
     */
    public void testReadIntOption()
    {
        System.setIn(
            new ByteArrayInputStream("B\n25\n\nabc\n-1\n31\n".getBytes()));

        Controller controller = new Controller();

        assertEquals(25, controller.readIntOption(0, 30));
        assertEquals(-1, controller.readIntOption(0, 30));
        assertEquals(-1, controller.readIntOption(0, 30));
        assertEquals(-1, controller.readIntOption(0, 30));
        assertEquals(-1, controller.readIntOption(0, 30));
    }


    // ----------------------------------------------------------
    /**
     * Tests the main entry point of the program.
     */
    public void testMain()
    {
        System.setIn(new ByteArrayInputStream("B\n".getBytes()));

        Controller.main(new String[0]);

        assertTrue(output.toString().contains("Thanks for playing!"));
    }
}

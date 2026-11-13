package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Tests the Package class.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class PackageTest
    extends student.TestCase
{
    // ~ Fields ................................................................
    private Package p;

    // ~ Constructors / Setup ..................................................

    // ----------------------------------------------------------
    /**
     * Creates a new Package before each test.
     */
    public void setUp()
    {
        p = new Package();
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests that weigh returns a value within the valid range (1-30 lbs).
     */
    public void testWeigh()
    {
        for (int i = 0; i < 50; i++)
        {
            Package pkg = new Package();
            int weight = pkg.weigh();
            assertTrue(weight >= 1 && weight <= 30);
        }
    }


    // ----------------------------------------------------------
    /**
     * Tests that measure returns a positive volume within the valid bounds.
     */
    public void testMeasure()
    {
        for (int i = 0; i < 50; i++)
        {
            Package pkg = new Package();
            int volume = pkg.measure();
            assertTrue(volume >= 36 && volume <= 6000);
        }
    }


    // ----------------------------------------------------------
    /**
     * Tests that readDestination returns one of the valid Virginia cities.
     */
    public void testReadDestination()
    {
        List<String> validCities = Arrays.asList(
            "Virginia Beach",
            "Chesapeake",
            "Arlington",
            "Richmond",
            "Norfolk",
            "Newport News",
            "Alexandria",
            "Hampton",
            "Suffolk",
            "Roanoke");

        for (int i = 0; i < 50; i++)
        {
            Package pkg = new Package();
            assertTrue(validCities.contains(pkg.readDestination()));
        }
    }


    // ----------------------------------------------------------
    /**
     * Tests that viewContents returns a non-empty comma-separated list of valid
     * package items.
     */
    public void testViewContents()
    {
        List<String> validItems = Arrays.asList(
            "Candy",
            "Clothes",
            "Fireworks",
            "Painting",
            "Shoes",
            "Gas",
            "Stuffed Animal",
            "Chips",
            "Live Snake",
            "Live Bird",
            "Glass Cup",
            "Charger",
            "Letter",
            "Unidentified Bugs",
            "Ceramic Mug",
            "Camera",
            "Pencils",
            "Battery");

        for (int i = 0; i < 50; i++)
        {
            Package pkg = new Package();
            String contents = pkg.viewContents();
            assertNotNull(contents);
            assertFalse(contents.isEmpty());
            String[] items = contents.split(", ");
            assertTrue(items.length >= 1 && items.length <= 5);
            for (String item : items)
            {
                assertTrue(validItems.contains(item));
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Tests addStamp for null, empty, blank, valid, and full-capacity cases.
     */
    public void testAddStamp()
    {
        assertFalse(p.addStamp(null));
        assertFalse(p.addStamp(""));
        assertFalse(p.addStamp("   "));

        for (int i = 0; i < 10; i++)
        {
            assertTrue(p.addStamp("Fragile"));
        }
        assertFalse(p.addStamp("Fragile"));
    }


    // ----------------------------------------------------------
    /**
     * Tests viewStamps when no stamps, one stamp, and multiple stamps are
     * applied.
     */
    public void testViewStamps()
    {
        assertEquals("No stamps", p.viewStamps());

        p.addStamp("Fragile");
        assertEquals("Fragile", p.viewStamps());

        p.addStamp("Small");
        p.addStamp("Norfolk");
        assertEquals("Fragile, Small, Norfolk", p.viewStamps());
    }


    // ----------------------------------------------------------
    /**
     * Tests gradeStamps when a safe package receives all required stamps.
     */
    public void testGradeStampsAllCorrect()
    {
        Package safePkg = findPackageBySafety(true);
        List<String> expected = getExpectedStamps(safePkg);
        for (String stamp : expected)
        {
            assertTrue(safePkg.addStamp(stamp));
        }
        assertEquals(expected.size(), safePkg.gradeStamps());
    }


    // ----------------------------------------------------------
    /**
     * Tests gradeStamps when a safe package receives no stamps, extra stamps,
     * and duplicate stamps.
     */
    public void testGradeStampsMissedAndExtra()
    {
        Package safePkg1 = findPackageBySafety(true);
        List<String> expected1 = getExpectedStamps(safePkg1);
        assertEquals(-expected1.size(), safePkg1.gradeStamps());

        Package safePkg2 = findPackageBySafety(true);
        List<String> expected2 = getExpectedStamps(safePkg2);
        String firstExpected = expected2.get(0);
        safePkg2.addStamp(firstExpected);
        safePkg2.addStamp(firstExpected);
        safePkg2.addStamp("Invalid Stamp");
        // +1 for first match, -1 for duplicate, -1 for invalid stamp,
        // -(expected2.size() - 1) for remaining missed stamps
        int expectedScore = 1 - 1 - 1 - (expected2.size() - 1);
        assertEquals(expectedScore, safePkg2.gradeStamps());
    }


    // ----------------------------------------------------------
    /**
     * Tests gradeStamps on an unsafe package, verifying it always scores -5.
     */
    public void testGradeStampsUnsafe()
    {
        Package unsafePkg = findPackageBySafety(false);
        assertEquals(-5, unsafePkg.gradeStamps());

        Package stampedUnsafe = findPackageBySafety(false);
        for (String stamp : getExpectedStamps(stampedUnsafe))
        {
            stampedUnsafe.addStamp(stamp);
        }
        assertEquals(-5, stampedUnsafe.gradeStamps());
    }


    // ----------------------------------------------------------
    /**
     * Tests grading across weight and volume thresholds to cover all size and
     * load categories.
     */
    public void testGradeStampsAllCategories()
    {
        boolean sawLight = false;
        boolean sawMediumLoad = false;
        boolean sawHeavy = false;
        boolean sawSmall = false;
        boolean sawMediumSize = false;
        boolean sawLarge = false;

        int attempts = 0;
        while ((!sawLight || !sawMediumLoad || !sawHeavy || !sawSmall
            || !sawMediumSize || !sawLarge) && attempts < 5000)
        {
            attempts++;
            Package pkg = new Package();
            if (!isSafe(pkg))
            {
                continue;
            }
            if (pkg.weigh() < 10)
            {
                sawLight = true;
            }
            else if (pkg.weigh() < 20)
            {
                sawMediumLoad = true;
            }
            else
            {
                sawHeavy = true;
            }

            if (pkg.measure() < 2000)
            {
                sawSmall = true;
            }
            else if (pkg.measure() < 5000)
            {
                sawMediumSize = true;
            }
            else
            {
                sawLarge = true;
            }

            List<String> expected = getExpectedStamps(pkg);
            for (String stamp : expected)
            {
                pkg.addStamp(stamp);
            }
            assertEquals(expected.size(), pkg.gradeStamps());
        }

        assertTrue(sawLight);
        assertTrue(sawMediumLoad);
        assertTrue(sawHeavy);
        assertTrue(sawSmall);
        assertTrue(sawMediumSize);
        assertTrue(sawLarge);
    }

    // ~ Private Helper Methods ................................................


    // ----------------------------------------------------------
    /**
     * Generates packages until one with the requested safety status is found.
     *
     * @param desiredSafe
     *            true for a safe package, false for an unsafe package
     * @return a package matching the requested safety status
     */
    private Package findPackageBySafety(boolean desiredSafe)
    {
        Package candidate = new Package();
        int tries = 0;
        while (isSafe(candidate) != desiredSafe && tries < 1000)
        {
            candidate = new Package();
            tries++;
        }
        assertEquals(desiredSafe, isSafe(candidate));
        return candidate;
    }


    // ----------------------------------------------------------
    /**
     * Determines whether a package's contents are safe to ship.
     *
     * @param pkg
     *            the package to inspect
     * @return true if all contents are safe, false otherwise
     */
    private boolean isSafe(Package pkg)
    {
        List<String> unsafeItems = Arrays.asList(
            "Fireworks",
            "Gas",
            "Live Snake",
            "Live Bird",
            "Unidentified Bugs");
        for (String item : pkg.viewContents().split(", "))
        {
            if (unsafeItems.contains(item))
            {
                return false;
            }
        }
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Computes the list of stamps expected for a given package.
     *
     * @param pkg
     *            the package to inspect
     * @return the expected stamp names
     */
    private List<String> getExpectedStamps(Package pkg)
    {
        List<String> fragileItems = Arrays.asList(
            "Live Snake",
            "Live Bird",
            "Glass Cup",
            "Ceramic Mug",
            "Camera");
        List<String> flammableItems =
            Arrays.asList("Fireworks", "Gas", "Charger", "Battery");
        List<String> dryItems = Arrays.asList(
            "Painting",
            "Live Snake",
            "Live Bird",
            "Charger",
            "Camera",
            "Battery");

        boolean fragile = false;
        boolean flammable = false;
        boolean keepDry = false;
        for (String item : pkg.viewContents().split(", "))
        {
            fragile = fragile || fragileItems.contains(item);
            flammable = flammable || flammableItems.contains(item);
            keepDry = keepDry || dryItems.contains(item);
        }

        List<String> stamps = new ArrayList<String>();
        if (fragile)
        {
            stamps.add("Fragile");
        }
        if (flammable)
        {
            stamps.add("Flammable");
        }
        if (keepDry)
        {
            stamps.add("Keep Dry");
        }

        if (pkg.weigh() < 10)
        {
            stamps.add("Light Load");
        }
        else if (pkg.weigh() < 20)
        {
            stamps.add("Medium Load");
        }
        else
        {
            stamps.add("Heavy Load");
        }

        if (pkg.measure() < 2000)
        {
            stamps.add("Small");
        }
        else if (pkg.measure() < 5000)
        {
            stamps.add("Medium");
        }
        else
        {
            stamps.add("Large");
        }

        stamps.add(pkg.readDestination());
        return stamps;
    }
}

package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// -------------------------------------------------------------------------
/**
 * Represents a single package with a weight, dimensions, destination,
 * contents, the properties those contents imply (fragile, flammable, keep
 * dry, safe), the stamps the player applied, and the stamps that should have
 * been applied.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class Package
{
    // ~ Fields ................................................................

    /** Maximum number of stamps that fit on one package. */
    public static final int MAX_STAMPS = 20;

    /** Points lost when an unsafe package is processed instead of denied. */
    public static final int UNSAFE_PENALTY = -5;

    private static final int MAX_CONTENTS = 5;
    private static final int UNSAFE_ODDS = 5;
    private static final int LIGHT_LIMIT = 10;
    private static final int MEDIUM_LIMIT = 20;
    private static final int SMALL_LIMIT = 2000;
    private static final int MEDIUM_SIZE_LIMIT = 5000;

    private static final String[] DESTINATIONS = { "Virginia Beach",
        "Chesapeake", "Arlington", "Richmond", "Norfolk", "Newport News",
        "Alexandria", "Hampton", "Suffolk", "Roanoke" };

    // Every array below is indexed the same way as CONTENT_NAMES.
    private static final String[] CONTENT_NAMES = { "Candy", "Clothes",
        "Fireworks", "Painting", "Shoes", "Gas", "Stuffed Animal", "Chips",
        "Live Snake", "Live Bird", "Glass Cup", "Charger", "Letter",
        "Unidentified Bugs", "Ceramic Mug", "Camera", "Pencils", "Battery" };
    private static final boolean[] FRAGILE = flags(8, 9, 10, 14, 15);
    private static final boolean[] FLAMMABLE = flags(2, 5, 11, 17);
    private static final boolean[] KEEP_DRY = flags(3, 8, 9, 11, 15, 17);
    private static final boolean[] UNSAFE = flags(2, 5, 8, 9, 13);

    private final int weight;
    private final int length;
    private final int width;
    private final int height;
    private final String destination;
    private final boolean isFragile;
    private final boolean isFlammable;
    private final boolean keepDry;
    private final boolean isSafe;
    private final String[] contents;
    private final String[] actualStamps;
    private String[] stampsApplied;
    private int numStamps;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a package with random properties.
     */
    public Package()
    {
        this(new Random());
    }


    // ----------------------------------------------------------
    /**
     * Creates a package with random properties drawn from the given random
     * number generator. Using a seeded generator gives repeatable packages.
     *
     * @param random
     *            the source of randomness
     */
    public Package(Random random)
    {
        this(random.nextInt(30) + 1, random.nextInt(27) + 4,
            random.nextInt(8) + 3, random.nextInt(18) + 3,
            randomDestination(random), randomContents(random));
    }


    // ----------------------------------------------------------
    /**
     * Creates a package with the given properties. The fragile, flammable,
     * keep dry and safe properties and the correct stamps are worked out from
     * the contents, weight, size and destination.
     *
     * @param weight
     *            weight of the package
     * @param length
     *            length of the package
     * @param width
     *            width of the package
     * @param height
     *            height of the package
     * @param destination
     *            where the package is going
     * @param contents
     *            the items inside the package
     */
    public Package(
        int weight,
        int length,
        int width,
        int height,
        String destination,
        String[] contents)
    {
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.destination = destination == null ? "Unknown" : destination;
        this.contents = contents == null ? new String[0] : contents.clone();

        boolean fragile = false;
        boolean flammable = false;
        boolean dry = false;
        boolean safe = true;
        for (int i = 0; i < this.contents.length; i++)
        {
            int index = indexOfContent(this.contents[i]);
            if (index >= 0)
            {
                fragile = fragile || FRAGILE[index];
                flammable = flammable || FLAMMABLE[index];
                dry = dry || KEEP_DRY[index];
                safe = safe && !UNSAFE[index];
            }
        }
        isFragile = fragile;
        isFlammable = flammable;
        keepDry = dry;
        isSafe = safe;

        stampsApplied = new String[MAX_STAMPS];
        numStamps = 0;
        actualStamps = new String[MAX_STAMPS];
        int count = 0;
        if (isFragile)
        {
            actualStamps[count++] = "Fragile";
        }
        if (isFlammable)
        {
            actualStamps[count++] = "Flammable";
        }
        if (keepDry)
        {
            actualStamps[count++] = "Keep Dry";
        }
        actualStamps[count++] = weightStamp();
        actualStamps[count++] = sizeStamp();
        actualStamps[count++] = this.destination;
    }


    // ~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Returns the weight of this package.
     *
     * @return the weight
     */
    public int weigh()
    {
        return weight;
    }


    // ----------------------------------------------------------
    /**
     * Returns the volume of this package (length * width * height).
     *
     * @return the volume
     */
    public int measure()
    {
        return length * width * height;
    }


    // ----------------------------------------------------------
    /**
     * Returns the destination of this package.
     *
     * @return the destination
     */
    public String readDestination()
    {
        return destination;
    }


    // ----------------------------------------------------------
    /**
     * Returns the contents of this package as a comma separated list.
     *
     * @return the contents, or "No contents" if the package is empty
     */
    public String viewContents()
    {
        if (contents.length == 0)
        {
            return "No contents";
        }
        return join(contents, contents.length);
    }


    // ----------------------------------------------------------
    /**
     * Adds a stamp to this package.
     *
     * @param newStamp
     *            the name of the stamp to add
     * @return true if the stamp was added, false if the stamp is null or
     *         empty or there is no room left for more stamps
     */
    public boolean addStamp(String newStamp)
    {
        if (newStamp == null || newStamp.trim().isEmpty())
        {
            return false;
        }
        if (numStamps >= stampsApplied.length)
        {
            return false;
        }
        stampsApplied[numStamps] = newStamp;
        numStamps++;
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Grades the stamps the player applied. The player gains a point for each
     * stamp that is also a correct stamp (duplicates are counted separately)
     * and loses a point for each extra stamp and each missed stamp. An unsafe
     * package scores {@link #UNSAFE_PENALTY} no matter what, because it
     * should have been denied. A package with no stamps scores 0.
     *
     * @return the score for this package
     */
    public int gradeStamps()
    {
        if (!isSafe)
        {
            return UNSAFE_PENALTY;
        }
        if (numStamps == 0)
        {
            return 0;
        }
        boolean[] matched = new boolean[actualStamps.length];
        int score = 0;
        for (int i = 0; i < numStamps; i++)
        {
            boolean found = false;
            for (int j = 0; j < actualStamps.length && !found; j++)
            {
                if (actualStamps[j] != null && !matched[j]
                    && actualStamps[j].equals(stampsApplied[i]))
                {
                    matched[j] = true;
                    found = true;
                    score++;
                }
            }
            if (!found)
            {
                score--;
            }
        }
        for (int j = 0; j < actualStamps.length; j++)
        {
            if (actualStamps[j] != null && !matched[j])
            {
                score--;
            }
        }
        return score;
    }


    // ----------------------------------------------------------
    /**
     * Returns the stamps applied so far as a comma separated list.
     *
     * @return the applied stamps, or "No stamps" if none have been applied
     */
    public String viewStamps()
    {
        if (numStamps == 0)
        {
            return "No stamps";
        }
        return join(stampsApplied, numStamps);
    }


    // ----------------------------------------------------------
    /**
     * Returns whether the contents of this package are safe to ship.
     *
     * @return true if the package is safe
     */
    public boolean isSafe()
    {
        return isSafe;
    }


    // ----------------------------------------------------------
    /**
     * Returns how many stamps have been applied to this package.
     *
     * @return the number of applied stamps
     */
    public int getNumStamps()
    {
        return numStamps;
    }


    // ----------------------------------------------------------
    /**
     * Returns a copy of the stamps that should be applied to this package.
     * Used for testing; the game never shows these to the player.
     *
     * @return the correct stamps
     */
    String[] getRequiredStamps()
    {
        int count = 0;
        while (count < actualStamps.length && actualStamps[count] != null)
        {
            count++;
        }
        String[] result = new String[count];
        for (int i = 0; i < count; i++)
        {
            result[i] = actualStamps[i];
        }
        return result;
    }


    // ~ Private Methods .......................................................

    private String weightStamp()
    {
        if (weight < LIGHT_LIMIT)
        {
            return "Light Load";
        }
        else if (weight < MEDIUM_LIMIT)
        {
            return "Medium Load";
        }
        return "Heavy Load";
    }


    private String sizeStamp()
    {
        int volume = measure();
        if (volume < SMALL_LIMIT)
        {
            return "Small";
        }
        else if (volume < MEDIUM_SIZE_LIMIT)
        {
            return "Medium";
        }
        return "Large";
    }


    private static String join(String[] items, int count)
    {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < count; i++)
        {
            if (i > 0)
            {
                str.append(", ");
            }
            str.append(items[i]);
        }
        return str.toString();
    }


    private static boolean[] flags(int... trueIndexes)
    {
        boolean[] result = new boolean[CONTENT_NAMES.length];
        for (int i = 0; i < trueIndexes.length; i++)
        {
            result[trueIndexes[i]] = true;
        }
        return result;
    }


    private static int indexOfContent(String item)
    {
        for (int i = 0; i < CONTENT_NAMES.length; i++)
        {
            if (CONTENT_NAMES[i].equals(item))
            {
                return i;
            }
        }
        return -1;
    }


    private static String randomDestination(Random random)
    {
        return DESTINATIONS[random.nextInt(DESTINATIONS.length)];
    }


    /**
     * Picks one to five different items. Roughly one package in five holds at
     * least one unsafe item; the rest hold only safe items, so the player is
     * not buried in packages that must be denied.
     */
    private static String[] randomContents(Random random)
    {
        List<Integer> safeItems = new ArrayList<Integer>();
        List<Integer> unsafeItems = new ArrayList<Integer>();
        for (int i = 0; i < CONTENT_NAMES.length; i++)
        {
            if (UNSAFE[i])
            {
                unsafeItems.add(i);
            }
            else
            {
                safeItems.add(i);
            }
        }
        Collections.shuffle(safeItems, random);
        Collections.shuffle(unsafeItems, random);

        int count = random.nextInt(MAX_CONTENTS) + 1;
        List<String> chosen = new ArrayList<String>();
        if (random.nextInt(UNSAFE_ODDS) == 0)
        {
            chosen.add(CONTENT_NAMES[unsafeItems.get(0)]);
        }
        int next = 0;
        while (chosen.size() < count)
        {
            chosen.add(CONTENT_NAMES[safeItems.get(next)]);
            next++;
        }
        Collections.shuffle(chosen, random);
        return chosen.toArray(new String[chosen.size()]);
    }
}

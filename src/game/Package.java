package game;

import java.util.Random;

// -------------------------------------------------------------------------
/**
 * Represents a single package with a weight, dimensions, destination, contents,
 * the properties those contents imply (fragile, flammable, keep dry, safe), the
 * stamps the player applied, and the stamps that should have been applied.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class Package
{
    // ~ Fields ................................................................
    private int weight;
    private int length;
    private int width;
    private int height;
    private String destination;
    private boolean isFragile;
    private boolean isFlammable;
    private boolean keepDry;
    private boolean isSafe;
    private String[] contents;
    private String[] stampsApplied;
    private int numStamps;
    private String[] actualStamps;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a package with random weight (1-30 lbs), dimensions, destination,
     * and 1 to 5 random contents, and determines the stamps required for
     * grading.
     */
    public Package()
    {
        Random random = new Random();

        // Random Weight
        weight = random.nextInt(30) + 1;

        // Random Size
        length = random.nextInt(27) + 4;
        width = random.nextInt(8) + 3;
        height = random.nextInt(18) + 3;

        // Random Destination
        String[] possibleDestinations = { "Virginia Beach", "Chesapeake",
            "Arlington", "Richmond", "Norfolk", "Newport News", "Alexandria",
            "Hampton", "Suffolk", "Roanoke" };
        destination =
            possibleDestinations[random.nextInt(possibleDestinations.length)];

        // Random Contents and Corresponding Labels
        String[] possibleContents = { "Candy", "Clothes", "Fireworks",
            "Painting", "Shoes", "Gas", "Stuffed Animal", "Chips", "Live Snake",
            "Live Bird", "Glass Cup", "Charger", "Letter", "Unidentified Bugs",
            "Ceramic Mug", "Camera", "Pencils", "Battery" };
        boolean[] fragility =
            { false, false, false, false, false, false, false, false, true,
                true, true, false, false, false, true, true, false, false };
        boolean[] flammability =
            { false, false, true, false, false, true, false, false, false,
                false, false, true, false, false, false, false, false, true };
        boolean[] dryNeeds =
            { false, false, false, true, false, false, false, false, true, true,
                false, true, false, false, false, true, false, true };
        boolean[] safety = { true, true, false, true, true, false, true, true,
            false, false, true, true, true, false, true, true, true, true };
        isFragile = false;
        isFlammable = false;
        keepDry = false;
        isSafe = true;
        int numContents = random.nextInt(5) + 1;
        contents = new String[numContents];
        for (int i = 0; i < numContents; i++)
        {
            int index = random.nextInt(possibleContents.length);
            contents[i] = possibleContents[index];
            isFragile = isFragile || fragility[index];
            isFlammable = isFlammable || flammability[index];
            keepDry = keepDry || dryNeeds[index];
            isSafe = isSafe && safety[index];
        }

        // Create List for Stamps the User Applies

        stampsApplied = new String[10];
        numStamps = 0;

        // Create Lift of Actually Needed Stamps for Grading
        actualStamps = new String[10];
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
        if (weight < 10)
        {
            actualStamps[count++] = "Light Load";
        }
        else if (weight < 20)
        {
            actualStamps[count++] = "Medium Load";
        }
        else
        {
            actualStamps[count++] = "Heavy Load";
        }
        if (measure() < 2000)
        {
            actualStamps[count++] = "Small";
        }
        else if (measure() < 5000)
        {
            actualStamps[count++] = "Medium";
        }
        else
        {
            actualStamps[count++] = "Large";
        }
        actualStamps[count++] = destination;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Returns the weight of this package in lbs.
     *
     * @return the weight
     */
    public int weigh()
    {
        return weight;
    }


    // ----------------------------------------------------------
    /**
     * Returns the volume of this package in cubic inches (length * width *
     * height).
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
        String str = "";
        for (int i = 0; i < contents.length - 1; i++)
        {
            str += contents[i] + ", ";
        }
        return str + contents[contents.length - 1];
    }


    // ----------------------------------------------------------
    /**
     * Adds a stamp to this package (up to a maximum of 10 stamps).
     *
     * @param newStamp
     *            the name of the stamp to add
     * @return true if the stamp was added, false if the stamp is null or empty
     *             or there is no room left for more stamps
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
     * Grades the stamps the player applied. If the package is unsafe, returns
     * -5 regardless of applied stamps because it should have been denied.
     * Otherwise, the player gains 1 point for each applied stamp that matches a
     * required stamp (duplicates beyond the required count are penalized) and
     * loses 1 point for each extra stamp and each missed stamp. Note that
     * grading clears matched entries from the internal expected stamps array.
     *
     * @return the score for this package
     */
    public int gradeStamps()
    {
        if (!isSafe)
        {
            return -5;
        }
        int score = 0;
        for (int i = 0; i < numStamps; i++)
        {
            boolean found = false;
            for (int j = 0; j < actualStamps.length && !found; j++)
            {
                if (stampsApplied[i].equals(actualStamps[j]))
                {
                    actualStamps[j] = null;
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
            if (actualStamps[j] != null)
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
        String str = "";
        for (int i = 0; i < numStamps - 1; i++)
        {
            str += stampsApplied[i] + ", ";
        }
        return str + stampsApplied[numStamps - 1];
    }
}

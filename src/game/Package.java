package game;

import java.util.Random;

public class Package
{
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
            "Arlington", "Richmond", "Norfolk", "Newport News", " Alexandria",
            "Hampton", "Suffolk", "Roanoke" };
        destination = possibleDestinations[random
            .nextInt(possibleDestinations.length)];

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
        boolean[] dryNeeds = { false, false, false, true, false, false, false,
            false, true, true, false, true, false, false, false, false, true,
            false, false, true };
        boolean[] safety = { true, true, false, true, true, false, true, true,
            false, false, true, true, true, false, true, true, true, true };
        isFragile = false;
        isFlammable = false;
        keepDry = false;
        isSafe = false;
        int numContents = random.nextInt(5) + 1;
        contents = new String[numContents];
        for (int i = 0; i < numContents; i++)
        {
            int index = random.nextInt(possibleContents.length);
            contents[i] = possibleContents[index];
            isFragile = isFragile || fragility[index];
            isFlammable = isFlammable || flammability[index];
            keepDry = keepDry || dryNeeds[index];
            isSafe = isSafe || safety[index];
        }

        // Create List for Stamps the User Applies
        stampsApplied = new String[10];

        // Create List of Actually Needed Stamps for Grading
        actualStamps = new String[10];
        if (isFragile)
        {
            actualStamps[0] = "Fragile";
        }
        if (isFlammable)
        {
            actualStamps[1] = "Flammable";
        }
        if (keepDry)
        {
            actualStamps[2] = "Keep Dry";
        }
        if (weight < 10)
        {
            actualStamps[3] = "Light Load";
        }
        else if (weight < 20)
        {
            actualStamps[3] = "Medium Load";
        }
        else
        {
            actualStamps[3] = "Heavy Load";
        }
        if (measure() < 2000)
        {
            actualStamps[4] = "Small";
        }
        else if (measure() < 5000)
        {
            actualStamps[4] = "Medium";
        }
        else
        {
            actualStamps[4] = "Large";
        }
        actualStamps[5] = destination;
    }


    public int weigh()
    {
        return weight;
    }


    public int measure()
    {
        return (int)length * width * height;
    }


    public String readDestination()
    {
        return destination;
    }


    public String viewContents()
    {
        String str = "";
        for (int i = 0; i < contents.length - 1; i++)
        {
            str += contents[i] + ", ";
        }
        return str + contents[contents.length - 1];
    }


    public boolean addStamp(String newStamp)
    {
        if (numStamps == stampsApplied.length)
        {
            return false;
        }
        stampsApplied[numStamps] = newStamp;
        return true;
    }


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
                    score += 1;
                    actualStamps[j] = null;
                    found = true;
                }
            }
            if (!found)
            {
                score -= 1;
            }
        }
        for (int i = 0; i < actualStamps.length; i++)
        {
            if (actualStamps[i] != null)
            {
                score -= 1;
            }
        }
        return score;
    }


    public String viewStamps()
    {
        String str = "";
        for (int i = 0; i < stampsApplied.length - 1; i++)
        {
            str += stampsApplied[i] + ", ";
        }
        return str + stampsApplied[stampsApplied.length - 1];
    }
}

package game;

import java.util.ArrayList;

public class Model
{
    private Queue<Package> unprocessedPackages;
    private Queue<Package> processedPackages;
    private Package[][] shelves;
    private ArrayList<Integer> scores;
    private Package currentPackage;

    public Model()
    {
        unprocessedPackages = new Queue<Package>();
        processedPackages = new Queue<Package>();
        shelves = new Package[5][5];
        scores = new ArrayList<Integer>();
        currentPackage = null;
    }


    public void startDay()
    {
        for (int i = 0; i < 10; i++)
        {
            unprocessedPackages.enqueue(new Package());
        }
        processedPackages.clear();
        currentPackage = null;
    }


    public void addToQueue()
    {
        unprocessedPackages.enqueue(new Package());
    }


    public void checkQueue()
    {
        if (unprocessedPackages.size() < 3)
        {
            unprocessedPackages.enqueue(new Package());
        }
    }


    public String viewShelves()
    {
        if (shelves == null)
        {
            return "No shelves initalized";
        }
        String str = "";
        for (int i = 0; i < shelves.length; i++)
        {
            str += " -----   -----    -----    -----   ----- \n";
            for (int j = 0; j < shelves[i].length; j++)
            {
                if (shelves[i][j] != null)
                {
                    str += "|  x  | ";
                }
                else
                {
                    str += "|     | ";
                }
            }
        }
        return str;
    }


    public String viewShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null)
        {
            return "No shelves initalized";
        }
        if (shelfNumber >= shelves.length || shelveNumber >= shelves[0].length)
        {
            return "Invalid shelf position";
        }
        return shelves[shelfNumber][shelveNumber].viewStamps();
    }


    public boolean addToShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null || shelfNumber >= shelves.length
            || shelveNumber >= shelves[0].length)
        {
            return false;
        }
        if (shelves[shelfNumber][shelveNumber] != null)
        {
            return false;
        }
        shelves[shelfNumber][shelveNumber] = processedPackages.dequeue();
        currentPackage = null;
        return true;
    }


    public boolean removeFromShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null || shelfNumber >= shelves.length
            || shelveNumber >= shelves[0].length || processedPackages.isEmpty())
        {
            return false;
        }
        if (shelves[shelfNumber][shelveNumber] == null)
        {
            return false;
        }
        processedPackages.enqueue(shelves[shelfNumber][shelveNumber]);
        shelves[shelfNumber][shelveNumber] = null;
        return true;
    }


    public int calculateScore()
    {
        int totalScore = 0;
        for (int i = 0; i < 30 && !processedPackages.isEmpty(); i++)
        {
            totalScore += processedPackages.dequeue().gradeStamps();
        }
        scores.add(totalScore);
        return totalScore;
    }


    public String viewScores()
    {
        if (scores == null || scores.isEmpty())
        {
            return "No scores recorded";
        }
        String str = "";
        for (int i = 0; i < scores.size(); i++)
        {
            str += "Day " + (i + 1) + ": " + scores.get(i) + "\n";
        }
        return str;
    }


    public void startProcessing()
    {
        currentPackage = unprocessedPackages.dequeue();
    }


    public int weighPackage()
    {
        return currentPackage.weigh();
    }


    public int measurePackage()
    {
        return currentPackage.measure();
    }


    public String readDestinationOfPackage()
    {
        return currentPackage.readDestination();
    }


    public String viewContentsOfPackage()
    {
        return currentPackage.viewContents();
    }


    public boolean addStampToPackage(String stamp)
    {
        return currentPackage.addStamp(stamp);
    }


    public void denyPackage()
    {
        currentPackage = null;
    }


    public void finishProcessing()
    {
        processedPackages.enqueue(currentPackage);
        currentPackage = null;
    }
}

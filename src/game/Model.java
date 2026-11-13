package game;

import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Handles the state of the game: the queues of packages, the shelves, the
 * package currently being processed, and the scores for each day.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class Model
{
    // ~ Fields ................................................................
    private Queue<Package> unprocessedPackages;
    private Queue<Package> processedPackages;
    private Package[][] shelves;
    private ArrayList<Integer> scores;
    private Package currentPackage;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a model with empty package queues, an empty 5x5 grid of shelves,
     * no recorded scores, and no package currently being processed.
     */
    public Model()
    {
        unprocessedPackages = new Queue<Package>();
        processedPackages = new Queue<Package>();
        shelves = new Package[5][5];
        scores = new ArrayList<Integer>();
        currentPackage = null;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Starts a new day by ensuring the unprocessed queue has been checked for
     * new packages and clearing any package currently being processed.
     */
    public void startDay()
    {
        checkQueue();
        currentPackage = null;
    }


    // ----------------------------------------------------------
    /**
     * Creates a new random package and adds it to the back of the unprocessed
     * queue.
     */
    public void addToQueue()
    {
        unprocessedPackages.enqueue(new Package());
    }


    // ----------------------------------------------------------
    /**
     * Adds one new random package to the unprocessed queue if it currently
     * holds fewer than 3 packages.
     */
    public void checkQueue()
    {
        if (unprocessedPackages.size() < 3)
        {
            addToQueue();
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns an ASCII diagram of the shelves, with an x in each slot that
     * holds a package.
     *
     * @return the diagram of the shelves, or an error message if shelves are
     *             not initialized
     */
    public String viewShelves()
    {
        if (shelves == null)
        {
            return "No shelves initalized";
        }
        String str = "";
        for (int i = 0; i < shelves.length; i++)
        {
            str += " -----   -----   -----   -----   ----- \n";
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
            str += "\n";
        }
        str += " -----   -----   -----   -----   ----- \n";
        return str;
    }


    // ----------------------------------------------------------
    /**
     * Returns the stamps applied to the package in one slot of the shelves.
     *
     * @param shelfNumber
     *            the shelf (row), starting at 0
     * @param shelveNumber
     *            the slot on that shelf (column), starting at 0
     * @return the applied stamps of the package (or "No stamps"); "No package
     *             exists here" if the slot is empty; "Invalid shelf position"
     *             if the indices exceed the shelf bounds; or an error message
     *             if shelves are not initialized
     */
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
        Package p = shelves[shelfNumber][shelveNumber];
        if (p == null)
        {
            return "No package exists here";
        }
        return p.viewStamps();
    }


    // ----------------------------------------------------------
    /**
     * Moves the package at the front of the processed queue onto a shelf.
     *
     * @param shelfNumber
     *            the shelf (row), starting at 0
     * @param shelveNumber
     *            the slot on that shelf (column), starting at 0
     * @return true if the package was placed; false if shelves are not
     *             initialized, the indices exceed the shelf bounds, the slot
     *             already holds a package, or there is no processed package to
     *             place
     */
    public boolean addToShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null || shelfNumber >= shelves.length
            || shelveNumber >= shelves[0].length || processedPackages.isEmpty())
        {
            return false;
        }
        if (shelves[shelfNumber][shelveNumber] != null)
        {
            return false;
        }
        shelves[shelfNumber][shelveNumber] = processedPackages.dequeue();
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Moves the package in a shelf slot to the back of the processed queue.
     *
     * @param shelfNumber
     *            the shelf (row), starting at 0
     * @param shelveNumber
     *            the slot on that shelf (column), starting at 0
     * @return true if a package was moved; false if shelves are not
     *             initialized, the indices exceed the shelf bounds, or the slot
     *             is empty
     */
    public boolean removeFromShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null || shelfNumber >= shelves.length
            || shelveNumber >= shelves[0].length)
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


    // ----------------------------------------------------------
    /**
     * Scores up to the first 30 packages in the processed queue, removes them
     * from the queue, and records the total score for the day.
     *
     * @return the total score, or 0 if there are no processed packages
     */
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


    // ----------------------------------------------------------
    /**
     * Returns the score recorded for each day played.
     *
     * @return one line per day, or "No scores recorded\n" if no day has been
     *             scored
     */
    public String viewScores()
    {
        if (scores == null || scores.isEmpty())
        {
            return "No scores recorded\n";
        }
        String str = "";
        for (int i = 0; i < scores.size(); i++)
        {
            str += "Day " + (i + 1) + ": " + scores.get(i) + "\n";
        }
        return str;
    }


    // ----------------------------------------------------------
    /**
     * Takes the package at the front of the unprocessed queue to work on.
     * Callers should finish or deny the previous package first.
     *
     * @return true if a package is now being processed, false if there were no
     *             packages waiting
     */
    public boolean startProcessing()
    {
        currentPackage = unprocessedPackages.dequeue();
        return currentPackage != null;
    }


    // ----------------------------------------------------------
    /**
     * Returns the weight of the current package.
     *
     * @return the weight, or -1 if no package is being processed
     */
    public int weighPackage()
    {
        if (currentPackage == null)
        {
            return -1;
        }
        return currentPackage.weigh();
    }


    // ----------------------------------------------------------
    /**
     * Returns the volume of the current package.
     *
     * @return the volume, or -1 if no package is being processed
     */
    public int measurePackage()
    {
        if (currentPackage == null)
        {
            return -1;
        }
        return currentPackage.measure();
    }


    // ----------------------------------------------------------
    /**
     * Returns the destination of the current package.
     *
     * @return the destination, or "No package currently being processed"
     */
    public String readDestinationOfPackage()
    {
        if (currentPackage == null)
        {
            return "No package currently being processed";
        }
        return currentPackage.readDestination();
    }


    // ----------------------------------------------------------
    /**
     * Returns the contents of the current package.
     *
     * @return the contents, or "No package currently being processed"
     */
    public String viewContentsOfPackage()
    {
        if (currentPackage == null)
        {
            return "No package currently being processed";
        }
        return currentPackage.viewContents();
    }


    // ----------------------------------------------------------
    /**
     * Adds a stamp to the current package.
     *
     * @param stamp
     *            the name of the stamp
     * @return true if the stamp was added; false if no package is being
     *             processed or the package has no room for another stamp
     */
    public boolean addStampToPackage(String stamp)
    {
        if (currentPackage == null)
        {
            return false;
        }
        return currentPackage.addStamp(stamp);
    }


    // ----------------------------------------------------------
    /**
     * Denies the current package. It is thrown away and not scored.
     */
    public void denyPackage()
    {
        currentPackage = null;
    }


    // ----------------------------------------------------------
    /**
     * Finishes the current package by adding it to the back of the processed
     * queue.
     */
    public void finishProcessing()
    {
        if (currentPackage != null)
        {
            processedPackages.enqueue(currentPackage);
            currentPackage = null;
        }
    }
}

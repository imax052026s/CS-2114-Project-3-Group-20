package game;

import java.util.ArrayList;
import java.util.Random;

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

    /** Number of shelves (rows). */
    public static final int SHELF_ROWS = 5;

    /** Number of slots on each shelf (columns). */
    public static final int SHELF_SLOTS = 5;

    /** Number of processed packages that fit on the vehicle (and are scored). */
    public static final int VEHICLE_CAPACITY = 30;

    /** Number of packages waiting when a day starts. */
    public static final int STARTING_PACKAGES = 10;

    /** The queue is topped up whenever it holds fewer packages than this. */
    public static final int MIN_QUEUE_SIZE = 3;

    private static final String NO_SHELVES = "No shelves initialized";
    private static final String NO_PACKAGE = "No package currently being "
        + "processed";

    private Queue<Package> unprocessedPackages;
    private Queue<Package> processedPackages;
    private Package[][] shelves;
    private ArrayList<Integer> scores;
    private Package currentPackage;
    private Random random;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a model with no packages and empty shelves.
     */
    public Model()
    {
        this(new Random());
    }


    // ----------------------------------------------------------
    /**
     * Creates a model that makes its packages with the given random number
     * generator. A seeded generator makes the game repeatable.
     *
     * @param random
     *            the source of randomness
     */
    public Model(Random random)
    {
        this.random = random;
        unprocessedPackages = new Queue<Package>();
        processedPackages = new Queue<Package>();
        shelves = new Package[SHELF_ROWS][SHELF_SLOTS];
        scores = new ArrayList<Integer>();
        currentPackage = null;
    }


    // ~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Starts a new day. Fills the unprocessed queue with random packages and
     * clears the processed queue, the shelves and the current package. If a
     * day was already in progress it is reset.
     */
    public void startDay()
    {
        unprocessedPackages.clear();
        processedPackages.clear();
        shelves = new Package[SHELF_ROWS][SHELF_SLOTS];
        currentPackage = null;
        for (int i = 0; i < STARTING_PACKAGES; i++)
        {
            addToQueue();
        }
    }


    // ----------------------------------------------------------
    /**
     * Adds a new random package to the back of the unprocessed queue. If the
     * package cannot be made, the queue is left unchanged.
     */
    public void addToQueue()
    {
        try
        {
            unprocessedPackages.enqueue(new Package(random));
        }
        catch (RuntimeException e)
        {
            // Package generation failed; leave the queue unchanged.
        }
    }


    // ----------------------------------------------------------
    /**
     * Adds a new package to the queue if it holds fewer than
     * {@link #MIN_QUEUE_SIZE} packages.
     */
    public void checkQueue()
    {
        if (unprocessedPackages.size() < MIN_QUEUE_SIZE)
        {
            addToQueue();
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns a picture of the shelves, with an x in each slot that holds a
     * package.
     *
     * @return the picture, or "No shelves initialized" if there are no shelves
     */
    public String viewShelves()
    {
        if (shelves == null)
        {
            return NO_SHELVES;
        }
        StringBuilder str = new StringBuilder();
        str.append("    ");
        for (int j = 0; j < shelves[0].length; j++)
        {
            str.append("   ").append(j + 1).append("    ");
        }
        str.append("\n");
        for (int i = 0; i < shelves.length; i++)
        {
            str.append(border(shelves[i].length)).append("\n");
            str.append(" ").append(i + 1).append("  ");
            for (int j = 0; j < shelves[i].length; j++)
            {
                if (shelves[i][j] != null)
                {
                    str.append("|  x  | ");
                }
                else
                {
                    str.append("|     | ");
                }
            }
            str.append("\n");
        }
        str.append(border(shelves[0].length));
        return str.toString();
    }


    // ----------------------------------------------------------
    /**
     * Describes the package in one slot of the shelves.
     *
     * @param shelfNumber
     *            the shelf (row), starting at 0
     * @param shelveNumber
     *            the slot on that shelf (column), starting at 0
     * @return the contents, size, weight, destination and stamps of the
     *         package; "No package exists here" if the slot is empty;
     *         "Invalid shelf position" if the slot does not exist; or
     *         "No shelves initialized" if there are no shelves
     */
    public String viewShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null)
        {
            return NO_SHELVES;
        }
        if (!isValidPosition(shelfNumber, shelveNumber))
        {
            return "Invalid shelf position";
        }
        Package p = shelves[shelfNumber][shelveNumber];
        if (p == null)
        {
            return "No package exists here";
        }
        return "Contents: " + p.viewContents() + "\nSize: " + p.measure()
            + " in^3\nWeight: " + p.weigh() + " lbs\nDestination: "
            + p.readDestination() + "\nStamps: " + p.viewStamps();
    }


    // ----------------------------------------------------------
    /**
     * Moves the package at the front of the processed queue onto a shelf.
     *
     * @param shelfNumber
     *            the shelf (row), starting at 0
     * @param shelveNumber
     *            the slot on that shelf (column), starting at 0
     * @return true if the package was placed; false if there are no shelves,
     *         the position does not exist, the slot already holds a package,
     *         or there is no processed package to place
     */
    public boolean addToShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null || !isValidPosition(shelfNumber, shelveNumber))
        {
            return false;
        }
        if (shelves[shelfNumber][shelveNumber] != null
            || processedPackages.isEmpty())
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
     * @return true if a package was moved; false if there are no shelves, the
     *         position does not exist, or the slot is empty
     */
    public boolean removeFromShelf(int shelfNumber, int shelveNumber)
    {
        if (shelves == null || !isValidPosition(shelfNumber, shelveNumber))
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
     * Scores the first {@link #VEHICLE_CAPACITY} packages in the processed
     * queue, removes them from the queue, and records the total.
     *
     * @return the total score, or 0 if there are no processed packages
     */
    public int calculateScore()
    {
        int totalScore = 0;
        for (int i = 0; i < VEHICLE_CAPACITY && !processedPackages.isEmpty();
            i++)
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
     * @return one line per day, or "No scores recorded" if no day has been
     *         scored
     */
    public String viewScores()
    {
        if (scores == null || scores.isEmpty())
        {
            return "No scores recorded";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < scores.size(); i++)
        {
            if (i > 0)
            {
                str.append("\n");
            }
            str.append("Day ").append(i + 1).append(": ")
                .append(scores.get(i));
        }
        return str.toString();
    }


    // ----------------------------------------------------------
    /**
     * Takes the package at the front of the unprocessed queue to work on.
     * Callers should finish or deny the previous package first.
     *
     * @return true if a package is now being processed, false if there were
     *         no packages waiting
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
            return NO_PACKAGE;
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
            return NO_PACKAGE;
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
     *         processed or the package has no room for another stamp
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


    // ----------------------------------------------------------
    /**
     * Returns how many packages are waiting to be processed.
     *
     * @return the number of unprocessed packages
     */
    public int getUnprocessedCount()
    {
        return unprocessedPackages.size();
    }


    // ----------------------------------------------------------
    /**
     * Returns how many processed packages are waiting to be shelved or
     * scored.
     *
     * @return the number of processed packages
     */
    public int getProcessedCount()
    {
        return processedPackages.size();
    }


    // ----------------------------------------------------------
    /**
     * Returns whether a package is currently being processed.
     *
     * @return true if there is a current package
     */
    public boolean hasCurrentPackage()
    {
        return currentPackage != null;
    }


    // ~ Package-private and private methods ...................................

    // ----------------------------------------------------------
    /**
     * Adds a specific package to the unprocessed queue (used for testing).
     *
     * @param p
     *            the package to add
     */
    void enqueueUnprocessed(Package p)
    {
        unprocessedPackages.enqueue(p);
    }


    // ----------------------------------------------------------
    /**
     * Adds a specific package to the processed queue (used for testing).
     *
     * @param p
     *            the package to add
     */
    void enqueueProcessed(Package p)
    {
        processedPackages.enqueue(p);
    }


    private boolean isValidPosition(int shelf, int slot)
    {
        return shelf >= 0 && shelf < shelves.length && slot >= 0
            && slot < shelves[shelf].length;
    }


    private String border(int slots)
    {
        StringBuilder str = new StringBuilder("    ");
        for (int j = 0; j < slots; j++)
        {
            str.append("------- ");
        }
        return str.toString();
    }
}

package viewControl;

// -------------------------------------------------------------------------
/**
 * Creates a View object.
 * 
 * @author mniac
 * @version Nov 10, 2026
 */
public class View
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Displays the opening menu.
     */
    public void printDayMenu()
    {
        System.out.println("\n===== PRECISION POST =====");
        System.out.println("1. Start a new day");
        System.out.println("2. Exit game");
        System.out.print("Enter your choice (number): ");
    }


    // ----------------------------------------------------------
    /**
     * Displays the main game menu.
     */
    public void printGameMenu()
    {
        System.out.println("\n==== MAILROOM MENU =====");
        System.out.println("1. Process next package");
        System.out.println("2. End day");
        System.out.print("Enter your choice: ");
    }


    // ----------------------------------------------------------
    /**
     * Displays options for examining a package.
     */
    public void printPackageProcessMenu()
    {
        System.out.println("\n===== PACKAGE OPTIONS =====");
        System.out.println("1. Weigh package");
        System.out.println("2. Measure package");
        System.out.println("3. View package contents");
        System.out.println("4. Read delivery destination");
        System.out.println("5. Add a stamp");
        System.out.println("6. Reject package");
        System.out.println("7. Finish package");
        System.out.print("Enter your choice: ");
    }


    // ----------------------------------------------------------
    /**
     * Displays available stamps.
     */
    public void printStampMenu()
    {
        System.out.println("\n===== AVAILABLE STAMPS =====");
        System.out.println("1. Fragile");
        System.out.println("2. Flammable");
        System.out.println("3. Keep Dry");
        System.out.println("4. Handle With Care");
        System.out.println("5. Priority");
        System.out.print("Select a stamp: ");
    }


    // ----------------------------------------------------------
    /**
     * Displays an invalid menu selection message.
     */
    public void printMenuError()
    {
        System.out.println("\nInvalid option. Please try again.");
    }


    // ----------------------------------------------------------
    /**
     * Displays game instructions.
     */
    public void printInstructions()
    {
        System.out.println("\n===== HOW TO PLAY =====");
        System.out.println("Welcome to Precision Post!");
        System.out.println(
            "Your job is to process packages and apply the correct "
                + "stamps before they are shipped.");
        System.out
            .println("\nExamine each package to determine its properties.");
        System.out.println("Apply stamps that correctly describe the package.");
        System.out.println(
            "You earn points for correct stamps and "
                + "lose points for incorrect or missing stamps.");
        System.out.println("\nProcess as many packages as possible each day!");
    }


    // ----------------------------------------------------------
    /**
     * Displays package weight.
     * 
     * @param weight
     *            weight of the package
     */
    public void printWeight(int weight)
    {
        System.out.println("Package weight: " + weight);
    }


    // ----------------------------------------------------------
    /**
     * Displays package volume.
     * 
     * @param volume
     *            volume of the package
     */
    public void printVolume(int volume)
    {
        System.out.println("Package volume: " + volume);
    }


    // ----------------------------------------------------------
    /**
     * Displays the package's destination
     * 
     * @param destination
     *            destination of the package.
     */
    public void printDestination(String destination)
    {
        System.out.println("Destination: " + destination);
    }


    // ----------------------------------------------------------
    /**
     * Displays the contents of the package.
     * 
     * @param contents
     */
    public void printContents(String contents)
    {
        System.out.println("Package contents: " + contents);
    }


    // ----------------------------------------------------------
    /**
     * Displays the daily score.
     * 
     * @param score
     *            score earned that day
     */
    public void printScore(int score)
    {
        System.out.println("\nYour daily score: " + score);
    }
    
    // ----------------------------------------------------------
    /**
     * Displays the scores from all completed days.
     * 
     * @param scores
     *            String containing score history
     */
    public void printFinalScore(String scores)
    {
        System.out.println("\n===== FINAL SCORE HISTORY =====");
        System.out.println(scores);
    }


    // ----------------------------------------------------------
    /**
     * Displays a general message.
     * 
     * @param message
     *            message to display
     */
    public void printMessage(String message)
    {
        System.out.println(message);
    }
    
    /**
     * Displays a message confirming a stamp was added.
     * 
     * @param stamp
     *            name of the stamp added
     */
    public void printStampAdded(String stamp)
    {
        System.out.println(stamp + " stamp successfully added!");
    }
}

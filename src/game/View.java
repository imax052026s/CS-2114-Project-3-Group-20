package game;

// -------------------------------------------------------------------------
/**
 * Displays menus, package information and scores to the player. The view only
 * prints what the controller gives it and never touches the model.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class View
{
    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a view.
     */
    public View()
    {
        // Nothing to set up.
    }

    // ~ Menus .................................................................


    // ----------------------------------------------------------
    /**
     * Displays the menu for starting a new day or leaving the game.
     */
    public void printDayMenu()
    {
        System.out.println("\n===== PRECISION POST =====");
        System.out.println("What would you like to do?");
        System.out.println("A) Start New Day");
        System.out.println("B) Leave Game");
        System.out.print("Enter a choice from the menu: ");
    }


    // ----------------------------------------------------------
    /**
     * Displays the main menu used during a day.
     */
    public void printGameMenu()
    {
        System.out.println("\n===== MAILROOM MENU =====");
        System.out.println("What would you like to do?");
        System.out.println("A) Process a new package");
        System.out.println("B) View which shelves hold packages");
        System.out.println("C) View package on shelf");
        System.out.println("D) Place a package on a shelf");
        System.out.println("E) Remove a package from a shelf");
        System.out.println("F) End Day");
        System.out.print("Enter a choice from the menu: ");
    }


    // ----------------------------------------------------------
    /**
     * Displays the options for examining and finishing a package.
     */
    public void printPackageProcessMenu()
    {
        System.out.println("\n===== PACKAGE OPTIONS =====");
        System.out.println("What would you like to do?");
        System.out.println("A) Weigh Package");
        System.out.println("B) Measure Package");
        System.out.println("C) View Contents of Package");
        System.out.println("D) View Destination of Package");
        System.out.println("E) Add Stamp");
        System.out.println("F) Deny Package");
        System.out.println("G) Finish Processing");
        System.out.print("Enter a choice from the menu: ");
    }


    // ----------------------------------------------------------
    /**
     * Displays the stamps that can be added to a package.
     */
    public void printStampMenu()
    {
        System.out.println("\n===== AVAILABLE STAMPS =====");
        System.out.println("Which stamp would you like to add?");
        System.out.println("A) Add Light Load Stamp");
        System.out.println("B) Add Medium Load Stamp");
        System.out.println("C) Add Heavy Load Stamp");
        System.out.println("D) Add Small Size Stamp");
        System.out.println("E) Add Medium Size Stamp");
        System.out.println("F) Add Large Size Stamp");
        System.out.println("G) Add Fragile Stamp");
        System.out.println("H) Add Flammable Stamp");
        System.out.println("I) Add Keep Dry Stamp");
        System.out.println("J) Add Virginia Beach Stamp");
        System.out.println("K) Add Chesapeake Stamp");
        System.out.println("L) Add Arlington Stamp");
        System.out.println("M) Add Richmond Stamp");
        System.out.println("N) Add Norfolk Stamp");
        System.out.println("O) Add Newport News Stamp");
        System.out.println("P) Add Alexandria Stamp");
        System.out.println("Q) Add Hampton Stamp");
        System.out.println("R) Add Suffolk Stamp");
        System.out.println("S) Add Roanoke Stamp");
        System.out.println("T) Go Back");
        System.out.print("Enter a choice from the menu: ");
    }

    // ~ Errors ................................................................


    // ----------------------------------------------------------
    /**
     * Displays an error for a menu choice that is not available.
     */
    public void printMenuError()
    {
        System.out.println(
            "The entered value is not valid. "
                + "Please enter a single letter option from the menu.");
    }


    // ----------------------------------------------------------
    /**
     * Displays an error for a number that is not valid or out of range.
     */
    public void printIntError()
    {
        System.out.print(
            "The entered value is not valid. "
                + "Please enter a whole number in the range shown: ");
    }

    // ~ Information ...........................................................


    // ----------------------------------------------------------
    /**
     * Displays the instructions shown when the game starts.
     */
    public void printInstructions()
    {
        System.out.println("\n===== HOW TO PLAY =====");
        System.out.println(
            "In this game, you will process packages and ship "
                + "them out across Virginia.");
        System.out.println(
            "You will receive packages that need to be "
                + "processed. View their properties to apply the appropriate "
                + "stamps.");
        System.out.println(
            "One stamp should be for the weight, one should be "
                + "for the size, and one should be for the destination.");
        System.out.println(
            "The weight stamp represents either a light, "
                + "medium, or heavy load. A light load weighs under 10 lbs, a "
                + "medium load weighs under 20 lbs, and any load heavier than "
                + "that is a heavy load.");
        System.out.println(
            "The size stamp represents either a small, medium, "
                + "or large package. A small package has a volume under 2000 "
                + "in^3, a medium package has a volume under 5000 in^3, and any "
                + "package larger than that is large.");
        System.out.println(
            "The destination stamp represents where the "
                + "package must be shipped to. The name of the stamp is the same "
                + "as the destination.");
        System.out.println(
            "Additionally, there are special stamps such as "
                + "'Fragile', 'Flammable', and 'Keep Dry' which should be "
                + "applied based on the contents of the package.");
        System.out.println(
            "Some packages will also be too unsafe to ship. Do "
                + "not worry about stamping these packages, just deny them.");
        System.out.println(
            "Finally, you will be scored when you end the day "
                + "based on the first 30 packages you have processed. You will "
                + "receive a point for each correct stamp added to a package. "
                + "You will lose a point for each incorrectly added stamp or "
                + "stamp missed. If an unsafe package is processed, you will "
                + "lose five points.");
        System.out.println(
            "If you are not yet ready to ship a processed "
                + "package, you can place it on a shelf.");
        System.out.println("Good luck!");
    }


    // ----------------------------------------------------------
    /**
     * Displays the goodbye message.
     */
    public void printLeaveGame()
    {
        System.out.println("Thanks for playing!");
    }


    // ----------------------------------------------------------
    /**
     * Asks for a shelf number.
     */
    public void printReadShelfNumber()
    {
        System.out.print("Enter the shelf number (1-5): ");
    }


    // ----------------------------------------------------------
    /**
     * Asks for a slot number on a shelf.
     */
    public void printReadShelveNumber()
    {
        System.out.print("Enter the slot number (1-5): ");
    }


    // ----------------------------------------------------------
    /**
     * Displays the picture of the shelves.
     *
     * @param shelves
     *            the picture to display
     */
    public void printShelves(String shelves)
    {
        System.out.println(shelves);
    }


    // ----------------------------------------------------------
    /**
     * Displays package details or shelf stamp information.
     *
     * @param description
     *            the package information to display
     */
    public void printPackage(String description)
    {
        System.out.println(description);
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
        System.out.println("Package weight: " + weight + " lbs");
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
        System.out.println("Package volume: " + volume + " in^3");
    }


    // ----------------------------------------------------------
    /**
     * Displays the package's destination.
     *
     * @param destination
     *            destination of the package
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
     *            contents of the package
     */
    public void printContents(String contents)
    {
        System.out.println("Package contents: " + contents);
    }


    // ----------------------------------------------------------
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
     *            text containing the score history
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
}

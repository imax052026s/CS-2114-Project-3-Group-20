package game;

import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Reads and checks the player's input, then updates the model and tells the
 * view what to show. The controller is the only class that talks to both.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class Controller
{
    // ~ Fields ................................................................
    private Scanner scanner;
    private View view;
    private Model model;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a controller that reads from System.in and starts the game loop.
     */
    public Controller()
    {
        setUp();

        run();
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Displays the game instructions and runs the main game loop until the
     * player chooses to leave.
     */
    private void run()
    {
        view.printInstructions();
        boolean playing = true;
        while (playing)
        {
            view.printMessage("\n" + model.viewScores());
            view.printDayMenu();
            char choice = readMenuOption(2);
            if (choice == 'A')
            {
                model.startDay();
                boolean day = true;
                while (day)
                {
                    model.checkQueue();
                    view.printGameMenu();
                    choice = readMenuOption(6);
                    if (choice == 'A')
                    {
                        model.startProcessing();
                        boolean processing = true;
                        while (processing)
                        {
                            view.printPackageProcessMenu();
                            choice = readMenuOption(7);
                            if (choice == 'A')
                            {
                                view.printPackage(
                                    "\nWeight in lbs: " + model.weighPackage());
                            }
                            else if (choice == 'B')
                            {
                                view.printPackage(
                                    "\nVolume in in^3: "
                                        + model.measurePackage());
                            }
                            else if (choice == 'C')
                            {
                                view.printPackage(
                                    "\nContents: "
                                        + model.viewContentsOfPackage());
                            }
                            else if (choice == 'D')
                            {
                                view.printPackage(
                                    "\nDestination: "
                                        + model.readDestinationOfPackage());
                            }
                            else if (choice == 'E')
                            {
                                boolean stampAdded = false;
                                while (!stampAdded)
                                {
                                    view.printStampMenu();
                                    choice = readMenuOption(20);
                                    if (choice == 'A')
                                    {
                                        if (!model
                                            .addStampToPackage("Light Load"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'B')
                                    {
                                        if (!model
                                            .addStampToPackage("Medium Load"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'C')
                                    {
                                        if (!model
                                            .addStampToPackage("Heavy Load"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'D')
                                    {
                                        if (!model.addStampToPackage("Small"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'E')
                                    {
                                        if (!model.addStampToPackage("Medium"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'F')
                                    {
                                        if (!model.addStampToPackage("Large"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'G')
                                    {
                                        if (!model.addStampToPackage("Fragile"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'H')
                                    {
                                        if (!model
                                            .addStampToPackage("Flammable"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'I')
                                    {
                                        if (!model
                                            .addStampToPackage("Keep Dry"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'J')
                                    {
                                        if (!model.addStampToPackage(
                                            "Virginia Beach"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'K')
                                    {
                                        if (!model
                                            .addStampToPackage("Chesapeake"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'L')
                                    {
                                        if (!model
                                            .addStampToPackage("Arlington"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'M')
                                    {
                                        if (!model
                                            .addStampToPackage("Richmond"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'N')
                                    {
                                        if (!model.addStampToPackage("Norfolk"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'O')
                                    {
                                        if (!model
                                            .addStampToPackage("Newport News"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'P')
                                    {
                                        if (!model
                                            .addStampToPackage("Alexandria"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'Q')
                                    {
                                        if (!model.addStampToPackage("Hampton"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'R')
                                    {
                                        if (!model.addStampToPackage("Suffolk"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'S')
                                    {
                                        if (!model.addStampToPackage("Roanoke"))
                                        {
                                            view.printMessage(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if (choice == 'T')
                                    {
                                        stampAdded = true;
                                    }
                                }
                            }
                            else if (choice == 'F')
                            {
                                model.denyPackage();
                                processing = false;
                            }
                            else if (choice == 'G')
                            {
                                model.finishProcessing();
                                processing = false;
                            }
                        }
                    }
                    else if (choice == 'B')
                    {
                        view.printShelves(model.viewShelves());
                    }
                    else if (choice == 'C')
                    {
                        boolean shelfSelected = false;
                        int shelf = 0;
                        while (!shelfSelected)
                        {
                            view.printReadShelfNumber();
                            shelf = readIntOption(1, 5) - 1;
                            if (shelf != -2)
                            {
                                shelfSelected = true;
                            }
                        }
                        boolean shelveSelected = false;
                        int shelve = 0;
                        while (!shelveSelected)
                        {
                            view.printReadShelveNumber();
                            shelve = readIntOption(1, 5) - 1;
                            if (shelve != -2)
                            {
                                shelfSelected = true;
                            }
                        }
                        view.printPackage(model.viewShelf(shelf, shelve));
                    }
                    else if (choice == 'D')
                    {
                        boolean shelfSelected = false;
                        int shelf = 0;
                        while (!shelfSelected)
                        {
                            view.printReadShelfNumber();
                            shelf = readIntOption(1, 5) - 1;
                            if (shelf != -2)
                            {
                                shelfSelected = true;
                            }
                        }
                        boolean shelveSelected = false;
                        int shelve = 0;
                        while (!shelveSelected)
                        {
                            view.printReadShelveNumber();
                            shelve = readIntOption(1, 5) - 1;
                            if (shelve != -2)
                            {
                                shelfSelected = true;
                            }
                        }
                        if (!model.addToShelf(shelf, shelve))
                        {
                            view.printMessage(
                                "Either a package is already on that shelf or you have no processed packages to place.");
                        }
                    }
                    else if (choice == 'E')
                    {
                        boolean shelfSelected = false;
                        int shelf = 0;
                        while (!shelfSelected)
                        {
                            view.printReadShelfNumber();
                            shelf = readIntOption(1, 5) - 1;
                            if (shelf != -2)
                            {
                                shelfSelected = true;
                            }
                        }
                        boolean shelveSelected = false;
                        int shelve = 0;
                        while (!shelveSelected)
                        {
                            view.printReadShelveNumber();
                            shelve = readIntOption(1, 5) - 1;
                            if (shelve != -2)
                            {
                                shelfSelected = true;
                            }
                        }
                        if (!model.removeFromShelf(shelf, shelve))
                        {
                            view.printMessage(
                                "A package is not on that shelf.");
                        }
                    }
                    else if (choice == 'F')
                    {
                        view.printMessage("Score: " + model.calculateScore());
                        day = false;
                    }
                }
            }
            else if (choice == 'B')
            {
                view.printFinalScore(model.viewScores());
                view.printLeaveGame();
                playing = false;
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Initializes the input scanner, view, and model.
     */
    private void setUp()
    {
        scanner = new Scanner(System.in);
        view = new View();
        model = new Model();
    }


    // ----------------------------------------------------------
    /**
     * Reads one menu choice from standard input and validates that it is a
     * single letter within the allowed option range.
     *
     * @param numOptions
     *            how many letters (starting at A) are valid
     * @return the chosen letter in upper case, or '0' if the input was not a
     *             valid choice (an error message is displayed)
     */
    private char readMenuOption(int numOptions)
    {
        char selection = '0';
        String input = scanner.nextLine();

        if (input.trim().isEmpty())
        {
            view.printMenuError();
        }
        else
        {
            char[] inputArray = input.toCharArray();
            if (inputArray.length > 1)
            {
                view.printMenuError();
            }
            else
            {
                char inputChar = Character.toUpperCase(inputArray[0]);
                if ((int)inputChar - 65 < 0
                    || (int)inputChar - 65 > numOptions - 1)
                {
                    view.printMenuError();
                }
                else
                {
                    selection = inputChar;
                }
            }
        }
        return selection;
    }


    // ----------------------------------------------------------
    /**
     * Reads a single line of input and parses it as a whole number within the
     * specified range, displaying an error message if the input is invalid.
     *
     * @param min
     *            smallest valid number (inclusive)
     * @param max
     *            largest valid number (inclusive)
     * @return the valid integer entered, or -1 if the input was not a valid
     *             number or was out of range
     */
    public int readIntOption(int min, int max)
    {
        String input = scanner.nextLine().trim();
        if (input.equals(""))
        {
            view.printIntError();
        }
        else
        {
            try
            {
                int number = Integer.parseInt(input);
                if (number < min || number > max)
                {
                    view.printIntError();
                }
                else
                {
                    return number;
                }
            }
            catch (NumberFormatException e)
            {
                view.printIntError();
            }
        }
        return -1;
    }


    // ----------------------------------------------------------
    /**
     * Starts the program by creating a new Controller.
     *
     * @param args
     *            command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        new Controller();
    }
}

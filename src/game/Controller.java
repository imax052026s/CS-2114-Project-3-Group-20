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

    /** The stamps, in the same order as the letters in the stamp menu. */
    static final String[] STAMPS = { "Light Load", "Medium Load", "Heavy Load",
        "Small", "Medium", "Large", "Fragile", "Flammable", "Keep Dry",
        "Virginia Beach", "Chesapeake", "Arlington", "Richmond", "Norfolk",
        "Newport News", "Alexandria", "Hampton", "Suffolk", "Roanoke" };

    private static final int DAY_OPTIONS = 2;
    private static final int GAME_OPTIONS = 6;
    private static final int PACKAGE_OPTIONS = 7;
    private static final int STAMP_OPTIONS = STAMPS.length + 1;
    private static final char NO_CHOICE = '0';

    private Scanner scanner;
    private View view;
    private Model model;
    private boolean inputClosed;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a controller that reads from System.in.
     */
    public Controller()
    {
        this(new Model());
    }


    // ----------------------------------------------------------
    /**
     * Creates a controller that reads from System.in and uses the given model.
     *
     * @param model
     *            the model to update
     */
    Controller(Model model)
    {
        setUp(model);
    }


    // ~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Starts the program.
     *
     * @param args
     *            not used
     */
    public static void main(String[] args)
    {
        new Controller().run();
    }


    // ----------------------------------------------------------
    /**
     * Plays the game until the player leaves or the input runs out.
     */
    public void run()
    {
        view.printInstructions();
        boolean playing = true;
        while (playing && !inputClosed)
        {
            view.printDayMenu();
            char choice = readMenuOption(DAY_OPTIONS);
            if (choice == 'A')
            {
                playDay();
            }
            else if (choice == 'B')
            {
                view.printFinalScore(model.viewScores());
                view.printLeaveGame();
                playing = false;
            }
        }
    }


    // ~ Package-private and private methods ...................................

    private void setUp(Model theModel)
    {
        scanner = new Scanner(System.in);
        view = new View();
        model = theModel;
        inputClosed = false;
    }


    private void playDay()
    {
        model.startDay();
        boolean day = true;
        while (day && !inputClosed)
        {
            model.checkQueue();
            view.printGameMenu();
            char choice = readMenuOption(GAME_OPTIONS);
            if (choice == 'A')
            {
                processPackage();
            }
            else if (choice == 'B')
            {
                view.printShelves(model.viewShelves());
            }
            else if (choice == 'C')
            {
                viewShelf();
            }
            else if (choice == 'D')
            {
                placeOnShelf();
            }
            else if (choice == 'E')
            {
                removeFromShelf();
            }
            else if (choice == 'F')
            {
                view.printScore(model.calculateScore());
                day = false;
            }
        }
    }


    private void processPackage()
    {
        if (!model.startProcessing())
        {
            view.printMessage("There are no packages to process right now.");
            return;
        }
        boolean processing = true;
        while (processing && !inputClosed)
        {
            view.printPackageProcessMenu();
            char choice = readMenuOption(PACKAGE_OPTIONS);
            if (choice == 'A')
            {
                view.printWeight(model.weighPackage());
            }
            else if (choice == 'B')
            {
                view.printVolume(model.measurePackage());
            }
            else if (choice == 'C')
            {
                view.printContents(model.viewContentsOfPackage());
            }
            else if (choice == 'D')
            {
                view.printDestination(model.readDestinationOfPackage());
            }
            else if (choice == 'E')
            {
                addStamp();
            }
            else if (choice == 'F')
            {
                model.denyPackage();
                view.printMessage("Package denied.");
                processing = false;
            }
            else if (choice == 'G')
            {
                model.finishProcessing();
                view.printMessage("Package processed.");
                processing = false;
            }
        }
    }


    private void addStamp()
    {
        char choice = NO_CHOICE;
        while (choice == NO_CHOICE && !inputClosed)
        {
            view.printStampMenu();
            choice = readMenuOption(STAMP_OPTIONS);
        }
        int index = choice - 'A';
        if (choice == NO_CHOICE || index >= STAMPS.length)
        {
            return;
        }
        if (model.addStampToPackage(STAMPS[index]))
        {
            view.printStampAdded(STAMPS[index]);
        }
        else
        {
            view.printMessage("There is no room for any more stamps.");
        }
    }


    private void viewShelf()
    {
        int[] position = readShelfPosition();
        if (position != null)
        {
            view.printPackage(model.viewShelf(position[0], position[1]));
        }
    }


    private void placeOnShelf()
    {
        int[] position = readShelfPosition();
        if (position == null)
        {
            return;
        }
        if (model.addToShelf(position[0], position[1]))
        {
            view.printMessage("Package placed on the shelf.");
        }
        else if (model.getProcessedCount() == 0)
        {
            view.printMessage("You have no processed packages to place.");
        }
        else
        {
            view.printMessage("A package is already on that shelf slot.");
        }
    }


    private void removeFromShelf()
    {
        int[] position = readShelfPosition();
        if (position == null)
        {
            return;
        }
        if (model.removeFromShelf(position[0], position[1]))
        {
            view.printMessage("Package removed from the shelf.");
        }
        else
        {
            view.printMessage("There is no package on that shelf slot.");
        }
    }


    /**
     * Asks for a shelf and a slot.
     *
     * @return the zero-based shelf and slot, or null if the input ran out
     */
    private int[] readShelfPosition()
    {
        view.printReadShelfNumber();
        int shelf = readIntOption(1, Model.SHELF_ROWS);
        if (shelf < 0)
        {
            return null;
        }
        view.printReadShelveNumber();
        int slot = readIntOption(1, Model.SHELF_SLOTS);
        if (slot < 0)
        {
            return null;
        }
        return new int[] { shelf - 1, slot - 1 };
    }


    private String readLine()
    {
        if (!scanner.hasNextLine())
        {
            inputClosed = true;
            return null;
        }
        return scanner.nextLine();
    }


    /**
     * Reads one menu choice.
     *
     * @param numOptions
     *            how many letters (starting at A) are valid
     * @return the chosen letter in upper case, or '0' if the input was not a
     *         valid choice (an error is shown) or the input ran out
     */
    char readMenuOption(int numOptions)
    {
        String input = readLine();
        if (input == null)
        {
            return NO_CHOICE;
        }
        input = input.trim();
        if (input.length() != 1)
        {
            view.printMenuError();
            return NO_CHOICE;
        }
        char choice = Character.toUpperCase(input.charAt(0));
        int index = choice - 'A';
        if (index < 0 || index >= numOptions)
        {
            view.printMenuError();
            return NO_CHOICE;
        }
        return choice;
    }


    /**
     * Reads a whole number, asking again until it is in range.
     *
     * @param min
     *            smallest valid number
     * @param max
     *            largest valid number
     * @return the number, or -1 if the input ran out
     */
    int readIntOption(int min, int max)
    {
        while (true)
        {
            String input = readLine();
            if (input == null)
            {
                return -1;
            }
            try
            {
                int number = Integer.parseInt(input.trim());
                if (number >= min && number <= max)
                {
                    return number;
                }
            }
            catch (NumberFormatException e)
            {
                // Not a number; fall through to the error message.
            }
            view.printIntError();
        }
    }
}

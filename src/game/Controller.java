package game;

import java.util.Scanner;

public class Controller
{
    private Scanner scanner;
    private View view;
    private Model model;

    public Controller()
    {
        setUp();
        view.printInstructions();

        boolean playing = true;
        while (playing)
        {
            view.printDayMenu();
            view.printMisc(model.viewScores());
            char choice = readMenuOption(2);
            if ((int)choice - 65 == 0)
            {
                model.startDay();
                boolean day = true;
                while (day)
                {
                    model.checkQueue();
                    view.printGameMenu();
                    choice = readMenuOption(6);
                    if ((int)choice - 65 == 0)
                    {
                        model.startProcessing();
                        boolean processing = true;
                        while (processing)
                        {
                            view.printPackageProcessMenu();
                            choice = readMenuOption(7);
                            if ((int)choice - 65 == 0)
                            {
                                view.printPackage(model.weighPackage() + "");
                            }
                            else if ((int)choice - 65 == 1)
                            {
                                view.printPackage(model.measurePackage() + "");
                            }
                            else if ((int)choice - 65 == 2)
                            {
                                view.printPackage(
                                    model.viewContentsOfPackage());
                            }
                            else if ((int)choice - 65 == 3)
                            {
                                view.printPackage(
                                    model.readDestinationOfPackage());
                            }
                            else if ((int)choice - 65 == 4)
                            {
                                view.printStampMenu();
                                choice = readMenuOption(19);
                                boolean stampAdded = false;
                                while (!stampAdded)
                                {
                                    if ((int)choice - 65 == 0)
                                    {
                                        if (!model
                                            .addStampToPackage("Light Load"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 1)
                                    {
                                        if (!model
                                            .addStampToPackage("Medium Load"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 2)
                                    {
                                        if (!model
                                            .addStampToPackage("Heavy Load"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 3)
                                    {
                                        if (!model.addStampToPackage("Small"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 4)
                                    {
                                        if (!model.addStampToPackage("Medium"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 5)
                                    {
                                        if (!model.addStampToPackage("Large"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 6)
                                    {
                                        if (!model.addStampToPackage("Fragile"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 7)
                                    {
                                        if (!model
                                            .addStampToPackage("Flammable"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 8)
                                    {
                                        if (!model
                                            .addStampToPackage("Keep Dry"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 9)
                                    {
                                        if (!model.addStampToPackage(
                                            "Virginia Beach"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 10)
                                    {
                                        if (!model
                                            .addStampToPackage("Chesapeake"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 11)
                                    {
                                        if (!model
                                            .addStampToPackage("Arlington"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 12)
                                    {
                                        if (!model
                                            .addStampToPackage("Richmond"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 13)
                                    {
                                        if (!model.addStampToPackage("Norfolk"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 14)
                                    {
                                        if (!model
                                            .addStampToPackage("Newport News"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 15)
                                    {
                                        if (!model
                                            .addStampToPackage("Alexandria"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 16)
                                    {
                                        if (!model.addStampToPackage("Hampton"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 17)
                                    {
                                        if (!model.addStampToPackage("Suffolk"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                    else if ((int)choice - 65 == 18)
                                    {
                                        if (!model.addStampToPackage("Roanoke"))
                                        {
                                            view.printMisc(
                                                "There is no room for any more stamps.");
                                        }
                                        stampAdded = true;
                                    }
                                }
                            }
                            else if ((int)choice - 65 == 5)
                            {
                                model.denyPackage();
                                processing = false;
                            }
                            else if ((int)choice - 65 == 6)
                            {
                                model.finishProcessing();
                                processing = false;
                            }
                        }
                    }
                    else if ((int)choice - 65 == 1)
                    {
                        view.printShelves(model.viewShelves());
                    }
                    else if ((int)choice - 65 == 2)
                    {
                        view.printReadShelfNumber();
                        int shelf = readIntOption(1, 5) - 1;
                        view.printReadShelveNumber();
                        int shelve = readIntOption(1, 5) - 1;
                        view.printPackage(model.viewShelf(shelf, shelve));
                    }
                    else if ((int)choice - 65 == 3)
                    {
                        view.printReadShelfNumber();
                        int shelf = readIntOption(1, 5) - 1;
                        view.printReadShelveNumber();
                        int shelve = readIntOption(1, 5) - 1;
                        if (!model.addToShelf(shelf, shelve))
                        {
                            view.printMisc(
                                "A package is already on that shelf.");
                        }
                    }
                    else if ((int)choice - 65 == 4)
                    {
                        view.printReadShelfNumber();
                        int shelf = readIntOption(1, 5) - 1;
                        view.printReadShelveNumber();
                        int shelve = readIntOption(1, 5) - 1;
                        if (!model.removeFromShelf(shelf, shelve))
                        {
                            view.printMisc(
                                "Either a package is not on that shelf or you have no processed packages to place.");
                        }
                    }
                    else if ((int)choice - 65 == 5)
                    {
                        view.printMisc("Score: " + model.calculateScore());
                        day = false;
                    }
                }
            }
            else if ((int)choice - 65 == 1)
            {
                view.printLeaveGame();
            }
        }
    }


    private void setUp()
    {
        scanner = new Scanner(System.in);
        view = new View();
        model = new Model();
    }


    private char readMenuOption(int numOptions)
    {
        char selection = '0';
        String input = scanner.nextLine();

        if (input == "")
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
                if ((int)inputChar - 65 < 0 || (int)inputChar - 65 > numOptions)
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


    private int readIntOption(int min, int max)
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
                int num = Integer.parseInt(input);
                if (num < min || num > max)
                {
                    view.printIntError();
                }
                else
                {
                    return num;
                }
            }
            catch (NumberFormatException e)
            {
                view.printIntError();
            }
        }
        return -1;
    }


    public static void main(String arg[])
    {
        new Controller();
    }
}

package game;

public class View
{
    public View()
    {
    }


    public void printDayMenu()
    {
        System.out.println("What would you like to do?");
        System.out.println("A) Start New Day");
        System.out.println("B) Leave Game");
        System.out.print("Enter a choice from the menu: ");
    }


    public void printGameMenu()
    {
        System.out.println("What would you like to do?");
        System.out.println("A) Process a new package");
        System.out.println("B) View which shelves hold packages");
        System.out.println("C) View package on shelf");
        System.out.println("D) Place a package on a shelf");
        System.out.println("E) Remove a package from a shelf");
        System.out.println("F) End Day");
        System.out.print("Enter a choice from the menu: ");
    }


    public void printPackageProcessMenu()
    {

        System.out.println("What would you like to do?");
        System.out.println("A) Weight Package");
        System.out.println("B) Measure Package");
        System.out.println("C) View Contents of Package");
        System.out.println("D) View Destination of Package");
        System.out.println("E) Add Stamp");
        System.out.println("F) Deny Package");
        System.out.println("G) Finish Processing");
        System.out.print("Enter a choice from the menu: ");
    }


    public void printStampMenu()
    {
        System.out.println("What would you like to do?");
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
        System.out.print("Enter a choice from the menu: ");
    }


    public void printMenuError()
    {
        System.out.println(
            "The entered value is not valid. Please enter a single letter option from the menu.");
    }


    public void printIntError()
    {
        System.out.println(
            "The entered value is not valid. Please enter a single digit number in the specified range.");
    }


    public void printInstructions()
    {
        System.out.println(
            "In this game, you will process packages and ship them out across Virginia.");
        System.out.println(
            "You can recieve packages that need to be processed and view their properties to apply the appropriate stamp.");
        System.out.println(
            "One stamp should be for the weight, one should be for the size, and one should be for the destination.");
        System.out.println(
            "The weight stamp represents either a light, medium, or heavy load. A light load weighs under 10 lbs, a medium load weights under 20 lbs, and any load heavier than that is a heavy load");
        System.out.println(
            "The size stamp represents either a small, medium, or large package. A small package has a volume under 2000 in^3, a medium package has a volume under 5000 in^3, and any package larger than that is large.");
        System.out.println(
            "The destination stamp represents where the package must be shipped too. The name of the stamp is the same as the destination.");
        System.out.println(
            "Additionally, there will special stamps such as 'Fragile', 'Flammable', and 'Keep Dry' which will be applied based on the contents of the package.");
        System.out.println(
            "Some packages will also be too unsafe to ship. Do not worry about labeling these packages, just deny them from being processed.");
        System.out.println(
            "Finally, you will be scored when you end the day based on the first 30 packages you have processed. You will recieve a point for each correct stamp added to the package. You will lose a point for each incorrectly added stamp or stamp missed. If a unsafe package is processed, you will lose five points.");
        System.out.println(
            "If you are not yet ready for a package to be processed, you can add the package to a shelf.");
        System.out.println("Good luck!");
    }


    public void printLeaveGame()
    {
        System.out.println("Thanks for playing!");
    }


    public void printReadShelfNumber()
    {
        System.out.print("Enter the shelf number (1-5): ");
    }


    public void printReadShelveNumber()
    {
        System.out.print("Enter the shelve number (1-5): ");
    }


    public void printShelves(String shelves)
    {
        System.out.println(shelves);
    }


    public void printPackage(String p)
    {
        System.out.println(p);
    }


    public void printMisc(String m)
    {
        System.out.println(m);
    }
}

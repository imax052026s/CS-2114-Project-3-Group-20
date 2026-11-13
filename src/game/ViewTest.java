package game;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// -------------------------------------------------------------------------
/**
 * Simple test class for the View class.
 * 
 * @author mniac
 * @version Nov 12, 2026
 */
public class ViewTest
    extends student.TestCase
{

    // ----------------------------------------------------------

    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * Tests the View class
     * 
     * @param args
     *            args
     */
    public static void main(String[] args)
    {
        View view = new View();

        System.out.println("===== TEST 1: MENUS =====");
        view.printDayMenu();
        view.printGameMenu();
        view.printPackageProcessMenu();
        view.printStampMenu();

        System.out.println("\n===== TEST 2: INSTRUCTIONS =====");
        view.printInstructions();

        System.out.println("\n===== TEST 3: PACKAGE INFORMATION =====");
        view.printWeight(25);
        view.printVolume(120);
        view.printDestination("Blacksburg");
        view.printContents("Books, Glass Vase, Clothing");

        System.out.println("\n===== TEST 4: SCORES =====");
        view.printScore(15);
        view.printFinalScore("Day 1: 15\nDay 2: 22\nDay 3: 18");

        System.out.println("\n===== TEST 5: MESSAGES =====");
        view.printMenuError();
        view.printMessage("Package successfully processed!");

        System.out.println("\n===== TEST 6: STAMP ADD =====");
        view.printStampAdded("Fragile");
    }
}

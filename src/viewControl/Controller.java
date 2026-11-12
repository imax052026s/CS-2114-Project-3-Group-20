package viewControl;

import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 *  Controls user input and communication between
 * the Model and View.
 * 
 *  @author mniac
 *  @version Nov 12, 2026
 */
public class Controller
{
    //~ Fields ................................................................

    private Scanner scanner;
    private View view;
    //Eventually, a model field
    //~ Constructors ..........................................................

    public Controller()
    {
        scanner = new Scanner(System.in);
        view = new View();
        //Eventually a model initializer
    }
    // ----------------------------------------------------------
    /**
     * Starts the program.
     * @param args args
     */
    //~Public  Methods ........................................................

    public static void main(String[] args)
    {
        Controller controller = new Controller();
        controller.setUp();
    }
    
    void setUp()
    {
        view.printInstructions();
        
        boolean running = true;
        
        while (running)
        {
            view.printDayMenu();
            
            char choice = readMenuOption();
            
            if (choice == '1')
            {
                System.out.println("Starting a new day...");
                runDay();
            }
            else if (choice == '2')
            {
                System.out.println("Thanks for playing Precision Post!");
                running = false;
            }
            else
            {
                view.printMenuError();
            }
        }
        
        scanner.close();
    }
    
    private void runDay()
    {
        boolean dayRunning = true;
        
        while (dayRunning)
        {
            view.printGameMenu();
            
            char choice = readMenuOption();
            
            if (choice == '1')
            {
                System.out.println("Processing next package...");
                processPackage();
            }
            else if (choice == '2')
            {
                endDay();
                dayRunning = false;
            }
            else
            {
                view.printMenuError();
            }
        }
    }
    
    private void processPackage()
    {
        boolean processing = true;
        
        while (processing)
        {
            view.printPackageProcessMenu();
            
            char choice = readMenuOption();
            
            switch (choice)
            {
                case '1':
                    System.out.println("Getting package weight...");
                    break;
                
                case '2':
                    System.out.println("Getting package volume...");
                    break;
                    
                case '3':
                    System.out.println("Viewing package contents...");
                    break;
                    
                case '4':
                    System.out.println("Reading package destination...");
                    break;
                    
                case '5':
                    String stamp = addStamp();
                    
                    if (stamp != null)
                    {
                        //stamp goes to model.
                    }
                    break;
                    
                case '6':
                    System.out.println("Rejecting package...");
                    processing = false;
                    break; //for now
                    
                case '7':
                    System.out.println("Finished processing package.");
                    processing = false;
                    break; //for now
                    
                default:
                    view.printMenuError();
            }
        }
    }
    
    private String addStamp()
    {
        while (true)
        {
            view.printStampMenu();
    
            char choice = readMenuOption();
    
            switch (choice)
            {
                case '1':
                    return "Fragile";
    
                case '2':
                    return "Flammable";
    
                case '3':
                    return "Keep Dry";
    
                case '4':
                    return "Handle With Care";
    
                case '5':
                    return "Priority";
    
                default:
                    view.printMenuError();
                    break;
            }
        }
    }
    
    private char readMenuOption()
    {
        String input = scanner.nextLine().trim();
        
        if (input.length() == 1)
        {
            return input.charAt(0);
        }
        
        return '0';
    }
    
    int readInt()
    {
        while (true)
        {
        
            String input = scanner.nextLine().trim();
        
            try
            {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a valid number:");
            }
        }
    }
    
    private void endDay()
    {
        System.out.println("\n===== END OF DAY =====");
        System.out.println("Calculating your score...");
        
        //Retrive score from model
        //view.printScore(score);
    }
}

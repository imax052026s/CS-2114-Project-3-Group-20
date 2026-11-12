package viewControl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// -------------------------------------------------------------------------
/**
 *  Test the Controller class.
 * 
 *  @author mniac
 *  @version Nov 12, 2026
 */
class ControllerTest
{

    //~ Fields ................................................................

    //~ Constructors ..........................................................

    //~Public  Methods ........................................................

    @Test
    public void testExitGame()
    {
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains(
            "Thanks for playing Precision Post!"));
    }
    
    @Test
    public void testStartAndEndDay()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        String result = output.toString();

        assertTrue(result.contains("Starting a new day..."));
        assertTrue(result.contains("END OF DAY"));
        assertTrue(result.contains("Thanks for playing Precision Post!"));
    }
    
    @Test
    public void testFinishPackage()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        String result = output.toString();

        assertTrue(result.contains("Processing next package..."));
        assertTrue(result.contains("Finished processing package."));
        assertTrue(result.contains("END OF DAY"));
    }
    
    @Test
    public void testRejectPackage()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n6\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        String result = output.toString();

        assertTrue(result.contains("Rejecting package..."));
        assertTrue(result.contains("END OF DAY"));
    }
    
    @Test
    public void testAddFragileStamp()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n5\n1\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        String result = output.toString();

        assertTrue(result.contains("Fragile"));
        assertTrue(result.contains("Finished processing package."));
    }
    
    @Test
    public void testAddFlammableStamp()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n5\n2\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains("Flammable"));
    }
    
    @Test
    public void testAddKeepDryStamp()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n5\n3\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains("Keep Dry"));
    }
    
    @Test
    public void testAddHandleWithCareStamp()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n5\n4\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains("Handle With Care"));
    }
    
    @Test
    public void testAddPriorityStamp()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n5\n5\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains("Priority"));
    }
    
    @Test
    public void testInvalidDayMenuOption()
    {
        System.setIn(new ByteArrayInputStream(
            "9\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains(
            "Invalid option. Please try again."));
    }
    
    @Test
    public void testInvalidGameMenuOption()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n9\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains(
            "Invalid option. Please try again."));
    }
    
    @Test
    public void testInvalidPackageMenuOption()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n9\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains(
            "Invalid option. Please try again."));
    }
    
    @Test
    public void testInvalidStampOption()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n5\n9\n1\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        String result = output.toString();

        assertTrue(result.contains(
            "Invalid option. Please try again."));
        assertTrue(result.contains("Fragile"));
    }
    
    @Test
    public void testMultiplePackages()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n7\n1\n6\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        String result = output.toString();

        assertTrue(result.contains("Processing next package..."));
        assertTrue(result.contains("Finished processing package."));
        assertTrue(result.contains("Rejecting package..."));
        assertTrue(result.contains("END OF DAY"));
    }
    
    @Test
    public void testInvalidMenuInput()
    {
        System.setIn(new ByteArrayInputStream(
            "abc\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains(
            "Invalid option. Please try again."));
    }
    
    @Test
    public void testReadInt()
    {
        System.setIn(new ByteArrayInputStream("25\n".getBytes()));

        Controller controller = new Controller();

        assertEquals(25, controller.readInt());
    }
    
    @Test
    public void testReadInvalidInt()
    {
        System.setIn(new ByteArrayInputStream(
            "abc\n25\n".getBytes()));

        Controller controller = new Controller();

        assertEquals(25, controller.readInt());
    }
    
    @Test
    public void testPackageWeightOption()
    {
        System.setIn(new ByteArrayInputStream(
            "1\n1\n1\n7\n2\n2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller controller = new Controller();
        controller.setUp();

        assertTrue(output.toString().contains(
            "Getting package weight..."));
    }
    
    @Test
    public void testMain()
    {
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Controller.main(new String[0]);

        assertTrue(output.toString().contains(
            "Thanks for playing Precision Post!"));
    }
}

package game;

/**
 * Test class for Queue
 * 
 * @author Sarah Gage
 * @version 2026.11.11
 */
public class QueueTest
    extends student.TestCase
{
    public Queue<String> queue;

    // ----------------------------------------------------------
    /**
     * Sets up
     */
    public void setUp()
    {
        queue = new Queue<String>();
    }


    // ----------------------------------------------------------
    /**
     * test enqueue()
     */
    public void testEnqueue()
    {
        assertEquals(queue.size(), 0);
        assertTrue(queue.enqueue("test"));
        assertEquals(queue.size(), 1);

        Exception exception = null;
        try
        {
            queue.enqueue(null);
        }
        catch (IllegalArgumentException e)
        {
            exception = e;
        }
        assertNotNull(exception);
    }


    // ----------------------------------------------------------
    /**
     * test dequeue()
     */
    public void testDequeue()
    {
        assertEquals(queue.size(), 0);
        assertNull(queue.dequeue());
        queue.enqueue("test");
        assertEquals(queue.size(), 1);
        assertEquals(queue.dequeue(), "test");
        assertEquals(queue.size(), 0);
    }


    // ----------------------------------------------------------
    /**
     * test peek()
     */
    public void testPeek()
    {
        assertNull(queue.peek());
        queue.enqueue("test");
        assertEquals(queue.peek(), "test");
        assertEquals(queue.size(), 1);
    }


    // ----------------------------------------------------------
    /**
     * test isEmpty()
     */
    public void testIsEmpty()
    {
        assertTrue(queue.isEmpty());
        queue.enqueue("test");
        assertFalse(queue.isEmpty());
    }
}

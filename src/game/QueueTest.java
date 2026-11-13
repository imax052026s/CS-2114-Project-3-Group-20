package game;

// -------------------------------------------------------------------------
/**
 * Tests the Queue class.
 *
 * @author Group 20
 * @version 2026.11.12
 */
public class QueueTest
    extends student.TestCase
{
    private Queue<String> queue;

    // ----------------------------------------------------------
    /**
     * Creates an empty queue before each test.
     */
    public void setUp()
    {
        queue = new Queue<String>();
    }


    // ----------------------------------------------------------
    /**
     * A new queue is empty.
     */
    public void testConstructor()
    {
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
    }


    // ----------------------------------------------------------
    /**
     * Enqueue adds to the back and counts entries.
     */
    public void testEnqueue()
    {
        assertTrue(queue.enqueue("a"));
        assertEquals(1, queue.size());
        assertTrue(queue.enqueue("b"));
        assertEquals(2, queue.size());
        assertEquals("a", queue.peek());
    }


    // ----------------------------------------------------------
    /**
     * Enqueueing null returns false and changes nothing.
     */
    public void testEnqueueNull()
    {
        assertFalse(queue.enqueue(null));
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.enqueue("a");
        assertFalse(queue.enqueue(null));
        assertEquals(1, queue.size());
    }


    // ----------------------------------------------------------
    /**
     * Dequeue removes entries first in, first out.
     */
    public void testDequeue()
    {
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        assertEquals("a", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals("b", queue.dequeue());
        assertEquals("c", queue.dequeue());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Dequeueing an empty queue returns null.
     */
    public void testDequeueEmpty()
    {
        assertNull(queue.dequeue());
        assertEquals(0, queue.size());
        queue.enqueue("a");
        queue.dequeue();
        assertNull(queue.dequeue());
        assertEquals(0, queue.size());
    }


    // ----------------------------------------------------------
    /**
     * The queue still works after being emptied by dequeue.
     */
    public void testReuseAfterEmptying()
    {
        queue.enqueue("a");
        queue.dequeue();
        assertTrue(queue.enqueue("b"));
        assertEquals(1, queue.size());
        assertEquals("b", queue.peek());
        assertEquals("b", queue.dequeue());
    }


    // ----------------------------------------------------------
    /**
     * Peek returns the front entry without removing it.
     */
    public void testPeek()
    {
        assertNull(queue.peek());
        queue.enqueue("a");
        queue.enqueue("b");
        assertEquals("a", queue.peek());
        assertEquals("a", queue.peek());
        assertEquals(2, queue.size());
    }


    // ----------------------------------------------------------
    /**
     * isEmpty tracks whether entries exist.
     */
    public void testIsEmpty()
    {
        assertTrue(queue.isEmpty());
        queue.enqueue("a");
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Clear removes everything and the queue can be used again.
     */
    public void testClear()
    {
        queue.enqueue("a");
        queue.enqueue("b");
        queue.clear();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.dequeue());
        queue.enqueue("c");
        assertEquals("c", queue.dequeue());
    }


    // ----------------------------------------------------------
    /**
     * Many entries stay in order.
     */
    public void testManyEntries()
    {
        for (int i = 0; i < 1000; i++)
        {
            queue.enqueue("" + i);
        }
        assertEquals(1000, queue.size());
        for (int i = 0; i < 1000; i++)
        {
            assertEquals("" + i, queue.dequeue());
        }
        assertTrue(queue.isEmpty());
    }
}

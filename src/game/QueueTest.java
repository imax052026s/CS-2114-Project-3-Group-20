
package game;

import student.TestCase;

/**
 * Tests the Queue class, including normal FIFO operations,
 * priority processing, and invalid inputs.
 *
 * @author Group 20
 * @version 2026.11.19
 */
public class QueueTest extends TestCase
{
    private Queue<String> queue;

    /**
     * Creates an empty queue before each test.
     */
    public void setUp()
    {
        queue = new Queue<String>();
    }

    /**
     * Tests the constructor.
     */
    public void testConstructor()
    {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
        assertNull(queue.dequeue());
    }

    /**
     * Tests adding normal entries.
     */
    public void testEnqueue()
    {
        assertTrue(queue.enqueue("A"));
        assertTrue(queue.enqueue("B"));

        assertEquals(2, queue.size());
        assertEquals("A", queue.peek());
    }

    /**
     * Tests rejecting null entries.
     */
    public void testEnqueueNull()
    {
        assertFalse(queue.enqueue(null));
        assertTrue(queue.isEmpty());

        queue.enqueue("A");

        assertFalse(queue.enqueue(null));
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());
    }

    /**
     * Tests normal FIFO removal.
     */
    public void testDequeue()
    {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    /**
     * Tests removing from an empty queue.
     */
    public void testDequeueEmpty()
    {
        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
        assertEquals(0, queue.size());
    }

    /**
     * Tests reusing a queue after emptying it.
     */
    public void testReuseAfterEmptying()
    {
        queue.enqueue("A");
        assertEquals("A", queue.dequeue());

        assertTrue(queue.enqueue("B"));
        assertEquals("B", queue.peek());
        assertEquals("B", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests looking at the first entry.
     */
    public void testPeek()
    {
        assertNull(queue.peek());

        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.peek());
        assertEquals("A", queue.peek());
        assertEquals(2, queue.size());

        queue.dequeue();

        assertEquals("B", queue.peek());
    }

    /**
     * Tests the isEmpty method.
     */
    public void testIsEmpty()
    {
        assertTrue(queue.isEmpty());

        queue.enqueue("A");
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    /**
     * Tests clearing a queue.
     */
    public void testClear()
    {
        queue.enqueue("A");
        queue.enqueue("B");

        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
        assertNull(queue.dequeue());

        assertTrue(queue.enqueue("C"));
        assertEquals("C", queue.dequeue());
    }

    /**
     * Tests clearing an already empty queue.
     */
    public void testClearEmpty()
    {
        queue.clear();
        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    /**
     * Tests priority insertion into an empty queue.
     */
    public void testEnqueueFrontEmpty()
    {
        assertTrue(queue.enqueueFront("Priority"));

        assertEquals(1, queue.size());
        assertEquals("Priority", queue.peek());
        assertEquals("Priority", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests priority insertion into a nonempty queue.
     */
    public void testEnqueueFrontNonEmpty()
    {
        queue.enqueue("A");
        queue.enqueue("B");

        assertTrue(queue.enqueueFront("Priority"));

        assertEquals(3, queue.size());
        assertEquals("Priority", queue.dequeue());
        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests rejecting null priority entries.
     */
    public void testEnqueueFrontNull()
    {
        assertFalse(queue.enqueueFront(null));
        assertTrue(queue.isEmpty());

        queue.enqueue("A");

        assertFalse(queue.enqueueFront(null));
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());
    }

    /**
     * Tests multiple priority entries.
     */
    public void testMultiplePriorityEntries()
    {
        queue.enqueue("Normal");
        queue.enqueueFront("Priority1");
        queue.enqueueFront("Priority2");

        assertEquals(3, queue.size());

        assertEquals("Priority2", queue.dequeue());
        assertEquals("Priority1", queue.dequeue());
        assertEquals("Normal", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests mixed normal and priority operations.
     */
    public void testInterleavedEnqueueDequeue()
    {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.dequeue());

        queue.enqueue("C");
        queue.enqueueFront("Priority");

        assertEquals("Priority", queue.dequeue());
        assertEquals("B", queue.dequeue());

        queue.enqueue("D");

        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());

        assertNull(queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    /**
     * Tests priority insertion after emptying the queue.
     */
    public void testPriorityAfterEmptying()
    {
        queue.enqueue("A");
        queue.dequeue();

        assertTrue(queue.enqueueFront("Priority"));
        assertEquals("Priority", queue.peek());
        assertEquals("Priority", queue.dequeue());

        assertTrue(queue.enqueue("B"));
        assertEquals("B", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests priority insertion after clearing the queue.
     */
    public void testPriorityAfterClear()
    {
        queue.enqueue("A");
        queue.clear();

        queue.enqueueFront("Priority");
        queue.enqueue("B");

        assertEquals("Priority", queue.dequeue());
        assertEquals("B", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests a large number of entries.
     */
    public void testManyEntries()
    {
        for (int i = 0; i < 1000; i++)
        {
            assertTrue(queue.enqueue("Item" + i));
        }

        assertEquals(1000, queue.size());

        for (int i = 0; i < 1000; i++)
        {
            assertEquals("Item" + i, queue.dequeue());
        }

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
}

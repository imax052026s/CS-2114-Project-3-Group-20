
package game;

/**
 * A generic singly linked queue that supports normal FIFO
 * processing and priority insertion at the front.
 *
 * @param <T> the type of entries stored in the queue
 * @author Group 20
 * @version 2026.11.19
 */
public class Queue<T>
{
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int numberOfEntries;

    /**
     * Creates an empty queue.
     */
    public Queue()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    /**
     * Adds an entry to the back of the queue.
     *
     * @param newEntry the entry to add
     * @return true if successful, false if null
     */
    public boolean enqueue(T newEntry)
    {
        if (newEntry == null)
        {
            return false;
        }

        Node<T> newNode = new Node<T>(newEntry);

        if (isEmpty())
        {
            firstNode = newNode;
        }
        else
        {
            lastNode.next = newNode;
        }

        lastNode = newNode;
        numberOfEntries++;
        return true;
    }

    /**
     * Adds a priority entry to the front of the queue.
     * The next dequeue will remove this entry first.
     *
     * @param newEntry the priority entry
     * @return true if successful, false if null
     */
    public boolean enqueueFront(T newEntry)
    {
        if (newEntry == null)
        {
            return false;
        }

        Node<T> newNode = new Node<T>(newEntry);
        newNode.next = firstNode;
        firstNode = newNode;

        if (lastNode == null)
        {
            lastNode = newNode;
        }

        numberOfEntries++;
        return true;
    }

    /**
     * Removes and returns the front entry.
     *
     * @return the removed entry, or null if empty
     */
    public T dequeue()
    {
        if (isEmpty())
        {
            return null;
        }

        T frontData = firstNode.data;
        firstNode = firstNode.next;
        numberOfEntries--;

        if (firstNode == null)
        {
            lastNode = null;
        }

        return frontData;
    }

    /**
     * Returns the front entry without removing it.
     *
     * @return the front entry, or null if empty
     */
    public T peek()
    {
        if (isEmpty())
        {
            return null;
        }

        return firstNode.data;
    }

    /**
     * Returns the number of entries.
     *
     * @return queue size
     */
    public int size()
    {
        return numberOfEntries;
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }

    /**
     * Removes every entry from the queue.
     */
    public void clear()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    /**
     * Internal singly linked node.
     *
     * @param <E> node data type
     */
    private static class Node<E>
    {
        private E data;
        private Node<E> next;

        /**
         * Creates a node containing data.
         *
         * @param data the stored value
         */
        public Node(E data)
        {
            this.data = data;
            this.next = null;
        }
    }
}

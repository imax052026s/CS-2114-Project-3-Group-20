package game;

// -------------------------------------------------------------------------
/**
 * A generic first-in, first-out queue implemented as a singly linked chain.
 * New entries are added to the back and removed from the front.
 *
 * @param <T>
 *            the type of entries stored in the queue
 * @author Group 20
 * @version 2026.11.12
 */
public class Queue<T>
{
    // ~ Fields ................................................................

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int numberOfEntries;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates an empty queue.
     */
    public Queue()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }


    // ~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Adds a new entry to the back of this queue.
     *
     * @param newEntry
     *            the object to be added
     * @return true if the entry was added, false if newEntry is null
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


    // ----------------------------------------------------------
    /**
     * Removes and returns the entry at the front of this queue.
     *
     * @return the removed entry, or null if the queue is empty
     */
    public T dequeue()
    {
        if (firstNode == null)
        {
            return null;
        }
        T entry = firstNode.data;
        firstNode = firstNode.next;
        if (firstNode == null)
        {
            lastNode = null;
        }
        numberOfEntries--;
        return entry;
    }


    // ----------------------------------------------------------
    /**
     * Returns the entry at the front of this queue without removing it.
     *
     * @return the first entry, or null if the queue is empty
     */
    public T peek()
    {
        if (firstNode == null)
        {
            return null;
        }
        return firstNode.data;
    }


    // ----------------------------------------------------------
    /**
     * Returns the number of entries in this queue.
     *
     * @return the number of entries
     */
    public int size()
    {
        return numberOfEntries;
    }


    // ----------------------------------------------------------
    /**
     * Returns whether this queue has no entries.
     *
     * @return true if the queue is empty
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    // ----------------------------------------------------------
    /**
     * Removes every entry from this queue.
     */
    public void clear()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    // ~ Node class ............................................................

    // -------------------------------------------------------------------------
    /**
     * A single entry in the chain. Holds one piece of data and a link to the
     * next node.
     *
     * @param <E>
     *            the type of data held in the node
     */
    private static class Node<E>
    {
        private E data;
        private Node<E> next;

        // ----------------------------------------------------------
        /**
         * Creates a node with the given data and no next node.
         *
         * @param dataPortion
         *            the entry to store
         */
        private Node(E dataPortion)
        {
            this(dataPortion, null);
        }


        // ----------------------------------------------------------
        /**
         * Creates a node with the given data and next node.
         *
         * @param dataPortion
         *            the entry to store
         * @param nextNode
         *            the node that follows this one
         */
        private Node(E dataPortion, Node<E> nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }
    }
}

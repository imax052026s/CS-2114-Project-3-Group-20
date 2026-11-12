package game;

/**
 * A generic queue implemented as a LinkedChain.
 *
 * @param <T>
 *            the type of entries stored in the queue
 */
public class Queue<T>
{
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int numberOfEntries;

    /**
     * Creates an empty queue
     */
    public Queue()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }


    /**
     * Adds a new entry to the back of this queue.
     *
     * @param newEntry
     *            the object to be added; must not be null
     * @return true
     * @throws IllegalArgumentException
     *             if newEntry is null
     */
    public boolean enqueue(T newEntry)
    {
        if (newEntry == null)
        {
            throw new IllegalArgumentException("Cannot add null to chain");
        }
        Node<T> newNode = new Node<T>(lastNode, newEntry, null);
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
     * Removes and returns the entry from the front of this queue.
     *
     * @return the removed entry, or null if the queue is empty
     */
    public T dequeue()
    {
        if (firstNode != null)
        {
            T entry = firstNode.data;
            firstNode = firstNode.next;

            if (firstNode == null)
            {
                lastNode = null;
            }
            else
            {
                firstNode.prev = null;
            }
            numberOfEntries--;
            return entry;
        }
        return null;
    }


    /**
     * Returns the entry from the front of this queue without removing.
     * 
     * @return the first entry, or null if the queue is empty
     */
    public T peek()
    {
        if (firstNode != null)
        {
            return firstNode.data;
        }
        return null;
    }


    /**
     * Returns the size of the queue.
     * 
     * @return the number of entries in the queue
     */
    public int size()
    {
        return numberOfEntries;
    }


    /**
     * Returns whether or not the queue is empty.
     * 
     * @return true if the queue is empty
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    /**
     * Clears out the queue.
     */
    public void clear()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    /**
     * A node in the chain. Holds one entry and a link to the next node.
     *
     * @param <E>
     *            the type of data held in the node
     */
    private static class Node<E>
    {
        private E data;        // entry stored in this node
        private Node<E> next;  // link to the next node, or null
        private Node<E> prev;  // to the prior node or null

        /**
         * Creates a node with the given data and no next node.
         *
         * @param dataPortion
         *            the entry to store
         */
        private Node(E dataPortion)
        {
            this(null, dataPortion, null);
        }


        /**
         * Creates a node with the given data and next link.
         *
         * @param dataPortion
         *            the entry to store
         * @param nextNode
         *            the node that follows this one
         */
        private Node(Node<E> prevNode, E dataPortion, Node<E> nextNode)
        {
            prev = prevNode;
            data = dataPortion;
            next = nextNode;
        }
    }
}

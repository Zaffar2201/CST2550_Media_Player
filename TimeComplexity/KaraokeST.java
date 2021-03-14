

public class KaraokeST<Key, Value> {
    private static final int INITIAL_CAPACITY = 4;

    private int numOfPairs;        // number of key-value pairs
    private int numOfChains;        // number of chains
    private Node[] linkedListSymbolTable;   // array of linked-list symbol tables

    // node list data type
    private static class Node {
        private final Object key;
        private Object val;
        private Node next;

        public Node(Object key, Object val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public KaraokeST() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create an empty symbol table with {@code initialNumOfChains} chains.
     * @param initialNumOfChains the initial number of chains
     */
    public KaraokeST(int initialNumOfChains) {
        this.numOfChains = initialNumOfChains;
        linkedListSymbolTable = new Node[initialNumOfChains];
    }

    // resize the hash table to increase the given number of chains,
    // rehashing all of the keys
    @SuppressWarnings("unchecked")
    private void resize(int chains) {
        KaraokeST<Key, Value> temp = new KaraokeST<Key, Value>(chains);
        for (int i = 0; i < numOfChains; i++) {
            for (Node x = linkedListSymbolTable[i]; x != null; x = x.next) {
                temp.put((Key) x.key, (Value) x.val);
            }
        }

        this.numOfChains  = temp.numOfChains;
        this.numOfPairs  = temp.numOfPairs;
        this.linkedListSymbolTable = temp.linkedListSymbolTable;
    }

    // hash value between 0 and m-1
    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % numOfChains;
    }


    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  searchKey the key
     * @return {@code true} if this symbol table contains {@code searchKey};
     *         {@code false} otherwise
     */
    public boolean contains(Key searchKey) {

        return get(searchKey) != null;
    }

    /**
     * Returns the value associated with the specified key in this symbol table.
     *
     * @param  searchKey the key
     * @return the value associated with {@code searchKey} in the symbol table;
     *         {@code null} if no such value
     */
    @SuppressWarnings("unchecked")
    public Value get(Key searchKey) {

        int i = hash(searchKey);
        for (Node x = linkedListSymbolTable[i]; x != null; x = x.next) {
            if (searchKey.equals(x.key)) return (Value) x.val;
        }
        return null;
    }

    /**
     *Insert the new data into the hash table
     * Resize hash reached load factor
     * @param  key the key
     * @param  val the value
     */
    public void put(Key key, Value val) {

        // double table size if average length of list >= 10
        if (numOfPairs >= 10*numOfChains) resize(2*numOfChains);


        int hashIndex = hash(key);

        numOfPairs++;
        linkedListSymbolTable[hashIndex] = new Node(key, val, linkedListSymbolTable[hashIndex]);
    }


    /**
     * Returns all keys in the hash symbol table.
     *
     * @return all keys in the hash symbol table, as in iterable
     */
    @SuppressWarnings("unchecked")
    public Iterable<Key> keys()  {
        HashQueue<Key> hashQueue = new HashQueue<Key>();
        for (int index = 0; index < numOfChains; index++) {
            for (Node x = linkedListSymbolTable[index]; x != null; x = x.next) {
                hashQueue.enqueue((Key) x.key);
            }
        }
        return hashQueue;
    }




}



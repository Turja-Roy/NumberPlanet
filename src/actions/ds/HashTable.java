package actions.ds;

import actions.ds.ll.DLL;
import actions.ds.ll.Node;

public class HashTable {

    public static int TABLE_SIZE = 100; 
    @SuppressWarnings("unchecked")
    public static DLL<Integer>[] table = (DLL<Integer>[]) new DLL[TABLE_SIZE];
    public static int size = 0; // number of elements in the hash table

    public HashTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new DLL<>(); // initialize each linked list in the array
        }
    }
    public int hash(int key) {
        double A = (Math.sqrt(5) - 1) / 2 ; // Golden ratio
        return (int) (TABLE_SIZE * (key * A % 1)); // hash function
    }
    public void insert(int key) {
        int index = hash(key); // get the index for the key
        table[index].add(key); // add the key to the linked list at that index
        size++; // increment the size of the hash table
    }
    public boolean search(int key) {
        int index = hash(key); // get the index for the key
        Node<Integer> node = table[index].find(key); // find the key in the linked list at that index
        return node != null; // return true if found, false otherwise
    }
    public void remove(int key) {
        int index = hash(key); // get the index for the key
        table[index].remove(key); // remove the key from the linked list at that index
        size--; // decrement the size of the hash table
    }
    public void printTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            System.out.print("Index " + i + ": ");
            table[i].traverse(); // print the linked list at that index
        }
    }
    public int getSize() {
        return size; // return the size of the hash table
    }
}

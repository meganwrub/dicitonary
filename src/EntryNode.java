/**
 * Represents a single node in a singly linked list for dictionary entries
 * Stored entreis consist of a key-value pair containing a word and its definition, 
 * along with a pointer to the next entry
 */

package src;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

class EntryNode {
    String word;
    String definition;
    EntryNode next;

    public EntryNode(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.next = null;
    }

}


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


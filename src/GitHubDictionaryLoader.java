/**
 * Loads dictionary dataset entries from GitHub CSV/text URL
 * Parses lines containing word-definition pairs into a singly linked list in O(1) time 
 * for insertion 
 */

package src;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class GitHubDictionaryLoader implements DictionaryLoader {
    private final String rawURL;

    public GitHubDictionaryLoader(String rawURL) {
        this.rawURL = rawURL;
    }
/**
 * Downloads and parses dictionary data from the URL into a linked list
 * Supports standard delimited lines (Comma,tab,colon) as well as CSV formats
 * All parsed words are normalized to lower case
 */
    @Override
    public EntryNode loadData() {
        EntryNode head = null;

        try {
            URL url = URI.create(this.rawURL).toURL();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.length() > 0) {
                    String word = "";
                    String def = "";

                    // If the line is in CSV format: "word","definition" OR word,definition
                    if (line.startsWith("\"")) {
                        // Find the closing quote of the first column
                        int firstQuoteClose = line.indexOf("\"", 1);
                        if (firstQuoteClose != -1) {
                            word = line.substring(1, firstQuoteClose).trim();
                            
                            // Definition is everything after the comma following the first column
                            int nextComma = line.indexOf(',', firstQuoteClose);
                            if (nextComma != -1 && nextComma + 1 < line.length()) {
                                def = line.substring(nextComma + 1).trim();
                            } else {
                                def = "No definition available";
                            }
                        }
                    } else {
                        int splitIndex = line.indexOf('\t');
                        if (splitIndex == -1) splitIndex = line.indexOf(':');
                        if (splitIndex == -1) splitIndex = line.indexOf(',');

                        if (splitIndex != -1) {
                            word = line.substring(0, splitIndex).trim();
                            def = line.substring(splitIndex + 1).trim();
                        } else {
                            word = line;
                            def = "No definition available";
                        }
                    }

                    // Clean any remaining quotes from word and def
                    if (word.startsWith("\"")) word = word.substring(1);
                    if (word.endsWith("\"")) word = word.substring(0, word.length() - 1);
                    if (def.startsWith("\"")) def = def.substring(1);
                    if (def.endsWith("\"")) def = def.substring(0, def.length() - 1);

                    word = word.trim().toLowerCase();
                    def = def.trim();

                    if (word.length() > 0) {
                        EntryNode newNode = new EntryNode(word, def);
                        newNode.next = head;
                        head = newNode;
                    }
                }
            }
                  
            reader.close();
            
        }

        catch(Exception e) {
            System.err.println("Failed to load dictionary: " + e.getMessage());
        }
        return head;
    }


}
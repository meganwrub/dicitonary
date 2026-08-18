package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvDictionaryLoader implements DictionaryLoader {

    private final String filePath;

    public CsvDictionaryLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public EntryNode loadData() {
        EntryNode head = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while(line!=null) {
                line = line.trim();
                if(line.length()==0){
                    continue;
                }
                String word = "";
                String def = "";

                //check for commas or colons in CSV
                int splitIndex = line.indexOf(',');
                if(splitIndex ==-1) {
                    splitIndex = line.indexOf(':');
                }
                if(splitIndex != -1) {
                    word = line.substring(0, splitIndex).trim();
                    def = line.substring(splitIndex +1).trim();
                }
                else {
                    word = line;
                    def = "No definition provided";
                }
                if(word.length()>0) {
                    EntryNode newNode = new EntryNode(word,def);
                    newNode.next = head;
                    head = newNode;
                }
            }
            
        }
        catch (IOException e) {
                System.out.println("Error reading CSV Dictionary" + e.getMessage());
            }
        return head;
    }
}
/**
 * This class provides an interactive command line that allows users to
 * search for words
 * Add custom slang and or change the definition of exisiting word
 * insepct metrics such as word counter and tree height
 */
package src;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String githubRawUrl = "https://raw.githubusercontent.com/benjihillard/English-Dictionary-Database/main/english%20Dictionary.csv";

        System.out.println("=========================================");
        System.out.println("   CONNECTING TO GITHUB & DOWNLOADING   ");
        System.out.println("=========================================");
        System.out.println("Please wait a few seconds...");

        DictionaryLoader loader = new GitHubDictionaryLoader(githubRawUrl);
        DictionaryLogic dictionary = new DictionaryLogic(loader);

       
        System.out.printf("Total Words: %,d\n", dictionary.getTotalWords());
        System.out.printf("Tree Height: %d\n", dictionary.getTreeHeight());
        System.out.println("=========================================\n");
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choose an action:");
            System.out.println("  [1] Search for a Word");
            System.out.println("  [2] Add Slang");
            System.out.println("  [3] View Tree Stats");
            System.out.println("  [4] Exit");
            System.out.print("Enter choice (1-4): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1": {
                System.out.println("\nEnter a word to search: ");
                String query = scanner.nextLine().trim();

                AVLTree.SearchResult result = dictionary.searchWord(query);

                System.out.println("\n----------- Search Result -----------");
                if (result.found) {
                    System.out.println("Status     : FOUND");
                    System.out.println("Word       : " + query);
                    System.out.println("Definition : " + result.definition);
                } else {
                    System.out.println("Status     : NOT FOUND");
                    System.out.println("Word '" + query + "' does not exist in the dictionary.");
                }
                int n = dictionary.getTotalWords();
                double theoreticalMax = (n > 0) ? Math.ceil(1.44 * (Math.log(n) / Math.log(2))) : 1;

                System.out.println("--- O(log N) Complexity Proof ---");
                System.out.printf("Comparisons Made  : %d comparisons\n", result.comparisons);
                System.out.printf("Max Theoretical   : ~%.0f comparisons (1.44 * log2(%,d))\n", theoreticalMax, n);
                System.out.println("-------------------------------------\n");
                break;
                }
                case "2": {
                    System.out.print("\nEnter slang word: ");
                String slang = scanner.nextLine().trim();

                // Check if word exists before prompting for definition
                AVLTree.SearchResult existing = dictionary.searchWord(slang);
                boolean shouldInsert = true;

                if (existing.found) {
                    System.out.println("Current definition: " + existing.definition);
                    System.out.print("Word already defined in dictionary, would you like to create a new definition? (yes/no): ");
                    String answer = scanner.nextLine().trim().toLowerCase();

                    if (!answer.equals("yes") && !answer.equals("y")) {
                        shouldInsert = false;
                        System.out.println("Cancelled. Original definition retained.\n");
                    }
                }

                if (shouldInsert) {
                    System.out.print("Enter definition: ");
                    String def = scanner.nextLine().trim();

                    dictionary.addSlang(slang, def);

                    if (existing.found) {
                        System.out.printf("\n[SUCCESS] Updated definition for \"%s\"!\n\n", slang);
                    } else {
                        System.out.printf("\n[SUCCESS] Added \"%s\" to AVL Tree! Total words: %,d\n\n", slang, dictionary.getTotalWords());
                    }

                }
                break;
            }
            case "3": {
                System.out.println("\n----------- Tree Stats -----------");
                    System.out.printf("Total Words : %,d\n", dictionary.getTotalWords());
                    System.out.printf("Tree Height : %d\n", dictionary.getTreeHeight());
                    System.out.println("----------------------------------\n");
                    break;
            }
            case "4": {
                System.out.println("\nExiting the program. Goodbye");
                running = false;
                break;
            }
            default: {
                System.out.println("\nInvalid choice, please try again");
            }

            }
        }

        scanner.close();
    }
}
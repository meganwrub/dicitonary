/**
 * This class acts as a coordinator between data loader and AVL tree
 * 
 */
package src;
public class DictionaryLogic {
    private final AVLTree tree;
    private DictionaryLoader loader;

   /**
    * Constructs an instance with the specified loader
    * and automatically populates AVL tree with the loaded dataset
    * @param loader
    */
    public DictionaryLogic(DictionaryLoader loader) {
        this.tree= new AVLTree();
        this.loader = loader;
        loadDictionary();
    }

    /**
     * reolaces current data loader with new one
     * @param newLoader
     */
    public void swapLoader(DictionaryLoader newLoader) {
        this.loader = newLoader;
        loadDictionary();
    }

     /**
     * Iterates through linked list returned by the active dicitonary loader
     * and inserts every entry into the AVL tree
     */
    private void loadDictionary() {
        if(loader==null) {
            return;
        }
        EntryNode curr = loader.loadData();
        while(curr!=null) {
            tree.insert(curr.word,curr.definition);
            curr = curr.next;
        }
    }

    /**
     * Inserts a new word and defionition (or updates existing definition) into the tree
     * @param word
     * @param definition
     */
    public void addSlang(String word, String definition) {
        tree.insert(word, definition);
    }

    /**
     * searches dictionary for a specific word
     * @param word is the word to look up
     */
    public AVLTree.SearchResult searchWord(String word) {
        return tree.search(word);
    }

    public int getTotalWords() {
        return tree.getWordCount();
    }

    public int getTreeHeight() {
        return tree.getTreeHeight();
    }
}
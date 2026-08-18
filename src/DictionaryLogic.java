package src;
public class DictionaryLogic {
    private final AVLTree tree;
    private DictionaryLoader loader;

    public DictionaryLogic(DictionaryLoader loader) {
        this.tree= new AVLTree();
        this.loader = loader;
        loadDictionary();
    }

    public void swapLoader(DictionaryLoader newLoader) {
        this.loader = newLoader;
        loadDictionary();
    }

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

    public void addSlang(String word, String definition) {
        tree.insert(word, definition);
    }

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
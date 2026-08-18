package src;

public class AVLTree {

    public static class AVLNode {
        String word;
        String definition;
        int height;
        AVLNode left;
        AVLNode right;
    

    public  AVLNode(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.height = 1;
    }
}

public static class SearchResult {
    public final boolean found;
    public final String definition;
    public final int comparisons;

    public SearchResult(boolean found, String definition, int comparisons) {
        this.found = found;
        this.definition = definition;
        this.comparisons = comparisons;
    }
}

private AVLNode root;
private int wordCount = 0;

public int getWordCount() {
    return wordCount;
}

private int getHeight(AVLNode node) {
    int result = 0;
    if(node!=null) {
        result = node.height;
    }
        return result;
    
}

private int getBalance(AVLNode node) {
    int result = 0;
    if(node!=null) {
        result = getHeight(node.left) - getHeight(node.right);
    }
    return result;
}

public int max(int a, int b) {
    int result;
    if(a>b) {
        result = a;
    }
    else {
        result = b;
    }
    return result;
}

private AVLNode rotateRight(AVLNode y) {
if(y==null||y.left ==null) {
    return y;
}
AVLNode x = y.left;
AVLNode T2 = x.right;

x.right = y;
y.left = T2;

y.height = max(getHeight(y.left), getHeight(y.right)) +1;
x.height = max(getHeight(x.left), getHeight(x.right)) +1;

return x;
}

private AVLNode rotateLeft(AVLNode x) {
    if(x==null||x.right ==null) {
    return x;
}
AVLNode y = x.right;
AVLNode T2 = y.left;

y.left = x;
x.right = T2;

y.height = max(getHeight(y.left), getHeight(y.right)) +1;
x.height = max(getHeight(x.left), getHeight(x.right)) +1;

return y;
}

public void insert(String word, String definition) {
    root = insertRec(root, word.toLowerCase().trim(), definition);
}

private AVLNode insertRec(AVLNode node, String word, String definition) {
    if(node == null) {
        wordCount++;
        return new AVLNode(word, definition);
    }
    int cmp = word.compareTo(node.word);
     if(cmp >0) {
        node.right = insertRec(node.right, word, definition);
    }
    else if(cmp <0) {
        node.left = insertRec(node.left, word, definition);
    }
    else {
        node.definition = definition;
         return node;
    }
   node.height = 1+ max(getHeight(node.left), getHeight(node.right));
   int balance = getBalance(node);
    
   //left left case
   if(balance > 1 && getBalance(node.left) >=0) {
    return rotateRight(node);
   }
    
   //right right case
   if(balance < -1 && getBalance(node.right) <=0) {
    return rotateLeft(node);
   }

   //left right case
   if(balance > 1 && getBalance(node.left) <0) {
    node.left = rotateLeft(node.left);
    return rotateRight(node);
   }

   //right left case
   if(balance< -1 && getBalance(node.right) >0) {
    node.right = rotateRight(node.right);
    return rotateLeft(node);
   }

   return node;
}

public SearchResult search(String query) {
    String target = query.toLowerCase().trim();
    AVLNode curr = root;
    int steps = 0;

    while(curr!=null) {
        steps++;
        int cmp = target.compareTo(curr.word);
        if(cmp == 0) {
            return new SearchResult(true, curr.definition, steps);
        }
        else if(cmp <0) {
            curr = curr.left;
        }
        else {
            curr = curr.right;
        }
    }
    return new SearchResult(false, null, steps);
}
public int getTreeHeight() {
    return getHeight(root);
}

}



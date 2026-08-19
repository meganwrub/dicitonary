# AVL-tree-dictionary
A command-line English dictionary application implemented in Java. 
Stores data in a self-balancing AVL tree that guarentees O(log n)time 
complexity for all lookups and insertions

** Key features**
-Streams and parses remote dictionary datasets 
- Self-balancing AVL tree
-Displays actual number of comparisons made during a lookup alongside the theoretical maximum comparisons 
-Allows users to add new words to the dictionary or update current definitions for existing words
-Easy to swap data sources

Classes:
-Main: Handles user interaction through command line interface menu

-DictionaryLogic: Acts as the controller coordinating the loader and the tree

-DictionaryLoader: An interface that sets up the required method for loading dictionary data

-GitHubDictionaryLoader: Downloads remote dataset from GitHub and builds a singly linked list

-EntryNode: A simple node that holds a word and its definition and a link to the next word in the list

-AVLTree: The self-balancing binary search tree structure storing and balancing words

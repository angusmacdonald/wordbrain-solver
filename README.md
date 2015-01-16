## WordBrain Solver ##

This is a toy project I created for solving puzzles in the iOS game, 'WordBrain'.

### What is WordBrain? ###
WordBrain is a word search like puzzle, where you are presented with a grid of characters, and you must find the word or words contained within. It differs in two ways:
1. Characters do not necessarily need to be connected in a straight line. If a character can reach another character diagonally, they can be used to form a word.
2. Once you have found a word, the characters used to form this word are removed and the remaining characters 'drop down' to fill the void created by the removed characters. This means that for puzzles with multiple words, later words may not become accessible until the first word is found.
3. Once all words are found, all characters in the grid must have been used.
 
WordBrain provides you with the grid, and the length of each word. It's possible to get the right word, but with the wrong characters, meaning you are unable to get later words.

### How does this solve it? ###

My solution solves a grid by taking in a dictionary of english words, and analyzing a grid of characters, using the lengths of the words required to narrow the search space.
 
The solver performs an exhaustive search of the grid. It starts from each character in the grid and recursively builds all possible combinations of words starting from this character. If it finds a word of the correct length, it removes the characters in this word and starts searching for a word with the next desired length.

I have included two approaches to implementing the dictionary. There is a simple set implementation that adds all words to a `HashSet` and uses the `contains()` call to determine a given string is a word. There is also a Trie implementation which provides an additional `isPrefix()` call, that allows the solver to terminate some searches early (if there are no words that begin with the given prefix).  

### How to run the solver? ###

#### In Code ####

To run the code, you will need Java and Maven installed.

You can see examples of the solver being run in the `SolverTests` class.

First, to create the dictionary, you need to load it from disk into either the `SetDictionary` or the `TrieDictionary`. These are shown below:

	final SetDictionary setDictionary = new SetDictionary(wordSet);
	final TrieDictionary trieDictionary = TrieDictionary.createTrie(wordSet);
   
Then, using one of these dictionaries, initialize the solver class:

	WordGridSolver solver = new WordGridSolver(trieDictionary);
	
To solve a grid, you must provide the grid and a queue of the word lengths required:

	final List<LinkedList<String>> solutions = solver.findWords(grid, wordLengths);

This call returns a list of of lists, where the inner list is a set of words that make up a valid solution. This inner list is ordered, so the first entry in the list may be required before the next entry becomes available on the grid. 
   
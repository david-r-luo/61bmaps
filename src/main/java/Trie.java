import java.util.LinkedList;

/**
 * Created by David on 4/18/2016.
 */
public class Trie {
    private TrieNode root;
    private boolean hasNodes;

    public Trie() {
        root = new TrieNode();
        hasNodes = false;
    }

    public boolean hasNodes() {
        return hasNodes;
    }

    public void add(String s) {
        root.insert(s, s);
        if (!hasNodes) {
            hasNodes = true;
        }
    }

    public LinkedList wordList(String s) {
        TrieNode last = root;
        for (int i = 0; i < s.length(); i++) {
            last = last.nodeAt(s.charAt(i));
            if (last == null) {
                return new LinkedList();
            }
        }

        return last.getWords();
    }
}

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by David on 4/18/2016.
 */
public class TrieNode {
    char c;
    boolean isLeaf;
    HashMap<Character, TrieNode> children;
    String location = null;

    public TrieNode() {
        children = new HashMap<>();
        isLeaf = true;
    }


    public TrieNode(char ch) {
        children = new HashMap<>();
        isLeaf = true;
        this.c = ch;
    }

    public TrieNode nodeAt(char ch) {
        return children.get(ch);
    }

    public void insert(String s, String total) {
        if (s.length() == 0) {
            return;
        }
        isLeaf = false;

        char firstChar = s.charAt(0);
        if (children.get(firstChar) == null) {
            children.put(firstChar, new TrieNode(firstChar));
        }

        if (s.length() == 1) {
            children.get(firstChar).location = total;
        }

        if (s.length() > 1) {
            children.get(firstChar).insert(s.substring(1), total);
        }
    }


    public LinkedList getWords() {
        LinkedList wordList = new LinkedList();
        if (location != null) {
            wordList.add(location);
        }

        if (!isLeaf) {
            for (TrieNode t: children.values()) {
                wordList.addAll(t.getWords());
            }
        }
        return wordList;
    }


}

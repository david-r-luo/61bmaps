import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by David on 4/18/2016.
 */
public class TrieNode {
    char c;
    boolean isLeaf;
    HashMap<Character, TrieNode> children;
    boolean isEnd;
    String location = null;

    public TrieNode() {
        children = new HashMap<>();
        isLeaf = true;
        isEnd = false;
    }


    public TrieNode(char ch) {
        children = new HashMap<>();
        isLeaf = true;
        isEnd = false;
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
            children.get(firstChar).isEnd = true;
            children.get(firstChar).location = total;
        }

        if (s.length() > 1) {
            children.get(firstChar).insert(s.substring(1), total);
        }
    }


    public LinkedList getWords() {
        LinkedList wordList = new LinkedList();
        if (isEnd) {
            wordList.add(location);
        }

        if (!isLeaf) {
            children.values().stream().filter(t -> t != null).forEach(t -> {
                wordList.addAll(t.getWords());
            });
        }
        return wordList;
    }


}

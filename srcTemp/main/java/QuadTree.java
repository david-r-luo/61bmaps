/**
 * Created by David on 4/10/2016.
 */
public class QuadTree {
    private TreeNode root;
    QuadTree ul;
    QuadTree ur;
    QuadTree ll;
    QuadTree lr;

    public TreeNode root() {
        return root;
    }

    public QuadTree() {
        this.root = new TreeNode(-122.2998046875, 37.892195547244356,
                -122.2119140625, 37.82280243352756, "root");
        this.ul = new QuadTree(new TreeNode(-122.2998046875, 37.892195547244356,
                -122.255859375, 37.857498990385958, "1"));
        this.ur = new QuadTree(new TreeNode(-122.255859375, 37.892195547244356,
                -122.2119140625, 37.857498990385958, "2"));
        this.ll = new QuadTree(new TreeNode(-122.2998046875, 37.85749899038596,
                -122.255859375, 37.82280243352756, "3"));
        this.lr = new QuadTree(new TreeNode(-122.255859375, 37.85749899038596,
                -122.2119140625, 37.82280243352756, "4"));
    }

    public QuadTree(TreeNode root) {
        if (root.fileName().length() > 7) {
            return;
        }
        this.root = root;
        this.ul = new QuadTree(new TreeNode(this.root.ullon,
                this.root.ullat,
                (this.root.lrlon + this.root.ullon) / 2,
                (this.root.lrlat + this.root.ullat) / 2,
                this.root.fileName() + "1"));
        this.ur = new QuadTree(new TreeNode((this.root.lrlon + this.root.ullon) / 2,
                this.root.ullat,
                this.root.lrlon,
                (this.root.lrlat + this.root.ullat) / 2,
                this.root.fileName() + "2"));
        this.ll = new QuadTree(new TreeNode(this.root.ullon,
                (this.root.lrlat + this.root.ullat) / 2,
                (this.root.lrlon + this.root.ullon) / 2,
                this.root.lrlat,
                this.root.fileName() + "3"));
        this.lr = new QuadTree(new TreeNode((this.root.lrlon + this.root.ullon) / 2,
                (this.root.lrlat + this.root.ullat) / 2,
                this.root.lrlon,
                this.root.lrlat,
                this.root.fileName() + "4"));
    }
}

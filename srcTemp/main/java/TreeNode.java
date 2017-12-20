public class TreeNode {
    double ullon, ullat, lrlon, lrlat;

    @Override
    public String toString() {
        return "Node{" + ", fileName='" + fileName + '\'' + '}';
    }

    public String fileName() {
        return fileName;
    }

    private String fileName;
    Rectangle rectangle;

    public TreeNode(double ullon, double ullat, double lrlon, double lrlat, String file) {
        this.ullon = ullon;
        this.ullat = ullat;
        this.lrlon = lrlon;
        this.lrlat = lrlat;
        fileName = file;
        this.rectangle = new Rectangle(ullon, ullat, lrlon, lrlat);
    }
}

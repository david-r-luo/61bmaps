/**
 * Created by David on 4/13/2016.
 */
public class Rectangle {
    double ullon, ullat, lrlon, lrlat;

    public Rectangle(double ullon, double ullat, double lrlon, double lrlat) {
        this.ullon = ullon;
        this.ullat = ullat;
        this.lrlon = lrlon;
        this.lrlat = lrlat;
    }

    public boolean intersects(Rectangle r) {
        if ((this.ullon >= r.lrlon) || (this.lrlon <= r.ullon)
                || (this.ullat <= r.lrlat) || (this.lrlat >= r.ullat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rectangle{"
                + "ullon=" + ullon
                + ", ullat=" + ullat
                + ", lrlon=" + lrlon
                + ", lrlat=" + lrlat
                + '}';
    }
}

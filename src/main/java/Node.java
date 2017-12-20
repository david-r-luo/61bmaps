import java.util.HashSet;

/**
 * Created by David on 4/15/2016.
 */
public class Node implements Comparable {
    long id;
    double lon, lat;
    String name;
    Node prev;
    double distFromStart;
    double priority;
    HashSet<Node> connected = new HashSet<>();

    public Node(long id, double lonn, double latt) {
        this.id = id;
        this.lon = lonn;
        this.lat = latt;
    }

    public Node closerNode(Node other, double lonn, double latt) {
        double thisDistance = this.distanceTo(lonn, latt);
        double otherDistance = other.distanceTo(lonn, latt);
        if (thisDistance < otherDistance) {
            return this;
        }
        return other;
    }

    public double distanceTo(double lonn, double latt) {
        return Math.sqrt((Math.pow((this.lon - lonn), 2) + (Math.pow((this.lat - latt), 2))));
    }

    @Override
    public String toString() {
        return "Node{"
                + "id=" + id
                + ", lat=" + lat
                + ", lon=" + lon
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;

        if (id != node.id) {
            return false;
        }
        if (Double.compare(node.lat, lat) != 0) {
            return false;
        }
        return Double.compare(node.lon, lon) == 0;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Object other) {
        Node nother = (Node) other;
        if (this.priority < nother.priority) {
            return -1;
        } else if (this.priority > nother.priority) {
            return 1;
        } else {
            return 0;
        }
    }
}

package pathfinder;
import java.util.HashMap;

public class Node {
    
    // MAP DETAILS
    private final double longitude;
    private final double latitude;
    
    // CONNECTED WAYPOINTS
    private HashMap<String, Node> waypoints = new HashMap<>();

    // CONSTRUCTOR
    public Node(double _longitude, double _latitude) {
        this.longitude = _longitude;
        this.latitude = _latitude;
    }
    
    // ADD CHILD
    public void add_waypoint(String _name, Node _node) {
        this.waypoints.put(_name, _node);
    }
    
    // GETTERS
    public double longitude() { return this.longitude; } 
    public double latitude() { return this.latitude; } 
    public HashMap<String, Node> waypoints() { return this.waypoints; }
}


package pathfinder;
import java.util.ArrayList;

public class Node {
    
    // NODE DETAILS
    private final String name;
    private final double longitude;
    private final double latitude;
    
    // MOST RECENT FCOST
    private double cost;
    private Node previous;
    
    // CONNECTED WAYPOINTS
    private ArrayList<Node> waypoints = new ArrayList<>();

    // CONSTRUCTOR
    public Node(String _name, double _longitude, double _latitude) {
        this.name = _name;
        this.longitude = _longitude;
        this.latitude = _latitude;
    }
    
    // ADD CHILD
    public void add_waypoint(Node _node) { this.waypoints.add(_node); }
    
    // GETTERS
    public String get_name() { return this.name; } 
    public double get_longitude() { return this.longitude; } 
    public double get_latitude() { return this.latitude; } 
    public ArrayList<Node> get_waypoints() { return this.waypoints; }
    
    // COST GETTER/SETTER
    public double get_cost() { return this.cost; }
    public void set_cost(double _value) { this.cost = _value; }
    
    // NEXT GETTER/SETTER
    public Node get_previous() { return this.previous; }
    public void set_previous(Node _value) { this.previous = _value; }
}
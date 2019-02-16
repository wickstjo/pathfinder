package pathfinder;
import java.util.ArrayList;

public class Node {
    
    // NODE DETAILS
    private final String name;
    private final double longitude;
    private final double latitude;
    
    // CONNECTED NODES
    private final ArrayList<Node> children = new ArrayList<>();
    
    // LATEST COSTS & ITS PREDECESSOR
    private double fcost, gcost = Double.POSITIVE_INFINITY;
    private Node previous;

    // CONSTRUCTOR
    public Node(String _name, double _longitude, double _latitude) {
        this.name = _name;
        this.longitude = _longitude;
        this.latitude = _latitude;
    }
    
    // GENERIC SETTERS
    public void set_previous(Node node) { this.previous = node; }
    public void set_fcost(double cost) { this.fcost = cost; }
    public void set_gcost(double cost) { this.gcost = cost; }
    
    // GENERIC GETTERS
    public Node get_previous() { return this.previous; }
    public double get_fcost() { return this.fcost; }
    public double get_gcost() { return this.gcost; }
    public String get_name() { return this.name; }
    public double get_latitude() { return this.latitude; }
    public double get_longitude() { return this.longitude; }
    
    // GET ALL & ADD CHILDREN
    public ArrayList<Node> get_children() { return this.children; }
    public void add_child(Node node) { this.children.add(node); }
}
package pathfinder;
import java.util.ArrayList;

public class Node {
    
    // NODE DETAILS
    public final String name;
    public final double longitude;
    public final double latitude;
    
    public Node previous;
    
    // ROUTE COSTS
    public double fcost, gcost = Double.POSITIVE_INFINITY;
    
    // CONNECTED WAYPOINTS
    public ArrayList<Node> waypoints = new ArrayList<>();

    // CONSTRUCTOR
    public Node(String _name, double _longitude, double _latitude) {
        this.name = _name;
        this.longitude = _longitude;
        this.latitude = _latitude;
    }
}
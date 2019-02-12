package pathfinder;
import java.util.HashMap;

public class Backend {
    
    // DECLARE NODE CONTAINER
    HashMap<String, Node> nodes = new HashMap<>();
    
    // CONSTRUCTOR
    public Backend() { generate_data(); }
    
    // GENERATE DATASET
    private void generate_data() {
        
        // ADD PARENT NODES
        add_node("helsinki", 60.1640504, 24.7600896);
        add_node("tampere", 61.6277369, 23.5501169);
        add_node("turku", 60.4327477, 22.0853171);
        add_node("jyvaskyla", 62.1373432, 25.0954598);
        add_node("kuopio", 62.9950487, 26.556762);
        add_node("lahti", 60.9948736, 25.5747703);
        
        // ADD HELSINKI CHILDREN
        add_child("helsinki", "tampere");
        add_child("helsinki", "turku");
        add_child("helsinki", "lahti");
        
        // ADD TAMPERE CHILDREN
        add_child("tampere", "helsinki");
        add_child("tampere", "turku");
        add_child("tampere", "jyvaskyla");
        add_child("tampere", "lahti");
        
        // ADD TURKU CHILDREN
        add_child("turku", "helsinki");
        add_child("turku", "tampere");
        
        // ADD KUOPIO CHILDREN
        add_child("kuopio", "lahti");
        
        // ADD LAHTIS CHILDREN
        add_child("lahti", "helsinki");
        add_child("lahti", "tampere");
        add_child("lahti", "kuopio");
    }
    
    // ADD NODE
    private void add_node(String _name, double _longitude, double _latitude) {
        
        // CREATE NEW NODE INSTANCE
        Node node = new Node(_longitude, _latitude);
        
        // PUSH IT INTO THE CONTAINER
        this.nodes.put(_name, node);
    }
    
    // ADD A NODE CHILD
    private void add_child(String _parent, String _child) {
        
        // FETCH REQUESTED NODES
        Node parent_node = get_node(_parent);
        Node child_node = get_node(_child);
        
        // ADD THE NODE CHILD
        parent_node.add_waypoint(_child, child_node);
    }
    
    // GETTERS
    public Node get_node(String _name) { return nodes.get(_name); }
    public HashMap<String, Node> get_nodes() { return this.nodes; }
}
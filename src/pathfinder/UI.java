package pathfinder;
import java.util.HashMap;
import java.util.Scanner;

public class UI {
    
    // FETCH ASSIST MODULES
    private final Scanner scan = new Scanner(System.in);
    Backend backend;
    
    // CONSTRUCTOR
    public UI(Backend _backend) { this.backend = _backend; }
    
    // SHORTHAND FOR DEBUGGING
    public void log(Object content) { System.out.println(content); }
    
    // RENDER NODES & CHILDREN
    public void render_nodes() {
        
        // FETCH THE PARENT NODES
        HashMap<String, Node> parent_nodes = backend.get_nodes();
        
        // LOOP THROUGH THE PARENTS
        for (String parent_city : parent_nodes.keySet()) {
            
            // FIND THE PARENT NODE & PRINT THE CITY NAME
            Node parent_node = backend.get_node(parent_city);
            log(parent_city.toUpperCase());
            
            // FETCH THE CHILD NODES
            HashMap<String, Node> child_nodes = parent_node.waypoints();
            
            // LOOP THROUGH THE CHILDREN & PRINT ITS NAME
            for (String child_city : child_nodes.keySet()) {
                log("\u00A0\u00A0> " + child_city.toUpperCase());
            }
            
            // LOG AN EMPTY LINE FOR AESTHETICAL PURPOSES
            log("");
        }
    }
    
    // ASK FOR USER INPUT
    public void query() {
        
        String from = "turku";
        String to = "kuopio";
        
        log("FROM:\t" + from);
        log("TO:\t" + to + "\n");
        Route route = new Route(backend, from, to);
    }
}

package pathfinder;
import java.util.ArrayList;
import java.util.HashMap;

public class UI {
    
    // FETCH ASSIST MODULES
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
            ArrayList<Node> child_nodes = parent_node.get_waypoints();
            
            // LOOP THROUGH THE CHILDREN & PRINT ITS NAME
            for (Node child_node : child_nodes) {
                log("\u00A0\u00A0> " + child_node.get_name().toUpperCase());
            }
            
            // LOG AN EMPTY LINE FOR AESTHETICAL PURPOSES
            log("");
        }
    }
    
    // ASK FOR USER INPUT
    public void query() {
        
        // START & END NODES
        String from = "jyvaskyla";
        String to = "kuopio";
        
        // LOG THEM FOR CLARITY
        log("FROM:\t" + from);
        log("TO:\t" + to + "\n");
      
        // GENERATE A ROUTE & FETCH IT
        Route route = new Route(backend, from, to); 
        ArrayList<String> result = route.get_path();
        
        log(result);
    }
}

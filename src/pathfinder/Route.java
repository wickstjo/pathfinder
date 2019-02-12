package pathfinder;
import java.util.ArrayList;
import java.util.HashMap;

public class Route {
    
    // ALL NODES
    Backend backend;
    
    // ROUTE START/FINISH
    Node start;
    Node finish;
    
    // BLACKLISTED NODES
    ArrayList<Node> blacklist = new ArrayList();
    
    Node last_node = null;
    
    // CONSTRUCTOR
    public Route(Backend _backend, String _from, String _to) {
        
        // SET CLASS VARS
        this.backend = _backend;
        this.start = _backend.get_node(_from);
        this.finish = _backend.get_node(_to);
        
        // START THE ROUTING PROCESS
        find_next(this.start);
    }
    
    // SHORTHAND FOR DEBUGGING
    private void log(Object content) { System.out.println(content); }
    
    // START THE ROUTING PROCESS
    private void find_next(Node _node) {
        
        // IF THE REQUESTED NODE ISNT THE DESTINATION
        if (_node != this.finish) {
        
        // BLACKLIST THE NODE
        this.blacklist.add(_node);
        
        // FETCH CHILD NODES
        HashMap<String, Node> child_nodes = _node.waypoints();
        
        // KEEP TRACK OF THE LOWEST COST NODE
        String next_name = null;
        Node next_node = null;
        double next_cost = -1;
        
        // LOOP THROUGH THEM
        for (String child_city : child_nodes.keySet()) {
            
            // SHORTHAND TO TARGET NODE
            Node target = child_nodes.get(child_city);
            
            // IF THE NODE HASNT BEEN VISITED BEFORE
            if (blacklisted(target) == false) {
            
                // FIND COSTS
                double g_cost = distance(target, this.start);
                double h_cost = distance(target, this.finish);
                double f_cost = g_cost + h_cost;

                // LOG THE NAME & COST
                log(child_city + ": " + f_cost);

                // IF NEXT IS UNDEFINED
                if (next_node == null) {
                    next_name = child_city;
                    next_node = target;
                    next_cost = f_cost;
                }

                if (f_cost < next_cost) {
                    next_name = child_city;
                    next_node = target;
                    next_cost = f_cost;
                }
            }
        }

        if (next_node != null) {
            log("\n-- " + next_name + " is picked!\n");
            this.last_node = _node;
            find_next(next_node);
        } else {
            log("NOTHING FOUND, BACKTRACKING!\n");
            find_next(this.last_node);
        }
        
        // WHEN DESTINATION IS REACHED
        } else { log("DESTINATION WAS REACHED!"); }
    }
    
    // FIND KM DISTANCE BETWEEN TWO NODES
    private double distance(Node _from, Node _to) {
        
        // CONVERT LONGITUDES TO RADIANS
        double longitude_from = to_radian(_from.longitude());
        double longitude_to = to_radian(_to.longitude());
        
        // CONVERT LATITUDES TO RADIANS
        double latitude_from = to_radian(_from.latitude());
        double latitude_to = to_radian(_to.latitude());
        
        // FIND THE DIFFERENCE & CONVERT NEGATIVES INTO POSITIVES
        double longitude = longitude_from - longitude_to;
        double latitude = latitude_from - latitude_to;
        
        // PERFORM HAVERSINE FORMULA
        double a = Math.pow(Math.sin(latitude/2), 2) + Math.cos(latitude_to) * Math.cos(latitude_from) * Math.pow(Math.sin(longitude/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));    
        
        // MULTIPLY BY THE EARTH RADIUS & RETURN
        return 6367 * c;
    }
    
    // CONVERT COORDINATE TO RADIAN
    private double to_radian(double _value) { return _value * Math.PI / 180.0; }
    
    private boolean blacklisted(Node _target) { return this.blacklist.contains(_target); }
}

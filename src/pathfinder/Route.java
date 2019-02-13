package pathfinder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Route {
    
    // ROUTE START/FINISH
    Node start;
    Node finish;
    
    // INITIALIZE NODE QUEUE & BLACKLIST
    PriorityQueue<Node> queue = new PriorityQueue(new compare()); 
    ArrayList<Node> blacklist = new ArrayList();
    
    // CONSTRUCTOR
    public Route(Backend _backend, String _from, String _to) {
        
        // FIND & SET FIRST & LAST NODES
        this.start = _backend.get_node(_from);
        this.finish = _backend.get_node(_to);
        
        // ADD THE STARTING NODE TO THE QUEUE & FIND THE ROUTE
        queue.add(this.start);
        find_route();
    }
    
    // SHORTHAND FOR DEBUGGING
    private void log(Object content) { System.out.println(content); }
    
    // START THE ROUTING PROCESS
    private void find_route() {
        
        // KEEP TRACK OF LAST PARENT
        Node last = null;
        
        // LOOP UNTIL THE QUEUE IS EMPTY
        while (!queue.isEmpty() && last != this.finish) {
            
            // SHORTHAND FOR PARENT NODE
            Node parent_node = queue.poll();
            
            // REMOVE NODE FROM THE QUEUE, BLACKLIST IT AND SET IS AS LAST
            queue.remove(parent_node);
            this.blacklist.add(parent_node);
            last = parent_node;
            
            log(parent_node.get_name());

            // FETCH ITS CHILD NODES
            ArrayList<Node> child_nodes = parent_node.get_waypoints();

            // LOOP THROUGH
            for (Node target : child_nodes) {

                // IF THE NODE HASNT BEEN VISITED BEFORE
                if (blacklisted(target) == false) {

                    // FIND COSTS
                    double g_cost = distance(target, this.start);
                    double h_cost = distance(target, this.finish);
                    double f_cost = g_cost + h_cost;

                    // SET THE FCOST & ADD NODE TO THE QUEUE
                    target.set_cost(f_cost);
                    queue.add(target);
                }
            }
        }
    }
    
    // FIND KM DISTANCE BETWEEN TWO NODES
    private double distance(Node _from, Node _to) {
        
        // CONVERT LONGITUDES TO RADIANS
        double longitude_from = to_radian(_from.get_longitude());
        double longitude_to = to_radian(_to.get_longitude());
        
        // CONVERT LATITUDES TO RADIANS
        double latitude_from = to_radian(_from.get_latitude());
        double latitude_to = to_radian(_to.get_latitude());
        
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
    
    // SORT PRIORITY QUEUE
    class compare implements Comparator<Node> {

        // OVERRIDE THE DEFAULT COMPARE METHOD
        @Override public int compare(Node first, Node second) {
            
            // DEFAULT TO NOT MOVING
            Integer response = 0;
            
            // MOVE ELEMENT FORWARD
            if (first.get_cost() > second.get_cost()) {
                response = 1;
            
            // MOVE ELEMENT BACKWARD
            } else if (first.get_cost() < second.get_cost()) {
                response = -1;
            }
            
            return response;
        }
    }
}

package pathfinder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Route {
    
    // ROUTE START/FINISH
    Node beginning;
    Node ending;
    
    // TEMPLATE HASHMAP FOR ROUTING
    HashMap<Node, Double> template;
    
    // CONSTRUCTOR
    public Route(Backend _backend, String _from, String _to) {
        
        // FIND & SET FIRST & LAST NODES
        this.beginning = _backend.get_node(_from);
        this.ending = _backend.get_node(_to);
        
        // GENERATE TEMPLATE HASHMAP
        this.template = _backend.generate_template();
        
        // START THE PATHFINGINDER
        find_route();
    }
    
    // START THE ROUTING PROCESS
    private void find_route() {

        // INITIALIZE CORE COMPONENTS
        PriorityQueue<Node> queue = new PriorityQueue(new compare()); 
        ArrayList<Node> blacklist = new ArrayList();
        
        // INITIALIZE HASHMAPS
        HashMap<Node, Node> route = new HashMap();
        HashMap<Node, Double> g_costs = this.template;
        HashMap<Node, Double> f_costs = this.template;
        
        // SET THE COSTS FOR FIRST NODE
        g_costs.put(this.beginning, 0.0);
        f_costs.put(this.beginning, distance(this.beginning, this.ending));
        
        // ADD THE FIRST NODE TO THE QUEUE
        queue.add(this.beginning);
        
        // LOOP UNTIL THE QUEUE IS EMPTY
        while (!queue.isEmpty()) {
            
            // SELECT NODE WITH LOWEST FCOST, THEN REMOTE IT
            Node parent_target = queue.poll();
            
            // BREAK THE LOOP IF ITS THE GOAL
            if (parent_target == this.ending) {
                summary(route);
                break;
            }
            
            // BLACKLIST IT
            blacklist.add(parent_target);
            
            // FETCH ITS CHILD NODES
            ArrayList<Node> child_nodes = parent_target.get_waypoints();
            
            // LOOP THROUGH THEM
            for (Node child_target : child_nodes) {
                
                // CHECK IF THE CHILD IS BLACKLISTED
                if (!blacklist.contains(child_target)) {
                    
                    // CALCULATE THE TENTATIVE COST
                    double tentative_cost = g_costs.get(parent_target) + distance(parent_target, child_target);
                    
                    // IF THE CHILD ISNT ALREADY QUEUED
                    if (!queue.contains(child_target)) {
                        
                        // FIND & INJECT THE FCOST TO THE NODE
                        double f_cost = distance(child_target, this.beginning) + distance(child_target, this.ending);
                        child_target.set_cost(tentative_cost);
                        
                        log(f_cost);
                        log(tentative_cost);
                        
                        // ADD IT TO THE QUEUE
                        queue.add(child_target);
                        //log(child_target.get_name());
                        
                    // IF IT IS QUEUED
                    } else if (tentative_cost >= g_costs.get(child_target)) {
                        
                        // REPLACE THE OLD ROUTE VALUE
                        if (route.containsKey(child_target)) { route.replace(child_target, parent_target);
                        
                        // INJECT NEW
                        } else { route.put(child_target, parent_target); }
                        
                        // REPLACE OLD G/F VALUES
                        g_costs.replace(child_target, tentative_cost);
                        f_costs.replace(child_target, g_costs.get(child_target) + distance(child_target, this.ending));
                    }
                }
            }
        }
    }
    
    // CONVERT COORDINATE TO RADIAN
    private double to_radian(double _value) { return _value * Math.PI / 180.0; }
    
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
    
    // SHORTHAND FOR DEBUGGING
    private void log(Object content) { System.out.println(content); }
    
    // PRINT ROUTE SUMMARY
    private void summary(HashMap<Node, Node> route) {
  
//        Node target = this.beginning;
//        
//        while (target != this.ending) {
//            target = route.get(target);
//            log(target.get_name());
//        }
        
        for (Node node : route.keySet()) {
           log(node.get_name() + " => " + route.get(node).get_name());
        }
    }
    
    // PRIORITY QUEUE SORTER
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

package pathfinder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Route {
    
    // ROUTE START/FINISH
    Node beginning;
    Node end;
    
    // CONSTRUCTOR
    public Route(Backend _backend, String _from, String _to) {
        
        // FIND & SET FIRST & LAST NODES
        this.beginning = _backend.get_node(_from);
        this.end = _backend.get_node(_to);
        
        // START THE PATHFINGINDER
        find_route();
    }
    
    // SHORTHAND FOR DEBUGGING
    private void log(Object content) { System.out.println(content); }
    
    // START THE ROUTING PROCESS
    private void find_route() {

        // INITIALIZE THE NECESSARY COMPONENTS
        PriorityQueue<Node> queue = new PriorityQueue(new compare()); 
        ArrayList<Node> blacklist = new ArrayList();
        ArrayList<Node> route = new ArrayList();
        HashMap<Node, Double> g_costs = new HashMap<>();
        HashMap<Node, Double> f_costs = new HashMap<>();
        
        Double check;
        
        // SET THE COSTS FOR FIRST NODE
        g_costs.put(this.beginning, 0.0);
        f_costs.put(this.beginning, distance(this.beginning, this.end));
        
        // SET THE FIRST NODE INTO THE QUEUE
        queue.add(this.beginning);
        
        // LOOP UNTIL THE QUEUE IS EMPTY
        while (!queue.isEmpty()) {
            
            // SELECT NODE WITH LOWEST FCOST, THEN REMOTE IT
            Node parent_target = queue.poll();
            
            // BREAK THE LOOP IF ITS THE GOAL
            if (parent_target == this.end) {
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
                if (blacklist.contains(child_target) == false) {
                       
                    // CHECK FOR NULL
                    check = check(g_costs.get(parent_target));
                    
                    // CALCULATE THE TENTATIVE COST
                    double tentative_cost = check + distance(parent_target, child_target);
                    
                    // CHECK FOR NULL
                    check = check(g_costs.get(child_target));
                    
                    // IF THE CHILD ISNT ALREADY QUEUED
                    if (queue.contains(child_target) == false) {
                        
                        // FIND & INJECT THE FCOST TO THE NODE
                        double f_cost = distance(child_target, this.beginning) + distance(child_target, this.end);
                        child_target.set_cost(f_cost);
                        
                        // ADD IT TO THE QUEUE
                        queue.add(child_target);
                        
                    // IF IT IS QUEUED
                    } else if(tentative_cost >= check) {
                        
                        route.add(parent_target);
                        g_costs.replace(child_target, tentative_cost);
                        
                        // CHECK FOR NULL
                        check = check(g_costs.get(child_target));
                        
                        double foo = check + distance(child_target, this.end);
                        f_costs.put(child_target, foo);
                    }
                }
            }
        }
    }
    
    private Double check(Double foo) {
        
        Double value = foo;
        
        if (foo == null) {
            value = 10000000.00;
        }
        
        return value;
    }
    
    private void summary(ArrayList<Node> route) {
        for (Node node : route) {
            log(node.get_name());
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

package pathfinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Route {
    
    // ROUTE START/FINISH
    private final Node beginning;
    private final Node ending;
    
    // THE FINALIZED ROUTE RESULT
    private ArrayList<String> result = new ArrayList();
    
    // CONSTRUCTOR
    public Route(Backend _backend, String _from, String _to) {
        
        // FIND & SET FIRST & LAST NODES
        this.beginning = _backend.get_node(_from);
        this.ending = _backend.get_node(_to);
        
        // START THE PATHFINGINDER
        find_route();
    }
    
    // START THE ROUTING PROCESS
    private void find_route() {

        // INITIALIZE CORE COMPONENTS
        PriorityQueue<Node> queue = new PriorityQueue(new compare()); 
        ArrayList<Node> blacklist = new ArrayList();
        
        // REUSABLE COST VARS FOR READABILITY
        double gcost, hcost, tentative_g;
        
        // FIND THE COSTS FOR THE FIRST NODE
        gcost = distance(this.beginning, this.beginning);
        hcost = distance(this.beginning, this.ending);
        
        // SET THEM IN THE OBJECT
        this.beginning.set_gcost(gcost);
        this.beginning.set_fcost(gcost + hcost);
        
        // ADD THE FIRST NODE TO THE QUEUE
        queue.add(this.beginning);
        
        // LOOP UNTIL THE QUEUE IS EMPTY
        while (!queue.isEmpty()) {
            
            // SELECT, THEN REMOVE THE NODE WITH THE LOWEST FCOST IN THE QUEUE
            Node parent_target = queue.poll();
            
            // BREAK THE LOOP IF ITS THE GOAL
            if (parent_target == this.ending) {
                summary(parent_target);
                break;
            }
            
            // BLACKLIST THE PARENT TO PREVENT RE-EXPLORATION
            blacklist.add(parent_target);
            
            // LOOP THROUGH ITS CHILDREN
            for (Node child_target : parent_target.get_children()) {
                
                // CHECK IF THE CHILD IS BLACKLISTED
                if (!blacklist.contains(child_target)) {
                    
                    // FIND TENTATIVE G-COST & H-COST
                    tentative_g = parent_target.get_gcost() + distance(parent_target, child_target);
                    hcost = distance(child_target, this.ending);
                    
                    // IF THE CHILD ISNT ALREADY QUEUED
                    if (!queue.contains(child_target)) {
                        
                        // SET CHILD NODE COSTS
                        child_target.set_gcost(tentative_g);
                        child_target.set_fcost(tentative_g + hcost);
                        
                        // ADD TO THE QUEUE
                        queue.add(child_target);
                    }
                    
                    // IF THE TENTATIVE G-COST IS LOWER THAN THE EXISTING ONE
                    if (tentative_g <= child_target.get_gcost()) {
                        
                        // ADD THE PARENT AS ITS PREDICESSOR
                        child_target.set_previous(parent_target);
                        
                        // UPDATE CHILD NODE COSTS
                        child_target.set_gcost(tentative_g);
                        child_target.set_fcost(tentative_g + hcost);
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
    
    // PRINT ROUTE SUMMARY
    private void summary(Node current) {
        
        // CREATE A TEMP LIST & ADD THE FIRST ELEMENT
        ArrayList<String> temp = new ArrayList();
        temp.add(current.get_name());
        
        // WHILE THERE IS A PREDICESSOR
        while (current.get_previous() != null) {
            
            // ADD THE NODE & KEEP DELVING
            temp.add(current.get_previous().get_name());
            current = current.get_previous();
        }
        
        // REVERSE & SET IT
        Collections.reverse(temp);
        this.result = temp;
    }

    // GET THE ROUTE RESULT
    public ArrayList<String> get_result() { return this.result; }
    
    // PRIORITY QUEUE COMPARATOR
    class compare implements Comparator<Node> {

        // OVERRIDE THE DEFAULT COMPARE METHOD
        @Override public int compare(Node first, Node second) {
            
            // DEFAULT TO NOT MOVING
            Integer response = 0;
            
            // MOVE ELEMENT FORWARD
            if (first.get_fcost() > second.get_fcost()) {
                response = 1;
            
            // MOVE ELEMENT BACKWARD
            } else if (first.get_fcost() < second.get_fcost()) {
                response = -1;
            }
            
            return response;
        }
    }
}

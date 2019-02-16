package pathfinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Route {
    
    // ROUTE START/FINISH
    private final Node beginning;
    private final Node ending;
    
    // THE FINALIZED ROUTE
    private final ArrayList<String> finalized = new ArrayList();
    
    // COST SHORTHANDS
    double gcost, hcost, tentative_g;
    
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
        
        // INITIALIZE HASHMAPS
        HashMap<Node, Node> route = new HashMap();
        ArrayList<Node> testing = new ArrayList();
        
        // FIND THE COSTS
        gcost = distance(this.beginning, this.beginning);
        hcost = distance(this.beginning, this.ending);
        
        // SET THE THEM
        this.beginning.gcost = gcost;
        this.beginning.fcost = gcost + hcost;
        
        // ADD THE FIRST NODE TO THE QUEUE
        queue.add(this.beginning);
        
        // LOOP UNTIL THE QUEUE IS EMPTY
        while (!queue.isEmpty()) {
            
            // SELECT NODE WITH LOWEST FCOST, THEN REMOTE IT
            Node parent_target = queue.poll();
            
            // BREAK THE LOOP IF ITS THE GOAL
            if (parent_target == this.ending) {
                summary(parent_target);
                break;
            }
            
            // BLACKLIST IT
            blacklist.add(parent_target);
            
            // FETCH ITS CHILD NODES
            ArrayList<Node> child_nodes = parent_target.waypoints;
            
            // LOOP THROUGH THEM
            for (Node child_target : child_nodes) {
                
                // CHECK IF THE CHILD IS BLACKLISTED
                if (!blacklist.contains(child_target)) {
                    
                    // FIND TENTATIVE- & HCOST
                    tentative_g = parent_target.gcost + distance(parent_target, child_target);
                    hcost = distance(child_target, this.ending);
                    
                    // IF THE CHILD ISNT ALREADY QUEUED
                    if (!queue.contains(child_target)) {
                        
                        // SET CHILDS VALUES
                        child_target.gcost = tentative_g;
                        child_target.fcost = tentative_g + hcost;
                        
                        // ADD TO THE QUEUE
                        queue.add(child_target);
                        
                    // IF THE NEW FCOST IS HIGHER THAN THE PREVIOIS
                    } else if (tentative_g >= child_target.gcost)
                        continue;
                    
                    child_target.previous = parent_target;

                    // RECALIBRATE VALUES
                    child_target.gcost = tentative_g;
                    child_target.fcost = tentative_g + hcost;

                    // RECALIBRATE THE QUEUE
                    queue.remove(child_target);
                    queue.add(child_target);
                }
            }
        }
    }
    
    // CONVERT COORDINATE TO RADIAN
    private double to_radian(double _value) { return _value * Math.PI / 180.0; }
    
    // FIND KM DISTANCE BETWEEN TWO NODES
    private double distance(Node _from, Node _to) {
        
        // CONVERT LONGITUDES TO RADIANS
        double longitude_from = to_radian(_from.longitude);
        double longitude_to = to_radian(_to.longitude);
        
        // CONVERT LATITUDES TO RADIANS
        double latitude_from = to_radian(_from.latitude);
        double latitude_to = to_radian(_to.latitude);
        
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
        
        ArrayList<String> fin = new ArrayList();
        fin.add(current.name);
        
        while (current.previous != null) {
            fin.add(current.previous.name);
            current = current.previous;
        }
        
        Collections.reverse(fin);
        log(fin);
        
//        log("----");
//        
//        // ADD THE FIRST NODE TO THE FINALIZED LIST
//        finalized.add(this.beginning.get_name());
//           
//        // ADD THE GENERATED NODES
//        for (Node node : route.keySet()) {
//           finalized.add(node.get_name());
//        }
//        
//        // ADD THE LAST NODE
//        finalized.add(this.ending.get_name());
    }

    // GET THE PATH
    public ArrayList<String> get_path() { return this.finalized; }
    
    // PRIORITY QUEUE SORTER
    class compare implements Comparator<Node> {

        // OVERRIDE THE DEFAULT COMPARE METHOD
        @Override public int compare(Node first, Node second) {
            
            // DEFAULT TO NOT MOVING
            Integer response = 0;
            
            // MOVE ELEMENT FORWARD
            if (first.fcost > second.fcost) {
                response = 1;
            
            // MOVE ELEMENT BACKWARD
            } else if (first.fcost < second.fcost) {
                response = -1;
            }
            
            return response;
        }
    }
    
    // SHORTHAND FOR DEBUGGING
    public void log(Object content) { System.out.println(content); }
}

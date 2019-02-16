package pathfinder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UI {
    
    // BIND THE BACKEND MODULE
    Backend backend;
    
    // FETCH THE SCANNER MODULE
    private final Scanner scan = new Scanner(System.in);
    
    // CONSTRUCTOR
    public UI(Backend _backend) { this.backend = _backend; }
    
    // SHORTHANDS FOR LOGGING
    public void log(Object content) { System.out.println(content); }
    public void error(Object content) { log("\n" + "\u001B[31m" + content + "\u001B[0m" + "\n"); }
    
    // RENDER NODES & CHILDREN
    public void list_nodes() {
        
        // FETCH THE PARENT NODES
        HashMap<String, Node> parent_nodes = backend.get_nodes();
        
        // LOOP THROUGH THE PARENTS
        for (String parent_city : parent_nodes.keySet()) {
            
            // FIND THE PARENT NODE & PRINT THE CITY NAME
            Node parent_node = backend.get_node(parent_city);
            log(parent_city.toUpperCase());
            
            // FETCH THE CHILD NODES
            ArrayList<Node> child_nodes = parent_node.get_children();
            
            // LOOP THROUGH THE CHILDREN & PRINT ITS NAME
            for (Node child_node : child_nodes) {
                log("\u00A0\u00A0> " + child_node.get_name().toUpperCase());
            }
            
            // ADD AN EXTRA LINEBREAK AFTER EACH BLOCK
            log("");
        }
    }
    
    // ASK FOR USER INPUT
    public void query() {
        
        // ASK USER TO SPECIFY START & END POINTS
        String from = question("FROM?");
        String to = question("\nTO?");
        
        // LOG THEM FOR CLARITY
        log("\nFROM:\t" + from.toUpperCase());
        log("TO:\t" + to.toUpperCase() + "\n");
      
        // GENERATE A ROUTE & FETCH IT
        Route route = new Route(backend, from, to); 
        ArrayList<String> result = route.get_result();
        
        // LOOP OUT THE RESULTING ARRAYLIST
        for (int x = 0; x < result.size(); x++) {
            log((x + 1) + ". " + result.get(x).toUpperCase());
        }
    }
    
    // ASK A QUESTION
    private String question(String _question) {
        
        // ASK THE QUESTION & SAVE THE ANSWER
        System.out.print(_question + "\n\u00A0\u00A0> ");
        String answer = scan.next();
        
        // CHECK IF THE REQUESTED CITY EXISTS -- FORCE LOWERCASE
        boolean check = this.backend.get_nodes().containsKey(answer.toLowerCase());
        
        // IF IT DOESNT
        if (check == false) {
            
            // PROMPT ERROR & ASK AGAIN
            error("CITY NOT FOUND, TRY AGAIN!");
            answer = question(_question);
        }
        
        // OTHERWISE, RETURN THE ANSWER
        return answer;
    }
}

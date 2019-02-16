package pathfinder;

public class Pathfinder {
    public static void main(String[] args) {
        
        // START THE NECESSARY MODULES
        Backend backend = new Backend();
        UI ui = new UI(backend);
        
        // SHOW THE EXISTING NODES & ASK FOR A ROUTE TO SOLVE
        ui.list_nodes();
        ui.query();
    }
}

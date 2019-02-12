package pathfinder;

public class Pathfinder {
    public static void main(String[] args) {
        
        // START THE NECESSARY MODULES
        Backend backend = new Backend();
        UI ui = new UI(backend);
        
        // SHOW THE EXISTING NODES & CHILDREN
        ui.render_nodes();
        ui.query();
    }
}

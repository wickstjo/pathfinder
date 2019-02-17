package pathfinder;
import java.util.HashMap;

public class Backend {
    
    // DECLARE NODE CONTAINER
    HashMap<String, Node> nodes = new HashMap<>();
    
    // CONSTRUCTOR
    public Backend() { generate_data(); }
    
    // GENERATE DATASET
    private void generate_data() {

        // https://i.imgur.com/GidndrQ.png

        // PARENTS
        add_parent("helsinki", 60.1640504, 24.7600896);
        add_parent("tampere", 61.6277369, 23.5501169);
        add_parent("turku", 60.4327477, 22.0853171);
        add_parent("kuopio", 62.9950487, 26.556762);
        add_parent("kuopio", 62.9950487, 26.556762);
        add_parent("jyvaskyla", 62.2426, 25.7473);
        add_parent("oulu", 65.0121, 25.4651);
        add_parent("vaasa", 63.0951, 21.6165);
        add_parent("karjaa", 60.0714, 23.6619);
        add_parent("seinajoki", 62.7877, 22.8504);
        add_parent("kajaani", 64.2222, 27.7278);
        add_parent("joensuu", 62.6010, 29.7636);
        add_parent("parikkala", 61.5502, 29.5024);
        add_parent("ylivieska", 61.5502, 29.5024);
        add_parent("pori", 61.4851, 21.7974);
        add_parent("nurmes", 63.5419, 29.1396);
        add_parent("kouvola", 60.8679, 26.7042);

        // HELSINKI
        add_child("helsinki", "kouvola");
        add_child("helsinki", "jyvaskyla");
        add_child("helsinki", "tampere");
        add_child("helsinki", "karjaa");

        // TAMPERE
        add_child("tampere", "helsinki");
        add_child("tampere", "turku");
        add_child("tampere", "jyvaskyla");
        add_child("tampere", "pori");
        add_child("tampere", "seinajoki");

        // TURKU
        add_child("turku", "karjaa");
        add_child("turku", "tampere");
        add_child("turku", "pori");

        // KUOPIO
        add_child("kuopio", "jyvaskyla");
        add_child("kuopio", "joensuu");
        add_child("kuopio", "kajaani");
        add_child("kuopio", "ylivieska");

        // JYVÄSKYLÄ
        add_child("jyvaskyla", "ylivieska");
        add_child("jyvaskyla", "seinajoki");
        add_child("jyvaskyla", "tampere");
        add_child("jyvaskyla", "helsinki");
        add_child("jyvaskyla", "kouvola");
        add_child("jyvaskyla", "parikkala");
        add_child("jyvaskyla", "kuopio");

        // OULU
        add_child("oulu", "ylivieska");
        add_child("oulu", "kajaani");

        // VAASA
        add_child("vaasa", "ylivieska");
        add_child("vaasa", "pori");
        add_child("vaasa", "seinajoki");

        // KARJAA
        add_child("karjaa", "helsinki");
        add_child("karjaa", "turku");

        // SEINAJOKI
        add_child("seinajoki", "vaasa");
        add_child("seinajoki", "jyvaskyla");
        add_child("seinajoki", "tampere");

        // KAJAAI
        add_child("kajaani", "oulu");
        add_child("kajaani", "nurmes");
        add_child("kajaani", "kuopio");
        add_child("kajaani", "ylivieska");

        // JOENSUU
        add_child("joensuu", "nurmes");
        add_child("joensuu", "kuopio");
        add_child("joensuu", "parikkala");

        // PARIKKALA
        add_child("parikkala", "joensuu");
        add_child("parikkala", "jyvaskyla");
        add_child("parikkala", "kouvola");

        // YLIVIESKA
        add_child("ylivieska", "oulu");
        add_child("ylivieska", "kajaani");
        add_child("ylivieska", "kuopio");
        add_child("ylivieska", "jyvaskyla");
        add_child("ylivieska", "vaasa");

        // PORI
        add_child("pori", "vaasa");
        add_child("pori", "turku");
        add_child("pori", "tampere");

        // NURMES
        add_child("nurmes", "kajaani");
        add_child("nurmes", "joensuu");

        // KOUVOLA
        add_child("kouvola", "parikkala");
        add_child("kouvola", "jyvaskyla");
        add_child("kouvola", "helsinki");
    }
    
    // ADD PARENT NODE
    private void add_parent(String _name, double _longitude, double _latitude) {
        
        // CREATE NEW NODE INSTANCE & PUSH IT
        Node node = new Node(_name, _longitude, _latitude);
        this.nodes.put(_name, node);
    }
    
    // ADD CHILD NODE
    private void add_child(String _parent, String _child) {
        
        // FETCH REQUESTED NODES
        Node parent_node = get_node(_parent);
        Node child_node = get_node(_child);
        
        // ADD THE NODE CHILD
        parent_node.add_child(child_node);
    }
    
    // GETTERS
    public HashMap<String, Node> get_nodes() { return this.nodes; }
    public Node get_node(String _name) { return this.nodes.get(_name); }
}
import java.util.*;

public class Graph {

    private Map<String, Set<String>> adjacency;

    public Graph() {
        adjacency = new HashMap<String, Set<String>>();
    }

    public Map<String, Set<String>> getAdjacency() {
        return this.adjacency;
    }

    public void addEdge(String name1, String name2) {
        adjacency.get(name1).add(name2);
        adjacency.get(name2).add(name1);
    }

    /*
     * Check if actor is in the graph
     */
    public boolean actorInGraph(String name) {
        return adjacency.keySet().contains(name);
    }

    /*
     * Adds actor to the graph
     */
    public void addActorToGraph(String name) {
        adjacency.put(name.toLowerCase(), new HashSet());
    }


}

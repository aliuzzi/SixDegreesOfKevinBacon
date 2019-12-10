import java.util.*;

public class bfs {
    private Set<String> visited = new HashSet<String>();
    private boolean found = false;
    private Graph graph;
    private Queue<kvPair> queue = new LinkedList<kvPair>();
    private Map<String, String> connects = new HashMap<String, String>();
    private List<String> data = new LinkedList<String>();


    public bfs(Graph graph) {
        this.graph = graph;
    }

    /**
     * Calculate the shortest path between two actors and return a list of the shortest path
     */
    public List<String> shortestPath(String start, String toFind) {
        if (found) {
            return data;
        }
        if (start.equals(toFind)) {
            found = true;
            data = new LinkedList<>();
            data.add(start);
            return data;
        }
        return bfs(start, toFind);
    }

    /*
     * Utility function for shortest path
     */
    private List<String> bfs(String first, String second) {
        queue.add(new kvPair(first, graph.getAdjacencyMap().get(first)));
        visited.add(first);
        if (!found) {
            return null;
        }
        while (!queue.isEmpty()) {
            for (String current : queue.peek().getValue()) {
                if (visited.contains(current)) {
                    continue;
                }
                visited.add(current);
                queue.add(new kvPair(current, graph.getAdjacencyMap().get(current)));
                connects.put(current, queue.peek().getKey());

                if (current.equals(second)) {
                    found = true;
                    break;
                }
            }
            queue.poll();
        }

        List<String> dataOut = new LinkedList<>();
        String currentPerson = second;

        while (!currentPerson.equals(first)) {
            dataOut.add(0, currentPerson);
            currentPerson = connects.get(currentPerson);
        }
        dataOut.add(0, first);
        return dataOut;
    }
}

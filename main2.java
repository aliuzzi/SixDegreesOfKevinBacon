import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class main2 {


    /*
     * Graph and BFS Objects
     */
    private static Graph graph = new Graph();
    private static bfs bfsearch = new bfs(graph);


    /**
     * Read into the document and arrange graph
     */
    private static void fileParse(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        String fileName = args[0];
        BufferedReader reader;
        String line = "";
        String delim = ",\"";

        try {
            reader = new BufferedReader(new FileReader(fileName));
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.contains("[],[]")) {
                    line = reader.readLine();
                }

                String[] castInformation = line.split(delim);
                String actor = "";

                if (castInformation[1].contains("[{")) {
                    actor = castInformation[1].replace("\"\"", "\"");
                } else if (castInformation[2].contains("[{"))
                    actor = castInformation[2].replace("\"\"", "\"");
                JSONParser parser = new JSONParser();
                JSONArray castArray;
                try {
                    castArray = (JSONArray) parser.parse(actor);
                } catch (Exception e) {
                    line = reader.readLine();
                    castInformation = line.split(delim);
                    actor = "";
                    if (castInformation[1].contains("[{"))
                        actor = castInformation[1].replace("\"\"", "\"");
                    castArray = (JSONArray) parser.parse(actor);
                }
                for (Object object : castArray) {
                    JSONObject objectOne = (JSONObject) object;
                    String Actor1 = ((String) objectOne.get("name"));

                    if (!graph.actorInGraph(Actor1.toLowerCase())) {
                        graph.addActorToGraph(Actor1);
                    }

                    for (Object onjectTwo : castArray) {
                        JSONObject two = (JSONObject) onjectTwo;
                        String Actor2 = ((String) two.get("name"));

                        if (!graph.actorInGraph(Actor2.toLowerCase())) {
                            graph.addActorToGraph(Actor2);
                        }

                        graph.addEdge(Actor1.toLowerCase(), Actor2.toLowerCase());
                    }
                }
            }
            reader.close();
        }
        /* If file not found return error and 404 status code*/
        catch (FileNotFoundException e) {
            System.out.println("File not found! 404");
            System.exit(1);
        }
    }

    /*
     * Collects strings for first and last names and capitalizes first letter
     */
    private static String capitalize(String str) {
        StringBuilder sb = new StringBuilder();
        int start = 0;
        int space = str.indexOf(" ") + 1;
        for (int i = 0; i < str.length(); i++) {
            if (i == start || i == space) {
                sb.append((char) ((int) (str.charAt(i)) - 32));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    /*
     * Use scanner to get actors from user and return two actors for user
     */
    private static List<String> getActors(Scanner scanner) {
        // Actor 1 inputs
        System.out.print("Name of actor one: ");
        String actor1 = scanner.nextLine();
        while (!graph.actorInGraph(actor1.toLowerCase())) {
            System.out.println("Actor doesn't exist.");
            System.out.print("Name of actor one: ");
            actor1 = scanner.nextLine();
        }

        // Actor 2 inputs
        System.out.print("Name of actor 2: ");
        String actor2 = scanner.nextLine();
        while (!graph.actorInGraph(actor2.toLowerCase())) {
            System.out.println("Actor doesn't exist.");
            System.out.print("Name of actor 2: ");
            actor2 = scanner.nextLine();
        }

        List<String> actorsList = new LinkedList<String>();
        actorsList.add(actor1);
        actorsList.add(actor2);
        return actorsList;
    }


    private static void actorConnections() {
        List<String> actors = getActors(new Scanner(System.in));
        List<String> path = bfsearch.shortestPath(actors.get(0).toLowerCase(), actors.get(1).toLowerCase());
        System.out.print("Path between " + capitalize(path.get(0)) + " and " + capitalize(path.get(path.size() - 1)) + ": ");

        // Print out statement if there's only one actor
        if (path.size() == 1) {
            System.out.println(capitalize(path.get(0)));
        }

        // Print out total path
        else {
            for (int i = 0; i < path.size(); i++) {
                System.out.print(capitalize(path.get(i)));
                if (i != path.size() - 1)
                    System.out.print(" --> ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        // Check to make sure you inputted the command line arguments correctly
        if (args.length < 1) {
            System.out.println("Please include the path to the file as a command line argument");
        } else {
            fileParse(args);
            actorConnections();
        }
    }
}

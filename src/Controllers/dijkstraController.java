package Controllers;

import java.util.*;
import static Controllers.graphController.getEdges;
import static Controllers.graphController.getNodes;
import Models.Edge;
import Models.Node;

/**
 * Controlador para la ejecución de Dijkstra.
 *
 * @author krthr
 */
public class dijkstraController {

    /**
     * Lista de nodos.
     */
    private final List<Node> nodes;
    /**
     * Lista de aristas.
     */
    private final List<Edge> edges;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;

    /**
     * Crear uno nuevo controlador.
     */
    public dijkstraController() {
        this.nodes = new ArrayList<>(getNodes());
        this.edges = new ArrayList<>(getEdges());
    }

    /**
     * Ejecutar Dijkstra.
     *
     * @param source Nodo desde donde se partirá.
     */
    public void execute(Node source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    /**
     * Encontrar las distancias mínimas entre los nodos.
     *
     * @param node
     */
    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    /**
     * Obtener distancia entre nodos.
     *
     * @param node Nodo inicial.
     * @param target Nodo final.
     * @return El peso de una arista.
     */
    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("ERROR (Dijkstra): Ocurrió un error al tratar de obtener la distancia entre dos nodos.");
    }

    /**
     * Obtener los vecinos de un nodo.
     *
     * @param node
     * @return
     */
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    /**
     *
     * @param vertexes
     * @return
     */
    private Node getMinimum(Set<Node> vertexes) {
        Node minimum = null;
        for (Node vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    /**
     * Comprobar si un nodo ya fue seleccionado.
     *
     * @param vertex Nodo
     * @return Falso o verdadero
     */
    private boolean isSettled(Node vertex) {
        return settledNodes.contains(vertex);
    }

    /**
     *
     * @param destination
     * @return
     */
    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}

package es.uned.peda.algorithm;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.uned.peda.dataStructures.MinDijkstraHeap;
import es.uned.peda.dataStructures.Node;

// TODO: Auto-generated Javadoc
/**
 * The Class RobotDijkstraAlgorithm.
 */
public class RobotAlgorithm implements Runnable {

	/** The trace. */
	private final boolean trace;

	/** The initial node. */
	private final Node initialNode;

	/** The final node. */
	private final Node finalNode;

	/** The special. */
	private final MinDijkstraHeap heap;

	/** The output. */
	private final PrintStream output;

	/** The nodes. */
	private final List<Node> nodes;

	/**
	 * Instantiates a new robot dijkstra algorithm.
	 *
	 * @param nodes
	 *            the nodes
	 * @param initialNode
	 *            the initial node tag
	 * @param finalNode
	 *            the final node tag
	 * @param trace
	 *            the trace
	 * @param outputStream
	 *            the output stream
	 */
	public RobotAlgorithm(List<Node> nodes, Node initialNode, Node finalNode, boolean trace, PrintStream outputStream) {

		this.initialNode = initialNode;
		this.finalNode = finalNode;

		this.nodes = nodes;
		this.heap = new MinDijkstraHeap(nodes);

		this.trace = trace;
		this.output = outputStream;

	}

	/**
	 * Algorithm.
	 */
	@Override
	public void run() {

		Node selectedNode = heap.extractFirstNode();
		double nodeValue = selectedNode.getValue();
		int count = 0;

		if (trace) {
			printTrace(selectedNode, count);
		}

		while (!Double.isInfinite(nodeValue) && !selectedNode.equals(finalNode)) {

			count++;

			for (final Node adjacentNode : selectedNode.getAdjacentNodes()) {
				final double cost = adjacentNode.getCost();
				if (!adjacentNode.isVisited() && (adjacentNode.getValue() > nodeValue + cost)) {
					adjacentNode.setValue(nodeValue + cost);
					adjacentNode.setPreviousNode(selectedNode);
					heap.bubbleUpNode(adjacentNode);
				}
			}

			if (trace) {
				printTrace(selectedNode, count);
			}

			selectedNode = heap.extractFirstNode();
			nodeValue = selectedNode.getValue();

		}

		printResult(selectedNode, count);

	}

	/**
	 * Prints the trace.
	 *
	 * @param node
	 *            the node
	 * @param count
	 *            the count
	 */
	private void printTrace(Node node, int count) {

		if (count == 0) {
			output.format("Inicio del Algoritmo. Casilla origen: %d\n", node.getTag());
		} else {
			output.format("\nIteracion Nº %d  Casilla seleccionada: %d\n", count, node.getTag());
		}

		printUnselectedNodes();
		printDistances();
		printPrevious();

	}

	/**
	 * Prints the result.
	 *
	 * @param selectedNode
	 *            the selected node
	 * @param count
	 *            the count
	 */
	private void printResult(Node selectedNode, int count) {

		if (Double.isInfinite(selectedNode.getValue())) {
			output.format(
					"\nFinal del algoritmo. Iteraciones realizadas %d. No ha sido posible ancanzar la casilla de llegada. No existe camino.\n",
					count);

		} else if (selectedNode.equals(finalNode)) {

			final List<Node> path = getPathToFinalNode();

			output.format(
					"\nFinal del algoritmo. La casilla seleccionada (%d,%d) es la casilla de llegada. Iteraciones realizadas %d\n",
					selectedNode.getX(), selectedNode.getY(), count);

			output.format("\nCamino seguido: ");

			for (final Node node : path) {

				final String c;
				if (node.equals(initialNode)) {
					c = "R";
				} else {
					c = "";
				}

				output.format("%s[%d,%d],", c, node.getX(), node.getY());
			}

			output.format("S[%d,%d]", finalNode.getX(), finalNode.getY());

			output.format("  Energía consumida: %.1f\n", finalNode.getValue());
		}

	}

	private List<Node> getPathToFinalNode() {
		final List<Node> list = new ArrayList<>();

		Node node = finalNode;
		while (node != initialNode) {
			node = node.getPreviousNode();
			list.add(node);
		}

		Collections.reverse(list);

		return list;
	}

	/**
	 * Prints the distances array.
	 *
	 */
	private void printDistances() {
		output.format("   Distancia al nodo inicial [");
		for (final Node node : nodes) {
			final double v = node.getValue();
			if (Double.isInfinite(v)) {
				output.format("  Inf");
			} else {
				output.format("%5.1f", v);
			}
		}
		output.format("]\n");
	}

	/**
	 * Prints the previous node in the path.
	 *
	 */
	private void printPrevious() {
		output.format("   Nodos anteriores          [");

		for (final Node node : nodes) {
			output.format("%5d", node.getPreviousNode().getTag());
		}

		output.format("]\n");
	}

	private void printUnselectedNodes() {

		output.format("   Nodos pendientes          {");

		for (final Node node : nodes) {
			if (!node.isVisited()) {
				output.format(" %d", node.getTag());
			}
		}

		output.format("}\n");

	}

}

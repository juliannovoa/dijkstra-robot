package es.uned.peda.dataStructures;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProblemData.
 */
public class ProblemData {

	/** The nodes. */
	private final List<Node> nodes;

	/** The initial node. */
	private final Node initialNode;

	/** The final node. */
	private final Node finalNode;

	/** The number of columns. */
	private final int numberOfColumns;

	/** The number of rows. */
	private final int numberOfRows;

	/**
	 * Instantiates a new problem data.
	 *
	 * @param nodes
	 *            the nodes
	 * @param initialNode
	 *            the initial node
	 * @param finalNode
	 *            the final node
	 * @param numberOfRows
	 *            the number of rows
	 * @param numberOfColumns
	 *            the number of columns
	 */
	public ProblemData(List<Node> nodes, Node initialNode, Node finalNode, int numberOfRows, int numberOfColumns) {
		this.nodes = nodes;
		this.initialNode = initialNode;
		this.finalNode = finalNode;

		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;

		initialiseNodes();
	}

	/**
	 * Initialise nodes: set previous of each node to the initial node and set
	 * adjacent nodes.
	 *
	 */
	private void initialiseNodes() {
		for (int idx = 0; idx < nodes.size(); idx++) {
			final Node node = nodes.get(idx);
			node.setPreviousNode(initialNode);

			final int row = idx / numberOfColumns;
			final int column = idx % numberOfColumns;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (checkRange(row, column, i, j)) {
						final Node candidate = nodes.get((row + i) * numberOfColumns + (column + j));

						if (!Double.isInfinite(candidate.getCost())) {
							node.addAdjacentNode(candidate);
						}
					}
				}
			}
		}
	}

	/**
	 * Check if range is within bounds.
	 *
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return true, if successful
	 */
	private boolean checkRange(int row, int column, int i, int j) {
		row += i;
		column += j;
		return !(i == 0 && j == 0) && row >= 0 && row < numberOfRows && column >= 0 && column < numberOfColumns;
	}

	/**
	 * Gets the nodes.
	 *
	 * @return the nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Gets the initial node.
	 *
	 * @return the initial node
	 */
	public Node getInitialNode() {
		return initialNode;
	}

	/**
	 * Gets the final node.
	 *
	 * @return the final node
	 */
	public Node getFinalNode() {
		return finalNode;
	}

}

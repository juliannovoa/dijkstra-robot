package es.uned.peda.dataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class Node.
 *
 */
public final class Node {

	/** The tag. */
	private final int tag;

	/** The value. */
	private double value;

	/** The cost. */
	private final double cost;

	/** The visited. */
	private boolean visited;

	/** The previous node. */
	private Node previousNode;

	/** The adjacent nodes. */
	private final List<Node> adjacentNodes;

	/** The x. */
	private final int x;

	/** The y. */
	private final int y;

	/**
	 * Instantiates a new node.
	 *
	 * @param tag
	 *            the tag
	 * @param cost
	 *            the cost
	 * @param value
	 *            the value
	 */
	public Node(int tag, int x, int y, double cost, double value) {
		this.tag = tag;
		this.x = x;
		this.y = y;
		this.value = value;
		this.cost = cost;
		this.visited = false;
		this.previousNode = null;
		this.adjacentNodes = new ArrayList<>();
	}

	/**
	 * Instantiates a new node with infinite value.
	 *
	 * @param tag
	 *            the tag
	 * @param cost
	 *            the cost
	 */
	public Node(int tag, int x, int y, double cost) {
		this(tag, x, y, cost, Double.POSITIVE_INFINITY);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Checks if is visited.
	 *
	 * @return true, if is visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Sets the visited.
	 *
	 * @param visited
	 *            the new visited
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Gets the previous node.
	 *
	 * @return the previous node
	 */
	public Node getPreviousNode() {
		return previousNode;
	}

	/**
	 * Sets the previous node.
	 *
	 * @param previousNode
	 *            the new previous node
	 */
	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public int getTag() {
		return tag;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Gets the adjacent nodes.
	 *
	 * @return the adjacent nodes
	 */
	public List<Node> getAdjacentNodes() {
		return adjacentNodes;
	}

	/**
	 * Add the given adjacent node.
	 *
	 * @param node
	 *            the node
	 * @return the adjacent nodes
	 */
	public void addAdjacentNode(Node node) {
		adjacentNodes.add(node);
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(tag);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Node) {
			return this.getTag() == ((Node) other).getTag();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(Locale.ENGLISH, "Node[tag=%d,value=%.1f,cost=%.1f]", tag, value, cost);
	}

}

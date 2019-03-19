package es.uned.peda.dataStructures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class MinDijkstraHeap.
 */
public class MinDijkstraHeap {

	/** The heap is implemented on top of this array. */
	private final Node[] array;

	/** This map maps node tags to positions. */
	private final Map<Node, Integer> nodePositionMap;

	/** The max size. */
	private final int maxSize;

	/** The current size. */
	private int currentSize;

	/**
	 * Instantiates a new min dijkstra heap.
	 *
	 * @param elements
	 *            the list of nodes
	 */
	public MinDijkstraHeap(List<Node> elements) {

		this.maxSize = elements.size();
		this.currentSize = maxSize;
		this.array = new Node[maxSize + 1];
		this.nodePositionMap = new HashMap<>(maxSize);

		int position = 1;
		for (final Node element : elements) {
			nodePositionMap.put(element, position);
			array[position++] = element;
		}

		for (int i = maxSize / 2 + maxSize % 2; i >= 1; i--) {
			bubbleDown(i);
		}

	}

	/**
	 * Swap two nodes of the heap given their positions.
	 *
	 * @param firstPosition
	 *            the position in the heap of the first node
	 * @param secondPosition
	 *            the position in the heap of the second node
	 */
	private final void swapNodes(int firstPosition, int secondPosition) {

		if (firstPosition < 1 || firstPosition > maxSize || secondPosition < 1 || secondPosition > maxSize) {
			throw new IllegalArgumentException();
		}

		final Node firstNode = array[firstPosition];
		final Node secondNode = array[secondPosition];

		array[firstPosition] = secondNode;
		array[secondPosition] = firstNode;

		nodePositionMap.put(firstNode, secondPosition);
		nodePositionMap.put(secondNode, firstPosition);

	}

	/**
	 * Bubble down.
	 *
	 * @param position
	 *            the position
	 */
	private void bubbleDown(int position) {
		int currentPosition = position;

		while (currentPosition < currentSize) {
			final int leftChild = currentPosition * 2;
			if (leftChild > currentSize) {
				return;
			}
			final int rightChild = currentPosition * 2 + 1;
			if (rightChild <= currentSize) {
				if (array[currentPosition].getValue() > array[leftChild].getValue()
						&& array[rightChild].getValue() >= array[leftChild].getValue()) {

					swapNodes(currentPosition, leftChild);
					currentPosition = leftChild;

				} else if (array[currentPosition].getValue() > array[rightChild].getValue()
						&& array[rightChild].getValue() < array[leftChild].getValue()) {

					swapNodes(currentPosition, rightChild);
					currentPosition = rightChild;

				} else {
					return;
				}
			} else if (array[currentPosition].getValue() > array[leftChild].getValue()) {
				swapNodes(currentPosition, leftChild);
				currentPosition = leftChild;
			} else {
				return;
			}

		}
	}

	/**
	 * Bubble up.
	 *
	 * @param position
	 *            the position
	 */
	private void bubbleUp(int position) {
		int currentPosition = position;
		int fatherPosition = currentPosition / 2;

		while (currentPosition > 1 && array[fatherPosition].getValue() > array[currentPosition].getValue()) {
			swapNodes(fatherPosition, currentPosition);
			currentPosition = fatherPosition;
			fatherPosition = currentPosition / 2;
		}
	}

	/**
	 * Extract first node from heap and mark it as visited.
	 *
	 * @return the node
	 */
	public final Node extractFirstNode() {

		final Node node = array[1];

		node.setVisited(true);

		swapNodes(1, currentSize);
		currentSize--;
		bubbleDown(1);

		return node;

	}

	/**
	 * Bubble up to the correct position.
	 *
	 * @param node
	 *            the node
	 */
	public void bubbleUpNode(Node node) {
		bubbleUp(getPosition(node));
	}

	/**
	 * Gets the position.
	 *
	 * @param node
	 *            the node
	 * @return the position
	 */
	private int getPosition(Node node) {
		return nodePositionMap.get(node);
	}

	/**
	 * Gets the max size.
	 *
	 * @return the max size
	 */
	public int getMaxSize() {
		return maxSize;
	}

}

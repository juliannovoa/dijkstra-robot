package es.uned.peda.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import es.uned.peda.dataStructures.Node;
import es.uned.peda.dataStructures.ProblemData;

/**
 * The Class IOData.
 */
public class IOData {

	/** The number of rows. */
	private int numberOfRows;

	/** The number of columns. */
	private int numberOfColumns;

	/** The nodes. */
	private final List<Node> nodes = new ArrayList<>();

	/** The initial node. */
	private Node initialNode = null;

	/** The final node. */
	private Node finalNode = null;

	/** The obstacle found. */
	private boolean obstacleFound = false;

	/**
	 * Read input.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the problem data
	 */
	public ProblemData readInput(String inputFile) {

		final Path inputPath = Paths.get(inputFile);

		if (!Files.exists(inputPath)) {
			throw new IllegalArgumentException("Input file does not exist");
		}

		try (BufferedReader br = Files.newBufferedReader(inputPath)) {

			String line;
			int lineNumber = 1;

			numberOfRows = readInteger(br, lineNumber++);
			numberOfColumns = readInteger(br, lineNumber++);

			while ((line = br.readLine()) != null) {
				processLine(line, lineNumber++);
			}

			checkData(lineNumber);

			return new ProblemData(nodes, initialNode, finalNode, numberOfRows, numberOfColumns);

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void checkData(int lineNumber) {
		if (initialNode == null) {
			throw new IllegalArgumentException("Starting position not found");
		}
		if (finalNode == null) {
			throw new IllegalArgumentException("Final position not found");
		}
		if (!obstacleFound) {
			throw new IllegalArgumentException("Obstacle not found");
		}
		if (lineNumber - 3 != numberOfRows * numberOfColumns) {
			throw new IllegalArgumentException("Invalid number of lines in file");
		}
	}

	/**
	 * Process line.
	 *
	 * @param line
	 *            the line
	 * @param lineNumber
	 *            the line number
	 */
	private void processLine(String line, int lineNumber) {

		final int position = lineNumber - 2;
		final int x = (position - 1) / numberOfColumns + 1;
		final int y = (position - 1) % numberOfColumns + 1;
		line = line.trim().toUpperCase();

		switch (line) {
		case "R":
			checkR(lineNumber, position);
			initialNode = new Node(position, x, y, 0.0, 0.0);
			nodes.add(initialNode);
			break;
		case "S":
			checkS(lineNumber, position, x, y);
			finalNode = new Node(position, x, y, 0.0);
			nodes.add(finalNode);
			break;
		case "O":
			obstacleFound = true;
			nodes.add(new Node(position, x, y, Double.POSITIVE_INFINITY));
			break;
		default:
			nodes.add(new Node(position, x, y, readDouble(line, lineNumber)));
		}

	}

	private double readDouble(String line, int lineNumber) {
		final double n;
		if (line.equals("")) {
			throw new IllegalArgumentException("Error reading argument. Line " + lineNumber + " is empty");
		}
		try {
			n = Double.parseDouble(line);
		} catch (final Exception e) {
			throw new IllegalArgumentException("Error reading floating point number in line " + lineNumber);
		}
		if (n <= 0.0) {
			throw new IllegalArgumentException("Cost should be greater than zero (line " + lineNumber + ")");
		}
		return n;

	}

	/**
	 * Check S.
	 *
	 * @param lineNumber
	 *            the line number
	 * @param position
	 *            the position
	 * @param y
	 * @param x
	 */
	private void checkS(int lineNumber, int position, int x, int y) {

		if (finalNode != null) {
			throw new IllegalArgumentException("Two end positions specified (line " + lineNumber + ")");
		}

		if (!(y == 1 || y == numberOfColumns || x == 1 || x == numberOfRows)) {
			throw new IllegalArgumentException("The exit position should be on a border (line " + lineNumber + ")");
		}

	}

	/**
	 * Check R.
	 *
	 * @param lineNumber
	 *            the line number
	 * @param position
	 *            the position
	 */
	private void checkR(int lineNumber, int position) {

		if (initialNode != null) {
			throw new IllegalArgumentException("Two start positions specified (line " + lineNumber + ")");
		}

	}

	/**
	 * Read integer.
	 *
	 * @param br
	 *            the br
	 * @param lineNumber
	 *            the line number
	 * @return the int
	 */
	private int readInteger(BufferedReader br, int lineNumber) {
		final int n;
		try {
			n = Integer.parseInt(br.readLine().trim());
		} catch (final Exception e) {
			throw new RuntimeException("Error reading integer in line " + lineNumber);
		}
		if (n < 1) {
			throw new IllegalArgumentException(
					"Number of rows and columns must be greater than zero (line " + lineNumber + ")");
		}
		return n;

	}

	/**
	 * Gets the output.
	 *
	 * @param outputFile
	 *            the output file
	 * @return the output
	 */
	public PrintStream getOutput(String outputFile) {
		final Path outputPath = Paths.get(outputFile);

		if (Files.exists(outputPath)) {
			throw new IllegalArgumentException("Output file already exists");
		}

		try {
			return new PrintStream(Files.newOutputStream(outputPath));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}

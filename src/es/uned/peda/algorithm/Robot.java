package es.uned.peda.algorithm;

import java.io.PrintStream;
import java.util.List;

import es.uned.peda.dataStructures.Node;
import es.uned.peda.dataStructures.ProblemData;

// TODO: Auto-generated Javadoc
/**
 * The Class ShortestDistance.
 */
public class Robot {

	/** The input file. */
	private static String inputFile;

	/** The output file. */
	private static String outputFile = null;

	/** The trace. */
	private static boolean trace = false;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		try {

			parseArguments(args);

			final IOData iodata = new IOData();
			final ProblemData data = iodata.readInput(inputFile);
			final PrintStream output = outputFile == null ? System.out : iodata.getOutput(outputFile);

			final List<Node> nodes = data.getNodes();
			final Node initialNode = data.getInitialNode();
			final Node finalNode = data.getFinalNode();

			new RobotAlgorithm(nodes, initialNode, finalNode, trace, output).run();

		} catch (final Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

	}

	/**
	 * Parses the arguments.
	 *
	 * @param args
	 *            the args
	 */
	private static void parseArguments(String[] args) {
		// Check if user asks for help
		for (final String arg : args) {
			if (arg.equals("-h")) {
				help();
				System.exit(0);
			}
		}

		// Otherwise, run the algorithm
		switch (args.length) {
		case 1:
			if (args[0].equals("-t")) {
				error();
				System.exit(-1);
			}
			inputFile = args[0];
			break;
		case 2:
			if (args[0].equals("-t")) {
				trace = true;
				inputFile = args[1];
			} else {
				inputFile = args[0];
				outputFile = args[1];
			}
			break;
		case 3:
			if (!args[0].equals("-t")) {
				error();
				System.exit(-1);
			}
			trace = true;
			inputFile = args[1];
			outputFile = args[2];
			break;
		default:
			error();
			System.exit(-1);
		}
	}

	/**
	 * Error.
	 */
	public static void error() {
		System.err.println("Invalid arguments");
		help();
	}

	/**
	 * Help.
	 */
	public static void help() {
		System.out.println("Sintaxis:\n");
		System.out.println("robot [-t]  [-h]  [fichero_entrada]  [fichero_salida]\n");
		System.out.println("-t                Traza la aplicacion del algoritmo a los datos\n");
		System.out.println("-h                Muestra esta ayuda\n");
		System.out.println("fichero_entrada   Nombre del fichero de entrada. Si no existe dara error\n");
		System.out.println("fichero_salida    Nombre del fichero de salida que se creara para almacenar");
		System.out.println("                  la salida. Si falta este argumento se muestra el resultado");
		System.out.println("                  por la salida estandar\n");
	}

}

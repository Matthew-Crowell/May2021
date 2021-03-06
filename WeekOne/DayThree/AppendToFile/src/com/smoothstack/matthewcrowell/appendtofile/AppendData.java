package com.smoothstack.matthewcrowell.appendtofile;

/* imports for appendToFile */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Class to append text to an existing file
 * called outFile.txt.
 *
 * @author matthew.crowell
 */
public class AppendData {

	/**
	 * Handles command line arguments and coordinates appending data to file.
	 *
	 * @param args String[] of command line arguments to be appended to file
	 */
	public static void main(String[] args) {
		AppendData application = new AppendData();
		StringBuilder data = new StringBuilder();

		switch(args.length > 0 ? args[0] : "--help")
		{
			case("--help"):
			case("-h"):
				application.printHelp();
				break;
			case("--file"):
			case("-f"):
				data.append(application.readFileWithNIO(args[1]));
				application.appendToFileWithNIO(data.toString());
				break;
			default:
				data.append(application.convertArgsToStringBuilder(args));
				application.appendToFile(data.toString());
				break;
		}
	}

	/**
	 * Appends data to file via buffered writer.
	 *
	 * @param data String to append to file
	 */
	private void appendToFile(String data) {
		try (BufferedWriter outFile = new BufferedWriter(new FileWriter("outFile.txt", true))) {
			outFile.write(data);
		} catch (IOException | RuntimeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Appends data to file with NIO
	 *
	 * @param data String of data to append
	 */
	private void appendToFileWithNIO(String data) {
		try {
			Files.write(Paths.get("outFile.txt"), data.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Convert command line arguments into a StringBuilder object.
	 *
	 * @param args String[] of command line arguments
	 * @return StringBuilder made from args
	 */
	private StringBuilder convertArgsToStringBuilder(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i == args.length - 1) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}
		return sb;
	}

	/**
	 * Reads the bytes from a file and returns a string object.
	 *
	 * @param filename String representation of a filename
	 * @return String created from bytes read from file
	 */
	private String readFileWithNIO(String filename) {
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(filename)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result == null ? "" : result;
	}

	/**
	 * Prints help information to terminal on standard output.
	 */
	private void printHelp(){
		System.out.println("Application to write data to a file from the command line or another file.\n" +
				"Usage:\n\tjava com.smoothstack.matthewcrowell.appendtofile.AppendData <text>\n" +
				"\tjava com.smoothstack.matthewcrowell.appendtofile.AppendData <--file | -f> <path/to/file>\n");
	}
}

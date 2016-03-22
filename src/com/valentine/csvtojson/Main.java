package com.valentine.csvtojson;

import java.io.*;
import java.nio.file.*;

import com.valentine.csvtojson.csv.*;
import com.valentine.csvtojson.json.*;

public abstract class Main
{
	public static void main(String[] _args)
	{
		Interface.console.println("Welcome to CSV to JSON converter");
		Interface.console.println("by Leonid Romanovky");
		Interface.console.stroke();

		while (true)
		{
			int filesProcessed = 0;
			for (File file : Interface.selectFiles())
			{
				csvToJson(file.getAbsolutePath());
				filesProcessed++;
			}

			if (filesProcessed == 0)
			{
				if (Interface.ask("No files selected, quit?"))
				{
					Interface.console.stroke();
					break;
				}
			}
			Interface.console.stroke();
		}

		Interface.console.println("Thanks for using Valentine's Solutions CSV to JSON converter!");
	}

	private static void csvToJson(String _sourceFilePathString)
	{
		Interface.console.println("CONVERTING");

		if (_sourceFilePathString.lastIndexOf(".csv") != _sourceFilePathString.length() - 4)
		{
			Interface.console.println("Invalid input, should be .scv file: \"" + _sourceFilePathString + '"');
			Interface.console.println("FAILURE\n");
			return;
		}

		String targetFilePathString = _sourceFilePathString.substring(0, _sourceFilePathString.lastIndexOf(".csv")) + ".json";

		Path sourceFilePath = Paths.get(_sourceFilePathString);
		Path targetFilePath = Paths.get(targetFilePathString);

		Interface.console.println("Source: \"" + sourceFilePath.toAbsolutePath().toString() + '"');
		Interface.console.println("Target: \"" + targetFilePath.toAbsolutePath().toString() + '"');

		if (Files.notExists(sourceFilePath))
		{
			Interface.console.println("Input file does not exist");
			Interface.console.println("FAILURE\n");
			return;
		}
		if (Files.exists(targetFilePath))
		{
			if (Interface.ask("Output file already exists, override?"))
			{
				try
				{
					Files.delete(targetFilePath);
				}
				catch (IOException e)
				{
					Interface.console.println("Can't delete target file");
					Interface.console.println("FAILURE\n");
					return;
				}
			}
			else
			{
				Interface.console.println("Skipping...");
				Interface.console.println("FAILURE\n");
				return;
			}
		}

		try
		{
			Files.createFile(targetFilePath);
		}
		catch (IOException e)
		{
			Interface.console.println("Can't create target file");
			Interface.console.println("FAILURE\n");
			return;
		}

		byte[] inputDataBytes = null;

		try
		{
			inputDataBytes = Files.readAllBytes(sourceFilePath);
		}
		catch (IOException e)
		{
			Interface.console.println("Can't read source file");
			Interface.console.println("FAILURE\n");
			e.printStackTrace();
			return;
		}

		String inputDataString = new String(inputDataBytes);

		String outputDataString = parseAndConvert(inputDataString);

		Interface.console.println("Processing...");

		try
		{
			Files.write(targetFilePath, outputDataString.getBytes());
		}
		catch (IOException e)
		{
			Interface.console.println("Problems writing to output file");
			Interface.console.println("FAILURE\n");
			return;
		}

		Interface.console.println("SUCCESS\n");
	}

	private static String parseAndConvert(String _csvString)
	{
		CSVParser.parseStructure(CSVParser.disassemble(_csvString), 0, 0).print();
		
		return JSONPrinter.pop();
	}

}

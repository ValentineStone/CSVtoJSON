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
		
		//csvToJson("C:/Users/Valentine/Dropbox/Роман Леонид/Leonid's Test/struct.csv");
		//csvToJson("C:/Users/Valentine/Desktop/zerg.csv");

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

		String inputDataString = new String();
		
		try
		{
			inputDataString = new String(inputDataBytes, "UTF-8");
		}
		catch (UnsupportedEncodingException _exception)
		{
			Interface.console.println("Can't decode source file");
			Interface.console.println("FAILURE\n");
			_exception.printStackTrace();
			return;
		}

		String outputDataString = parseAndConvert(inputDataString);

		Interface.console.println("Processing...");

		try
		{
			Files.write(targetFilePath, outputDataString.getBytes("UTF-8"));
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
		(new CSVParser(_csvString)).getObject().print();
		
		/*
		Interface.console.println("Resulting JSON object:");
		Interface.console.println(JSONPrinter.peek());
		*/
		
		return JSONPrinter.pop();
	}

}

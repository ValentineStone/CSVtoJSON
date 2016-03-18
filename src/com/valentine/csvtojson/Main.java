package com.valentine.csvtojson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Main
{
	public static void main(String[] _args)
	{
		Interface.console("Welcome to CSV to JSON converter");
		Interface.console("by Leonid Romanovky\n");
		
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
				Interface.console("No files selected");
				if (Interface.ask("No files selected, quit?"))
				{
					return;
				}
			}
		}
		
	}
	
	private static void csvToJson(String _sourceFilePathString)
	{
		Interface.console("CONVERTING");
		
		if (_sourceFilePathString.lastIndexOf(".csv") != _sourceFilePathString.length() - 4)
		{
			Interface.console("Invalid input, should be .scv file: \"" + _sourceFilePathString + '"');
			Interface.console("FAILURE\n");
			return;
		}
		
		String targetFilePathString = _sourceFilePathString.substring(0, _sourceFilePathString.lastIndexOf(".csv")) + ".json";
		
		Path sourceFilePath = Paths.get(_sourceFilePathString);
		Path targetFilePath = Paths.get(targetFilePathString);
		
		Interface.console("Source: \"" + sourceFilePath.toAbsolutePath().toString() + '"');
		Interface.console("Target: \"" + targetFilePath.toAbsolutePath().toString() + '"');
		
		if (Files.notExists(sourceFilePath))
		{
			Interface.console("Input file does not exist");
			Interface.console("FAILURE\n");
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
					Interface.console("Can't delete target file");
					Interface.console("FAILURE\n");
					return;
				}
			}
			else
			{
				Interface.console("Skipping...");
				Interface.console("FAILURE\n");
				return;
			}
		}
		
		try
		{
			Files.createFile(targetFilePath);
		}
		catch (IOException e)
		{
			Interface.console("Can't create target file");
			Interface.console("FAILURE\n");
			return;
		}
		
		byte[] inputDataBytes = null;
		
		try
		{
			inputDataBytes = Files.readAllBytes(sourceFilePath);
		}
		catch (IOException e)
		{
			Interface.console("Can't read source file");
			Interface.console("FAILURE\n");
			e.printStackTrace();
			return;
		}
		
		String inputDataString = new String(inputDataBytes);
		
		String outputDataString = parseAndConvert(inputDataString);
		
		Interface.console("Processing...");
		
		try
		{
			Files.write(targetFilePath, outputDataString.getBytes());
		}
		catch (IOException e)
		{
			Interface.console("Problems writing to output file");
			Interface.console("FAILURE\n");
			return;
		}
		
		Interface.console("SUCCESS\n");
	}
	
	private static String parseAndConvert(String _inputDataString)
	{
		String outputDataString = new String();
		
		List<String> inputDataStringLines = Arrays.asList(_inputDataString.split("\n"));
		
		List<List<String>> inputDataStringCells = new ArrayList<List<String>>();
		
		for (String inputDataStringLine : inputDataStringLines)
		{
			inputDataStringCells.add(Arrays.asList(inputDataStringLine.split(";")));
		}
		
		for (int lineNo = 0; lineNo < inputDataStringCells.size(); lineNo++)
		{
			outputDataString += String.format("LINE % 5d:\n", lineNo);
			
			for (int cellNo = 0; cellNo < inputDataStringCells.get(lineNo).size(); cellNo++)
			{
				outputDataString += String.format("\tCELL % 5d: %s\n", cellNo, inputDataStringCells.get(lineNo).get(cellNo));
			}
		}
		
		
		return outputDataString;
	}

}

package com.valentine.csvtojson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public abstract class Main
{
	static final Scanner sysin = new Scanner(System.in);
	
	public static void main(String[] _args)
	{
		JFileChooser fileChoser = new JFileChooser();
		
		System.err.println("Welcome to CSV to JSON converter");
		System.err.println("by Leonid Romanovky\n");
		
		while (true)
		{
			if (fileChoser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				csvToJson(fileChoser.getSelectedFile().getAbsolutePath());
			}
			else
			{
				return;
			}
		}
	}
	
	private static void csvToJson(String _sourceFilePathString)
	{
		System.err.println("CONVERTING");
		
		if (_sourceFilePathString.lastIndexOf(".csv") != _sourceFilePathString.length() - 4)
		{
			System.err.println("Invalid input, should be .scv file: \"" + _sourceFilePathString + '"');
			System.err.println("FAILURE\n");
			return;
		}
		
		String targetFilePathString = _sourceFilePathString.substring(0, _sourceFilePathString.lastIndexOf(".csv")) + ".json";
		
		Path sourceFilePath = Paths.get(_sourceFilePathString);
		Path targetFilePath = Paths.get(targetFilePathString);
		
		System.err.println("Source: \"" + sourceFilePath.toAbsolutePath().toString() + '"');
		System.err.println("Target: \"" + targetFilePath.toAbsolutePath().toString() + '"');
		
		if (Files.notExists(sourceFilePath))
		{
			System.err.println("Input file does not exist");
			System.err.println("FAILURE\n");
			return;
		}
		if (Files.exists(targetFilePath))
		{
			System.err.println("Output file already exists");
			System.err.print("Override? (y/n) : ");
			
			switch (sysin.next().charAt(0))
			{
				case 'n':
				case 'N':
				{
					System.err.println("Skipping...");
					System.err.println("FAILURE\n");
					return;
				}
				case 'y':
				case 'Y':
				{
					try
					{
						Files.delete(targetFilePath);
					}
					catch (IOException e)
					{
						System.err.println("Can't delete target file");
						System.err.println("FAILURE\n");
						return;
					}
				}
			}
		}
		
		try
		{
			Files.createFile(targetFilePath);
		}
		catch (IOException e)
		{
			System.err.println("Can't create target file");
			System.err.println("FAILURE\n");
			return;
		}
		
		byte[] inputDataBytes = null;
		
		try
		{
			inputDataBytes = Files.readAllBytes(sourceFilePath);
		}
		catch (IOException e)
		{
			System.err.println("Can't read source file");
			System.err.println("FAILURE\n");
			e.printStackTrace();
			return;
		}
		
		String inputDataString = new String(inputDataBytes);
		
		String outputDataString = parseAndConvert(inputDataString);
		
		System.err.println("Processing...");
		
		try
		{
			Files.write(targetFilePath, outputDataString.getBytes());
		}
		catch (IOException e)
		{
			System.err.println("Problems writing to output file");
			System.err.println("FAILURE\n");
			return;
		}
		
		System.err.println("SUCCESS\n");
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

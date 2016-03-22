package com.valentine.csvtojson.json;

public class JSONPrinter
{
	private static StringBuilder stringBuilder = new StringBuilder();
	private static char indentSymbol = '\t';
	private static String indentString = new String();
	
	
	
	
	public static String peek()
	{
		return stringBuilder.toString();
	}
	
	public static String pop()
	{
		String content = stringBuilder.toString();
		
		stringBuilder = new StringBuilder();
		
		return content;
		
	}
	
	
	

	public static void print(String _line)
	{
		stringBuilder.append(indentString + _line);
	}

	public static void println(String _line)
	{
		stringBuilder.append(indentString + _line + '\n');
	}

	public static void indent()
	{
		indentString += indentSymbol;
	}

	public static void outdent()
	{
		indentString = indentString.substring(0, indentString.length() - 1);
	}
	
	
	
	
	

	public static void setIndentSymbol(char _indentSymbol)
	{
		indentString = indentString.replace(indentSymbol, _indentSymbol);
		indentSymbol = _indentSymbol;
	}

	public static char getIndentSymbol()
	{
		return indentSymbol;
	}
}

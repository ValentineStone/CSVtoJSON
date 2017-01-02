package com.valentine.csvtojson.json;

import java.io.*;

public class JSONParser
{
	public static void parse(Reader _reader, JSONObject _jsonObject)
	{
		try
		{
			parseJSONObject(_reader, _jsonObject);
		}
		catch (IOException _exception)
		{
			_jsonObject = null;
			_exception.printStackTrace();
		}
	}
	
	private static void parseJSONObject(Reader _reader, JSONObject _jsonObject) throws IOException
	{
		JSONObject jsonObject = null;
		
		int intRead;
		char charRead;
		
		while (-1 != (intRead = _reader.read()))
		{
			charRead = (char) intRead;
			
			if (Character.isWhitespace(intRead))
				continue;
			else if (charRead == '{')
			{
				_jsonObject = new JSONStructure();
				
			}
		}
	}
	
	private static void parseJSONStructure(Reader _reader, JSONObject _jsonObject) throws IOException
	{
		int intRead;
		char charRead;
		
		while (-1 != (intRead = _reader.read()))
		{
			charRead = (char) intRead;
			
			if (Character.isWhitespace(intRead))
			{
				continue;
			}
			else if (charRead == '}')
			{
				return;
			}
			else {}
		}
	}
	
	private static String parseQuotedString(Reader _reader) throws IOException
	{
		StringBuilder stringBuilder = new StringBuilder("");
		
		int intRead;
		char charRead;
		
		while (-1 != (intRead = _reader.read()))
		{
			charRead = (char) intRead;
			
			if (charRead == '"')
			{
				break;
			}
			else if (charRead == '\\')
			{
				_reader.read();
			}
			else
			{
				stringBuilder.append(charRead);
			}
		}
		
		return stringBuilder.toString();
	}
	
	
}

enum JSONCharacter
{
	COMMA,
	CURLY_CLOSING_BRACKET,
	SQUARE_CLOSING_BRACKET
}
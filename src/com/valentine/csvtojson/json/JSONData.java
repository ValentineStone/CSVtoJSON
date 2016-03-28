package com.valentine.csvtojson.json;

import java.util.*;

public class JSONData extends JSONObject
{
	//public static final JSONData EMPTY = new JSONData("");
	
	private String data;
	
	public JSONData(int _csvLeftIndex)
	{
		this(null, _csvLeftIndex);
	}
	
	public JSONData(String _data, int _csvLeftIndex)
	{
		super(_csvLeftIndex);
		set(_data);
	}

	public void print()
	{
		if (data.isEmpty())
		{
			JSONPrinter.println("null");
			return;
		}
		
		if (data.length() <= 6 || data.length() >= 4)
		{
			String dataLowercase = data.toLowerCase();
			if (dataLowercase.equals("true") || dataLowercase.equals("false"))
			{
				JSONPrinter.println(dataLowercase);
				return;
			}
			else
			{
				if (dataLowercase.equals("истина"))
				{
					JSONPrinter.println("true");
					return;
				}
				else if (dataLowercase.equals("ложь"))
				{
					JSONPrinter.println("false");
					return;
				}
			}
		}
		
		JSONPrinter.println(data);
	}
	
	public void set(String _data)
	{
		data = _data == null ? "" : _data;
	}
	
	public JSONData clone()
	{
		return new JSONData(data, getCsvLeftIndex());
	}
	
	public Iterator<JSONObject> iterator()
	{
		return (new ArrayList<JSONObject>()).iterator();
	}
}

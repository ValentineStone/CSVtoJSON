package com.valentine.csvtojson.json;

import java.util.*;

public class JSONData implements JSONObject
{
	public static final JSONData EMPTY = new JSONData("");
	
	private String data;
	
	public JSONData(String _data)
	{
		set(_data);
	}

	public void print()
	{
		if (data.isEmpty())
		{
			JSONPrinter.println("null");
			return;
		}
		
		if (data.length() == 5 || data.length() == 4)
		{
			String dataLowercase = data.toLowerCase();
			if (dataLowercase.equals("true") || dataLowercase.equals("false"))
			{
				JSONPrinter.println(dataLowercase);
				return;
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
		return new JSONData(data);
	}
	
	public Iterator<JSONObject> iterator()
	{
		return (new ArrayList<JSONObject>()).iterator();
	}
}

package com.valentine.csvtojson.json;

import java.util.*;
import java.util.Map.*;

public class JSONStructure extends JSONObject implements Iterable<Entry<String, JSONObject>>
{
	private Map<String, JSONObject> fields;
	
	public JSONStructure()
	{
		fields = new LinkedHashMap<String, JSONObject>();
	}
	
	public JSONStructure(int _csvLeftIndex)
	{
		super(_csvLeftIndex);
		
		fields = new LinkedHashMap<String, JSONObject>();
	}
	
	public void print()
	{
		JSONPrinter.println("{");
		JSONPrinter.indent();
		
		boolean isFirstElement = true;
		
		for (Entry<String, JSONObject> field : this)
		{
			if (!field.getValue().isEmpty())
			{
				if (isFirstElement)
				{
					isFirstElement = false;
				}
				else
				{
					JSONPrinter.println(",");
				}
				
				JSONPrinter.println(field.getKey() + ":");
				field.getValue().print();
			}
		}
		
		JSONPrinter.outdent();
		JSONPrinter.println("}");
	}
	
	public void addField(String _string, JSONObject _object)
	{
		fields.put(_string, _object);
	}
	
	public JSONObject get(String _string)
	{
		return fields.get(_string);
	}
	
	public int size()
	{
		return fields.size();
	}
	
	public JSONStructure clone()
	{
		JSONStructure copy = new JSONStructure(getCsvLeftIndex());
		for (Entry<String, JSONObject> field : this)
		{
			if (field.getValue() != null)
			{
				copy.addField(field.getKey(), field.getValue().clone());
			}
			else
			{
				copy.addField(field.getKey(), null);
			}
		}
		
		return copy;
	}
	
	public boolean isEmpty()
	{
		for (Entry<String, JSONObject> field : this)
		{
			if (!field.getValue().isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	public Iterator<Entry<String, JSONObject>> iterator()
	{
		return fields.entrySet().iterator();
	}
}

package com.valentine.csvtojson.json;

import java.util.*;
import java.util.Map.*;

public class JSONStructure implements JSONObject, Iterable<Entry<String, JSONObject>>
{
	private Map<String, JSONObject> fields;
	
	public JSONStructure()
	{
		fields = new HashMap<String, JSONObject>();
	}
	
	public void print()
	{
		JSONPrinter.println("{");
		JSONPrinter.indent();
		
		boolean isFirstElement = true;
		
		for (Entry<String, JSONObject> field : this)
		{
			if (isFirstElement)
			{
				isFirstElement = false;
			}
			else
			{
				JSONPrinter.println(",");
			}
			field.getValue().print();
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
		JSONStructure copy = new JSONStructure();
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

	public Iterator<Entry<String, JSONObject>> iterator()
	{
		return fields.entrySet().iterator();
	}
}

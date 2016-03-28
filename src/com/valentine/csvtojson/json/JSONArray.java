package com.valentine.csvtojson.json;

import java.util.*;

public class JSONArray extends JSONObject implements Iterable<JSONObject>
{
	private List<JSONObject> objects;
	private JSONObject template;
	
	public JSONArray(JSONObject _template, int _csvLeftIndex)
	{
		super(_csvLeftIndex);
		
		objects = new ArrayList<JSONObject>();
		setTemplate(_template);
	}
	
	public void print()
	{
		JSONPrinter.println("[");
		JSONPrinter.indent();
		
		/*
		JSONPrinter.println("prototype :");
		template.print();
		*/
		
		boolean isFirstElement = true;
		
		for (JSONObject field : this)
		{
			if (isFirstElement)
			{
				isFirstElement = false;
			}
			else
			{
				JSONPrinter.println(",");
			}
			field.print();
		}
		
		JSONPrinter.outdent();
		JSONPrinter.println("]");
	}
	
	public void addObject(JSONObject _object)
	{
		objects.add(_object);
	}
	
	public JSONObject get(int _i)
	{
		return objects.get(_i);
	}
	
	public int size()
	{
		return objects.size();
	}
	
	public JSONArray clone()
	{
		JSONArray copy = new JSONArray(getTemplate(), getCsvLeftIndex());
		for (JSONObject object : this)
		{
			if (object != null)
			{
				copy.addObject(object.clone());
			}
			else
			{
				copy.addObject(null);
			}
		}
		
		return copy;
	}
	
	public JSONObject getTemplate()
	{
		return template;
	}
	
	public void setTemplate(JSONObject _template)
	{
		template = _template;
	}

	public Iterator<JSONObject> iterator()
	{
		return objects.iterator();
	}
}

package com.valentine.csvtojson.json;

import java.util.*;

@Deprecated
public class JSONField implements JSONObject
{
	private JSONObject object;
	private String name;
	
	public JSONField(String _name, JSONObject _object)
	{
		name = _name;
		object = _object;
	}
	
	public JSONField(String _name)
	{
		this(_name, null);
	}
	
	public void set(JSONObject _object)
	{
		object = _object;
	}

	public void print()
	{
		JSONPrinter.println(name + " :");
		object.print();
	}
	
	public JSONField clone()
	{
		if (object != null)
		{
			return new JSONField(name, object.clone());
		}
		else
		{
			return new JSONField(name, null);
		}
	}
	
	public Iterator<JSONObject> iterator()
	{
		return (new ArrayList<JSONObject>()).iterator();
	}
}

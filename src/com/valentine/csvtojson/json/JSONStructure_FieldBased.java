package com.valentine.csvtojson.json;

import java.util.*;

@Deprecated
public class JSONStructure_FieldBased implements JSONObject, Iterable<JSONField>
{
	private List<JSONField> fields;
	
	public JSONStructure_FieldBased()
	{
		fields = new ArrayList<JSONField>();
	}
	
	public void print()
	{
		JSONPrinter.println("{");
		JSONPrinter.indent();
		
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
		JSONPrinter.println("}");
	}
	
	public void addField(JSONField _field)
	{
		fields.add(_field);
	}
	
	public JSONField get(int _i)
	{
		return fields.get(_i);
	}
	
	public int size()
	{
		return fields.size();
	}
	
	public JSONStructure_FieldBased clone()
	{
		JSONStructure_FieldBased copy = new JSONStructure_FieldBased();
		for (JSONField field : this)
		{
			if (field != null)
			{
				copy.addField(field.clone());
			}
			else
			{
				copy.addField(null);
			}
		}
		
		return copy;
	}

	public Iterator<JSONField> iterator()
	{
		return fields.iterator();
	}
}

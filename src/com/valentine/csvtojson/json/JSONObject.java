package com.valentine.csvtojson.json;

public abstract class JSONObject
{
	int csvLeftIndex = 0;
	
	public JSONObject()
	{}
	
	public JSONObject(int _csvLeftIndex)
	{
		csvLeftIndex = _csvLeftIndex;
	}
	
	public abstract void print();
	
	public abstract JSONObject clone();
	
	public int getCsvLeftIndex()
	{
		return csvLeftIndex;
	}
	
	public abstract boolean isEmpty();
}

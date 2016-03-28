package com.valentine.csvtojson.json;

public abstract class JSONObject
{
	int csvLeftIndex;
	
	public JSONObject(int _csvLeftIndex)
	{
		csvLeftIndex = _csvLeftIndex;
		
		System.err.println(this.getClass().getSimpleName() + " at " + getCsvLeftIndex());
	}
	
	public abstract void print();
	
	public abstract JSONObject clone();
	
	public int getCsvLeftIndex()
	{
		return csvLeftIndex;
	}
}

package com.valentine.csvtojson.csv;

import java.util.Map.*;

import com.valentine.csvtojson.json.*;

public class CSVParser
{
	private String[][] csv;
	private int headerHeight = 0;
	private JSONObject object;
	
	
	public CSVParser(String _csvString)
	{
		String[] _csvLines = _csvString.split("\\r?\\n");
		String[][] _csv = new String[_csvLines.length][];

		for (int i = 0; i < _csvLines.length; i++)
		{
			_csv[i] = _csvLines[i].split(";", -1);
		}
		
		csv = _csv;
		
		if (!isValid())
		{
			System.err.println("Input is not valid!");
			
			return;
		}
		
		while (!isLineEmpty(++headerHeight));
		
		object =  parseHeader();
		
		fillJSONObject(object, headerHeight + 1, 0);
	}
	
	
	
	
	
	
	
	

	private JSONObject parseJSONObject(int _row, int _col, int _limit)
	{
		System.err.println("[" + _row + "," + _col + "] : " + getCsv()[_row][_col] + " limited by " + _limit);
		
		// check for left most elements to avoid array indexation errors
		
		if (_col == _limit)
		{
			if (isData(_row, _col))
			{
				return new JSONData(_col);
			}
			else
			{
				JSONStructure structure = new JSONStructure(_col);
				
				structure.addField(getCsv()[_row][_col], parseJSONObject(_row + 1, _col, _limit));
				
				return structure;
			}
		}
		
		// if not leftmost, check for array
		
		if (isArray(_row, _col))
		{
			return new JSONArray(parseJSONObject(_row, _col + 1, _limit), _col);
		}
		
		// if not array, check if data
		
		if (isData(_row, _col))
		{
			return new JSONData(_col);
		}
		
		// if not data, it is a structure.
		
		JSONStructure structure = new JSONStructure(_col);
		
		int previousElementIndex = 0;
		int thisCellIndex = 1;
		
		while (isCellEmpty(_row, _col + thisCellIndex))
		{
			if (!isCellEmpty(_row + 1, _col + thisCellIndex))
			{
				structure.addField(getCsv()[_row + 1][_col + previousElementIndex], parseJSONObject(_row + 1, _col + previousElementIndex, _col + thisCellIndex - 1));
				
				previousElementIndex = thisCellIndex;
			}
			
			thisCellIndex++;
			
			if (_col + thisCellIndex > _limit)
			{
				break;
			}
		}
		structure.addField(getCsv()[_row + 1][_col + previousElementIndex], parseJSONObject(_row + 1, _col + previousElementIndex, _col + thisCellIndex - 1));
		
		
		return structure;
	}
	
	
	
	
	
	
	
	
	private JSONObject parseHeader()
	{
		if (isArray(0, 0))
		{
			if (isCellEmpty(0, 0))
			{
				return new JSONArray(parseJSONObject(0, 1, getCsv()[0].length - 1), 0);
			}
			else
			{
				JSONStructure structure = new JSONStructure(0);
				
				structure.addField(getCsv()[0][0], new JSONArray(parseJSONObject(0, 1, getCsv()[0].length - 1), 0));
				
				return structure;
			}
		}
		
		System.err.println("Structure at root!");
		
		JSONStructure structure = new JSONStructure(0);
		
		int previousElementIndex = 0;
		int thisCellIndex = 1;
		
		while (thisCellIndex < getCsv()[0].length)
		{
			if (!isCellEmpty(0, thisCellIndex))
			{
				structure.addField(getCsv()[0][previousElementIndex], parseJSONObject(0, previousElementIndex, thisCellIndex - 1));
				
				previousElementIndex = thisCellIndex;
			}
			
			thisCellIndex++;
		}
		structure.addField(getCsv()[0][previousElementIndex], parseJSONObject(0, previousElementIndex, thisCellIndex - 1));
		
		return structure;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private void fillJSONObject(JSONObject _object, int _row, int _col)
	{
		if (_object instanceof JSONData)
		{
			((JSONData)_object).set(getCsv()[_row][_col]);
		}
		else if (_object instanceof JSONStructure)
		{
			for (Entry<String, JSONObject> entry : (JSONStructure)_object)
			{
				fillJSONObject(entry.getValue(), _row, entry.getValue().getCsvLeftIndex());
			}
		}
		else if (_object instanceof JSONArray)
		{
			if (isCellEmpty(_row, _col))
			{
				return;
			}
			
			int lastIndex = 0;
			int thisIndex;
			
			for (int i = 0; _row + i < getCsv().length; i++)
			{
				if (!isCellEmpty(_row + i, _col))
				{
					try
					{
						System.err.println("Array index \"" + getCsv()[_row + i][_col] + "\" at [" + (_row + i) + "," + _col + "]");
						
						thisIndex = Integer.parseInt(getCsv()[_row + i][_col]);
					}
					catch (NumberFormatException _exc)
					{
						continue;
					}
					
					if (thisIndex - lastIndex == 1)
					{
						((JSONArray)_object).addObject(((JSONArray)_object).getTemplate().clone());
						
						fillJSONObject(((JSONArray)_object).get(thisIndex - 1), _row + i, _col + 1);
					}
					else
					{
						break;
					}
					
					lastIndex = thisIndex;
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	private boolean isValid()
	{
		if (getCsv().length == 0) return false;
		
		int width = getCsv()[0].length;
		
		for(String[] row : getCsv())
		{
			if (width != row.length)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	private boolean isArray(int _row, int _col)
	{
		return isCellEmpty(_row, _col + 1) && isCellEmpty(_row + 1, _col);
	}
	
	private boolean isStructure(int _row, int _col)
	{
		return !isCellEmpty(_row + 1, _col);
	}
	
	private boolean isData(int _row, int _col)
	{
		return isCellEmpty(_row + 1, _col);
	}
	
	
	
	
	
	
	
	
	
	private boolean isCellEmpty(int _row, int _col)
	{
		return getCsv()[_row][_col].isEmpty();
	}
	
	private boolean isLineEmpty(int _row)
	{
		for (String cell : getCsv()[_row])
		{
			if (!cell.isEmpty()) return false;
		}
		
		return true;
	}
	
	private int countElementsInLine(int _row)
	{
		int count = 0;
		
		for (String cell : getCsv()[_row])
		{
			if (!cell.isEmpty()) return count++;
		}
		
		return count;
	}
	
	
	
	
	
	
	
	public int getheaderHeightIndex()
	{		
		return headerHeight;
	}
	
	public int getWidthIndex()
	{
		return getCsv().length - 1;
	}
	
	public String[][] getCsv()
	{
		return csv;
	}

	public JSONObject getObject()
	{
		return object;
	}
}

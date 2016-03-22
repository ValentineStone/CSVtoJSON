package com.valentine.csvtojson.csv;

import com.valentine.csvtojson.json.*;

public class CSVParser
{
	public static JSONObject parseObject(String[][] _csv, int _row, int _col)
	{
		if (_col != getWidthIndex(_csv))
		{
			if (isArrayHeader(_csv, _row, _col))
			{
				return parseArray(_csv, _row, _col);
			}
		}

		if (isCellEmpty(_csv, _row + 1, _col))
		{
			return JSONData.EMPTY;
		}
		else 
		{
			return parseStructure(_csv, _row, _col);
		}
	}
	
	public static JSONStructure parseStructure(String[][] _csv, int _row, int _col)
	{
		JSONStructure structure = new JSONStructure();
		
		if (_row == 0)
		{
			for (int col = _col; col < _csv[_row].length; col++)
			{
				structure.addField(_csv[_row][col], parseObject(_csv, _row, col));
			}
		}
		else
		{
			
			for (int col = _col; col < _csv[_row].length; col++)
			{
				structure.addField(_csv[_row][col], parseObject(_csv, _row, col));
			}
		}
		
		return structure;
	}
	
	public static JSONArray parseArray(String[][] _csv, int _row, int _col)
	{
		JSONArray array;
		
		if (isCellEmpty(_csv, _row, _col+2))
		{
			array = new JSONArray(parseStructure(_csv, _row+1, _col+1));
		}
		else
		{
			array = new JSONArray(JSONData.EMPTY);
		}
		
		return array;
	}
	
	public static String[][] disassemble(String _csvString)
	{
		String[] csvLines = _csvString.split("\\r?\\n");
		String[][] csv = new String[csvLines.length][];

		for (int i = 0; i < csvLines.length; i++)
		{
			csv[i] = csvLines[i].split(";");
		}
		
		return csv;
	}
	
	public static boolean isArrayHeader(String[][] _csv, int _row, int _col)
	{
		return !isCellEmpty(_csv, _row, _col) && isCellEmpty(_csv, _row, _col+1);
	}
	
	public static boolean isCellEmpty(String[][] _csv, int _row, int _col)
	{
		return _csv[_row][_col].isEmpty();
	}
	
	public static boolean isLineEmpty(String[][] _csv, int _row)
	{
		boolean answer = true;
		
		for (String cell : _csv[_row])
		{
			answer = cell.isEmpty() ? false : answer;
		}
		
		return answer;
	}
	
	public static int getHeaderHeightIndex(String[][] _csv)
	{
		int height = 1;
		
		while (isLineEmpty(_csv, height))
		{
			height++;
		}
		
		return height;
	}
	
	public static int getWidthIndex(String[][] _csv)
	{
		return _csv.length - 1;
	}
}

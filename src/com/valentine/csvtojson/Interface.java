package com.valentine.csvtojson;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public abstract class Interface
{
	private static JFileChooser fileChoser = new JFileChooser();
	private static JDialog dialog = new JDialog();
	private static JFrame frame = new JFrame();
	private static JTextArea textArea = new JTextArea();
	
	public static final Console console = new Console(textArea);
	
	static
	{
		fileChoser.setMultiSelectionEnabled(true);
		
		textArea.setEditable(false);
		
		frame.setSize(300, 600);
		frame.add(textArea);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static List<File> selectFiles()
	{
		if (fileChoser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		{
			return Arrays.asList(fileChoser.getSelectedFiles());
		}
		return new LinkedList();
	}
	
	public static boolean ask(String _question)
	{
		if (JOptionPane.showConfirmDialog(frame, _question, "Request dialog", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

class Console
{
	private JTextArea textArea;
	private char indentSymbol;
	private String indentString = new String();
	
	public Console(JTextArea _textArea)
	{
		textArea = _textArea;
	}
	
	public void print(String _line)
	{
		textArea.append(_line);
	}
	
	public void println(String _line)
	{
		textArea.append(_line + '\n');
	}
	
	public void in()
	{
		indentString += indentSymbol;
	}
	
	public void out()
	{
		indentString = indentString.substring(0, indentString.length()-1);
	}

	public void setIndentSymbol(char _indentSymbol)
	{
		indentString = indentString.replace(indentSymbol, _indentSymbol);
		indentSymbol = _indentSymbol;
	}
	
	
}

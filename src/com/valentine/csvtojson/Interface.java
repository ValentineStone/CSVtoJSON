package com.valentine.csvtojson;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

public abstract class Interface
{
	private static JFileChooser fileChoser;
	private static JFrame frame;
	private static JScrollPane scrollPane;
	private static JTextArea textArea;

	public static final Console console;

	static
	{
		fileChoser = new JFileChooser();
		frame = new JFrame();
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		console = new Console(textArea);

		fileChoser.setMultiSelectionEnabled(true);

		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));

		scrollPane.setBorder(new LineBorder(Color.BLACK, 5));

		frame.setSize(console.getCharWidth() * 80, console.getCharHeight() * 18);
		frame.add(scrollPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static List<File> selectFiles()
	{
		List<File> selectedFiles;

		console.println("Select files.");
		if (fileChoser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		{
			console.println("Files piked:");
			console.indent();
			for (File file : fileChoser.getSelectedFiles())
			{
				console.println(file.getName());
			}
			console.outdent();
			selectedFiles = Arrays.asList(fileChoser.getSelectedFiles());
		}
		else
		{
			console.println("Canceled.");
			selectedFiles = new LinkedList<File>();
		}

		fileChoser.setSelectedFile(new File(""));

		// console.stroke();

		return selectedFiles;
	}

	public static boolean ask(String _question)
	{
		boolean answer;

		console.print(_question + " [y/n]: ");

		if (JOptionPane.showConfirmDialog(frame, _question, "Request dialog", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			console.println("y");
			answer = true;
		}
		else
		{
			console.println("n");
			answer = false;
		}

		// console.stroke();

		return answer;
	}

	public static int getWidth()
	{
		return textArea.getWidth();
	}

	public static int getHeight()
	{
		return textArea.getHeight();
	}
}

class Console
{
	private JTextArea textArea;
	private char indentSymbol = '\t';
	private String indentString = new String();
	private String strokeString = new String();

	public Console(JTextArea _textArea)
	{
		textArea = _textArea;
	}

	public void print(String _line)
	{
		textArea.append(indentString + _line);
	}

	public void println(String _line)
	{
		textArea.append(indentString + _line + '\n');
	}

	public void indent()
	{
		indentString += indentSymbol;
	}

	public void outdent()
	{
		indentString = indentString.substring(0, indentString.length() - 1);
	}

	public void stroke()
	{
		int lineWidth = Interface.getWidth() / getCharWidth();

		if (strokeString.length() < lineWidth)
		{
			for (int i = strokeString.length(); i < lineWidth; i++)
			{
				strokeString += '-';
			}
		}
		else
			if (strokeString.length() > lineWidth)
			{
				strokeString = strokeString.substring(0, lineWidth);
			}

		println(strokeString);
	}

	public void setIndentSymbol(char _indentSymbol)
	{
		indentString = indentString.replace(indentSymbol, _indentSymbol);
		indentSymbol = _indentSymbol;
	}

	public char getIndentSymbol()
	{
		return indentSymbol;
	}

	public int getCharWidth()
	{
		return (int) textArea.getFontMetrics(textArea.getFont()).charWidth('_');
	}

	public int getCharHeight()
	{
		return textArea.getFontMetrics(textArea.getFont()).getHeight();
	}
}

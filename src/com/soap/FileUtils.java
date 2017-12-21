package com.soap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils 
{
	private static File file;
	private static Scanner in;
	static
	{
		file = new File("coins.txt");
		try 
		{
			in = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			try 
			{
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	
}

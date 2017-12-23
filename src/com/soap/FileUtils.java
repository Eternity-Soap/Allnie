package com.soap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.common.graph.ElementOrder.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class FileUtils 
{
	private static File coins;
	public static void initCoinsFile()
	{
		coins = new File("coins.json");
		if (!coins.exists())
		{
			try 
			{
				coins.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static List<Coin> loadCoins()
	{
		List<Coin> list = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		java.lang.reflect.Type coinListType = new TypeToken<ArrayList<Coin>>(){}.getType();
		try 
		{
			JsonReader reader = new JsonReader(new FileReader(coins));
			list = gson.fromJson(reader, coinListType);
		} 
		catch (FileNotFoundException | NullPointerException e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static void saveCoins(List<Coin> list)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try
		{
			FileWriter writer = new FileWriter(coins);
			gson.toJson(list.subList(3, list.size() - 1), writer);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

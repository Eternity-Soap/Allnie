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
	private static File keys;
	public static void initFiles()
	{
		coins = new File("coins.json");
		keys = new File("keys.json");
		if (!coins.exists())
		{
			try 
			{
				coins.createNewFile();
				System.out.println("No coin file found, new one created.");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		if (!keys.exists())
		{
			try 
			{
				keys.createNewFile();
				System.out.println("No key file found, new one created.");
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
			reader.close();
		} 
		catch (FileNotFoundException | NullPointerException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Coins loaded");
		return list;
	}
	
	public static List<SatoshiKeypair> loadKeys()
	{
		List<SatoshiKeypair> list = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		java.lang.reflect.Type keyListType = new TypeToken<ArrayList<SatoshiKeypair>>(){}.getType();
		try 
		{
			JsonReader reader = new JsonReader(new FileReader(keys));
			list = gson.fromJson(reader, keyListType);
			reader.close();
		} 
		catch (FileNotFoundException | NullPointerException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Keys loaded");
		return list;
	}
	
	public static void saveCoins(List<Coin> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (isHardcoded(list.get(i), Main.loadHardcoded()))
			{
				list.remove(i);
				i--;
			}
		}
		//System.out.println(list.size());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try
		{
			FileWriter writer = new FileWriter(coins, false);
			gson.toJson(list, writer);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Coins saved");
	}
	
	public static void saveKeys(List<SatoshiKeypair> list)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try
		{
			FileWriter writer = new FileWriter(keys, false);
			gson.toJson(list, writer);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Keys saved");
	}
	
	public static boolean isHardcoded(Coin coin, List<Coin> hardcoded)
	{
		for (Coin c : hardcoded)
		{
				if (coin.NAME.trim().equals(c.NAME.trim()))
					return true;
		}
		return false;
	}
}

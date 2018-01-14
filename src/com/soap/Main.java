package com.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args)
	{	
		/* Scanner in = new Scanner(System.in);
		FileUtils.initCoinsFile();
		List<Coin> list = loadHardcoded();
		if (FileUtils.loadCoins() != null)
			list.addAll(FileUtils.loadCoins());
		list = delDuplicates(list);
		System.out.print("Add public prefix (in hex): ");
		String pubprefix;
		String privprefix;
		String name;
		String code;
		pubprefix = in.nextLine();
		System.out.print("Add private prefix (in hex): ");
		privprefix = in.nextLine();
		System.out.print("Add name: ");
		name = in.nextLine();
		System.out.println("Add code: ");
		code = in.nextLine();
		Coin coin = new Coin((byte)Integer.parseInt(pubprefix, 16), (byte)Integer.parseInt(privprefix, 16), name, code);
		list.add(coin);
		list = delDuplicates(list);
		try
		{
			SatoshiKeypair pair;
			for (Coin c : list)
			{
				pair = new SatoshiKeypair(c);
				System.out.println(c.NAME);
				System.out.println(pair.getBase58());
				System.out.println(pair.getWiF());
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		} 
		FileUtils.saveCoins(list);
		System.out.println("done");
		in.close(); */
		boolean run = true;
		FileUtils.initFiles();
		List<Coin> coinlist = loadHardcoded();
		List<SatoshiKeypair> keylist = new ArrayList<SatoshiKeypair>();
		if (FileUtils.loadCoins() != null)
			coinlist.addAll(FileUtils.loadCoins());
		coinlist = delDuplicates(coinlist);
		printCoins(coinlist);
		if (FileUtils.loadKeys() != null)
			keylist = FileUtils.loadKeys();
		coinlist = delDuplicates(coinlist);
		while (run)
		{
			System.out.println("Welcome to Allnie!");
			System.out.println("Please make a selection: ");
			System.out.println("a: List addresses");
			System.out.println("b: Add new address");
			System.out.println("c: Remove address");
			System.out.println("d: Add coin");
			System.out.println("e: Remove coin");
			run = false;
		}
		System.out.println("Printing test key...");
		System.out.println(keylist.get(0).getCoinname());
		System.out.println(keylist.get(0).getBase58());
		System.out.println(keylist.get(0).getWiF());
		//keylist.add(new SatoshiKeypair(coinlist.get(0)));
		//System.out.println("New test key added");
		FileUtils.saveCoins(coinlist);
		FileUtils.saveKeys(keylist);
	}
	
	public static List<Coin> loadHardcoded()
	{
		List<Coin> list = new ArrayList<Coin>();
		list.add(HardcodedCoins.BTC);
		list.add(HardcodedCoins.BCH);
		list.add(HardcodedCoins.LTC);
		list.add(HardcodedCoins.DOGE);
		return list;
	}
	
	public static List<Coin> delDuplicates(List<Coin> list)
	{
		List<Coin> seen = new ArrayList<Coin>();
		for (int i = 0; i < list.size(); i++)
		{
			if (!findByName(seen, list.get(i).NAME))
				seen.add(list.get(i));
		}
		return seen;
	}
	
	public static boolean findByName(List<Coin> list, String name)
	{
		for (Coin c : list)
		{
			if (c.NAME.trim().equals(name.trim()))
				return true;
		}
		return false;
	}
	
	public static void printCoins(List<Coin> list)
	{
		for (Coin c : list)
		{
			System.out.println(c.NAME);
		}
	}
}


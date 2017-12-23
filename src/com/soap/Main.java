package com.soap;

import java.util.ArrayList;
import java.util.List;

public class Main 
{
	public static void main(String[] args)
	{	
		FileUtils.initCoinsFile();
		List<Coin> list = loadHardcoded();
		if (FileUtils.loadCoins() != null)
			list.addAll(FileUtils.loadCoins());
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
}


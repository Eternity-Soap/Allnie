package com.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main 
{
	static List<Coin> coinlist;
	static List<SatoshiKeypair> keylist;
	static Scanner in;
	static
	{
		FileUtils.initFiles();
		keylist = new ArrayList<SatoshiKeypair>();
		coinlist = loadHardcoded();
		if (FileUtils.loadCoins() != null)
			coinlist.addAll(FileUtils.loadCoins());
		if (FileUtils.loadKeys() != null)
			keylist = FileUtils.loadKeys();
		keylist.add(null);
		coinlist = delDuplicates(coinlist);
		in = new Scanner(System.in);
	}
	
	public static void main(String[] args)
	{	
		
		boolean run = true;
		String choice = "";
		//printCoins(coinlist);
		System.out.println("Welcome to Allnie!");
		while (true)
		{
			System.out.println("Please make a selection: ");
			System.out.println("a: List addresses");
			System.out.println("b: Add new address");
			System.out.println("c: Remove address (keypair will be deleted)");
			System.out.println("d: Add coin");
			System.out.println("e: List coins");
			System.out.println("f: Remove coin");
			System.out.println("g: View private address (WiF)");
			System.out.println("x: Exit");
			choice = in.nextLine();
			if (choice.equalsIgnoreCase("a"))
				listAddrs();
			else if (choice.equalsIgnoreCase("b"))
			{
				addAddr();
				System.out.println("New address created.");
			}
			else if (choice.equalsIgnoreCase("c"))
				removeAddr();
			else if (choice.equalsIgnoreCase("d"))
				addCoin();
			else if (choice.equalsIgnoreCase("e"))
				printCoins();
			else if (choice.equalsIgnoreCase("f"))
				removeCoin();
			else if (choice.equalsIgnoreCase("g"))
				dumpPriv();
			else if (choice.equalsIgnoreCase("x"))
			{
				System.out.println("Halting...");
				break;
			}
		}
		keylist.remove(keylist.size() - 1);
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
	
	public static void printCoins()
	{
		int i = 1;
		for (Coin c : coinlist)
		{
			System.out.println(i + ": " + c.NAME);
			i++;
		}
	}
	
	public static void listAddrs()
	{
		int choice = 0;
		String input;
		System.out.println("For which coin would you like to see addresses?");
		for (int i = 0; i < coinlist.size(); i++)
		{
			System.out.println((i + 1)+" "+coinlist.get(i).NAME);
		}
		
		input = in.nextLine();
		choice = Integer.parseInt(input);
		choice--;
		int i = 1;
		for (SatoshiKeypair k : keylist)
		{
			if (k != null && k.getCoinname().equals(coinlist.get(choice).NAME))
			{
				System.out.println(i + ": " + k.getBase58());
				i++;
			}
		}
		if (i == 1)
			System.out.println("No addresses of this type.");
	}
	
	public static void removeAddr()
	{
		int coinchoice = 0;
		int keychoice = 0;
		int firstIndex = 0;
		String input;
		System.out.println("What kind of address would you like to remove?");
		for (int i = 0; i < coinlist.size(); i++)
		{
			System.out.println((i + 1)+": "+coinlist.get(i).NAME);
		}
		
		input = in.nextLine();
		coinchoice = Integer.parseInt(input);
		coinchoice--;
		for (int i = 0; i < keylist.size() - 1; i++)
		{
			if (keylist.get(i).getCoinname().equals(coinlist.get(coinchoice).NAME))
			{
				firstIndex = i;
				break;
			}
		}
		keychoice = firstIndex;
		System.out.println("Which address would you like to remove?");
		int i = 0;
		for (i = firstIndex; keylist.get(i) != null && keylist.get(i).getCoinname().equals(coinlist.get(coinchoice).NAME); i++)
		{
			System.out.println(i + 1 - firstIndex + ": " + keylist.get(i).getBase58());
		}
		if (i == 0)
		{
			System.out.println("Error: no addresses of this kind");
			return;
		}
		System.out.println(i + 1 - firstIndex + ": I've changed my mind.");
		input = in.nextLine();
		keychoice = Integer.parseInt(input);
		keychoice = keychoice - 1 + firstIndex;
		if (keychoice == i)
		{
			System.out.println("Key remove aborted.");
			return;
		}
		keylist.remove(keychoice);
		System.out.println("Key deleted.");
	}
	
	public static void addAddr()
	{
		int choice = 0;
		String input;
		System.out.println("What kind of address do you want to make?");
		for (int i = 0; i < coinlist.size(); i++)
		{
			System.out.println((i + 1)+": "+coinlist.get(i).NAME);
		}
		
		input = in.nextLine();
		choice = Integer.parseInt(input);
		choice--;
		if (keylist.size() == 1)
		{
			keylist.add(0, new SatoshiKeypair(coinlist.get(choice)));
			return;
		}
		for (int i = 0; i < keylist.size() - 1; i++)
		{
			if (i == keylist.size()-2)
			{
				keylist.add(keylist.size() - 2, new SatoshiKeypair(coinlist.get(choice)));
				return;
			}
			if (!keylist.get(i).getCoinname().equals(keylist.get(i+1).getCoinname()))
			{
				keylist.add(i+1, new SatoshiKeypair(coinlist.get(choice)));
				return;
			}
		}
	}
	
	public static void addCoin()
	{
		
		String name = "";
		String code = "";
		String input;
		byte pubprefix = 0x0;
		byte privprefix = 0x0;
		System.out.println("What is the name of the new coin?");
		name = in.nextLine();
		System.out.println("What is the currency code (i.e. BTC, LTC...)?");
		code = in.nextLine();
		System.out.println("In hexadecimal (do not include 0x), what is the public prefix for this coin (IT IS VERY IMPORTANT THAT THIS IS CORRECT)?");
		input = in.nextLine();
		pubprefix = (byte)Integer.parseInt(input, 16);
		System.out.println("In hexadecimal (do not include 0x), what is the private prefix for this coin (IT IS VERY IMPORTANT THAT THIS IS CORRECT)?");
		input = in.nextLine();
		privprefix = (byte)Integer.parseInt(input, 16);
		coinlist.add(new Coin(pubprefix, privprefix, name, code));
		System.out.println("New coin successfully added.");
	}
	
	public static void removeCoin()
	{
		
		int choice = 0;
		int i = 0;
		String input;
		System.out.println("Which coin would you like to remove?");
		for (i = 4; i < coinlist.size(); i++)
		{
			System.out.println(i - 3 + ": " + coinlist.get(i).NAME);
		}
		System.out.println(i - 3 + ": I've changed my mind.");
		input = in.nextLine();
		choice = Integer.parseInt(input);
		choice += 3;
		if (choice == i)
		{
			System.out.println("Coin remove aborted.");
			return;
		}
		coinlist.remove(choice);
		System.out.println("Coin removed.");
	}
	
	public static void dumpPriv()
	{
		int coinchoice = 0;
		int keychoice = 0;
		int firstIndex = 0;
		String input;
		System.out.println("What kind of key do you want to see?");
		for (int i = 0; i < coinlist.size(); i++)
		{
			System.out.println((i + 1)+": "+coinlist.get(i).NAME);
		}
		
		input = in.nextLine();
		coinchoice = Integer.parseInt(input);
		coinchoice--;
		for (int i = 0; i < keylist.size() - 1; i++)
		{
			if (keylist.get(i).getCoinname().equals(coinlist.get(coinchoice).NAME))
			{
				firstIndex = i;
				break;
			}
		}
		keychoice = firstIndex;
		System.out.println("For which public key would you like to see the private counterpart?");
		int i = 0;
		for (i = firstIndex; keylist.get(i) != null && keylist.get(i).getCoinname().equals(coinlist.get(coinchoice).NAME); i++)
		{
			System.out.println(i + 1 - firstIndex + ": " + keylist.get(i).getBase58());
		}
		if (i == 0)
		{
			System.out.println("Error: no addresses of this kind");
			return;
		}
		System.out.println(i + 1 - firstIndex + ": I've changed my mind.");
		input = in.nextLine();
		keychoice = Integer.parseInt(input);
		keychoice--;
		keychoice+=firstIndex;
		if (keychoice == i)
		{
			System.out.println("Key dump aborted.");
			return;
		}
		System.out.println("Private key for " + keylist.get(keychoice).getBase58() + " is " + keylist.get(keychoice).getWiF());
	}
}


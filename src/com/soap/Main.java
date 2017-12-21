package com.soap;


public class Main 
{
	public static void main(String[] args)
	{	
		try
		{
			Coin btc = new Coin((byte)0x00, (byte)0x80, "Bitcoin", "BTC");
			Coin fun = new Coin((byte)0x23, (byte)0xa3, "Funcoin", "FUN");
			SatoshiKeypair pair = new SatoshiKeypair(btc);
			for (int i = 0; i < 10000; i++)
			{
				System.out.println(pair.getBase58());
				System.out.println(pair.getWiF());
				pair = new SatoshiKeypair(fun);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		} 
		System.out.println("done");
	}
}

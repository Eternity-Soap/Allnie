package com.soap;

public abstract interface HardcodedCoins 
{
	public static final Coin BTC = new Coin((byte)0x00, (byte)0x80, "Bitcoin", "BTC");
	public static final Coin LTC = new Coin((byte)0x30, (byte)0xb0, "Litecoin", "LTC");
	public static final Coin BCH = new Coin((byte)0x00, (byte)0x80, "Bitcoin Cash", "BCH");
	public static final Coin DOGE = new Coin ((byte)0x1e, (byte)0x9e, "Dogecoin", "DOGE");
}

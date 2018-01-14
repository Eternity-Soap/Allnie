package com.soap;

public class Address 
{
	boolean isSatoshi;
	String coinName;
	byte[] pubKey;
	byte[] privKey;
	String pubAddr; //Only used if coin is not a btc derivative
	String label;
}

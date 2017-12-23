/*
 * Very simple class that represents a coin. It stores the necessary information to generate a key pair
 */

package com.soap;

public class Coin 
{
	private final byte PUBLIC_PREFIX;
	private final byte PRIVATE_PREFIX;
	public final String NAME;
	public final String CODE;
	
	public Coin(byte pub, byte priv, String name, String code)
	{
		PUBLIC_PREFIX = pub;
		PRIVATE_PREFIX = priv;
		NAME = name;
		CODE = code;
	}
	
	public byte getPub()
	{
		return PUBLIC_PREFIX;
	}
	
	public byte getPriv()
	{
		return PRIVATE_PREFIX;
	}
}

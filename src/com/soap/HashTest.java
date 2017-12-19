/*
 * Class tests BTC address generation using the sample provided on the bitcoin wiki
 */

package com.soap;

import java.nio.charset.StandardCharsets;

import org.bouncycastle.util.encoders.Hex;

public class HashTest 
{
	public static void main(String[] args)
	{	
		try
		{
			String publicTestKey = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
			System.out.println(Hex.toHexString(Crypto.sha256String(publicTestKey)));
			System.out.println(publicTestKey.getBytes(StandardCharsets.UTF_8).length);
			byte[] byteKey = new byte[65];
			//Converts key represented in string to actual 65 byte public key
			for (int i = 0; i < 65; i++)
			{
				byteKey[i] = (byte) Integer.parseInt(publicTestKey.substring(2*i, (2*i)+2), 16);
				//System.out.println(String.format("%x", byteKey[i]));
			}
			System.out.println(Hex.toHexString(byteKey));
			System.out.println(Hex.toHexString(Crypto.sha256bytes(byteKey)));
			byte[] shahash1 = Crypto.sha256bytes(byteKey);
			byte[] ripe160hash = Crypto.ripemd160hash(shahash1);
			byte[] newripehash = Crypto.addVer(ripe160hash, (byte)0x00);
			System.out.println(Hex.toHexString(newripehash));
			byte[] shahash2 = Crypto.sha256bytes(newripehash);
			byte[] shahash3 = Crypto.sha256bytes(shahash2);
			System.out.println(Hex.toHexString(shahash2));
			System.out.println(Hex.toHexString(shahash3));
			byte[] binaryaddr = Crypto.binaryAddress(newripehash, shahash3);
			System.out.println(Hex.toHexString(binaryaddr));
			String btcaddr = Base58.encode(binaryaddr);
			System.out.println(btcaddr);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		} 
	}
}

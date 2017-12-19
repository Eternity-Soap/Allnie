package com.soap;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

public class SatoshiKeypair 
{
	static
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	public SatoshiKeypair()
	{
		try
		{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDsA", "BC");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
			keyGen.initialize(ecSpec, new SecureRandom());
			KeyPair pair = keyGen.generateKeyPair();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

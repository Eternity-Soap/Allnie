package com.soap;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;



public class Main 
{
	public static void main(String[] args)
	{	
		try
		{
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		} 
	}
}

package com.soap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.util.encoders.Hex;

public class Crypto
{	
	
	public static KeyPair genKeyPair()
	{
		return null;
	}
	
	public static byte[] getHexEncodedPublicKey(PublicKey publicKey) throws IOException, InvalidKeyException 
	{
	    ECPublicKeyParameters ecPublicKeyParameters
	            = (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(publicKey);
	    byte[] encoded = ecPublicKeyParameters.getQ().getEncoded(false);
	    return encoded;
	}
	
	public static byte[] ripemd160hash(byte[] data) throws IOException
	{
		RIPEMD160Digest d = new RIPEMD160Digest();
        d.update (data, 0, data.length);
        byte[] o = new byte[d.getDigestSize()];
        d.doFinal (o, 0);
        Hex.encode (o);
        //System.out.println();
        return o;
	}
	
	public static byte[] addVer(byte[] input, byte ver)
	{
		byte[] newbyte = new byte[input.length+1];
		newbyte[0] = ver;
		for (int i = 0; i < input.length; i++)
		{
			newbyte[i+1] = input[i];
		}
		//System.out.println(Hex.toHexString(newbyte));
		return newbyte;
	}
	
	public static byte[] sha256String(String data) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(data.getBytes(StandardCharsets.UTF_8));
	}
	
	public static byte[] sha256bytes(byte[] data) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(data);
	}
	
	public static byte[] binaryAddress(byte[] extended, byte[] second256)
	{
		byte[] binaryAddress = new byte[25];
		for (int i = 0; i < 21; i++)
		{
			binaryAddress[i] = extended[i];
		}
		binaryAddress[21] = second256[0];
		binaryAddress[22] = second256[1];
		binaryAddress[23] = second256[2];
		binaryAddress[24] = second256[3];
		return binaryAddress;
	}
}

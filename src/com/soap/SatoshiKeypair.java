package com.soap;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.util.encoders.Hex;
import static com.google.common.base.Preconditions.checkArgument;

public class SatoshiKeypair 
{
	public static final ECDomainParameters CURVE;
	private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
	private static final SecureRandom secureRandom;
	static
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                CURVE_PARAMS.getH());
		secureRandom = new SecureRandom();
	}
	
	AsymmetricCipherKeyPair pair;
	private byte[] hexPublicKey = new byte[65];
	private String base58addr;
	private byte[] binaryaddr;
	private byte[] hexPrivateKey = new byte[32];
	private String privateWiF;
	
	public SatoshiKeypair(Coin coin)
	{
		try
		{
			ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
			ECKeyGenerationParameters keygenParams = new ECKeyGenerationParameters(CURVE, secureRandom);
			keyGen.init(keygenParams);
			pair = keyGen.generateKeyPair();
			ECPrivateKeyParameters privParams = (ECPrivateKeyParameters)pair.getPrivate();
			ECPublicKeyParameters pubParams = (ECPublicKeyParameters) pair.getPublic();
			hexPrivateKey = bigIntegerToBytes(privParams.getD(), 32);
			hexPublicKey = pubParams.getQ().getEncoded(false);
			toAddress(coin.getPub());
			toWiF(coin.getPriv());
		}
		catch (Exception e)
		{
			System.out.println(e.getClass());
		}
	}
	
	public static byte[] bigIntegerToBytes(BigInteger b, int numBytes) //Taken from Bitcoinj
	{
        checkArgument(b.signum() >= 0, "b must be positive or zero");
        checkArgument(numBytes > 0, "numBytes must be positive");
        byte[] src = b.toByteArray();
        byte[] dest = new byte[numBytes];
        boolean isFirstByteOnlyForSign = src[0] == 0;
        int length = isFirstByteOnlyForSign ? src.length - 1 : src.length;
        checkArgument(length <= numBytes, "The given number does not fit in " + numBytes);
        int srcPos = isFirstByteOnlyForSign ? 1 : 0;
        int destPos = numBytes - length;
        System.arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }
	
	private void toAddress(byte ver) throws NoSuchAlgorithmException, IOException
	{
		byte[] shahash1 = Crypto.sha256bytes(hexPublicKey);
		byte[] ripe160hash = Crypto.ripemd160hash(shahash1);
		byte[] newripehash = Crypto.addVer(ripe160hash, ver);
		byte[] shahash2 = Crypto.sha256bytes(newripehash);
		byte[] shahash3 = Crypto.sha256bytes(shahash2);
		binaryaddr = Crypto.binaryAddress(newripehash, shahash3);
		base58addr = Base58.encode(binaryaddr);
	}
	
	private void toWiF(byte ver) throws NoSuchAlgorithmException
	{
		byte[] withVer = Crypto.addVer(hexPrivateKey, ver);
		byte[] shahash1 = Crypto.sha256bytes(withVer);
		byte[] shahash2 = Crypto.sha256bytes(shahash1);
		byte[] rawWiF = new byte[37];
		for (int i = 0; i < 33; i++)
		{
			rawWiF[i] = withVer[i];
		}
		rawWiF[33] = shahash2[0];
		rawWiF[34] = shahash2[1];
		rawWiF[35] = shahash2[2];
		rawWiF[36] = shahash2[3];
		privateWiF = Base58.encode(rawWiF);
	}
	
	public String getBase58()
	{
		return base58addr;
	}
	
	public String getWiF()
	{
		return privateWiF;
	}
}

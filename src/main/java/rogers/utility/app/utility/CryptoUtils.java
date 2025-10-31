package rogers.utility.app.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class CryptoUtils {

		private static final String ALGORITHM = "AES/GCM/NoPadding";
	    public static String encrypt(String valueToEnc) throws Exception {
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGORITHM);
	        byte[] iv = getNonceKeyValue().getBytes();
	        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
	        c.init(Cipher.ENCRYPT_MODE, key,parameterSpec);
	        byte[] encValue = c.doFinal(valueToEnc.getBytes());
	        String encryptedValue = new Base64().encodeAsString(encValue);
	        return encryptedValue;
	    }
	 
	    public static String decrypt(String encryptedValue) throws Exception {
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGORITHM);
	        byte[] iv = getNonceKeyValue().getBytes();
	        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
	       // SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
	        c.init(Cipher.DECRYPT_MODE, key,parameterSpec);
	        byte[] decordedValue 
	                =  new Base64().decode(encryptedValue);
	        byte[] decValue = c.doFinal(decordedValue);
	        String decryptedValue = new String(decValue);
	        return decryptedValue;
	    }
	 
	    private static Key generateKey() throws Exception {
	    	final byte[] keyValue = getSecretKeyValue().getBytes();
	        Key key = new SecretKeySpec(keyValue, "AES");
	        // SecretKeyFactory keyFactory 
	        //              = SecretKeyFactory.getInstance(ALGORITHM);
	        // key = keyFactory.generateSecret(new DESKeySpec(keyValue));
	        return key;
	    }
	    
	    private static String getSecretKeyValue(){
	    	String keyValue="";
	    	InputStream input=null;
	    	try {
		    	Properties properties = new Properties();
		    	 input = new FileInputStream("applicationruntime.properties");
				properties.load(input);
				keyValue= properties.getProperty("ngpp.secret.key");
				System.out.println("key found:");
				
			} catch (IOException e) {
				System.out.println("key not found:"+e);
			}finally{
				if(input!=null){
					try {
						input.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
			}
	    	return keyValue;
	    }
	    private static String getNonceKeyValue(){
	    	String keyValue="";
	    	InputStream input=null;
	    	try {
		    	Properties properties = new Properties();
		    	 input = new FileInputStream("applicationruntime.properties");
				properties.load(input);
				keyValue= properties.getProperty("ngpp.nonce.key");
				System.out.println("key found:");
				
			} catch (IOException e) {
				System.out.println("key not found:"+e);
			}finally{
				if(input!=null){
					try {
						input.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
			}
	    	return keyValue;
	    }
		public static void main(String[] args) {
			
	    	try {
				System.out.println(CryptoUtils.decrypt("Kdc89ir41C5i6WZ9fkK59HCXTWU//zqW"));
				System.out.println(CryptoUtils.decrypt(CryptoUtils.encrypt("tarini")));
			} catch (Exception e) {
				System.out.println("Exception:"+e);
			}
		}
}
	

package rogers.utility.app.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class ReloadablePropertySourceFactory {// extends DefaultPropertySourceFactory {

	// public PropertySource<?> createPropertySource(String s, EncodedResource
	// encodedResource) throws IOException {
	// Resource internal = encodedResource.getResource();

	public static Properties loader(File file) {

		Properties prop = new Properties();
		Properties returnPropeties = new Properties();
		FileInputStream file1 = null;
		FileOutputStream file2 = null;
		try {
			file1 = new FileInputStream(file);
			prop.load(file1);
			returnPropeties.putAll(prop);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block

		}
		finally {
			try {
			if(file1 != null) {
				file1.close();
			}
			}
			catch (IOException e2) {
				// TODO Auto-generated catch block

			}
		}
		boolean flag = false;

		for (Object keyName : Collections.list(prop.propertyNames())) {
			String key = (String) keyName;
			if (key.contains("password")) {
				String password = prop.getProperty(key);
				try {
					String dec = CryptoUtils.decrypt(password);
					//System.out.println("Already Encryoted  " + password);
					//returnPropeties.setProperty(key, dec);
				} catch (Exception e) {
					//System.out.println("Encryting " + password);

					try {
						String enc = CryptoUtils.encrypt(password);
						 prop.setProperty(key, enc);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						
					}

					flag = true;

				}
			}

		}

		if (flag) {
			try {
				file2 = new FileOutputStream(file);
				prop.save(file2, "Encrypted");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
			}
			finally {
				try {
				if (file2 != null) {
					file2.close();
				}
				}catch (IOException e2) {
					// TODO Auto-generated catch block

				}
			}
			
		} else {

		}

		
		return returnPropeties;

		
	}

}
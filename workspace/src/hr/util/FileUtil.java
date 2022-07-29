package hr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {
	private static final Logger log = Logger.getLogger(FileUtil.class);
	
	public static final String readFile(String pathFile) {
		String result = "";
		byte[] data = readFileByteArray(pathFile);
		if (data != null) {
			try {
				result = new String(data, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}
        
        return result;
	}
	
	public static final byte[] readFileByteArray(String pathFile) {
		byte[] data = null;
        InputStream is = null;
        try {
        	File file = new File(pathFile);
        	is = new FileInputStream(file);
        	data = new byte[(int)file.length()];
        	is.read(data);
        }
        catch(IOException e) {
        	log.debug(e);
        } finally {
        	try {
				is.close();
			} catch (IOException e) {
				log.error(e);
			}
        }
        
        return data;
	}
	
    @SuppressWarnings("rawtypes")
	public static <T> List<Class> getClasses(String packageName, String endWith, Class<T> ofBaseLiteralClass) throws IOException, ClassNotFoundException {
    	List<Class> classes = new ArrayList<Class>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName, endWith, ofBaseLiteralClass));
        }
    	
        return classes;
    }
    @SuppressWarnings("rawtypes")
	private static <T> List<Class> findClasses(File directory, String packageName, String endWith, Class<T> ofBaseLiteralClass) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
        	String fileName = file.getName();
            if (file.isDirectory()) {
                assert !fileName.contains(".");
                classes.addAll(findClasses(file, new StringBuilder(packageName).append(".").append(fileName).toString(), endWith, ofBaseLiteralClass));
            } else if (fileName.endsWith(".class")) {
            	if (!fileName.endsWith(endWith)) {
            		continue;
            	}
            	fileName = fileName.substring(0, fileName.length() - 6);
            	String className = new StringBuilder(packageName).append('.').append(fileName).toString();
            	if (ofBaseLiteralClass.isAssignableFrom(Class.forName(className))) {
	        		Class<?> literalElement = Class.forName(className).asSubclass(ofBaseLiteralClass);
	        		classes.add(literalElement);
            	}
            }
        }
        
        return classes;
    }
}

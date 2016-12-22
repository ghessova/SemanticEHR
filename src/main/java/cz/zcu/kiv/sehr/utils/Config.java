package cz.zcu.kiv.sehr.utils;

import cz.zcu.kiv.sehr.database.DBConnector;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Created by ondraprazak on 19.12.16.
 */
public class Config {

    public static final String CONFIG_NAME = "configs/config.properties"; //$NON-NLS-1$

    private static final Properties properties = new Properties();

    //constants
    public static final String DEFINITIONS = "definitions"; //name of collection to store archetypes definitions
    public static final String REQUESTS = "requests"; //name of collection to store archetypes requests
    public static final String DOCUMENTS = "documents"; //name of collection to store user data
    public static final String USERS = "users"; //name of collection to store existing user's

    private static Writer writer;

    //private static OutputStream out;
    static
    {
        try {
            properties.load(new FileReader(CONFIG_NAME));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Config() {
    }

    public static void loadProperties(String file)
    {
        properties.clear();
        try {
            properties.load(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets value of the property with specific key
     * @param key the key
     * @return value of the property
     */
    public static String getString(String key) {
        String ret = properties.getProperty(key);
        return ret;
    }

    public static Integer getInteger(String key)
    {
        String value = getString(key);
        if(value == null)
            return null;
        return Integer.parseInt(value);
    }

    public static Double getDouble(String key)
    {
        String value = getString(key);
        if(value == null)
            return null;
        return Double.parseDouble(value);
    }

    public static Boolean getBoolean(String key)
    {
        return getString(key) != null && !"0".equals(getString(key));
    }

    public static void setString(String key, String value)
    {
        setString(properties, CONFIG_NAME, key, value);
    }

    private static void setString(Properties properties, String file, String key, String value)
    {
        try {
            properties.setProperty(key, value);
            Writer writer = new FileWriter(file);
            //out = new FileOutputStream(XML_CONFIG_NAME);
            properties.store(writer, null);
            //properties.storeToXML(out, null, "UTF-8");
            writer.close();
            //out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void setBoolean(String key, boolean value)
    {
        setString(key, value ? "1": "0");
    }

    public static Object getInstance(String className)
    {
        Object obj = null;
        try {
            Class<?> instanceClass = Class.forName(className);
            obj = instanceClass.getDeclaredMethod("getInstance").invoke(null);
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static DBConnector getDBC()
    {
        return (DBConnector)getInstance(getString("DBConnector"));
    }

    public static String getDBAddress()
    {
        return getString("DBAddress");
    }

    public static String getDBName()
    {
        return getString("DBName");
    }


}

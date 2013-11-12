package pgDev.bukkit.SimpleCommandSigns;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.NoSuchElementException;
import java.util.Properties;

public class SimpleCommandSignsConfig {
	private Properties properties;
	private final SimpleCommandSigns plugin;
	public boolean upToDate = true;
	
	// List of Config Options
	public boolean signAutoLock;
	public String commandSignIdentifier;
	public boolean callPreprocess;
	
	
	public SimpleCommandSignsConfig(Properties p, final SimpleCommandSigns plugin) {
        properties = p;
        this.plugin = plugin;
        
        // Grab values here.
        signAutoLock = getBoolean("autoSignLock", false);
        commandSignIdentifier = getString("csActivator", "[SCS]").trim();
        callPreprocess = getBoolean("calllPreprocess", true);
	}
	
	
	// Value obtaining functions down below
	public int getInt(String label, int thedefault) {
		String value;
        try {
        	value = getString(label);
        	return Integer.parseInt(value);
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public double getDouble(String label) throws NoSuchElementException {
        String value = getString(label);
        return Double.parseDouble(value);
    }

    public boolean getBoolean(String label, boolean thedefault) {
    	String values;
        try {
        	values = getString(label);
        	return Boolean.valueOf(values).booleanValue();
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public String getString(String label) throws NoSuchElementException {
        String value = properties.getProperty(label);
        if (value == null) {
        	upToDate = false;
            throw new NoSuchElementException("Config did not contain: " + label);
        }
        return value;
    }
    
    public String getString(String label, String thedefault) {
    	String value;
    	try {
        	value = getString(label);
        } catch (NoSuchElementException e) {
        	value = thedefault;
        }
        return value;
    }
    
    
    // Config creation method
    public void createConfig() {
    	try{
    		File configfile = new File(plugin.pluginMainDir);
    		if(!configfile.exists()) {
    			configfile.mkdirs();
    		}
    		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plugin.pluginConfigLocation)));
    		out.write("#\r\n");
    		out.write("# SimpleCommandSigns Configuration\r\n");
    		out.write("#\r\n");
    		out.write("\r\n");
    		out.write("# LWC Sign Automatic Locking\r\n");
    		out.write("#	If you have the LightWeight Chest plugin installed, you can\r\n");
    		out.write("#	have your command signs automatically protected after\r\n");
    		out.write("#	you have created them.\r\n");
    		out.write("autoSignLock=" + signAutoLock + "\r\n");
    		out.write("\r\n");
    		out.write("# Command Sign Identifier String\r\n");
    		out.write("#	This is the line that if you place at the top of your sign will\r\n");
    		out.write("#	convert it into a command sign.\r\n");
    		out.write("#	Warning: Changing this after having placed command signs\r\n");
    		out.write("#	will disable previous signs.\r\n");
    		out.write("csActivator=" + commandSignIdentifier + "\r\n");
    		out.write("\r\n");
    		out.write("# PlayerCommandPreprocessEvent\r\n");
    		out.write("#	With this enabled, the player will call Bukkit's\r\n");
    		out.write("#	PlayerCommandPreprocessEvent before running any command.\r\n");
    		out.write("#	This will allow other plugins to cancel the command\r\n");
    		out.write("#	and/or change the content of the command.\r\n");
    		out.write("callPreprocess=" + callPreprocess + "\r\n");
    		out.close();
    	} catch (Exception e) {
    		System.out.println(e);
    		// Not sure what to do? O.o
    	}
    }
}

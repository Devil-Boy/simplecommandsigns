package pgDev.bukkit.SimpleCommandSigns;

import org.bukkit.block.*;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

/**
 * SimpleCommandSigns block listener
 * @author DevilBoy
 */
public class SimpleCommandSignsBlockListener implements Listener {
    private final SimpleCommandSigns plugin;

    public SimpleCommandSignsBlockListener(final SimpleCommandSigns plugin) {
        this.plugin = plugin;
    }

    //put all Block related code here
    
    /* Useless Code
    public void onBlockPlace(BlockPlaceEvent event) {
    	Block currentBlock = event.getBlock();
    	Player thePlayer = event.getPlayer();
    	if (plugin.debug) {
			System.out.println("A block was placed by " + thePlayer.getDisplayName());
		}
    	if (isSign(currentBlock)) {
    		if (plugin.debug) {
    			System.out.println("and it's a sign!");
    		}
    		Sign theSign = (Sign)currentBlock.getState();
    		if (theSign.getLine(0).equals("[SCS]")) {
    			if (plugin.debug) {
        			System.out.println("and now it's a Command Sign!");
        		}
    			theSign.setLine(0, ChatColor.GREEN + theSign.getLine(0));
    		}
    	}
    }*/
    
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
    	if (event.getLine(0).equalsIgnoreCase(plugin.pluginSettings.commandSignIdentifier) && plugin.hasPermissions(event.getPlayer(), "scsigns.create")) {
			event.setLine(0, ChatColor.GREEN + plugin.pluginSettings.commandSignIdentifier);
			
			if (plugin.debug) { // Some debug code
				System.out.println("CommandSign created!");
    		}

			if(plugin.pluginSettings.signAutoLock && plugin.lwc != null && plugin.hasPermissions(event.getPlayer(), "scsigns.autolock")) {
				Block tS = event.getBlock();
				int blockId = tS.getTypeId();
				int type = 0;
				String world = tS.getWorld().getName();
				String owner = event.getPlayer().getName();
				String password = "";
				int x = tS.getX();
				int y = tS.getY();
				int z = tS.getZ();
				type = com.griefcraft.model.ProtectionTypes.PRIVATE;
				plugin.lwc.getPhysicalDatabase().registerProtection(blockId, type, world, owner, password, x, y, z);
				
				if (plugin.debug) { // Some debug code
					System.out.println("CommandSign locked!");
	    		}
			}
		}
    }
    
    /* Can be used later on
    public void onBlockDamage(BlockDamageEvent event) {
    	Block currentBlock = event.getBlock();
    	if (isSign(currentBlock)) {
    		Sign theSign = (Sign)currentBlock.getState();
    		if (theSign.getLine(0).equals(ChatColor.GREEN + "[SCS]")) {
    			event.getPlayer().performCommand(theSign.getLine(1) + theSign.getLine(2) + theSign.getLine(3));
    		}
    	}
    }*/
    
}

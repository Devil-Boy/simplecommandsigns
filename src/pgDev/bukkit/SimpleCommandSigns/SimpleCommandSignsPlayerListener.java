package pgDev.bukkit.SimpleCommandSigns;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Handle events for all Player related events
 * @author DevilBoy
 */
public class SimpleCommandSignsPlayerListener implements Listener {
    private final SimpleCommandSigns plugin;

    public SimpleCommandSignsPlayerListener(SimpleCommandSigns instance) {
        plugin = instance;
    }

    //Insert Player related code here
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().hasPermission("scsigns.use")) {
    		if (plugin.debug) { // Some debug code
				System.out.println(event.getPlayer().getName() + " right-clicked a block of type " + event.getClickedBlock().getType().toString() + "!");
    		}
    		
	    	if (event.getClickedBlock() != null && plugin.isSign(event.getClickedBlock())) {
	    		if (plugin.debug) { // Some debug code
					System.out.println("The block was a sign!");
	    		}
	    		
	    		Sign theSign = (Sign)event.getClickedBlock().getState();
	    		if (theSign.getLine(0).equals(ChatColor.GREEN + plugin.pluginSettings.commandSignIdentifier)) {
	    			if (plugin.debug) { // Some debug code
	    				System.out.println("It was a CommandSign!");
	        		}
	    			String commandString = theSign.getLine(1) + theSign.getLine(2) + theSign.getLine(3);
	    			if (commandString.startsWith("/")) {
	    				commandString = commandString.substring(1);
	    			}
	    			
	    			commandString = commandString.replace("%p", event.getPlayer().getName());
	    			
	    			if (plugin.pluginSettings.callPreprocess) {
	    				PlayerCommandPreprocessEvent pcpe = new PlayerCommandPreprocessEvent(event.getPlayer(), commandString);
		    			plugin.getServer().getPluginManager().callEvent(pcpe);
		    			if (!pcpe.isCancelled()) {
		    				event.getPlayer().performCommand(pcpe.getMessage());
		    			}
	    			} else {
	    				event.getPlayer().performCommand(commandString);
	    			}
	    		}
	    	}
    	}
    }
}


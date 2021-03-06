package com.miningbear.claimthatchunk.commands.user;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;

import com.miningbear.claimthatchunk.lib.Constants;

public class CommandRemoveMember extends CommandBase {
	private List aliases;
	
	public CommandRemoveMember() {
		this.aliases = new ArrayList();
		this.aliases.add("removemember");
		this.aliases.add("remmem");
	}
	
	@Override
	public String getCommandName() {
		return "removemember";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/removemember <name>";
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if ( astring.length == 0 ) {
			icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Invalid arguments! Usage: /removemember <name>"));
			return;
		}
		
		EntityPlayer player;
        
        if(icommandsender instanceof EntityPlayer){
                player = (EntityPlayer)icommandsender;
                
                ChunkCoordinates coords = icommandsender.getPlayerCoordinates();
        		double chunkX = Math.floor(coords.posX / 16);
        		double chunkZ = Math.floor(coords.posZ / 16);
        
        		if ( Constants.data.removeMember( chunkX, chunkZ, player, astring[0] ) ) {
        			icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Successfully removed " + astring[0] + " from the chunk!"));
        		} else {
        			icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + Constants.data.getReason()));
        		}
        }
        else {
        	icommandsender.addChatMessage(new ChatComponentText("This command can only be done via a player!"));
        }
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return astring.length == 1 ? getListOfStringsMatchingLastWord(astring, this.getListOfPlayerUsernames()) : null;
	}
	
	protected String[] getListOfPlayerUsernames()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

	@Override
	public boolean isUsernameIndex(String[] astring, int id) {
		return false;
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
}
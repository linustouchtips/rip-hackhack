package me.zoom.hackhack.command;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;

public class CommandManager {
    private static ArrayList<Command> commands;
    boolean b;

    public static void initCommands(){
        commands = new ArrayList<>();
    }

    public static void addCommand(Command c){
        commands.add(c);
    }

    public static ArrayList<Command> getCommands(){
        return commands;
    }

    public void callCommand(String input){
        String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by every space if it isn't surrounded by quotes // credit 086/KAMI
        String command = split[0];
        String args = input.substring(command.length()).trim();
        b = false;
        commands.forEach(c ->{
            for(String s : c.getAlias()) {
                if (s.equalsIgnoreCase(command)) {
                    b = true;
                    try {
                        c.onCommand(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                    } catch (Exception e) {
                        Command.sendClientMessage(ChatFormatting.GRAY + c.getSyntax());
                    }
                }
            }
        });
        if(!b) Command.sendClientMessage(ChatFormatting.GRAY + "Unknown command!");
    }
}

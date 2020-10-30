package me.zoom.hackhack.module.modules.client;

import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;

public class CommandColor extends Module {
    public CommandColor() {
        super("CommandColor", Category.Client);
    }

    public static Setting.Mode CommandColor;
    public static Setting.Mode BracketColor;

    public void setup() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("Black");
        colors.add("Dark Green");
        colors.add("Dark Red");
        colors.add("Gold");
        colors.add("Dark Gray");
        colors.add("Green");
        colors.add("Red");
        colors.add("Yellow");
        colors.add("Dark Blue");
        colors.add("Dark Aqua");
        colors.add("Dark Purple");
        colors.add("Gray");
        colors.add("Blue");
        colors.add("Aqua");
        colors.add("Light Purple");
        colors.add("White");
        ArrayList<String> brackets = new ArrayList<>();
        brackets.add("Black");
        brackets.add("Dark Green");
        brackets.add("Dark Red");
        brackets.add("Gold");
        brackets.add("Dark Gray");
        brackets.add("Green");
        brackets.add("Red");
        brackets.add("Yellow");
        brackets.add("Dark Blue");
        brackets.add("Dark Aqua");
        brackets.add("Dark Purple");
        brackets.add("Gray");
        brackets.add("Blue");
        brackets.add("Aqua");
        brackets.add("Light Purple");
        brackets.add("White");
        CommandColor = registerMode("Text", "Color", colors, "Light Purple");
        BracketColor = registerMode("Brackets", "BracketColor", brackets, "Light Purple");
    }

    public static ChatFormatting getTextColor(){
        if (CommandColor.getValue().equalsIgnoreCase("Black")){
            return ChatFormatting.BLACK;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Dark Green")){
            return ChatFormatting.DARK_GREEN;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Dark Red")){
            return ChatFormatting.DARK_RED;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Gold")){
            return ChatFormatting.GOLD;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Dark Gray")){
            return ChatFormatting.DARK_GRAY;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Green")){
            return ChatFormatting.GREEN;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Red")){
            return ChatFormatting.RED;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Yellow")){
            return ChatFormatting.YELLOW;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Dark Blue")){
            return ChatFormatting.DARK_BLUE;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Dark Aqua")){
            return ChatFormatting.DARK_AQUA;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Dark Purple")){
            return ChatFormatting.DARK_PURPLE;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Gray")){
            return ChatFormatting.GRAY;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Blue")){
            return ChatFormatting.BLUE;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Light Purple")){
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (CommandColor.getValue().equalsIgnoreCase("White")){
            return ChatFormatting.WHITE;
        }
        if (CommandColor.getValue().equalsIgnoreCase("Aqua")){
            return ChatFormatting.AQUA;
        }
        return null;
    }

    public static ChatFormatting getBrackets(){
        if (BracketColor.getValue().equalsIgnoreCase("Black")){
            return ChatFormatting.BLACK;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Dark Green")){
            return ChatFormatting.DARK_GREEN;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Dark Red")){
            return ChatFormatting.DARK_RED;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Gold")){
            return ChatFormatting.GOLD;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Dark Gray")){
            return ChatFormatting.DARK_GRAY;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Green")){
            return ChatFormatting.GREEN;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Red")){
            return ChatFormatting.RED;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Yellow")){
            return ChatFormatting.YELLOW;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Dark Blue")){
            return ChatFormatting.DARK_BLUE;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Dark Aqua")){
            return ChatFormatting.DARK_AQUA;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Dark Purple")){
            return ChatFormatting.DARK_PURPLE;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Gray")){
            return ChatFormatting.GRAY;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Blue")){
            return ChatFormatting.BLUE;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Light Purple")){
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (BracketColor.getValue().equalsIgnoreCase("White")){
            return ChatFormatting.WHITE;
        }
        if (BracketColor.getValue().equalsIgnoreCase("Aqua")){
            return ChatFormatting.AQUA;
        }
        return null;
    }

}

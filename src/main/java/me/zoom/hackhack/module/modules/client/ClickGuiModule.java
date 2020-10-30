package me.zoom.hackhack.module.modules.client;

import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module{
    public ClickGuiModule() {
        super("ClickGUI", Category.Client);
        setDrawn(false);
        setBind(Keyboard.KEY_P);
    }

    public static Setting.Integer opacity;
    public static Setting.Integer red;
    public static Setting.Integer green;
    public static Setting.Integer blue;
    public static Setting.Integer fontRed;
    public static Setting.Integer fontGreen;
    public static Setting.Integer fontBlue;


    public void setup(){
        red = registerInteger("Red", "RedHUD", 255, 0, 255);
        green = registerInteger("Green", "GreenHUD", 255, 0, 255);
        blue = registerInteger("Blue", "BlueHUD", 255, 0, 255);
        fontRed = registerInteger("Font Red", "RedFONT", 255, 0, 255);
        fontGreen = registerInteger("Font Green", "GreenFONT", 255, 0, 255);
        fontBlue = registerInteger("Font Blue", "BlueFONT", 255, 0, 255);
        opacity = registerInteger("Opacity", "Opacity", 200,50,255);
    }

    public void onEnable(){
        mc.displayGuiScreen(HackHack.getInstance().clickGUI);
        disable();
    }
}

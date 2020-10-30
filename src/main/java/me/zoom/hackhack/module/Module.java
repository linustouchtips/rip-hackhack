package me.zoom.hackhack.module;

import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.event.events.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    String name;
    Category category;
    int bind;
    boolean enabled;
    boolean drawn;
    String description;

    public Module(String n, Category c) {
        name = n;
        category = c;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;
        setup();
    }

    public Module(String n, String desc, Category c) {
        name = n;
        category = c;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;
        this.description = desc;
        setup();
    }


    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category c){
        category = c;
    }

    public int getBind(){
        return bind;
    }

    public void setBind(int b){
        bind = b;
    }

    protected void onEnable(){ }

    protected void onDisable(){ }

    public void onUpdate(){ }

    public void onRender(){}

    public void onWorldRender(RenderEvent event) {}

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean e){
        enabled = e;
    }

    public void enable(){
        setEnabled(true);
        onEnable();
    }

    public void disable(){
        setEnabled(false);
        onDisable();
    }

    public void toggle(){
        if(isEnabled()) {
            disable();
        }
        else if(!isEnabled()){
            enable();
        }
    }

    public String getHudInfo(){
        return "";
    }

    public void setup(){}

    public boolean isDrawn(){
        return drawn;
    }

    public void setDrawn(boolean d){
        drawn = d;
    }

    protected Setting.Integer registerInteger(final String name, final String configname, final int value, final int min, final int max) {
        final Setting.Integer s = new Setting.Integer(name, configname, this, getCategory(), value, min, max);
        HackHack.getInstance().settingsManager.addSetting(s);
        return s;
    }

    protected Setting.Double registerDouble(final String name, final String configname, final double value, final double min, final double max) {
        final Setting.Double s = new Setting.Double(name, configname, this, getCategory(), value, min, max);
        HackHack.getInstance().settingsManager.addSetting(s);
        return s;
    }

    protected Setting.Boolean registerBoolean(final String name, final String configname, final boolean value) {
        final Setting.Boolean s = new Setting.Boolean(name, configname, this, getCategory(), value);
        HackHack.getInstance().settingsManager.addSetting(s);
        return s;
    }

    protected Setting.Mode registerMode(final String name, final String configname, final List<String> modes, final String value) {
        final Setting.Mode s = new Setting.Mode(name, configname, this, getCategory(), modes, value);
        HackHack.getInstance().settingsManager.addSetting(s);
        return s;
    }

    public enum Category{
        Combat,
        Player,
        Movement,
        Chat,
        Misc,
        Render,
        Client,
        HudEditor
    }
}

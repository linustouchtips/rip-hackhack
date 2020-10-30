package me.zoom.hackhack.util.config;

import me.zoom.hackhack.util.config.SaveConfiguration;
import me.zoom.hackhack.HackHack;

public class Stopper extends Thread {

    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){

        HackHack.getInstance().saveModules.saveModules();

        SaveConfiguration.saveBinds();
        SaveConfiguration.saveDrawn();
        SaveConfiguration.saveEnabled();
        SaveConfiguration.saveEnemies();
        SaveConfiguration.saveFont();
        SaveConfiguration.saveFriends();
        SaveConfiguration.saveGUI();
        SaveConfiguration.saveMacros();
        SaveConfiguration.saveMessages();
        SaveConfiguration.savePrefix();
    }
}

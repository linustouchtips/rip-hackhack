package me.zoom.hackhack;

import cookiedragon.eventsystem.EventDispatcher;
import me.zoom.hackhack.ClickGui2.ClickGUI2;
import me.zoom.hackhack.event.EventProcessor;
import me.zoom.hackhack.macro.MacroManager;
import me.zoom.hackhack.util.config.LoadConfiguration;
import me.zoom.hackhack.util.config.LoadModules;
import me.zoom.hackhack.util.config.SaveConfiguration;
import me.zoom.hackhack.util.config.SaveModules;
import me.zoom.hackhack.util.enemy.Enemies;
import me.zoom.hackhack.util.friend.Friends;
import me.zoom.hackhack.util.TpsUtils;
import net.minecraftforge.common.MinecraftForge;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import me.zoom.hackhack.util.config.Stopper;
import me.zoom.hackhack.command.CommandManager;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.setting.SettingsManager;
import me.zoom.hackhack.util.font.CFontRenderer;
import me.zoom.hackhack.clickgui.ClickGUI;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.Display;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@Mod(modid = HackHack.MOD_ID, name = HackHack.MOD_NAME, version = HackHack.VERSION)
public class HackHack {
    public static final String MOD_ID = "hackhack";
    public static final String MOD_NAME = "HackHack";
    public static final String VERSION = "1.0";

    public static final Logger log = LogManager.getLogger(MOD_NAME);

    public MacroManager macroManager;
    public SaveConfiguration saveConfiguration;
    public LoadConfiguration loadConfiguration;
    public SaveModules saveModules;
    public LoadModules loadModules;
    public ClickGUI2 clickGUI;
    public Friends friends;
    public SettingsManager settingsManager;
    public ModuleManager moduleManager;
    EventProcessor eventProcessor;
    public static CFontRenderer fontRenderer;
    public static Enemies enemies;

    public static final EventBus EVENT_BUS = new EventManager();

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance
    private static HackHack INSTANCE;

    public HackHack(){
        INSTANCE = this;
    }

    /**
     * This is the first initialization event.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    }

    /**
     * This is the second initialization event. Initialize your managers here.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        eventProcessor.init();

        fontRenderer = new CFontRenderer(new Font("Ariel", Font.PLAIN, 18), true, false);

        TpsUtils tpsUtils = new TpsUtils();

        settingsManager = new SettingsManager();
        log.info("Settings initialized!");

        friends = new Friends();
        enemies = new Enemies();
        log.info("Friends and enemies initialized!");

        moduleManager = new ModuleManager();
        log.info("Modules initialized!");

        clickGUI = new ClickGUI2();
        log.info("ClickGUI initialized!");

        macroManager = new MacroManager();
        log.info("Macros initialized!");

        saveConfiguration = new SaveConfiguration();
        Runtime.getRuntime().addShutdownHook(new Stopper());
        log.info("Config Saved!");

        loadConfiguration = new LoadConfiguration();
        log.info("Config Loaded!");

        saveModules = new SaveModules();
        Runtime.getRuntime().addShutdownHook(new Stopper());
        log.info("Modules Saved!");

        loadModules = new LoadModules();
        log.info("Modules Loaded!");

        CommandManager.initCommands();
        log.info("Commands initialized!");

        log.info("Initialization complete!\n");
    }

    /**
     * This is the final initialization event.
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        Display.setTitle(MOD_NAME + " " + VERSION);

        log.info("PostInitialization complete!\n");
    }

    public static HackHack getInstance(){
        return INSTANCE;
    }
}

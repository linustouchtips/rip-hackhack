package me.zoom.hackhack.module.modules.render;

import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.module.Module;
import net.minecraft.client.renderer.ItemRenderer;

public class LowHands extends Module {
    public LowHands() {
        super("LowHands", Category.Render);
    }
    ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

    Setting.Double off;
    Setting.Double main;

    public void setup(){
        off = registerDouble("Off Height", "LowOffhandHeight", 0.5, 0, 1);
        main = registerDouble("Main Height", "LowMainhandHeight", 0.5, 0, 1);
    }

    public void onUpdate(){
        itemRenderer.equippedProgressOffHand = (float)off.getValue();
        itemRenderer.equippedProgressMainHand = (float)main.getValue();
    }
}


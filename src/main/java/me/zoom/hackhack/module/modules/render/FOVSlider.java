package me.zoom.hackhack.module.modules.render;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.setting.Setting;

public class FOVSlider extends Module {
    public FOVSlider() {
        super("FOVSlider","Lets you change your FOV" , Category.Render);
    }
    Setting.Double fov;
    // Setting.Boolean DynamicFOV;

    public void setup() {
        fov = registerDouble("FOV", "fov", 115, 25, 180);
      //DynamicFOV = registerBoolean("DynamicFOV", "DynamicFOV", false);
    }
    @Override


    public void onRender() {
        if (!ModuleManager.isModuleEnabled("Zoom") && !(mc.gameSettings.fovSetting == ((float) fov.getValue()))){
            mc.gameSettings.fovSetting = ((float) fov.getValue());
        }

    }
}
//
//DynamicFOV is an optifine setting lol im a retard

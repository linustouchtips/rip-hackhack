package me.zoom.hackhack.module.modules.render;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Brightens up your world", Category.Render);
    }

    Setting.Double gamma;
    Setting.Boolean refresh;

    @Override
    public void setup() {
        gamma = registerDouble("Gamma", "gamma", 6, 1, 6);
        refresh = registerBoolean("Refresh", "refresh", false);
    }

    @Override
    public void onUpdate() {
        if (refresh.getValue()){
            mc.gameSettings.gammaSetting = ((float) gamma.getValue());

            if (mc.gameSettings.gammaSetting == ((float) gamma.getValue())){
                refresh.setValue(false);
            }
        }
    }
}

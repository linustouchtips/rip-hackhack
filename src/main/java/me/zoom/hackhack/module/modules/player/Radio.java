package me.zoom.hackhack.module.modules.player;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;

public class Radio extends Module {
    public Radio() {
        super("Radio", "Built in music player", Category.Player);
    }

    Setting.Double volume;

    @Override
    public void setup() {
        volume = registerDouble("Volume", "volume", 5, 0, 10);
        super.setup();
    }


}

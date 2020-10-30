package me.zoom.hackhack.module.modules.client;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;

public class Rainbow extends Module {
    public Rainbow() {
        super("Rainbow", Category.Client);
    }

    Setting.Integer speed;

    @Override
    public void setup() {
        speed = registerInteger("Speed", "speed", 2, 1, 10);
        super.setup();
    }

    static int RainbowOffset = 0;

    @Override
    public void onRender() {
        RainbowOffset += speed.getValue();

        super.onRender();
    }

    public static int getRainbowOffset() {
        return RainbowOffset;
    }
}

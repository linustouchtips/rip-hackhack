package me.zoom.hackhack.module.modules.client;

import me.zoom.hackhack.module.Module;

public class CustomFont extends Module {
    public CustomFont() {
        super("CustomFont", Category.Client);
    }

    private static CustomFont INSTANCE;

    private void setInstance() {
        CustomFont.INSTANCE = this;
    }

    public static CustomFont getInstance() {
        if (CustomFont.INSTANCE == null) {
            CustomFont.INSTANCE = new CustomFont();
        }
        return CustomFont.INSTANCE;
    }

    static {
        CustomFont.INSTANCE = new CustomFont();
    }
}

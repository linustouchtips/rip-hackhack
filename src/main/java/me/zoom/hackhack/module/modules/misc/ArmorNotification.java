package me.zoom.hackhack.module.modules.misc;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.font.FontUtils;
import net.minecraft.client.audio.ISound;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ArmorNotification extends Module {
    public ArmorNotification() {
        super("Armor Notify", "Notifies you when you need to mend your armor", Category.Misc);
    }

    Setting.Integer x;
    Setting.Integer y;
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;

    @Override
    public void setup() {
        x= registerInteger("X", "x", 500, 0, 1920);
        y= registerInteger("Y", "y", 500, 0, 1080);
        r= registerInteger("Red", "red", 255, 0, 255);
        g= registerInteger("Green", "green", 255, 0, 255);
        b= registerInteger("Blue", "blue", 255, 0, 255);

    }

    @Override
    public void onUpdate() {


//        int MaxDamage = getArmorDurabilityTotal();

/*
        if (ArmorDurability){
            FontUtils.drawStringWithShadow(false, "Armor is below 50%", x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }

 */

        super.onUpdate();
    }

    @Override
    public void onRender() {
        boolean ArmorDurability = getArmorDurability();

        if (ArmorDurability){
            FontUtils.drawStringWithShadow(false, "Armor is below 50%", x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
        super.onRender();
    }

    private boolean getArmorDurability(){
        boolean TotalDurability = false;

        for(ItemStack itemStack : mc.player.inventory.armorInventory){
            if ((itemStack.getMaxDamage()/2) < itemStack.getItemDamage()){
                return true;
            }
        }
        return TotalDurability;
    }

}

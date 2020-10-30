package me.zoom.hackhack.module.modules.combat;

import me.zoom.hackhack.module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class EasyPearl extends Module {
    public EasyPearl() {
        super("Easy Pearl", "Throws a pearl on toggle", Category.Combat);
    }

    int oldSlot = 0;
    int tick = 0;

    @Override
    protected void onEnable() {
        oldSlot = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = findPearlInHotbar();
    }

    @Override
    public void onUpdate() {

        tick += 1;

        if (mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() == Items.ENDER_PEARL && tick == 3){
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        } else if (!(mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() == Items.ENDER_PEARL) && tick == 3){
            mc.player.inventory.currentItem = findPearlInHotbar();
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        }

        if (mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() == Items.ENDER_PEARL && tick == 6){
            this.disable();
        }

    }

    @Override
    protected void onDisable() {
        mc.player.inventory.currentItem = oldSlot;
        tick = 0;
    }

    private int findPearlInHotbar(){
        // search for exp
        int slot = 0;
        for (int i = 0; i < 9; i++){
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}

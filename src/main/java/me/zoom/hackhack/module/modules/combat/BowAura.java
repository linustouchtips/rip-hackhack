package me.zoom.hackhack.module.modules.combat;

import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class BowAura extends Module {


    public BowAura() {
        super("Bow Aura", "Automatically bows the opponent", Category.Combat);
    }

//    Setting.Boolean autoSwitch;
    int tick = 0;
    int randomVariation = 0;

    @Override
    public void setup() {
 //       autoSwitch = registerBoolean("Auto Switch", "autoSwitch", false);
    }

    @Override
    protected void onEnable() {
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.player.setActiveHand(EnumHand.MAIN_HAND);
    }

    @Override
    public void onUpdate() {

        tick += 1;

        if (mc.player.inventory.getCurrentItem().getItem() == Items.BOW){
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
            mc.rightClickDelayTimer = 0;
            if (mc.player.getItemInUseMaxCount() >= getBowCharge()){
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                mc.player.stopActiveHand();
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                mc.player.setActiveHand(EnumHand.MAIN_HAND);


            } else if (mc.player.getItemInUseMaxCount() == 0){
 //               mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));

                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                mc.player.setActiveHand(EnumHand.MAIN_HAND);
            }

        }
    }

    private int getBowCharge(){
        if (randomVariation == 0) {
            randomVariation = 1;
        }
        return 3 + randomVariation;
    }
}

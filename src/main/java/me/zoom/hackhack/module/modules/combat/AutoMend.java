package me.zoom.hackhack.module.modules.combat;


import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoMend extends Module {

    public AutoMend() { super("AutoMend", "Automatically mends your armor",Category.Combat); }

    int delay = 0;

    Setting.Integer ThrowDelay;

    public void setup(){
        ThrowDelay = registerInteger("Throw Delay", "throwDelay", 2, 1, 10);
    }


    @Override
    public void onUpdate() {
        int ArmorDurability = getArmorDurability();



        boolean safe = isSafe();
        boolean AutoCrystal = ModuleManager.isModuleEnabled("AutoCrystal");
        BlockPos q = mc.player.getPosition();
        if (!AutoCrystal && mc.player.isSneaking() && safe && 0 < ArmorDurability){

            delay = delay + 1;

            if (delay % ThrowDelay.getValue() == 0) {
                mc.player.inventory.currentItem = findExpInHotbar();
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
//            Command.sendClientMessage("ArmorDura=" + String.valueOf(ArmorDurability));
//            Command.sendClientMessage("MaxArmorDura=" + String.valueOf(MaxArmorDurability));
            }

        } else {
            delay = 0;
        }
        super.onUpdate();
    }


    private int findExpInHotbar(){
        // search for exp
        int slot = 0;
        for (int i = 0; i < 9; i++){
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private boolean isSafe(){
        boolean safe = true;
        BlockPos playerPos = getPlayerPos();
        for (BlockPos offset : surroundOffset){
            Block block = mc.world.getBlockState(playerPos.add(offset)).getBlock();
            if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL){
                safe = false;
                break;
            }
        }
        return safe;
    }

    private static BlockPos getPlayerPos(){
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private final BlockPos[] surroundOffset ={
            new BlockPos(0, -1, 0), // down
            new BlockPos(0, 0, -1), // north
            new BlockPos(1, 0, 0), // east
            new BlockPos(0, 0, 1), // south
            new BlockPos(-1, 0, 0) // west
    };

    private int getArmorDurability(){
        int TotalDurability = 0;

        for(ItemStack itemStack : mc.player.inventory.armorInventory){
        TotalDurability = TotalDurability + itemStack.getItemDamage();
        }
        return TotalDurability;
    }

}

package me.zoom.hackhack.module.modules.client;

import me.zoom.hackhack.clickgui.ClickGUI;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.module.modules.combat.AutoCrystal;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.font.FontUtils;
import me.zoom.hackhack.util.friend.Friends;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class CombatInfo extends Module {

    public CombatInfo() {
        super("CombatInfo", Category.Client);
    }

    /* displays ping, totem count, PLR, HTR and LBY
     *  By the one and only _KA1
     */

    public static
    Setting.Mode CombatInfoWatermark;
    Setting.Integer infoX;
    Setting.Integer infoY;
    Setting.Mode GUIColorSync;
    Setting.Integer colorRed;
    Setting.Integer colorGreen;
    Setting.Integer colorBlue;
    Setting.Integer colorAlpha;
    Setting.Mode Mode;
    Color c;
    int totems;
    int color;
    private BlockPos[] surroundOffset;

    public void setup() {
        // holy shit this took ages bc im a retard

        infoX = registerInteger("Combat info X", "Combat info X", 0, 0, 1200);
        infoY = registerInteger("Combat info Y", "Combat info Y", 200, 0, 600);

        /* colorRed = registerInteger("Red", "colorRed", 255, 0, 255);
          colorGreen = registerInteger("Green", "colorGreen", 255, 0, 255);
          colorBlue = registerInteger("Blue", "colorBlue", 255, 0, 255);
          colorAlpha = registerInteger("Alpha", "colorAlpha", 255, 0, 255);

         */

//GUIColorSync = registerMode("GUIColorSync", "GUIColorSync")

        ArrayList<String> Modes;
        Modes = new ArrayList<>();
        Modes.add("HACKHACK");
        Modes.add("KAiKLiENT");
        Modes.add("ZOOMGOD.CC");
        Modes.add("MAXHAX.CA");

        Mode = registerMode("Mode", "Mode", Modes, "HACKHACK");
    }

    final AutoCrystal a = (AutoCrystal) ModuleManager.getModuleByName("AutoCrystal");

    public int getPing() {
        int p = -1;
        if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null) {
        } else {
            p = mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
        }
        return p;
    }

    public void onRender() {


        EntityOtherPlayerMP players = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityOtherPlayerMP)
                .filter(e -> mc.player.getDistance(e) <= AutoCrystal.placeRange.getValue())
                .map(entity -> (EntityOtherPlayerMP) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);

        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) totems++;


        this.surroundOffset = new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
        final List<EntityPlayer> entities = mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList());

        Color on = new Color(0, 255, 0);
        Color off = new Color(255, 0, 0);


            ClickGUI.color = new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(), ClickGuiModule.blue.getValue(), ClickGuiModule.opacity.getValue()).getRGB();

            // color = new Color(colorRed.getValue(), colorGreen.getValue(), colorBlue.getValue(), colorAlpha.getValue()).getRGB();


            FontUtils.drawKeyStringWithShadow(false, (Mode.getValue()), infoX.getValue(), infoY.getValue() + 1, ClickGUI.color);

            if (players != null && mc.player.getDistance(players) <= AutoCrystal.range.getValue()) {
                FontUtils.drawStringWithShadow(true, "HTR", infoX.getValue(), infoY.getValue() + 10, on.getRGB());
            } else {
                FontUtils.drawStringWithShadow(true, "HTR", infoX.getValue(), infoY.getValue() + 10, off.getRGB());
            }
            if (players != null && mc.player.getDistance(players) <= AutoCrystal.placeRange.getValue()) {
                FontUtils.drawStringWithShadow(true, "PLR", infoX.getValue(), infoY.getValue() + 20, on.getRGB());
            } else {
                FontUtils.drawStringWithShadow(true, "PLR", infoX.getValue(), infoY.getValue() + 20, off.getRGB());
            }
            if (totems > 0 && ModuleManager.isModuleEnabled("AutoTotem")) {

                FontUtils.drawStringWithShadow(true, totems + "", infoX.getValue(), infoY.getValue() + 30, on.getRGB());
            } else {
                FontUtils.drawStringWithShadow(true, totems + "", infoX.getValue(), infoY.getValue() + 30, off.getRGB());
            }
            if (getPing() > 100) {
                FontUtils.drawStringWithShadow(true, "PING " + getPing(), infoX.getValue(), infoY.getValue() + 40, off.getRGB());
            } else {
                FontUtils.drawStringWithShadow(true, "PING " + getPing(), infoX.getValue(), infoY.getValue() + 40, on.getRGB());
            }

            for (final EntityPlayer e : entities) {
                int i = 0;
                for (final BlockPos add : this.surroundOffset) {
                    ++i;
                    final BlockPos o = new BlockPos(e.getPositionVector().x, e.getPositionVector().y, e.getPositionVector().z).add(add.getX(), add.getY(), add.getZ());
                    if (mc.world.getBlockState(o).getBlock() == Blocks.OBSIDIAN) {
                        if (i == 1 && a.canPlaceCrystal(o.north(1).down())) {
                            FontUtils.drawStringWithShadow(true, "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                        }
                        if (i == 2 && a.canPlaceCrystal(o.east(1).down())) {
                            FontUtils.drawStringWithShadow(true, "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                        }
                        if (i == 3 && a.canPlaceCrystal(o.south(1).down())) {
                            FontUtils.drawStringWithShadow(true, "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                        }
                        if (i == 4 && a.canPlaceCrystal(o.west(1).down())) {
                            FontUtils.drawStringWithShadow(true, "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                        }
                    } else
                        FontUtils.drawStringWithShadow(true, "LBY", infoX.getValue(), infoY.getValue() + 50, off.getRGB());

                }


            }


        }


    }


 // EZZZZZ




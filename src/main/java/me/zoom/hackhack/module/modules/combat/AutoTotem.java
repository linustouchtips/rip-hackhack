package me.zoom.hackhack.module.modules.combat;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.setting.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class AutoTotem extends Module {
	public AutoTotem(){super("AutoTotem", "Automatically moves a totem to your offhand", Category.Combat);}

	int totems;
	boolean moving = false;
	boolean returnI = false;
	Setting.Boolean soft;
	Setting.Mode OffhandItem;
	Setting.Integer TotemHealth;
	Setting.Boolean CrystalAura;
	Setting.Boolean BedAura;
	Setting.Boolean AuraGap;


	public void setup(){
		soft = registerBoolean("Soft", "Soft", true);
		TotemHealth = registerInteger("Health", "health", 10, 1, 20);
		CrystalAura = registerBoolean("Crystal on CA", "crystalaura", false);
		BedAura = registerBoolean("Bed on BA", "bedaura", true);
		AuraGap = registerBoolean("Gap on Aura", "auraGap", true);


		ArrayList<String> modes = new ArrayList<>();
		modes.add("totem");
		modes.add("bed");
		modes.add("crystal");
		modes.add("golden apple");

		OffhandItem = registerMode("Item", "item", modes, "totem");
	}

	@Override
	public void onUpdate(){

		Item item = getItem();

		if (mc.currentScreen instanceof GuiContainer) return;
		if (returnI){
			int t = -1;
			for (int i = 0; i < 45; i++)
				if (mc.player.inventory.getStackInSlot(i).isEmpty()){
					t = i;
					break;
				}
			if (t == -1) return;
			mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
			returnI = false;
		}
		totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == item).mapToInt(ItemStack::getCount).sum();
		if (mc.player.getHeldItemOffhand().getItem() == item) totems++;
		else{
			if (soft.getValue() && !AutoTotem.mc.player.getHeldItemOffhand().isEmpty()) return;
			if (moving){
				mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
				moving = false;
				if (!mc.player.inventory.getItemStack().isEmpty()) returnI = true;
				return;
			}
			if (mc.player.inventory.getItemStack().isEmpty()){
				if (totems == 0) return;
				int t = -1;
				for (int i = 0; i < 45; i++)
					if (mc.player.inventory.getStackInSlot(i).getItem() == item){
						t = i;
						break;
					}
				if (t == -1) return; // Should never happen!
				mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
				moving = true;
			} else if (!soft.getValue()){
				int t = -1;
				for (int i = 0; i < 45; i++)
					if (mc.player.inventory.getStackInSlot(i).isEmpty()){
						t = i;
						break;
					}
				if (t == -1) return;
				mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
			}
		}
	}

	@Override
	public String getHudInfo(){
		String t = "[" + ChatFormatting.WHITE + OffhandItem.getValue() + ChatFormatting.GRAY + "]";
		return t;
	}

	public Item getItem(){
		Item item = Items.TOTEM_OF_UNDYING;
		Item mainItem = null;

		boolean Crystal = false;
		boolean Bed = false;

		if (mc.player.getHealth() < TotemHealth.getValue()){
			item = Items.TOTEM_OF_UNDYING;
		} else if (CrystalAura.getValue() && ModuleManager.isModuleEnabled("AutoCrystal")){
			item = Items.END_CRYSTAL;
			Crystal = true;
		} else if (BedAura.getValue() && ModuleManager.isModuleEnabled("BedAura") && !Crystal){
			item = Items.BED;
			Bed = true;
		} else if (AuraGap.getValue() && ModuleManager.isModuleEnabled("KillAura") && !Crystal && !Bed){
			item = Items.GOLDEN_APPLE;
		} else if (OffhandItem.getValue() == "bed"){
			item = Items.BED;
			mainItem = Items.BED;
		} else if (OffhandItem.getValue() == "totem") {
			item = Items.TOTEM_OF_UNDYING;
			mainItem = Items.TOTEM_OF_UNDYING;
		} else if (OffhandItem.getValue() == "crystal") {
			item = Items.END_CRYSTAL;
			mainItem = Items.END_CRYSTAL;
		} else if (OffhandItem.getValue() == "golden apple"){
			item = Items.GOLDEN_APPLE;
			mainItem = Items.GOLDEN_APPLE;
		}

		return item;
	}
}


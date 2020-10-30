package me.zoom.hackhack.module.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.event.events.PacketEvent;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.friend.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public KillAura(){
        super("KillAura", "Automatically smacks people", Category.Combat);
    }

    private Setting.Boolean swordOnly;
    private Setting.Boolean caCheck;
    private Setting.Boolean criticals;
    private Setting.Double range;

    public void setup(){
        range = registerDouble("Range", "Range", 5,0,10);
        swordOnly = registerBoolean("Sword Only", "SwordOnly",true);
        criticals = registerBoolean("Criticals", "Criticals",true);
        caCheck = registerBoolean("AC Check", "ACCheck",false);
    }

    private boolean isAttacking = false;

    public void onUpdate(){
        if(mc.player == null || mc.player.isDead) return;
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValue())
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !Friends.isFriend(entity.getName()))
                .sorted(Comparator.comparing(e->mc.player.getDistance(e)))
                .collect(Collectors.toList());

        targets.forEach(target ->{
            if(swordOnly.getValue())
                if(!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return;

            if(caCheck.getValue())
                if(((AutoCrystal) ModuleManager.getModuleByName("AutoCrystal")).isActive) return;

            attack(target);
        });
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity) {
            if(criticals.getValue() && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround && isAttacking) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });

    public void onEnable(){
        HackHack.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        HackHack.EVENT_BUS.unsubscribe(this);
    }

    public void attack(Entity e){
        if(mc.player.getCooledAttackStrength(0) >= 1) {
            isAttacking = true;
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            isAttacking = false;
        }
    }
}

package me.zoom.hackhack.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * 336
**/
public class BlockInteractionHelper {
   // $FF: synthetic field
   private static final Minecraft mc;
   // $FF: synthetic field
   public static final List blackList;
   // $FF: synthetic field
   public static final List shulkerList;

   private static float[] getLegitRotations(Vec3d var0) {
      Vec3d var1 = getEyesPos();
      double var2 = var0.x - var1.x;
      double var4 = var0.y - var1.y;
      double var6 = var0.z - var1.z;
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      float var10 = (float)Math.toDegrees(Math.atan2(var6, var2)) - 90.0F;
      float var11 = (float)(-Math.toDegrees(Math.atan2(var4, var8)));
      return new float[]{Wrapper.getPlayer().rotationYaw + MathHelper.wrapDegrees(var10 - Wrapper.getPlayer().rotationYaw), Wrapper.getPlayer().rotationPitch + MathHelper.wrapDegrees(var11 - Wrapper.getPlayer().rotationPitch)};
   }

   public static boolean canBeClicked(BlockPos var0) {
      return getBlock(var0).canCollideCheck(getState(var0), false);
   }

   private static IBlockState getState(BlockPos var0) {
      return Wrapper.getWorld().getBlockState(var0);
   }

   public static EnumFacing getPlaceableSide(BlockPos var0) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing var4 = var1[var3];
         BlockPos var5 = var0.offset(var4);
         if (mc.world.getBlockState(var5).getBlock().canCollideCheck(mc.world.getBlockState(var5), false)) {
            IBlockState var6 = mc.world.getBlockState(var5);
            if (!var6.getMaterial().isReplaceable()) {
               return var4;
            }
         }
      }

      return null;
   }

   public static float getYaw(Entity var0) {
      double var1 = var0.posX - mc.player.posX;
      double var3 = var0.posZ - mc.player.posZ;
      double var5;
      if (var3 < 0.0D && var1 < 0.0D) {
         var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
      } else if (var3 < 0.0D && var1 > 0.0D) {
         var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
      } else {
         var5 = Math.toDegrees(-Math.atan(var1 / var3));
      }

      return MathHelper.wrapDegrees(-(mc.player.rotationYaw - (float)var5));
   }

   public static void faceVectorPacketInstant(Vec3d var0) {
      float[] var1 = getLegitRotations(var0);
      Wrapper.getPlayer().connection.sendPacket(new net.minecraft.network.play.client.CPacketPlayer.Rotation(var1[0], var1[1], Wrapper.getPlayer().onGround));
   }

   public static boolean hotbarSlotCheckEmpty(ItemStack var0) {
      return var0 != ItemStack.EMPTY;
   }

   public static double[] calculateLookAt(double var0, double var2, double var4, EntityPlayer var6) {
      double var7 = var6.posX - var0;
      double var9 = var6.posY - var2;
      double var11 = var6.posZ - var4;
      double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
      var7 /= var13;
      var9 /= var13;
      var11 /= var13;
      double var15 = Math.asin(var9);
      double var17 = Math.atan2(var11, var7);
      var15 = var15 * 180.0D / 3.141592653589793D;
      var17 = var17 * 180.0D / 3.141592653589793D;
      var17 += 90.0D;
      return new double[]{var17, var15};
   }

   public static void rotate(float var0, float var1) {
      Minecraft.getMinecraft().player.rotationYaw = var0;
      Minecraft.getMinecraft().player.rotationPitch = var1;
   }

   private static PlayerControllerMP getPlayerController() {
      return Minecraft.getMinecraft().playerController;
   }

   private static Vec3d getEyesPos() {
      return new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
   }

   public static boolean blockCheckNonBlock(ItemStack var0) {
      return var0.getItem() instanceof ItemBlock;
   }

   public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
      EntityEgg var4 = new EntityEgg(mc.world);
      var4.posX = (double)var0 + 0.5D;
      var4.posY = (double)var1 + 0.5D;
      var4.posZ = (double)var2 + 0.5D;
      var4.posX += (double)var3.getDirectionVec().getX() * 0.25D;
      var4.posY += (double)var3.getDirectionVec().getY() * 0.25D;
      var4.posZ += (double)var3.getDirectionVec().getZ() * 0.25D;
      return getDirectionToEntity(var4);
   }

   public static List getSphere(BlockPos var0, float var1, int var2, boolean var3, boolean var4, int var5) {
      ArrayList var6 = new ArrayList();
      int var7 = var0.getX();
      int var8 = var0.getY();
      int var9 = var0.getZ();

      for(int var10 = var7 - (int)var1; (float)var10 <= (float)var7 + var1; ++var10) {
         for(int var11 = var9 - (int)var1; (float)var11 <= (float)var9 + var1; ++var11) {
            for(int var12 = var4 ? var8 - (int)var1 : var8; (float)var12 < (var4 ? (float)var8 + var1 : (float)(var8 + var2)); ++var12) {
               double var13 = (double)((var7 - var10) * (var7 - var10) + (var9 - var11) * (var9 - var11) + (var4 ? (var8 - var12) * (var8 - var12) : 0));
               if (var13 < (double)(var1 * var1) && (!var3 || var13 >= (double)((var1 - 1.0F) * (var1 - 1.0F)))) {
                  BlockPos var15 = new BlockPos(var10, var12 + var5, var11);
                  var6.add(var15);
               }
            }
         }
      }

      return var6;
   }

   private static void processRightClickBlock(BlockPos var0, EnumFacing var1, Vec3d var2) {
      getPlayerController().processRightClickBlock(Wrapper.getPlayer(), mc.world, var0, var1, var2, EnumHand.MAIN_HAND);
   }

   public static float[] getRotationNeededForBlock(EntityPlayer var0, BlockPos var1) {
      double var2 = (double)var1.getX() - var0.posX;
      double var4 = (double)var1.getY() + 0.5D - (var0.posY + (double)var0.getEyeHeight());
      double var6 = (double)var1.getZ() - var0.posZ;
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      float var10 = (float)(Math.atan2(var6, var2) * 180.0D / 3.141592653589793D) - 90.0F;
      float var11 = (float)(-(Math.atan2(var4, var8) * 180.0D / 3.141592653589793D));
      return new float[]{var10, var11};
   }

   private static Block getBlock(BlockPos var0) {
      return getState(var0).getBlock();
   }

   public static void placeBlockScaffold(BlockPos var0) {
      Vec3d var1 = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumFacing var5 = var2[var4];
         BlockPos var6 = var0.offset(var5);
         EnumFacing var7 = var5.getOpposite();
         if (canBeClicked(var6)) {
            Vec3d var8 = (new Vec3d(var6)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(var7.getDirectionVec())).rotatePitch(0.5F));
            if (var1.squareDistanceTo(var8) <= 18.0625D) {
               faceVectorPacketInstant(var8);
               processRightClickBlock(var6, var7, var8);
               Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
               mc.rightClickDelayTimer = 4;
               return;
            }
         }
      }

   }

   public static boolean checkForNeighbours(BlockPos var0) {
      if (!hasNeighbour(var0)) {
         EnumFacing[] var1 = EnumFacing.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            EnumFacing var4 = var1[var3];
            BlockPos var5 = var0.offset(var4);
            if (hasNeighbour(var5)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static void rotate(double[] var0) {
      Minecraft.getMinecraft().player.rotationYaw = (float)var0[0];
      Minecraft.getMinecraft().player.rotationPitch = (float)var0[1];
   }

   private static boolean hasNeighbour(BlockPos var0) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing var4 = var1[var3];
         BlockPos var5 = var0.offset(var4);
         if (!Wrapper.getWorld().getBlockState(var5).getMaterial().isReplaceable()) {
            return true;
         }
      }

      return false;
   }

   public static float getPitch(Entity var0) {
      double var1 = var0.posX - mc.player.posX;
      double var3 = var0.posZ - mc.player.posZ;
      double var5 = var0.posY - 1.6D + (double)var0.getEyeHeight() - mc.player.posY;
      double var7 = (double)MathHelper.sqrt(var1 * var1 + var3 * var3);
      double var9 = -Math.toDegrees(Math.atan(var5 / var7));
      return -MathHelper.wrapDegrees(mc.player.rotationPitch - (float)var9);
   }

   private static float[] getDirectionToEntity(Entity var0) {
      return new float[]{getYaw(var0) + mc.player.rotationYaw, getPitch(var0) + mc.player.rotationPitch};
   }

   private static float wrapAngleTo180(float var0) {
      for(var0 %= 360.0F; var0 >= 180.0F; var0 -= 360.0F) {
      }

      while(var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static void lookAtBlock(BlockPos var0) {
      rotate(calculateLookAt((double)var0.getX(), (double)var0.getY(), (double)var0.getZ(), Minecraft.getMinecraft().player));
   }

   public static List getCircle(BlockPos var0, int var1, float var2, boolean var3) {
      ArrayList var4 = new ArrayList();
      int var5 = var0.getX();
      int var6 = var0.getZ();

      for(int var7 = var5 - (int)var2; (float)var7 <= (float)var5 + var2; ++var7) {
         for(int var8 = var6 - (int)var2; (float)var8 <= (float)var6 + var2; ++var8) {
            double var9 = (double)((var5 - var7) * (var5 - var7) + (var6 - var8) * (var6 - var8));
            if (var9 < (double)(var2 * var2) && (!var3 || var9 >= (double)((var2 - 1.0F) * (var2 - 1.0F)))) {
               BlockPos var11 = new BlockPos(var7, var1, var8);
               var4.add(var11);
            }
         }
      }

      return var4;
   }

   static {
      blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
      shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
      mc = Minecraft.getMinecraft();
   }
}

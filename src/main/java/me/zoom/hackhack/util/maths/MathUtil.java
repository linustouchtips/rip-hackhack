/*     */ package me.zoom.hackhack.util.maths;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ public final class MathUtil
/*     */ {
/*  13 */   private static Minecraft mc = Minecraft.getMinecraft();
/*     */ 
/*     */   
/*     */   public static double calculateDistanceWithPartialTicks(double n, double n2, float renderPartialTicks) {
/*  17 */     return n2 + (n - n2) * mc.getRenderPartialTicks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d interpolateEntityClose(Entity entity, float renderPartialTicks) {
/*  22 */     return new Vec3d(
/*  23 */         calculateDistanceWithPartialTicks(entity.posX, entity.lastTickPosX, renderPartialTicks) - (mc.getRenderManager()).renderPosX, 
/*  24 */         calculateDistanceWithPartialTicks(entity.posY, entity.lastTickPosY, renderPartialTicks) - (mc.getRenderManager()).renderPosY, 
/*  25 */         calculateDistanceWithPartialTicks(entity.posZ, entity.lastTickPosZ, renderPartialTicks) - (mc.getRenderManager()).renderPosZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d interpolateVec3dPos(Vec3d pos, float renderPartialTicks) {
/*  30 */     return new Vec3d(
/*  31 */         calculateDistanceWithPartialTicks(pos.x, pos.x, renderPartialTicks) - (mc.getRenderManager()).renderPosX, 
/*  32 */         calculateDistanceWithPartialTicks(pos.y, pos.y - 0.021D, renderPartialTicks) - (mc.getRenderManager()).renderPosY, 
/*  33 */         calculateDistanceWithPartialTicks(pos.z, pos.z, renderPartialTicks) - (mc.getRenderManager()).renderPosZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d interpolateEntity(Entity entity, float time) {
/*  38 */     return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double radToDeg(double rad) {
/*  45 */     return rad * 57.295780181884766D;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double degToRad(double deg) {
/*  50 */     return deg * 0.01745329238474369D;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d direction(float yaw) {
/*  55 */     return new Vec3d(Math.cos(degToRad((yaw + 90.0F))), 0.0D, Math.sin(degToRad((yaw + 90.0F))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/*  60 */     double difX = to.x - from.x;
/*  61 */     double difY = (to.y - from.y) * -1.0D;
/*  62 */     double difZ = to.z - from.z;
/*     */     
/*  64 */     double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
/*     */     
/*  66 */     return new float[] {
/*  67 */         (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), 
/*  68 */         (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)))
/*     */       };
/*     */   }
/*     */   
/*     */   public static double[] directionSpeed(double speed) {
/*  73 */     Minecraft mc = Minecraft.getMinecraft();
/*  74 */     float forward = mc.player.movementInput.moveForward;
/*  75 */     float side = mc.player.movementInput.moveStrafe;
/*     */     
/*  77 */     float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
/*     */     
/*  79 */     if (forward != 0.0F) {
/*     */       
/*  81 */       if (side > 0.0F) {
/*     */         
/*  83 */         yaw += ((forward > 0.0F) ? -45 : 45);
/*     */       }
/*  85 */       else if (side < 0.0F) {
/*     */         
/*  87 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/*  89 */       side = 0.0F;
/*     */ 
/*     */       
/*  92 */       if (forward > 0.0F) {
/*     */         
/*  94 */         forward = 1.0F;
/*     */       }
/*  96 */       else if (forward < 0.0F) {
/*     */         
/*  98 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 103 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 104 */     double posX = forward * speed * cos + side * speed * sin;
/* 105 */     double posZ = forward * speed * sin - side * speed * cos;
/* 106 */     return new double[] { posX, posZ };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] directionSpeedNoForward(double speed) {
/* 112 */     Minecraft mc = Minecraft.getMinecraft();
/* 113 */     float forward = 1.0F;
/*     */     
/* 115 */     float side = 0.0F;
/*     */     
/* 117 */     float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
/*     */     
/* 119 */     if (forward != 0.0F) {
/*     */       
/* 121 */       if (side > 0.0F) {
/*     */         
/* 123 */         yaw += ((forward > 0.0F) ? -45 : 45);
/*     */       }
/* 125 */       else if (side < 0.0F) {
/*     */         
/* 127 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/* 129 */       side = 0.0F;
/*     */ 
/*     */       
/* 132 */       if (forward > 0.0F) {
/*     */         
/* 134 */         forward = 1.0F;
/*     */       }
/* 136 */       else if (forward < 0.0F) {
/*     */         
/* 138 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 143 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 144 */     double posX = forward * speed * cos + side * speed * sin;
/* 145 */     double posZ = forward * speed * sin - side * speed * cos;
/* 146 */     return new double[] { posX, posZ };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d mult(Vec3d factor, Vec3d multiplier) {
/* 152 */     return new Vec3d(factor.x * multiplier.x, factor.y * multiplier.y, factor.z * multiplier.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d mult(Vec3d factor, float multiplier) {
/* 157 */     return new Vec3d(factor.x * multiplier, factor.y * multiplier, factor.z * multiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d div(Vec3d factor, Vec3d divisor) {
/* 162 */     return new Vec3d(factor.x / divisor.x, factor.y / divisor.y, factor.z / divisor.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d div(Vec3d factor, float divisor) {
/* 167 */     return new Vec3d(factor.x / divisor, factor.y / divisor, factor.z / divisor);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double round(double value, int places) {
/* 172 */     if (places < 0)
/*     */     {
/* 174 */       return value;
/*     */     }
/* 176 */     return (new BigDecimal(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static float clamp(float val, float min, float max) {
/* 181 */     if (val <= min)
/*     */     {
/* 183 */       val = min;
/*     */     }
/* 185 */     if (val >= max)
/*     */     {
/* 187 */       val = max;
/*     */     }
/* 189 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float wrap(float val) {
/* 194 */     val %= 360.0F;
/* 195 */     if (val >= 180.0F)
/* 196 */       val -= 360.0F; 
/* 197 */     if (val < -180.0F)
/* 198 */       val += 360.0F; 
/* 199 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double map(double value, double a, double b, double c, double d) {
/* 206 */     value = (value - a) / (b - a);
/*     */     
/* 208 */     return c + value * (d - c);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double linear(double from, double to, double incline) {
/* 213 */     return (from < to - incline) ? (from + incline) : ((from > to + incline) ? (from - incline) : to);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parabolic(double from, double to, double incline) {
/* 218 */     return from + (to - from) / incline;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getDistance(Vec3d pos, double x, double y, double z) {
/* 223 */     double deltaX = pos.x - x;
/* 224 */     double deltaY = pos.y - y;
/* 225 */     double deltaZ = pos.z - z;
/* 226 */     return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double[] calcIntersection(double[] line, double[] line2) {
/* 231 */     double a1 = line[3] - line[1];
/* 232 */     double b1 = line[0] - line[2];
/* 233 */     double c1 = a1 * line[0] + b1 * line[1];
/*     */     
/* 235 */     double a2 = line2[3] - line2[1];
/* 236 */     double b2 = line2[0] - line2[2];
/* 237 */     double c2 = a2 * line2[0] + b2 * line2[1];
/*     */     
/* 239 */     double delta = a1 * b2 - a2 * b1;
/*     */     
/* 241 */     return new double[] { (b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double calculateAngle(double x1, double y1, double x2, double y2) {
/* 247 */     double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
/*     */     
/* 249 */     angle += Math.ceil(-angle / 360.0D) * 360.0D;
/*     */     
/* 251 */     return angle;
/*     */   }
/*     */ }

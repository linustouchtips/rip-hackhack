/*    */ package me.zoom.hackhack.util.colors;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class ColourUtilities
/*    */ {
/*  8 */   public static final int[] RAINBOW_COLORS = new int[] { 16711680, 16728064, 16744192, 16776960, 8453888, 65280, 65535, 33023, 255 };
/*    */   
/* 10 */   private static Random random = new Random();
/*    */   
/*    */   public static double[] toRGBA(int hex) {
/* 13 */     return new double[] { (hex >> 16 & 0xFF) / 255.0D, (hex >> 8 & 0xFF) / 255.0D, (hex & 0xFF) / 255.0D, (hex >> 24 & 0xFF) / 255.0D, (hex >> 24 & 0xFF) / 255.0D };
/*    */   }
/*    */   
/*    */   public static int generateColor() {
/* 17 */     float hue = random.nextFloat();
/*    */     
/* 19 */     float saturation = random.nextInt(5000) / 10000.0F + 0.5F;
/* 20 */     float brightness = random.nextInt(5000) / 10000.0F + 0.5F;
/* 21 */     return Color.HSBtoRGB(hue, saturation, brightness);
/*    */   }
/*    */   
/*    */   public static int generateWaypointColour() {
/* 25 */     return Color.HSBtoRGB((float)Math.random(), (float)Math.random() / 4.0F + 0.75F, (float)Math.random() / 2.0F + 0.25F);
/*    */   }
/*    */   
/*    */   public static Color blend(Color color1, Color color2, float ratio) {
/* 29 */     if (ratio < 0.0F)
/* 30 */       return color2; 
/* 31 */     if (ratio > 1.0F)
/* 32 */       return color1; 
/* 33 */     float ratio2 = 1.0F - ratio;
/* 34 */     float[] rgb1 = new float[3];
/* 35 */     float[] rgb2 = new float[3];
/* 36 */     color1.getColorComponents(rgb1);
/* 37 */     color2.getColorComponents(rgb2);
/* 38 */     return new Color(rgb1[0] * ratio + rgb2[0] * ratio2, rgb1[1] * ratio + rgb2[1] * ratio2, rgb1[2] * ratio + rgb2[2] * ratio2);
/*    */   }
/*    */   
/*    */   public static int parseColor(String color) {
/* 42 */     if (color.startsWith("#")) {
/* 43 */       color = color.substring(1);
/*    */     }
/* 45 */     if (color.toLowerCase().startsWith("0x")) {
/* 46 */       color = color.substring(2);
/*    */     }
/*    */     try {
/* 49 */       return (int)Long.parseLong(color, 16);
/* 50 */     } catch (Exception exception) {
/*    */       
/* 52 */       return -1;
/*    */     } 
/*    */   }
/*    */ }
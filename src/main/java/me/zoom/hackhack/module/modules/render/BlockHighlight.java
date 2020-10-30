package me.zoom.hackhack.module.modules.render;


import me.zoom.hackhack.event.events.RenderEvent;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.GeometryMasks;
import me.zoom.hackhack.util.RenderUtil;
import me.zoom.hackhack.util.font.FontUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockHighlight extends Module {
	public BlockHighlight(){
		super("BlockHighlight", Category.Render);
	}
	
	Setting.Integer w;
	Setting.Boolean shade;
	Setting.Boolean coords;
	Setting.Integer r;
	Setting.Integer g;
	Setting.Integer b;
	Setting.Integer a;

	public void setup() {
		shade = registerBoolean("Fill", "Fill", false);
		w = registerInteger("Width", "Width", 2, 1, 10);
		r= registerInteger("Red", "red", 255, 0, 255);
		g= registerInteger("Green", "green", 255, 0, 255);
		b= registerInteger("Blue", "blue", 255, 0, 255);
		a= registerInteger("Alpha", "alpha", 255, 0, 255);

		coords = registerBoolean("Coordinates", "coords", true);
	}

	public void onWorldRender(RenderEvent event) {
		RayTraceResult ray = mc.objectMouseOver;
		AxisAlignedBB bb;
		BlockPos pos;
//		GSColor c2=new GSColor(color.getValue(),50);
		if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
			pos = ray.getBlockPos();
			bb = mc.world.getBlockState(pos).getSelectedBoundingBox(mc.world, pos);
			if (bb != null && pos != null && mc.world.getBlockState(pos).getMaterial() != Material.AIR) {
				RenderUtil.prepareGL();
				RenderUtil.drawBoundingBox(bb, w.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), a.getValue()).getRGB());
				RenderUtil.releaseGL();
				if (shade.getValue()) {
					RenderUtil.prepare(GL11.GL_QUADS);
					RenderUtil.drawBox(bb, new Color(r.getValue(), g.getValue(), b.getValue(), 50).getRGB(), GeometryMasks.Quad.ALL);
					RenderUtil.release();
				}
				if (coords.getValue()){
					RenderUtil.glBillboardDistanceScaled((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, mc.player, 1);
					int cx = pos.getX();
					int cy = pos.getY();
					int cz = pos.getZ();
					String CoordsText = Math.floor(cx) + " " + Math.floor(cy) + " " + Math.floor(cz);
					GlStateManager.disableDepth();
					GlStateManager.translate(-(mc.fontRenderer.getStringWidth(CoordsText) / 2.0d), 0, 0);
					//mc.fontRenderer.drawStringWithShadow(damageText, 0, 0, 0xFFffffff);
					FontUtils.drawStringWithShadow(false, CoordsText, 0, 0, new Color(255,255,255).getRGB());
				}
			}
		}
	}
}

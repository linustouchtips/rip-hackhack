package me.zoom.hackhack.ClickGui2;


import me.zoom.hackhack.ClickGui2.frame.Component;
import me.zoom.hackhack.ClickGui2.frame.Frames;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.module.ModuleManager;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI2 extends GuiScreen {
	public static ArrayList<Frames> frames;

	public ClickGUI2(){
		ClickGUI2.frames = new ArrayList<Frames>();
		int DevFrameX = 10;
		for (final Module.Category category : Module.Category.values()){
			final Frames devframe = new Frames(category);
			devframe.setX(DevFrameX);
			ClickGUI2.frames.add(devframe);
			DevFrameX += devframe.getWidth() + 10;
		}
	}

	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

//		GL11.glScaled(ClickGuiModule.scale.getValue(), ClickGuiModule.scale.getValue(), 1);
//		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		for (final Frames frames : ClickGUI2.frames){



			if (ModuleManager.isModuleEnabled("Hud Editor")){
				if (frames.category.name() == "HudEditor"){
					frames.renderGUIFrame(this.fontRenderer);
					frames.updatePosition(mouseX, mouseY);
					frames.updateMouseWheel();
					for (final Component comp : frames.getComponents()) {
						comp.updateComponent(mouseX, mouseY);
					}
				}
			} else {
				frames.renderGUIFrame(this.fontRenderer);
				frames.updatePosition(mouseX, mouseY);
				frames.updateMouseWheel();
				for (final Component comp : frames.getComponents()) {
					comp.updateComponent(mouseX, mouseY);
				}
			}
		}
	}

	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException{
		for (final Frames frames : ClickGUI2.frames){

			if (ModuleManager.isModuleEnabled("Hud Editor")){
				if (frames.category.name() == "HudEditor"){
					if (frames.isWithinHeader(mouseX, mouseY) && mouseButton == 0){
						frames.setDrag(true);
						frames.dragX = mouseX - frames.getX();
						frames.dragY = mouseY - frames.getY();

					}
					if (frames.isWithinHeader(mouseX, mouseY) && mouseButton == 1){
						frames.setOpen(!frames.isOpen());
					}
					if (frames.isOpen() && !frames.getComponents().isEmpty()){
						for (final Component component : frames.getComponents()){
							component.mouseClicked(mouseX, mouseY, mouseButton);
						}
					}


				}
			} else {
				if (frames.isWithinHeader(mouseX, mouseY) && mouseButton == 0){
					frames.setDrag(true);
					frames.dragX = mouseX - frames.getX();
					frames.dragY = mouseY - frames.getY();
				}
				if (frames.isWithinHeader(mouseX, mouseY) && mouseButton == 1){
					frames.setOpen(!frames.isOpen());
				}
				if (frames.isOpen() && !frames.getComponents().isEmpty()){
					for (final Component component : frames.getComponents()){
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}

		}
	}

	protected void mouseReleased(final int mouseX, final int mouseY, final int state){
		for (final Frames frames : ClickGUI2.frames){
			frames.setDrag(false);
		}
		for (final Frames frames : ClickGUI2.frames){
			if (frames.isOpen() && !frames.getComponents().isEmpty()){
				for (final Component component : frames.getComponents()){
					component.mouseReleased(mouseX, mouseY, state);
				}
			}
		}
	}

	protected void keyTyped(final char typedChar, final int keyCode){
		for (final Frames frames : ClickGUI2.frames){
			if (frames.isOpen() && !frames.getComponents().isEmpty()){
				for (final Component component : frames.getComponents()){
					component.keyTyped(typedChar, keyCode);
				}
			}
		}
		if (keyCode == 1){
			this.mc.displayGuiScreen(null);
		}
	}

	public boolean doesGuiPauseGame(){
		return false;
	}

	public void initGui(){

	}

	public static Frames getFrameByName(String name){
		Frames pa = null;

		for (Frames frames : getFrames()){
			if (name.equalsIgnoreCase(String.valueOf(frames.category))) pa = frames;
		}
		return pa;
	}

	public static ArrayList<Frames> getFrames(){
		return frames;
	}
}

package me.zoom.hackhack.ClickGui2.frame;

import me.zoom.hackhack.ClickGui2.buttons.*;
import me.zoom.hackhack.HackHack;
import me.zoom.hackhack.module.Module;
import me.zoom.hackhack.setting.Setting;
import me.zoom.hackhack.util.font.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class Buttons extends Component {
	public Module mod;
	public Frames parent;
	public int offset;
	private boolean isHovered;
	private final ArrayList<Component> subcomponents;
	public boolean open;
	private final int height;
	int nameWidth;
	int centeredNameCoords;
	public int settingHeight;

//	public HudComponent hudComponent;

	public int animating;

	boolean hovering;

	Minecraft mc = Minecraft.getMinecraft();

	private static final ResourceLocation opengui = new ResourceLocation("minecraft:opengui.png");

	public Buttons(final Module mod, final Frames parent, final int offset) {

		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		int opY = offset + 12;
		this.nameWidth = 0;
		this.centeredNameCoords = 0;
		this.settingHeight = 12;

		this.hovering = false;
		this.animating = 0;
/*
		if (mod.isHudComponent()){
			this.hudComponent = new HudComponent(this.mod.x.getValue(), this.mod.y.getValue(), this.mod.width, this.mod.height, this, this.mod);
		}

 */

		if (HackHack.getInstance().settingsManager.getSettingsForMod(mod) != null && !HackHack.getInstance().settingsManager.getSettingsForMod(mod).isEmpty()) {
			for (final Setting s : HackHack.getInstance().settingsManager.getSettingsForMod(mod)) {
				switch (s.getType()) {
					case MODE:
						this.subcomponents.add(new ModeComponent((Setting.Mode)s, this, mod, opY));
						break;
					case BOOLEAN:
						this.subcomponents.add(new BooleanComponent((Setting.Boolean)s, this, opY));
						break;
					case DOUBLE:
						this.subcomponents.add(new DoubleComponent((Setting.Double)s, this, opY));
						break;
					case INT:
						this.subcomponents.add(new IntegerComponent((Setting.Integer)s, this, opY));
						break;
				}
				opY += 12;
				settingHeight += 12;
			}
		}
		this.subcomponents.add(new KeybindComponent(this, opY));
		this.height=opY+12-offset;
	}

	@Override
	public void setOff(final int newOff) {
		this.offset = newOff;
		int opY = this.offset + 12;
		for (final Component comp : this.subcomponents) {
			comp.setOff(opY);
//			if (comp instanceof ColorComponent) opY+=60;
			opY += 12;
		}
	}

	@Override
	public void renderComponent() {
		if (this.mod.isEnabled()){
			GlStateManager.pushMatrix();
			mc.renderEngine.bindTexture(new ResourceLocation("minecraft:rainbow.png"));
			Renderer.renderImage(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX(), this.parent.getY() + this.offset + this.parent.getRainbowOffset(), this.parent.getWidth(), 12, 1920f, 1080f);
			Renderer.RenderBoxOutline(1.5, this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, new Color(0, 0, 0));
			GlStateManager.popMatrix();
		} else {
			Renderer.drawRectStatic(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, Renderer.getTransColor(false));
		}
		this.nameWidth = FontUtils.getStringWidth(false, this.mod.getName());
		this.centeredNameCoords = (this.parent.getWidth() - nameWidth) / 2;

		FontUtils.drawStringWithShadow(false, this.mod.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2, Renderer.getFontColor(this.parent.isHovering(), this.parent.getAnimation()).getRGB());

		drawOpenRender(this.parent.getX() + this.parent.getWidth() - 12, this.parent.getY() + this.offset + 2);

		if (isMouseOnButton(this.parent.getMouseX(), this.parent.getMouseY()) && !(this.mod.getDescription()==null)){
			Renderer.drawRectStatic(0, 0, FontUtils.getStringWidth(false, this.mod.getDescription()) + 5, 12, new Color(0, 0, 0, 150));
			FontUtils.drawStringWithShadow(false, this.mod.getDescription(), 2, 2, new Color(255, 255, 255, 255).getRGB());
		}

		if (this.open && !this.subcomponents.isEmpty()) {
			for (final Component comp : this.subcomponents) {
				comp.renderComponent();
			}
		}

		/*
		if (mod.isHudComponent()){
			this.hudComponent = new HudComponent(this.mod.x.getValue(), this.mod.y.getValue(), this.mod.width, this.mod.height, this, this.mod);
		}

		 */
		/*
		if (this.mod.isHudComponent() && this.mod.isEnabled() && ModuleManager.isModuleEnabled("Hud Editor")){
			this.hudComponent.renderComponent();
		}

		 */
	}

	@Override
	public int getHeight() {
		if (this.open) {
			return height;
		}
		return 12;
	}

	public int getButtonHeight() {
		if (this.open) {
			return height;
		}
		return 12;
	}
/*
	public HudComponent getHudComponent() {
		return hudComponent;
	}

 */

	@Override
	public void updateComponent(final int mouseX, final int mouseY) {
		this.isHovered = this.isMouseOnButton(mouseX, mouseY);
		if (!this.subcomponents.isEmpty()) {
			for (final Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}

	@Override
	public void mouseClicked(final int mouseX, final int mouseY, final int button) {
		if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		/*
		if (ModuleManager.isModuleEnabled("Hud Editor")) {
			this.hudComponent.mouseClicked(mouseX, mouseY, button);
		}

		 */

		for (final Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);

		}

	}

	@Override
	public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
		for (final Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void keyTyped(final char typedChar, final int key) {
		for (final Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}



	public boolean isMouseOnButton(final int x, final int y) {
		return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth()&& y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
	}

	public void drawOpenRender(int x, int y){
		GlStateManager.enableAlpha();
		this.mc.getTextureManager().bindTexture(opengui);

		GlStateManager.color(1, 1, 1, 1);
		GL11.glPushMatrix();
//		GlStateManager.enableBlend();
//		GlStateManager.enableAlpha();
//		GL11.glEnable(GL11.GL_LINE_SMOOTH);

//		Renderer.renderImage(x, y, 0, 0, 10, 10, 512, 512);
		Gui.drawScaledCustomSizeModalRect(x,y,0,0,512,512,8,8,512,512);
//		Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, 8, 8, 512f, 512f);
		GL11.glPopMatrix();
		GlStateManager.disableAlpha();
		GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
	}


}

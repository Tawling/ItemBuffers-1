package com.blametaw.gui;

import com.blametaw.itembuffers.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonDecrease extends GuiButton {
	
	protected static final ResourceLocation buttonTextures = new ResourceLocation(Reference.MODID, "textures/gui/buffer_gui.png");

	public GuiButtonDecrease(int buttonId, int x, int y, int widthIn,
			int heightIn) {
		super(buttonId, x, y, widthIn, heightIn, "");
		//Construct Stuff
	}
	
	public GuiButtonDecrease(int buttonId, int x, int y){
		this(buttonId,x,y,18,8);
	}
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 178 + i * 18 - 18, 38, this.width, this.height);
        }
    }

}

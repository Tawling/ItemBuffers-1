package com.blametaw.gui;

import com.blametaw.itembuffers.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiContainerBasicBuffer extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/buffer_gui.png");
	
	private ContainerBasicBuffer container;
	
	public GuiContainerBasicBuffer(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		container = (ContainerBasicBuffer)inventorySlotsIn;
		this.xSize = 176;
        this.ySize = 178;
        
        
	}
	
	@Override
	public void initGui(){
		super.initGui();
		int buttonId = 0;
		for (BufferSlot s : this.container.teSlots){
			this.buttonList.add(new GuiButtonIncrease(2*buttonId,guiLeft + s.xPos - 1,guiTop + s.yPos + 20));
			this.buttonList.add(new GuiButtonDecrease(2*buttonId + 1,guiLeft + s.xPos - 1,guiTop + s.yPos + 37));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		// Bind the image texture of our custom container
				mc.getTextureManager().bindTexture(texture);
				// Draw the image
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
				
				for (BufferSlot s : this.container.teSlots){
					//Draw actual slot
					this.drawTexturedModalRect(guiLeft + s.xPos - 1, guiTop + s.yPos - 1, 178, 0, 18, 18);
					//Draw stack size background
					this.drawTexturedModalRect(guiLeft + s.xPos - 1, guiTop + s.yPos + 28, 178, 29, 18, 9);
					//If filter, draw filter
					if (s.hasFilter()){
						mc.getTextureManager().bindTexture(texture);
						this.drawTexturedModalRect(guiLeft + s.xPos - 1, guiTop + s.yPos + 48,
								178 + (s.getStackFilter() != null && s.getStackFilter().getCount() > 0 ? 18 : 0), 49,
								18, 18);
					}
				}
				for (BufferSlot s : this.container.teSlots){
					//Draw stack size text
					this.fontRendererObj.drawString("" + s.getSlotStackLimit(),
							guiLeft + s.xPos + 12 - this.fontRendererObj.getStringWidth("" + s.getSlotStackLimit())/2,
							guiTop + s.yPos + 29,
							0x4C4C4C);
				}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		for(GuiButton b : this.buttonList){
			b.drawButtonForegroundLayer(mouseX, mouseY);
		}
	}
	@Override
	public void actionPerformed(GuiButton g){
		int slot = g.id/2;
		boolean isIncrement = (g.id % 2 == 0);
		if (isIncrement){
			container.getItemHandler().incrementBufferSize(slot);
		}else {
			container.getItemHandler().decrementBufferSize(slot);
		}
		container.detectAndSendChanges();
	}
}

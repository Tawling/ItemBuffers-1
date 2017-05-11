package com.blametaw.itembuffers.blocks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiContainerBasicBuffer extends GuiContainer {

	public GuiContainerBasicBuffer(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		this.xSize = 176;
        this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		//TODO: Draw anything at all
	}

}

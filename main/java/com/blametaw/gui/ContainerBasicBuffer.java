package com.blametaw.gui;

import java.util.List;

import com.blametaw.itembuffers.blocks.BufferStackHandler;
import com.blametaw.itembuffers.blocks.TileEntityBasicBuffer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerBasicBuffer extends Container {
	
	
	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COL_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_ROW_COUNT * PLAYER_INVENTORY_COL_COUNT;
	private final int PLAYER_SLOT_COUNT = PLAYER_INVENTORY_SLOT_COUNT + HOTBAR_SLOT_COUNT;
	private final int PLAYER_FIRST_SLOT_INDEX = 0;
	private final int TE_FIRST_SLOT_INDEX = PLAYER_FIRST_SLOT_INDEX + PLAYER_SLOT_COUNT;
	private int TE_SLOT_COUNT;
	
	private final int WIDTH = 176;
	private final int HEIGHT = 178;
	
	
	private TileEntityBasicBuffer te;
	private BufferStackHandler handler;
	
	public BufferSlot[] teSlots;
	
	public ContainerBasicBuffer(IInventory playerInv, TileEntityBasicBuffer te){
		this.te = te;
		this.handler = (BufferStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		TE_SLOT_COUNT = handler.getSlots();
		
		teSlots = new BufferSlot[TE_SLOT_COUNT];
		
		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		
		final int HOTBAR_X = 8;
		final int HOTBAR_Y = 154;
		
		final int PLAYER_INVENTORY_X = 8;
		final int PLAYER_INVENTORY_Y = 96;
		//Add hotbar slots
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++){
			int slot = PLAYER_FIRST_SLOT_INDEX + x;
			addSlotToContainer(new Slot(playerInv, slot, HOTBAR_X + SLOT_X_SPACING * x, HOTBAR_Y));
		}
		
		//Add player inventory slots
		for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++){
			for (int x = 0; x < PLAYER_INVENTORY_COL_COUNT; x++){
				int slot = HOTBAR_SLOT_COUNT + x + y * PLAYER_INVENTORY_COL_COUNT;
				addSlotToContainer(new Slot(playerInv, slot, x * SLOT_X_SPACING + PLAYER_INVENTORY_X, y * SLOT_Y_SPACING + PLAYER_INVENTORY_Y));
			}
		}
		
		
		//Add TE slots
		final int TE_SLOT_SPACING = 24;
		final int TE_SLOT_WIDTH = 18;
		final int TE_SLOT_X = WIDTH/2 - ((TE_SLOT_COUNT-1)*TE_SLOT_SPACING + TE_SLOT_WIDTH)/2 + 1;
		final int TE_SLOT_Y = 13;
		for(int x = 0; x < TE_SLOT_COUNT; x++){
			int slot = TE_FIRST_SLOT_INDEX + x;
			BufferSlot bufferSlot = new BufferSlot(handler, x, TE_SLOT_X + x * TE_SLOT_SPACING, TE_SLOT_Y, handler, x);
			teSlots[x] = bufferSlot;
			addSlotToContainer(bufferSlot);
		}
		
		
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot){
		//Shift clicking should do nothing for now I guess
		return ItemStack.EMPTY;
	}
	
	public TileEntityBasicBuffer getTileEntity(){
		return te;
	}
	
	public BufferStackHandler getItemHandler(){
		return handler;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn){
		if (playerIn.world.isRemote){
			//TODO: drop items here?
		}
	}

}

package com.blametaw.gui;

import com.blametaw.itembuffers.blocks.BufferStackHandler;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BufferSlot extends SlotItemHandler {

	private BufferStackHandler handler;
	public int bufferSlotNum;
	public BufferSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition, BufferStackHandler itemHandler, int bufferSlotNum) {
		super(inventoryIn, index, xPosition, yPosition);
		this.handler = itemHandler;
		this.bufferSlotNum = bufferSlotNum;
	}
	
	@Override
	public int getSlotStackLimit(){
		return this.handler.getBufferSize(bufferSlotNum);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return this.handler.isStackValidForSlot(bufferSlotNum, stack);
	}
	
	public boolean hasFilter(){
		return this.handler.isFiltered();
	}
	
	public ItemStack getStackFilter(){
		return this.handler.getFilterForSlot(bufferSlotNum);
	}
	
	public ItemStack getStack(){
		return this.handler.getStackInSlot(bufferSlotNum);
	}
	
	

}

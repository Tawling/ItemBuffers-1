package com.blametaw.itembuffers.blocks;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class BufferStackHandler extends ItemStackHandler {
	protected TileEntity tileEntity;
	
	protected int[] stackLimits;
	
	protected int maxLimit = 64;
	
	public BufferStackHandler(TileEntity tileEntity){
		this(1, 64, tileEntity);
	}
	
	public BufferStackHandler(int size, int maxLimit, TileEntity tileEntity){
		super(size);
		this.tileEntity = tileEntity;
		stackLimits = new int[size];
	}
	
	public boolean isStackValidForSlot(int slot, ItemStack stack){
		if (stack != null && stack.getCount() > 0){
			//if any other stack contains the item, return false
			//if any other stack contains a filter for the item, return false
			return true;
		}
		return false;
	}
	
	protected int getStackLimit(int slot, ItemStack stack){
		return Math.min(getBufferSize(slot),super.getStackLimit(slot, stack));
	}
	
	
	protected void onContentsChanged(int slot){
		tileEntity.getWorld().updateComparatorOutputLevel(tileEntity.getPos(), tileEntity.getWorld().getBlockState(tileEntity.getPos()).getBlock());
	}
	
	public void setBufferSize(int slot, int limit){
		validateSlotIndex(slot);
		this.stackLimits[slot] = limit;
		ejectExcessItems();
	}
	
	public int getBufferSize(int slot){
		validateSlotIndex(slot);
		return this.stackLimits[slot];
	}
	
	protected void ejectExcessItems(){
		for(int i = 0; i < getSlots(); i++){
			ItemStack stack = getStackInSlot(i);
			if (stack.getCount() > getBufferSize(i)){
				BlockPos pos = tileEntity.getPos();
				InventoryHelper.spawnItemStack(tileEntity.getWorld(), pos.getX(), pos.getY(), pos.getZ(), extractItem(i,stack.getCount() - getBufferSize(i), false));
			}
		}
		onContentsChanged(0);
	}
	
	public float getFillPercent(){
		float sum = 0;
		for(int i = 0; i < getSlots(); i++) {
			sum += getFillPercent(i);
		}
		return sum / getSlots();
	}
	
	public float getFillPercent(int slot){
		if (getStackInSlot(slot) == null) return 0.0f;
		float max = Math.min(getStackInSlot(slot).getMaxStackSize(),getBufferSize(slot));
		return max / getStackInSlot(slot).getCount();
	}
	
	@Override
	public NBTTagCompound serializeNBT(){
		NBTTagCompound nbt = super.serializeNBT();
		nbt.setIntArray("stackLimits", stackLimits);
		return nbt;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		stackLimits = nbt.getIntArray("stackLimits");
		super.deserializeNBT(nbt);
	}
}

package com.blametaw.itembuffers.blocks;

import javax.annotation.Nonnull;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class BufferStackHandler extends ItemStackHandler {
	protected TileEntity tileEntity;
	
	protected int[] stackLimits;
	protected boolean useFilters;
	
	protected ItemStack[] filters;
	
	protected int maxLimit = 64;
	
	public BufferStackHandler(int size, int maxLimit, TileEntity tileEntity){
		super(size);
		this.maxLimit = maxLimit;
		this.tileEntity = tileEntity;
		stackLimits = new int[size];
		filters = new ItemStack[size];
		for(int i = 0; i < size; i++){
			stackLimits[i] = maxLimit;
		}
		useFilters = true;
		
	}
	
	public boolean isStackValidForSlot(int slot, ItemStack stack){
		if (stack != null && stack.getCount() > 0){
			//if any other stack contains the item, return false
			//if any other stack contains a filter for the item, return false
			for(int i = 0; i < getSlots(); i++) {
				if (i != slot) {
					//not sure if getCount() is necessary, but I'll leave it
					if (getStackInSlot(i) != null && getStackInSlot(i).getCount() > 0 && getStackInSlot(i).isItemEqual(stack)) {
						return false;
					}
					if (getFilterForSlot(i) != null && getFilterForSlot(i).isItemEqual(stack)) {
						return false;
					}
				}
			}
			if (getFilterForSlot(slot) != null && getFilterForSlot(slot).getCount() > 0) {
				return getFilterForSlot(slot).isItemEqual(stack);
			}
			return true;
		}
		return false;
	}
	
	@Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        
        if (!isStackValidForSlot(slot,stack)) return stack;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }
	
	public boolean isFilterValidForSlot(int slot, ItemStack stack){
		//if any other stack contains the item, return false
		//if any other stack contains a filter for the item, return false
		return false;
	}
	
	@Override
	protected int getStackLimit(int slot, ItemStack stack){
		return Math.min(getBufferSize(slot),super.getStackLimit(slot, stack));
	}
	
	public boolean isFiltered(){
		return useFilters;
	}
	
	public ItemStack getFilterForSlot(int slot){
		//TODO: return filter slot item
		return ItemStack.EMPTY;
	}
	
	protected void onContentsChanged(int slot){
		System.out.println(getFillPercent());
		tileEntity.getWorld().updateComparatorOutputLevel(tileEntity.getPos(), tileEntity.getWorld().getBlockState(tileEntity.getPos()).getBlock());
	}
	
	public void setBufferSize(int slot, int limit){
		validateSlotIndex(slot);
		this.stackLimits[slot] = limit;
		//ejectExcessItems();
	}
	
	public int getBufferSize(int slot){
		validateSlotIndex(slot);
		return this.stackLimits[slot];
	}
	
	public void incrementBufferSize(int slot){
		validateSlotIndex(slot);
		if (this.stackLimits[slot] < maxLimit) {
			this.setBufferSize(slot,this.stackLimits[slot] + 1);
		}
	}
	
	public void decrementBufferSize(int slot){
		validateSlotIndex(slot);
		if (this.stackLimits[slot] > 0) {
			this.setBufferSize(slot,this.stackLimits[slot] - 1);
		}
	}
	
	public void ejectExcessItems(){
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
		return getStackInSlot(slot).getCount() / max;
	}
	
	@Override
	public NBTTagCompound serializeNBT(){
		NBTTagCompound nbt = super.serializeNBT();
		nbt.setIntArray("stackLimits", stackLimits);
		return nbt;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		super.deserializeNBT(nbt);
		System.out.println(nbt);
		stackLimits = nbt.getIntArray("stackLimits");
		System.out.println("STACK LIMITS SIZE: " + stackLimits.length);
	}
}

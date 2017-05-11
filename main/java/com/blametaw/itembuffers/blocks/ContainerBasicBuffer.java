package com.blametaw.itembuffers.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerBasicBuffer extends Container {
	
	private TileEntityBasicBuffer te;
	
	public ContainerBasicBuffer(IInventory playerInv, TileEntityBasicBuffer te){
		te = this.te;
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot){
		//Shift clicking should do nothing for now I guess
		return null;
	}

}

package com.blametaw.gui;

import com.blametaw.itembuffers.blocks.TileEntityBasicBuffer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class BasicBufferGuiHandler implements IGuiHandler {

	private static final int GUIID = 1;
	public static int getGuiID(){return GUIID;}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileEntityBasicBuffer) {
			TileEntityBasicBuffer tebb = (TileEntityBasicBuffer) tileEntity;
			return new ContainerBasicBuffer(player.inventory, tebb);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileEntityBasicBuffer) {
			TileEntityBasicBuffer tebb = (TileEntityBasicBuffer) tileEntity;
			return new GuiContainerBasicBuffer(new ContainerBasicBuffer(player.inventory, tebb));
		}
		return null;
	}

}

package com.blametaw.itembuffers.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.blametaw.itembuffers.ItemBuffers;
import com.blametaw.itembuffers.Reference;

public class BlockBasicBuffer extends BlockContainer {

	public BlockBasicBuffer() {
		super(Material.IRON);
		setUnlocalizedName(Reference.ItemBufferBlocks.BASICBUFFER.getUnlocalizedName());
		setRegistryName(Reference.ItemBufferBlocks.BASICBUFFER.getRegistryName());
		
		setResistance(6.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBasicBuffer();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityBasicBuffer te = (TileEntityBasicBuffer) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int slot = 0; slot < handler.getSlots(); slot++){
			ItemStack stack = handler.getStackInSlot(slot);
			InventoryHelper.spawnItemStack(world,pos.getX(),pos.getY(),pos.getZ(),stack);
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.SOLID;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos){
		TileEntityBasicBuffer te = (TileEntityBasicBuffer) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if (handler.getStackInSlot(0) != null && handler.getStackInSlot(0).getCount() > 0){
			float fill = ((BufferStackHandler)handler).getFillPercent();
			return (int)Math.floor(fill*15);
		}
		return 0;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ){
		if (worldIn.isRemote) return true;
		playerIn.openGui(ItemBuffers.instance, BasicBufferGuiHandler.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}

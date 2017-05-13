package com.blametaw.itembuffers.blocks;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;

public class BufferEjectMessage implements IMessage {

	public BlockPos pos; //TileEntity Block Coordinates
	public int worldId;
	public BufferEjectMessage(int x, int y, int z, int worldId){
		this.pos = new BlockPos(x,y,z);
		this.worldId = worldId;
	}
	
	public BufferEjectMessage(BlockPos pos, int worldId){
		this.pos = pos;
		this.worldId = worldId;
	}
	
	public BufferEjectMessage(){
		//Default constructor required for receiving message
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.worldId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(this.worldId);
	}
	
	public static class MyMessageHandler implements IMessageHandler<BufferEjectMessage, IMessage> {
		
		@Override
		public IMessage onMessage(final BufferEjectMessage message, MessageContext ctx) {
			DimensionManager.getWorld(message.worldId).addScheduledTask(new Runnable(){
				@Override
				public void run() {
					TileEntityBasicBuffer te = (TileEntityBasicBuffer) DimensionManager.getWorld(message.worldId).getTileEntity(message.pos);
					BufferStackHandler handler = (BufferStackHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
					
					handler.ejectExcessItems();
				}
				
			});
			return null;
		}
		
	}

}

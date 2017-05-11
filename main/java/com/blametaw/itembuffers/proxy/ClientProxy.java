package com.blametaw.itembuffers.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.blametaw.itembuffers.ItemBuffers;
import com.blametaw.itembuffers.blocks.BasicBufferGuiHandler;

import init.ModBlocks;

public class ClientProxy implements CommonProxy {

	@Override
	public void init(){
		ModBlocks.registerRenders();
		NetworkRegistry.INSTANCE.registerGuiHandler(ItemBuffers.instance, new BasicBufferGuiHandler());
	}
}

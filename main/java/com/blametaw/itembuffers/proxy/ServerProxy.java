package com.blametaw.itembuffers.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.blametaw.gui.BasicBufferGuiHandler;
import com.blametaw.itembuffers.ItemBuffers;

public class ServerProxy implements CommonProxy {
	
	@Override
	public void init(){
		NetworkRegistry.INSTANCE.registerGuiHandler(ItemBuffers.instance, new BasicBufferGuiHandler());
	}

}

package com.blametaw.itembuffers;

public class Reference {
	
	public static final String MODID = "itembuffers";
	public static final String NAME = "Item Buffers";
	public static final String VERSION = "0.1";
	public static final String ACCEPTED_VERSIONS = "[1.11.2]";
	
	public static final String CLIENT_PROXY_CLASS = "com.blametaw.itembuffers.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.blametaw.itembuffers.proxy.ServerProxy";
	
	
	public static enum ItemBufferBlocks {
		BASICBUFFER("blockbasicbuffer", "blockbasicbuffer");
		
		private String unlocalizedName;
		private String registryName;
		
		ItemBufferBlocks(String unlocalizedName, String registryName){
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}
		
		public String getUnlocalizedName(){
			return this.unlocalizedName;
		}
		
		public String getRegistryName(){
			return this.registryName;
		}
	}
}

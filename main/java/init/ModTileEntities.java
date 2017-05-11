package init;

import com.blametaw.itembuffers.Reference;
import com.blametaw.itembuffers.blocks.TileEntityBasicBuffer;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	public static void init(){
		GameRegistry.registerTileEntity(TileEntityBasicBuffer.class, Reference.MODID + "blockbasicbuffer");
	}
}

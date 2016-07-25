package zairus.uhcutils.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import zairus.uhcutils.UHCUtils;
import zairus.uhcutils.UUConstants;
import zairus.uhcutils.tileentity.TileEntityUHCController;

public class UUBlocks
{
	public static final Block UHC_CONTROLLER;
	
	static
	{
		UHC_CONTROLLER = new UHCController().setRegistryName(new ResourceLocation(UUConstants.MODID, "uhccontroller")).setUnlocalizedName("uhccontroller");
	}
	
	public static void register()
	{
		UHCUtils.proxy.registerBlock(UHC_CONTROLLER, "uhccontroller", TileEntityUHCController.class, "tileEntityUHCController");
	}
}

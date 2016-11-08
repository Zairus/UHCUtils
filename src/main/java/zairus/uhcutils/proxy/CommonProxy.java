package zairus.uhcutils.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.uhcutils.UUConstants;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	public void init(FMLInitializationEvent e)
	{
		;
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	public void registerItem(Item item, String name, int meta, boolean model)
	{
		if (meta == 0)
			GameRegistry.register(item);
	}
	
	public void registerBlock(Block block, String name)
	{
		GameRegistry.register(block);
		registerItem(new ItemBlock(block).setRegistryName(new ResourceLocation(UUConstants.MODID, name)), name, 0, true);
	}
	
	public void registerBlock(Block block, String name, Class<? extends TileEntity> clazz, String tileEntityId)
	{
		registerBlock(block, name);
		GameRegistry.registerTileEntity(clazz, tileEntityId);
	}
}

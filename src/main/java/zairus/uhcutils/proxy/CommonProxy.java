package zairus.uhcutils.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
			GameRegistry.registerItem(item);
	}
	
	public void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block);
		//registerItem(Item.getItemFromBlock(block), name, 0, true); //.setRegistryName(new ResourceLocation(UUConstants.MODID, name))
	}
	
	public void registerBlock(Block block, String name, Class<? extends TileEntity> clazz, String tileEntityId)
	{
		registerBlock(block, name);
		GameRegistry.registerTileEntity(clazz, tileEntityId);
	}
}

package zairus.uhcutils;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zairus.uhcutils.block.UUBlocks;
import zairus.uhcutils.gui.GuiHandler;
import zairus.uhcutils.proxy.CommonProxy;
import zairus.uhcutils.util.network.PacketPipeline;

@Mod(modid = UUConstants.MODID, name = UUConstants.NAME, version = UUConstants.VERSION)
public class UHCUtils
{
	@Mod.Instance(UUConstants.MODID)
	public static UHCUtils instance;
	
	@SidedProxy(clientSide = UUConstants.CLIENT_PROXY, serverSide = UUConstants.COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static PacketPipeline packetPipeline = new PacketPipeline();
	
	public static CreativeTabs tabUHC = new CreativeTabs("uhcutils") {
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(UUBlocks.UHC_CONTROLLER);
		}
	};
	
	public static Logger logger;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		UHCUtils.proxy.preInit(event);
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
		UHCUtils.proxy.init(event);
		UHCUtils.packetPipeline.initalise();
		
		UUBlocks.register();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(UHCUtils.instance, new GuiHandler());
    }
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		UHCUtils.proxy.postInit(event);
		UHCUtils.packetPipeline.postInitialise();
	}
}

package zairus.uhcutils.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zairus.uhcutils.client.gui.GuiUHCController;
import zairus.uhcutils.tileentity.TileEntityUHCController;

public class GuiHandler implements IGuiHandler
{
	public static final int GUI_UHCCONTROLLER_ID = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case GUI_UHCCONTROLLER_ID:
			TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
			if (te instanceof TileEntityUHCController)
			{
				return te;
			}
			break;
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case GUI_UHCCONTROLLER_ID:
			TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
			if (te instanceof TileEntityUHCController)
			{
				return new GuiUHCController((TileEntityUHCController)te);
			}
			break;
		}
		
		return null;
	}
}

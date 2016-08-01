package zairus.uhcutils.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import zairus.uhcutils.tileentity.TileEntityUHCController;

public class GlobalData
{
	public static GlobalData instance = new GlobalData();
	
	public NBTTagCompound controllerList = new NBTTagCompound();
	
	public int combatTime;
	public int fireworkTime;
	public int redstoneTime;
	
	public List<TileEntity> getControllersForTeamPrefix(World world, String prefix)
	{
		List<TileEntity> list = new ArrayList<TileEntity>();
		
		Set<String> keys = controllerList.getKeySet();
		
		for (String key : keys)
		{
			NBTTagCompound element = (NBTTagCompound)controllerList.getTag(key);
			
			EnumUHCGroups group = EnumUHCGroups.fromMeta(element.getInteger("group"));
			
			if (group.getColorPrefix() != "" && prefix.contains(group.getColorPrefix()))
			{
				int x = element.getInteger("xPos");
				int y = element.getInteger("yPos");
				int z = element.getInteger("zPos");
				
				TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
				if (te instanceof TileEntityUHCController)
				{
					list.add(te);
				}
			}
		}
		
		return list;
	}
}

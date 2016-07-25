package zairus.uhcutils.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityUHCController extends TileEntity implements ITickable
{
	private int group = 0;
	
	public TileEntityUHCController ()
	{
		;
	}
	
	@Override
	public void update()
	{
		++group;
		
		if (group > 16)
			group = 0;
		
		this.worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
	}
	
	public int getGroup()
	{
		return this.group;
	}
}

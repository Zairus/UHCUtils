package zairus.uhcutils.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import zairus.uhcutils.tileentity.TileEntityUHCController;

public class UUControllerPacket extends AbstractPacket
{
	private int x;
	private int y;
	private int z;
	
	private int group;
	
	private int cTime;
	private int fTime;
	private int rTime;
	
	private boolean cOn;
	private boolean fOn;
	private boolean rOn;
	
	public UUControllerPacket()
	{
		;
	}
	
	public UUControllerPacket(int x, int y, int z, int group, int cTime, int fTime, int rTime, boolean cOn, boolean fOn, boolean rOn)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.group = group;
		
		this.cTime = cTime;
		this.fTime = fTime;
		this.rTime = rTime;
		
		this.cOn = cOn;
		this.fOn = fOn;
		this.rOn = rOn;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		
		buffer.writeInt(group);
		
		buffer.writeInt(cTime);
		buffer.writeInt(fTime);
		buffer.writeInt(rTime);
		
		buffer.writeBoolean(cOn);
		buffer.writeBoolean(fOn);
		buffer.writeBoolean(rOn);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		
		group = buffer.readInt();
		
		cTime = buffer.readInt();
		fTime = buffer.readInt();
		rTime = buffer.readInt();
		
		cOn = buffer.readBoolean();
		fOn = buffer.readBoolean();
		rOn = buffer.readBoolean();
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
		doSync(player);
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		doSync(player);
	}
	
	private void doSync(EntityPlayer player)
	{
		TileEntity tileEntity = player.worldObj.getTileEntity(new BlockPos(x, y, z));
		
		if (tileEntity instanceof TileEntityUHCController)
		{
			TileEntityUHCController controller = (TileEntityUHCController)tileEntity;
			controller.setGroup(group);
			controller.setTime(0, cTime);
			controller.setTime(1, fTime);
			controller.setTime(2, rTime);
			controller.setTaskFlags(cOn, fOn, rOn);
		}
	}
}

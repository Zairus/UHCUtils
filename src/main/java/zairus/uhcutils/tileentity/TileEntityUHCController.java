package zairus.uhcutils.tileentity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import zairus.uhcutils.UHCUtils;
import zairus.uhcutils.block.UUBlocks;
import zairus.uhcutils.util.GlobalData;

public class TileEntityUHCController extends TileEntity implements ITickable, ICommandSender
{
	private int group = 0;
	private UUID controllerUUID = null;
	private int internalTime;
	private boolean started = false;
	private int countdown = 3;
	private int countdownSeconds = 0;
	private int combatSeconds = 0;
	private int fireworkSeconds = 0;
	private int fireworkCountdown = 0;
	private int fireworkCountdownSeconds = 0;
	private int redstoneSeconds = 0;
	private int redstoneCountdown = 0;
	private int redstoneCountdownSeconds = 0;
	
	private int updateSeconds = 0;
	
	public boolean redstoneOn = false;
	
	private boolean[] taskFlags = {false, false, false};
	
	public TileEntityUHCController ()
	{
		;
	}
	
	public TileEntityUHCController setUID(UUID id)
	{
		this.controllerUUID = id;
		return this;
	}
	
	public UUID getUID()
	{
		return this.controllerUUID;
	}
	
	@Override
	public void update()
	{
		if (!this.worldObj.isRemote)
			checkAndAddToList();
		
		++internalTime;
		
		if (group == 0)
		{
			if (!started && this.taskFlags[0])
			{
				if (countdown <= 0 && getEllapsedSeconds() - countdownSeconds > 1)
				{
					UHCUtils.logger.info("startng");
					countdownSeconds = getEllapsedSeconds();
					showTitle("Starting!");
					started = true;
				}
				
				if (countdown > 0 && getEllapsedSeconds() - countdownSeconds > 1)
				{
					countdownSeconds = getEllapsedSeconds();
					showTitle(countdown + "");
					--countdown;
				}
			}
			else if (this.taskFlags[0])
			{
				if (getEllapsedSeconds() - combatSeconds > GlobalData.instance.combatTime)
				{
					combatSeconds = getEllapsedSeconds();
					executeCombat();
				}
			}
			
			if (fireworkCountdown > 0 && getEllapsedSeconds() - fireworkCountdownSeconds > 1)
			{
				--fireworkCountdown;
				fireworkCountdownSeconds = getEllapsedSeconds();
				executeFireworks(this.worldObj.rand);
			}
			
			if (this.taskFlags[1])
			{
				if (getEllapsedSeconds() - fireworkSeconds > GlobalData.instance.fireworkTime)
				{
					fireworkSeconds = getEllapsedSeconds();
					fireworkCountdown = 3 + this.worldObj.rand.nextInt(4);
				}
			}
			
			if (redstoneCountdown > 0 && getEllapsedSeconds() - redstoneCountdownSeconds > 1)
			{
				--redstoneCountdown;
				redstoneCountdownSeconds = getEllapsedSeconds();
				executeRedstone(true);
				
				if (redstoneCountdown <= 0)
					executeRedstone(false);
			}
			
			if (this.taskFlags[2])
			{
				if (getEllapsedSeconds() - redstoneSeconds > GlobalData.instance.redstoneTime)
				{
					redstoneSeconds = getEllapsedSeconds();
					redstoneCountdown = 3;
				}
			}
		}
		
		if (getEllapsedSeconds() - updateSeconds > 5)
		{
			updateSeconds = getEllapsedSeconds();
			updateMe();
		}
	}
	
	private void executeRedstone(boolean active)
	{
		List<TileEntity> cont = GlobalData.instance.getControllersForTeamPrefix(this.worldObj, "r");
		
		for (int i = 0; i < cont.size(); ++i)
		{
			TileEntity te = cont.get(i);
			if (te instanceof TileEntityUHCController)
			{
				TileEntityUHCController controllerTE = (TileEntityUHCController)te;
				controllerTE.redstoneOn = active;
				
				this.markDirty();
				this.worldObj.notifyNeighborsOfStateChange(controllerTE.getPos(), Blocks.REDSTONE_WIRE);
			}
		}
	}
	
	private void executeFireworks(Random rand)
	{
		List<EntityPlayer> players = this.worldObj.playerEntities;
		
		ItemStack stack = new ItemStack(Items.FIREWORKS);
		
		for (EntityPlayer player : players)
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setTag("Fireworks", new NBTTagCompound());
			tag.getCompoundTag("Fireworks").setByte("Flight", (byte)rand.nextInt(3));
			NBTTagList explosions = new NBTTagList();
			
			int explCount = 2 + rand.nextInt(6);
			
			for (int e = 0; e < explCount; ++e)
			{
				NBTTagCompound explData = new NBTTagCompound();
				
				explData.setInteger("Flicker", rand.nextInt(5));
				explData.setInteger("Trail", rand.nextInt(5));
				explData.setInteger("Type", rand.nextInt(12));
				
				int colCount = 3 + rand.nextInt(9);
				int[] col = new int[colCount];
				for (int eColors = 0; eColors < colCount; ++ eColors)
					col[eColors] = rand.nextInt(16777215);
				explData.setIntArray("Colors", col);
				
				colCount = rand.nextInt(9);
				col = new int[colCount];
				for (int eColors = 0; eColors < colCount; ++ eColors)
					col[eColors] = rand.nextInt(16777215);
				explData.setIntArray("FadeColors", col);
				
				explosions.appendTag(explData);
			}
			tag.getCompoundTag("Fireworks").setTag("Explosions", explosions);
			stack.setTagCompound(tag);
			
			EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(
					this.worldObj, 
					(double)((float)player.posX), 
					(double)((float)player.posY + 1), 
					(double)((float)player.posZ), 
					stack);
			
			if (!this.worldObj.isRemote)
				this.worldObj.spawnEntityInWorld(entityfireworkrocket);
		}
	}
	
	private void executeCombat()
	{
		List<EntityPlayer> players = this.worldObj.playerEntities;
		
		for (EntityPlayer player : players)
		{
			Team t = player.getTeam();
			if (t != null)
			{
				if (t instanceof ScorePlayerTeam)
				{
					ScorePlayerTeam team = (ScorePlayerTeam)t;
					
					List<TileEntity> cont = GlobalData.instance.getControllersForTeamPrefix(this.worldObj, team.getColorPrefix());
					
					if (cont.size() > 0)
					{
						TileEntity targetCont = cont.get(this.worldObj.rand.nextInt(cont.size()));
						player.setPosition(targetCont.getPos().getX(), targetCont.getPos().getY() + 1, targetCont.getPos().getZ());
					}
				}
			}
		}
		
		/*
		§
		
		"black"=0        black
		"dark_blue"=1    blue
		"dark_green"=2   green
		"dark_aqua"=3   cyan?
		"dark_red"=4     brown?
		"dark_purple"=5   purple
		"gold"=6        orange
		"gray"=7        light gray
		"dark_gray"=8    gray
		"blue"=9     light blue
		"green"=a     llime
		"aqua"=b       magenta
		"red"=c         red
		"light_purple"=d   pink
		"yellow"=e        yellow
		"white"=f        white
		*/
	}
	
	private void showTitle(String text)
	{
		executeCommand("title @a title [{text:\"" + text + "\"}]");
	}
	
	private void executeCommand(String command)
	{
		if (this.worldObj != null)
			if (this.worldObj.getMinecraftServer() != null)
				if (this.worldObj.getMinecraftServer().getCommandManager() != null)
					this.worldObj.getMinecraftServer().getCommandManager().executeCommand(this, command);
	}
	
	public int getEllapsedSeconds()
	{
		return MathHelper.floor_float(((float)this.internalTime / 20.0F));
	}
	
	private void checkAndAddToList()
	{
		if (this.controllerUUID != null && !keyExists())
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setInteger("group", this.group);
			tag.setInteger("xPos", this.getPos().getX());
			tag.setInteger("yPos", this.getPos().getY());
			tag.setInteger("zPos", this.getPos().getZ());
			
			GlobalData.instance.controllerList.setTag(this.controllerUUID.toString(), tag);
		}
	}
	
	public boolean getTaskFlag(int taskIndex)
	{
		if (taskIndex > 2)
			taskIndex = 2;
		
		return this.taskFlags[taskIndex];
	}
	
	public void setTaskFlags(boolean combat, boolean firework, boolean redstone)
	{
		this.taskFlags[0] = combat;
		this.taskFlags[1] = firework;
		this.taskFlags[2] = redstone;
		
		if (combat)
		{
			this.started = false;
			this.countdownSeconds = 0;
			this.countdown = 3;
		}
	}
	
	public void removeFromList()
	{
		if (this.controllerUUID != null && keyExists())
		{
			GlobalData.instance.controllerList.removeTag(this.controllerUUID.toString());
		}
	}
	
	public void setGroup(int g)
	{
		this.group = g;
		if (keyExists())
		{
			((NBTTagCompound)GlobalData.instance.controllerList.getTag(this.controllerUUID.toString())).setInteger("group", this.group);
		}
		updateMe();
	}
	
	private boolean keyExists()
	{
		return GlobalData.instance.controllerList.hasKey(this.controllerUUID.toString());
	}
	
	public int getGroup()
	{
		updateMe();
		return this.group;
	}
	
	public void setTime(int type, int time)
	{
		time = (time < 0)? 0 : time;
		switch(type)
		{
		case 1:
			GlobalData.instance.fireworkTime = time;
			break;
		case 2:
			GlobalData.instance.redstoneTime = time;
			break;
		default:
			GlobalData.instance.combatTime = time;
			break;
		}
	}
	
	public int getTime(int type)
	{
		int time;
		
		switch(type)
		{
		case 1:
			time = GlobalData.instance.fireworkTime;
			break;
		case 2:
			time = GlobalData.instance.redstoneTime;
			break;
		default:
			time = GlobalData.instance.combatTime;
			break;
		}
		
		return time;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.group = compound.getInteger("group");
		
		String stringId = compound.getString("uid");
		UUID uid;
		if (stringId == null || stringId == "")
			uid = MathHelper.getRandomUuid(new Random());
		else
			uid = UUID.fromString(stringId);
		this.controllerUUID = uid;
		
		GlobalData.instance.combatTime = compound.getInteger("combatTime");
		GlobalData.instance.fireworkTime = compound.getInteger("fireworkTime");
		GlobalData.instance.redstoneTime = compound.getInteger("redstoneTime");
		
		if (this.group == 0 && compound.hasKey("controllerList"))
		{
			if (GlobalData.instance.controllerList.getKeySet().size() == 0)
			{
				GlobalData.instance.controllerList = (NBTTagCompound)compound.getTag("controllerList");
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("group", this.group);
		compound.setString("uid", this.controllerUUID.toString());
		
		compound.setInteger("combatTime", GlobalData.instance.combatTime);
		compound.setInteger("fireworkTime", GlobalData.instance.fireworkTime);
		compound.setInteger("redstoneTime", GlobalData.instance.redstoneTime);
		
		if (this.group == 0)
			compound.setTag("controllerList", GlobalData.instance.controllerList);
		
		return compound;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncableDataToNBT(syncData);
		return new SPacketUpdateTileEntity(this.getPos(), 1, syncData);
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}
	
	protected void writeSyncableDataToNBT(NBTTagCompound syncData)
	{
		syncData.setInteger("group", this.group);
		syncData.setInteger("cTime", GlobalData.instance.combatTime);
		syncData.setInteger("fTime", GlobalData.instance.fireworkTime);
		syncData.setInteger("rTime", GlobalData.instance.redstoneTime);
	}
	
	protected void readSyncableDataFromNBT(NBTTagCompound syncData)
	{
		this.group = syncData.getInteger("group");
	}
	
	private void updateMe()
	{
		this.markDirty();
		this.worldObj.markBlockRangeForRenderUpdate(getPos().add(-1, -1, -1), getPos().add(1, 1, 1));
		
		IBlockState state = this.worldObj.getBlockState(getPos());
		
		this.worldObj.notifyBlockUpdate(getPos(), state, state, 0);
	}
	
	@Override
	public String getName()
	{
		return "UHC Controller";
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(int permLevel, String commandName)
	{
		return true;
	}
	
	@Override
	public BlockPos getPosition()
	{
		return getPos();
	}
	
	@Override
	public World getEntityWorld()
	{
		return this.worldObj;
	}
	
	@Override
	public Entity getCommandSenderEntity()
	{
		return new EntityItem(this.worldObj, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ() ,new ItemStack(UUBlocks.UHC_CONTROLLER));
	}
	
	@Override
	public boolean sendCommandFeedback()
	{
		return false;
	}
	
	@Override
	public void setCommandStat(Type type, int amount)
	{
	}
	
	@Override
	public void addChatMessage(ITextComponent component)
	{
	}
	
	@Override
	public Vec3d getPositionVector()
	{
		return new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ());
	}
	
	@Override
	public MinecraftServer getServer()
	{
		return this.worldObj.getMinecraftServer();
	}
}

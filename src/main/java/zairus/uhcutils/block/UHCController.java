package zairus.uhcutils.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zairus.uhcutils.UHCUtils;
import zairus.uhcutils.gui.GuiHandler;
import zairus.uhcutils.tileentity.TileEntityUHCController;
import zairus.uhcutils.util.EnumUHCGroups;

public class UHCController extends Block implements ITileEntityProvider
{
	public static final PropertyEnum<EnumUHCGroups> GROUP = PropertyEnum.create("group", EnumUHCGroups.class);
	
	public UHCController()
	{
		super(Material.ROCK);
		this.setSoundType(SoundType.GLASS);
		this.setCreativeTab(UHCUtils.tabUHC);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(GROUP, EnumUHCGroups.MAIN));
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityUHCController().setUID(MathHelper.getRandomUuid(world.rand));
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityUHCController)
			{
				((TileEntityUHCController)te).removeFromList();
			}
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(GROUP).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(GROUP, EnumUHCGroups.fromMeta(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return MathHelper.clamp_int(state.getValue(GROUP).getIndex(), 0, 15);
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {GROUP});
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		EnumUHCGroups group = EnumUHCGroups.MAIN;
		
		if (te != null && te instanceof TileEntityUHCController)
		{
			TileEntityUHCController uhcc = (TileEntityUHCController)te;
			group = EnumUHCGroups.fromMeta(uhcc.getGroup());
		}
		
		return state.withProperty(GROUP, group);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote && player.capabilities.isCreativeMode)
		{
			player.openGui(UHCUtils.instance, GuiHandler.GUI_UHCCONTROLLER_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		int power = 0;
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityUHCController)
		{
			TileEntityUHCController cont = (TileEntityUHCController)te;
			EnumUHCGroups g = EnumUHCGroups.fromMeta(cont.getGroup());
			
			if (g.getIndex() == 17 && cont.redstoneOn)
				power = 15;
			else
				power = 0;
		}
		
		return power;
	}
}

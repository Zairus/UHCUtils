package zairus.uhcutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zairus.uhcutils.UHCUtils;
import zairus.uhcutils.tileentity.TileEntityUHCController;

public class UHCController extends Block implements ITileEntityProvider
{
	public static final PropertyEnum<EnumUHCGroups> GROUP = PropertyEnum.create("group", EnumUHCGroups.class);
	
	public UHCController()
	{
		super(Material.rock);
		this.setStepSound(Block.soundTypeGlass);
		this.setCreativeTab(UHCUtils.tabUHC);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setHardness(1.9F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(GROUP, EnumUHCGroups.MAIN));
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityUHCController();
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
		return state.getValue(GROUP).getIndex();
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {GROUP});
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
	
	public enum EnumUHCGroups implements IStringSerializable
	{
		MAIN("main", 0),
		WHITE("white", 1),
		ORANGE("orange", 2),
		MAGENTA("magenta", 3),
		LIGHTBLUE("lightblue", 4),
		YELLOW("yellow", 5),
		LIME("lime", 6),
		PINK("pink", 7),
		GRAY("gray", 8),
		LIGHTGRAY("lightgray", 9),
		CYAN("cyan", 10),
		PURPLE("purple", 11),
		BLUE("blue", 12),
		BROWN("brown", 13),
		GREEN("green", 14),
		RED("red", 15),
		BLACK("black", 16);
		
		private String name;
		private int index;
		
		private EnumUHCGroups(String name, int index)
		{
			this.name = name;
			this.index = index;
		}
		
		public static EnumUHCGroups fromMeta(int meta)
		{
			switch(meta)
			{
			case 1:
				return WHITE;
			case 2:
				return ORANGE;
			case 3:
				return MAGENTA;
			case 4:
				return LIGHTBLUE;
			case 5:
				return YELLOW;
			case 6:
				return LIME;
			case 8:
				return PINK;
			case 9:
				return GRAY;
			case 10:
				return LIGHTGRAY;
			case 11:
				return CYAN;
			case 12:
				return PURPLE;
			case 13:
				return BROWN;
			case 14:
				return GREEN;
			case 15:
				return RED;
			case 16:
				return BLACK;
			default:
				return MAIN;
			}
		}
		
		public int getIndex()
		{
			return this.index;
		}
		
		@Override
		public String getName()
		{
			return this.name;
		}
		
		@Override
		public String toString()
		{
			return this.name;
		}
	}
}

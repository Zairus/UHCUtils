package zairus.uhcutils.util;

import net.minecraft.util.IStringSerializable;

public enum EnumUHCGroups implements IStringSerializable
{
	MAIN("main", 0, ""),
	WHITE("white", 1, "f"),
	ORANGE("orange", 2, "6"),
	MAGENTA("magenta", 3, "b"),
	LIGHTBLUE("lightblue", 4, "9"),
	YELLOW("yellow", 5, "e"),
	LIME("lime", 6, "a"),
	PINK("pink", 7, "d"),
	GRAY("gray", 8, "8"),
	LIGHTGRAY("lightgray", 9, "7"),
	CYAN("cyan", 10, "3"),
	PURPLE("purple", 11, "5"),
	BLUE("blue", 12, "1"),
	BROWN("brown", 13, "4"),
	GREEN("green", 14, "2"),
	RED("red", 15, "c"),
	BLACK("black", 16, "0"),
	REDSTONE("redstone", 17, "r");
	
	/*
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
	
	private String name;
	private int index;
	private String colorPrefix;
	
	private EnumUHCGroups(String name, int index, String colorPrefix)
	{
		this.name = name;
		this.index = index;
		this.colorPrefix = colorPrefix;
	}
	
	public static EnumUHCGroups fromMeta(int meta)
	{
		switch(meta)
		{
		case 1:
			return BLACK;
		case 2:
			return BLUE;
		case 3:
			return GREEN;
		case 4:
			return MAGENTA;
		case 5:
			return BROWN;
		case 6:
			return PURPLE;
		case 7:
			return ORANGE;
		case 8:
			return LIGHTGRAY;
		case 9:
			return GRAY;
		case 10:
			return LIGHTBLUE;
		case 11:
			return LIME;
		case 12:
			return CYAN;
		case 13:
			return RED;
		case 14:
			return PINK;
		case 15:
			return YELLOW;
		case 16:
			return WHITE;
		case 17:
			return REDSTONE;
		default:
			return MAIN;
		}
	}
	
	public String getColorPrefix()
	{
		return this.colorPrefix;
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

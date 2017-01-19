package com.bioxx.tfc.api;

import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class Metal
{
	public String name;
	public Item meltedItem;
	public Item ingot;
	public boolean canUse = true;

    /** The Icons for this metal. */
	private IIcon stillIcon;
    private IIcon flowingIcon;
	private int color = 0xffffff;
	
	public Metal(String name)
	{
		this.name = name;
	}

	public Metal(String name, Item m, Item i)
	{
		this(name);
		meltedItem = m;
		ingot = i;
	}

	public Metal(String name, Item m, Item i, boolean use)
	{
		this(name);
		meltedItem = m;
		ingot = i;
		canUse = use;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public Metal setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	public IIcon getStillIcon()
	{
		if(this.stillIcon == null)
			return TFCBlocks.hotWater.getIcon(0, 0);
		return this.stillIcon;
	}
	
	public IIcon getFlowingIcon()
	{
		if(this.flowingIcon == null)
			return TFCBlocks.hotWater.getIcon(2, 0);
		return this.flowingIcon;
	}
}

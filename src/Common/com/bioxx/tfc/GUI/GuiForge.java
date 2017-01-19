package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerForge;
import com.bioxx.tfc.TileEntities.TEForge;

public class GuiForge extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_forge.png");

	private TEForge forgeTE;

	public GuiForge(InventoryPlayer inventoryplayer, TEForge te, World world, int x, int y, int z)
	{
		super(new ContainerForge(inventoryplayer, te, world, x, y, z), 176, 85);
		forgeTE = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (forgeTE != null)
		{
			int scale = forgeTE.getTemperatureScaled(49);
			drawTexturedModalRect(guiLeft + 8, guiTop + 65 - scale, 185, 31, 15, 6);
		}
	}

	@Override
	protected boolean checkHotbarKeys(int keycode)
	{
		//Disabled to prevent players placing stacks into the forge
		// return super.checkHotbarKeys(keycode);
		return false;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		if (forgeTE != null && forgeTE.fuelTimeLeft > 0)
			this.fontRendererObj.drawString(""+forgeTE.fuelTimeLeft, 28, 70, 0x000000);
	}
	
	@Override
	public void drawScreen(int i, int j, float par3) 
	{
		super.drawScreen(i, j, par3);

		if (forgeTE != null && this.mouseInRegion(11, 17, 9, 52, i, j))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+(int)forgeTE.fireTemp);
			
			this.drawHoveringText(list, i, j+8, this.fontRendererObj);
		}
	}
	
	@Override
	public void updateScreen()
	{
		TileEntity tileEntity = forgeTE;
		if (tileEntity.isInvalid())
		{
			World world = tileEntity.getWorldObj();
			tileEntity = world.getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			
			if (tileEntity instanceof TEForge && !tileEntity.isInvalid())
				forgeTE = (TEForge)tileEntity;
		}
	}
}

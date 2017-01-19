package com.bioxx.tfc.GUI;

import java.util.ArrayList;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerFirepit;
import com.bioxx.tfc.TileEntities.TEFirepit;

public class GuiFirepit extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_firepit.png");

	private TEFirepit firepitTE;

	public GuiFirepit(InventoryPlayer inventoryplayer, TEFirepit te, World world, int x, int y, int z)
	{
		super(new ContainerFirepit(inventoryplayer, te, world, x, y, z), 176, 85);
		firepitTE = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (firepitTE != null) // Fixes OpenEye-reported NPE
		{
			int scale = firepitTE.getTemperatureScaled(49);
			drawTexturedModalRect(guiLeft + 30, guiTop + 65 - scale, 185, 31, 15, 6);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		if (firepitTE.fuelTimeLeft > 0)
			this.fontRendererObj.drawString(""+firepitTE.fuelTimeLeft, 50, 70, 0x000000);
	}
	
	@Override
	public void drawScreen(int i, int j, float par3) 
	{
		super.drawScreen(i, j, par3);

		if (this.mouseInRegion(33, 17, 9, 52, i, j))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+(int)firepitTE.fireTemp);
			
			this.drawHoveringText(list, i, j+8, this.fontRendererObj);
		}
	}
	
	@Override
	public void updateScreen()
	{
		TileEntity tileEntity = firepitTE;
		if (tileEntity.isInvalid())
		{
			World world = tileEntity.getWorldObj();
			tileEntity = world.getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			
			if (tileEntity instanceof TEFirepit && !tileEntity.isInvalid())
				firepitTE = (TEFirepit)tileEntity;
		}
	}
}

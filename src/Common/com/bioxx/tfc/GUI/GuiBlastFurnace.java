package com.bioxx.tfc.GUI;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerBlastFurnace;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.HeatItemDetails;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.api.TFCOptions;

public class GuiBlastFurnace extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_blast_furnace.png");

	private TEBlastFurnace blastFurnaceTE;

	public GuiBlastFurnace(InventoryPlayer inventoryplayer, TEBlastFurnace te, World world, int x, int y, int z)
	{
		super(new ContainerBlastFurnace(inventoryplayer, te, world, x, y, z), 176, 85);
		blastFurnaceTE = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		int scale = 0;

		scale = blastFurnaceTE.getTemperatureScaled(49);
		drawTexturedModalRect(guiLeft + 8, guiTop + 65 - scale, 185, 31, 15, 6);

		scale = blastFurnaceTE.getOreCountScaled(80);
		drawTexturedModalRect(guiLeft + 40, guiTop + 25, 176, 0, scale + 1, 8);

		scale = blastFurnaceTE.getCharcoalCountScaled(80);
		drawTexturedModalRect(guiLeft + 40, guiTop + 43, 176, 0, scale + 1, 8);

		int x = guiLeft + 40;
		x += 3;
		
		for (int fireItemIndex = 0; fireItemIndex < blastFurnaceTE.fireItemStacks.length; fireItemIndex++)
		{
			ItemStack is = blastFurnaceTE.fireItemStacks[fireItemIndex];
			
			if (is != null)
			{
				int y = guiTop + 66;

				HeatItemDetails details = new HeatItemDetails(is);
				if (details.hasTemp && details.range > 0)
				{
					if (details.isLiquid)
					{
						renderQuad(x, y, 2, 12, details.color);
					}
					else
					{
						y += (12 - details.range);
						renderQuad(x, y, 2, details.range, details.color);
					}
				}
			}
			
			x += 3;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		this.fontRendererObj.drawString(TFC_Core.translate("gui.Bloomery.Ore"), 40, 17, 0x000000);
		this.fontRendererObj.drawString(TFC_Core.translate("gui.Bloomery.Charcoal"), 40, 35, 0x000000);
		if (blastFurnaceTE.fuelTimeLeft > 0)
			this.fontRendererObj.drawString(""+blastFurnaceTE.fuelTimeLeft, 138, 35, 0x000000);

		if (TFCOptions.enableDebugMode)
		{
			this.fontRendererObj.drawString("Temp : " + blastFurnaceTE.fireTemp, 40, 71, 0x000000);
		}
		
		float maxTemp = 0F;
		int fireStackLength = blastFurnaceTE.fireItemStacks.length;
		for (int fireItemIndex = 0; fireItemIndex < fireStackLength; fireItemIndex++)
		{
			ItemStack is = blastFurnaceTE.fireItemStacks[fireItemIndex];
			HeatItemDetails details = new HeatItemDetails(is);
			if (details.hasTemp && details.temp > maxTemp)
				maxTemp = details.temp;
		}
		this.fontRendererObj.drawString("Max : " + (int)maxTemp, 110, 71, 0x000000);
	}

	@Override
	public void drawScreen(int i, int j, float par3) 
	{
		super.drawScreen(i, j, par3);

		if (this.mouseInRegion(11, 17, 9, 52, i, j))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+(int)blastFurnaceTE.fireTemp);
			
			this.drawHoveringText(list, i, j+8, this.fontRendererObj);
		}

		if (this.mouseInRegion(40, 25, 80, 8, i, j))
		{
			int oreCount = blastFurnaceTE.oreCount;
			int stackSize = blastFurnaceTE.getMaxValidStackSize();

			ArrayList<String> list = new ArrayList<String>();
			list.add(""+oreCount+"/"+stackSize*4);
			
			this.drawHoveringText(list, i, j+8, this.fontRendererObj);			
		}

		if (this.mouseInRegion(40, 43, 80, 8, i, j))
		{
			int charcoalCount = blastFurnaceTE.charcoalCount;
			int stackSize = blastFurnaceTE.getMaxValidStackSize();
			
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+charcoalCount+"/"+stackSize*4);
			
			this.drawHoveringText(list, i, j+8, this.fontRendererObj);			
		}
		
		int x = guiLeft + 40;
		x += 3;
		
		for (int fireItemIndex = 0; fireItemIndex < blastFurnaceTE.fireItemStacks.length; fireItemIndex++)
		{
			ItemStack is = blastFurnaceTE.fireItemStacks[fireItemIndex];
			
			if (is != null)
			{
				int y = guiTop + 66;

				HeatItemDetails details = new HeatItemDetails(is);
				if (details.hasTemp && details.range > 0)
				{
					y += (12 - details.range);
					if (i >= x && i <= x+2 && j >= y && j <= y+details.range)
					{
						ArrayList<String> list = new ArrayList<String>();
						list.add(""+(int)details.temp);
						
						drawHoveringText(list, i, j, fontRendererObj);
					}
				}
			}
			
			x += 3;
		}		
	}
	
	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	private static void renderQuad(double x, double y, double sizeX, double sizeY, int color)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex((double)(x + 0), (double)(y + 0), 0.0D);
		tess.addVertex((double)(x + 0), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + 0), 0.0D);
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	@Override
	public void updateScreen()
	{
		TileEntity tileEntity = blastFurnaceTE;
		if (tileEntity.isInvalid())
		{
			World world = tileEntity.getWorldObj();
			tileEntity = world.getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			
			if (tileEntity instanceof TEBlastFurnace && !tileEntity.isInvalid())
				blastFurnaceTE = (TEBlastFurnace)tileEntity;
		}
	}
}

package com.bioxx.tfc.GUI;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerSkills;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.TFCOptions;

public class GuiHealth extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_health.png");
	protected EntityPlayer player;

	public GuiHealth(EntityPlayer player)
	{
		super(new ContainerSkills(player), 176, 104);
		this.setDrawInventory(false);
		this.player = player;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		DecimalFormat fmt = new DecimalFormat("#0.00;-#0.00");
		
		FoodStatsTFC food = TFC_Core.getPlayerFoodStats(player);

		fontRendererObj.drawString(TFC_Core.translate("gui.food.fruit"), 5, 13, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.vegetable"), 5, 23, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.grain"), 5, 33, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.protein"), 5, 43, 0, false);
		fontRendererObj.drawString(TFC_Core.translate("gui.food.dairy"), 5, 53, 0, false);
		if (TFCOptions.enableDebugMode)
		{
			fontRendererObj.drawString(Float.toString(food.nutrFruit), 85, 13, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrVeg), 85, 23, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrGrain), 85, 33, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrProtein), 85, 43, 0, false);
			fontRendererObj.drawString(Float.toString(food.nutrDairy), 85, 53, 0, false);
		}

		GL11.glPushMatrix();
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		
		int xOffset = 1;
		int yOffset = 20;
		
		fontRendererObj.drawString(TFC_Core.translate("gui.food.satifaction"), 5+xOffset, 63+yOffset, 0, false);
		fontRendererObj.drawString(""+fmt.format(food.getSatisfaction()), 55+xOffset, 63+yOffset, 0, false);

		fontRendererObj.drawString(TFC_Core.translate("gui.food.stomach"), 85+xOffset, 63+yOffset, 0, false);
		fontRendererObj.drawString(""+fmt.format(food.getFoodLevel()), 130+xOffset, 63+yOffset, 0, false);

		int[] tastePref = food.getPrefTaste();
		
		fontRendererObj.drawString(TFC_Core.translate("gui.taste.sweet"), 5+xOffset, 73+yOffset, 0);
		fontRendererObj.drawString(TFC_Core.translate("gui.taste.sour"), 85+xOffset, 73+yOffset, 0);
		fontRendererObj.drawString(TFC_Core.translate("gui.taste.salty"), 5+xOffset, 83+yOffset, 0);
		fontRendererObj.drawString(TFC_Core.translate("gui.taste.bitter"), 85+xOffset, 83+yOffset, 0);
		fontRendererObj.drawString(TFC_Core.translate("gui.taste.savory"), 5+xOffset, 93+yOffset, 0);

		fontRendererObj.drawString(""+tastePref[0], 55+xOffset, 73+yOffset, 0, false);
		fontRendererObj.drawString(""+tastePref[1], 130+xOffset, 73+yOffset, 0, false);
		fontRendererObj.drawString(""+tastePref[2], 55+xOffset, 83+yOffset, 0, false);
		fontRendererObj.drawString(""+tastePref[3], 130+xOffset, 83+yOffset, 0, false);
		fontRendererObj.drawString(""+tastePref[4], 55+xOffset, 93+yOffset, 0, false);
		
		GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawGui(ResourceLocation rl)
	{
		bindTexture(rl);
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2 - 34; //Shifted 34 pixels up to match other inventory tabs
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize); //No inventory drawn, so shifted ySize is not necessary
		drawForeground(guiLeft, guiTop);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		FoodStatsTFC food = TFC_Core.getPlayerFoodStats(player);

		drawTexturedModalRect(guiLeft + 55, guiTop + 14, 0, 106, (int) (food.nutrFruit * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 24, 0, 106, (int) (food.nutrVeg * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 34, 0, 106, (int) (food.nutrGrain * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 44, 0, 106, (int) (food.nutrProtein * 24), 6);
		drawTexturedModalRect(guiLeft + 55, guiTop + 54, 0, 106, (int) (food.nutrDairy * 24), 6);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft + 176, guiTop - 31, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Inventory"), TFC_Textures.guiInventory));
		buttonList.add(new GuiInventoryButton(1, guiLeft + 176, guiTop - 12, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Skills"), TFC_Textures.guiSkills));
		buttonList.add(new GuiInventoryButton(2, guiLeft + 176, guiTop + 7, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Calendar.Calendar"), TFC_Textures.guiCalendar));
		buttonList.add(new GuiInventoryButton(3, guiLeft + 176, guiTop + 26, 25, 20, 0, 86, 25, 20, TFC_Core.translate("gui.Inventory.Health"), TFC_Textures.guiHealth));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer));
		else if (guibutton.id == 1)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSkills(Minecraft.getMinecraft().thePlayer));
		else if (guibutton.id == 2)
			Minecraft.getMinecraft().displayGuiScreen(new GuiCalendar(Minecraft.getMinecraft().thePlayer));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) 
	{
		super.drawScreen(par1, par2, par3);
		
		FoodStatsTFC food = TFC_Core.getPlayerFoodStats(player);

		if (this.mouseInRegion(54, 13, 27, 8, par1, par2))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+food.nutrFruit);
			
			this.drawHoveringText(list, par1, par2+8, this.fontRendererObj);
		}
		
		if (this.mouseInRegion(54, 23, 27, 8, par1, par2))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+food.nutrVeg);
			
			this.drawHoveringText(list, par1, par2+8, this.fontRendererObj);
		}
		
		if (this.mouseInRegion(54, 33, 27, 8, par1, par2))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+food.nutrGrain);
			
			this.drawHoveringText(list, par1, par2+8, this.fontRendererObj);
		}
		
		if (this.mouseInRegion(54, 43, 27, 8, par1, par2))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+food.nutrProtein);
			
			this.drawHoveringText(list, par1, par2+8, this.fontRendererObj);
		}
		
		if (this.mouseInRegion(54, 53, 27, 8, par1, par2))
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(""+food.nutrDairy);
			
			this.drawHoveringText(list, par1, par2+8, this.fontRendererObj);
		}
	}
}

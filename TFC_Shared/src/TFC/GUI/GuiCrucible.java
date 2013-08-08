package TFC.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Containers.ContainerCrucible;
import TFC.Core.TFC_Settings;
import TFC.Core.Util.StringUtil;
import TFC.TileEntities.TECrucible;


public class GuiCrucible extends GuiContainer
{
	private TECrucible te;


	public GuiCrucible(InventoryPlayer inventoryplayer, TECrucible tileEntity, World world, int x, int y, int z)
	{
		super(new ContainerCrucible(inventoryplayer,tileEntity, world, x, y, z) );
		te = tileEntity;
		this.xSize = 175;
		this.ySize = 193;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.renderEngine.bindTexture(Reference.AssetPathGui + "gui_crucible.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        int w = (width - xSize) / 2;
        int h = (height - ySize) / 2;
        drawTexturedModalRect(w, h, 0, 0, xSize, ySize);

        int scale = 0;

        scale = te.getTemperatureScaled(49);
        drawTexturedModalRect(w + 153, h + 65 - scale, 185, 0, 15, 6);
        
        scale = te.getOutCountScaled(100);
        drawTexturedModalRect(w + 129, h + 106 - scale, 177, 6, 8, scale);
        
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
    {
		if(te.currentAlloy != null)
		{
			this.fontRenderer.drawString(StringUtil.localize("gui.metal." + te.currentAlloy.outputType.Name.replace(" ", "")),4,4,0x000000);
			
		}

		if(TFC_Settings.enableDebugMode)
			this.fontRenderer.drawString("Temp: " + te.temperature ,77, 8, 0xffffff);
    }
	
	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }


}

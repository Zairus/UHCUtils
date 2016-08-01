package zairus.uhcutils.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.uhcutils.UHCUtils;
import zairus.uhcutils.UUConstants;
import zairus.uhcutils.tileentity.TileEntityUHCController;
import zairus.uhcutils.util.GlobalData;
import zairus.uhcutils.util.network.UUControllerPacket;

@SideOnly(Side.CLIENT)
public class GuiUHCController extends GuiScreen
{
	public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(UUConstants.MODID, "textures/gui/gui_uhccontroller.png");
	
	private final TileEntityUHCController controller;
	
	protected int xSize = 176;
	protected int ySize = 174;
	
	private int selectedMode;
	
	private boolean combatOn = false;
	private boolean fireworkOn = false;
	private boolean redstoneOn = false;
	
	private GuiColorSelectorButton buttonC0;
	private GuiColorSelectorButton buttonC1;
	private GuiColorSelectorButton buttonC2;
	private GuiColorSelectorButton buttonC3;
	private GuiColorSelectorButton buttonC4;
	private GuiColorSelectorButton buttonC5;
	private GuiColorSelectorButton buttonC6;
	private GuiColorSelectorButton buttonC7;
	private GuiColorSelectorButton buttonC8;
	private GuiColorSelectorButton buttonC9;
	private GuiColorSelectorButton buttonCa;
	private GuiColorSelectorButton buttonCb;
	private GuiColorSelectorButton buttonCc;
	private GuiColorSelectorButton buttonCd;
	private GuiColorSelectorButton buttonCe;
	private GuiColorSelectorButton buttonCf;
	
	private GuiColorSelectorButton buttonMain;
	private GuiColorSelectorButton buttonRedstone;
	
	private GuiColorSelectorButton buttonCHu;
	private GuiColorSelectorButton buttonCHd;
	private GuiColorSelectorButton buttonCMu;
	private GuiColorSelectorButton buttonCMd;
	private GuiColorSelectorButton buttonCSu;
	private GuiColorSelectorButton buttonCSd;
	
	private GuiColorSelectorButton buttonFHu;
	private GuiColorSelectorButton buttonFHd;
	private GuiColorSelectorButton buttonFMu;
	private GuiColorSelectorButton buttonFMd;
	private GuiColorSelectorButton buttonFSu;
	private GuiColorSelectorButton buttonFSd;
	
	private GuiColorSelectorButton buttonRHu;
	private GuiColorSelectorButton buttonRHd;
	private GuiColorSelectorButton buttonRMu;
	private GuiColorSelectorButton buttonRMd;
	private GuiColorSelectorButton buttonRSu;
	private GuiColorSelectorButton buttonRSd;
	
	private GuiColorSelectorButton buttonCOn;
	private GuiColorSelectorButton buttonFOn;
	private GuiColorSelectorButton buttonROn;
	
	public GuiUHCController(TileEntityUHCController controllerTE)
	{
		this.controller = controllerTE;
		this.selectedMode = this.controller.getGroup();
		this.combatOn = controller.getTaskFlag(0);
		this.fireworkOn = controller.getTaskFlag(1);
		this.redstoneOn = controller.getTaskFlag(2);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	@Override
	public void initGui()
	{
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		int palletButtonLeft = 39;
		
		Keyboard.enableRepeatEvents(true);
		
		this.buttonList.add(buttonMain = new GuiColorSelectorButton(0, left + 25, top + 79).setDimensions(10, 174, 8, 7));
		
		this.buttonList.add(buttonC0 = new GuiColorSelectorButton(1, left + palletButtonLeft + (6 * 0), top + 83));
		this.buttonList.add(buttonC1 = new GuiColorSelectorButton(2, left + palletButtonLeft + (6 * 1), top + 83));
		this.buttonList.add(buttonC2 = new GuiColorSelectorButton(3, left + palletButtonLeft + (6 * 2), top + 83));
		this.buttonList.add(buttonC3 = new GuiColorSelectorButton(4, left + palletButtonLeft + (6 * 3), top + 83));
		this.buttonList.add(buttonC4 = new GuiColorSelectorButton(5, left + palletButtonLeft + (6 * 4), top + 83));
		this.buttonList.add(buttonC5 = new GuiColorSelectorButton(6, left + palletButtonLeft + (6 * 5), top + 83));
		this.buttonList.add(buttonC6 = new GuiColorSelectorButton(7, left + palletButtonLeft + (6 * 6), top + 83));
		this.buttonList.add(buttonC7 = new GuiColorSelectorButton(8, left + palletButtonLeft + (6 * 7), top + 83));
		this.buttonList.add(buttonC8 = new GuiColorSelectorButton(9, left + palletButtonLeft + (6 * 8), top + 83));
		this.buttonList.add(buttonC9 = new GuiColorSelectorButton(10, left + palletButtonLeft + (6 * 9), top + 83));
		this.buttonList.add(buttonCa = new GuiColorSelectorButton(11, left + palletButtonLeft + (6 * 10), top + 83));
		this.buttonList.add(buttonCb = new GuiColorSelectorButton(12, left + palletButtonLeft + (6 * 11), top + 83));
		this.buttonList.add(buttonCc = new GuiColorSelectorButton(13, left + palletButtonLeft + (6 * 12), top + 83));
		this.buttonList.add(buttonCd = new GuiColorSelectorButton(14, left + palletButtonLeft + (6 * 13), top + 83));
		this.buttonList.add(buttonCe = new GuiColorSelectorButton(15, left + palletButtonLeft + (6 * 14), top + 83));
		this.buttonList.add(buttonCf = new GuiColorSelectorButton(16, left + palletButtonLeft + (6 * 15), top + 83));
		
		this.buttonList.add(buttonRedstone = new GuiColorSelectorButton(17, left + 150, top + 79).setDimensions(10, 174, 8, 7));
		
		this.buttonList.add(buttonCHu = new GuiColorSelectorButton(18, left, top).setDimensions(26, 174, 7, 6).configure(0, 2, 1));
		this.buttonList.add(buttonCHd = new GuiColorSelectorButton(19, left, top).setDimensions(33, 174, 7, 6).configure(0, 2, -1));
		this.buttonList.add(buttonCMu = new GuiColorSelectorButton(20, left, top).setDimensions(26, 174, 7, 6).configure(0, 1, 1));
		this.buttonList.add(buttonCMd = new GuiColorSelectorButton(21, left, top).setDimensions(33, 174, 7, 6).configure(0, 1, -1));
		this.buttonList.add(buttonCSu = new GuiColorSelectorButton(22, left, top).setDimensions(26, 174, 7, 6).configure(0, 0, 1));
		this.buttonList.add(buttonCSd = new GuiColorSelectorButton(23, left, top).setDimensions(33, 174, 7, 6).configure(0, 0, -1));
		
		this.buttonList.add(buttonFHu = new GuiColorSelectorButton(24, left, top).setDimensions(26, 174, 7, 6).configure(1, 2, 1));
		this.buttonList.add(buttonFHd = new GuiColorSelectorButton(25, left, top).setDimensions(33, 174, 7, 6).configure(1, 2, -1));
		this.buttonList.add(buttonFMu = new GuiColorSelectorButton(26, left, top).setDimensions(26, 174, 7, 6).configure(1, 1, 1));
		this.buttonList.add(buttonFMd = new GuiColorSelectorButton(27, left, top).setDimensions(33, 174, 7, 6).configure(1, 1, -1));
		this.buttonList.add(buttonFSu = new GuiColorSelectorButton(28, left, top).setDimensions(26, 174, 7, 6).configure(1, 0, 1));
		this.buttonList.add(buttonFSd = new GuiColorSelectorButton(29, left, top).setDimensions(33, 174, 7, 6).configure(1, 0, -1));
		
		this.buttonList.add(buttonRHu = new GuiColorSelectorButton(30, left, top).setDimensions(26, 174, 7, 6).configure(2, 2, 1));
		this.buttonList.add(buttonRHd = new GuiColorSelectorButton(31, left, top).setDimensions(33, 174, 7, 6).configure(2, 2, -1));
		this.buttonList.add(buttonRMu = new GuiColorSelectorButton(32, left, top).setDimensions(26, 174, 7, 6).configure(2, 1, 1));
		this.buttonList.add(buttonRMd = new GuiColorSelectorButton(33, left, top).setDimensions(33, 174, 7, 6).configure(2, 1, -1));
		this.buttonList.add(buttonRSu = new GuiColorSelectorButton(34, left, top).setDimensions(26, 174, 7, 6).configure(2, 0, 1));
		this.buttonList.add(buttonRSd = new GuiColorSelectorButton(35, left, top).setDimensions(33, 174, 7, 6).configure(2, 0, -1));
		
		this.buttonList.add(buttonCOn = new GuiColorSelectorButton(36, left, top).setDimensions(40, 174, 5, 5));
		this.buttonList.add(buttonFOn = new GuiColorSelectorButton(37, left, top).setDimensions(40, 174, 5, 5));
		this.buttonList.add(buttonROn = new GuiColorSelectorButton(38, left, top).setDimensions(40, 174, 5, 5));
		
		this.updateButtons();
	}
	
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			switch (button.id)
			{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
				this.selectedMode = button.id;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
				GuiColorSelectorButton b = (GuiColorSelectorButton)button;
				
				int timeAdd = (b.level == 0)? 1 : (b.level == 1)? 60 : 3600;
				
				timeAdd *= b.factor;
				controller.setTime(b.type, controller.getTime(b.type) + timeAdd);
				break;
			case 36:
				this.combatOn = !this.combatOn;
				controller.setTaskFlags(combatOn, fireworkOn, redstoneOn);
				break;
			case 37:
				this.fireworkOn = !this.fireworkOn;
				controller.setTaskFlags(combatOn, fireworkOn, redstoneOn);
				break;
			case 38:
				this.redstoneOn = !this.redstoneOn;
				controller.setTaskFlags(combatOn, fireworkOn, redstoneOn);
				break;
			default:
				break;
			}
		}
		
		this.updateButtons();
		
		syncTE();
	}
	
	private void updateButtons()
	{
		this.buttonMain.offsetValue = 8;
		this.buttonMain.offset = (this.selectedMode == 0)? 1 : 0;
		
		this.buttonRedstone.offsetValue = 8;
		this.buttonRedstone.offset = (this.selectedMode == 17)? 1 : 0;
		
		this.buttonC0.offset = (this.selectedMode == 1)? 1 : 0;
		this.buttonC1.offset = (this.selectedMode == 2)? 1 : 0;
		this.buttonC2.offset = (this.selectedMode == 3)? 1 : 0;
		this.buttonC3.offset = (this.selectedMode == 4)? 1 : 0;
		this.buttonC4.offset = (this.selectedMode == 5)? 1 : 0;
		this.buttonC5.offset = (this.selectedMode == 6)? 1 : 0;
		this.buttonC6.offset = (this.selectedMode == 7)? 1 : 0;
		this.buttonC7.offset = (this.selectedMode == 8)? 1 : 0;
		this.buttonC8.offset = (this.selectedMode == 9)? 1 : 0;
		this.buttonC9.offset = (this.selectedMode == 10)? 1 : 0;
		this.buttonCa.offset = (this.selectedMode == 11)? 1 : 0;
		this.buttonCb.offset = (this.selectedMode == 12)? 1 : 0;
		this.buttonCc.offset = (this.selectedMode == 13)? 1 : 0;
		this.buttonCd.offset = (this.selectedMode == 14)? 1 : 0;
		this.buttonCe.offset = (this.selectedMode == 15)? 1 : 0;
		this.buttonCf.offset = (this.selectedMode == 16)? 1 : 0;
		
		this.buttonCOn.offset = (this.combatOn)? 1 : 0;
		this.buttonFOn.offset = (this.fireworkOn)? 1 : 0;
		this.buttonROn.offset = (this.redstoneOn)? 1 : 0;
	}
	
	private void syncTE()
	{
		this.controller.setGroup(this.selectedMode);
		UHCUtils.packetPipeline.sendToServer(new UUControllerPacket(
				controller.getPos().getX(), 
				controller.getPos().getY(), 
				controller.getPos().getZ(), 
				selectedMode,
				controller.getTime(0),
				controller.getTime(1),
				controller.getTime(2),
				this.combatOn,
				this.fireworkOn,
				this.redstoneOn));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		setButtonPositions(left, top);
		drawTime(left, top);
		//renderInfoText(left, top);
		
		writeText("Teams", left + 35, top + 65);
		writeText("Main", left + 5, top + 79);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void drawTime(int left, int top)
	{
		writeText(getHours(controller.getTime(0)) + "", left + 55, top + 13);
		writeText(":", left + 70, top + 13);
		writeText(getMinutes(controller.getTime(0)) + "", left + 85, top + 13);
		writeText(":", left + 100, top + 13);
		writeText(getSeconds(controller.getTime(0)) + "", left + 115, top + 13);
		
		writeText(getHours(controller.getTime(1)) + "", left + 55, top + 28);
		writeText(":", left + 70, top + 28);
		writeText(getMinutes(controller.getTime(1)) + "", left + 85, top + 28);
		writeText(":", left + 100, top + 28);
		writeText(getSeconds(controller.getTime(1)) + "", left + 115, top + 28);
		
		writeText(getHours(controller.getTime(2)) + "", left + 55, top + 45);
		writeText(":", left + 70, top + 45);
		writeText(getMinutes(controller.getTime(2)) + "", left + 85, top + 45);
		writeText(":", left + 100, top + 45);
		writeText(getSeconds(controller.getTime(2)) + "", left + 115, top + 45);
	}
	
	private void renderInfoText(int left, int top)
	{
		List<String> lines = new ArrayList<String>();
		Set<String> keys = GlobalData.instance.controllerList.getKeySet();
		
		lines.add("Seconds: " + controller.getEllapsedSeconds());
		lines.add("Controllers: " + keys.size());
		
		for(String uuid : keys)
			lines.add(uuid);
		
		int i = 0;
		for (String line : lines)
		{
			int maxLen = 26;
			
			writeText(line.substring(0, (line.length() > maxLen)? maxLen : line.length()), left + 10, top + 95 + (10 * i));
			++i;
			if (i > 6)
				break;
		}
	}
	
	private void writeText(String text, int left, int top)
	{
		this.fontRendererObj.drawString(text, left, top, 4210752);
	}
	
	private void setButtonPositions(int left, int top)
	{
		this.buttonRedstone.setScreenPos(left + 157, top + 79);
		
		this.buttonCHu.setScreenPos(left + 46, top + 10);
		this.buttonCHd.setScreenPos(left + 46, top + 16);
		this.buttonCMu.setScreenPos(left + 74, top + 10);
		this.buttonCMd.setScreenPos(left + 74, top + 16);
		this.buttonCSu.setScreenPos(left + 104, top + 10);
		this.buttonCSd.setScreenPos(left + 104, top + 16);
		
		this.buttonFHu.setScreenPos(left + 46, top + 26);
		this.buttonFHd.setScreenPos(left + 46, top + 32);
		this.buttonFMu.setScreenPos(left + 74, top + 26);
		this.buttonFMd.setScreenPos(left + 74, top + 32);
		this.buttonFSu.setScreenPos(left + 104, top + 26);
		this.buttonFSd.setScreenPos(left + 104, top + 32);
		
		this.buttonRHu.setScreenPos(left + 46, top + 42);
		this.buttonRHd.setScreenPos(left + 46, top + 48);
		this.buttonRMu.setScreenPos(left + 74, top + 42);
		this.buttonRMd.setScreenPos(left + 74, top + 48);
		this.buttonRSu.setScreenPos(left + 104, top + 42);
		this.buttonRSd.setScreenPos(left + 104, top + 48);
		
		this.buttonCOn.setScreenPos(left + 128, top + 14);
		this.buttonFOn.setScreenPos(left + 128, top + 28);
		this.buttonROn.setScreenPos(left + 128, top + 45);
	}
	
	private int getHours(int time)
	{
		int h;
		
		h = MathHelper.floor_float(time / 3600);
		
		return h;
	}
	
	private int getMinutes(int time)
	{
		int m;
		int h = getHours(time);
		
		m = MathHelper.floor_float(time / 60) - (h * 60);
		
		return m;
	}
	
	private int getSeconds(int time)
	{
		int s;
		int h = getHours(time);
		int m = getMinutes(time);
		
		s = time - (m * 60) - (h * 3600);
		
		return s;
	}
	
	static class GuiColorSelectorButton extends GuiButton
	{
		public int offset = 0;
		public int offsetValue = 5;
		
		public int textureX = 0;
		public int textureY = 174;
		
		public int type = 0;
		public int level = 0;
		public int factor = 1;
		
		public GuiColorSelectorButton(int buttonId, int x, int y)
		{
			super(buttonId, x, y, 16, 13, "");
			this.width = 5;
			this.height = 3;
		}
		
		public GuiColorSelectorButton configure(int type, int level, int factor)
		{
			this.type = type;
			this.level = level;
			this.factor = factor;
			
			return this;
		}
		
		public GuiColorSelectorButton setDimensions(int tX, int tY, int w, int h)
		{
			this.textureX = tX;
			this.textureY = tY;
			this.width = w;
			this.height = h;
			return this;
		}
		
		@Override
		public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				minecraft.getTextureManager().bindTexture(GuiUHCController.GUI_BACKGROUND);
				
				int k = textureX + (offset * offsetValue);
				int l = textureY;
				
				this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, this.width, this.height);
			}
		}
		
		public void setScreenPos(int x, int y)
		{
			this.xPosition = x;
			this.yPosition = y;
		}
	}
}

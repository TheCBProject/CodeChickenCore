package codechicken.core;

import codechicken.core.internal.CCCEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.NetworkManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientUtils extends CommonUtils {
    private static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static World getWorld() {
        return mc().theWorld;
    }

    public static boolean inWorld()
    {
        return mc().getNetHandler() != null;
    }

    public static void openSMPGui(int windowId, GuiScreen gui) {
        mc().displayGuiScreen(gui);
        if (windowId != 0) {
            mc().thePlayer.openContainer.windowId = windowId;
        }
    }

    public static float getRenderFrame() {
        return CCCEventHandler.renderFrame;
    }

    public static double getRenderTime() {
        return CCCEventHandler.renderTime + getRenderFrame();
    }

    public static String getServerIP() {
        try {
            NetworkManager networkManager = mc().getNetHandler().getNetworkManager();
            String s = networkManager.getRemoteAddress().toString();
            s = s.substring(s.indexOf("/") + 1);
            return s;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SideOnly(Side.CLIENT)
    public static String getWorldSaveName() {
        return mc().isSingleplayer() ? FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName() : null;
    }

    @Deprecated
    public static void enhanceSupportersList(Object mod) {
        ModDescriptionEnhancer.enhanceMod(mod);
    }
}

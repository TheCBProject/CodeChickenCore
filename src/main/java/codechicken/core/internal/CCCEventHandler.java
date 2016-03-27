package codechicken.core.internal;

import codechicken.core.CCUpdateChecker;
import codechicken.core.GuiModListScroll;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CCCEventHandler {
    public static int renderTime;
    public static float renderFrame;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == Phase.END) {
            CCUpdateChecker.tick();
            renderTime++;
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == Phase.START) {
            renderFrame = event.renderTickTime;
        }
    }

    //@SubscribeEvent //TODO Rewrite or get rid of.
    @SideOnly(Side.CLIENT)
    public void posGuiRender(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiModList) {
            GuiModListScroll.draw((GuiModList) event.getGui(), event.getMouseX(), event.getMouseY());
        }
    }
}

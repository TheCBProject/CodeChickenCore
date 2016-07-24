package codechicken.core.featurehack.config;

import codechicken.core.ReflectionManager;
import codechicken.lib.config.ConfigFile;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Created by covers1624 on 6/9/2016.
 */
@SideOnly(Side.CLIENT)
public class ConfigGuiInjector {

    private static boolean hasInit = false;

    public static void init() {
        if (hasInit || !FMLLaunchHandler.side().isClient()) {
            return;
        } else {
            hasInit = true;
        }
        HashMap<ModContainer, CCConfigGuiFactory> containersToInject = new HashMap<ModContainer, CCConfigGuiFactory>();
        for (ModContainer modContainer : Loader.instance().getModList()) {
            if (modContainer.getMod() != null) {
                CCConfigGuiFactory guiFactory = modContainer.getMod().getClass().getAnnotation(CCConfigGuiFactory.class);
                if (guiFactory != null && guiFactory.configObject() != null) {
                    containersToInject.put(modContainer, guiFactory);
                }
            }
        }
        for (Entry<ModContainer, CCConfigGuiFactory> entry : containersToInject.entrySet()) {
            ConfigFile file = processAnnotationData(entry.getValue().configObject());

        }
    }

    private static ConfigFile processAnnotationData(String data) {
        ConfigFile file;
        try {
            int lastDot = data.lastIndexOf(".");
            String className = data.substring(0, lastDot);
            String fieldOrMethod = data.substring(lastDot + 1, data.length());
            Class<?> clazz = Class.forName(className);
            if (ReflectionManager.hasField(clazz, fieldOrMethod)) {
                file = ReflectionManager.getField(clazz, ConfigFile.class, null, fieldOrMethod);
            } else {
                Method method = clazz.getDeclaredMethod(fieldOrMethod);
                Object methodReturn = method.invoke(null);
                if (methodReturn instanceof ConfigFile) {
                    file = (ConfigFile) methodReturn;
                } else {
                    throw new Exception(String.format("Method %s does not return an instance of ConfigFile!", fieldOrMethod));
                }
            }
        } catch (Exception e) {
            FMLLog.log(Level.ERROR, "CodeChickenCore", "Unable to parse data from CCConfigGuiFactory! There is no method or field! Data: [%s]", data);
            e.printStackTrace();
            file = null;
        }
        return file;
    }

}

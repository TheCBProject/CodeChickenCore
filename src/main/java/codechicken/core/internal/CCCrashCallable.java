package codechicken.core.internal;

import codechicken.core.asm.CodeChickenCoreModContainer;
import codechicken.lib.config.ConfigTag;
import net.minecraftforge.fml.common.ICrashCallable;

/**
 * Created by covers1624 on 7/13/2016.
 */
public class CCCrashCallable implements ICrashCallable {
    @Override
    public String getLabel() {
        return "CodeChickenCore";
    }

    @Override
    public String call() throws Exception {

        ConfigTag tag = CodeChickenCoreModContainer.config.getTag("ignoreInvalidMCVersion", false);
        boolean state = tag != null && tag.getBooleanValue();
        return "ignoreInvalidMCVersion state: " + state;
    }
}

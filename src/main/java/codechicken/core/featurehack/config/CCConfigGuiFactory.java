package codechicken.core.featurehack.config;

import codechicken.lib.config.ConfigFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This defines a ConfigGui to be injected for a given mod container.
 * Any mod container found with this annotation will have a config gui injected client side.
 *
 * @author covers1624
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CCConfigGuiFactory {

    /**
     * Used to mark the class to load the {@link ConfigFile} field from.
     * Can be a method to invoke only if isMethod is set to true;
     *
     * @return Field or method to access. Format: "some.package.SomeClass.someFieldOrMethod"
     */
    String configObject();

    /**
     * Use this to override the mod the config is for.
     *
     * @return ModID to be used for.
     */
    String modidOverride() default "";

}

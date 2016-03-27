package codechicken.core.featurehack;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;

import java.util.Map;

public class GameDataManipulator {

    /**
     * Replaces an item with another item.
     *
     * @param needle      Item to replace.
     * @param replacement Replacement.
     */
    public static void replaceItem(Item needle, Item replacement) {
        try {
            ResourceLocation name = Item.itemRegistry.getNameForObject(needle);
            int needleId = Item.getIdFromItem(needle);
            Item.itemRegistry.registryObjects.put(name, replacement);
            Item.itemRegistry.underlyingIntegerMap.put(replacement, needleId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Replaces the ItemBlock for the given block.
     *
     * @param needle
     * @param replacement
     */
    public static void replaceItemBlock(Block needle, Item replacement) {
        try {
            int needleId = Block.getIdFromBlock(needle);
            ResourceLocation name = Item.itemRegistry.getNameForObject(Item.getItemById(needleId));
            Item.itemRegistry.registryObjects.put(name, replacement);
            Item.itemRegistry.underlyingIntegerMap.put(replacement, needleId);
            //GameData
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void replaceItem(int id, Item item) {
        try {
            ResourceLocation name = Item.itemRegistry.getNameForObject(Item.getItemById(id));
            Item.itemRegistry.registryObjects.put(name, item);
            Item.itemRegistry.underlyingIntegerMap.put(item, id);
            Block block = Block.getBlockById(id);
            if (block != Blocks.air) {
                GameData.getBlockItemMap().put(block, item);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

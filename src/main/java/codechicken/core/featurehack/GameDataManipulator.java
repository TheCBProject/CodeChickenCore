package codechicken.core.featurehack;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;

public class GameDataManipulator {

    /**
     * Replaces an item with another item.
     *
     * @param needle      Item to replace.
     * @param replacement Replacement.
     */
    public static void replaceItem(Item needle, Item replacement) {
        try {
            ResourceLocation name = Item.REGISTRY.getNameForObject(needle);
            int needleId = Item.getIdFromItem(needle);
            Item.REGISTRY.registryObjects.put(name, replacement);
            Item.REGISTRY.underlyingIntegerMap.put(replacement, needleId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Replaces the ItemBlock for the given block.
     *
     * @param needle      Block to replace.
     * @param replacement Replacement.
     */
    public static void replaceItemBlock(Block needle, Item replacement) {
        try {
            int needleId = Block.getIdFromBlock(needle);
            ResourceLocation name = Item.REGISTRY.getNameForObject(Item.getItemById(needleId));
            Item.REGISTRY.registryObjects.put(name, replacement);
            Item.REGISTRY.underlyingIntegerMap.put(replacement, needleId);
            if (needle != Blocks.AIR) {
                GameData.getBlockItemMap().put(needle, replacement);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static void replaceItem(int id, Item item) {
        try {
            ResourceLocation name = Item.REGISTRY.getNameForObject(Item.getItemById(id));
            Item.REGISTRY.registryObjects.put(name, item);
            Item.REGISTRY.underlyingIntegerMap.put(item, id);
            Block block = Block.getBlockById(id);
            if (block != Blocks.AIR) {
                GameData.getBlockItemMap().put(block, item);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.bawnorton.allthetrims;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AllTheTrims implements ModInitializer {
    public static final String MOD_ID = "allthetrims";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static List<Item> USED_MATERIALS = new ArrayList<>(List.of(
            Items.DIAMOND,
            Items.IRON_INGOT,
            Items.COPPER_INGOT,
            Items.GOLD_INGOT,
            Items.NETHERITE_INGOT,
            Items.QUARTZ,
            Items.EMERALD,
            Items.LAPIS_LAZULI,
            Items.REDSTONE,
            Items.LAPIS_LAZULI
    ));

    public static List<Item> USED_ARMOUR = new ArrayList<>();

    public static void addUsedAsMaterial(Item item) {
        USED_MATERIALS.add(item);
    }

    public static void addUsedAsEquipment(Item item) {
        USED_ARMOUR.add(item);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("AllTheTrims Initialized!");
    }

	public static boolean isUsedAsMaterial(Item item) {
		return USED_MATERIALS.contains(item);
	}

    public static boolean isUsedAsEquipment(Item item) {
        return USED_ARMOUR.contains(item);
    }
}
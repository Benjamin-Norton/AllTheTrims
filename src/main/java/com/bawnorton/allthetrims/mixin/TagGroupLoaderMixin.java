package com.bawnorton.allthetrims.mixin;

import com.bawnorton.allthetrims.AllTheTrims;
import com.bawnorton.allthetrims.client.compat.Compat;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin {
    @Unique
    private static final ThreadLocal<Identifier> allthetrims$TAG_ID = ThreadLocal.withInitial(() -> null);

    @ModifyVariable(
            //? if fabric {
            method = "method_51476",
            //?} elif neoforge {
            /*method = "lambda$build$6",
            *///?}
            at = @At("HEAD"),
            argsOnly = true
    )
    private Identifier captureTagId(Identifier id) {
        allthetrims$TAG_ID.set(id);
        return id;
    }

    @ModifyVariable(method = "resolveAll", at = @At("HEAD"), argsOnly = true)
    private <T> List<TagGroupLoader.TrackedEntry> addToTagEntries(List<TagGroupLoader.TrackedEntry> entries, TagEntry.ValueGetter<T> valueGetter) {
        Identifier id = allthetrims$TAG_ID.get();
        if (id.equals(ItemTags.TRIM_MATERIALS.id())) {
            entries.addAll(Registries.ITEM.stream()
                    .filter(item -> item != Items.AIR)
                    .map(Registries.ITEM::getId)
                    .map(itemId -> new TagGroupLoader.TrackedEntry(TagEntry.create(itemId), AllTheTrims.MOD_ID))
                    .collect(Collectors.toSet()));
        } else if (id.equals(ItemTags.TRIMMABLE_ARMOR.id())) {
            entries.addAll(Registries.ITEM.stream()
                    //? if >1.20.6
                    .filter(item -> !(item instanceof net.minecraft.item.AnimalArmorItem))
                    .filter(item -> item instanceof Equipment equipment && equipment.getSlotType().isArmorSlot())
                    .filter(item -> !(item instanceof ElytraItem) || Compat.getElytraTrimsCompat().isPresent())
                    .map(Registries.ITEM::getId)
                    .map(itemId -> new TagGroupLoader.TrackedEntry(TagEntry.create(itemId), AllTheTrims.MOD_ID))
                    .collect(Collectors.toSet()));
        }

        return entries;
    }
}

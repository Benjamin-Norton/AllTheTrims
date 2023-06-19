package com.bawnorton.allthetrims.mixin.client;

import com.bawnorton.allthetrims.AllTheTrims;
import com.bawnorton.allthetrims.json.JsonRepresentable;
import com.bawnorton.allthetrims.util.DebugHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.texture.atlas.AtlasLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mixin(AtlasLoader.class)
public abstract class AtlasLoaderMixin {
    @ModifyExpressionValue(method = "of", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getAllResources(Lnet/minecraft/util/Identifier;)Ljava/util/List;"))
    private static List<Resource> addAllTrimMaterials(List<Resource> resourceList, ResourceManager resourceManager, Identifier id) {
        if (id.getPath().contains("armor_trims")) {
            List<Resource> newResources = new ArrayList<>();
            for (Resource resource : resourceList) {
                try (BufferedReader reader = resource.getReader()) {
                    JsonObject atlas = JsonRepresentable.fromJson(reader, JsonObject.class);
                    if(!atlas.has("sources")) return resourceList;

                    JsonArray sources = atlas.getAsJsonArray("sources");
                    if(sources.size() == 0) return resourceList;

                    for(JsonElement source: sources) {
                        if(!(source instanceof JsonObject sourceJson)) continue;
                        if(!sourceJson.has("permutations")) continue;
                        if(!sourceJson.has("type")) continue;

                        String type = sourceJson.get("type").getAsString();
                        if(!type.equals("paletted_permutations")) continue;

                        JsonObject permutations = sourceJson.getAsJsonObject("permutations");
                        permutations.addProperty("att_blank", "trims/color_palettes/blank");
                    }
                    newResources.add(new Resource(resource.getPack(), () -> IOUtils.toInputStream(JsonRepresentable.toJson(atlas), "UTF-8")));

                    DebugHelper.createDebugFile("atlases", resource.getResourcePackName() + "_armour_trims.json", JsonRepresentable.toJson(atlas));
                } catch (RuntimeException | IOException e) {
                    AllTheTrims.LOGGER.error("Failed to modify trim atlas: " + id);
                    return resourceList;
                }
            }
            resourceList = newResources;
        }
        return resourceList;
    }
}

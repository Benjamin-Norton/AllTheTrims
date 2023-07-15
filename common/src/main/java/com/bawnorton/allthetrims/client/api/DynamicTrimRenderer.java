package com.bawnorton.allthetrims.client.api;

import com.bawnorton.allthetrims.Compat;
import com.bawnorton.allthetrims.client.util.PaletteHelper;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.List;

public abstract class DynamicTrimRenderer {
    private static SpriteAtlasTexture armorTrimsAtlas;

    public static void setAtlas(SpriteAtlasTexture atlas) {
        armorTrimsAtlas = atlas;
    }

    public static SpriteAtlasTexture getAtlas() {
        return armorTrimsAtlas;
    }

    public static void renderTrim(ArmorMaterial material, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, BipedEntityModel<?> model, boolean leggings) {
        ArmorTrimMaterial trimMaterial = trim.getMaterial().value();
        String assetName =  trimMaterial.assetName();
        List<Color> palette = PaletteHelper.getPalette(trimMaterial.ingredient().value());
        Identifier modelId = leggings ? trim.getLeggingsModelId(material) : trim.getGenericModelId(material);
        for(int i = 0; i < 8; i++) {
            String layerPath = modelId.getPath().replace(assetName, i + "_" + assetName);
            Sprite sprite = armorTrimsAtlas.getSprite(modelId.withPath(layerPath));
            VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(Compat.getTrimRenderLayer()));
            Color colour = palette.get(i);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, Compat.getTrimTransparency());
        }
    }
}

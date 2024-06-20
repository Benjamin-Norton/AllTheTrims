package com.bawnorton.allthetrims.client.mixin.shader;

import com.bawnorton.allthetrims.AllTheTrims;
import com.bawnorton.allthetrims.client.extend.RenderLayer$MultiPhaseParameters$BuilderExtender;
import com.bawnorton.allthetrims.client.render.TrimPalettePhase;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayer.MultiPhaseParameters.Builder.class)
public abstract class RenderLayer$MultiPhaseParameters$BuilderMixin implements RenderLayer$MultiPhaseParameters$BuilderExtender {
    @Unique
    private TrimPalettePhase allthetrims$trimPalette = TrimPalettePhase.NO_PALETTE;

    @Override
    public void allthetrims$trimPalette(TrimPalettePhase trimPalettePhase) {
        this.allthetrims$trimPalette = trimPalettePhase;
    }

    @Inject(
            method = "build(Lnet/minecraft/client/render/RenderLayer$OutlineMode;)Lnet/minecraft/client/render/RenderLayer$MultiPhaseParameters;",
            at = @At("HEAD")
    )
    private void appendTrimPalettePhase(CallbackInfoReturnable<RenderLayer.MultiPhaseParameters> cir) {
        AllTheTrims.getShaderManager().PHASE_ARG_LOCAL.set(allthetrims$trimPalette);
    }
}

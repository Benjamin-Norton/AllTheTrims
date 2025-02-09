package com.bawnorton.allthetrims.mixin.compat.rei;

import com.bawnorton.allthetrims.util.mixin.ConditionalMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.plugin.client.DefaultClientPlugin;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.Collection;
import java.util.function.Function;

@ConditionalMixin("roughlyenoughitems")
@Mixin(DefaultClientPlugin.class)
public abstract class DefaultClientPluginMixin {
    @WrapOperation(
            method = "registerDisplays",
            at = @At(
                    value = "INVOKE",
                    target = "Lme/shedaniel/rei/api/client/registry/display/DisplayRegistry;registerRecipesFiller(Ljava/lang/Class;Lnet/minecraft/recipe/RecipeType;Ljava/util/function/Function;)V",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "classValue=net.minecraft.recipe.SmithingTrimRecipe"
                    )
            )
    )
    private <T extends Recipe<?>, D extends Display> void dontRegisterDefaultSmithingDisplay(DisplayRegistry instance, Class<T> typeClass, RecipeType<? super T> recipeType, Function<?, @Nullable Collection<? extends D>> filler, Operation<Void> original) {
    }
}

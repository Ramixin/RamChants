package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(
            method = "travelInFluid",
            at = @At(value = "CONSTANT", args = "floatValue=0.0F", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D")
            )
    )
    private float allowNegativeSwimEfficiency(float original) {
        return -1.00001F;
    }

}

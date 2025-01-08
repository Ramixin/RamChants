package net.ramixin.ramchants.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @ModifyArg(method = "getFishingTimeReduction", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 0)
    private static float allowNegativeFishingTimeReduction(float a) {
        return Float.MIN_VALUE;
    }

    @ModifyArg(method = "getFishingLuckBonus", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"), index = 0)
    private static int allowNegativeFishingLuckBonus(int a) {
        return Integer.MIN_VALUE;
    }

}

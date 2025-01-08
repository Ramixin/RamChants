package net.ramixin.ramchants.mixins;

import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {

    @ModifyArg(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;II)V", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"), index = 0)
    private int allowNegativeFishingTimeReductionAndFishingLuckBonus(int a) {
        return Integer.MIN_VALUE;
    }

}

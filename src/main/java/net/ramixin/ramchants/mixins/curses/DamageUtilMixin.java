package net.ramixin.ramchants.mixins.curses;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DamageUtil.class)
public class DamageUtilMixin {

    @WrapOperation(method = "getDamageLeft", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getArmorEffectiveness(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;F)F"))
    private static float applyArmorEffectivenessMultiplierEffect(ServerWorld world, ItemStack stack, Entity user, DamageSource damageSource, float baseArmorEffectiveness, Operation<Float> original) {
        return original.call(world, stack, user, damageSource, baseArmorEffectiveness) * ModHelper.getArmorEffectivenessMultiplier(world, stack, user, damageSource, 1F);
    }

    @ModifyArg(method = "getInflictedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), index = 1)
    private static float allowNegativeProtectionToBeApplied(float value) {
        return -20F;
    }
}

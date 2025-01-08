package net.ramixin.ramchants.enchantments;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.ramixin.ramchants.EnchantmentDuck;
import net.ramixin.ramchants.mixins.EnchantmentHelperAccessor;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface ModHelper {

    static float getArmorEffectivenessMultiplier(ServerWorld world, ItemStack stack, Entity user, DamageSource damageSource, float baseArmorEffectiveness) {
        MutableFloat mutableFloat = new MutableFloat(baseArmorEffectiveness);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> ((EnchantmentDuck)(Object)enchantment.value()).ramChants$modifyArmorEffectivenessMultiplier(world, level, stack, user, damageSource, mutableFloat));
        return mutableFloat.floatValue();
    }

    static float getConsumeArrowChance(ServerWorld world, ItemStack stack, float chance) {
        MutableFloat mutableFloat = new MutableFloat(chance);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> ((EnchantmentDuck)(Object)enchantment.value()).ramChants$modifyConsumeArrow(world, level, stack, mutableFloat));
        return mutableFloat.floatValue();
    }

    static float getIgniteUserTime(ServerWorld world, ItemStack stack, float time) {
        MutableFloat mutableFloat = new MutableFloat(time);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> ((EnchantmentDuck)(Object)enchantment.value()).ramChants$modifyIgniteUser(world, level, stack, mutableFloat));
        return mutableFloat.floatValue();
    }

}

package net.ramixin.ramchants.enchantments;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.ramixin.ramchants.mixins.EnchantmentHelperAccessor;
import net.ramixin.ramchants.util.EnchantmentDuck;
import net.ramixin.ramchants.util.ModEffectValueConsumer;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface ModHelper {

    static float getArmorEffectivenessMultiplier(ServerWorld world, ItemStack stack, Entity user, DamageSource damageSource, float defaultValue) {
        MutableFloat mutableFloat = new MutableFloat(defaultValue);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> ((EnchantmentDuck)(Object)enchantment.value()).ramChants$modifyArmorEffectivenessMultiplier(world, level, stack, user, damageSource, mutableFloat));
        return mutableFloat.floatValue();
    }

    static float getGhostQuiverChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyGhostQuiverChance);
    }

    static float getKnockbackUserStrength(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyKnockbackUser);
    }

    static float getHitMissChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyMissChance);
    }

    static float getConsumeDropChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyConsumeDropChance);
    }

    static float getStagnationAmount(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyStagnationAmount);
    }

    static float getBetrayalChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyBetrayalChance);
    }

    static float getDeflectionChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyDeflectionChance);
    }

    private static float getValue(ServerWorld world, ItemStack stack, ModEffectValueConsumer<ServerWorld, Integer, ItemStack, MutableFloat> function) {
        MutableFloat mutableFloat = new MutableFloat((float) 0);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> function.accept((EnchantmentDuck) (Object) enchantment.value(), world, level, stack, mutableFloat));
        return mutableFloat.floatValue();
    }

}

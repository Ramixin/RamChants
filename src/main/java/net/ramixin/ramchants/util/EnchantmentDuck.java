package net.ramixin.ramchants.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface EnchantmentDuck {

    void ramChants$modifyArmorEffectivenessMultiplier(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat armorEffectiveness);

    void ramChants$modifyGhostQuiverChance(ServerWorld world, int level, ItemStack projectileStack, MutableFloat ammoUse);

    void ramChants$modifyKnockbackUser(ServerWorld world, int level, ItemStack stack, MutableFloat damage);

    void ramChants$modifyMissChance(ServerWorld world, int level, ItemStack stack, MutableFloat chance);

    void ramChants$modifyConsumeDropChance(ServerWorld world, int level, ItemStack stack, MutableFloat chance);

    void ramChants$modifyStagnationAmount(ServerWorld world, int level, ItemStack stack, MutableFloat amount);

    void ramChants$modifyBetrayalChance(ServerWorld world, int level, ItemStack stack, MutableFloat amount);

    void ramChants$modifyDeflectionChance(ServerWorld world, int level, ItemStack stack, MutableFloat amount);
}

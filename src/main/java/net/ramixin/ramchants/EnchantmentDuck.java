package net.ramixin.ramchants;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface EnchantmentDuck {

    void ramChants$modifyArmorEffectivenessMultiplier(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat armorEffectiveness);

    void ramChants$modifyConsumeArrow(ServerWorld world, int level, ItemStack projectileStack, MutableFloat ammoUse);

    void ramChants$modifyIgniteUser(ServerWorld world, int level, ItemStack stack, MutableFloat damage);
}

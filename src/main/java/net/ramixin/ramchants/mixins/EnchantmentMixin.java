package net.ramixin.ramchants.mixins;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.ramixin.ramchants.EnchantmentDuck;
import net.ramixin.ramchants.enchantments.effects.ModEnchantmentEffects;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements EnchantmentDuck {

    @Shadow protected abstract void modifyValue(ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> type, ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat value);

    @Shadow protected abstract void modifyValue(ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> type, ServerWorld world, int level, ItemStack stack, MutableFloat value);

    @Unique
    @Override
    public void ramChants$modifyArmorEffectivenessMultiplier(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat armorEffectiveness) {
        this.modifyValue(ModEnchantmentEffects.ARMOR_EFFECTIVENESS_MULTIPLIER, world, level, stack, user, damageSource, armorEffectiveness);
    }

    @Override
    public void ramChants$modifyConsumeArrow(ServerWorld world, int level, ItemStack projectileStack, MutableFloat chance) {
        this.modifyValue(ModEnchantmentEffects.CONSUME_ARROW, world, level, projectileStack, chance);
    }

    @Override
    public void ramChants$modifyIgniteUser(ServerWorld world, int level, ItemStack stack, MutableFloat damage) {
        this.modifyValue(ModEnchantmentEffects.IGNITE_USER, world, level, stack, damage);
    }
}

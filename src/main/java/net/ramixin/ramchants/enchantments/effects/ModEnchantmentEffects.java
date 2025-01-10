package net.ramixin.ramchants.enchantments.effects;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;
import net.ramixin.ramchants.Ramchants;

import java.util.List;
import java.util.function.UnaryOperator;

public interface ModEnchantmentEffects {

    static void onInitialize() {

        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Ramchants.id("electric_attraction"), ElectricAttractionEnchantmentEffect.CODEC);
        Registry.register(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE, Ramchants.id("add_binomial"), AddBinomialEnchantmentEffect.CODEC);
    }

    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> ARMOR_EFFECTIVENESS_MULTIPLIER = register("armor_effectiveness_multiplier", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_DAMAGE).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> CONSUME_DROP = register("consume_drop", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> GHOST_QUIVER = register("ghost_quiver", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> MISS = register("miss", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> KNOCKBACK_USER = register("knockback_user", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> STAGNATION = register("stagnation", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> BETRAYAL = register("betrayal", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));
    ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> DEFLECTION = register("deflection", (builder) -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf()));

    ComponentType<Unit> BOILING_STRING = register("boiling_string", (builder) -> builder.codec(Unit.CODEC));
    ComponentType<Unit> INACCURACY = register("inaccuracy", (builder) -> builder.codec(Unit.CODEC));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Ramchants.id(id), builderOperator.apply(ComponentType.builder()).build());
    }
}

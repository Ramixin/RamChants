package net.ramixin.ramchants.enchantments.effects;

import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ramixin.ramchants.Ramchants;

public interface ModEnchantmentEffects {

    static void onInitialize() {

        registerEntityEffect("electric_attraction", new ElectricAttractionEnchantmentEffect());

    }

    private static <T extends EnchantmentEntityEffect> void registerEntityEffect(String path, T effect) {
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Ramchants.id(path), effect.getCodec());
    }
}

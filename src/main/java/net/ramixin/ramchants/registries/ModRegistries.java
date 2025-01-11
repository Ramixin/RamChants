package net.ramixin.ramchants.registries;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.ramixin.ramchants.Ramchants;

public interface ModRegistries {

    RegistryKey<Registry<EnchantabilityEntry>> ENCHANTABILITY = RegistryKey.ofRegistry(Ramchants.id("enchantability"));


    static void onInitialize() {
        DynamicRegistries.registerSynced(ENCHANTABILITY, EnchantabilityEntry.CODEC);

    }
}

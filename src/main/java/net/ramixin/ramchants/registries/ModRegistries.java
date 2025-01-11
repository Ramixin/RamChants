package net.ramixin.ramchants.registries;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public interface ModRegistries {

    RegistryKey<Registry<EnchantabilityEntry>> ENCHANTABILITY = RegistryKey.ofRegistry(Identifier.ofVanilla("enchantability"));
    RegistryKey<Registry<LinkedCursesEntry>> LINKED_CURSES = RegistryKey.ofRegistry(Identifier.ofVanilla("linked_curses"));


    static void onInitialize() {
        DynamicRegistries.registerSynced(ENCHANTABILITY, EnchantabilityEntry.CODEC);
        DynamicRegistries.register(LINKED_CURSES, LinkedCursesEntry.CODEC);

    }
}

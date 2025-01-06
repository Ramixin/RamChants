package net.ramixin.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.ramixin.ramchants.Ramchants;

@SuppressWarnings("unused")
public interface ModEnchantments {

    // TESTED
    RegistryKey<Enchantment> VISCOSITY = of("viscosity");
    RegistryKey<Enchantment> AQUA_HAUL = of("aqua_haul");
    RegistryKey<Enchantment> ARTHROPODS_FAVOR = of("arthropods_favor");
    RegistryKey<Enchantment> ELECTRIC_ATTRACTION = of("electric_attraction");

    // IN THE WORKS
    RegistryKey<Enchantment> DESTITUTION = of("destitution");

    // COPIED FROM OLDER VERSION
    RegistryKey<Enchantment> EXPLOSIVE_FRAGILITY = of("explosive_fragility");
    RegistryKey<Enchantment> SNARE = of("snare");
    RegistryKey<Enchantment> HALLOW = of("hallow");
    RegistryKey<Enchantment> TORPIDITY = of("torpidity");
    RegistryKey<Enchantment> PLUMMET = of("plummet");
    RegistryKey<Enchantment> MOLTEN_HANDLE = of("molten_handle");
    
    private static RegistryKey<Enchantment> of(String path) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Ramchants.id(path));
    }
    
}

package net.ramixin.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.ramixin.ramchants.Ramchants;

@SuppressWarnings("unused")
public interface ModEnchantments {

    RegistryKey<Enchantment> VISCOSITY = of("viscosity"); //Aqua Affinity
    RegistryKey<Enchantment> AQUA_HAUL = of("aqua_haul"); //Depth Strider
    RegistryKey<Enchantment> ARTHROPODS_FAVOR = of("arthropods_favor"); //Bane of A.
    RegistryKey<Enchantment> ELECTRIC_ATTRACTION = of("electric_attraction"); //Channeling
    RegistryKey<Enchantment> DESTITUTION = of("destitution"); //Fortune
    RegistryKey<Enchantment> EXPLOSIVE_FRAGILITY = of("explosive_fragility"); // BLAST PROT.
    RegistryKey<Enchantment> SNARE = of("snare"); // BREACH
    RegistryKey<Enchantment> HALLOW = of("hallow"); // DENSITY
    RegistryKey<Enchantment> TORPIDITY = of("torpidity"); // EFFICIENCY
    RegistryKey<Enchantment> PLUMMET = of("plummet"); // FEATHER FALLING
    RegistryKey<Enchantment> MOLTEN_HANDLE = of("molten_handle"); // FIRE ASPECT
    RegistryKey<Enchantment> DULLNESS = of("dullness"); // SHARPNESS
    RegistryKey<Enchantment> WRAITHWARD = of("wraithward"); // SMITE
    RegistryKey<Enchantment> VULNERABILITY = of("vulnerability"); // PROTECTION
    RegistryKey<Enchantment> ARROWS_BANE = of("arrows_bane"); // PROJECTILE_PROTECTION
    RegistryKey<Enchantment> BURNING = of("burning"); // FIRE_PROTECTION
    RegistryKey<Enchantment> VOIDING = of("voiding"); // SILK TOUCH
    RegistryKey<Enchantment> CRUMBLING = of("crumbling"); // UNBREAKING
    RegistryKey<Enchantment> PRICKING = of("pricking"); // THORNS
    RegistryKey<Enchantment> DETERRENCE = of("deterrence"); // LURE
    RegistryKey<Enchantment> SEAS_WOE = of("seas_woe"); // LUCK OF THE SEA
    RegistryKey<Enchantment> GHOST_QUIVER = of("ghost_quiver"); // INFINITY
    RegistryKey<Enchantment> FLIMSY = of("flimsy"); // POWER
    RegistryKey<Enchantment> SLOW_DRAW = of("slow_draw"); // QUICK CHARGE
    RegistryKey<Enchantment> INACCURACY = of("inaccuracy"); // MULTISHOT
    RegistryKey<Enchantment> DROWNING = of("drowning"); // RESPIRATION
    RegistryKey<Enchantment> BOILING_STRING = of("boiling_string"); // FLAME
    RegistryKey<Enchantment> REPULSION = of("repulsion"); // KNOCKBACK
    RegistryKey<Enchantment> RECOIL = of("recoil"); // PUNCH
    RegistryKey<Enchantment> AQUATIC_IMPOTENCE = of("aquatic_impotence"); // IMPALING
    RegistryKey<Enchantment> WHIMSY_BLADE = of("whimsy_blade"); // SWEEPING_EDGE
    RegistryKey<Enchantment> SCARCITY = of("scarcity"); // LOOTING
    RegistryKey<Enchantment> STAGNATION = of("stagnation"); // RIPTIDE
    RegistryKey<Enchantment> BETRAYAL = of("betrayal"); // LOYALTY
    RegistryKey<Enchantment> DEFLECTION = of("deflection"); // PIERCING
    
    private static RegistryKey<Enchantment> of(String path) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Ramchants.id(path));
    }
    
}

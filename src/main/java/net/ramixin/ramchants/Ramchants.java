package net.ramixin.ramchants;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.ramixin.ramchants.enchantments.effects.ModEnchantmentEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ramchants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger("RamChants");

    //public static final RegistryKey<Registry<LinkedEnchantmentCurse>> LINKED_CURSE_KEY = RegistryKey.ofRegistry(Identifier.of(MOD_ID, "linked_curse"));

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }


    @Override
    public void onInitialize() {

        LOGGER.info("Initializing (1/1)");
        ModEnchantmentEffects.onInitialize();
        ModSounds.onInitialize();
    }
}

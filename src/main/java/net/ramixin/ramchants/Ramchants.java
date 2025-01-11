package net.ramixin.ramchants;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.ramixin.mixson.DebugMode;
import net.ramixin.mixson.Mixson;
import net.ramixin.ramchants.enchantments.effects.ModEnchantmentEffects;
import net.ramixin.ramchants.items.ModComponents;
import net.ramixin.ramchants.registries.ModRegistries;
import net.ramixin.ramchants.util.ExternalDynamicRegistryContext;
import net.ramixin.ramchants.util.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Ramchants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger("RamChants");


    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }


    @Override
    public void onInitialize() {

        LOGGER.info("Initializing (1/1)");
        mixsonOnInitialize();
        ModEnchantmentEffects.onInitialize();
        ModSounds.onInitialize();
        ModComponents.onInitialize();
        ModRegistries.onInitialize();
        ExternalDynamicRegistryContext.onInitialize();
    }

    private static void mixsonOnInitialize() {

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) Mixson.setDebugMode(DebugMode.EXPORT);
        Map<String, String> map = buildEnchantmentMap();
        for(Map.Entry<String, String> entry : map.entrySet())
            Mixson.registerModificationEvent(
                    Identifier.ofVanilla("enchantment/"+entry.getKey()),
                    id("add_exclusivity_to_"+entry.getKey()),
                    (elem) -> {
                        JsonObject object = elem.getAsJsonObject();
                        if(object.has("exclusive_set")) object.remove("exclusive_set");
                        object.addProperty("exclusive_set", "#ramchants:exclusive_set/"+entry.getValue());
                        return object;
                    }
            );

    }

    private static @NotNull Map<String, String> buildEnchantmentMap() {
        Map<String, String> map = new HashMap<>();
        map.put("flame", "boiling_string");
        map.put("unbreaking", "crumbling");
        map.put("lure", "deterrence");
        map.put("respiration", "drowning");
        map.put("channeling", "electric_attraction");
        map.put("power", "flimsy");
        map.put("fire_aspect", "molten_handle");
        map.put("feather_falling", "plummet");
        map.put("thorns", "pricking");
        map.put("punch", "recoil");
        map.put("knockback", "repulsion");
        map.put("looting", "scarcity");
        map.put("luck_of_the_sea", "seas_woe");
        map.put("quick_charge", "slow_draw");
        map.put("riptide", "stagnation");
        map.put("efficiency", "torpidity");
        map.put("depth_strider", "viscosity");
        map.put("sweeping_edge", "whimsy_blade");
        return map;
    }
}

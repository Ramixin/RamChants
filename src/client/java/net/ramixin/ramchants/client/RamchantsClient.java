package net.ramixin.ramchants.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.GrindstoneScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;

public class RamchantsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    }

    public static DynamicRegistryManager getRegistryManager() {
        if(MinecraftClient.getInstance().world == null) throw new IllegalStateException("attempted to get RegistryManager before world load");
        return MinecraftClient.getInstance().world.getRegistryManager();
    }

    public static boolean shouldDisplayEnchantabilityInTooltip() {
        return MinecraftClient.getInstance().options.advancedItemTooltips
                || MinecraftClient.getInstance().currentScreen instanceof EnchantmentScreen
                || MinecraftClient.getInstance().currentScreen instanceof GrindstoneScreen;
    }

    public static boolean shouldDisplayMinusOneInTooltip(ItemStack stack) {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if(!(screen instanceof GrindstoneScreen grindstone)) return false;
        return grindstone.getScreenHandler().slots.get(2).getStack().equals(stack);
    }
}

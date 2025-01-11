package net.ramixin.ramchants.mixins.grindstone;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.ramixin.ramchants.enchantments.ModHelper;
import net.ramixin.ramchants.items.ModComponents;
import net.ramixin.ramchants.registries.EnchantabilityEntry;
import net.ramixin.ramchants.util.ExternalDynamicRegistryContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {

    @Inject(method = "grind", at = @At("HEAD"), cancellable = true)
    private void removeSealAndIncreaseGrinds(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        ModHelper.incrementGrinds(stack);
        if(!stack.contains(ModComponents.SEALED)) return;
        Optional<DynamicRegistryManager> manager = ExternalDynamicRegistryContext.getInstance().getRegistryManager();
        if(manager.isEmpty()) return;
        stack.remove(ModComponents.SEALED);
        int enchantability = EnchantabilityEntry.getEnchantability(manager.get(), stack).actualValue(stack);
        if(enchantability >= 0) cir.setReturnValue(stack);
    }

    @ModifyReturnValue(method = "method_58073", at = @At("RETURN"))
    private static boolean allowGrindingCurses(boolean original) {
        return true;
    }

}

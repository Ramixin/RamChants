package net.ramixin.ramchants.mixins;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.ramixin.ramchants.registries.EnchantabilityEntry;
import net.ramixin.ramchants.util.ExternalDynamicRegistryContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "isEnchantable", at =  @At("HEAD"), cancellable = true)
    private void changeIsEnchantableLogic(CallbackInfoReturnable<Boolean> cir) {
        Optional<DynamicRegistryManager> maybeManager = ExternalDynamicRegistryContext.getInstance().getRegistryManager();
        if(maybeManager.isEmpty()) return;
        EnchantabilityEntry entry = EnchantabilityEntry.getEnchantability(maybeManager.get(), (ItemStack) (Object) this);
        //noinspection DataFlowIssue
        cir.setReturnValue(entry.actualValue((ItemStack) (Object) this) > 0);
    }

}

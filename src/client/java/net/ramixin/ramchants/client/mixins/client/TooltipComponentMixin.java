package net.ramixin.ramchants.client.mixins.client;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import net.ramixin.ramchants.client.items.tooltip.EnchantabilityToolTipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {

    @Inject(method = "of(Lnet/minecraft/item/tooltip/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;", at = @At("HEAD"), cancellable = true)
    private static void ofMixin(TooltipData data, CallbackInfoReturnable<TooltipComponent> cir) {
        if (data instanceof net.ramixin.ramchants.client.items.tooltip.EnchantabilityToolTipData toolTipData) cir.setReturnValue(new EnchantabilityToolTipComponent(toolTipData));
    }
}

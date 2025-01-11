package net.ramixin.ramchants.client.mixins.client;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.ramixin.ramchants.client.RamchantsClient;
import net.ramixin.ramchants.client.items.tooltip.EnchantabilityToolTipData;
import net.ramixin.ramchants.items.ModComponents;
import net.ramixin.ramchants.registries.EnchantabilityEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {


    @Inject(method = "getTooltipData", at = @At("HEAD"), cancellable = true)
    private void getTooltipDataMixin(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        EnchantabilityEntry entry = EnchantabilityEntry.getEnchantability(RamchantsClient.getRegistryManager(), stack);
        if(entry.baseValue() > 0) getEnchantabilityToolTip(entry, stack, cir);
    }

    @Unique
    private void getEnchantabilityToolTip(EnchantabilityEntry entry, ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        cir.setReturnValue(Optional.of(new EnchantabilityToolTipData(
                RamchantsClient.shouldDisplayEnchantabilityInTooltip(),
                entry.actualValue(stack),
                entry.actualBaseValue(stack),
                stack.contains(ModComponents.SEALED),
                RamchantsClient.shouldDisplayMinusOneInTooltip(stack)
        )));
    }

}

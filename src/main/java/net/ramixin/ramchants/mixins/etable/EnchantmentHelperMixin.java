package net.ramixin.ramchants.mixins.etable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.collection.Weighting;
import net.minecraft.util.math.random.Random;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V"))
    private static <T> void applyExclusivityWhenGeneratingEnchantments(Stream<RegistryEntry<Enchantment>> instance, Consumer<? super T> consumer, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
        Set<RegistryEntry<Enchantment>> enchants = ModHelper.getEnchantments(stack).getEnchantments();
        original.call(instance.filter((entry) -> {
            for(RegistryEntry<Enchantment> enchantment : enchants) if(entry.value().exclusiveSet().contains(enchantment)) return false;
            return true;
        }), consumer);
    }

    @WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V"))
    private static <T> void enforceMaxLevelWhenGeneratingEnchantments(Stream<RegistryEntry<Enchantment>> instance, Consumer<? super T> consumer, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
        ItemEnchantmentsComponent enchantsComp = ModHelper.getEnchantments(stack);
        original.call(instance.filter((entry) -> enchantsComp.getLevel(entry) < entry.value().getMaxLevel()), consumer);
    }

    @WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V"))
    private static <T> void maybeRemoveTreasureEnchantmentsWhenGeneratingEnchantments(Stream<RegistryEntry<Enchantment>> instance, Consumer<? super T> consumer, Operation<Void> original) {
        Random random = Random.create();
        original.call(instance.filter((entry) -> {
            if(!entry.isIn(EnchantmentTags.TREASURE)) return true;
            return random.nextBoolean();
        }), consumer);
    }

    @Inject(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 0), cancellable = true)
    private static void exitGenerateEnchantmentsEarly(Random random, ItemStack stack, int level, Stream<RegistryEntry<Enchantment>> possibleEnchantments, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir, @Local(ordinal = 1) List<EnchantmentLevelEntry> list) {
        if(list.isEmpty()) {
            cir.setReturnValue(List.of());
            return;
        }
        while(true) {
            Optional<EnchantmentLevelEntry> maybeEntry = Weighting.getRandom(random, list);
            if(maybeEntry.isEmpty()) continue;
            EnchantmentLevelEntry entry = maybeEntry.get();
            cir.setReturnValue(List.of(new EnchantmentLevelEntry(entry.enchantment, 1)));
            return;
        }
    }

}

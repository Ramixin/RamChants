package net.ramixin.ramchants.mixins.curses;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.ramixin.ramchants.enchantments.ModEnchantments;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BlockMixin {

    @ModifyReturnValue(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"))
    private static List<ItemStack> applyConsumeDropEffect(List<ItemStack> original, @Local(argsOnly = true) ItemStack userStack, @Local(argsOnly = true) ServerWorld world) {
        float chance = ModHelper.getConsumeDropChance(world, userStack);
        Random random  = world.getRandom();
        List<ItemStack> newResult = new ArrayList<>();
        for(ItemStack stack : original) if(random.nextFloat() > chance) newResult.add(stack);
        return newResult;
    }

    @ModifyReturnValue(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"))
    private static List<ItemStack> applyVoidingEffect(List<ItemStack> original, @Local(argsOnly = true) ItemStack userStack, @Local(argsOnly = true) ServerWorld world) {
        Optional<RegistryEntry.Reference<Enchantment>> voidingEntry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getEntry(ModEnchantments.VOIDING.getValue());
        if(voidingEntry.isEmpty()) return original;
        if(userStack.getEnchantments().getLevel(voidingEntry.get()) > 0) return List.of();
        return original;
    }

}

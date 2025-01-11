package net.ramixin.ramchants.mixins.etable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import net.ramixin.ramchants.items.ModComponents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler  {

    @Shadow @Final public int[] enchantmentPower;

    @Shadow @Final public int[] enchantmentId;

    @Shadow @Final private Random random;
    @Unique
    private boolean used = false;

    protected EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "onContentChanged", at = @At("TAIL"))
    private void setPowerLevelAndMaybeSeal(Inventory inventory, CallbackInfo ci) {
        for(int i = 0; i < this.enchantmentPower.length; i++) {
            if (this.enchantmentPower[i] > 0 && this.enchantmentId[i] == -1) this.enchantmentPower[i] = 0;
        }
        if(used && Arrays.equals(this.enchantmentPower, new int[]{0,0,0}) && inventory.getStack(0).getItem() != Items.AIR) {
            inventory.getStack(0).set(ModComponents.SEALED, Unit.INSTANCE);
        }
    }

    @Inject(method = "onButtonClick", at = @At("HEAD"))
    private void determineUsed(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        used = true;
    }

    @Inject(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V", shift = At.Shift.AFTER))
    private void sealEnchantmentsMaybe(ItemStack itemStack, int id, PlayerEntity playerEntity, int j, ItemStack itemStack2, World world, BlockPos pos, CallbackInfo ci) {
        float sealChance = (float) (0.05f * Math.pow(2, id));
        if(this.random.nextFloat() < sealChance)
            itemStack.set(ModComponents.SEALED, Unit.INSTANCE);
    }

    @WrapOperation(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;generateEnchantments(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/item/ItemStack;II)Ljava/util/List;"))
    private List<EnchantmentLevelEntry> applyCurseMaybe(EnchantmentScreenHandler instance, DynamicRegistryManager registryManager, ItemStack stack, int slot, int level, Operation<List<EnchantmentLevelEntry>> original) {
        List<EnchantmentLevelEntry> originalList = original.call(instance, registryManager, stack, slot, level);
        int enchantingPower = enchantmentPower[slot];
        if (random.nextBetween(1, enchantingPower + 1) != 1) return originalList;
        EnchantmentLevelEntry originalEntry = originalList.getFirst();
        Optional<RegistryEntry<Enchantment>> maybeCurse = ModHelper.getCurse(registryManager, this.random, originalEntry.enchantment);
        if(maybeCurse.isEmpty()) return originalList;
        int oldLev = ModHelper.getEnchantments(stack).getLevel(originalEntry.enchantment);
        ModHelper.removeEnchantment(stack, originalEntry.enchantment);
        return List.of(new EnchantmentLevelEntry(maybeCurse.get(), oldLev+1));
    }

}

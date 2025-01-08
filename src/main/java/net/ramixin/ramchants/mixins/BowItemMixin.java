package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.ramixin.ramchants.ModSounds;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public class BowItemMixin {

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getProjectileType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack cancelShootIfConsumeArrowEffect(PlayerEntity instance, ItemStack stack, Operation<ItemStack> original) {
        if(!(instance.getWorld() instanceof ServerWorld world)) return original.call(instance, stack);
        float chance = ModHelper.getConsumeArrowChance(world, stack, 0);
        if(instance.getRandom().nextFloat() >= chance) return original.call(instance, stack);
        world.playSound(null, instance.getX(), instance.getY(), instance.getZ(), ModSounds.GHOST_QUIVER, SoundCategory.PLAYERS, 1.0F, 1F);
        return ItemStack.EMPTY;
    }

}

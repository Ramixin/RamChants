package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import net.ramixin.ramchants.util.ModSounds;
import net.ramixin.ramchants.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowItemMixin {

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getProjectileType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack cancelShootIfConsumeArrowEffect(PlayerEntity instance, ItemStack stack, Operation<ItemStack> original) {
        if(!(instance.getWorld() instanceof ServerWorld world)) return original.call(instance, stack);
        float chance = ModHelper.getGhostQuiverChance(world, stack);
        if(instance.getRandom().nextFloat() >= chance) return original.call(instance, stack);
        world.playSound(null, instance.getX(), instance.getY(), instance.getZ(), ModSounds.GHOST_QUIVER, SoundCategory.PLAYERS, 1.0F, 1F);
        return ItemStack.EMPTY;
    }

    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BowItem;shootAll(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;Ljava/util/List;FFZLnet/minecraft/entity/LivingEntity;)V"))
    private void applyKnockbackUserEffect(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
        if(!(user.getWorld() instanceof ServerWorld serverWorld)) return;
        float k = ModHelper.getKnockbackUserStrength(serverWorld, stack);
        if(k > 0) ModUtil.applyKnockback(k, user);
    }

}

package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import net.ramixin.ramchants.util.ModUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;onAttacking(Lnet/minecraft/entity/Entity;)V"))
    private void applyKnockbackUserEffect(PlayerEntity instance, Entity entity, Operation<Void> original, @Local DamageSource source) {
        if(!(getWorld() instanceof ServerWorld serverWorld)) return;
        float k = ModHelper.getKnockbackUserStrength(serverWorld, source.getWeaponStack());
        if(k > 0) ModUtil.applyKnockback(k, this, getYaw());
        original.call(instance, entity);
    }

    @WrapMethod(method = "attack")
    private void applyMissEffect(Entity target, Operation<Void> original) {
        if(!(getWorld() instanceof ServerWorld serverWorld)) {
            original.call(target);
            return;
        }
        ItemStack stack = getWeaponStack();
        float chance = ModHelper.getHitMissChance(serverWorld, stack);
        if(getRandom().nextFloat() >= chance) original.call(target);
        else serverWorld.playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.PLAYERS, 1.0F, 1F);
    }

}

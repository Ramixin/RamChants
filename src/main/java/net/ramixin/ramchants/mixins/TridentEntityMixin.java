package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import net.ramixin.ramchants.util.TridentEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity implements TridentEntityDuck {

    @Unique
    private float stagnation = 0f;

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    private void applyStagnationEffect(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci) {
        if(world instanceof ServerWorld serverWorld)
            stagnation = ModHelper.getStagnationAmount(serverWorld, stack);
    }

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getDragInWater", at = @At("RETURN"))
    private float applyStagnationEffect(float original) {
        return original - stagnation;
    }

    @Override
    public float ramChants$getStagnation() {
        return stagnation;
    }

    @Override
    public void ramChants$setStagnation(float stagnation) {
        this.stagnation = stagnation;
    }
}

package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Float> STAGNATION = DataTracker.registerData(TridentEntity.class, TrackedDataHandlerRegistry.FLOAT);
    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> BETRAY = DataTracker.registerData(TridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void addStagnationToDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(STAGNATION, 0f);
        builder.add(BETRAY, false);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    private void applyStagnationEffect(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci) {
        if(world instanceof ServerWorld serverWorld) {
            this.dataTracker.set(STAGNATION, ModHelper.getStagnationAmount(serverWorld, stack));
            this.dataTracker.set(BETRAY, world.getRandom().nextFloat() < ModHelper.getBetrayalChance(serverWorld, stack));
        }

    }

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getDragInWater", at = @At("RETURN"))
    private float applyStagnationEffect(float original) {
        return original - this.dataTracker.get(STAGNATION);
    }

    @WrapMethod(method = "onPlayerCollision")
    private void betrayPlayerIfEnabled(PlayerEntity player, Operation<Void> original) {
        if(this.dataTracker.get(BETRAY)) {
            Entity owner = getOwner();
            if(owner != null && owner.getUuid() == player.getUuid()) return;
        }
        original.call(player);
    }
}

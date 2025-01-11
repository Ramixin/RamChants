package net.ramixin.ramchants.mixins.curses;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {

    @Shadow public abstract boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack);

    @Shadow @Nullable public abstract Entity getOwner();

    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "hitOrDeflect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getProjectileDeflection(Lnet/minecraft/entity/projectile/ProjectileEntity;)Lnet/minecraft/entity/ProjectileDeflection;"), cancellable = true)
    private void applyDeflectionEffect(HitResult hitResult, CallbackInfoReturnable<ProjectileDeflection> cir, @Local Entity victim) {
        if(!(((ProjectileEntity)(Object)this) instanceof PersistentProjectileEntity persistentProjectile)) return;
        if(!(getWorld() instanceof ServerWorld serverWorld)) return;
        ItemStack stack = persistentProjectile.getWeaponStack();
        float chance = ModHelper.getDeflectionChance(serverWorld, stack);
        if(serverWorld.getRandom().nextFloat() >= chance) return;
        this.deflect(ProjectileDeflection.SIMPLE, victim, getOwner(), false);
        this.setVelocity(this.getVelocity().multiply(0.2));
        cir.setReturnValue(ProjectileDeflection.SIMPLE);
    }

}

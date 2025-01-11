package net.ramixin.ramchants.mixins.curses;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute);

    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyExpressionValue(
            method = "travelInFluid",
            at = @At(value = "CONSTANT", args = "floatValue=0.0F", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D")
            )
    )
    private float allowNegativeSwimEfficiency(float original) {
        return -1.00001F;
    }

    @WrapOperation(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getProtectionAmount(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/damage/DamageSource;)F"))
    private float allowNegativeProtection(ServerWorld world, LivingEntity user, DamageSource damageSource, Operation<Float> original, @Local(argsOnly = true) LocalFloatRef amount) {
        float val = original.call(world, user, damageSource);
        if(val < 0) amount.set(DamageUtil.getInflictedDamage(amount.get(), val));
        return val;
    }

    @ModifyReturnValue(method = "getNextAirUnderwater", at = @At("RETURN"))
    private int allowDrowningExtraFast(int original) {
        EntityAttributeInstance oxygenBonus = this.getAttributeInstance(EntityAttributes.OXYGEN_BONUS);
        if(oxygenBonus == null) return original;
        double val = oxygenBonus.getValue();
        return val < 0.0F && this.random.nextDouble() >= 1.0F / (-val + 1.0F) ? original - 1 : original;
    }

    @WrapOperation(method = "dropLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootWorldContext;JLjava/util/function/Consumer;)V"))
    private void applyConsumeDropEffect(LootTable instance, LootWorldContext parameters, long seed, Consumer<ItemStack> lootConsumer, Operation<Void> original, @Local(argsOnly = true) DamageSource source, @Local(argsOnly = true) ServerWorld world) {
        original.call(instance, parameters, seed, (Consumer<ItemStack>) stack -> {
            float chance = ModHelper.getConsumeDropChance(world, source.getWeaponStack());
            Random random  = world.getRandom();
            if(random.nextFloat() > chance) lootConsumer.accept(stack);
        });
    }

}

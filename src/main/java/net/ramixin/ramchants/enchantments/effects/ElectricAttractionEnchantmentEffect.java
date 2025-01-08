package net.ramixin.ramchants.enchantments.effects;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public record ElectricAttractionEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<ElectricAttractionEnchantmentEffect> CODEC = MapCodec.unit(new ElectricAttractionEnchantmentEffect());

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        LivingEntity owner = context.owner();
        if(owner == null) return;
        BlockPos blockPos = owner.getBlockPos();
        if (!World.isValid(blockPos)) return;
        LightningEntity entity = EntityType.LIGHTNING_BOLT.spawn(world, blockPos, SpawnReason.TRIGGERED);
        if (entity == null) return;
        entity.refreshPositionAndAngles(owner.getX(), owner.getY(), owner.getZ(), entity.getYaw(), entity.getPitch());
        if (owner instanceof ServerPlayerEntity serverPlayer) entity.setChanneler(serverPlayer);

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}

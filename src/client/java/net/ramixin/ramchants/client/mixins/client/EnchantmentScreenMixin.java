package net.ramixin.ramchants.client.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.ramixin.ramchants.enchantments.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/MutableText;", ordinal = 0))
    public MutableText removeNumberFromEnchantmentPreview(String key, Object[] args, Operation<MutableText> original, @Local Optional<RegistryEntry.Reference<Enchantment>> optional) {
        return original.call(key, new Object[] { ModHelper.getEnchantmentNameWithoutLevel(optional.orElseThrow()) });
    }
}

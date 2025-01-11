package net.ramixin.ramchants.util;

import it.unimi.dsi.fastutil.ints.IntCollection;
import net.minecraft.component.type.ItemEnchantmentsComponent;

public interface ItemEnchantmentsComponentDuck {

    IntCollection ramChants$getEnchantmentLevels();

    static ItemEnchantmentsComponentDuck get(ItemEnchantmentsComponent component) {
        return (ItemEnchantmentsComponentDuck) component;
    }

}

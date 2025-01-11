package net.ramixin.ramchants.client.items.tooltip;

import net.minecraft.item.tooltip.TooltipData;

public record EnchantabilityToolTipData(boolean show, int actual, int actualBase, boolean sealed, boolean minusOne) implements TooltipData {

}

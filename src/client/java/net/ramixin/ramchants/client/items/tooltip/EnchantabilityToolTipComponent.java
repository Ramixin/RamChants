package net.ramixin.ramchants.client.items.tooltip;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.joml.Matrix4f;

public class EnchantabilityToolTipComponent implements TooltipComponent {
    private final EnchantabilityToolTipData data;
    private final Text text;

    public EnchantabilityToolTipComponent(EnchantabilityToolTipData tooltipData) {
    this.data = tooltipData;
    MutableText mutableText;
    if(!data.show()) mutableText = Text.empty();
    else mutableText = Text.literal("Enchantability: ");

    if(data.sealed()) text = mutableText.formatted(Formatting.DARK_AQUA).append("sealed");
    else {
        mutableText.formatted(Formatting.DARK_PURPLE);
        if(data.actualBase() == data.actual()) mutableText.append(String.valueOf(data.actual()));
        else mutableText.append(data.actual() + "/" + data.actualBase());
        if(data.minusOne()) mutableText.append(" (-1)");
        text = mutableText;
    }
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(text, x, y, 0, false, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 255);
        TooltipComponent.super.drawText(textRenderer, x, y, matrix, vertexConsumers);
    }

    @Override
    public int getHeight(TextRenderer textRenderer) {
        return data.show() ? 10 : 0;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return textRenderer.getWidth(text);
    }
}

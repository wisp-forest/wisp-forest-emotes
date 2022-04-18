package ninjaphenix.aofemotes.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import ninjaphenix.aofemotes.Constants;
import ninjaphenix.aofemotes.render.EmoteRenderHelper;
import ninjaphenix.aofemotes.render.RenderEmote;
import ninjaphenix.aofemotes.text.TextReaderVisitor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin({ChatHud.class})
public abstract class ChatHudMixin {
    @Redirect(
            method = {"render"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"
            )
    )
    private int drawWithShadow(TextRenderer textRenderer, MatrixStack matrices, OrderedText text, float x, float y, int color) {
        TextReaderVisitor textReaderVisitor = new TextReaderVisitor();
        text.accept(textReaderVisitor);
        float emoteSize = (float) textRenderer.getWidth(Constants.EMOTE_PLACEHOLDER);
        float emoteAlpha = (float) (color >> 24 & 255) / 255.0f;
        List<RenderEmote> renderEmoteList = EmoteRenderHelper.extractEmotes(textReaderVisitor, textRenderer, x, y);
        matrices.translate(0.0D, -0.5D, 0.0D);
        for (RenderEmote renderEmote : renderEmoteList) {
            EmoteRenderHelper.drawEmote(matrices, renderEmote, emoteSize, emoteAlpha, 1.05F, 1.5F);
        }
        matrices.translate(0.0D, 0.5D, 0.0D);
        return textRenderer.draw(matrices, textReaderVisitor.getOrderedText(), x, y, color);
    }
}

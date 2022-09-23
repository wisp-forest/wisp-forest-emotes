package ellemes.aofemotes.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.math.MatrixStack;
import ellemes.aofemotes.Constants;
import ellemes.aofemotes.render.EmoteRenderHelper;
import ellemes.aofemotes.text.TextReaderVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {ChatHud.class}, priority = 1010)
public abstract class ChatHudMixin {
    @Redirect(
            method = {"render"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"
            )
    )
    private int drawWithShadow(TextRenderer textRenderer, MatrixStack matrices, OrderedText text, float x, float y, int color) {
        matrices.translate(0.0D, -0.5D, 0.0D);
        TextReaderVisitor textReaderVisitor = new TextReaderVisitor();
        text.accept(textReaderVisitor);
        float emoteSize = (float) textRenderer.getWidth(Constants.EMOTE_PLACEHOLDER);
        float emoteAlpha = (float) (color >> 24 & 255) / 255.0f;
        EmoteRenderHelper.extractEmotes(textReaderVisitor, textRenderer, x, y, (emote, emoteX, emoteY) -> {
            EmoteRenderHelper.drawEmote(matrices, emote, emoteX, emoteY, emoteSize, emoteAlpha, 1.05F, 1.5F);
        });
        matrices.translate(0.0D, 0.5D, 0.0D);
        return textRenderer.drawWithShadow(matrices, textReaderVisitor.getOrderedText(), x, y, color);
    }
}

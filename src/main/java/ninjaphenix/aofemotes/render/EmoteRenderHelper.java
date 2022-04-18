package ninjaphenix.aofemotes.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import ninjaphenix.aofemotes.Constants;
import ninjaphenix.aofemotes.emotes.Emote;
import ninjaphenix.aofemotes.emotes.EmoteRegistry;
import ninjaphenix.aofemotes.text.TextReaderVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class EmoteRenderHelper {
    public static List<RenderEmote> extractEmotes(TextReaderVisitor textReaderVisitor, TextRenderer textRenderer, float renderX, float renderY) {
        List<RenderEmote> renderEmoteList = new ArrayList<>();
        boolean emotesLeft = true;
        while (emotesLeft) {
            String textStr = textReaderVisitor.getString();
            Matcher emoteMatch = Constants.EMOTE_ID_PATTEN.matcher(textStr);
            //noinspection AssignmentUsedAsCondition
            while (emotesLeft = emoteMatch.find()) {
                String emoteIdStr = emoteMatch.group(2);
                try {
                    int emoteId = Integer.parseInt(emoteIdStr);
                    Emote emote = EmoteRegistry.getInstance().getEmoteById(emoteId);
                    int startPos = emoteMatch.start(1);
                    int endPos = emoteMatch.end(1);
                    if (emote != null) {
                        float beforeTextWidth = (float) textRenderer.getWidth(textStr.substring(0, startPos));
                        renderEmoteList.add(new RenderEmote(emote, renderX + beforeTextWidth, renderY));
                        textReaderVisitor.replaceBetween(startPos, endPos, "  ", Style.EMPTY);
                        break;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return renderEmoteList;
    }

    public static void drawEmote(MatrixStack matrices, RenderEmote renderEmote, float size, float alpha, float sizeMult, float maxWidthMult) {
        Emote emote = renderEmote.getEmote();
        float scaleX = (float) emote.getWidth() / (float) emote.getHeight() * sizeMult;
        float scaleY = sizeMult;
        if (scaleX > maxWidthMult) {
            scaleX = maxWidthMult;
            scaleY = maxWidthMult * ((float) emote.getHeight() / (float) emote.getWidth());
        }
        scaleX = (float) Math.round(size * scaleX) / size;
        scaleY = (float) Math.round(size * scaleY) / size;
        int x = (int) (renderEmote.getX() + size * (1.0F - scaleX) / 2.0F);
        int y = (int) (renderEmote.getY() + size * (1.0F - scaleY) / 2.0F);
        RenderSystem.setShaderTexture(0, emote.getTextureIdentifier());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        int frameNumber = 1;
        if (emote.isAnimated()) {
            frameNumber = (int) (System.currentTimeMillis() / (long) emote.getFrameTimeMs() % (long) emote.getFrameCount());
        }
        DrawableHelper.drawTexture(matrices, x, y, Math.round(size * scaleX), Math.round(size * scaleY), 0.0F, (float) (emote.getHeight() * frameNumber), emote.getWidth(), emote.getHeight(), emote.getSheetWidth(), emote.getSheetHeight());
    }
}

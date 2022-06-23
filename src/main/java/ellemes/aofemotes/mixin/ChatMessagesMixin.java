package ellemes.aofemotes.mixin;

import net.minecraft.client.util.ChatMessages;
import ellemes.aofemotes.Constants;
import ellemes.aofemotes.emotes.Emote;
import ellemes.aofemotes.emotes.EmoteRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.regex.Matcher;

@Mixin({ChatMessages.class})
public class ChatMessagesMixin {
    @ModifyVariable(
            method = {"getRenderedChatMessage"},
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private static String getRenderedChatMessageParam(String message) {
        boolean emotesLeft = true;
        while (emotesLeft) {
            Matcher emoteMatch = Constants.EMOTE_PATTEN.matcher(message);
            //noinspection AssignmentUsedAsCondition
            while (emotesLeft = emoteMatch.find()) {
                String emoteName = emoteMatch.group(2);
                Emote emote = EmoteRegistry.getInstance().getEmoteByName(emoteName);
                if (emote != null) {
                    int startPos = emoteMatch.start(1);
                    int endPos = emoteMatch.end(1);
                    message = message.substring(0, startPos) + "▏" + emote.getId() + message.substring(endPos);
                    break;
                }
            }
        }
        return message;
    }
}

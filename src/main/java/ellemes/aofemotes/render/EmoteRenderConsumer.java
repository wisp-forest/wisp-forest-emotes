package ellemes.aofemotes.render;

import ellemes.aofemotes.emotes.Emote;

/**
 * @author Ellemes
 */
public interface EmoteRenderConsumer {
    void accept(Emote emote, float emoteX, float emoteY);
}

package ellemes.aofemotes.render;

import ellemes.aofemotes.emotes.Emote;

public interface EmoteRenderConsumer {
    void accept(Emote emote, float emoteX, float emoteY);
}

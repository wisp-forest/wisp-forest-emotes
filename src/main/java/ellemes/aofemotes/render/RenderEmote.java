package ellemes.aofemotes.render;

import ellemes.aofemotes.emotes.Emote;

@SuppressWarnings("ClassCanBeRecord")
public final class RenderEmote {
    private final Emote emote;
    private final float x;
    private final float y;

    public RenderEmote(Emote emote, float x, float y) {
        this.emote = emote;
        this.x = x;
        this.y = y;
    }

    public Emote getEmote() {
        return emote;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

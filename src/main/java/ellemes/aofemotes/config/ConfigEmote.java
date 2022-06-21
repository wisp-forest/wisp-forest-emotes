package ellemes.aofemotes.config;

import net.minecraft.util.Identifier;
import ellemes.aofemotes.emotes.Emote;

import java.io.IOException;

@SuppressWarnings("ClassCanBeRecord")
public final class ConfigEmote {
    private final String name;
    private final String path;
    private final int frameTime;

    public ConfigEmote(String name, String path, int frameTime) {
        this.name = name;
        this.path = path;
        this.frameTime = frameTime;
    }

    public Emote asRegistryEmote(int emoteId) throws IOException {
        return new Emote(emoteId, name, new Identifier(path), frameTime);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getFrameTime() {
        return frameTime;
    }

    static class Builder {
        private final String name;
        private String path;
        private int frameTime = -1;

        private Builder(String name) {
            this.name = name;
        }

        public static Builder create(String name) {
            return new Builder(name);
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setFrameTime(int frameTime) {
            this.frameTime = frameTime;
        }

        public boolean isValid() {
            return path != null && frameTime != -1;
        }

        public ConfigEmote finish() {
            return new ConfigEmote(name, path, frameTime);
        }
    }
}

package ninjaphenix.aofemotes.emotes;

import ellemes.aofemotes.Constants;
import ellemes.aofemotes.config.ConfigEmote;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

// todo: are int ids even needed? (spoiler yes)
//  Should find a way to remove int ids but they are currently used to "fix" line wrapping for when emotes are used
public class EmoteRegistry {
    private static EmoteRegistry emoteRegistry;
    private final ConcurrentHashMap<Integer, Emote> emoteIdMap;
    private final ConcurrentHashMap<String, Emote> emoteMap;
    private final List<ConfigEmote> emotes;

    private EmoteRegistry(List<ConfigEmote> emotes) {
        this.emotes = emotes;
        emoteIdMap = new ConcurrentHashMap<>();
        emoteMap = new ConcurrentHashMap<>();
    }

    public static EmoteRegistry getInstance() {
        return emoteRegistry;
    }

    public static void construct(List<ConfigEmote> emotes) {
        emoteRegistry = new EmoteRegistry(emotes);
    }

    public void init() {
        emoteIdMap.clear();
        emoteMap.clear();
        for (ConfigEmote emote : emotes) {
            try {
                this.registerEmote(emote.asRegistryEmote(emoteMap.size()));
            } catch (Exception e) {
                Constants.LOGGER.error("init(): Failed to load emote: " + emote.getName() + "(" + emote.getPath() + ")", e);
            }
        }
    }

    public void registerEmote(Emote emote) {
        if (emoteMap.containsKey(emote.getName())) {
            throw new RuntimeException("Emote with the name " + emote.getName() + " already exists, failed to register");
        } else {
            emoteIdMap.put(emote.getId(), emote);
            emoteMap.put(emote.getName(), emote);
        }
    }

    public void registerEmote(IntFunction<Emote> func) throws Exception {
        Emote emote = func.apply(emoteMap.size());
        if (emoteMap.containsKey(emote.getName())) {
            throw new Exception("Emote with the name " + emote.getName() + " already exists, failed to register");
        } else {
            emoteIdMap.put(emote.getId(), emote);
            emoteMap.put(emote.getName(), emote);
        }
    }

    public Emote getEmoteById(int id) {
        return emoteIdMap.get(id);
    }

    public Emote getEmoteByName(String name) {
        return emoteMap.get(name);
    }

    public Collection<String> getEmoteSuggestions() {
        return emoteMap.keySet().stream().map(name -> ":" + name + ":").collect(Collectors.toList());
    }
}

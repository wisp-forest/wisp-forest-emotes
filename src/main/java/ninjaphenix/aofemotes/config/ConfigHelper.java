package ninjaphenix.aofemotes.config;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.loader.api.FabricLoader;
import ninjaphenix.aofemotes.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ConfigHelper {
    public static List<ConfigEmote> loadOrSaveConfig() {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json");
        if (Files.exists(configFile)) {
            List<ConfigEmote> emotes = new ArrayList<>();
            try (BufferedReader reader = Files.newBufferedReader(configFile)) {
                JsonReader jsonReader = new GsonBuilder().create().newJsonReader(reader);
                jsonReader.beginObject();
                if (jsonReader.nextName().equals("emotes")) {
                    jsonReader.beginObject();
                    while (jsonReader.peek() == JsonToken.NAME) {
                        String emoteName = jsonReader.nextName();
                        JsonToken type = jsonReader.peek();
                        if (type == JsonToken.STRING) {
                            emotes.add(new ConfigEmote(emoteName, jsonReader.nextString(), 0));
                        } else if (type == JsonToken.BEGIN_OBJECT) {
                            jsonReader.beginObject();
                            ConfigEmote.Builder emote = ConfigEmote.Builder.create(emoteName);
                            while (jsonReader.peek() != JsonToken.END_OBJECT) {
                                String nextName = jsonReader.nextName();
                                if (nextName.equals("path")) {
                                    emote.setPath(jsonReader.nextString());
                                } else if (nextName.equals("frame_time")) {
                                    emote.setFrameTime(jsonReader.nextInt());
                                } else {
                                    Constants.LOGGER.warn("Emote " + emoteName + " has an invalid entry: " + nextName + ".");
                                }
                            }
                            jsonReader.endObject();
                            if (emote.isValid()) {
                                emotes.add(emote.finish());
                            } else {
                                Constants.LOGGER.warn("Invalid emote " + emoteName + " missing either path or frame_time entry.");
                            }
                        }
                    }
                    jsonReader.endObject();
                }
                jsonReader.endObject();
            } catch (IOException e) {
                Constants.LOGGER.warn("Failed to read emote config file.");
            }
            return emotes;
        } else {
            List<ConfigEmote> emotes = ConfigHelper.getDefaultConfig();
            try (BufferedWriter writer = Files.newBufferedWriter(configFile, StandardOpenOption.CREATE_NEW)) {
                JsonWriter jsonWriter = new GsonBuilder().setPrettyPrinting().create().newJsonWriter(writer);
                jsonWriter.beginObject();
                jsonWriter.name("emotes");
                jsonWriter.beginObject();
                for (ConfigEmote emote : emotes) {
                    jsonWriter.name(emote.getName());
                    if (emote.getFrameTime() == 0) {
                        jsonWriter.value(emote.getPath());
                    } else {
                        jsonWriter.beginObject();
                        jsonWriter.name("path");
                        jsonWriter.value(emote.getPath());
                        jsonWriter.name("frame_time");
                        jsonWriter.value(emote.getFrameTime());
                        jsonWriter.endObject();
                    }
                }
                jsonWriter.endObject();
                jsonWriter.endObject();
            } catch (IOException e) {
                Constants.LOGGER.warn("Failed to save default emote config file.");
            }
            return emotes;
        }
    }

    private static List<ConfigEmote> getDefaultConfig() {
        return List.of(
                new ConfigEmote("Bedge", "aofemotes:emotes/bedge.png", 0),
                new ConfigEmote("bright_eyes", "aofemotes:emotes/bright-eyes.png", 0),
                new ConfigEmote("catCry", "aofemotes:emotes/cat-cry.png", 0),
                new ConfigEmote("catScream", "aofemotes:emotes/cat-scream.png", 0),
                new ConfigEmote("cryIgnore", "aofemotes:emotes/cry-ignore.png", 0),
                new ConfigEmote("ded", "aofemotes:emotes/ded.png", 0),
                new ConfigEmote("ggeeAww", "aofemotes:emotes/ggee-aww.png", 0),
                new ConfigEmote("ggeeCoffee", "aofemotes:emotes/ggee-coffee.png", 0),
                new ConfigEmote("ggeeOOOO", "aofemotes:emotes/ggee-oooo.png", 0),
                new ConfigEmote("Hmm", "aofemotes:emotes/hmm.png", 0),
                new ConfigEmote("Hmmge", "aofemotes:emotes/hmmge.png", 0),
                new ConfigEmote("KEKW", "aofemotes:emotes/kekw.png", 0),
                new ConfigEmote("Madge", "aofemotes:emotes/madge.png", 0),
                new ConfigEmote("monkaS", "aofemotes:emotes/monkas.png", 0),
                new ConfigEmote("Okayge", "aofemotes:emotes/okayge.png", 0),
                new ConfigEmote("peepoBlush", "aofemotes:emotes/peepo-blush.png", 0),
                new ConfigEmote("peepoHappy", "aofemotes:emotes/peepo-happy.png", 0),
                new ConfigEmote("peepoHappyGun", "aofemotes:emotes/peepo-happy-gun.png", 0),
                new ConfigEmote("peepoPoint", "aofemotes:emotes/peepo-point.png", 0),
                new ConfigEmote("peepoSelfie", "aofemotes:emotes/peepo-selfie.png", 0),
                new ConfigEmote("PepeHands", "aofemotes:emotes/pepe-hands.png", 0),
                new ConfigEmote("PepeNotes", "aofemotes:emotes/pepe-notes.png", 0),
                new ConfigEmote("POGGIES", "aofemotes:emotes/poggies.png", 0),
                new ConfigEmote("Prayge", "aofemotes:emotes/prayge.png", 0),
                new ConfigEmote("Sadge", "aofemotes:emotes/sadge.png", 0),
                new ConfigEmote("Smadge", "aofemotes:emotes/smadge.png", 0),
                new ConfigEmote("Starege", "aofemotes:emotes/starege.png", 0),
                new ConfigEmote("suffer", "aofemotes:emotes/suffer.png", 0),
                new ConfigEmote("Susge", "aofemotes:emotes/susge.png", 0),
                new ConfigEmote("catJAM", "aofemotes:emotes/animated/catjam.png", 40),
                new ConfigEmote("HYPERJAMMIES", "aofemotes:emotes/animated/hyperjammies.png", 20),
                new ConfigEmote("HYPERS", "aofemotes:emotes/animated/hypers.png", 70),
                new ConfigEmote("Jammies", "aofemotes:emotes/animated/jammies.png", 90),
                new ConfigEmote("NODDERS", "aofemotes:emotes/animated/nodders.png", 20),
                new ConfigEmote("NOPERS", "aofemotes:emotes/animated/nopers.png", 70),
                new ConfigEmote("peepoShy", "aofemotes:emotes/animated/peepo-shy.png", 250),
                new ConfigEmote("Tastyge", "aofemotes:emotes/animated/tastyge.png", 30));
    }
}

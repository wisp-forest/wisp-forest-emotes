package ellemes.aofemotes.config;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.loader.api.FabricLoader;
import ellemes.aofemotes.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ellemes
 */
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
                            String path = null;
                            Integer frameTime = null;
                            while (jsonReader.peek() != JsonToken.END_OBJECT) {
                                String nextName = jsonReader.nextName();
                                if (nextName.equals("path")) {
                                    path = jsonReader.nextString();
                                } else if (nextName.equals("frame_time")) {
                                    frameTime = jsonReader.nextInt();
                                } else {
                                    Constants.LOGGER.warn("Emote " + emoteName + " has an invalid entry: " + nextName + ".");
                                }
                            }
                            jsonReader.endObject();
                            if (path != null && frameTime != null) {
                                if (frameTime < 0) {
                                    Constants.LOGGER.warn("Invalid emote " + emoteName + " frame_time is less than 0.");
                                } else {
                                    emotes.add(new ConfigEmote(emoteName, path, frameTime));
                                }
                            } else {
                                Constants.LOGGER.warn("Invalid emote " + emoteName + " missing either path or frame_time entry, or frame_time is less than 0.");
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

    private static ConfigEmote emote(String name, String fileName) {
        return emote(name, fileName, 0);
    }

    private static ConfigEmote emote(String name, String fileName, int frameTime) {
        if (frameTime > 0) {
            fileName = "animated/" + fileName;
        }
        return new ConfigEmote(name, Constants.MOD_ID + ":emotes/" + fileName, frameTime);
    }

    private static List<ConfigEmote> getDefaultConfig() {
        return List.of(
                emote("Bedge", "bedge.png"),
                emote("bright_eyes", "bright-eyes.png"),
                emote("catCry", "cat-cry.png"),
                emote("catScream", "cat-scream.png"),
                emote("cryIgnore", "cry-ignore.png"),
                emote("ded", "ded.png"),
                emote("ggeeAww", "ggee-aww.png"),
                emote("ggeeCoffee", "ggee-coffee.png"),
                emote("ggeeOOOO", "ggee-oooo.png"),
                emote("Hmm", "hmm.png"),
                emote("Hmmge", "hmmge.png"),
                emote("KEKW", "kekw.png"),
                emote("Madge", "madge.png"),
                emote("monkaS", "monkas.png"),
                emote("Okayge", "okayge.png"),
                emote("peepoBlush", "peepo-blush.png"),
                emote("peepoHappy", "peepo-happy.png"),
                emote("peepoHappyGun", "peepo-happy-gun.png"),
                emote("peepoPoint", "peepo-point.png"),
                emote("peepoSelfie", "peepo-selfie.png"),
                emote("PepeHands", "pepe-hands.png"),
                emote("PepeNotes", "pepe-notes.png"),
                emote("POGGIES", "poggies.png"),
                emote("Prayge", "prayge.png"),
                emote("Sadge", "sadge.png"),
                emote("Smadge", "smadge.png"),
                emote("Starege", "starege.png"),
                emote("suffer", "suffer.png"),
                emote("Susge", "susge.png"),
                emote("catJAM", "catjam.png", 40),
                emote("HYPERJAMMIES", "hyperjammies.png", 20),
                emote("HYPERS", "hypers.png", 70),
                emote("Jammies", "jammies.png", 90),
                emote("NODDERS", "nodders.png", 20),
                emote("NOPERS", "nopers.png", 70),
                emote("peepoShy", "peepo-shy.png", 250),
                emote("Tastyge", "tastyge.png", 30));
    }
}

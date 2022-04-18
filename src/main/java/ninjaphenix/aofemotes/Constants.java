package ninjaphenix.aofemotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class Constants {
    public static final String MOD_ID = "aofemotes";
    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);
    public static final String EMOTE_PLACEHOLDER = "  ";
    public static final Pattern EMOTE_PATTEN = Pattern.compile("(?:\\s|^)(:([A-Za-z][A-Za-z0-9_-]*):)(?:\\s|$)");
    public static final Pattern EMOTE_ID_PATTEN = Pattern.compile("(?:\\s|^)(▏([0-9]+))(?:\\s|$)");
}

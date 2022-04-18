package ninjaphenix.aofemotes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import ninjaphenix.aofemotes.config.ConfigEmote;
import ninjaphenix.aofemotes.config.ConfigHelper;
import ninjaphenix.aofemotes.emotes.EmoteRegistry;

import java.util.List;

public class Main implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        List<ConfigEmote> emotes = ConfigHelper.loadOrSaveConfig();
        EmoteRegistry.construct(emotes);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            public Identifier getFabricId() {
                return new Identifier(Constants.MOD_ID, "emotes");
            }

            public void reload(ResourceManager manager) {
                EmoteRegistry.getInstance().init();
            }
        });
    }
}

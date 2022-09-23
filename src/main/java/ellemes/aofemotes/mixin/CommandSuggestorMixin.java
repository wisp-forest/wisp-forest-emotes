package ellemes.aofemotes.mixin;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.network.ClientCommandSource;
import ellemes.aofemotes.emotes.EmoteRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin({ChatInputSuggestor.class})
public class CommandSuggestorMixin {
    @Redirect(
            method = {"refresh"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientCommandSource;getChatSuggestions()Ljava/util/Collection;"
            )
    )
    private Collection<String> breakRenderedChatMessageLines(ClientCommandSource clientCommandSource) {
        List<String> suggestions = new ArrayList<>();
        suggestions.addAll(clientCommandSource.getChatSuggestions());
        suggestions.addAll(EmoteRegistry.getInstance().getEmoteSuggestions());
        return suggestions;
    }
}

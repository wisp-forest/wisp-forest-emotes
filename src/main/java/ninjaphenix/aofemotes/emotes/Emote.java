package ninjaphenix.aofemotes.emotes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Emote {
    private final int id;
    private final String name;
    private final Identifier filePath;
    private final int frameTimeMs;
    private final int width;
    private final int height;
    private final int frameCount;

    public Emote(int id, String name, Identifier filePath, int frameTimeMs) throws IOException {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.frameTimeMs = frameTimeMs;
        try (Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(filePath)) {
            BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());
            if (bufferedImage == null) {
                throw new IOException("Failed to load image: " + filePath);
            }
            width = bufferedImage.getWidth();
            if (this.isAnimated()) {
                frameCount = bufferedImage.getHeight() / width;
                //noinspection SuspiciousNameCombination
                height = width;
            } else {
                frameCount = 1;
                height = bufferedImage.getHeight();
            }
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getFrameTimeMs() {
        return frameTimeMs;
    }

    public boolean isAnimated() {
        return frameTimeMs > 0;
    }

    public int getSheetWidth() {
        return width;
    }

    public int getSheetHeight() {
        return height * frameCount;
    }

    public Identifier getTextureIdentifier() {
        return filePath;
    }
}

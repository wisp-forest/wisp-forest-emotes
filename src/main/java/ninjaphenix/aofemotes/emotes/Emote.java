package ninjaphenix.aofemotes.emotes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

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
        Optional<Resource> optRecource = MinecraftClient.getInstance().getResourceManager().getResource(filePath);
        if(optRecource.isPresent()) {
            Resource resource = optRecource.get();
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
        else{
            width = 0;
            height = 0;
            frameCount = -1;
        }


    }

    public Emote(int id, String name, Identifier filePath, int frameTimeMs, int width, int height, int frameCount) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.frameTimeMs = frameTimeMs;
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;
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

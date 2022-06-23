package ellemes.aofemotes.text;

import net.minecraft.text.Style;

public final class TextPart {
    private final Style style;
    private final char chr;

    public TextPart(Style style, char chr) {
        this.style = style;
        this.chr = chr;
    }

    public Style getStyle() {
        return style;
    }

    public char getChar() {
        return chr;
    }
}

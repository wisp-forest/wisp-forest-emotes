package ellemes.aofemotes.text;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;

import java.util.ArrayList;
import java.util.List;

public class TextReaderVisitor implements CharacterVisitor {
    private final List<TextPart> parts = new ArrayList<>();

    @Override
    public boolean accept(int val, Style style, int currCharInt) {
        parts.add(new TextPart(style, (char) currCharInt));
        return true;
    }

    public void replaceBetween(int beginIndex, int endIndex, String text, Style style) {
        this.deleteBetween(beginIndex, endIndex);
        this.insertAt(beginIndex, text, style);
    }

    private void deleteBetween(int beginIndex, int endIndex) {
        if (endIndex > beginIndex) {
            parts.subList(beginIndex, endIndex).clear();
        }
    }

    private void insertAt(int index, String text, Style style) {
        for (int i = 0; i < text.length(); ++i) {
            parts.add(index + i, new TextPart(style, text.charAt(i)));
        }
    }

    public OrderedText getOrderedText() {
        LiteralText text = new LiteralText("");
        for (TextPart part : parts) {
            text.append(new LiteralText(Character.toString(part.getChar())).setStyle(part.getStyle()));
        }
        return text.asOrderedText();
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();
        for (TextPart part : parts) {
            builder.append(part.getChar());
        }
        return builder.toString();
    }
}

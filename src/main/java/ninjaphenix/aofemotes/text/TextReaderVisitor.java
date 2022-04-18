package ninjaphenix.aofemotes.text;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;

import java.util.ArrayList;
import java.util.List;

public class TextReaderVisitor implements CharacterVisitor {
    private final List<TextPart> textParts = new ArrayList<>();

    @Override
    public boolean accept(int val, Style style, int currCharInt) {
        textParts.add(new TextPart(style, (char) currCharInt));
        return true;
    }

    public void replaceBetween(int beginIndex, int endIndex, String text, Style style) {
        this.deleteBetween(beginIndex, endIndex);
        this.insertAt(beginIndex, text, style);
    }

    private void deleteBetween(int beginIndex, int endIndex) {
        if (endIndex > beginIndex) {
            textParts.subList(beginIndex, endIndex).clear();
        }
    }

    private void insertAt(int index, String text, Style style) {
        for (int i = 0; i < text.length(); ++i) {
            textParts.add(index + i, new TextPart(style, text.charAt(i)));
        }
    }

    public OrderedText getOrderedText() {
        LiteralText literalText = new LiteralText("");
        for (TextPart textPart : textParts) {
            literalText.append(new LiteralText(Character.toString(textPart.getChar())).setStyle(textPart.getStyle()));
        }
        return literalText.asOrderedText();
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        for (TextPart textPart : textParts) {
            sb.append(textPart.getChar());
        }
        return sb.toString();
    }
}

package dev.cheercode.connectfour.renderer;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;
import dev.cheercode.connectfour.renderer.renderer_for_idea.SimpleElement;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;
import dev.cheercode.connectfour.renderer.renderer_for_idea.decorator.AppendElementDecorator;
import dev.cheercode.connectfour.renderer.renderer_for_idea.decorator.PaintBackgroundElementDecorator;

public class BoardMaskRenderer {
    public void show(boolean[][] mask) {
        int width = mask[0].length;
        Element field = new SimpleElement("");
        for (boolean[] row : mask) {
            for (int column = 0; column < width; column++) {
                Element slot = new SimpleElement(" ");
                if (row[column]) {
                    slot = new PaintBackgroundElementDecorator(slot, BackgroundColor.BLUE);
                }
                field = new AppendElementDecorator(field, slot);
            }
            field = new AppendElementDecorator(field, "\n");
        }
        System.out.println(field.getValue());
    }
}

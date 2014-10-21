package keso.editor.gui.shape.design;

import java.awt.Rectangle;

import org.eclipse.swt.widgets.Display;

import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.KesoShape;
import keso.editor.gui.shape.KesoTextShapeContainer;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;

public class WorldKesoShapeDesign extends KesoShapeDesign {

	public WorldKesoShapeDesign(KesoShape shape) {
		super(shape);
	}
	
	/*
	public void draw(KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		
		IKesoGraphics gc = drawingmanager.getGraphics();
		IKesoShapeStyle style = drawingmanager.getStyleManager().getStyle(this.shape.getData().getIdentifier());
		
		int x = offsetx;
		int y = offsety;
		int width = this.getShape().getWidth();
		int height = this.getShape().getHeight();
		
		int fontscale = drawingmanager.fontscale;
		int shapescale = drawingmanager.shapescale;
		
		KesoFont font = style.getFont("FONT_STANDARD");
		System.err.println(font.getSize() + ", " + (font.getSize() * fontscale));
		KesoFont nfont = new KesoFont(font.getFontname(), font.getSize() * fontscale, font.getType());
		gc.setFont(nfont);
		
		
		if (style.hasShadow()) {
			Rectangle area = gc.getClipping();
			gc.setClipping(0, 0, width + 10, height + 10);
			gc.setAlpha(80);
			gc.setBackground(style.getColor("COLOR_SHADOW"));
			gc.fillRoundRectangle(x + 3, y + 3, width - 1, height - 1, 4, 4);
			gc.setAlpha(255);
			gc.setClipping(area);
		}
		
		
		gc.setBackground(style.getColor("COLOR_BACKGROUND"));
		gc.fillRoundRectangle(shapescale * x, y, shapescale * width, shapescale * height, 5, 5);
		gc.setForeground(style.getColor("COLOR_TEXT"));
		gc.drawText(this.getText(), shapescale * (x + 2), shapescale * (y + 2));
		gc.setForeground(style.getColor("COLOR_FOREGROUND"));
		gc.drawRoundRectangle(shapescale * x, shapescale * y, shapescale * (width - 1), shapescale * (height - 1), 5, 5);
		
	}
*/
}

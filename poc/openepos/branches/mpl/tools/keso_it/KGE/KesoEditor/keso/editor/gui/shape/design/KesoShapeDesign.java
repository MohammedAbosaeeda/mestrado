package keso.editor.gui.shape.design;

import java.awt.Rectangle;
import keso.editor.KGE;
import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.graphics.SwtKesoGraphics;
import keso.editor.gui.shape.KesoShape;
import keso.editor.gui.shape.KesoTextShapeContainer;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

/**
 * @author  Wilhelm Haas
 */
public class KesoShapeDesign implements IKesoShapeDesign {
	KesoShape shape;
	
	public KesoShapeDesign(KesoShape shape) {
		this.setShape(shape);
	}

	/**
	 * @param shape  the shape to set
	 * @uml.property  name="shape"
	 */
	public void setShape(KesoShape shape) {
		this.shape = shape;
	}

	/**
	 * @return  the shape
	 * @uml.property  name="shape"
	 */
	public KesoShape getShape() {
		return this.shape;
	}
	
	public void draw(KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		
		if (!this.getShape().amIHidden()) {
			IKesoGraphics gc = drawingmanager.getGraphics();
			IKesoShapeStyle style = drawingmanager.getStyleManager().getStyle(this.shape.getData().getIdentifier());
			int x = offsetx;
			int y = offsety;
			int width = this.getShape().getWidth();
			int height = this.getShape().getHeight();
			
			double fontscale = drawingmanager.fontscale;
			double shapescale = drawingmanager.shapescale;
			
			KesoFont font = style.getFont("FONT_STANDARD");
			KesoFont nfont = new KesoFont(font.getFontname(), (int) Math.floor(font.getSize() * fontscale), font.getType());
			gc.setFont(nfont);
			
			/*
			if (style.hasShadow()) {
				Rectangle area = gc.getClipping();
				if (this.getShape().getParent() != null) {
					Rectangle clientarea = this.getShape().getParent().getChildArea();	
					gc.setClipping(offsetx, offsety, clientarea.width - this.getShape().getX() + 1, clientarea.height - this.getShape().getY() + 1);
				}
				gc.setAlpha(80);
				gc.setBackground(style.getColor("COLOR_SHADOW"));
				gc.fillRoundRectangle(x + 3, y + 3, width - 1, height - 1, 4, 4);
				gc.setAlpha(255);
				gc.setClipping(area);
			}
			*/
			
			gc.setLineWidth((int) Math.floor(1 * shapescale));
			
			gc.setBackground(style.getColor("COLOR_BACKGROUND"));
			gc.fillRoundRectangle((int) Math.ceil(shapescale * x), 
					(int) Math.ceil(shapescale * y), 
					(int) Math.ceil(shapescale * width), 
					(int) Math.ceil(shapescale * height), 
					(int) Math.ceil(shapescale * 4), 
					(int) Math.ceil(shapescale * 4));
			gc.setForeground(style.getColor("COLOR_TEXT"));
			gc.drawText(this.getText(), 
					(int) Math.ceil(shapescale * (x + 2)), 
					(int) Math.ceil(shapescale * (y + 2)), 
					true);
			
			gc.setForeground(style.getColor("COLOR_FOREGROUND"));
			gc.drawRoundRectangle((int) Math.ceil(shapescale * x), 
					(int) Math.ceil(shapescale * y), 
					(int) Math.ceil(shapescale * width), 
					(int) Math.ceil(shapescale * height), 
					(int) Math.ceil(shapescale * 4), 
					(int) Math.ceil(shapescale * 4));
		}
		
	}

	public String getText() {
		return this.getShape().getData().getIdentifier() + ": " + this.getShape().getData().getName();
	}
	
	public int getMinWidth() {
		int minwidth = 0;
		int width;
		String text = this.getText();
		GC gc = this.getShape().getCanvas().getGC();
		minwidth = 2 + (new SwtKesoGraphics(gc)).textExtent(text) + 7;
		gc.dispose();
		return (minwidth < 50) ? 50 : minwidth;
	}

	public int getMinHeight() {
		int minheight = 0;
		GC gc = this.getShape().getCanvas().getGC();
		minheight = gc.getFontMetrics().getHeight();
		gc.dispose();
		return (minheight == 0) ? 18 : minheight;
	}
}

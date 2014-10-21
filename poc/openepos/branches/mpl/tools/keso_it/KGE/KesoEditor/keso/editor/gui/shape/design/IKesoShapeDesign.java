package keso.editor.gui.shape.design;

import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.shape.KesoShape;
import keso.editor.gui.shape.KesoTextShapeContainer;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public interface IKesoShapeDesign {
	public void setShape(KesoShape shape);
	public void draw(KesoDrawingManager drawingmanager, int offsetx, int offsety);
	public int getMinWidth();
	public int getMinHeight();
}

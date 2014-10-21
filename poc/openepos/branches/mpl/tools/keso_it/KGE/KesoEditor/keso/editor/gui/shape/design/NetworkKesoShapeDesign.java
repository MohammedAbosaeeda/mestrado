package keso.editor.gui.shape.design;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import keso.editor.KGE;
import keso.editor.data.IKesoData;
import keso.editor.data.KesoData;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.gui.canvas.drawing.KesoDrawingManager;
import keso.editor.gui.graphics.IKesoGraphics;
import keso.editor.gui.graphics.KesoColor;
import keso.editor.gui.graphics.KesoFont;
import keso.editor.gui.shape.KesoLine;
import keso.editor.gui.shape.KesoShape;
import keso.editor.gui.shape.KesoTextShape;
import keso.editor.gui.shape.KesoTextShapeContainer;
import keso.editor.gui.shape.design.style.IKesoShapeStyle;
import keso.editor.gui.shape.design.stylemanager.IKesoShapeStyleManager;
import keso.editor.property.IKesoProperty;

public class NetworkKesoShapeDesign extends KesoShapeDesign {

	public NetworkKesoShapeDesign(KesoShape shape) {
		super(shape);
	}
	
	public void draw(KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		boolean highlight = false;
		IKesoGraphics gc = drawingmanager.getGraphics();
		IKesoShapeStyle style = drawingmanager.getStyleManager().getStyle(this.shape.getData().getIdentifier());
		super.draw(drawingmanager, offsetx, offsety);
		
		int oldlinewidth = gc.getLineWidth();
		/*
		gc.setLineWidth(5);
		gc.setLineCap(IKesoGraphics.CAP_ROUND);
		gc.setForeground(drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_CONNECTION"));
		gc.drawLine(offsetx + 6, offsety + this.getMinHeight() - 20 + 10, offsetx + this.getShape().getWidth() - 6, offsety + this.getMinHeight() - 20 + 10);
		gc.setLineWidth(oldlinewidth);
		*/
		
		
		Rectangle oldrect = gc.getClipping();
		
		gc.setClipping(0, 0, this.getShape().getCanvas().getRootShape().getWidth(),
				this.getShape().getCanvas().getRootShape().getHeight());
		
		double fontscale = drawingmanager.fontscale;
		double shapescale = drawingmanager.shapescale;
		
		KesoFont font = style.getFont("FONT_CONNECTION");
		KesoFont nfont = new KesoFont(font.getFontname(), (int) Math.floor(font.getSize() * fontscale), font.getType());
		gc.setFont(nfont);
		
		//Vector services = this.filter(KesoFilterManager.get("filter_services").filter(this.getShape().getData()));
		Vector connections = this.filter(KesoFilterManager.get("filter_imports_and_services").filter(this.getShape().getData()));
		Vector cpy_connections = (Vector) connections.clone();
		
		
		if (this.getShape().getCanvas().isHighlightConnections() && this.getShape().getCanvas().getSelection() != null) {
			if (this.getShape().getCanvas().getSelection() ==  this.getShape()) {
				highlight = true;
				for (Iterator i = cpy_connections.iterator(); i.hasNext(); ) {
					KesoData target = (KesoData) i.next();
					this.drawConnectionToNetwork(this.getShape().getCanvas().find(target), 
							drawingmanager, true, offsetx, offsety);
					connections.remove(target);
				}
			} else {
				Vector selections = this.filter(KesoFilterManager.get("filter_selected_imports_and_services").filter(this.getShape().getCanvas().getSelection().getData()));
				for (Iterator i = selections.iterator(); i.hasNext(); ){
					IKesoData selection = (IKesoData) i.next();
					if (selection.getIdentifier().equals(KGE.SERVICE)) {
						for (Iterator j = cpy_connections.iterator(); j.hasNext(); ) {
							IKesoData connection = (IKesoData) j.next();
							if (selection.equals(connection)) {
								highlight = true;
								this.drawConnectionToNetwork(this.getShape().getCanvas().find(connection), drawingmanager,
										true, offsetx, offsety);
								connections.remove(connection);
							} else {
								if (connection.getIdentifier().equals(KGE.IMPORT) && 
										connection.getName().equals(selection.getName())) {
									highlight = true;
									this.drawConnectionToNetwork(this.getShape().getCanvas().find(connection), drawingmanager,
											true, offsetx, offsety);
									connections.remove(connection);
								}
							}
						}
					} else {
						IKesoData service = this.filterImportedService(selection, connections);
						if (service != null) {
							highlight = true;
							this.drawConnectionToNetwork(this.getShape().getCanvas().find(service), drawingmanager,
									true, offsetx, offsety);
							connections.remove(service);
							
							this.drawConnectionToNetwork(this.getShape().getCanvas().find(selection), drawingmanager,
									true, offsetx, offsety);
							connections.remove(selection);
						}
					}
				}
			}
		}
		
		
		for (Iterator i = connections.iterator(); i.hasNext(); ) {
			KesoData target = (KesoData) i.next();
			this.drawConnectionToNetwork(this.getShape().getCanvas().find(target), 
					drawingmanager, offsetx, offsety);
			
		}
		
		
		
		font = style.getFont("FONT_STANDARD");
		nfont = new KesoFont(font.getFontname(), (int) Math.floor(font.getSize() * fontscale), font.getType());
		gc.setFont(nfont);
		
		gc.setClipping(oldrect);
		
		if (highlight) {
			gc.setForeground(drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_HIGHLIGHTED_CONNECTION"));
		} else {
			gc.setForeground(drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_CONNECTION"));
		}
		
		gc.setLineWidth((int) Math.floor(shapescale * 5));
		gc.setLineCap(IKesoGraphics.CAP_ROUND);
		gc.drawLine((int) Math.ceil(shapescale * (offsetx + 6)), 
				(int) Math.ceil(shapescale * (offsety + this.getMinHeight() - 20 + 10)), 
				(int) Math.ceil(shapescale * (offsetx + this.getShape().getWidth() - 6)), 
				(int) Math.ceil(shapescale * (offsety + this.getMinHeight() - 20 + 10)));
		gc.setLineWidth(oldlinewidth);
		
	}
	
	public int getMinHeight() {
		int minheight = super.getMinHeight();
		minheight += 20;
		return minheight;
	}
	
	private Vector filter(Vector connections) {
		Vector filtered = new Vector();
		for (Iterator i = connections.iterator(); i.hasNext(); ) {
			KesoData data = (KesoData) i.next();
			if (this.isConnected(data)) {
				filtered.add(data);
			}
		}
		return filtered;
	}
	
	private IKesoData filterImportedService(IKesoData importdata, Vector connections) {
		for (Iterator i = connections.iterator(); i.hasNext(); ){
			IKesoData data = (IKesoData) i.next();
		
			if (data.getIdentifier().equals(KGE.SERVICE) &&
					data.getName().equals(importdata.getName())) {
				return data;
			}
		}
		return null;
	}
	
	private boolean isConnected(IKesoData data) {
		IKesoProperty property = data.getPropertyContainer().getFirstPropertyByName("Access");
		if (property != null) {
			String [] accesses = property.getValue().split(":");
			for (int k = 0; k < accesses.length; k++) {
				accesses[k] = accesses[k].trim();
				if (accesses[k].equals(this.getShape().getData().getName())) {
					return true;
				}
			}	
		}
		return false;
	}
	
	private boolean isConnectedTo(IKesoData source, IKesoData network) {
		IKesoProperty property = source.getPropertyContainer().getFirstPropertyByName("Access");
		if (property != null) {
			String [] accesses = property.getValue().split(":");
			for (int k = 0; k < accesses.length; k++) {
				if (network.getName().equals(accesses[k].trim())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void drawConnectionToNetwork(KesoShape target, KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		this.drawConnectionToNetwork(target, drawingmanager, false, offsetx, offsety);
	}
	
	private void drawConnectionToNetwork(KesoShape target, KesoDrawingManager drawingmanager, boolean highlighted, int offsetx, int offsety) {
		KesoShape source = this.getShape();
		KesoShape cpytarget;
		
		if (source == null || target == null) {
			return;
		}
		cpytarget = target;
		
		while (source.amIHidden() && source != null) {
			source = source.getParent();
		}
		
		String text = null;
		
		if (target != null && target.amIHidden()) {
			text = target.getData().getName();
		}
		
		while (target.amIHidden() && target != null) {
			target = target.getParent();
		}
		
		if (source == null || target == null) {
			return;
		}
		
		KesoShape tmp = target;
		target = source;
		source = tmp;
		
		Rectangle bounds_source = new Rectangle(source.getAbsoluteX(), source.getAbsoluteY(), source.getWidth(), source.getHeight());
		Rectangle bounds_target = new Rectangle(target.getAbsoluteX(), target.getAbsoluteY(), target.getWidth(), target.getHeight());
		
		Point start_point = new Point(bounds_source.x + bounds_source.width / 2, bounds_source.y + bounds_source.height / 2);
		Point end_point = new Point(bounds_target.x + bounds_target.width / 2, bounds_target.y + bounds_target.height / 2);
		
		end_point.y += 7;
		
		
		if (start_point.x > bounds_target.x + bounds_target.width - 6) {
			end_point.x = bounds_target.x + bounds_target.width - 6;
		} else if (start_point.x < bounds_target.x + 6) {
			end_point.x = bounds_target.x + 6;
		} else {
			end_point.x = start_point.x;
		}
		
		if (end_point.x > bounds_source.x + bounds_source.width) {
			start_point.x = bounds_source.x + bounds_source.width;
		} else if (end_point.x < bounds_source.x) {
			start_point.x = bounds_source.x;
		} else {
			start_point.x = end_point.x;
		}
		
		if (end_point.y > bounds_source.y + bounds_source.height) {
			start_point.y = bounds_source.y + bounds_source.height;
		} else if (end_point.y < bounds_source.y) {
			start_point.y = bounds_source.y;
		} else {
			start_point.y = end_point.y;
		}
		
		double shapescale = drawingmanager.shapescale;
		
		IKesoGraphics gc = drawingmanager.getGraphics();
		
		
		KesoLine line = new KesoLine(
				(int) Math.ceil(shapescale * (start_point.x + drawingmanager.start_offsetx)),
				(int) Math.ceil(shapescale * (start_point.y + drawingmanager.start_offsety)), 
				(int) Math.ceil(shapescale * (end_point.x + drawingmanager.start_offsetx)), 
				(int) Math.ceil(shapescale * (end_point.y + drawingmanager.start_offsety)));
		
		if (highlighted) {
			gc.setLineWidth((int) Math.floor(1 * shapescale));
			KesoColor color = drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_HIGHLIGHTED_CONNECTION");
			line.setColor(color);
			gc.setForeground(color);
			
			drawingmanager.getSpecialLineContainer().add(line);
		} else {
			KesoColor color = drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_CONNECTION");
			KesoLine found = drawingmanager.getSpecialLineContainer().find(line);
			if (found != null) {
				line = found;
				color = line.getColor();
				gc.setLineWidth((int) Math.floor(1 * shapescale));
			} else {
				gc.setLineWidth((int) Math.floor(1 * shapescale));
			}
			line.setColor(color);
			gc.setForeground(color);
		}
		gc.drawLine((int) Math.ceil(shapescale * (start_point.x + drawingmanager.start_offsetx)), 
				(int) Math.ceil(shapescale * (start_point.y + drawingmanager.start_offsety)), 
				(int) Math.ceil(shapescale * (end_point.x + drawingmanager.start_offsetx)), 
				(int) Math.ceil(shapescale * (end_point.y + drawingmanager.start_offsety)));
		gc.setLineWidth((int) Math.floor(1 * shapescale));
		
		if (!highlighted) {
			gc.setForeground(drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_CONNECTION"));
		}
		
		if (text != null && this.getShape().getCanvas().isTextAtConnectionLine()) {
			KesoTextShape textshape = new KesoTextShape(line, text, gc.textExtent(text), gc.getCharHeight() - 2);
			if (this.getShape().getCanvas().isArrowAtConnectionLine()) {
				if (cpytarget.getData().getIdentifier().equals(KGE.SERVICE)) {
					if (textshape.getAngle() == - 90.0) {
						if (start_point.y > end_point.y) {
							text = text + " >";
						} else {
							text = "< " + text;
						}
					} else {
						double angle = textshape.getAngle();
						if (end_point.x < start_point.x && ((angle >= 0 && angle <= 90) || (angle >= 0 && angle >= 270))) {
							text = "< " + text;
						} else {
							text = text + " >";
						}
					}
				} else if (cpytarget.getData().getIdentifier().equals(KGE.IMPORT)) {
					if (textshape.getAngle() == - 90.0) {
						if (start_point.y > end_point.y) {
							text = "< " + text;
						} else {
							text = text + " >";
						}
					} else {
						double angle = textshape.getAngle();
						if (end_point.x < start_point.x && ((angle >= 0 && angle <= 90) || (angle >= 0 && angle >= 270))) {
							text = text + " >";
						} else {
							text = "< " + text;
						}
					}
				}
		
				textshape.setText(text);
			}
			
			drawingmanager.getTextShapeContainer().add(textshape);
			
			gc.drawText(textshape.getText(), 
					textshape.getLocation().x,
					textshape.getLocation().y,
					(float) textshape.getAngle());
			
		}
		
	}
}

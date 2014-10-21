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
import keso.editor.data.NetworkKesoData;
import keso.editor.filter.KesoFilter;
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

public class ImportKesoShapeDesign extends KesoShapeDesign {

	public ImportKesoShapeDesign(KesoShape shape) {
		super(shape);
	}
	
	public void draw(KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		IKesoGraphics gc = drawingmanager.getGraphics();
		IKesoShapeStyle style = drawingmanager.getStyleManager().getStyle(this.shape.getData().getIdentifier());
		
		super.draw(drawingmanager, offsetx, offsety);
		
		Rectangle oldrect = gc.getClipping();
		
		gc.setClipping(0, 0, this.getShape().getCanvas().getRootShape().getWidth(),
				this.getShape().getCanvas().getRootShape().getHeight());
		
		
		double fontscale = drawingmanager.fontscale;
		double shapescale = drawingmanager.shapescale;
		
		KesoFont font = style.getFont("FONT_CONNECTION");
		KesoFont nfont = new KesoFont(font.getFontname(), (int) Math.floor(font.getSize() * fontscale), font.getType());
		gc.setFont(nfont);
		
		Vector local_services = this.filter(KesoFilterManager.get("filter_local_services").filter(this.getShape().getData())); 
		Vector services = this.filter(KesoFilterManager.get("filter_services").filter(this.getShape().getData()));
		Vector networks = this.filter(KesoFilterManager.get("filter_networks").filter(this.getShape().getData()));
		
		
		if (this.getShape().getCanvas().isHighlightConnections() && this.getShape().getCanvas().getSelection() != null) {
			Vector selections = this.filter(KesoFilterManager.get("filter_selected_imports_services_and_networks").filter(this.getShape().getCanvas().getSelection().getData()));
		
			Vector cpy_networks = (Vector) networks.clone();
			if (selections.contains(this.getShape().getData())) {

				if (services.size() != 0) {
					IKesoData service = (IKesoData) services.get(0);
					if (local_services.contains(service)) {
						this.drawConnectionToService(this.getShape().getCanvas().find(service), 
								drawingmanager, true, offsetx, offsety);
						local_services.clear();
					} //else {
						for (Iterator i = cpy_networks.iterator(); i.hasNext(); ) {
							IKesoData network = (IKesoData) i.next();
							if (this.isConnectedTo(service, network)) {
								this.drawConnectionToNetwork(this.getShape().getCanvas().find(network), 
										drawingmanager, true, offsetx, offsety);
								networks.remove(network);
							}
						}
					//}
					services.clear();
				}
			} else {

				
				if (local_services.size() != 0) {
					IKesoData service = (IKesoData) local_services.get(0);	
					if (selections.contains(service)) {
						this.drawConnectionToService(this.getShape().getCanvas().find(service), 
								drawingmanager, true, offsetx, offsety);
						//services.clear();
						local_services.clear();
					}
				}
				
				if (services.size() != 0) {
					IKesoData service = (IKesoData) services.get(0);	
					if (selections.contains(service)) {						
						for (Iterator i = cpy_networks.iterator(); i.hasNext(); ) {
							IKesoData network = (IKesoData) i.next();
							if (this.isConnectedTo(service, network)) {
								this.drawConnectionToNetwork(this.getShape().getCanvas().find(network), 
										drawingmanager, true, offsetx, offsety);
								networks.remove(network);
							}
						}
						services.clear();
					}
				}
				
				cpy_networks = (Vector) networks.clone();
				for (Iterator i = cpy_networks.iterator(); i.hasNext(); ) {
					IKesoData network = (IKesoData) i.next();
					if (selections.contains(network)) {
						this.drawConnectionToNetwork(this.getShape().getCanvas().find(network), 
								drawingmanager, true, offsetx, offsety);
						networks.remove(network);
					}
				}
				
			}
		}
		
		
		if (local_services.size() != 0) {
			IKesoData service = (IKesoData) local_services.get(0);
			this.drawConnectionToService(this.getShape().getCanvas().find(service), 
					drawingmanager,  offsetx, offsety);
		}
		
		for (Iterator i = networks.iterator(); i.hasNext(); ) {
			IKesoData network = (IKesoData) i.next();
			this.drawConnectionToNetwork(this.getShape().getCanvas().find(network), 
					drawingmanager,  offsetx, offsety);
		}
		
		
		font = style.getFont("FONT_STANDARD");
		nfont = new KesoFont(font.getFontname(), (int) Math.floor(font.getSize() * fontscale), font.getType());
		gc.setFont(nfont);
		
		gc.setClipping(oldrect);
		
	
	}
	
	private Vector filter(Vector connections) {
		Vector filtered = new Vector();
		for (Iterator i = connections.iterator(); i.hasNext(); ){
			IKesoData data = (IKesoData) i.next();
			if (data == this.getShape().getData()) {
				filtered.add(data);
			} else {
				if (data.getIdentifier().equals(KGE.SERVICE)) {
					if (data.getName().equals(this.getShape().getData().getName())) {
						filtered.add(data);
					}
				} else{
					if (this.isConnectedTo(data)) {
						filtered.add(data);
					}
				}
			}
		}
		return filtered;
	}
	
	private boolean isConnectedTo(IKesoData network) {
		return this.isConnectedTo(this.getShape().getData(), network);
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
	
	private void drawConnectionToService(KesoShape target, KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		this.drawConnectionToService(target, drawingmanager, false, offsetx, offsety);
	}
	
	private void drawConnectionToService(KesoShape target, KesoDrawingManager drawingmanager, boolean highlighted, int offsetx, int offsety) {
		KesoShape source = this.getShape();
		double shapescale = drawingmanager.shapescale;
		
		if (source == null || target == null) {
			return;
		}
		
		String importtext = null;
		
		if (source.amIHidden()) {
			importtext = source.getData().getName();
		}
		
		while (source.amIHidden() && source != null) {
			source = source.getParent();
		}
		
		if (!target.amIHidden()) {
			importtext = null;
		}
		
		while (target.amIHidden() && target != null) {
			target = target.getParent();
		}
		
		if (source == null || target == null) {
			return;
		}
		
		Rectangle bounds_source = new Rectangle(source.getAbsoluteX(), source.getAbsoluteY(), source.getWidth(), source.getHeight());
		Rectangle bounds_target = new Rectangle(target.getAbsoluteX(), target.getAbsoluteY(), target.getWidth(), target.getHeight());
		
		Point start_point = new Point(bounds_source.x + bounds_source.width / 2, bounds_source.y + bounds_source.height / 2);
		Point end_point = new Point(bounds_target.x + bounds_target.width / 2, bounds_target.y + bounds_target.height / 2);
		
		
		if (start_point.x > bounds_target.x + bounds_target.width) {
			end_point.x = bounds_target.x + bounds_target.width;
		} else if (start_point.x < bounds_target.x) {
			end_point.x = bounds_target.x;
		} else {
			end_point.x = start_point.x;
		}
		
		if (start_point.y > bounds_target.y + bounds_target.height) {
			end_point.y = bounds_target.y + bounds_target.height;
		} else if (start_point.y < bounds_target.y) {
			end_point.y = bounds_target.y;
		} else {
			end_point.y = start_point.y;
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
		
		if (start_point.x != end_point.x || start_point.y != end_point.y) {
			IKesoGraphics gc = drawingmanager.getGraphics();
			
			//KesoLine line = new KesoLine(start_point, end_point);
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
			
			//gc.setForeground(new KesoColor(255, 0, 0));
			
			gc.drawLine((int) Math.ceil(shapescale * (start_point.x + drawingmanager.start_offsetx)), 
					(int) Math.ceil(shapescale * (start_point.y + drawingmanager.start_offsety)), 
					(int) Math.ceil(shapescale * (end_point.x + drawingmanager.start_offsetx)), 
					(int) Math.ceil(shapescale * (end_point.y + drawingmanager.start_offsety)));
			
			/*
			gc.drawLine(drawingmanager.start_offsetx + (int) Math.ceil(shapescale * start_point.x), 
					drawingmanager.start_offsety + (int) Math.ceil(shapescale * start_point.y), 
					drawingmanager.start_offsetx + (int) Math.ceil(shapescale * end_point.x), 
					drawingmanager.start_offsety + (int) Math.ceil(shapescale * end_point.y));
			/*
			gc.drawLine((int) Math.ceil(drawingmanager.start_offsetx + shapescale * start_point.x), 
					(int) Math.ceil(drawingmanager.start_offsety + shapescale * start_point.y), 
					(int) Math.ceil(drawingmanager.start_offsetx + shapescale * end_point.x), 
					(int) Math.ceil(drawingmanager.start_offsety + shapescale * end_point.y));
			*/
			gc.setLineWidth((int) Math.floor(1 * shapescale));
			
			if (!highlighted) {
				gc.setForeground(drawingmanager.getStyleManager().getStyle(this.getShape().getData().getIdentifier()).getColor("COLOR_CONNECTION"));
			}
			
			if (importtext != null  && this.getShape().getCanvas().isTextAtConnectionLine()) {
				KesoTextShape textshape = new KesoTextShape(line, importtext, gc.textExtent(importtext), gc.getCharHeight() - 2);
				if (this.getShape().getCanvas().isArrowAtConnectionLine()) {
					if (textshape.getAngle() == - 90.0) {
						if (start_point.y > end_point.y) {
							importtext = "< " + importtext;
						} else {
							importtext = importtext + " >";
						}
					} else {
						double angle = textshape.getAngle();
						if (start_point.x > end_point.x && ((angle >= 0 && angle <= 90) || (angle >= 0 && angle >= 270))) {
							importtext = importtext + " >";
						} else {
							importtext = "< " + importtext;
						}
					}
					textshape.setText(importtext);
				}
				drawingmanager.getTextShapeContainer().add(textshape);
				
				gc.drawText(importtext, 
						textshape.getLocation().x,
						textshape.getLocation().y,
						(float) textshape.getAngle());
				
			}
			
			
			
		}
		
	}
	
	public void drawConnectionToNetwork(KesoShape target, KesoDrawingManager drawingmanager, int offsetx, int offsety) {
		this.drawConnectionToNetwork(target, drawingmanager, false, offsetx, offsety);
	}
	
	public void drawConnectionToNetwork(KesoShape target, KesoDrawingManager drawingmanager, boolean highlighted, int offsetx, int offsety) {
		KesoShape source = this.getShape();
		
		if (source == null || target == null) {
			return;
		}
		
		String importtext = null;
		
		if (source != null && source.amIHidden()) {
			importtext = source.getData().getName();
		}
		
		while (source.amIHidden() && source != null) {
			source = source.getParent();
		}
		
		while (target.amIHidden() && target != null) {
			target = target.getParent();
		}
		
		if (source == null || target == null) {
			return;
		}
		
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
		
		if (importtext != null && this.getShape().getCanvas().isTextAtConnectionLine()) {
			KesoTextShape textshape = new KesoTextShape(line, importtext, gc.textExtent(importtext), gc.getCharHeight() - 2);
			if (this.getShape().getCanvas().isArrowAtConnectionLine()) {
				if (textshape.getAngle() == - 90.0) {
					if (start_point.y > end_point.y) {
						importtext = "< " + importtext;
					} else {
						importtext = importtext + " >";
					}
				} else {
					double angle = textshape.getAngle();
					if (start_point.x > end_point.x && ((angle >= 0 && angle <= 90) || (angle >= 0 && angle >= 270))) {
						importtext = importtext + " >";
					} else {
						importtext = "< " + importtext;
					}
				}
				textshape.setText(importtext);
			}
			drawingmanager.getTextShapeContainer().add(textshape);
			gc.drawText(importtext, 
					textshape.getLocation().x,
					textshape.getLocation().y,
					(float) textshape.getAngle());
		}
	}
}

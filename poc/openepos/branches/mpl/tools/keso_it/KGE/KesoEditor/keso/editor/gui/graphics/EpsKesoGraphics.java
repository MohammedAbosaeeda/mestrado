package keso.editor.gui.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import net.sf.epsgraphics.EpsGraphics;
import org.eclipse.swt.SWT;

/**
 * @author  Wilhelm Haas
 */
public class EpsKesoGraphics implements IKesoGraphics {

	EpsGraphics eps;
	KesoColor background;
	Color epsbackground;
	KesoColor foreground;
	Color epsforeground;
	
	float rotation;
	Point translation = new Point(0, 0);
	
	BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
	public EpsKesoGraphics(EpsGraphics eps) {
		this.eps = eps;
	}

	public void setAntialias(boolean antialias) {
		if (antialias) {
			this.eps.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			this.eps.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
	}

	public boolean getAntialias() {
		return (this.eps.getRenderingHint(RenderingHints.KEY_ANTIALIASING) == RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void setAlpha(int alpha) {
		this.eps.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, new Integer(alpha));
	}

	public int getAlpha() {
		return 255; //((Integer) this.eps.getRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION)).intValue();
	}

	public void setLineWidth(int width) {
		this.stroke = new BasicStroke(width, this.stroke.getEndCap(), this.stroke.getLineJoin());
		this.eps.setStroke(stroke);
	}

	public int getLineWidth() {
		return (int) ((BasicStroke) this.eps.getStroke()).getLineWidth();
	}

	public void setLineCap(int cap) {
		int epscap = SWT.CAP_FLAT;
		switch(cap) {
			case CAP_ROUND:
				epscap = BasicStroke.CAP_ROUND;
				break;
			case CAP_FLAT:
				epscap = BasicStroke.CAP_BUTT;
				break;
			case CAP_SQUARE:
				epscap = BasicStroke.CAP_SQUARE;
				break;
			default:
		}
		this.stroke = new BasicStroke(this.stroke.getLineWidth(), epscap, this.stroke.getLineJoin());
	}

	public int getLineCap() {
		int cap = CAP_FLAT;
		switch(cap) {
			case  BasicStroke.CAP_ROUND:
				cap = CAP_ROUND;
				break;
			case BasicStroke.CAP_BUTT:
				cap = CAP_FLAT;
				break;
			case BasicStroke.CAP_SQUARE:
				cap = CAP_SQUARE;
				break;
			default:
		}
		return cap;
	}

	/**
	 * @param background  the background to set
	 * @uml.property  name="background"
	 */
	public void setBackground(KesoColor color) {
		this.background = color;
		this.epsbackground = new Color(color.red, color.green, color.blue);
		this.eps.setColor(this.epsbackground);
	}

	/**
	 * @return  the background
	 * @uml.property  name="background"
	 */
	public KesoColor getBackground() {
		return this.background;
	}

	/**
	 * @param foreground  the foreground to set
	 * @uml.property  name="foreground"
	 */
	public void setForeground(KesoColor color) {
		this.foreground = color;
		this.epsforeground = new Color(color.red, color.green, color.blue);
		this.eps.setColor(this.epsforeground);
	}

	/**
	 * @return  the foreground
	 * @uml.property  name="foreground"
	 */
	public KesoColor getForeground() {
		return this.foreground;
	}

	public void setClipping(Rectangle clipping) {
		this.eps.setClip(clipping.x, clipping.y, clipping.width, clipping.height);
	}

	public Rectangle getClipping() {
		return this.eps.getClipBounds();
		
	}

	public void setClipping(int x, int y, int width, int height) {
		this.setClipping(new Rectangle(x, y, width, height));
	}

	public void fillRoundRectangle(int x, int y, int width, int height, int rx,
			int ry) {
		this.eps.fillRoundRect(x, y, width, height, rx, ry);

	}

	public void drawRoundRectangle(int x, int y, int width, int height, int rx,
			int ry) {
		this.eps.drawRoundRect(x, y, width, height, rx, ry);
	}

	public void fillRectangle(int x, int y, int width, int height) {
		this.eps.fillRect(x, y, width, height);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		this.eps.drawRect(x, y, width, height);
	}

	public void drawLine(int start_x, int start_y, int end_x, int end_y) {
		this.eps.drawLine(start_x, start_y, end_x, end_y);
	}

	public void drawImage(Object image, int x, int y) {
		
	}

	public void drawText(String text, int x, int y) {
		this.eps.drawString(text, x, y + this.getCharHeight() - 3);
	}
	
	public void drawText(String text, int x, int y, float angle) {
		AffineTransform transform = new AffineTransform();
		transform.translate(x, y);
		transform.rotate(angle * Math.PI / 180);
		this.eps.setTransform(transform);
		this.eps.drawString(text, 0, this.getCharHeight() - 4);
		this.eps.setTransform(null);
	}

	public int getCharWidth(char c) {
		FontMetrics fm = this.eps.getFontMetrics();
		return fm.charWidth(c);
	}

	public int getCharHeight() {
		FontMetrics fm = this.eps.getFontMetrics();
		return fm.getHeight() - 1;
	}

	public int textExtent(String text) {
		int width = 0;
		for (int i = 0; i < text.length(); i++) {
			width += this.getCharWidth(text.charAt(i));
		}
		return width;
	}

	public void setFont(KesoFont font) {
		int type = Font.PLAIN;
		switch(font.getType()) {
			case KesoFont.BOLD:
				type = Font.BOLD;
				break;
			case KesoFont.ITALIC:
				type = Font.ITALIC;
				break;
			case KesoFont.PLAIN:
			default:
				type = Font.PLAIN;
				break;
		}
		Font epsfont = new Font(font.getFontname(), type, font.getSize());
		this.eps.setFont(epsfont);
	}

	public KesoFont getFont() {
		Font font = this.eps.getFont();
		
		int type = KesoFont.PLAIN;
		switch(font.getStyle()) {
			case Font.BOLD:
				type = KesoFont.BOLD;
				break;
			case Font.ITALIC:
				type = KesoFont.ITALIC;
				break;
			case Font.PLAIN:
			default:
				type = KesoFont.PLAIN;
				break;
		}
		return new KesoFont(font.getName(), font.getSize(), type);
	}

	public void setTextAntialias(boolean antialias) {
		// TODO Auto-generated method stub
		
	}

	public boolean getTextAntialias() {
		// TODO Auto-generated method stub
		return false;
	}

	public void drawText(String text, int x, int y, boolean transparent) {
		this.drawText(text, x, y);
	}

	public void drawOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void fillOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

}

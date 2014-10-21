package keso.editor.gui.graphics;

import java.awt.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;

/**
 * @author  Wilhelm Haas
 */
public class SwtKesoGraphics implements IKesoGraphics {
	KesoColor foreground;
	Color swtforderground;
	
	KesoColor background;
	Color swtbackground;
	
	GC gc;
	Rectangle clipping;
	
	Font swtfont;
	
	public SwtKesoGraphics(GC gc) {
		this.gc = gc;
	}

	public void setAlpha(int alpha) {
		this.gc.setAlpha(alpha);
	}

	public int getAlpha() {
		return this.gc.getAlpha();
	}

	public void setLineWidth(int width) {
		this.gc.setLineWidth(width);
	}

	public int getLineWidth() {
		return this.gc.getLineWidth();
	}

	/**
	 * @param background  the background to set
	 * @uml.property  name="background"
	 */
	public void setBackground(KesoColor color) {
		this.background = color;
		if (this.swtbackground != null) {
			if (!this.swtbackground.isDisposed()) {
				this.swtbackground.dispose();
			}
		}
		this.swtbackground = new Color(this.gc.getDevice(), color.red, color.green, color.blue);
		this.gc.setBackground(this.swtbackground);
		this.swtbackground.dispose();
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
		
		if (this.swtforderground != null) {
			if (!this.swtforderground.isDisposed()) {
				this.swtforderground.dispose();
			}
		}
		this.swtforderground = new Color(this.gc.getDevice(), color.red, color.green, color.blue);
		this.gc.setForeground(this.swtforderground);
		this.swtforderground.dispose();
	}

	/**
	 * @return  the foreground
	 * @uml.property  name="foreground"
	 */
	public KesoColor getForeground() {
		return this.foreground;
	}

	/**
	 * @param clipping  the clipping to set
	 * @uml.property  name="clipping"
	 */
	public void setClipping(Rectangle clipping) {
		//this.clipping = (Rectangle) clipping.clone();
		//this.gc.setClipping(clipping.x, clipping.y, clipping.width, clipping.height);
	}

	/**
	 * @return  the clipping
	 * @uml.property  name="clipping"
	 */
	public Rectangle getClipping() {
		org.eclipse.swt.graphics.Rectangle clip = this.gc.getClipping();
		this.clipping = new Rectangle(clip.x, clip.y, clip.width, clip.height);
		return this.clipping;
	}

	public void setClipping(int x, int y, int width, int height) {
		//this.setClipping(new Rectangle(x, y, width, height));
	}

	public void fillRoundRectangle(int x, int y, int width, int height, int rx, int ry) {
		this.gc.fillRoundRectangle(x, y, width, height, rx, ry);
	}

	public void drawRoundRectangle(int x, int y, int width, int height, int rx, int ry) {
		this.gc.drawRoundRectangle(x, y, width, height, rx, ry);
	}

	public void drawLine(int start_x, int start_y, int end_x, int end_y) {
		this.gc.drawLine(start_x, start_y, end_x, end_y);
	}

	public void drawText(String text, int x, int y) {
		this.gc.drawText(text, x, y);
	}

	public int getCharWidth(char c) {
		return this.gc.getCharWidth(c);
	}

	public int getCharHeight() {
		return this.gc.getFontMetrics().getHeight();
	}

	public int textExtent(String text) {
		
		int width = 0;
		for (int i = 0; i < text.length(); i++) {
			width += this.gc.getCharWidth(text.charAt(i));
			//width += this.gc.
		}
		
		//return this.gc.textExtent(text, SWT.DRAW_DELIMITER | SWT.DRAW_TAB | SWT.DRAW_MNEMONIC).x;
		
		return width;
	}

	public void setLineCap(int cap) {
		int swt = SWT.CAP_FLAT;
		switch(cap) {
			case CAP_ROUND:
				swt = SWT.CAP_ROUND;
				break;
			case CAP_FLAT:
				swt = SWT.CAP_FLAT;
				break;
			case CAP_SQUARE:
				swt = SWT.CAP_SQUARE;
				break;
			default:
		}
		this.gc.setLineCap(swt);
	}
	
	public int getLineCap() {
		int cap = CAP_FLAT;
		switch(cap) {
			case SWT.CAP_ROUND:
				cap = CAP_ROUND;
				break;
			case SWT.CAP_FLAT:
				cap = CAP_FLAT;
				break;
			case SWT.CAP_SQUARE:
				cap = CAP_SQUARE;
				break;
			default:
		}
		return cap;
	}

	public void setAntialias(boolean antialias) {
		if (antialias) {
			this.gc.setAntialias(SWT.ON);
		} else {
			this.gc.setAntialias(SWT.OFF);
		}
	}

	public boolean getAntialias() {
		return (this.gc.getAntialias() == SWT.ON);
	}

	public void drawImage(Object image, int x, int y) {
		Image img = (Image) image;
		this.gc.drawImage(img, x, y);
	}

	public void fillRectangle(int x, int y, int width, int height) {
		this.gc.fillRectangle(x, y, width, height);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		this.gc.drawRectangle(x, y, width, height);
	}

	public void drawText(String text, int x, int y, float angle) {
		this.gc.setTransform(null);
		Transform transform = new Transform(this.gc.getDevice());
		transform.translate(x, y);
		transform.rotate(angle);
		this.gc.setTransform(transform);
		this.gc.drawText(text, 0, 0, SWT.DRAW_TRANSPARENT);
		this.gc.setTransform(null);
		transform.dispose();
	}

	public void setFont(KesoFont font) {
		int type;
		switch(font.getType()) {
			case KesoFont.BOLD:
				type = SWT.BOLD;
				break;
			case KesoFont.ITALIC:
				type = SWT.ITALIC;
				break;
			case KesoFont.PLAIN:
			default:
				type = SWT.NORMAL;
				break;
		}
		//FontData fontdata = new FontData(font.getFontname(), font.getSize(), type);
		FontData fontdata  = this.gc.getFont().getFontData()[0];
		
		if (this.swtfont != null) {
			if (!this.swtfont.isDisposed()) {
				this.swtfont.dispose();
			}
		}
		this.swtfont = new Font(this.gc.getDevice(), fontdata.getName(), font.getSize(), type);
		this.gc.setFont(this.swtfont);
	}

	public KesoFont getFont() {
		FontData fontdata = this.gc.getFont().getFontData()[0];
		int type = KesoFont.PLAIN;
		switch(fontdata.getStyle()) {
			case SWT.BOLD:
				type = KesoFont.BOLD;
				break;
			case SWT.ITALIC:
				type = KesoFont.ITALIC;
				break;
			case SWT.NORMAL:
			default:
				type = KesoFont.PLAIN;
				break;
		}
		
		return new KesoFont(fontdata.getName(), fontdata.getHeight(), type);
	}

	public void setTextAntialias(boolean antialias) {
		if (antialias) {
			this.gc.setTextAntialias(SWT.ON);
		} else {
			this.gc.setTextAntialias(SWT.OFF);
		}
	}

	public boolean getTextAntialias() {
		return this.gc.getTextAntialias() == SWT.ON;
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
		
		if (this.swtfont != null) {
			if (!this.swtfont.isDisposed()) {
				this.swtfont.dispose();
			}
		}
		if (this.swtbackground != null) {
			if (!this.swtbackground.isDisposed()) {
				this.swtbackground.dispose();
			}
		}
		if (this.swtforderground != null) {
			if (!this.swtforderground.isDisposed()) {
				this.swtforderground.dispose();
			}
		}
	}

	public void drawText(String text, int x, int y, boolean transparent) {
		this.gc.drawText(text, x, y, transparent);
	}

	public void drawOval(int x, int y, int width, int height) {
		this.gc.drawOval(x, y, width, height);
	}

	public void fillOval(int x, int y, int width, int height) {
		this.gc.fillOval(x, y, width, height);
	}
}

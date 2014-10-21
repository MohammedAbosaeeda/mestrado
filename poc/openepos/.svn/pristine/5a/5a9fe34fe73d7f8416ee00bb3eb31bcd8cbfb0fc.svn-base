package keso.editor.gui.graphics;

import java.awt.Rectangle;

/**
 * @author  Wilhelm Haas
 */
public interface IKesoGraphics {
	public static final int CAP_ROUND = 1;
	public static final int CAP_FLAT = 2;
	public static final int CAP_SQUARE = 3;
	
	/**
	 * @param antialias
	 * @uml.property  name="antialias"
	 */
	public void setAntialias(boolean antialias);
	/**
	 * @return
	 * @uml.property  name="antialias"
	 */
	public boolean getAntialias();
	/**
	 * @param antialias
	 * @uml.property  name="textAntialias"
	 */
	public void setTextAntialias(boolean antialias);
	/**
	 * @return
	 * @uml.property  name="textAntialias"
	 */
	public boolean getTextAntialias();
	
	/**
	 * @param alpha
	 * @uml.property  name="alpha"
	 */
	public void setAlpha(int alpha);
	/**
	 * @return
	 * @uml.property  name="alpha"
	 */
	public int getAlpha();
	
	/**
	 * @param width
	 * @uml.property  name="lineWidth"
	 */
	public void setLineWidth(int width);
	/**
	 * @return
	 * @uml.property  name="lineWidth"
	 */
	public int getLineWidth();
	/**
	 * @param cap
	 * @uml.property  name="lineCap"
	 */
	public void setLineCap(int cap);
	/**
	 * @return
	 * @uml.property  name="lineCap"
	 */
	public int getLineCap();
	
	/**
	 * @param color
	 * @uml.property  name="background"
	 */
	public void setBackground(KesoColor color);
	public KesoColor getBackground();
	/**
	 * @param color
	 * @uml.property  name="foreground"
	 */
	public void setForeground(KesoColor color);
	public KesoColor getForeground();
	
	/**
	 * @param clipping
	 * @uml.property  name="clipping"
	 */
	public void setClipping(Rectangle clipping);
	/**
	 * @return
	 * @uml.property  name="clipping"
	 */
	public Rectangle getClipping();
	public void setClipping(int x, int y, int width, int height);
	
	public void fillRoundRectangle(int x, int y, int width, int height, int rx, int ry);
	public void drawRoundRectangle(int x, int y, int width, int height, int rx, int ry);
	public void fillRectangle(int x, int y, int width, int height);
	public void drawRectangle(int x, int y, int width, int height);
	public void drawLine(int start_x, int start_y, int end_x, int end_y);
	public void drawImage(Object image, int x, int y);
	
	public void drawText(String text, int x, int y);
	public void drawText(String text, int x, int y, boolean transparent);
	
	public void drawOval(int x, int y, int width, int height);
	public void fillOval(int x, int y, int width, int height);
	
	public int getCharWidth(char c);
	public int getCharHeight();
	public int textExtent(String text);
	
	public void drawText(String text, int x, int y, float angle);
	
	/**
	 * @param font
	 * @uml.property  name="font"
	 */
	public void setFont(KesoFont font);
	public KesoFont getFont();
	
}

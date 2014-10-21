package keso.editor.gui.shape;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author  Wilhelm Haas
 */
public class KesoTextShape {
	
	KesoLine line;
	Point norm = new Point(0, 0);
	String text;
	Point localPosition = new Point(0, 0);
	Point globalPosition = new Point(0, 0);
	int width;
	int height;
	double angle;
	
	public KesoTextShape(KesoLine line, String text, int width, int height) {
		this.line = line;
		this.text = text;
		this.height = height;
		this.width = width;
		this.compute();
	}
	
	public void compute() {
		Point start = this.line.getStartPoint();
		Point end = this.line.getEndPoint();
		
		Point vector = this.line.getVector();
		
		if (vector.y == 0) {
			this.angle = 0;
		} else {	
			if (vector.x != 0) {
				this.angle = Math.atan2(vector.y, vector.x);
				if (start.x > end.x) {
					angle += Math.PI;
				}
			} else {
				this.angle = -1 * (Math.PI / 2);
			}
		}
		
		this.localPosition.x = (int) (this.line.getLength()  / 2);
		this.localPosition.y = 3;
		
		this.computeGlobalPosition();
	}

	private void computeGlobalPosition() {
		Point start = this.line.getStartPoint();
		Point end = this.line.getEndPoint();
		double angle = this.angle;
		Point local = this.getTransformedLocalPosition();
		
		if (start.y != end.y) {
			if (start.x != end.x) {
				if (start.x > end.x) {
					angle = this.angle - Math.PI;
				}
			} else {
				angle = -1 * this.angle;
			}
		}
		
		
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		this.globalPosition.x = (int) (cos *  local.x - sin * local.y + start.x);
		this.globalPosition.y = (int) (sin * local.x + cos * local.y + start.y);
	}
	
	/**
	 * @param localPosition  the localPosition to set
	 * @uml.property  name="localPosition"
	 */
	public void setLocalPosition(Point position) {
		this.localPosition = position;
		this.computeGlobalPosition();
	}
	
	/**
	 * @return  the angle
	 * @uml.property  name="angle"
	 */
	public double getAngle() {
		return this.angle * 180 / Math.PI;
	}
	
	/**
	 * @return  the line
	 * @uml.property  name="line"
	 */
	public KesoLine getLine() {
		return this.line;
	}
	
	public Point getTransformedLocalPosition() {
		Point start = this.line.getStartPoint();
		Point end = this.line.getEndPoint();
		Point local = new Point(this.localPosition);
		if (start.x < end.x && start.y < end.y) {
			local.x -= this.width / 2;
		} else {
			if (this.getAngle() == 0) {
				local.x -= this.width / 2;
			} else {
				local.x += this.width / 2;
				local.y *= -1;
			}
		}
		
		return local;
	}
	
	
	/**
	 * @return  the localPosition
	 * @uml.property  name="localPosition"
	 */
	public Point getLocalPosition() {
		return this.localPosition;
	}
	
	public Point getLocation() {
		
		return this.globalPosition;
	}
	
	/**
	 * @return  the norm
	 * @uml.property  name="norm"
	 */
	public Point getNorm() {
		return this.norm;
	}
	
	/**
	 * @return  the height
	 * @uml.property  name="height"
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * @return  the width
	 * @uml.property  name="width"
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * @return  the text
	 * @uml.property  name="text"
	 */
	public String getText() {
		return this.text;
	}
	
	public void move(int x, int y) {
		this.localPosition.x += x;
		this.localPosition.y += y;
		this.computeGlobalPosition();
	}
	
	public boolean intersects(KesoTextShape textshape) {
		Rectangle my = this.getLocalBounds();
		Rectangle he = textshape.getLocalBounds();		
		return my.intersects(he);
	}
	
	public Rectangle getLocalBounds() {
		return new Rectangle(this.localPosition.x, this.localPosition.y, this.width, this.height);
	}
	
	public boolean equalLine(KesoTextShape textshape) {
		return this.line.equals(textshape.getLine());
	}
	
	public boolean equal(KesoTextShape textshape) {
		return textshape.getText().equals(this.getText()) && this.equalLine(textshape); 
	}

	/**
	 * @param text  the text to set
	 * @uml.property  name="text"
	 */
	public void setText(String text) {
		this.text = text;
	}

}

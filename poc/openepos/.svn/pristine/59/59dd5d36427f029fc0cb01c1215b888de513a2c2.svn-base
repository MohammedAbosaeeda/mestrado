package keso.editor.gui.shape;

import java.awt.Point;
import keso.editor.gui.graphics.KesoColor;

/**
 * @author  Wilhelm Haas
 */
public class KesoLine {

	Point start_point = new Point(0, 0);
	Point end_point = new Point(0, 0);
	KesoColor color = new KesoColor(0, 0, 0);
	
	public KesoLine(int start_x, int start_y, int end_x, int end_y) {
		this.setLine(start_x, start_y, end_x, end_y);
	}
	
	public KesoLine(int start_x, int start_y, int end_x, int end_y, KesoColor color) {
		this.setLine(start_x, start_y, end_x, end_y);
		this.setColor(color);
	}
	
	public KesoLine(Point start_point, Point end_point) {
		this(start_point.x, start_point.y, end_point.x, end_point.y);
	}	
	
	public KesoLine(Point start_point, Point end_point, KesoColor color) {
		this(start_point.x, start_point.y, end_point.x, end_point.y, color);
	}	
	
	
	public void setLine(int start_x, int start_y, int end_x, int end_y) {
		this.start_point.x = start_x;
		this.start_point.y = start_y;
		this.end_point.x = end_x;
		this.end_point.y = end_y;
		
		if (this.start_point.y > this.end_point.y) {
			Point tmp = this.start_point;
			this.start_point = this.end_point;
			this.end_point = tmp;
		} else if (this.start_point.y == this.end_point.y) {
			if (this.start_point.x > this.end_point.x) {
				int tmp = this.start_point.x;
				this.start_point.x = this.end_point.x;
				this.end_point.x = tmp;
			}
		}
	}
	
	/**
	 * @param color  the color to set
	 * @uml.property  name="color"
	 */
	public void setColor(KesoColor color) {
		this.color = color;
	}
	
	public boolean equals(KesoLine line) {
		Point start_point = line.start_point;
		Point end_point = line.end_point;
		
		return (start_point.equals(this.start_point) && end_point.equals(this.end_point));
	}
	
	public double getLength() {
		Point vector = this.getVector();
		return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
	}
	
	public Point getVector() {
		return new Point(this.end_point.x - this.start_point.x, this.end_point.y - this.start_point.y);
	}

	public Point getStartPoint() {
		return this.start_point;
	}
	
	public Point getEndPoint() {
		return this.end_point;
	}

	/**
	 * @return  the color
	 * @uml.property  name="color"
	 */
	public KesoColor getColor() {
		return this.color;
	}
}

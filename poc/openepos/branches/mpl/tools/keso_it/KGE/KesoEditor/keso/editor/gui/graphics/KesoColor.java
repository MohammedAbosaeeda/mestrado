package keso.editor.gui.graphics;

public class KesoColor {
	public int red;
	public int green;
	public int blue;
	
	public KesoColor() {
		this.setRGB(0, 0, 0);
	}
	
	public KesoColor(int red, int green, int blue) {
		this.setRGB(red, green, blue);
	}
	
	public void setRGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
}

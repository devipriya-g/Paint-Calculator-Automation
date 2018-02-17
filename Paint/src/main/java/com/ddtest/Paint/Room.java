package com.ddtest.Paint;

/**
 * Model to hold room information - length, width and height
 * 
 * @author devi
 *
 */
public class Room {
	private String length;
	private String width;
	private String height;

	public Room(String length, String width, String height) {
		super();
		this.length = length;
		this.width = width;
		this.height = height;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "Room [length=" + length + ", width=" + width + ", height=" + height + "]";
	}

}

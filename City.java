/*
 * Author: Chris Garrett
 * Instructor: Dr. Fox
 * Class: CSC 425
 * 
 * Class to store a city object, containing the name of the city, and the coordinates of the office
 */
public class City {

	private double xCoord;
	private double yCoord;
	private String name;
	
	public City(String n, double x, double y) {
		
		this.xCoord = x;
		this.yCoord = y;
		this.name = n;
	}
	
	public double getXCoord() {
		
		return this.xCoord;
	}
	
	public double getYCoord() {
		
		return this.yCoord;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	//method to determine the distance between one city and another
	public double distanceTo(City city) {
		
		double xDist = Math.abs(getXCoord() - city.getXCoord());
		//if x distance is larger than 180, go around the globe the other way
		if (xDist > 180)
			xDist = xDist - 180;
		double yDist = Math.abs(getYCoord() - city.getYCoord());
		return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}
	
	public String toString() {
		
		return getName() + ": " + getXCoord() + " " + getYCoord();
	}
}

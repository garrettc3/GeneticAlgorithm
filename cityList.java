/*
 * Author: Chris Garrett
 * Instructor: Dr. Fox
 * Class: CSC 425
 * 
 * Singleton class containing an array list of all cities that the company has offices in
 */

import java.util.ArrayList;
import java.util.Collections;

public class cityList {

	private static ArrayList<City> cities = new ArrayList<City>();
	
	public static void addCity(City city) {
		
		cities.add(city);
	}
	
	public static City getCity(int i) {
		
		return cities.get(i);
	}
	
	public static ArrayList<City> getCities() {
		
		return cities;
	}
	
	public static int getNumCities() {
		
		return cities.size();
	}
	
	//method to randomize the ordering of the cities
	public static void randomizeRoute() {
		
		Collections.shuffle(cities);
	}
}

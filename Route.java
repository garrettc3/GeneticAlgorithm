/*
 * Author: Chris Garrett
 * Instructor: Dr. Fox
 * Class: CSC 425
 * 
 * Class holding routes that the salesperson may travel on
 */

import java.util.ArrayList;

public class Route {

	private ArrayList<City> route;
	private double fitness = 0;
	private double totalDistance = 0;
	
	//constructor method for route class
	public Route(ArrayList<City> r) {
		
		//if the list passed is not empty, set distance and fitness
		this.route = r;
		setTotalDistance();
		setFitness();
	}
	
	public City getCityAtIndex(int i) {
		
		return route.get(i);
	}
	
	public void setCityAtIndex(City c, int i) {
		
		route.set(i, c);
		//reset total distance and fitness
		setTotalDistance();
		setFitness();
	}
	
	//method for determining the total distance of the route
	public void setTotalDistance() {
		
		//determine if any null values exist
		boolean noNulls = true;
		for (int i = 0; i < route.size(); i++) {
			if (route.get(i) == null) {
				noNulls = false;
				break;
			}
		}
		//if the route is full, determine distance
		if (noNulls) {
			double dis = 0;
			for (int i = 0; i < route.size(); i++) {
				City fromCity = getCityAtIndex(i);
				//when the end of the route is reached, add distance back to first location
				City toCity = getCityAtIndex((i + 1) % route.size());
				dis += fromCity.distanceTo(toCity);
			}
			this.totalDistance = dis;
		}
		//if not, set distance to 0
		else 
			this.totalDistance = 0;
	}
	
	
	public double getTotalDistance() {
		
		return this.totalDistance;
	}
	
	public void setFitness() {
		
		this.fitness = 1/getTotalDistance();
	}
	
	public double getFitness() {
		
		return this.fitness;
	}
	
	public int getRouteLength() {
		
		return route.size();
	}
	
	public boolean containsCity(City c) {
		
		return route.contains(c);
	}

	
	public String toString() {
		
		String routeString = "|";
		for (int i = 0; i < route.size(); i++) {
			routeString += getCityAtIndex(i).getName() + "|";
		}
		return routeString;
	}
}

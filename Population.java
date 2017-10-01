/*
 * Author: Chris Garrett
 * Instructor: Dr. Fox
 * Class: CSC 425
 * 
 * Class to hold a population of routes
 */


public class Population {

	private Route[] routes;
	
	//constructor
	public Population(int popSize, boolean isOriginalPop) {
		
		//since we know the max size, use array for easier access
		this.routes = new Route[popSize];
		//if initializing the original population, add randomized routes
		if (isOriginalPop) {
			for (int i = 0; i < popSize; i++) {
				cityList.randomizeRoute();
				Route r = new Route(cityList.getCities());
				addRoute(i, r);
			}
		}
	}
	
	public void addRoute(int i, Route r) {
		
		this.routes[i] = r;
	}
	
	public Route getRouteAtIndex(int i) {
		
		return this.routes[i];
	}
	
	//method for finding the fittest route in population
	public Route getFittestRoute() {
		
		Route fittest = this.routes[0];
		for (int i = 1; i < this.routes.length; i++) {
			if (fittest.getFitness() <= getRouteAtIndex(i).getFitness())
				fittest = getRouteAtIndex(i);
		}
		return fittest;
	}
	
	public int getPopulationSize() {
		
		return routes.length;
	}
}

/*
 * Author: Chris Garrett
 * Instructor: Dr. Fox
 * Class: CSC 425
 * 
 * Driver class to control the genetic algorithm to optimize the traveling salesman problem. The program begins by initializing a singleton
 * object containing an Array List of all of the locations of the imaginary company's offices. The program then performs a genetic algorithm
 * consisting of crossovers and swap mutations, and uses this algorithm to evolve the route a salesperson should take in order to minimize the 
 * distance traveled. 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Driver {

	public static final int numCandidates = 10;
	public static final int numEvolutions = 100;
	public static final int populationSize = 500;
	public static final String fileName = "citiesinterNational.csv";
	public static final double mutationRate = .030;
	
	//main function: initialize list of cities and run the genetic algorithm
	public static void main(String [] args) {
		
		initializeCityList();
		//create a new population with 500 randomized routes
		Population pop = new Population(populationSize, true);
		DecimalFormat d2 = new DecimalFormat(".##");
		double initDist = pop.getFittestRoute().getTotalDistance();
		//display initial fittest formatted total distance and route
		System.out.println("Initial total distance: " + d2.format(initDist));
		System.out.println("Intitial route: " + pop.getFittestRoute().toString());
		//evolve the population 100 times
		for (int i = 0; i < numEvolutions; i++) {
			pop = evolve(pop);
		}
		//get the fittest route from final population
		double endDist = pop.getFittestRoute().getTotalDistance();
		//display fittest distance and route
		System.out.println("Final Distance: " + d2.format(endDist));
		System.out.println("Final route: " + pop.getFittestRoute().toString());
		double reductionPercent = 100 - (100 * (endDist / initDist));
		//display percentage of reduction
		System.out.println("Amount of reduction: " + d2.format(reductionPercent) + "%");
	}
	
	//function to initialize the singleton list of cities containing all of the company's offices
	public static void initializeCityList() {
		
		String csvFile = fileName;
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		//read csv file line by line
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				//parse city information
				String[] city = line.split(csvSplitBy);
				String name = city[0];
				double xCoord = Double.parseDouble(city[1]);
				double yCoord = Double.parseDouble(city[2]);
				City c = new City(name, xCoord, yCoord);
				//add city to list
				cityList.addCity(c);
			}
		}
		//handle exceptions
		catch(FileNotFoundException e) {
			System.out.println("Error retrieving file");
		}
		catch (IOException e) {
			System.out.println("IO Error");
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch(IOException e) {
					System.out.println("IOError");
				}
			}
		}
	}

	//function for evolution of the routes
	public static Population evolve(Population pop) {

		//create new empty population
		Population newPop = new Population(pop.getPopulationSize(), false);
		//perform crossover operations, add child routes to the new population
		for (int i = 0; i < newPop.getPopulationSize(); i++) {
			Route parent1 = selectCandidate(pop);
			Route parent2 = selectCandidate(pop);
			Route child = crossover(parent1, parent2);
			newPop.addRoute(i, child);
		}
		//Occasionally perform swap mutations to keep gene pool fresh
		for (int i = 0; i < newPop.getPopulationSize(); i++) {
			if (Math.random() < mutationRate) {
				swapMutate(newPop.getRouteAtIndex(i));
			}
		}
		return newPop;
	}

	//function for crossover breeding
	public static Route crossover(Route r1, Route r2) {
		
		//create child route filled with null values
		ArrayList<City> childRoute = new ArrayList<City>();
		for (int i = 0; i < cityList.getNumCities(); i++) {
			childRoute.add(null);
		}
		Route child = new Route(childRoute);
		//choose a random start and end point within the route
		int start = (int)(Math.random() * r1.getRouteLength());
		int end = (int)(Math.random() * r1.getRouteLength());
		//keep this random sub section of the route for the child, add the unused destinations from the other parent to the
		//remaining spots in the child route
		for (int i = 0; i < child.getRouteLength(); i++) {
			if (start < end && i > start && i < end)
				child.setCityAtIndex(r1.getCityAtIndex(i), i);
			else if (start > end)
				if (!(i < start && i > end))
					child.setCityAtIndex(r1.getCityAtIndex(i), i);
		}
		for (int i = 0; i < r2.getRouteLength(); i++) {
			if (!child.containsCity(r2.getCityAtIndex(i))) {
				for (int j = 0; i < child.getRouteLength(); j++) {
					if (child.getCityAtIndex(j) == null) {
						child.setCityAtIndex(r2.getCityAtIndex(i), j);
						break;
					}
				}
			}
		}
		return child;
	}

	//function to perform swap mutation
	public static void swapMutate(Route r) {
		
		for (int i = 0; i < r.getRouteLength(); i++) {
			//choose a random destination index
			int rand = (int)(Math.random() * r.getRouteLength());
			//swap random destination with current one
			City c1 = r.getCityAtIndex(i);
			City c2 = r.getCityAtIndex(rand);
			r.setCityAtIndex(c1, rand);
			r.setCityAtIndex(c2, i);
		}
	}
	
	//function for selection of candidate parents for crossover breeding
	public static Route selectCandidate(Population pop) {
		
		//create empty population of small number of routes
		Population randomSelect = new Population(numCandidates, false);
		//add random routes from current population
		for (int i = 0; i < randomSelect.getPopulationSize(); i++) {
			int rand = (int)(Math.random() * pop.getPopulationSize());
			randomSelect.addRoute(i, pop.getRouteAtIndex(rand));
		}
		//select the fittest route of the 5 random ones
		return randomSelect.getFittestRoute();
	}
	
}

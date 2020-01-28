package carinventorysystem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class used to create and modify the inventory of cars.
 *
 * @author Jordon
 */
public class CreateInventory {

    private ArrayList<Car> inventory = new ArrayList<Car>();

    public CreateInventory() {

        // Create a File instance
        try {
            java.io.File file = new java.io.File("cars.txt");

            // Create a Scanner for the file
            Scanner input = new Scanner(file);

            // Read data from a file
            while (input.hasNext()) {
                String year = input.next();
                String make = input.next();
                String model = input.next();
                String type = input.next();
                double price = input.nextDouble();
                inventory.add(new Car(year, make, model, type, price));
            }
            input.close();
        } catch (FileNotFoundException e) {
           
        }catch(Exception ex){
           
        }
        

        // Read data from a file
        
    }

    public ArrayList<Car> getInventory() {
        return inventory;
    }

    public void addCar(Car car) {
        inventory.add(car);
    }

    public void removeCar(Car car) {
        inventory.remove(car);
    }

    public ArrayList<Car> sortByYear(int year) {
        ArrayList<Car> sorted = new ArrayList<Car>();
        for (Car car : inventory) {
            if (car.getYear().equals(year)) {
                sorted.add(car);
            }
        }
        return sorted;
    }

    public ArrayList<Car> sortByType(String type) {
        ArrayList<Car> sorted = new ArrayList<Car>();
        for (Car car : inventory) {
            if (car.getBodyType().equals(type)) {
                sorted.add(car);
            }
        }
        return sorted;
    }

    public ArrayList<Car> searchByMake(String phrase) {
        ArrayList<Car> sorted = new ArrayList<Car>();
        phrase = phrase.toLowerCase();
        for (Car car : inventory) {
            String make = car.getMake().toLowerCase();
            if (make.contains(phrase)) {
                sorted.add(car);
            }
        }
        return sorted;
    }

    public ArrayList<Car> searchByModel(String phrase) {
        ArrayList<Car> sorted = new ArrayList<Car>();
        phrase = phrase.toLowerCase();
        for (Car car : inventory) {
            String model = car.getModel().toLowerCase();
            if (model.contains(phrase)) {
                sorted.add(car);
            }
        }
        return sorted;
    }

    public ArrayList<Car> searchByYear(String phrase) {
        ArrayList<Car> sorted = new ArrayList<Car>();
        phrase = phrase.toLowerCase();
        for (Car car : inventory) {
            String year = car.getYear().toLowerCase();
            if (year.contains(phrase)) {
                sorted.add(car);
            }
        }
        return sorted;
    }

}
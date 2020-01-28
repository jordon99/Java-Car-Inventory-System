package carinventorysystem;
/**
 * A class used to create car objects with only getter methods. There are no setters
 * because the values are not meant to be changed after creation.
 * @author Jordon
 */
public class Car {
    
    private String year;
    private String make;
    private String model;    
    private String bodyType;
    private double cost;
    
    public Car(String year, String make, String model, String bodyType, double cost) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.bodyType = bodyType;
        this.cost = cost;
    }
    
    @Override
    public String toString() {
        String car = "%s %s %s";
        return String.format(car, year, make, model);
    }
    
    public String getInfo() {
        String carInfo = "Year:\t\t%s\nMake:\t%s\nModel:\t%s\nType:\t%s\nCost:\t\t$%.2f/day%n";
        return String.format(carInfo, year, make, model, bodyType, cost);
    }

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public double getCost() {
        return cost;
    }
}

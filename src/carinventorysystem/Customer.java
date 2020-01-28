package carinventorysystem;

/**
 * Class used to store Customer objects and all the customer informations.
 * @author Jordon
 */
public class Customer {
    
    private String name;
    private int age;
    private String address;
    private String license;
    
    public Customer(String name, int age, String address, String license) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getLicense() {
        return license;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLicense(String license) {
        this.license = license;
    }
    
    @Override
    public String toString() {
        String info = "Name:\t%s\nAge:\t%d\nAddress:\t%s\nLicense#:\t%s\n";
        return String.format(info, name, age, address, license);
    }
}

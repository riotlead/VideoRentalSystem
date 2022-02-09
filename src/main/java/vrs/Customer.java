package vrs;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;

    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);

    }

    // Primitive Obession - Magic Number 남용
    // Long Method
    public String getReport() {
        String result = "Customer Report for " + getName() + "\n";

        double totalCharge = 0;
        int totalPoint = 0;

        for (Rental each : rentals) {
            totalCharge += each.calcEachCharge();
            totalPoint += each.calcEachPoint();
            result += "\t" + each.getReport();
        }

        result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

        if ( totalPoint >= 10 ) {
            SimpleLogger.log("Congrat! You earned one free coupon");
        }
        if ( totalPoint >= 30 ) {
            SimpleLogger.log("Congrat! You earned two free coupon");
        }
        return result ;
    }

    
}

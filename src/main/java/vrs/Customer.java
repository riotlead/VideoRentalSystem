package vrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    public static final int A_DAY_IN_MILLIS = (1000 * 60 * 60 * 24);
    private Rental.Status status;
    private String name;

    private List<Rental> rentals = new ArrayList<Rental>();

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
    // SRP 위반
    public String getReport() {
        String result = "Customer Report for " + getName() + "\n";

        List<Rental> rentals = getRentals();

        double totalCharge = 0;
        int totalPoint = 0;

        for (Rental each : rentals) {
            double eachCharge = 0;
            int eachPoint = 0 ;
            int daysRented = 0;

            if (each.getStatus() == status.RETURNED) { // returned Video
                long diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
                daysRented = (int) (diff / A_DAY_IN_MILLIS) + 1;
            } else { // not yet returned
                long diff = new Date().getTime() - each.getRentDate().getTime();
                daysRented = (int) (diff / A_DAY_IN_MILLIS) + 1;
            }

            switch (each.getVideo().getPriceCode()) {
                case Video.REGULAR:
                    eachCharge += 2;
                    if (daysRented > 2)
                        eachCharge += (daysRented - 2) * 1.5;
                    break;
                case Video.NEW_RELEASE:
                    eachCharge = daysRented * 3;
                    break;
            }

            eachPoint++;

            if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
                eachPoint++;

            if ( daysRented > each.getDaysRentedLimit() )
                eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;

            result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
                    + "\tPoint: " + eachPoint + "\n";

            totalCharge += eachCharge;

            totalPoint += eachPoint ;
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

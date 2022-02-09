package vrs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
    private static final Scanner scanner = new Scanner(System.in) ;
    VideoRentalSystem vrs = new VideoRentalSystem();

    public static void main(String[] args) {
        VRUI ui = new VRUI() ;

        boolean quit = false ;
        while ( ! quit ) {
            int command = ui.showCommand() ;
            switch (command) {
                case 0 -> quit = true;
                case 1 -> ui.listCustomers();
                case 2 -> ui.listVideos();
                case 3 -> ui.registerCustomer();
                case 4 -> ui.registerVideo();
                case 5 -> ui.rentVideo();
                case 6 -> ui.returnVideo();
                case 7 -> ui.getCustomerReport();
                case 8 -> ui.clearRentals();
                case -1 -> ui.init();
                default -> {
                }
            }
        }
        SimpleLogger.log("Bye");
    }

    private Customer getCustomer() {
        SimpleLogger.log("Enter customer name: ") ;
        String customerName = scanner.next() ;

        return vrs.getCustomer(customerName);
    }

    public void clearRentals() {
        Customer customer = getCustomer();

        if (customer != null) {
            SimpleLogger.log("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size()) ;
            for ( Rental rental: customer.getRentals() ) {
                SimpleLogger.logNoNewLine("\tTitle: " + rental.getVideo().getTitle() + " ") ;
                SimpleLogger.logNoNewLine("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }

            vrs.clearRentals(customer);
        }
    }

    public void returnVideo() {
        Customer customer = getCustomer();

        if (customer == null)
            return;

        SimpleLogger.log("Enter video title to return: ") ;
        String videoTitle = scanner.next() ;

        vrs.returnVideo(customer, videoTitle);
    }

    private void init() {
        vrs.init();
    }

    public void listVideos() {
        SimpleLogger.log("List of videos");

        for ( Video video: vrs.getVideos() ) {
            SimpleLogger.log("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
        }

        SimpleLogger.log("End of list");
    }

    public void listCustomers() {
        SimpleLogger.log("List of customers");
        for ( Customer customer: vrs.getCustomers() ) {
            SimpleLogger.log("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size()) ;
            for ( Rental rental: customer.getRentals() ) {
                SimpleLogger.logNoNewLine("\tTitle: " + rental.getVideo().getTitle() + " ") ;
                SimpleLogger.logNoNewLine("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }
        }
        SimpleLogger.log("End of list");
    }

    public void getCustomerReport() {
        Customer customer = getCustomer();
        if (customer != null) {
            String result = customer.getReport() ;
            SimpleLogger.log(result);
        }
    }

    public void rentVideo() {
        Customer customer = getCustomer();
        if (customer == null) return;

        SimpleLogger.log("Enter video title to rent: ") ;
        String videoTitle = scanner.next() ;

        vrs.rentVideo(customer, videoTitle);
    }

    public void registerCustomer() {
        SimpleLogger.log("Enter customer name: ");
        String name = scanner.next();
        vrs.registerCustomer(name);
    }

    public void registerVideo() {
        SimpleLogger.log("Enter video title to register: ") ;
        String title = scanner.next() ;

        SimpleLogger.log("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
        int videoType = scanner.nextInt();

        SimpleLogger.log("Enter price code( 1 for Regular, 2 for New Release ):") ;
        int priceCode = scanner.nextInt();

        vrs.registerVideo(title, videoType, priceCode);
    }

    public int showCommand() {
        SimpleLogger.log("\nSelect a command !");
        SimpleLogger.log("\t 0. Quit");
        SimpleLogger.log("\t 1. List customers");
        SimpleLogger.log("\t 2. List videos");
        SimpleLogger.log("\t 3. Register customer");
        SimpleLogger.log("\t 4. Register video");
        SimpleLogger.log("\t 5. Rent video");
        SimpleLogger.log("\t 6. Return video");
        SimpleLogger.log("\t 7. Show customer report");
        SimpleLogger.log("\t 8. Show customer and clear rentals");

        return scanner.nextInt();
    }
}

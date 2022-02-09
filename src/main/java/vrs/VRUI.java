package vrs;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
    private static final Scanner scanner = new Scanner(System.in) ;

    private final List<Customer> customers = new ArrayList<>() ;

    private final List<Video> videos = new ArrayList<>() ;

    public static void main(String[] args) {
        VRUI ui = new VRUI() ;

        boolean quit = false ;
        while ( ! quit ) {
            int command = ui.showCommand() ;
            switch (command) {
                case 0 -> quit = true;
                case 1 -> ui.listCustomers();
                case 2 -> ui.listVideos();
                case 3 -> ui.register("customer");
                case 4 -> ui.register("video");
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

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) {
            SimpleLogger.log("No customer found") ;
        } else {
            return foundCustomer;
        }

        return null;
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

            List<Rental> rentals = new ArrayList<>() ;
            customer.setRentals(rentals);
        }
    }

    public void returnVideo() {
        Customer customer = getCustomer();

        if (customer == null)
            return;

        SimpleLogger.log("Enter video title to return: ") ;
        String videoTitle = scanner.next() ;

        List<Rental> customerRentals = customer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    private void init() {
        Customer james = new Customer("James") ;
        Customer brown = new Customer("Brown") ;
        customers.add(james) ;
        customers.add(brown) ;

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
        videos.add(v1) ;
        videos.add(v2) ;

        Rental r1 = new Rental(v1) ;
        Rental r2 = new Rental(v2) ;

        james.addRental(r1) ;
        james.addRental(r2) ;
    }

    public void listVideos() {
        SimpleLogger.log("List of videos");

        for ( Video video: videos ) {
            SimpleLogger.log("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
        }
        SimpleLogger.log("End of list");
    }

    public void listCustomers() {
        SimpleLogger.log("List of customers");
        for ( Customer customer: customers ) {
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

        Video foundVideo = null ;
        for ( Video video: videos ) {
            if ( video.getTitle().equals(videoTitle) && !video.isRented()) {
                foundVideo = video ;
                break ;
            }
        }

        if ( foundVideo == null ) return ;

        Rental rental = new Rental(foundVideo) ;
        foundVideo.setRented(true);

        List<Rental> customerRentals = customer.getRentals() ;
        customerRentals.add(rental);
        customer.setRentals(customerRentals);
    }

    public void register(String object) {
        if ( object.equals("customer") ) {
            SimpleLogger.log("Enter customer name: ") ;
            String name = scanner.next();
            Customer customer = new Customer(name) ;
            customers.add(customer) ;
        }
        else {
            SimpleLogger.log("Enter video title to register: ") ;
            String title = scanner.next() ;

            SimpleLogger.log("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
            int videoType = scanner.nextInt();

            SimpleLogger.log("Enter price code( 1 for Regular, 2 for New Release ):") ;
            int priceCode = scanner.nextInt();

            Date registeredDate = new Date();
            Video video = new Video(title, videoType, priceCode, registeredDate) ;
            videos.add(video) ;
        }
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

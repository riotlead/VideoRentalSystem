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
        SimpleLogger.Log("Bye");
    }

    public void clearRentals() {
        SimpleLogger.Log("Enter customer name: ") ;
        String customerName = scanner.next() ;

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) {
            SimpleLogger.Log("No customer found") ;
        } else {
            SimpleLogger.Log("Name: " + foundCustomer.getName() +
                    "\tRentals: " + foundCustomer.getRentals().size()) ;
            for ( Rental rental: foundCustomer.getRentals() ) {
                SimpleLogger.LogL("\tTitle: " + rental.getVideo().getTitle() + " ") ;
                SimpleLogger.LogL("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }

            List<Rental> rentals = new ArrayList<>() ;
            foundCustomer.setRentals(rentals);
        }
    }

    public void returnVideo() {
        SimpleLogger.Log("Enter customer name: ") ;
        String customerName = scanner.next() ;

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }
        if ( foundCustomer == null ) return ;

        SimpleLogger.Log("Enter video title to return: ") ;
        String videoTitle = scanner.next() ;

        List<Rental> customerRentals = foundCustomer.getRentals() ;
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
        SimpleLogger.Log("List of videos");

        for ( Video video: videos ) {
            SimpleLogger.Log("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
        }
        SimpleLogger.Log("End of list");
    }

    public void listCustomers() {
        SimpleLogger.Log("List of customers");
        for ( Customer customer: customers ) {
            SimpleLogger.Log("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size()) ;
            for ( Rental rental: customer.getRentals() ) {
                SimpleLogger.LogL("\tTitle: " + rental.getVideo().getTitle() + " ") ;
                SimpleLogger.LogL("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }
        }
        SimpleLogger.Log("End of list");
    }

    public void getCustomerReport() {
        SimpleLogger.Log("Enter customer name: ") ;
        String customerName = scanner.next() ;

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) {
            SimpleLogger.Log("No customer found") ;
        } else {
            String result = foundCustomer.getReport() ;
            SimpleLogger.Log(result);
        }
    }

    public void rentVideo() {
        SimpleLogger.Log("Enter customer name: ") ;
        String customerName = scanner.next() ;

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) return ;

        SimpleLogger.Log("Enter video title to rent: ") ;
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

        List<Rental> customerRentals = foundCustomer.getRentals() ;
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    public void register(String object) {
        if ( object.equals("customer") ) {
            SimpleLogger.Log("Enter customer name: ") ;
            String name = scanner.next();
            Customer customer = new Customer(name) ;
            customers.add(customer) ;
        }
        else {
            SimpleLogger.Log("Enter video title to register: ") ;
            String title = scanner.next() ;

            SimpleLogger.Log("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
            int videoType = scanner.nextInt();

            SimpleLogger.Log("Enter price code( 1 for Regular, 2 for New Release ):") ;
            int priceCode = scanner.nextInt();

            Date registeredDate = new Date();
            Video video = new Video(title, videoType, priceCode, registeredDate) ;
            videos.add(video) ;
        }
    }

    public int showCommand() {
        SimpleLogger.Log("\nSelect a command !");
        SimpleLogger.Log("\t 0. Quit");
        SimpleLogger.Log("\t 1. List customers");
        SimpleLogger.Log("\t 2. List videos");
        SimpleLogger.Log("\t 3. Register customer");
        SimpleLogger.Log("\t 4. Register video");
        SimpleLogger.Log("\t 5. Rent video");
        SimpleLogger.Log("\t 6. Return video");
        SimpleLogger.Log("\t 7. Show customer report");
        SimpleLogger.Log("\t 8. Show customer and clear rentals");

        return scanner.nextInt();
    }
}

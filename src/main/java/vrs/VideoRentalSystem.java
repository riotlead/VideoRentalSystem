package vrs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoRentalSystem {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Video> videos = new ArrayList<>();

    public List<Video> getVideos() { return videos; }
    public List<Customer> getCustomers() { return customers; }

    public Customer getCustomer(String name) {
        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(name)) {
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

    public void returnVideo(Customer customer, String videoTitle) {
        List<Rental> customerRentals = customer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    public void clearRentals(Customer customer) {
        List<Rental> rentals = new ArrayList<>() ;
        customer.setRentals(rentals);
    }

    public void registerCustomer(String name) {
        Customer customer = new Customer(name) ;
        customers.add(customer) ;
    }

    public void registerVideo(String title, int videoType, int priceCode) {
        Date registeredDate = new Date();
        Video video = new Video(title, videoType, priceCode, registeredDate) ;
        videos.add(video);
    }

    public void rentVideo(Customer customer, String videoTitle) {
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

    public void init() {
        Customer james = new Customer("James");
        Customer brown = new Customer("brown");

        customers.add(james);
        customers.add(brown);

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date());
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date());

        videos.add(v1);
        videos.add(v2);

        Rental r1 = new Rental(v1) ;
        Rental r2 = new Rental(v2) ;

        james.addRental(r1) ;
        james.addRental(r2) ;
    }
}

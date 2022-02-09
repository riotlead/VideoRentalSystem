package vrs;
import java.util.Date;

public class Rental {
    private Video video ;
    public enum Status {
        RENTED, RETURNED
    };
    private Status status;
    private Date rentDate ;
    private Date returnDate ;

    public Rental(Video video) {
        this.video = video ;
        status = Status.RENTED ;
        rentDate = new Date() ;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Status getStatus() {
        return status;
    }

    public void returnVideo() {
        if ( status == Status.RETURNED ) {
            this.status = Status.RETURNED;
            returnDate = new Date() ;
        }
    }
    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getDaysRentedLimit() {
        int limit = 0 ;
        int daysRented ;
        if (getStatus() == Status.RETURNED) { // returned Video
            long diff = returnDate.getTime() - rentDate.getTime();
            daysRented = (int) (diff / (Constant.A_DAY_IN_MILLIS)) + 1;
        } else { // not yet returned
            long diff = new Date().getTime() - rentDate.getTime();
            daysRented = (int) (diff / (Constant.A_DAY_IN_MILLIS)) + 1;
        }
        if ( daysRented <= 2) return limit ;

        switch ( video.getVideoType() ) {
            case Video.VHS: limit = 5 ; break ;
            case Video.CD: limit = 3 ; break ;
            case Video.DVD: limit = 2 ; break ;
        }
        return limit ;
    }
}

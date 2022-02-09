package vrs;

import java.util.Date;

public class Video {
  private String title;

  /**
   * TODO
   * VideoType과 PriceCode에 따라 결정 되는 사항들이 있음
   * ex: Video.java에서는 pentalty 값이 결정 됨
   * Customer.java에서는 each charge가 결정 됨
   * 사용처에서는 Video의 Type과, PriceCode를 조합 하여 사용 하여야 하고
   * Video Type과 PriceCode가 확장성을 갖도록 
   * Decoration Pattern을 적용 하는게 좋아 보임
   */
  private int priceCode;
  public static final int REGULAR = 1;
  public static final int NEW_RELEASE = 2;

  private int videoType;
  public static final int VHS = 1;
  public static final int CD = 2;
  public static final int DVD = 3;

  private Date registeredDate;
  private boolean rented;

  public Video(String title, int videoType, int priceCode, Date registeredDate) {
    this.setTitle(title);
    this.setVideoType(videoType);
    this.setPriceCode(priceCode);
    this.registeredDate = registeredDate;
  }

  public int getLateReturnPointPenalty() {
    int pentalty = 0;
    switch (videoType) {
      case VHS:
        pentalty = 1;
        break;
      case CD:
        pentalty = 2;
        break;
      case DVD:
        pentalty = 3;
        break;
    }
    return pentalty;
  }

  public int getPriceCode() {
    return priceCode;
  }

  public void setPriceCode(int priceCode) {
    this.priceCode = priceCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isRented() {
    return rented;
  }

  public void setRented(boolean rented) {
    this.rented = rented;
  }

  public Date getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }

  public int getVideoType() {
    return videoType;
  }

  public void setVideoType(int videoType) {
    this.videoType = videoType;
  }
}

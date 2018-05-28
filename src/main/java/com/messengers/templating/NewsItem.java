package com.messengers.templating;

import java.util.Date;

public class NewsItem {
  private String title;
  private Date date;
  private String mainInfo;
  private String snippet;

  private String imageUrl;
  private int imageHeight = -1;
  private int imageWidth = -1;
  private boolean imageDimensionsSet = false;

  private String infoSource;
  private String learnMoreLink;

  public NewsItem(String title, String mainInfo) {
    this.title = title;
    this.mainInfo = mainInfo;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getMainInfo() {
    return mainInfo;
  }

  public void setMainInfo(String mainInfo) {
    this.mainInfo = mainInfo;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setImageUrl(String imageUrl, int imageWidth, int imageHeight) {
    this.imageUrl = imageUrl;
    this.imageHeight = imageHeight;
    this.imageWidth = imageWidth;
    this.imageDimensionsSet = true;
  }

  public String getInfoSource() {
    return infoSource;
  }

  public void setInfoSource(String infoSource) {
    this.infoSource = infoSource;
  }

  public String getSnippet() {
    return snippet;
  }

  public void setSnippet(String snippet) {
    this.snippet = snippet;
  }

  public String getLearnMoreLink() {
    return learnMoreLink;
  }

  public void setLearnMoreLink(String learnMoreLink) {
    this.learnMoreLink = learnMoreLink;
  }

  public int getImageHeight() {
    return imageHeight;
  }

  public int getImageWidth() {
    return imageWidth;
  }

  public boolean areImageDimensionsSet() {
    return imageDimensionsSet;
  }

  public void setImageDimensions(int imageWidth, int imageHeight) {
    this.imageWidth = imageWidth;
    this.imageHeight = imageHeight;
    this.imageDimensionsSet = true;
  }

  public void resetImageDimensions() {
    this.imageHeight = -1;
    this.imageWidth = -1;
    this.imageDimensionsSet = false;
  }
}

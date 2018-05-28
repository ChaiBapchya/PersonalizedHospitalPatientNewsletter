package com.messengers.templating;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class EmailWriter {
  private static final String DEFAULT_EMAIL_DATE_FORMAT = "EEE, MMM d, yyyy ha";
  private static final String DEFAULT_NEWS_ITEM_DATE_FORMAT = "EEE, MMM d, yyyy hh:mma";

  private List<NewsItem> newsItems = new ArrayList<>();
  private String title;
  private String recipientName;
  private Date date;

  private String emailSenderName;
  private String senderEmail;
  private String senderContactInfo;

  private SimpleDateFormat emailDateFormat = new SimpleDateFormat(DEFAULT_EMAIL_DATE_FORMAT);
  private SimpleDateFormat newsItemDateFormat = new SimpleDateFormat(DEFAULT_NEWS_ITEM_DATE_FORMAT);

  public EmailWriter() {}

  public abstract String writeHtmlEmailString();

  public EmailWriter addNewsItem(NewsItem newsItem) {
    newsItems.add(newsItem);
    return this;
  }

  public EmailWriter addNewsItems(List<NewsItem> newsItems) {
    this.newsItems.addAll(newsItems);
    return this;
  }

  public EmailWriter clearNewsItems() {
    newsItems.clear();
    return this;
  }

  public String getTitle() {
    return title;
  }

  public EmailWriter setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public EmailWriter setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public Date getDate() {
    return date;
  }

  public EmailWriter setDate(Date date) {
    this.date = date;
    return this;
  }

  public SimpleDateFormat getEmailDateFormat() {
    return emailDateFormat;
  }

  public EmailWriter setEmailDateFormat(SimpleDateFormat emailDateFormat) {
    this.emailDateFormat = emailDateFormat;
    return this;
  }

  public EmailWriter resetEmailDateFormat() {
    this.emailDateFormat = new SimpleDateFormat(DEFAULT_EMAIL_DATE_FORMAT);
    return this;
  }

  public SimpleDateFormat getNewsItemDateFormat() {
    return newsItemDateFormat;
  }

  public EmailWriter setNewsItemDateFormat(SimpleDateFormat newsItemDateFormat) {
    this.newsItemDateFormat = newsItemDateFormat;
    return this;
  }

  public EmailWriter resetNewsItemDateFormat() {
    this.newsItemDateFormat = new SimpleDateFormat(DEFAULT_NEWS_ITEM_DATE_FORMAT);
    return this;
  }

  public String formatNewsItemDate(Date date) {
    if (newsItemDateFormat != null && date != null)
      return newsItemDateFormat.format(date);
    else
      return "";
  }

  public String formatEmailDate(Date date) {
    if (emailDateFormat != null && date != null)
      return emailDateFormat.format(date);
    else
      return "";
  }

  public String formatEmailDate() {
    if (emailDateFormat != null && date != null)
      return emailDateFormat.format(date);
    else
      return "";
  }

  public List<NewsItem> getNewsItems() {
    return newsItems;
  }

  public String getEmailSenderName() {
    return emailSenderName;
  }

  public EmailWriter setEmailSenderName(String emailSenderName) {
    this.emailSenderName = emailSenderName;
    return this;
  }

  public String getSenderEmail() {
    return senderEmail;
  }

  public EmailWriter setSenderEmail(String senderEmail) {
    this.senderEmail = senderEmail;
    return this;
  }

  public String getSenderContactInfo() {
    return senderContactInfo;
  }

  public EmailWriter setSenderContactInfo(String senderContactInfo) {
    this.senderContactInfo = senderContactInfo;
    return this;
  }
}

package com.messengers.email;

import com.messengers.templating.EmailHtmlWriter;
import com.messengers.templating.EmailWriter;
import com.messengers.templating.NewsItem;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.email.Recipient;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;

import javax.mail.Message;
import java.util.*;

public class SimpleEmailServer {
  private Mailer mailer;
  private String serverName;
  private String serverEmailAddress;
  private String serverPassword;

  public static void mailSender(ArrayList<NewsItem> newsItems,String emailID, String patientName, String recipientName){
    SimpleEmailServer server = new SimpleEmailServer();
    EmailHtmlWriter writer = new EmailHtmlWriter();
    for(NewsItem news: newsItems){
      writer.addNewsItem(news);
    }
    writer.setTitle("Patient Updates")
        .setDate(new Date(Calendar.getInstance().getTimeInMillis()))
        .setRecipientName(patientName)
            .setSenderEmail("ihi.messengers@gmail.com")
            .setEmailSenderName("IHI Hospital")
            .setSenderContactInfo("(284)-294-2498");
    String body = writer.writeHtmlEmailString();
//        System.out.println(body);
    server.testSendEmail(recipientName, emailID, "Hospital Newsletter", body);
  }

  public static void main(String[] args) {
    MedlineScrapedImageProvider ip = new MedlineScrapedImageProvider();

    SimpleEmailServer server = new SimpleEmailServer();

    NewsItem newsItem1 = new NewsItem("<span class=\"qt0\">Diabetes</span>", "<p><span class=\"qt0\">Diabetes</span> is a disease in which your blood glucose, or blood sugar, levels are too high. Glucose comes from the foods you eat. Insulin is a hormone that helps the glucose get into your cells to give them energy. With type 1 <span class=\"qt0\">diabetes</span>, your body does not make insulin. With type 2 <span class=\"qt0\">diabetes</span>, the more common type, your body does not make or use insulin well. Without enough insulin, the glucose stays in your blood. You can also have prediabetes. This means that your blood sugar is higher than normal but not high enough to be called <span class=\"qt0\">diabetes</span>. Having prediabetes puts you at a higher risk of getting type 2 <span class=\"qt0\">diabetes</span>.</p><p>Over time, having too much glucose in your blood can cause serious problems. It can damage your eyes, kidneys, and nerves. <span class=\"qt0\">Diabetes</span> can also cause heart disease, stroke and even the need to remove a limb. Pregnant women can also get <span class=\"qt0\">diabetes</span>, called gestational <span class=\"qt0\">diabetes</span>.</p><p>Blood tests can show if you have <span class=\"qt0\">diabetes</span>. One type of test, the A1C, can also check on how you are managing your <span class=\"qt0\">diabetes</span>. Exercise, weight control and sticking to your meal plan can help control your <span class=\"qt0\">diabetes</span>. You should also monitor your blood glucose level and take medicine if prescribed. </p><p>NIH: National Institute of <span class=\"qt0\">Diabetes</span> and Digestive and Kidney Diseases</p>");
    newsItem1.setDate(new Date());

    NewsItem newsItem2 = new NewsItem("<span class=\"qt0\">Diabetes</span> Type 1", "<p><span class=\"qt0\">Diabetes</span> means your blood glucose, or blood sugar, levels are too high. With type 1 <span class=\"qt0\">diabetes</span>, your pancreas does not make insulin. Insulin is a hormone that helps glucose get into your cells to give them energy. Without insulin, too much glucose stays in your blood. Over time, high blood glucose can lead to serious problems with your heart, eyes, kidneys, nerves, and gums and teeth. </p><p>Type 1 <span class=\"qt0\">diabetes</span> happens most often in children and young adults but can appear at any age. Symptoms may include</p><ul><li> Being very thirsty </li><li>\tUrinating often </li><li>\tFeeling very hungry or tired </li><li>\tLosing weight without trying </li><li>\tHaving sores that heal slowly </li><li>\tHaving dry, itchy skin </li><li>\tLosing the feeling in your feet or having tingling in your feet </li><li>\tHaving blurry eyesight </li></ul><p>A blood test can show if you have <span class=\"qt0\">diabetes</span>. If you do, you will need to take insulin for the rest of your life. A blood test called the A1C can check to see how well you are managing your <span class=\"qt0\">diabetes</span>.</p><p>NIH: National Institute of <span class=\"qt0\">Diabetes</span> and Digestive and Kidney Diseases</p>");
    newsItem2.setDate(new Date());
    newsItem2.setLearnMoreLink("https://medlineplus.gov/diabetestype1.html");
    newsItem2.setImageUrl(ip.getImageLink("https://medlineplus.gov/diabetestype1.html"));

    NewsItem newsItem3 = new NewsItem("<span class=\"qt2\">Rehabilitation</span>", "<p>After a serious injury, illness or surgery, you may recover slowly. You may need to regain your strength, relearn skills or find new ways of doing things you did before. This process is <span class=\"qt2\">rehabilitation</span>. </p><p><span class=\"qt2\">Rehabilitation</span> often focuses on</p><ul><li>\t<span class=\"qt0\">Physical</span> <span class=\"qt1\">therapy</span> to help your strength, mobility and fitness</li><li>\tOccupational <span class=\"qt1\">therapy</span> to help you with your daily activities</li><li> Speech-language <span class=\"qt1\">therapy</span> to help with speaking, understanding, reading, writing and swallowing</li><li>\t<span class=\"qt1\">Treatment</span> of pain</li></ul><p>The type of <span class=\"qt1\">therapy</span> and goals of <span class=\"qt1\">therapy</span> may be different for different people. An older person who has had a stroke may simply want <span class=\"qt2\">rehabilitation</span> to be able to dress or bathe without help. A younger person who has had a heart attack may go through cardiac <span class=\"qt2\">rehabilitation</span> to try to return to work and normal activities. Someone with a lung disease may get pulmonary <span class=\"qt2\">rehabilitation</span> to be able to breathe better and improve their quality of life. </p>");
    newsItem3.setDate(new Date());
    newsItem3.setSnippet("After a serious injury, illness or surgery, you may recover slowly. You may need to regain your strength, relearn skills or find new ways of doing things you ...");
    newsItem3.setLearnMoreLink("https://medlineplus.gov/rehabilitation.html");
    newsItem3.setImageUrl(ip.getImageLink("https://medlineplus.gov/rehabilitation.html"));


    EmailHtmlWriter writer = new EmailHtmlWriter();
    writer.setTitle("Patient Updates")
        .setDate(new Date(1523448000000L))
        .setRecipientName("Larry Lorry")
        .setSenderEmail("ihi.messengers@gmail.com")
        .setEmailSenderName("IHI Hospital")
        .setSenderContactInfo("(284)-294-2498")
        .addNewsItem(newsItem3)
        .addNewsItem(newsItem2)
        .addNewsItem(newsItem1);
    String body = writer.writeHtmlEmailString();
    System.out.println(body);
    server.testSendEmail("Mr. Shim", "hyeonwooshim@gmail.com", "Hospital Newsletter4", body);
  }

  public SimpleEmailServer(String serverName, String serverEmailAddress, String serverPassword) {
    this.serverName = serverName;
    this.serverEmailAddress = serverEmailAddress;
    this.serverPassword = serverPassword;
    this.mailer = setupGmailMailer(this.serverEmailAddress, this.serverPassword);
  }

  public SimpleEmailServer() {
    this("IHI Messengers", "ihi.messengers@gmail.com", "pw.messengers");
  }

  private Mailer setupGmailMailer(String username, String password) {
    return MailerBuilder.withSMTPServer("smtp.gmail.com", 587, username, password)
        //.withDebugLogging(true)
        .buildMailer();
  }

  private Email buildPlainTextEmail(String recipient, String recipientAddr, String subject, String body) {
    List<Recipient> recipients = new ArrayList<>();
    recipients.add(new Recipient(recipient, recipientAddr, Message.RecipientType.TO));
    return startEmailBuilder(recipients, subject)
        .withPlainText(body)
        .buildEmail();
  }

  private EmailPopulatingBuilder startEmailBuilder(Collection<Recipient> recipients, String subject) {
    return EmailBuilder.startingBlank()
        .from(serverName, serverEmailAddress)
        .withRecipients(recipients)
        .withSubject(subject);
  }

  private Email buildHtmlEmail(String recipient, String recipientAddr, String subject, String html) {
    List<Recipient> recipients = new ArrayList<>();
    recipients.add(new Recipient(recipient, recipientAddr, Message.RecipientType.TO));
    return startEmailBuilder(recipients, subject)
        .withHTMLText(html)
        .buildEmail();
  }

  private void testSendEmail(String recipient, String recipientAddr, String subject, String body) {
    mailer.sendMail(buildHtmlEmail(recipient, recipientAddr,
        subject,
        body)
    );
  }


}

package com.messengers.email;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MedlineScrapedImageProvider implements ImageProvider {
  private WebClient webClient;

  private Random rand;

  private Map<String, String> siteImgMap = new HashMap<>();
  private double resetProbability = 0.1;

  public static void main(String[] args) {
    MedlineScrapedImageProvider ip = new MedlineScrapedImageProvider();
    System.out.println(ip.getImageLink("https://medlineplus.gov/ectopicpregnancy.html"));
  }

  public MedlineScrapedImageProvider() {
    webClient = new WebClient();
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);
    rand = new Random();
  }

  public void setResetProbability(double resetProbability) {
    this.resetProbability = resetProbability;
  }

  /**
   * Query is the link to a MedlinePlus page on a health topic.
   * @param query URL of the MedlinePlus health topic page.
   * @return A scraped link to the relevant image. Null if none were found.
   */
  @Override
  public String getImageLink(String query) {
    if (query == null) return null;

    // We may already have queried for that.
    String result = siteImgMap.get(query);
    if (result != null && rand.nextDouble() > resetProbability) return result;

    try {
      HtmlPage page = webClient.getPage(query);
      DomNodeList<DomElement> imgs = page.getElementsByTagName("img");
      List<String> candidates = new ArrayList<>();
      for (DomElement element : imgs) {
        String src = element.getAttribute("src");
        String title = element.getAttribute("title");
        if (title.isEmpty()) {
          // It seems MedlinePlus uses the same string for "title" and "alt" attributes anyways
          title = element.getAttribute("alt");
        }

        if (src.equals("https://medlineplus.gov/images/nih.png")) continue;
        if (src.equals("https://medlineplus.gov/images/return-top.png")) continue;

        if (src.contains("logo")) continue;
        if (src.contains("fb")) continue;
        if (src.contains("twitter")) continue;
        if (src.contains("googleplus")) continue;

        System.out.println(src + ", " + title);

        String lowerCaseQuery = query.toLowerCase();

        boolean addToCandidates = false;
        if (!title.isEmpty()) {
          String[] tokens = title.toLowerCase().split(" ");
          for (String token : tokens) {
            if (lowerCaseQuery.contains(token)) {
              addToCandidates = true;
              break;
            }
          }
        }

        if (addToCandidates) candidates.add(src);
      }

      if (candidates.size() > 1) System.out.println("Found more than 1 possible photo links at " + query);

      if (!candidates.isEmpty()) {
        String chosen = candidates.get(0);
        siteImgMap.put(query, chosen);
        return chosen;
      }

    } catch (IOException e) {
      return null;
    }

    return null;
  }
}

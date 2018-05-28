/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messengers.fhir;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.messengers.email.MedlineScrapedImageProvider;
import com.messengers.templating.NewsItem;


public class RequestNLM {
    public static void main(String[] args) throws Exception {
       // System.out.println("Hello World");
        RequestNLM request = new RequestNLM();
        //request.sendGet("snomedct", "195967001");
        NewsItem newsitem = request.sendGet("icd9", "556.8");
        System.out.println("\nTitle: " + newsitem.getTitle());
        System.out.println("Description :" + newsitem.getMainInfo());
        
    }
    public static final String USER_AGENT = "Mozilla/5.0";
    
     /**
     * Makes API request to NLM NIH server to get information about a condition
     *
     * @param type Type of code ICD9/SNOMEDCT
     * @param code the code value
     * @return Description of condition
     */
    public static NewsItem sendGet(String type, String code) throws Exception {
        StringBuilder stringBuilder = new StringBuilder("https://apps.nlm.nih.gov/medlineplus/services/mpconnect_service.cfm");
        stringBuilder.append("?mainSearchCriteria.v.c="); 
        stringBuilder.append(URLEncoder.encode(code, "UTF-8"));
        if(type.toLowerCase().equals("icd9"))
        {
           stringBuilder.append("&mainSearchCriteria.v.cs=2.16.840.1.113883.6.103"); 
        }
        else if(type.toLowerCase().equals("snomedct"))
        {
            stringBuilder.append("&mainSearchCriteria.v.cs=2.16.840.1.113883.6.96");
        }
 
        stringBuilder.append("&mainSearchCriteria.v.dn=&informationRecipient.languageCode.c=en&knowledgeResponseType=application/json");
        String url = stringBuilder.toString();
		
        URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	// optional default is GET
	con.setRequestMethod("GET");

	//add request header
	con.setRequestProperty("User-Agent", USER_AGENT);

	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + con.toString());
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine+ "\n");
	}
	in.close();
        //JSONObject dataSet = new JSONObject();
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(response.toString()).getAsJsonObject();
        //System.out.println(result);
        //System.out.println(result.get("feed").getAsJsonObject().get("entry").getAsJsonArray().get(0));
        JsonArray summary = result.getAsJsonObject("feed").get("entry").getAsJsonArray();
        if(summary.size() == 0)
        {
            return null;
        }
        else
        {
            MedlineScrapedImageProvider ip = new MedlineScrapedImageProvider();
            String title = summary.get(0).getAsJsonObject().get("link").getAsJsonArray().get(0).getAsJsonObject().get("title").toString();
            String image = summary.get(0).getAsJsonObject().get("link").getAsJsonArray().get(0).getAsJsonObject().get("href").toString();
            String value = summary.get(0).getAsJsonObject().get("summary").getAsJsonObject().get("_value").toString();
            title = title.substring(1, title.length()-1);
            value = value.substring(1, value.length()-1);
            image = image.substring(1, image.length()-1);
            System.out.println(value);
            NewsItem newsitem = new NewsItem(title,value);
            newsitem.setImageUrl(ip.getImageLink(image));
            return newsitem;
        }

    }
    
}

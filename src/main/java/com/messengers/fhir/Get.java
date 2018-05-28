/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messengers.fhir;

/**
 *
 * @author anita
 */

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Get {
    
   public static String getHTML(String urlToRead) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
   }
   public static String getCodeFormat(JsonObject o,String url){
       if(url.equals("http://snomed.info/sct"))
           return "SNOMEDCT";
       else if(url.equals("http://loinc.org"))
           return "LOINC";
       else if(url.equals("http://hl7.org/fhir/sid/icd-10"))
           return "ICD-10";
       else if(url.equals("http://hl7.org/fhir/sid/icd-9"))
           return "ICD-9";
       else 
           return "error in code format";
               
   }
   public static Map retrieveConditionCodes(String patientID)throws Exception{
    String result=getHTML("https://apps.hdap.gatech.edu/syntheticmass/baseDstu3/Condition?subject:Patient="+patientID+"&_format=json");    
    JsonObject job = new Gson().fromJson(result, JsonObject.class);
    JsonArray entry1=job.getAsJsonArray("entry");
    List<JsonElement> arr2=new ArrayList<>();
    for (int i = 0; i < entry1.size(); ++i) {
        JsonElement rec = entry1.get(i);
        JsonObject rec1=rec.getAsJsonObject();
        JsonElement rec2=rec1.getAsJsonObject("resource").getAsJsonObject("code").getAsJsonArray("coding");
        arr2.add(rec2);
    }
    Map<String,String> codes=new HashMap<>();
     for(JsonElement i:arr2){
        JsonObject o=i.getAsJsonArray().get(0).getAsJsonObject();
        if(!codes.containsKey(o.get("code").toString()))
                codes.put(o.get("code").toString(), getCodeFormat(o,o.get("system").getAsString()));       
     }
     return codes;
   }
   public static void main(String[] args) throws Exception
   {
     String id="296769f2-5fe9-4997-a521-82c5eb090311";
     String id2="67eba314-7595-431c-9d62-8d30e0bd76ac";
     String id3="aae6d4ab-c671-486a-9aa4-ba7ce93e804e";
     String id4="82b4a1c1-e093-4836-b14c-8df348e7574b";
     Map<String,String> codes=retrieveConditionCodes(id3);
     for(Map.Entry<String,String> entry:codes.entrySet()){
         System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
     }

   }
}

package com.messengers.fhir;
import com.messengers.*;
import com.messengers.email.SimpleEmailServer;
import com.mongodb.*;
 import com.mongodb.client.MongoDatabase;
 import java.io.FileInputStream;
import java.io.InputStream;

import com.messengers.templating.NewsItem;
 import java.util.*;
 import java.net.UnknownHostException;

public class MongoCode {

    public static void main(String args[]) {
        try {
            Properties prop = new Properties();
            InputStream input = null;
            String database="",database_name="",collection_name="";
            int port=0;
            try{
//                Runtime rt = Runtime.getRuntime();
//                Process pr = rt.exec("pwd");
//                System.out.print(pr);
                input = new FileInputStream("/Users/chaitanyabapat/Spring18/IHI/project/Messengers/src/main/java/com/messengers/fhir/config.properties");
                prop.load(input);
                database = GetProperties.getDatabase(prop);
               database_name = GetProperties.getDatabaseName(prop);
               port = GetProperties.getPort(prop);
                collection_name = GetProperties.getCollection(prop);

            }catch (Exception e){
                System.out.print(e);
            }
//            System.out.print(database+port);
            MongoClient mongo = new MongoClient(database, port);//put in config
//            String collection_name = "fhir_patient_db";//put in config
//            String database_name = "fhir_patient_db";//put in config
            DB db = mongo.getDB(database_name);
            DBCollection collection = db.getCollection(collection_name);
            BasicDBObject query = new BasicDBObject();
            BasicDBObject projection = new BasicDBObject();
            projection.put("patient_id","");
            query.put("dateOfDischarge", "");
            DBCursor cur = collection.find(query,projection);
            ArrayList<String> ids = new ArrayList<String>();
            while (cur.hasNext()) {
                String temp_id = cur.next().get("patient_id").toString();
                ids.add(temp_id);
                //System.out.println(temp_id);
            }
            Map<String,Map> patient_codes_map = new HashMap<String, Map>();
            for(String b : ids) {
                //System.out.println(b);
                Map<String,String>codes = Get.retrieveConditionCodes(b);
                patient_codes_map.put(b,codes);

            }
            for(Map.Entry<String,Map> codes:patient_codes_map.entrySet()) {
                String patient_code = codes.getKey();
//                System.out.println(patient_code + " ");

                projection.put("nextOfKin_email_address","");
                query.put("patient_id",patient_code);
                String email_id = collection.find(query,projection).next().get("nextOfKin_email_address").toString();

                projection.put("patient_name","");
                String patient_name = collection.find(query,projection).next().get("patient_name").toString();
                projection.put("recipient_name","");
                String recipient_name = collection.find(query,projection).next().get("recipient_name").toString(); //change it later

                System.out.println(email_id + " ");

                ArrayList<NewsItem> news = new ArrayList<NewsItem>();
                //System.out.println(code + " "+ codes);
                System.out.println(codes.getValue());
                Map<String,String> code_codetype_map = codes.getValue();
                for(Map.Entry<String,String> temp_code_codetype : code_codetype_map.entrySet())
                {
                    String temp_code = temp_code_codetype.getKey();
                    String temp_code_type = temp_code_codetype.getValue();
                    System.out.println(temp_code);
                    NewsItem get_news_item = RequestNLM.sendGet(temp_code_type,temp_code);
                    if(get_news_item!=null)
                        news.add(get_news_item);
                }
                for(NewsItem newsItem:news){
                    System.out.println("\nTitle: " + newsItem.getTitle());
                    System.out.println("Description :" + newsItem.getMainInfo());
                }
                SimpleEmailServer.mailSender(news,email_id,patient_name,recipient_name);
//                String emailID, String patientName, String recipientName)
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}

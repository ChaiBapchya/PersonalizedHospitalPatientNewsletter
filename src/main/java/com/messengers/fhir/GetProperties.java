/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messengers.fhir;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ashwinbhide
 */
public class GetProperties {
    
    static String  getDatabase(Properties p)
    {
        return p.getProperty("database");
    }
    
     static String getDatabaseName(Properties p)
    {
        return p.getProperty("database_name");
    }
    
     static String getCollection(Properties p)
    {
        return p.getProperty("collection_name");
    }
     static int getPort(Properties p)
    {
        return Integer.parseInt(p.getProperty("port"));
    }
    public static void main(String args[])
    {
        Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		System.out.println(prop.getProperty("database"));
		System.out.println(prop.getProperty("dbuser"));
		System.out.println(prop.getProperty("dbpassword"));

	} catch (IOException ex) {
		ex.printStackTrace();
        }
    } 
    
}

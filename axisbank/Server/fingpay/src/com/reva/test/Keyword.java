package com.reva.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Keyword {

	public static void main(String args[]) {
		String url = "http://www.flipkart.com";
		String[] keywords = { "online", "shopping", "pareshan" };

		String data = htmlContent(url);
		int score = keywordsMatch(data, keywords);
		if (score == keywords.length)
			System.out.println("Keywords Present");
		else
			System.out.println("Number of Keywords not matching: "
					+ (keywords.length - score));

	}

	private static int keywordsMatch(String data, String[] keywords) {
		int score = 0;
		for (String keyword : keywords) {
			if (data.toLowerCase().contains(keyword))
				score++;
		}
		return score;
	}

	private static String htmlContent(String url) {

		BufferedReader in = null;
		StringBuilder response = new StringBuilder();
		try {
			URLConnection connection = new URL(url).openConnection();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		return response.toString();
	}
	
	public static final String RECORD_SOA = "SOA";
    public static final String RECORD_A = "A";
    public static final String RECORD_NS = "NS";
    public static final String RECORD_MX = "MX";
    public static final String RECORD_CNAME = "CNAME";

    public static List lookup(String hostName, String record) {

        List result = new Vector();
        try {
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(hostName, new String[] { record });
            Attribute attr = attrs.get(record);

            NamingEnumeration attrEnum = attr.getAll();
            while (attrEnum.hasMoreElements())
                result.add(attrEnum.next());
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

}

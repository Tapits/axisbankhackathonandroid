package com.reva.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PayuResponse
 */
@WebServlet("/payuResponse")
public class PayuResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayuResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String queryStr = "";
		try {
			Hashtable<String, String> perameters = new Hashtable<String, String>();
			StringBuilder sb = new StringBuilder();
				BufferedReader reader = request.getReader();
				String line;
				do {
					line = reader.readLine();
					sb.append(line);
				} while (line != null);
				reader.reset();
				queryStr = sb.toString();
				String[] peramsStr = queryStr.split("&");
				System.out.println("PayuResponse : ");
				for (String peramStr : peramsStr) {
					String[] peramInfo = peramStr.split("=");
					if (peramInfo.length > 1){
						perameters.put(peramInfo[0], peramInfo[1]);
						System.out.println(peramInfo[0] +" = "+peramInfo[1]);
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().write(queryStr);
	}

}

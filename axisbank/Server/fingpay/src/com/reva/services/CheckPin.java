package com.reva.services;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.data.MpinData;
import com.reva.data.SessionData;
import com.reva.jpa.MerchantMaster;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class CheckPin
 */
@WebServlet("/checkPin")
public class CheckPin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckPin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityCheck.disableCache(response);
	    
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new GsonBuilder().serializeNulls().create();
		String dataSent = "";

		/*
		 * Reader reader = request.getReader(); int ch; while ((ch =
		 * reader.read()) != -1) { dataSent += (char) ch; }
		 */

		dataSent = request.getReader().readLine();

		if (CommonTasks.check(dataSent)) {
			MpinData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, MpinData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					MerchantMaster master = manageTransaction.find(MerchantMaster.class, modelM.getMerchantId());
					if (master != null && master.getMerchantPin() == modelM.getmPin()) {
						int merchantid = master.getId();
						
						SessionData sessionData = new SessionData();
						sessionData.setActiveFlag(master.getActiveFlag());
						sessionData.setId(merchantid);
						sessionData.setMerchantAddress1(master.getMerchantAddress1());
						sessionData.setMerchantAddress2(master.getMerchantAddress2());
						sessionData.setMerchantAddress3(master.getMerchantAddress3());
						sessionData.setMerchantId(master.getMerchantId());
						sessionData.setMerchantName(master.getMerchantName());
						
						HttpSession httpSessionTemp;
						HttpSession session = request.getSession();
						httpSessionTemp = session;
		    		    Map<String,Object> values = getSessionData(httpSessionTemp);
		    		    // Kill the current session
		    		    httpSessionTemp.invalidate();

		    		    httpSessionTemp = request.getSession(true);
		    		    putSessionValues(httpSessionTemp, values); //This line is psydo code
		    		    sessionData.setSessionId(httpSessionTemp.getId());
		    			httpSessionTemp.setAttribute("MerchantSessionData", sessionData);
		    			httpSessionTemp.setAttribute("isLogin", true);
		    			session = httpSessionTemp;
		    			System.out.println("Login Session : "+session.getId());
						
						result = gson.toJson(new GeneralResponse(
								Constants.TRUE, Constants.REQUEST_COMPLETED,
								merchantid));
					} else {
						result = gson.toJson(new GeneralResponse(
								Constants.FALSE,
								Constants.ERROR_AUTHENTICATION, null));
					}

				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, null));
				}
			}

		} else {
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERROR_INCOMPLETE_DATA, null));
		}
		response.getWriter().write(result);
	}
	
	private Map<String, Object> getSessionData(HttpSession session) {
   	 Map<String,Object> map = new HashMap<String, Object>();
   	Enumeration<String> it = session.getAttributeNames(); 
   	while (it.hasMoreElements()) { 
   	    String name = it.nextElement(); 
   	    String value = session.getAttribute(name).toString(); 
//   	    System.out.println(name + " = " + value);
   	    map.put(name, value);
   	}
   	return map;
	}
	
	private void putSessionValues(HttpSession newSession, Map<String, Object> values) {
    	for(Map.Entry<String, Object> entry : values.entrySet()){
    		newSession.putValue(entry.getKey(), entry.getValue());
    	}
	}
}

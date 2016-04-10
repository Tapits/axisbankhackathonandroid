package com.reva.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reva.data.GeneralResponse;
import com.reva.data.SessionData;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
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
		SecurityCheck.disableCache(response);
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new GsonBuilder().serializeNulls().create();
		HttpSession httpSession = request.getSession();
		
		SessionData sessionData = (SessionData) httpSession.getAttribute("MerchantSessionData");
        try {
            httpSession.removeAttribute("MerchantSessionData");
        	httpSession.removeAttribute("isLogin");
        	httpSession.invalidate();//newelly added
            result = gson.toJson(new GeneralResponse(Constants.TRUE, Constants.REQUEST_COMPLETED, null));
        } catch (Exception e) {
        	e.printStackTrace();
        	result = gson.toJson(new GeneralResponse(Constants.FALSE, Constants.ERRORS_SESSION_NOT_CLEARED, null));
        }
        response.getWriter().write(result);
	}
}

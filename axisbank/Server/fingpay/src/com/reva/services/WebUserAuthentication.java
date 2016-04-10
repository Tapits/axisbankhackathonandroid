package com.reva.services;

import java.io.IOException;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.data.UserAuthenticationData;
import com.reva.data.UserData;
import com.reva.jpa.User;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class WebUserAuthenetication
 */
@WebServlet("/webUserAuthentication")
public class WebUserAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebUserAuthentication() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SecurityCheck.disableCache(response);
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new GsonBuilder().serializeNulls().create();
		// HttpSession session = SecurityCheck.getSession(request);
		// if (SecurityCheck.checkSession(SecurityCheck.getSession(request))) {
		String dataSent = request.getReader().readLine();
		UserAuthenticationData modelM = null;
		try {
			modelM = gson.fromJson(dataSent, UserAuthenticationData.class);
			System.out.println("dataSent : " + dataSent);
		} catch (Exception e) {
			e.printStackTrace();
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERROR_PARSING_REQUEST_DATA, 0));
		}

		if (modelM != null && result.length() == 0) {
			ManageTransaction manageTransaction = new ManageTransaction();
			try {
				String authQuery = "SELECT u FROM User u where u.username = ?1 and u.password = ?2";
				Query query = manageTransaction.createQuery(User.class,	authQuery);
				query.setParameter(1, modelM.getUserId());
				query.setParameter(2, modelM.getPassword());
				User user = null;
				try {
					user = (User) query.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
					user = null;
				}

				if (user != null) {
					UserData userData = new UserData();
					userData.setActiveFlag(user.getActiveFlag());
					userData.setUserId(user.getUserId());
					userData.setUsername(user.getUsername());
					userData.setUserType(user.getUserType());
					result = gson.toJson(new GeneralResponse(Constants.TRUE,
							Constants.REQUEST_COMPLETED,
							Constants.CORRECT_STATUS_CODE, userData));

				} else {
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERROR_USER_AUTHENTICATION, null));
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERRORS_EXCEPTION_IN_SERVER,
						Constants.EXCEPTION_IN_SERVER_STATUS_CODE, null));
			} finally {
				manageTransaction.close();
			}
		} else {
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERROR_INCOMPLETE_DATA,
					Constants.ERROR_INCOMPLETE_DATA_STATUS_CODE, null));
		}
		// } else {
		// result = gson.toJson(Constants.INVALID_SEESION);
		// }
		response.getWriter().write(result);
	}
}

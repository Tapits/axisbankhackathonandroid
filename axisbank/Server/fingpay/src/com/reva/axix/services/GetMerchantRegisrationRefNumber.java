package com.reva.axix.services;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reva.axix.data.GetMerchantRegisrationRefNumberData;
import com.reva.axix.jpa.MerchantRegisrationId;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;

/**
 * Servlet implementation class GetMerchantRegisrationRefNumber
 */
@WebServlet("/getMerchantRegisrationRefNumber")
public class GetMerchantRegisrationRefNumber extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMerchantRegisrationRefNumber() {
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			GetMerchantRegisrationRefNumberData modelM = null;
			try {
				modelM = gson.fromJson(dataSent,
						GetMerchantRegisrationRefNumberData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					String merchantRegisrationRefNumber = CommonTasks.getRegisrationRefNumber();
					MerchantRegisrationId merchantRegisrationId = new MerchantRegisrationId();
					merchantRegisrationId.setRegistrationType(modelM.getRegType() == 1 ? "online": modelM.getRegType() == 2 ? "offline" : "");
					merchantRegisrationId.setMerchantRegisrationRefNumber(merchantRegisrationRefNumber);
					merchantRegisrationId.setStatus(0);
					merchantRegisrationId.setTimestamp(new Date());
					manageTransaction.saveObject(merchantRegisrationId);

					result = gson.toJson(new GeneralResponse(Constants.TRUE,
							Constants.REQUEST_COMPLETED,
							merchantRegisrationRefNumber));
				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, 0));
				} finally {
					manageTransaction.close();
				}
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_INCOMPLETE_DATA, 0));
			}
		}
		response.getWriter().write(result);
	}
}

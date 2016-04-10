package com.reva.services;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.jpa.PhoneNumberOtp;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class GetOTP
 */
@WebServlet("/getOTP")
public class GetOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOTP() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SecurityCheck.disableCache(response);

		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new GsonBuilder().serializeNulls().create();
//		String dataSent = "";
		String mobileNumber = request.getParameter("mobileNumber");

		/*
		 * Reader reader = request.getReader(); int ch; while ((ch =
		 * reader.read()) != -1) { dataSent += (char) ch; }
		 */

//		dataSent = request.getReader().readLine();

		if (CommonTasks.check(mobileNumber)) {
//			OTPData modelM = null;
//			try {
//				modelM = gson.fromJson(dataSent, OTPData.class);
//				System.out.println("dataSent : " + dataSent);
//			} catch (Exception e) {
//				e.printStackTrace();
//				result = gson.toJson(new GeneralResponse(Constants.FALSE,
//						Constants.ERROR_PARSING_REQUEST_DATA, 0));
//			}
//
//			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
//					mobileNumber = Encryption.decrypt(mobileNumber);
					PhoneNumberOtp numberOtp = manageTransaction.find(PhoneNumberOtp.class, mobileNumber);
					int otp = CommonTasks.generatePin();
					if (numberOtp == null) {
						numberOtp = new PhoneNumberOtp();
						numberOtp.setActiveFlag(1);
						numberOtp.setOtp(otp);
						numberOtp.setPhoneNumber(mobileNumber);
						numberOtp.setTimestamp(new Date());
					} else {
						numberOtp.setActiveFlag(1);
						numberOtp.setOtp(otp);
						numberOtp.setTimestamp(new Date());
					}
					manageTransaction.saveObject(numberOtp);
					boolean status = CommonTasks.SendMerchantRegOTP(mobileNumber, otp);

					if(status){
						result = gson.toJson(new GeneralResponse(Constants.TRUE,
								Constants.OTP_SUCCESS,
								Constants.OTP_SUCCESS_STATUS_CODE, null));
					} else {
						result = gson.toJson(new GeneralResponse(Constants.FALSE,
								Constants.OTP_FAIL,
								Constants.OTP_FAIL_STATUS_CODE, null));
					}

				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, null));
				}
//			}

		} else {
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERROR_INCOMPLETE_DATA, null));
		}
		response.getWriter().write(result);
	}
}
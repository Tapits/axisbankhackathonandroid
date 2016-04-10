package com.reva.services;

import java.io.IOException;
import java.util.List;

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
import com.reva.data.MerchantMasterData;
import com.reva.data.MerchantRegistrationData;
import com.reva.jpa.MerchantMaster;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class CheckMerchantRegistration
 */
@WebServlet("/checkMerchantRegistration")
public class CheckMerchantRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckMerchantRegistration() {
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
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
//        if(SecurityCheck.checkSession(session)){
		dataSent = request.getReader().readLine();

		if (CommonTasks.check(dataSent)) {
			MerchantRegistrationData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, MerchantRegistrationData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}
			if(modelM != null && result.length() == 0){
				if(!SecurityCheck.merchantId(modelM.getMerchantId())){
					result = gson.toJson(new GeneralResponse(Constants.FALSE, Constants.ERROR_INVAILD_MERCHANT_ID, null));
				}
				if(!SecurityCheck.merchantPin(modelM.getMerchantPin())){
					result = gson.toJson(new GeneralResponse(Constants.FALSE, Constants.ERROR_INVAILD_MERCHANT_PIN, null));
				}
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					String queryString = "SELECT m FROM MerchantMaster m where m.merchantId = ?1 and m.merchantPin = ?2";
					Query query = manageTransaction.createQuery(
							MerchantMaster.class, queryString);
					query.setParameter(1, modelM.getMerchantId());
					query.setParameter(2, modelM.getMerchantPin());
					List<MerchantMaster> merchantMaster = query.getResultList();
					if (merchantMaster.size() != 0) {
						MerchantMaster merchant = merchantMaster.get(merchantMaster.size() - 1);
						MerchantMasterData data = new MerchantMasterData();
						data.setActiveFlag(merchant.getActiveFlag());
						data.setId(merchant.getId());
						data.setMerchantAddress1(merchant.getMerchantAddress1());
						data.setMerchantAddress2(merchant.getMerchantAddress2());
						data.setMerchantAddress3(merchant.getMerchantAddress3());
						data.setMerchantLogo(merchant.getMerchantLogo());
						data.setMerchantName(merchant.getMerchantName());
						data.setTimestamp(merchant.getTimestamp().getTime());
						
						result = gson.toJson(new GeneralResponse(
								Constants.TRUE, Constants.REQUEST_COMPLETED,
								data));
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
//        } else {
//        	result = gson.toJson(Constants.INVALID_SEESION);
//        }
		response.getWriter().write(result);
	}
}

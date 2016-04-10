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
import com.reva.data.CreateMPinData;
import com.reva.data.GeneralResponse;
import com.reva.data.MerchantMasterData;
import com.reva.jpa.MerchantMaster;
import com.reva.jpa.PosMerchantMap;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class CreateMPin
 */
@WebServlet("/createMPin")
public class CreateMPin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateMPin() {
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
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
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
		// if(SecurityCheck.checkSession(session)){
		dataSent = request.getReader().readLine();

		if (CommonTasks.check(dataSent)) {
			CreateMPinData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, CreateMPinData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}
			/*
			 * if(modelM != null && result.length() == 0){
			 * if(!SecurityCheck.merchantId(modelM.getMerchantId())){ result =
			 * gson.toJson(new GeneralResponse(Constants.FALSE,
			 * Constants.ERROR_INVAILD_MERCHANT_ID, null)); }
			 * if(!SecurityCheck.merchantPin(modelM.getMerchantPin())){ result =
			 * gson.toJson(new GeneralResponse(Constants.FALSE,
			 * Constants.ERROR_INVAILD_MERCHANT_PIN, null)); } }
			 */

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					MerchantMaster master = null;
					int merchantId = modelM.getMerchantId();
					if (merchantId == 0) {
						merchantId = getMerchantId(manageTransaction,modelM.getDeviceId());
					}
					master = manageTransaction.find(MerchantMaster.class,merchantId);

					if (master != null) {
						
						master.setMerchantPin(modelM.getmPin());
						manageTransaction.saveObject(master);
						
						MerchantMasterData data = new MerchantMasterData();
						data.setActiveFlag(master.getActiveFlag());
						data.setId(master.getId());
						data.setMerchantAddress1(master.getMerchantAddress1());
						data.setMerchantAddress2(master.getMerchantAddress2());
						data.setMerchantAddress3(master.getMerchantAddress3());
						data.setMerchantLogo(master.getMerchantLogo());
						data.setMerchantName(master.getMerchantName());
						data.setTimestamp(master.getTimestamp().getTime());

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
		// } else {
		// result = gson.toJson(Constants.INVALID_SEESION);
		// }
		response.getWriter().write(result);
	}

	private int getMerchantId(ManageTransaction manageTransaction, int deviceId) {
		String mString = "SELECT m FROM PosMerchantMap m where m.id.deviceId = ?1";
		Query query = manageTransaction.createQuery(PosMerchantMap.class, mString);
		query.setParameter(1, deviceId);
		PosMerchantMap posMerchantMap = (PosMerchantMap) query.getSingleResult();
		return posMerchantMap.getId().getMerchantId();
	}
}

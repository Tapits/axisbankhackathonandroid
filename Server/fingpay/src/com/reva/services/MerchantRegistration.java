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
import com.reva.data.MerchantMasterData;
import com.reva.jpa.CityMaster;
import com.reva.jpa.MerchantMaster;
import com.reva.jpa.StateMaster;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.FilesUtil;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class MerchantRegistration
 */
@WebServlet("/merchantRegistration")
public class MerchantRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MerchantRegistration() {
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
			MerchantMasterData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, MerchantMasterData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}
			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					MerchantMaster master = manageTransaction.find(MerchantMaster.class, modelM.getId());
					CityMaster cityMaster = manageTransaction.find(CityMaster.class, modelM.getCityMaster().getCityId());
					StateMaster stateMaster = manageTransaction.find(StateMaster.class, modelM.getStateMaster().getStateId());
					if (master == null) {
						master = new MerchantMaster();
						master.setActiveFlag(1);
						master.setCityMaster(cityMaster);
						master.setMerchantAddress1(modelM.getMerchantAddress1());
						master.setMerchantAddress2(modelM.getMerchantAddress2());
						master.setMerchantAddress3(modelM.getMerchantAddress3());
						master.setMerchantPhoneNumber(modelM.getMerchantPhoneNumber());
						master.setMerchantId(modelM.getMerchantId());
						master.setMerchantLogo(FilesUtil.getProperty("ipaddress")+CommonTasks.toBase64(modelM.getMerchantLogo(), 0));
						master.setMerchantName(modelM.getMerchantName());
						master.setMerchantPin(modelM.getMerchantPin());
						master.setStateMaster(stateMaster);
						master.setTimestamp(new Date());

					} else {
						master.setActiveFlag(1);
						master.setCityMaster(cityMaster);
						master.setMerchantAddress1(modelM.getMerchantAddress1());
						master.setMerchantAddress2(modelM.getMerchantAddress2());
						master.setMerchantAddress3(modelM.getMerchantAddress3());
						master.setMerchantPhoneNumber(modelM.getMerchantPhoneNumber());
						master.setMerchantId(modelM.getMerchantId());
						master.setMerchantLogo(FilesUtil.getProperty("ipaddress")+CommonTasks.toBase64(modelM.getMerchantLogo(), 0));
						master.setMerchantName(modelM.getMerchantName());
						master.setMerchantPin(modelM.getMerchantPin());
						master.setStateMaster(stateMaster);
						master.setTimestamp(new Date());
					}

					manageTransaction.saveObject(master);
					boolean status = CommonTasks.SendAPKDownLinkToPhone(master.getMerchantPhoneNumber(), master.getId());

					if (status) {
						result = gson.toJson(new GeneralResponse(
								Constants.TRUE, Constants.REQUEST_COMPLETED,
								master.getId()));
					} else {
						result = gson.toJson(new GeneralResponse(
								Constants.FALSE, Constants.OTP_FAIL, null));
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
}
package com.reva.axix.services;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reva.axix.data.DeviceRegisterData;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.jpa.PosDevice;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;

/**
 * Servlet implementation class SaveDeviceRegistration
 */
@WebServlet("/saveDeviceRegistration")
public class SaveDeviceRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveDeviceRegistration() {
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
			DeviceRegisterData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, DeviceRegisterData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					String uniqueKey = modelM.getImei() + modelM.getMacAddr();
					String querySrting = "SELECT d FROM PosDevice d where d.uniqueKey = ?1";
					Query query = manageTransaction.createQuery(PosDevice.class, querySrting);
					query.setParameter(1, uniqueKey);
					PosDevice posDevice = null;
					try {
						posDevice = (PosDevice) query.getSingleResult();
					} catch (Exception e) {
						e.printStackTrace();
						posDevice = null;
					}
					if (posDevice != null) {
						if (!(posDevice.getPushToken() != null && posDevice.getPushToken().trim().length() != 0)) {
							posDevice.setPushToken(modelM.getPushToken());
							posDevice.setRegisteredTimestamp(new Date());
						}
					} else {
						posDevice = new PosDevice();
						posDevice.setActiveStatus(1);
						posDevice.setImei(modelM.getImei());
						posDevice.setMacAddr(modelM.getMacAddr());
						posDevice.setUniqueKey(uniqueKey);
						posDevice.setMake(modelM.getMake());
						posDevice.setPushToken(modelM.getPushToken());
						posDevice.setRegisteredTimestamp(new Date());
					}
					manageTransaction.saveObject(posDevice);

					result = gson.toJson(new GeneralResponse(Constants.TRUE,
							Constants.REQUEST_COMPLETED, posDevice.getDeviceId()));
				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, 0));
				} finally {
					manageTransaction.close();
				}
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_INVAILD_MERCHANT_DATA, null));
			}
		} else {
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERROR_INCOMPLETE_DATA, 0));
		}
		response.getWriter().write(result);
	}
}

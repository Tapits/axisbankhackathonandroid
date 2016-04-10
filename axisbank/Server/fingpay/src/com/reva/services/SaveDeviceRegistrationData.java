package com.reva.services;

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
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.DeviceRegistrationData;
import com.reva.data.GeneralResponse;
import com.reva.jpa.MerchantMaster;
import com.reva.jpa.PhoneNumberOtp;
import com.reva.jpa.PosDevice;
import com.reva.jpa.PosMerchantMap;
import com.reva.jpa.PosMerchantMapPK;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class SaveDeviceRegistrationData
 */
@WebServlet("/saveDeviceRegistrationData")
public class SaveDeviceRegistrationData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveDeviceRegistrationData() {
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
		String dataSent = "";

		/*
		 * Reader reader = request.getReader(); int ch; while ((ch =
		 * reader.read()) != -1) { dataSent += (char) ch; }
		 */

		dataSent = request.getReader().readLine();

		if (CommonTasks.check(dataSent)) {
			DeviceRegistrationData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, DeviceRegistrationData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				if (SecurityCheck.checkDeviceRegistrationData(modelM)) {
					ManageTransaction manageTransaction = new ManageTransaction();
					boolean OTPStatus = verifyOTP(manageTransaction, modelM.getPhoneNumber(), modelM.getOtp());
					// SimpleDateFormat format = new
					// SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					if (OTPStatus) {
						try {
							MerchantMaster merchantMaster = getMerchantDetails(manageTransaction, modelM.getPhoneNumber());
							if (merchantMaster != null) {
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
								int deviceId = posDevice.getDeviceId();

								PosMerchantMapPK pk = new PosMerchantMapPK();
								pk.setDeviceId(deviceId);
								pk.setMerchantId(merchantMaster.getId());

								PosMerchantMap merchantMap = manageTransaction.find(PosMerchantMap.class, pk);
								if (merchantMap == null) {
									merchantMap = new PosMerchantMap();
									merchantMap.setId(pk);
									merchantMap.setMerchantMaster(merchantMaster);
									merchantMap.setPosDevice(posDevice);
									merchantMap.setStatusDate(new Date());
									manageTransaction.saveObject(merchantMap);
								}

								result = gson.toJson(new GeneralResponse(
										Constants.TRUE,
										Constants.REQUEST_COMPLETED, deviceId));
							} else {
								result = gson.toJson(new GeneralResponse(
										Constants.FALSE,
										Constants.ERROR_INVAILD_MERCHANT_PHONE,
										null));
							}

						} catch (Exception e) {
							e.printStackTrace();
							result = gson.toJson(new GeneralResponse(
									Constants.FALSE,
									Constants.ERRORS_EXCEPTION_IN_SERVER, 0));
						} finally {
							manageTransaction.close();
						}
					} else {
						result = gson.toJson(new GeneralResponse(
								Constants.FALSE, Constants.ERROR_WRONG_OTP,
								Constants.ERROR_WRONG_OTP_STATUS, null));

					}
				} else {
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERROR_INVAILD_MERCHANT_DATA, null));
				}
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_INCOMPLETE_DATA, 0));
			}
		}
		response.getWriter().write(result);
	}

	private MerchantMaster getMerchantDetails(ManageTransaction manageTransaction, String phoneNumber) {
		MerchantMaster master = null;
		String mString = "SELECT m FROM MerchantMaster m where m.merchantPhoneNumber = ?1";
		Query query = manageTransaction.createQuery(MerchantMaster.class, mString);
		query.setParameter(1,phoneNumber);
		try{
		master = (MerchantMaster) query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return master;
	}

	private boolean verifyOTP(ManageTransaction manageTransaction,String phoneNumber, int otp) {
		PhoneNumberOtp numberOtp = manageTransaction.find(PhoneNumberOtp.class,phoneNumber);
		if (numberOtp != null && numberOtp.getOtp() == otp) {
			return true;
		}
		return false;
	}
}

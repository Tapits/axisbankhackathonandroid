package com.reva.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
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
import com.reva.data.GeneralResponse;
import com.reva.data.TransactionInitiateData;
import com.reva.data.TransactionInitiateResponce;
import com.reva.jpa.MerchantMaster;
import com.reva.jpa.PosDevice;
import com.reva.jpa.TransactionDetail;
import com.reva.jpa.UserTransactionAmountLimit;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.Encryption;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class TransactionInitiate
 */
@WebServlet("/transactionInitiate")
public class TransactionInitiate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransactionInitiate() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long start = new Date().getTime();
		SecurityCheck.disableCache(response);
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new GsonBuilder().serializeNulls().create();
//		HttpSession session = SecurityCheck.getSession(request);
		if (SecurityCheck.checkSession(SecurityCheck.getSession(request))) {
			String dataSent = request.getReader().readLine();
			TransactionInitiateData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, TransactionInitiateData.class);
//				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					String customerMobileNumber = Encryption.decrypt(modelM.getCustomerMobileNumber());
					String amount = Encryption.decrypt(modelM.getAmount());
					boolean amountLimitStatus = getamountLimitStatus(manageTransaction, customerMobileNumber, amount);
					if(amountLimitStatus){
					long currentTime = modelM.getCurrentTime();
					String customerFingerPrint = URLDecoder.decode(Encryption.decrypt(modelM.getCustomerFingerPrint()));
					int customerPin = Integer.parseInt(Encryption.decrypt(modelM.getCustomerPin()));
					String datachecksum = modelM.getDatachecksum();
					String deviceChecksum = modelM.getDeviceChecksum();
					int deviceId = modelM.getDeviceId();
					int merchantId = modelM.getMerchantId();
					String merchantTxnId = modelM.getMerchantTxnId();
					boolean retryFlag = modelM.isRetryFlag();
					double latitude = modelM.getLatitude();
					double longitude = modelM.getLongitude();
					
					PosDevice posDevice = manageTransaction.find(PosDevice.class, deviceId);
					MerchantMaster merchantMaster = manageTransaction.find(MerchantMaster.class, merchantId);
					if(posDevice != null && merchantMaster != null){
					
					String serverDeviceChecksum = SecurityCheck.SHA512(deviceId + merchantId + posDevice.getImei() + posDevice.getMacAddr() + currentTime);
					String serverDatachecksum = SecurityCheck.SHA512(merchantTxnId + deviceId + merchantId + customerMobileNumber  + customerPin + amount + customerFingerPrint + currentTime);
//					String datachecksum = SHA512(s + id + Globals.merchantMasterData.getId() + mobNum + pin + amount + base64 + d.getTime());
//					String checksum = SHA512(id + Globals.merchantMasterData.getId() + imei + Utils.getUniqueDeviceId(context) + d.getTime());
					if(serverDatachecksum.equals(datachecksum) && serverDeviceChecksum.equals(deviceChecksum)){
						
						TransactionDetail detail = new TransactionDetail();
						detail.setAmount(new BigDecimal(amount));
						detail.setCustomerPin(customerPin);
						detail.setFingerPrint(CommonTasks.toBase64(customerFingerPrint, deviceId));
						detail.setLatitude(latitude);
						detail.setLongitude(longitude);
						detail.setMerchantMaster(merchantMaster);
						detail.setMerchantTxnId(merchantTxnId);
						detail.setMobileNumber(customerMobileNumber);
						detail.setPosDevice(posDevice);
						detail.setRequestIp(request.getRemoteAddr());
						detail.setRequestTimestamp(new Date());
						detail.setRetryFlag(retryFlag ? 1 : 0);
						detail.setStatusTimestamp(new Date(currentTime));
						
						//Send to Bank
						
						String bankTxnId = "Test1234";
						String statusCode = "112211";
						String statusMessage = "Done";
						
//						detail.setStatusTimestamp(new Date());
//						detail.setStatusMessage(statusMessage);
//						detail.setTxnStatus(txnStatus);
//						detail.setBankTxnId(bankTxnId);
						
						manageTransaction.saveObject(detail);
						
						result = gson.toJson(new GeneralResponse(Constants.TRUE, 
								Constants.REQUEST_COMPLETED, Constants.CORRECT_STATUS_CODE ,
								new TransactionInitiateResponce(Encryption.encrypt(statusCode), Encryption.encrypt(statusMessage), Encryption.encrypt(bankTxnId))));
					}else{
						result = gson.toJson(new GeneralResponse(Constants.FALSE,
								Constants.ERRORS_INVALID_TRANSACTION, Constants.INVALID_STATUS_CODE, null));
					}
					}else{
						result = gson.toJson(new GeneralResponse(Constants.FALSE,
								Constants.ERRORS_INVALID_TRANSACTION, Constants.INVALID_STATUS_CODE, null));
					}
					} else {
						result = gson.toJson(new GeneralResponse(Constants.FALSE, 
								Constants.ERROR_TRANSACTION_LIMIT, Constants.ERROR_TRANSACTION_LIMIT_STATUS_CODE, null));
					}
				} catch (UnknownHostException e) {
			           // check spelling of hostname
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
			    			Constants.ERRORS_TRANSACTION_EXCEPTION, Constants.SERVER_TIME_OUT_STATUS_CODE, null));
			    } catch (ConnectException e) {
			           // connection refused - is server down? Try another port.
			    	result = gson.toJson(new GeneralResponse(Constants.FALSE,
			    			Constants.ERRORS_TRANSACTION_EXCEPTION, Constants.SERVER_TIME_OUT_STATUS_CODE, null));
			    	e.printStackTrace();
			    } catch (NoRouteToHostException e) {
			           // The connect attempt timed out.  Try connecting through a proxy
			    	result = gson.toJson(new GeneralResponse(Constants.FALSE,
			    			Constants.ERRORS_TRANSACTION_EXCEPTION, Constants.SERVER_TIME_OUT_STATUS_CODE, null));
			    	e.printStackTrace();
			    }catch(SocketTimeoutException e){
			    	e.printStackTrace();
			    	result = gson.toJson(new GeneralResponse(Constants.FALSE,
			    			Constants.ERRORS_TRANSACTION_EXCEPTION, Constants.SERVER_TIME_OUT_STATUS_CODE, null));
			    	
			    }catch (IOException e) {
			    	// another error occurred
			    	e.printStackTrace();
			    	result = gson.toJson(new GeneralResponse(Constants.FALSE,
			    			Constants.ERRORS_TRANSACTION_EXCEPTION, Constants.SERVER_TIME_OUT_STATUS_CODE, null));
			    }catch (Exception e) {
			    	e.printStackTrace();
			    	result = gson.toJson(new GeneralResponse(Constants.FALSE,
			    			Constants.ERRORS_EXCEPTION_IN_SERVER, Constants.EXCEPTION_IN_SERVER_STATUS_CODE, null));
			    }finally{
					manageTransaction.close();
				}
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_INCOMPLETE_DATA, Constants.ERROR_INCOMPLETE_DATA_STATUS_CODE, null));
			}
		} else {
			result = gson.toJson(Constants.INVALID_SEESION);
			response.sendError(420);
		}
		long end = new Date().getTime();
		long total = end-start;
		System.out.println("Total Time : "+total);
		response.getWriter().write(result);
	}

	private boolean getamountLimitStatus(ManageTransaction manageTransaction, String customerMobileNumber, String amount) {
		BigDecimal bAmount = new BigDecimal(amount);
		String queryStatus = "SELECT sum(t.amount) FROM TransactionDetail t where t.mobileNumber = ?1 group by t.mobileNumber";
		Query query = manageTransaction.createQuery(TransactionDetail.class, queryStatus);
		query.setParameter(1, customerMobileNumber);
		BigDecimal totalSpend = null;
		try{
			totalSpend = (BigDecimal) query.getSingleResult();
			totalSpend = totalSpend.add(bAmount);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		UserTransactionAmountLimit amountLimit = manageTransaction.find(UserTransactionAmountLimit.class, customerMobileNumber);
		if(totalSpend != null && amountLimit != null){
		if(amountLimit.getTransactionLimit().compareTo(bAmount) >=1 && amountLimit.getDayLimit().compareTo(totalSpend) >=1){
			return true;
		 }
		}
		return false;
	}
}
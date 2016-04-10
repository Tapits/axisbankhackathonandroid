package com.reva.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.data.TransactionDetailsData;
import com.reva.jpa.MerchantMaster;
import com.reva.jpa.TransactionDetail;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class GetTransactionDetail
 */
@WebServlet("/getTransactionDetail")
public class GetTransactionDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTransactionDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SecurityCheck.disableCache(response);
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);
		String mId = request.getParameter("merchantId");

		String result = "";
		Gson gson = new Gson();
		if (SecurityCheck.checkSession(SecurityCheck.getSession(request))) {
			if (CommonTasks.check(mId)) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					int merchantId = Integer.parseInt(mId);
					MerchantMaster master = manageTransaction.find(MerchantMaster.class, merchantId);
					if (master != null) {
						List<TransactionDetail> datas = master.getTransactionDetails();
						if (datas.size() != 0) {
							List<TransactionDetailsData> list = new ArrayList<TransactionDetailsData>();
							for (TransactionDetail data : datas) {
								TransactionDetailsData detailsData = new TransactionDetailsData();
								detailsData.setAmount(String.valueOf(data.getAmount()));
								detailsData.setBankTxnId(data.getBankTxnId());
								detailsData.setCurrentTime(data.getRequestTimestamp().getTime());
								detailsData.setCustomerMobileNumber(data.getMobileNumber());
								detailsData.setDeviceId(data.getPosDevice().getDeviceId());
								detailsData.setLatitude(data.getLatitude());
								detailsData.setLongitude(data.getLongitude());
								detailsData.setMerchantId(data.getMerchantMaster().getId());
								detailsData.setMerchantTxnId(data.getMerchantTxnId());
								detailsData.setRetryFlag(data.getRetryFlag() == 0 ? false : true);
								detailsData.setStatusCode(String.valueOf(data.getTxnStatus()));
								detailsData.setStatusMessage(data.getStatusMessage());
								list.add(detailsData);
							}
							result = gson.toJson(new GeneralResponse(
									Constants.TRUE,
									Constants.REQUEST_COMPLETED, list));
						} else {
							result = gson.toJson(new GeneralResponse(
									Constants.FALSE,
									Constants.ERROR_NO_DATA_AVAILABLE, null));
						}
					} else {
						result = gson.toJson(new GeneralResponse(
								Constants.FALSE,
								Constants.ERROR_NO_DATA_AVAILABLE, null));
					}

				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, null));
				} finally {
					manageTransaction.close();
				}
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_INCOMPLETE_DATA, null));
			}
		} else {
			result = gson.toJson(Constants.INVALID_SEESION);
		}
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

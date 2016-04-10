package com.reva.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.data.PaymentGateWayData;
import com.reva.jpa.PaymentGateWay;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class GetPaymentGateWaysList
 */
@WebServlet("/getPaymentGateWaysList")
public class GetPaymentGateWaysList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPaymentGateWaysList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityCheck.disableCache(response);
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new Gson();
//		if (SecurityCheck.checkSession(SecurityCheck.getSession(request))) {
		ManageTransaction manageTransaction = new ManageTransaction();
		try {
			List<PaymentGateWayData> datas = new ArrayList<PaymentGateWayData>();
			String queryString = "SELECT p FROM PaymentGateWay p where p.activeFlag = 1";
			Query query = manageTransaction.createQuery(PaymentGateWay.class, queryString);
			List<PaymentGateWay> list = query.getResultList();
			if (list.size() != 0) {
				for (PaymentGateWay master : list) {
					PaymentGateWayData data = new PaymentGateWayData();
					data.setActiveFlag(master.getActiveFlag());
					data.setCommet(master.getCommet());
					data.setId(master.getId());
					data.setPaymentGateImage(master.getPaymentGateImage());
					data.setPaymentGateName(master.getPaymentGateName());
					data.setTimestamp(master.getTimestamp().getTime());
					datas.add(data);
				}
				result = gson.toJson(new GeneralResponse(Constants.TRUE,
						Constants.REQUEST_COMPLETED, datas));
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_NO_DATA_AVAILABLE, null));
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERRORS_EXCEPTION_IN_SERVER, null));
		} finally {
			manageTransaction.close();
		}
//		}else { 
//			result = gson.toJson(Constants.INVALID_SEESION);
//		}
		response.getWriter().write(result);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

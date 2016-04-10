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
import com.reva.data.CityMasterData;
import com.reva.data.GeneralResponse;
import com.reva.data.MerchantMasterData;
import com.reva.data.StateMasterData;
import com.reva.jpa.MerchantMaster;
import com.reva.utilities.Constants;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class GetMerchantList
 */
@WebServlet("/getMerchantList")
public class GetMerchantList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMerchantList() {
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
			List<MerchantMasterData> datas = new ArrayList<MerchantMasterData>();
			String queryString = "SELECT m FROM MerchantMaster m";
			Query query = manageTransaction.createQuery(MerchantMaster.class, queryString);
			List<MerchantMaster> list = query.getResultList();
			if (list.size() != 0) {
				for (MerchantMaster master : list) {
					MerchantMasterData data = new MerchantMasterData();
					data.setActiveFlag(master.getActiveFlag());
					data.setCityMaster(new CityMasterData());
					data.getCityMaster().setCity(master.getCityMaster().getCity());
					data.setId(master.getId());
					data.setMerchantAddress1(master.getMerchantAddress1());
					data.setMerchantAddress2(master.getMerchantAddress2());
					data.setMerchantAddress3(master.getMerchantAddress3());
					data.setMerchantId(master.getMerchantId());
					data.setMerchantName(master.getMerchantName());
					data.setMerchantPhoneNumber(master.getMerchantPhoneNumber());
					data.setStateMaster(new StateMasterData());
					data.getStateMaster().setState(master.getStateMaster().getState());
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

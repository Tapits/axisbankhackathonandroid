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
import com.reva.axix.data.PosOfflineFormData;
import com.reva.axix.jpa.MerchantRegisrationId;
import com.reva.axix.jpa.PosOfflineForm;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;

/**
 * Servlet implementation class SavePosOfflineFormData
 */
@WebServlet("/SavePosOfflineFormData")
public class SavePosOfflineFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SavePosOfflineFormData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			PosOfflineFormData modelM = null;
			try {
				modelM = gson.fromJson(dataSent,
						PosOfflineFormData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
                    String merchantRegisrationId = modelM.getMerchantRegisrationId();
					
					MerchantRegisrationId id = getMerchantRegisrationRefNumberObj(merchantRegisrationId, manageTransaction);
					if(id != null){
					
					String queryString = "SELECT m FROM PosOfflineForm m where m.merchantRegisrationId.merchantRegisrationRefNumber =?1";
					Query query = manageTransaction.createQuery(PosOfflineForm.class, queryString);
					query.setParameter(1, merchantRegisrationId);
					PosOfflineForm data = null;
					try {
						 data = (PosOfflineForm) query.getSingleResult();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(data != null){
						data = updateForm(data, modelM, manageTransaction);
					} else {
						data = new PosOfflineForm();
						
						data.setFormat(modelM.getFormat());
						data.setFrequency(modelM.getFrequency());
						data.setMerchantRegisrationId(id);
						data.setRemarks("");
						data.setStatement(modelM.getStatement());
						data.setTimestamp(new Date());
						data.setTypeOfMaterial(modelM.getTypeOfMaterial());
						data.setTypeOfPos(modelM.getTypeOfPos());
						data.setVas(modelM.getVas());
					}
					
					manageTransaction.saveObject(data);
					result = gson.toJson(new GeneralResponse(Constants.TRUE, Constants.REQUEST_COMPLETED, merchantRegisrationId));
					
					} else {
						result = gson.toJson(new GeneralResponse(Constants.FALSE, Constants.ERROR_INVAILD_ACTION, null));
					}
				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, 0));
				} finally {
					manageTransaction.close();
				}
			} else {
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_INCOMPLETE_DATA, 0));
			}
		}
		response.getWriter().write(result);
	}
	
	private MerchantRegisrationId getMerchantRegisrationRefNumberObj(String merchantRegisrationId, ManageTransaction manageTransaction) {
		String querytString = "SELECT m FROM MerchantRegisrationId m where m.merchantRegisrationRefNumber = ?1";
		Query query = manageTransaction.createQuery(MerchantRegisrationId.class, querytString);
		query.setParameter(1, merchantRegisrationId);
		MerchantRegisrationId id = null;
		try {
			id = (MerchantRegisrationId) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return id;
	}

	private PosOfflineForm updateForm(PosOfflineForm data, PosOfflineFormData modelM, ManageTransaction manageTransaction) {
				return data;
		// TODO Auto-generated method stub
		
	}
}

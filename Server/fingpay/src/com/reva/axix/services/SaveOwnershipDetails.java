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
import com.reva.axix.data.OwnershipDetailData;
import com.reva.axix.jpa.MerchantRegisrationId;
import com.reva.axix.jpa.OwnershipDetail;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;

/**
 * Servlet implementation class SaveOwnershipDetails
 */
@WebServlet("/saveOwnershipDetails")
public class SaveOwnershipDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveOwnershipDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
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
			OwnershipDetailData modelM = null;
			try {
				modelM = gson.fromJson(dataSent,
						OwnershipDetailData.class);
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
					
					String queryString = "SELECT m FROM OwnershipDetail m where m.merchantRegisrationId.merchantRegisrationRefNumber =?1";
					Query query = manageTransaction.createQuery(OwnershipDetail.class, queryString);
					query.setParameter(1, merchantRegisrationId);
					OwnershipDetail data = null;
					try {
						 data = (OwnershipDetail) query.getSingleResult();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(data != null){
						data = updateForm(data, modelM, manageTransaction);
					} else {
						data = new OwnershipDetail();
						
						data.setEmailId(modelM.getEmailId());
						data.setLandlineNumber(modelM.getLandlineNumber());
						data.setMerchantRegisrationId(id);
						data.setMobileNumber(modelM.getMobileNumber());
						data.setNameOfOwnerPartnerOrDirector(modelM.getNameOfOwnerPartnerOrDirector());
						data.setPan(modelM.getPan());
						data.setPAN_verificaton(modelM.getPAN_verificaton());
						data.setPhotoUploaded(modelM.getPhotoUploaded() == null ? "":CommonTasks.toBase64(modelM.getPhotoUploaded(), 0));
						data.setPinCode(modelM.getPinCode());
						data.setRemarks("");
						data.setTan(modelM.getTan());
						data.setTAN_verification(modelM.getTAN_verification());
						data.setTimestamp(new Date());
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

	private OwnershipDetail updateForm(OwnershipDetail data, OwnershipDetailData modelM, ManageTransaction manageTransaction) {
				return data;
		// TODO Auto-generated method stub
		
	}
}

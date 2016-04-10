package com.reva.axix.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
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
import com.reva.axix.data.DeclaractionFormData;
import com.reva.axix.data.MerchantRegisrationIdData;
import com.reva.axix.jpa.DeclaractionForm;
import com.reva.axix.jpa.MerchantRegisrationId;
import com.reva.axix.jpa.OnlinePosForm;
import com.reva.axix.jpa.OwnershipDetail;
import com.reva.connectionmanager.ManageTransaction;
import com.reva.data.GeneralResponse;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;

/**
 * Servlet implementation class SaveDeclaractionFormData
 */
@WebServlet("/saveDeclaractionFormData")
public class SaveDeclaractionFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveDeclaractionFormData() {
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
			DeclaractionFormData modelM = null;
			try {
				modelM = gson.fromJson(dataSent,
						DeclaractionFormData.class);
				System.out.println("dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
                    String merchantRegisrationId = modelM.getMerchantRegisrationRefNumber();
					
					MerchantRegisrationId id = getMerchantRegisrationRefNumberObj(merchantRegisrationId, manageTransaction);
					if(id != null){
					
					String queryString = "SELECT m FROM DeclaractionForm m where m.merchantRegisrationId.merchantRegisrationRefNumber =?1";
					Query query = manageTransaction.createQuery(DeclaractionForm.class, queryString);
					query.setParameter(1, merchantRegisrationId);
					DeclaractionForm data = null;
					try {
						 data = (DeclaractionForm) query.getSingleResult();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(data != null){
						data = updateForm(data, modelM, manageTransaction);
					} else {
						data = new DeclaractionForm();
						
						data.setData(data.getData());
						data.setMerchantRegisrationId(id);
						data.setRemarks("");
						data.setTimesatmp(new Date());
					}
					
					manageTransaction.saveObject(data);
					
					MerchantRegisrationIdData getMerchantRegisrationRefNumberData = merchantValidation(id, manageTransaction);
					
					result = gson.toJson(new GeneralResponse(Constants.TRUE, Constants.REQUEST_COMPLETED, getMerchantRegisrationRefNumberData));
					
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

	private DeclaractionForm updateForm(DeclaractionForm data, DeclaractionFormData modelM, ManageTransaction manageTransaction) {
				return data;
		// TODO Auto-generated method stub
		
	}
	
private MerchantRegisrationIdData merchantValidation(MerchantRegisrationId id, ManageTransaction manageTransaction) {
		
//		MerchantRegistrationFrom merchantRegistrationFrom = id.getMerchantRegistrationFroms().get(0);
	    OnlinePosForm onlinePosForm = getOnlinePosFormObj(manageTransaction, id.getMerchantRegisrationRefNumber());
		OwnershipDetail ownershipDetail = id.getOwnershipDetails().get(0);
		int aadharNumberStatus = validateAadhar(ownershipDetail.getAadharCard());
		int panStatus = validatePAN(ownershipDetail.getPan());
		int tanStatus = validateTAN(ownershipDetail.getTan());
		int domainNameStatus = validateDomainName(onlinePosForm != null ? onlinePosForm.getWibsiteUrl() : "");
		int keywordsVerification = validateKeyWords(onlinePosForm != null ? onlinePosForm.getWibsiteUrl() : "");
		int highandlowriskbusinesscheck = 1;
		int currentaccountbalancetransferverification = 1;
		int websiteVerifications = validateWebsite(onlinePosForm != null ? onlinePosForm.getWibsiteUrl() : "");
		int socialCheck = 1;
		
		
		int finalStatus = aadharNumberStatus+panStatus+tanStatus+domainNameStatus;
		
		MerchantRegisrationIdData data = new MerchantRegisrationIdData();
		data.setDomainNameValidation(domainNameStatus);
		data.setId(id.getMerchantRegistrationFroms().get(0).getId());
		data.setMerchantRegisrationRefNumber(id.getMerchantRegisrationRefNumber());
		data.setPanValidationStatus(panStatus);
		data.setRegistrationType(id.getRegistrationType());
		data.setRemarks(id.getRemarks());
		data.setSecurityCheck(finalStatus == 4 ?1:0);
		data.setStatus(finalStatus == 4 ?1:0);
		data.setTanValidationStatus(tanStatus);
		data.setAadharValidation(aadharNumberStatus);
		data.setEmailId(id.getMerchantRegistrationFroms().get(0).getEmailAddress());
		data.setMerchantName(id.getMerchantRegistrationFroms().get(0).getMarketingNameOrChargeName());
		data.setMerchantPhoneNumber(id.getMerchantRegistrationFroms().get(0).getMobileNumber());
		data.setUpdatedTimestamp(ownershipDetail.getTimestamp().getTime());
		data.setCurrentaccountbalancetransferverification(currentaccountbalancetransferverification);
		data.setHighandlowriskbusinesscheck(highandlowriskbusinesscheck);
		data.setKeywordsVerification(keywordsVerification);
		data.setWebsiteVerifications(websiteVerifications);
		data.setSocialCheck(socialCheck);
		
		return data;
	}

	private OnlinePosForm getOnlinePosFormObj(ManageTransaction manageTransaction,
		String merchantRegisrationRefNumber) {
		String querytString = "SELECT m FROM OnlinePosForm m where m.merchantRegisrationId.merchantRegisrationRefNumber = ?1";
		Query query = manageTransaction.createQuery(OnlinePosForm.class, querytString);
		query.setParameter(1, merchantRegisrationRefNumber);
		OnlinePosForm id = null;
		try {
			id = (OnlinePosForm) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return id;
}

	private int validateWebsite(String wibsiteUrl) {
		try{
			InetAddress inetAddress = InetAddress.getByName(wibsiteUrl);
		    System.out.println(inetAddress.getHostName());
		    System.out.println(inetAddress.getHostAddress());
				return 1;
			} catch(Exception e){
				e.printStackTrace();
				return 0;
			}
}

	private int validateKeyWords(String wibsiteUrl) {
		try{
			String[] keywords = { "online", "shopping", "pareshan" };

			String data = htmlContent(wibsiteUrl);
			int score = keywordsMatch(data, keywords);
			if (score == keywords.length){
				System.out.println("Keywords Present");
				return 1;
			}else{
				System.out.println("Number of Keywords not matching: "
						+ (keywords.length - score));
				return 0;
			}
			} catch(Exception e){
				e.printStackTrace();
				return 0;
			}
}

	private int validateDomainName(String wibsiteUrl) {
		// TODO Auto-generated method stub
		try{
		InetAddress inetAddress = InetAddress.getByName(wibsiteUrl);
	    System.out.println(inetAddress.getHostName());
	    System.out.println(inetAddress.getHostAddress());
			return 1;
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	private int validateTAN(String tan) {
		// TODO Auto-generated method stub
		return 1;
	}

	private int validatePAN(String pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int validateAadhar(String aadharCard) {
		// TODO Auto-generated method stub
		return 1;
	}
	
	private static int keywordsMatch(String data, String[] keywords){
		int score = 0;
		for (String keyword : keywords){
			if (data.toLowerCase().contains(keyword))
				score++;
		}
		return score;
	}
	
	private static String htmlContent(String url){
		
		BufferedReader in = null;
		StringBuilder response = new StringBuilder();
		 try {
	        URLConnection connection = new URL(url).openConnection();
	        in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) 
	            response.append(inputLine);
	        in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
        return response.toString();
	}
	
	public static void main(String[] args) throws UnknownHostException {
	    InetAddress inetAddress = InetAddress.getByName("www.google.co.in");
	    System.out.println(inetAddress.getHostName());
	    System.out.println(inetAddress.getHostAddress());
	}
}


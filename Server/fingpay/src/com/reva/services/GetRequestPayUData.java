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
import com.reva.data.Products;
import com.reva.data.RequestPayUData;
import com.reva.jpa.PayuPayment;
import com.reva.utilities.CommonTasks;
import com.reva.utilities.Constants;
import com.reva.utilities.FilesUtil;
import com.reva.utilities.SecurityCheck;

/**
 * Servlet implementation class GetRequestPayUData
 */
@WebServlet("/getRequestPayUData")
public class GetRequestPayUData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRequestPayUData() {
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
        /*SecurityCheck.disableCache(response);
	    
		response.setContentType(Constants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF_8);

		String result = "";
		Gson gson = new GsonBuilder().serializeNulls().create();
		String dataSent = "";
		String payURequest = "";

		
		 * Reader reader = request.getReader(); int ch; while ((ch =
		 * reader.read()) != -1) { dataSent += (char) ch; }
		 

		dataSent = request.getReader().readLine();

		if (CommonTasks.check(dataSent)) {
			RequestPayUData modelM = null;
			try {
				modelM = gson.fromJson(dataSent, RequestPayUData.class);
				System.out.println("GetRequestPayUData dataSent : " + dataSent);
			} catch (Exception e) {
				e.printStackTrace();
				result = gson.toJson(new GeneralResponse(Constants.FALSE,
						Constants.ERROR_PARSING_REQUEST_DATA, 0));
			}

			if (modelM != null && result.length() == 0) {
				ManageTransaction manageTransaction = new ManageTransaction();
				try {
					PayURequestData data = new PayURequestData();
					data.setCurrencyCode("INR");
					data.setCustomerIp(request.getLocalAddr());
					data.setDescription(modelM.getDescription());
					data.setMerchantPosId("145227");
					data.setNotifyUrl(FilesUtil.getProperty("payuresponse"));
					data.setProducts(modelM.getProducts());
					data.setTotalAmount(modelM.getTotalAmount());
					
					PayUData payUData = new PayUData();
					payUData.setContentType(Constants.CONTENT_TYPE_JSON);
					payUData.setPayURequestData(data);
					payUData.setPayUURL(FilesUtil.getProperty("pauURL"));
					int i = 0;
					payURequest = "https://secure.payu.com/api/v2_1/orders?"
							+ "customerIp=123.123.123.123&"
							+ "merchantPosId=145227&"
							+ "description=Order description&"
							+ "totalAmount=1000&"
							+ "currencyCode=PLN&";
							for(Products p:modelM.getProducts()){
							payURequest = payURequest+""
									+ "products["+i+"].name="+p.getName()+"&"
								    + "products["+i+"].unitPrice="+p.getUnitPrice()+"&"
								    + "products["+i+"].quantity="+p.getQuantity()+"&";
							i++;
								
							}
					payURequest = payURequest
							+"notifyUrl="+FilesUtil.getIntProperty("payuresponsehttp")
							+"&continueUrl="+FilesUtil.getIntProperty("payuresponsehttp")
							+"&OpenPayu-Signature=sender=145227;algorithm=SHA-256;signature=bc94a8026d6032b5e216be112a5fb7544e66e23e68d44b4283ff495bdb3983a8";
					
					PayuPayment payment = new PayuPayment();
					payment.setExtOrderIid("");
					payment.setRequest(payURequest);
					payment.setTimestamp(new Date());
					manageTransaction.saveObject(payment);
					
					result = gson.toJson(new GeneralResponse(Constants.TRUE, Constants.REQUEST_COMPLETED, 0, payURequest));
					System.out.println("GetRequestPayUData Result : "+result);
					

				} catch (Exception e) {
					e.printStackTrace();
					result = gson.toJson(new GeneralResponse(Constants.FALSE,
							Constants.ERRORS_EXCEPTION_IN_SERVER, null));
				}
			}

		} else {
			result = gson.toJson(new GeneralResponse(Constants.FALSE,
					Constants.ERROR_INCOMPLETE_DATA, null));
		}*/
		response.sendRedirect("https://secure.payu.com/api/v2_1/orders?customerIp=123.123.123.123&merchantPosId=145227&description=Order%20description&totalAmount=1000&currencyCode=PLN&products[0].name=Product%201&products[0].unitPrice=1000&products[0].quantity=1&notifyUrl=http://shop.url/notify&continueUrl=http://shop.url/continue&OpenPayu-Signature=sender=145227;algorithm=SHA-256;signature=bc94a8026d6032b5e216be112a5fb7544e66e23e68d44b4283ff495bdb3983a8");;
	}
}
	
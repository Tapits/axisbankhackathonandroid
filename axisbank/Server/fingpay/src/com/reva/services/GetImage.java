package com.reva.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.reva.utilities.FilesUtil;

/**
 * Servlet implementation class GetImage
 */
@WebServlet("/getImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetImage() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/" + FilesUtil.getProperty("ImageExtension"));
		String path = request.getParameter("imagename");
		byte[] imageBytes = getImageAsBytes(path);
		response.setContentLength(imageBytes.length);
		response.getOutputStream().write(imageBytes);
	}

	public byte[] getImageAsBytes(String path) {
		FileInputStream fileInputStream = null;
		File file = new File(FilesUtil.getProperty("ImagePath") + "" + path);
		byte[] bFile = new byte[(int) file.length()];
		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			return bFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}
}
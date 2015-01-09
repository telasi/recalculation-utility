package telasi.recutil.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is called when downloading *.gz and *.zip files.
 * @author <a href="dimakura@gmail.com">Dimitri Kurashvili</a>
 */
public class DownloadServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1890419274450080334L;
	private static final String PATH_START = "/home/dimitri/downloads/";

	public DownloadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		String pathEnd = request.getServletPath();
		int index = pathEnd.lastIndexOf('/');
		if (index != -1) {
			pathEnd = pathEnd.substring(index + 1);
		}
		String path = PATH_START + pathEnd;
		
		copyToOutputStream(path, out, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//
	}

	public static void copyToOutputStream(String path, OutputStream output, HttpServletResponse resp)
	throws IOException {
		File f = new File(path);
		resp.setHeader("Content-Length", String.valueOf(f.length()));
		InputStream input = new FileInputStream(f);
		byte[] buffer = new byte[4069];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		output.flush();
	}
}
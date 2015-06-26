package main.webaccess;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.logic.Logic;
import main.model.Item;

import com.google.gson.Gson;

/**
 * I305845
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	private Logic logic = new Logic();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("MainServlet | init()");

		logic.addPredefineItems();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("MainServlet | destroy()");
		// Removing all objects:
		logic.destroy();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");

		String json = "[]";

		if (type == null || id == null) {
			json = gson.toJson(logic.getAll());
		} else {
			Item requestedItem = logic.findItem(type, id);
			if (requestedItem != null) {
				json = gson.toJson(requestedItem);
			}
		}

		response.setContentType("application/json");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");

		boolean isSucceed = false;
		if (type != null && id != null) {
			isSucceed = logic.deleteItem(type, id);
		}

		response.setContentType("application/json");
		String json = String.format("{ \"result\" : %s}", isSucceed);
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}

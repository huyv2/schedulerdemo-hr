package hr.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.function.StatusService;

@WebServlet(urlPatterns = {"/status"})
public class StatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fromDate = req.getParameter("fromDate");
		String toDate = req.getParameter("toDate");
		String taskName = req.getParameter("taskName");
		RequestDispatcher rd = req.getRequestDispatcher("/view/status.jsp");
		req.setAttribute("statusList", StatusService.statusListInquiry(fromDate, toDate, taskName));
		rd.forward(req, resp);
	}
}

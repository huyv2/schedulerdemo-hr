package hr.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.config.Config;
import hr.constant.ParamConstant;
import hr.function.JobService;

@WebServlet(urlPatterns = {"/putting"})
public class PuttingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		RequestDispatcher rd;
		if (action.equals(ParamConstant.REDIRECT_LOADING)) {
			rd = req.getRequestDispatcher("/view/loading.jsp");
		} else if (action.equals(ParamConstant.REDIRECT_RESETTING)) {
			rd = req.getRequestDispatcher("/view/resetting.jsp");
		} else {
			rd = req.getRequestDispatcher("/view/putting.jsp");
			if (action.equals(ParamConstant.MODEL_INSERT)) {
				req.setAttribute("jobList", JobService.getJobList());
				req.setAttribute("type", ParamConstant.MODEL_INSERT);
			} else if (action.equals(ParamConstant.MODEL_UPDATE)) {
				req.setAttribute("jobList", JobService.getScheduleJobList());
				req.setAttribute("type", ParamConstant.MODEL_UPDATE);
			} else if (action.equals(ParamConstant.MODEL_DELETE)) {
				req.setAttribute("jobList", JobService.getScheduleJobList());
				req.setAttribute("type", ParamConstant.MODEL_DELETE);
			}
			req.setAttribute("intervalUnit", Config.getValue("intervalUnit"));
		}
		rd.forward(req, resp);
	}
}

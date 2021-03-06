package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Reaction;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Employee reactioned_id = (Employee)request.getSession().getAttribute("login_employee");

        long reaction_num = em.createNamedQuery("getAllReactions_num", Long.class)
                .setParameter("reactioned_id", reactioned_id)
                .setParameter("report", r)
                .getSingleResult();


        for(int i = 1; i <= 5; i++){
            long reaction_nums =  em.createNamedQuery("getAllReaction_num", Long.class)
                                   .setParameter("report", r)
                                   .setParameter("reaction_flag", i)
                                   .getSingleResult();
            if(i == 1){
                request.setAttribute("reaction_nums1", reaction_nums);
            }else if(i == 2){
                request.setAttribute("reaction_nums2", reaction_nums);
            }else if(i == 3){
                request.setAttribute("reaction_nums3", reaction_nums);
            }else if(i == 4){
                request.setAttribute("reaction_nums4", reaction_nums);
            }else if(i ==5){
                request.setAttribute("reaction_nums5", reaction_nums);
            }
        }

        if(reaction_num != 0){
            Reaction reaction = em.createNamedQuery("getReaction", Reaction.class)
                                  .setParameter("reactioned_id", reactioned_id)
                                  .setParameter("report", r)
                                  .getSingleResult();

            request.setAttribute("reaction", reaction);
        }

        em.close();

        request.setAttribute("report", r);

        request.setAttribute("_token", request.getSession().getId());

        request.setAttribute("reaction_num", reaction_num);

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
        }

}

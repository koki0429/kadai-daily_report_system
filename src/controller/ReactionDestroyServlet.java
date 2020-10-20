package controller;

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
 * Servlet implementation class ReactionDestroyServlet
 */
@WebServlet("/destroy")
public class ReactionDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReactionDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Reaction reactioned = em.createNamedQuery("getAllReactions", Reaction.class)
                        .setParameter("reactioned_id", request.getSession().getAttribute("login_employee"))
                        .setParameter("report", em.find(Report.class, Integer.parseInt(request.getParameter("id"))))
                        .setParameter("reaction_flag", Integer.parseInt(request.getParameter("reaction_flag")))
                        .getSingleResult();

        em.getTransaction().begin();
        em.remove(reactioned);
        em.getTransaction().commit();

        Employee reactioned_id = (Employee)request.getSession().getAttribute("login_employee");
        request.setAttribute("reactioned_id", reactioned_id);

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

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

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);

    }

}

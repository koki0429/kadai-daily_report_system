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
 * Servlet implementation class ReactionServlet
 */
@WebServlet("/reports/reaction")
public class ReactionCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReactionCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            EntityManager em = DBUtil.createEntityManager();

            Reaction re = new Reaction();

            re.setReport(em.find(Report.class, Integer.parseInt(request.getParameter("id"))));

            re.setReactioned_id((Employee)request.getSession().getAttribute("login_employee"));
            Employee reactioned_id = re.getReactioned_id();
            request.setAttribute("reactioned_id", reactioned_id);

            Integer reaction_flag = Integer.parseInt(request.getParameter("reaction_flag"));
            re.setReaction_flag(reaction_flag);

            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

                em.getTransaction().begin();
                em.persist(re);
                em.getTransaction().commit();

                if(reaction_flag == 1){
                    request.setAttribute("flush", "いいねしました！");
                }else if(reaction_flag == 2){
                    request.setAttribute("flush", "おめでとうしました！");
                }else if(reaction_flag == 3){
                    request.setAttribute("flush", "質問しました！");
                }else if(reaction_flag == 4){
                    request.setAttribute("flush", "悲しいしました！");
                }else{
                    request.setAttribute("flush", "嬉しいしました！");
                }

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

            Reaction reaction = em.createNamedQuery("getReaction", Reaction.class)
                                  .setParameter("report", r)
                                  .setParameter("reactioned_id", reactioned_id)
                                  .getSingleResult();

            em.close();

            request.setAttribute("report", r);

            request.setAttribute("_token", request.getSession().getId());

            request.setAttribute("reaction_num", reaction_num);

            request.setAttribute("reaction", reaction);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
            rd.forward(request, response);

    }
}

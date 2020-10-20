<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <c:if test="${flush != null}">
        <div id="flush_success">
            <c:out value="${flush}"></c:out>
        </div>
    </c:if>
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>リアクション</th>
                                 <c:choose>
                                     <c:when test="${sessionScope.login_employee.id == report.employee.id}">
                                       <td>
                                         <button>👍</button>:${reaction_nums1}&nbsp;
                                         <button>🎉</button>:${reaction_nums2}&nbsp;
                                         <button>❓</button>:${reaction_nums3}&nbsp;
                                         <button>😭</button>:${reaction_nums4}&nbsp;
                                         <button>🤗</button>:${reaction_nums5}&nbsp;
                                       </td>
                                     </c:when>
                                     <c:when test="${reaction_num == 0}">
                                      <td>
                                         <form style="display: inline" method="POST" action="<c:url value='/reports/reaction?reaction_flag=1&id=${report.id}' />"><button>👍</button>:${reaction_nums1}&nbsp;</form>
                                         <form style="display: inline" method="POST" action="<c:url value='/reports/reaction?reaction_flag=2&id=${report.id}' />"><button>🎉</button>:${reaction_nums2}&nbsp;</form>
                                         <form style="display: inline" method="POST" action="<c:url value='/reports/reaction?reaction_flag=3&id=${report.id}' />"><button>❓</button>:${reaction_nums3}&nbsp;</form>
                                         <form style="display: inline" method="POST" action="<c:url value='/reports/reaction?reaction_flag=4&id=${report.id}' />"><button>😭</button>:${reaction_nums4}&nbsp;</form>
                                         <form style="display: inline" method="POST" action="<c:url value='/reports/reaction?reaction_flag=5&id=${report.id}' />"><button>🤗</button>:${reaction_nums5}&nbsp;</form>
                                       </td>
                                     </c:when>
                                     <c:when test="${reaction_num != 0}">
                                         <td>
                                             <c:choose>
                                                  <c:when test="${reaction.reaction_flag == 1}">
                                                      <form style="display: inline" method="POST" action="<c:url value='/destroy?reaction_flag=1&id=${report.id}' />"><button>👍</button>&nbsp;:${reaction_nums1}&nbsp;</form>
                                                  </c:when>
                                                  <c:otherwise>
                                                      <button id="bot">👍</button>:${reaction_nums1}&nbsp;
                                                  </c:otherwise>
                                             </c:choose>
                                             <c:choose>
                                                 <c:when test="${reaction.reaction_flag == 2}">
                                                     <form style="display: inline" method="POST" action="<c:url value='/destroy?reaction_flag=2&id=${report.id}' />"><button>🎉</button>&nbsp;:${reaction_nums2}&nbsp;</form>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <button id="bot">🎉</button>:${reaction_nums2}&nbsp;
                                                 </c:otherwise>
                                             </c:choose>
                                             <c:choose>
                                                 <c:when test="${reaction.reaction_flag == 3}">
                                                     <form style="display: inline" method="POST" action="<c:url value='/destroy?reaction_flag=3&id=${report.id}' />"><button>❓</button>&nbsp;:${reaction_nums3}&nbsp;</form>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <button id="bot">❓</button>:${reaction_nums3}&nbsp;
                                                 </c:otherwise>
                                             </c:choose>
                                             <c:choose>
                                                 <c:when test="${reaction.reaction_flag == 4}">
                                                     <form style="display: inline" method="POST" action="<c:url value='/destroy?reaction_flag=4&id=${report.id}' />"><button>😭</button>&nbsp;:${reaction_nums4}&nbsp;</form>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <button id="bot">😭</button>:${reaction_nums4}&nbsp;
                                                 </c:otherwise>
                                             </c:choose>
                                             <c:choose>
                                                 <c:when test="${reaction.reaction_flag == 5}">
                                                     <form style="display: inline" method="POST" action="<c:url value='/destroy?reaction_flag=5&id=${report.id}' />"><button>🤗</button>&nbsp;:${reaction_nums5}&nbsp;</form>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <button id="bot">🤗</button>:${reaction_nums5}&nbsp;
                                                 </c:otherwise>
                                             </c:choose>
                                        </td>
                                     </c:when>
                                 </c:choose>
                            </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value='/reports/edit?id=${report.id}' />">この日報を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>
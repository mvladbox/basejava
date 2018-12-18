<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.vlad.app.model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.vlad.app.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.vlad.app.model.ContactType, ru.vlad.app.model.Contact>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.vlad.app.model.SectionType, ru.vlad.app.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.getKey()}"/>
        <jsp:useBean id="type" type="ru.vlad.app.model.SectionType"/>
        <h3>${type.title}</h3>
        <c:choose>
            <c:when test="${type == SectionType.OBJECTIVE || type == SectionType.PERSONAL}">
                <p>${sectionEntry.getValue().description}</p>
            </c:when>
            <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                <ul>
                    <c:forEach var="item" items="${sectionEntry.getValue().items}">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                <table>
                    <c:set var="org" value="${null}"/>
                    <c:forEach var="item" items="${sectionEntry.getValue().items}">
                        <c:if test="${item.getOrganization() != org}">
                            <c:set var="org" value="${item.getOrganization()}"/>
                            <tr><td colspan="2"><h4>${item.organization}</h4></td></tr>
                        </c:if>
                        <tr>
                            <td width="20%" style="vertical-align: top">${item.startDate} &#8212; ${(item.endDate == null) ? "Сейчас" : item.endDate}</td>
                            <td><b>${item.title}</b><br>${item.description}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

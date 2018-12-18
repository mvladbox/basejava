<%@ page import="ru.vlad.app.model.ContactType" %>
<%@ page import="ru.vlad.app.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type).value}"></dd>
            </dl>
        </c:forEach>
        <h3>${SectionType.OBJECTIVE.title}:</h3>
        <textarea name="${SectionType.OBJECTIVE.name()}" cols="100" rows="2">${OBJECTIVE}</textarea><br/>
        <h3>${SectionType.PERSONAL.title}:</h3>
        <textarea name="${SectionType.PERSONAL.name()}" cols="100" rows="2">${PERSONAL}</textarea><br/>
        <h3>${SectionType.ACHIEVEMENT.title}:</h3>
        <textarea name="${SectionType.ACHIEVEMENT.name()}" cols="100" rows="10">${ACHIEVEMENT}</textarea><br/>
        <h3>${SectionType.QUALIFICATIONS.title}:</h3>
        <textarea name="${SectionType.QUALIFICATIONS.name()}" cols="100" rows="10">${QUALIFICATIONS}</textarea><br/>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

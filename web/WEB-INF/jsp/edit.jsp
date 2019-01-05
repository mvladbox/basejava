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
    <script>
        let global_id = 1000;

        function removeRow(row_id) {
            const row = document.getElementById(row_id);
            row.parentNode.removeChild(row);
            return false;
        }

        function cloneRow(source_id, button) {
            const source = document.getElementById(source_id);
            const new_row = source.cloneNode(true);

            button.setAttribute('onclick', "return removeRow('" + source.getAttribute('id') + "');");
            button.querySelector('img').setAttribute('src', 'img/delete.png');

            new_row.setAttribute('id', global_id);
            new_row.querySelector('a').setAttribute('onclick', 'return cloneRow("' + global_id + '", this);');
            source.parentNode.insertBefore(new_row, source.nextSibling);

            global_id++;
            return false;
        }
    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <c:if test="${not empty error}">
        <h1 class="error">${error}</h1>
    </c:if>
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
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h3>${type.title}:</h3>
            <c:choose>
                <c:when test="${type == SectionType.OBJECTIVE || type == SectionType.PERSONAL}">
                    <textarea name="${type.name()}" cols="100" rows="2">${requestScope[type.name()]}</textarea><br/>
                </c:when>
                <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                    <textarea name="${type.name()}" cols="100" rows="10">${requestScope[type.name()]}</textarea><br/>
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                    <c:set var="organizationName" value="${type.name()}_organizationName"/>
                    <c:set var="organizationUrl" value="${type.name()}_organizationUrl"/>
                    <c:set var="startDate" value="${type.name()}_startDate"/>
                    <c:set var="endDate" value="${type.name()}_endDate"/>
                    <c:set var="title" value="${type.name()}_title"/>
                    <c:set var="desc" value="${type.name()}_desc"/>
                    <table>
                        <th>Период</th>
                        <th>Организация</th>
                        <th>Деятельность</th>
                        <th></th>
                    <c:forEach var="i" begin="0" end="${requestScope[title].size() - 1}" varStatus="loop">
                        <tr id="${type.name()}${loop.index}" style="vertical-align: top">
                            <td width="25%">
                                <dl>
                                    <dd><input type="text" name="${startDate}" value="<c:out value="${requestScope[startDate].get(i)}"/>" size="3" maxlength="7" placeholder="dd/yyyy"></dd>
                                    &nbsp;&#8212;
                                    <dd><input type="text" name="${endDate}" value="<c:out value="${requestScope[endDate].get(i)}"/>" size="3" maxlength="7" placeholder="dd/yyyy"></dd>
                                </dl>
                            </td>
                            <td>
                                <dl>
                                <dd><input type="text" name="${organizationName}" value="<c:out value="${requestScope[organizationName].get(i)}"/>"></dd>
                                <dd><input type="text" name="${organizationUrl}" value="<c:out value="${requestScope[organizationUrl].get(i)}"/>"></dd>
                                </dl>
                            </td>
                            <td>
                                <dl>
                                <dd><input type="text" name="${title}" size="63" value="<c:out value="${requestScope[title].get(i)}"/>"></dd>
                                <dd><textarea name="${desc}" cols="65" rows="3">${requestScope[desc][i]}</textarea></dd>
                                </dl>
                            </td>
                            <td style="vertical-align: middle">
                                <c:if test="${not loop.last}">
                                    <a onclick="return removeRow('${type.name()}${loop.index}');"><img src="img/delete.png"></a>
                                </c:if>
                                <c:if test="${loop.last}">
                                    <a id="${type.name()}new" onclick="return cloneRow('${type.name()}${loop.index}', this);"><img src="img/add.png"></a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </table>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ import="java.util.*,java.io.IOException" %>

<c:out value="${expr}" escapeXml="false" />
<c:url value="http://xxx">
    <c:param name="story" value="${storyId}" />
    <c:param name="seo" value="{seoId}" />
</c:url>
<c:if test="${expr}" >
    xxx
</c:if>
<c:choose>
    <c:when test="${expr}" >
        xxx
    </c:when>
    <c:otherwise>
        yyy
    </c:otherwise>
</c:choose>
<c:forEach items="xxx" var="i" begin="0" end="100" varStatus="status">
    ${status.begin}
    ${status.end}
    ${status.step}
    ${status.count}
    ${status.current}
    ${status.index}
    ${status.first}
    ${status.last}
</c:forEach>
<c:forTokens items="xxx" delims="," var="word">
    ${word}
</c:forTokens>
<c:redirect url="xx">
    <c:param name="action" value="view">
</c:redirect>
<c:import url="xx" context="/x" var="x" scope="request">
    <c:param name="y" value="x" />
</c:import>
<c:set var="vv" value="kk" />
<c:remove var="vv" scope="request" />
<fmt:message key="">
    <fmt:para value="${xx}" />
</fmt:message>
<fmt:setLocal value="" />
<fmt:bundle>
<fmt:setBundle>
<fmt:requestEncoding>
<fmt:timeZone>
<fmt:setTimeZone>
<fmt:formatDate>
<fmt:parseDate>
<fmt:formatNumber>
<fmt:parseNumber>
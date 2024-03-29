
<%@ page import="co.bugu.CalendarEvent" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'calendarEvent.label', default: 'CalendarEvent')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-calendarEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-calendarEvent" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list calendarEvent">
			
				<g:if test="${calendarEventInstance?.allDay}">
				<li class="fieldcontain">
					<span id="allDay-label" class="property-label"><g:message code="calendarEvent.allDay.label" default="All Day" /></span>
					
						<span class="property-value" aria-labelledby="allDay-label"><g:formatBoolean boolean="${calendarEventInstance?.allDay}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${calendarEventInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="calendarEvent.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${calendarEventInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${calendarEventInstance?.endDate}">
				<li class="fieldcontain">
					<span id="endDate-label" class="property-label"><g:message code="calendarEvent.endDate.label" default="End Date" /></span>
					
						<span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${calendarEventInstance?.endDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${calendarEventInstance?.startDate}">
				<li class="fieldcontain">
					<span id="startDate-label" class="property-label"><g:message code="calendarEvent.startDate.label" default="Start Date" /></span>
					
						<span class="property-value" aria-labelledby="startDate-label"><g:formatDate date="${calendarEventInstance?.startDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${calendarEventInstance?.id}" />
					<g:link class="edit" action="edit" id="${calendarEventInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

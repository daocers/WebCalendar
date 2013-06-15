<%@ page import="co.bugu.CalendarEvent" %>



<div class="fieldcontain ${hasErrors(bean: calendarEventInstance, field: 'allDay', 'error')} ">
	<label for="allDay">
		<g:message code="calendarEvent.allDay.label" default="All Day" />
		
	</label>
	<g:checkBox name="allDay" value="${calendarEventInstance?.allDay}" />
</div>

<div class="fieldcontain ${hasErrors(bean: calendarEventInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="calendarEvent.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${calendarEventInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: calendarEventInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="calendarEvent.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${calendarEventInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: calendarEventInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="calendarEvent.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${calendarEventInstance?.startDate}"  />
</div>


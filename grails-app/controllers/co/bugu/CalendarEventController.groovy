package co.bugu

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.deep.JSON

class CalendarEventController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [calendarEventInstanceList: CalendarEvent.list(params), calendarEventInstanceTotal: CalendarEvent.count()]
    }

    def create() {
        [calendarEventInstance: new CalendarEvent(params)]
    }

    def save() {
        def calendarEventInstance = new CalendarEvent(params)
        if (!calendarEventInstance.save(flush: true)) {
            render(view: "create", model: [calendarEventInstance: calendarEventInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), calendarEventInstance.id])
        redirect(action: "show", id: calendarEventInstance.id)
    }

    def show() {
        def calendarEventInstance = CalendarEvent.get(params.id)
        if (!calendarEventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), params.id])
            redirect(action: "list")
            return
        }

        [calendarEventInstance: calendarEventInstance]
    }

    def edit() {
        def calendarEventInstance = CalendarEvent.get(params.id)
        if (!calendarEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), params.id])
            redirect(action: "list")
            return
        }

        [calendarEventInstance: calendarEventInstance]
    }

    def update() {
        def calendarEventInstance = CalendarEvent.get(params.id)
        if (!calendarEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (calendarEventInstance.version > version) {
                calendarEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'calendarEvent.label', default: 'CalendarEvent')] as Object[],
                          "Another user has updated this CalendarEvent while you were editing")
                render(view: "edit", model: [calendarEventInstance: calendarEventInstance])
                return
            }
        }

        calendarEventInstance.properties = params

        if (!calendarEventInstance.save(flush: true)) {
            render(view: "edit", model: [calendarEventInstance: calendarEventInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), calendarEventInstance.id])
        redirect(action: "show", id: calendarEventInstance.id)
    }

    def delete() {
        def calendarEventInstance = CalendarEvent.get(params.id)
        if (!calendarEventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), params.id])
            redirect(action: "list")
            return
        }

        try {
            calendarEventInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'calendarEvent.label', default: 'CalendarEvent'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def listAsJson(){
        def fcal = Calendar.getInstance()

        if(params.start){
            fcal.setTime(new Date(Long.parseLong(params.start)))
        }

        def lcal = Calendar.getInstance()
        if (params.end){
            lcal.setTime(new Date(Long.parseLong(params.end)))
        }

        def listOfEvents = CalendarEvent.findAll("""from CalendarEvent  as
            ce where ce.startDate >: startDate AND ce.endDate<:endDate""",
                [startDate: fcal.getTime(), endDate: lcal.getTime()])

        def listOfJsEvents = []

        listOfEvents.each {event ->
            def jsEvent = [:]

            jsEvent.id = event.id
            jsEvent.title = event.description?.length()> 15?
                event.description?.substring(0,14) + "..." : event.description
            jsEvent.description = event.description
            jsEvent.start = event.startDate
            jsEvent.end = event.endDate
            jsEvent.showTime = true
            jsEvent.url = "show?id=${event.id}"
            jsEvent.className = "scheduled"
            jsEvent.allDay = event.allDay

            listOfJsEvents.add(jsEvent)
        }

        render listOfJsEvents as JSON
    }

    def deletedWithJson(){
        def resultAsJson = [result: "success", message:"The event has been deleted"]

        def calendarEventInstance = CalendarEvent.get(params.id)
        if (calendarEventInstance){
            try{
                calendarEventInstance.delete()

                flash.message = "CalendarEvent ${params.id} deleted"
                redirect(action: list)
            }catch (org.springframework.dao.DataIntegrityViolationException e){
                resultAsJson.result = "fail"
                resultAsJson.message = "Database error, failed to delete the event."
            }
        }  else{
            resultAsJson.result = "fail"
            resultAsJson.message = "CalendarEvent not found in database."
        }

        render resultAsJson as JSON
    }

    def listAsCalendar(){

    }
}

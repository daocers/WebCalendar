package co.bugu



import org.junit.*
import grails.test.mixin.*

@TestFor(CalendarEventController)
@Mock(CalendarEvent)
class CalendarEventControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/calendarEvent/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.calendarEventInstanceList.size() == 0
        assert model.calendarEventInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.calendarEventInstance != null
    }

    void testSave() {
        controller.save()

        assert model.calendarEventInstance != null
        assert view == '/calendarEvent/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/calendarEvent/show/1'
        assert controller.flash.message != null
        assert CalendarEvent.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/calendarEvent/list'


        populateValidParams(params)
        def calendarEvent = new CalendarEvent(params)

        assert calendarEvent.save() != null

        params.id = calendarEvent.id

        def model = controller.show()

        assert model.calendarEventInstance == calendarEvent
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/calendarEvent/list'


        populateValidParams(params)
        def calendarEvent = new CalendarEvent(params)

        assert calendarEvent.save() != null

        params.id = calendarEvent.id

        def model = controller.edit()

        assert model.calendarEventInstance == calendarEvent
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/calendarEvent/list'

        response.reset()


        populateValidParams(params)
        def calendarEvent = new CalendarEvent(params)

        assert calendarEvent.save() != null

        // test invalid parameters in update
        params.id = calendarEvent.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/calendarEvent/edit"
        assert model.calendarEventInstance != null

        calendarEvent.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/calendarEvent/show/$calendarEvent.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        calendarEvent.clearErrors()

        populateValidParams(params)
        params.id = calendarEvent.id
        params.version = -1
        controller.update()

        assert view == "/calendarEvent/edit"
        assert model.calendarEventInstance != null
        assert model.calendarEventInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/calendarEvent/list'

        response.reset()

        populateValidParams(params)
        def calendarEvent = new CalendarEvent(params)

        assert calendarEvent.save() != null
        assert CalendarEvent.count() == 1

        params.id = calendarEvent.id

        controller.delete()

        assert CalendarEvent.count() == 0
        assert CalendarEvent.get(calendarEvent.id) == null
        assert response.redirectedUrl == '/calendarEvent/list'
    }
}

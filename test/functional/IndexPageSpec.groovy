
import geb.*
import geb.spock.GebSpec

class IndexPageSpec extends GebSpec {

  def "data written to the session should be retrievable from the session"(){
    given:
      to AssignToSession, key: "lastname", val: "lucchesi"
    expect:
      at AssignToSession
    when:
      to DumpSession
    then:
      $("#lastname").text() == "lucchesi"
  }

  def "cookie session plugin should be compatible with webflows that access a database"(){
    when:
      to TestWebflow
    then:
      at TestWebflow
      $("#v1").text() == "flowStart" 

    and:
      next.click()
    then:
      at TestWebflow
      $("#v1").text() == "flowStart" 
      $("#v2").text() == "step1" 
      $("#v3").text() == ""
      $("#v4").text() == ""

    and:
      next.click()
    then:
      at TestWebflow
      $("#v1").text() == "flowStart" 
      $("#v2").text() == "step1" 
      $("#v3").text() == "step2"
      $("#v4").text() == ""
 
    and:
      next.click()
    then:
      at TestWebflow
      $("#flowScope").find("#v1").text() == "flowStart" 
      $("#flowScope").find("#v2").text() == "step1" 
      $("#flowScope").find("#v3").text() == "step2" 
      $("#flowScope").find("#v4").text() == "step3" 
      $("#dbEntries").find("#v1").text() == "flowStart" 
      $("#dbEntries").find("#v2").text() == "step1" 
      $("#dbEntries").find("#v3").text() == "step2" 
      $("#dbEntries").find("#v4").text() == "step3" 
  }

  def "cookie session plugin should work with the flash scope"(){
    when:
      to DumpFlash
    then:
      at DumpFlash
      $("#firstname").text() == null
    
    when:
      to AssignToFlash, key: "firstname", val: "ben"
    then:
      at AssignToFlash
      $("#key").text() == "firstname"
      $("#val").text() == "ben"

    when:
      to DumpFlash
    then:
      at DumpFlash
      $("#firstname").text() == "ben"
 
    when:
      to DumpFlash
    then:
      at DumpFlash
      $("#firstname").text() == null

  }

  def "the cookie session should support being invalidated"(){
    given:
      to AssignToSession, key: "lastname", val: "lucchesi"
      to AssignToSession, key: "firstname", val: "benjamin"
    expect:
      at AssignToSession

    when:
      to DumpSession
    then:
      $("#lastname").text() == "lucchesi"
      $("#firstname").text() == "benjamin"

    when:
      go "index/invalidateSession"
    and:
      to DumpSession
    then:
      $("#lastname").text() == null
      $("#firstname").text() == null

    when:
      to AssignToSession, key: "lastname", val: "lucchesi"
      to AssignToSession, key: "firstname", val: "benjamin"
      to DumpSession
    then: 
      $("#lastname").text() == "lucchesi"
      $("#firstname").text() == "benjamin"
  }

  def "the cookie session should expire when the max inactive interval is exceeded"(){
    given:
      go "index/configureSessionRepository?maxInactiveInterval=10000"
      to AssignToSession, key: "lastname", val: "lucchesi"
    expect:
      at AssignToSession
    
    when:
      to DumpSession
    then:
      $("#lastname").text() == "lucchesi"

    when:
     sleep(5000)
     to DumpSession
    then:
      $("#lastname").text() == "lucchesi"

    when:
     sleep(11000)
     to DumpSession
    then:
      $("#lastname").text() == null

    when:
      to AssignToSession, key: "lastname", val: "lucchesi"
     to DumpSession
    then:
      $("#lastname").text() == "lucchesi"
  }

  def "the cookie session should be compatible with spring-security"(){
    when:
      to Login
    then: 
      at Login
   
    when:
      username = "testuser"
      password = "password"
      submit.click()
      go "index/whoami"
    then:
      $("#username").text() == "testuser"
  }

  def "the cookie session should be set and sent with controller redirects"(){
    when:
      to RedirectTest

    then:
      at RedirectTarget
      flashMessage == "this is a flash message"
  }

  def "exceptions should only be stored as strings"(){
    when:
      to StoreLargeException
      sleep(2000)
      to DumpSession

    then:
      $("#lastError").text() == "exception from recursive method: 1900"
  }
}

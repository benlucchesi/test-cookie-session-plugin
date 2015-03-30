
import geb.spock.*
import pages.*
import org.junit.Test
import geb.Browser

class IndexPageSpec extends GebReportingSpec {

  def setup(){
    browser.baseUrl = "https://localhost:8443/test-cookie-session-plugin/"
  }

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
      go "index/configureSessionRepository?maxInactiveInterval=10"
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
      to WhoAmI
    then:
      username == "testuser"
  }

  def "the cookie session should be method compatible with Secured attributes"(){
    when:
      go "https://localhost:8443/test-cookie-session-plugin/logout"
    then:
      println "should be logged out..."

    when:
      to SecuredPage
    then: 
      at Login
   
    when:
      username = "testuser"
      password = "password"
      submit.click()
      to SecuredPage
    then:
      username == "testuser"
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

  def "detect Locale serialization problem after multiple refreshes"(){
   when:
      to  SecuredPage
    then: 
      at  Login
   
    when:
      username = "testuser"
      password = "password"
      submit.click()
      to SecuredPage
    then:
      username == "testuser"
    
    when: 
      to WhoAmI
    then:
      username == "testuser"
  }

  def "test reauthenticate security method"(){
    when: 
      to  Logout
      to  WhoAmI
    then:
      username != "testuser"

    when:
      to  Reauthenticate
      to  WhoAmI

    then:
      username == "testuser"
  }

  def "test to verify that session is created always when spring security is enabled"(){
    when: 
      to  Logout
      to  WhoAmI
    then:
      username != "testuser"

    when:
      to SessionExists 

    then:
      sessionExists == true

    when:
      to Login
      username = "testuser"
      password = "password"
      submit.click()
      to SessionExists
    then:
      sessionExists == true

    when:
      to Logout
      to SessionExists
    then:
      sessionExists == false
  }

  def "sendError should cause session to be written"(){
    when: 
      to  Logout
      to  WhoAmI
    then:
      username != "testuser"

    when:
      to SessionExists 

    then:
      sessionExists == true

    when:
      to Login
      username = "testuser"
      password = "password"
      submit.click()
      to SessionExists
    then:
      sessionExists == true

    when: "server send's error"
      to SendError1
    then:
      at SendError1 
   
    when: "verify that session exists"
      to SessionExists
    then:
      sessionExists == true

    when: "server send's an error with a message"
      to SendError2
    then:
      at SendError2

    when: "verify that session exists"
      to SessionExists
    then:
      sessionExists == true
  }
}

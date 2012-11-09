
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
      $("#1").text() == "flowStart" 

    and:
      next.click()
    then:
      at TestWebflow
      $("#1").text() == "flowStart" 
      $("#2").text() == "step1" 
      $("#3").text() == ""
      $("#4").text() == ""

    and:
      next.click()
    then:
      at TestWebflow
      $("#1").text() == "flowStart" 
      $("#2").text() == "step1" 
      $("#3").text() == "step2"
      $("#4").text() == ""
 
    and:
      next.click()
    then:
      at TestWebflow
      $("#flowScope").find("#1").text() == "flowStart" 
      $("#flowScope").find("#2").text() == "step1" 
      $("#flowScope").find("#3").text() == "step2" 
      $("#flowScope").find("#4").text() == "step3" 
      $("#dbEntries").find("#1").text() == "flowStart" 
      $("#dbEntries").find("#2").text() == "step1" 
      $("#dbEntries").find("#3").text() == "step2" 
      $("#dbEntries").find("#4").text() == "step3" 
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

  }

  def "the cookie session should expire when the max inactive interval is exceeded"(){

  }
}

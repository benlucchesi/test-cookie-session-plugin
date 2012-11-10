package test.cookie.plugin

import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.plugins.testing.*
import com.granicus.grails.plugins.cookiesession.*

//@TestFor(IndexController)
class CookieSessionRepositorySpec extends IntegrationSpec {

  def sessionRepository 
  def request
  def response
  def session

	def setup() {
    request = new GrailsMockHttpServletRequest()
    response = new GrailsMockHttpServletResponse()
    session = new SerializableSession() 
	}

	def cleanup() {
	}

	void "verify sessionRepository bean isn't null"() {
    expect:
      sessionRepository != null
	}

  void "session repository can save data to a httpservletresponse"(){
    when:
      session.setAttribute("k1","v1")
      session.setAttribute("k2","v2")
      session.setAttribute("k3","v3")
      sessionRepository.cookieCount = 5
      sessionRepository.maxCookieSize = 4096
      sessionRepository.cookieName = "testcookie"
      sessionRepository.saveSession(session,response)

    then:
      response.cookies.size() == 5
      response.cookies[0].name == "testcookie-0"
      response.cookies[1].name == "testcookie-1"
      response.cookies[2].name == "testcookie-2"
      response.cookies[3].name == "testcookie-3"
      response.cookies[4].name == "testcookie-4"
      response.cookies.each{
        println "${it.name} : ${it.value}"
      }

    when:
      request.cookies = response.cookies
      session = sessionRepository.restoreSession(request)

    then:
      session.k1 == "v1"
      session.k2 == "v2"
      session.k3 == "v3"
  }

  void "session repository should produce cookies that live only as long as the browser is open"(){
    when:
      sessionRepository.cookieCount = 1
      sessionRepository.maxCookieSize = 4096
      sessionRepository.maxInactiveInterval = -1
      sessionRepository.cookieName = "testcookie"
      sessionRepository.saveSession(session,response)

    then:
      response.cookies.size() == 1
      response.cookies[0].name == "testcookie-0"
      response.cookies[0].maxAge == -1
  }

  void "session repository should produce cookies that expire in the session timeout period"(){
    when:
      sessionRepository.cookieCount = 1
      sessionRepository.maxCookieSize = 4096
      sessionRepository.maxInactiveInterval = 10000
      sessionRepository.cookieName = "testcookie"
      sessionRepository.saveSession(session,response)

    then:
      response.cookies.size() == 1
      response.cookies[0].name == "testcookie-0"
      response.cookies[0].maxAge == 10
  }

  void "session repository can encrypt data stored in the session"(){

  }

  void "session repository works with large session"(){

  }

}

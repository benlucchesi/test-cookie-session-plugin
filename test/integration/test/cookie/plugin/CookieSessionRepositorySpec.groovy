package test.cookie.plugin

import spock.lang.*
import grails.plugin.spock.IntegrationSpec
import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*


import org.codehaus.groovy.grails.plugins.testing.*
import com.granicus.grails.plugins.cookiesession.*
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken as UPAT

class CookieSessionRepositorySpec extends IntegrationSpec {

  def sessionRepository 
  def request
  def response
  def session
  def springSecurityService 

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

  void "session exceeds max session size"(){
    when: "when the session exceeds the the max storable session size"
      session.setAttribute("key","ABCDEFGHIJ")
      sessionRepository.cookieCount = 1
      sessionRepository.maxCookieSize = 10
      sessionRepository.cookieName = "testcookie"
      sessionRepository.encryptCookie = false
      sessionRepository.saveSession(session,response)

    then:
      thrown(Exception)
  }

  void "session repository can encrypt data stored in the session"(){
    when: "the session repository encrypts a session"
      session.setAttribute("k1","v1")
      session.setAttribute("k2","v2")
      session.setAttribute("k3","v3")
      sessionRepository.cookieCount = 5
      sessionRepository.maxCookieSize = 4096
      sessionRepository.cookieName = "testcookie"
      sessionRepository.encryptCookie = true
      sessionRepository.saveSession(session,response)

    then: "the requisite number of cookie are created"
      response.cookies.size() == 5
      response.cookies[0].name == "testcookie-0"
      response.cookies[1].name == "testcookie-1"
      response.cookies[2].name == "testcookie-2"
      response.cookies[3].name == "testcookie-3"
      response.cookies[4].name == "testcookie-4"
      response.cookies.each{
        println "${it.name} : ${it.value}"
      }

    when: "the session is restored"
      request.cookies = response.cookies
      session = sessionRepository.restoreSession(request)
    then: "the values are recoverable from the session"
      session.k1 == "v1"
      session.k2 == "v2"
      session.k3 == "v3"
  }


  void "session repository can store an unmodifiable collection"(){
    when: "an unmodifiable collection is written to the session"
      List<String> strings = new ArrayList<String>();
      strings.add("string 1") 
      strings.add("string 2") 
      strings.add("string 3") 
      strings.add("string 4") 
      session.setAttribute("unmodifiable",Collections.unmodifiableList(strings))
      sessionRepository.cookieCount = 5
      sessionRepository.maxCookieSize = 4096
      sessionRepository.cookieName = "testcookie"
      sessionRepository.encryptCookie = true
      sessionRepository.saveSession(session,response)

    then: "the requisited number of cookie are created"
      response.cookies.size() == 5
      response.cookies[0].name == "testcookie-0"
      response.cookies[1].name == "testcookie-1"
      response.cookies[2].name == "testcookie-2"
      response.cookies[3].name == "testcookie-3"
      response.cookies[4].name == "testcookie-4"
      response.cookies.each{
        println "${it.name} : ${it.value}"
      }

    when: "the session is restored"
      request.cookies = response.cookies
      session = sessionRepository.restoreSession(request)
    then: "the values are recoverable from the session"
      session.unmodifiable.size() == 4
      session.unmodifiable[0] == "string 1"
      session.unmodifiable[1] == "string 2"
      session.unmodifiable[2] == "string 3"
      session.unmodifiable[3] == "string 4"
  }

  void "session repository can store and restore a username and password authentication token"(){
    setup:
      springSecurityService.reauthenticate "testuser"
      def auth = springSecurityService.authentication
      println auth
    when: "an unmodifiable collection is written to the session"
      session.setAttribute("unpat", springSecurityService.authentication)
      sessionRepository.cookieCount = 5
      sessionRepository.maxCookieSize = 4096
      sessionRepository.cookieName = "testcookie"
      sessionRepository.encryptCookie = true
      sessionRepository.saveSession(session,response)

    then: "the requisited number of cookie are created"
      response.cookies.size() == 5
      response.cookies[0].name == "testcookie-0"
      response.cookies[1].name == "testcookie-1"
      response.cookies[2].name == "testcookie-2"
      response.cookies[3].name == "testcookie-3"
      response.cookies[4].name == "testcookie-4"
      response.cookies.each{
        println "${it.name} : ${it.value}"
      }
    
    when: "the session is restored"
      request.cookies = response.cookies
      session = sessionRepository.restoreSession(request)
    then: "the values are recoverable from the session"
      session.unpat.name == "testuser"
  }


}

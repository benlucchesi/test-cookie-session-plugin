package test.cookie.plugin
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugins.springsecurity.Secured

class IndexController {

    def springSecurityService
    def sessionRepository

    def whoami(){
      if( springSecurityService.isLoggedIn() )
        render "<html><span id=\"username\">${springSecurityService.principal.username}</span></html>"
      else
        render "<html><span id=\"username\"></span></html>"
    }
  
    def assignToSession(){
      session."${params.key}" = params.val
      render model: [key: params.key, val: params.val], view: "assignToSession"
    }

    def dumpSession(){
      def s = session
      withFormat{
        html {
          render model: [ sessionData: s.attributeNames.toList().collectEntries{ [ it, s.getAttribute(it) ] } ], view: "dumpSession"
        }
        json {
          render s.attributeNames.toList().collect{ [(it):s.getAttribute(it)] } as JSON
        }
      }
    }

    def assignToFlash(){
      flash."${params.key}" = params.val
      // reusing the assignToSession view on purpose
      render model: [key: params.key, val: params.val], view: "assignToSession" 
    }

    def dumpFlash(){
      def f = flash
      withFormat{
        html {
          render model: [ flashData: f.keySet().toList().collectEntries{ [ it, f.get(it) ] } ], view: "dumpFlash"
        }
        json {
          render f as JSON
        }
      }
    }

    def redirectTest(){
      flash.message = "this is a flash message"
      redirect action: 'redirectTarget'
    }

    def redirectTarget(){
    }

    def testFlow = {
      flowStart{
        action{
          flow.value1 = "flowStart"
          FlowEntry fs = new FlowEntry()
          fs.message = "flowStart"
          fs.save()
        }
        on("success").to("step1")
      }
      step1{
        on("next"){
          flow.value2 = "step1"
          FlowEntry fs = new FlowEntry()
          fs.message = "step1"
          fs.save()
        }.to("step2")
        on("cancel").to("flowStart")
      }

      step2{
        on("next"){
          flow.value3 = "step2"
          FlowEntry fs = new FlowEntry()
          fs.message = "step2"
          fs.save(true)
        }.to("step3")
        on("back").to("step1")
        on("cancel").to("flowStart")
      }

      step3{
        on("next"){
          flow.value4 = "step3"
          FlowEntry fs = new FlowEntry()
          fs.message = "step3"
          fs.save()
          def entries = FlowEntry.list()
          [dbEntries: entries, flowScope: [flow.value1,flow.value2,flow.value3,flow.value4]] 
        }.to("flowComplete")
        on("back").to("step2")
        on("cancel").to("flowStart")
      }

      flowComplete{
      }
    }

    def invalidateSession(){
      session.invalidate()
      render status: 200
    }

    def configureSessionRepository(){
      if( params.maxInactiveInterval )
        sessionRepository.maxInactiveInterval = params.maxInactiveInterval as int
      if( params.cookieCount )
        sessionRepository.cookieCount = params.cookieCount as int
      if( params.maxCookieSize )
        sessionRepository.maxCookieSize = params.maxCookieSize as int
      if( params.cookieName )
        sessionRepository.cookieName = params.cookieName

      [ maxInactiveInterval: sessionRepository.maxInactiveInterval, 
        cookieCount: sessionRepository.cookieCount,
        maxCookieSize: sessionRepository.maxCookieSize,
        cookieName: sessionRepository.cookieName ]
    }

  def maxRecurseCount = 0 
  def recurseCount = 0
  private def recurse(){
    recurseCount++
    if( recurseCount == maxRecurseCount )
      throw new Exception("exception from recursive method: ${recurseCount}")
    else
      recurse()
  }
  def storeLargeException(){
    try{
      maxRecurseCount = 1900
      recurse()
    }
    catch( excp ){
      session.lastError = excp
    }

    def stackTrace = session.lastError.stackTrace.collect{ it.toString() }
    render text: "stored exception: ${stackTrace}"
  }

  def throwException(){
    throw new Exception("this is an exception")
  }

  def reauthenticate(){
    //springSecurityService.reauthenticate "testuser"
    println "Session before reauthenticate: ${session}"
    SpringSecurityUtils.reauthenticate "testuser", "password"
    println "Session after reauthenticate: ${session}"
    render "reauthenticated"
  }

  def assignSecurityContextNull(){
    session.SPRING_SECURITY_CONTEXT = "test"
    render text: "assigned null to SPRING_SECURITY_CONTEXT"
  }

  @Secured(['ROLE_USER'])
  def securedMethod(){
    if( springSecurityService.isLoggedIn() )
      render "<html><span id=\"username\">${springSecurityService.principal.username}</span></html>"
    else
      render "<html><span id=\"username\"></span></html>"
  }

  def sessionExists(){
    render text: "${request.getSession(false) != null}"
  }
}

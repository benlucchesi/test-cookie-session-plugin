class BootStrap {

    def init = { servletContext ->

      // create a user to login as

      def user = new test.cookie.plugin.User()
      user.username = "testuser"
      user.password = "password"
      user.enabled = true
      user.accountExpired = false
      user.accountLocked = false
      user.passwordExpired = false

      user.save(true)

      def userRole = new test.cookie.plugin.Role(authority: 'ROLE_USER').save(failOnError: true)

      test.cookie.plugin.UserRole.create(user, userRole).save(failOnError: true)

      //com.esotericsoftware.minlog.Log.TRACE()

      try{
        // configure sessionCookieConfig
        if( servletContext.majorVersion >= 3 ){
          
          servletContext.sessionCookieConfig.name = "testproperty"
          servletContext.sessionCookieConfig.secure = "false"
          servletContext.sessionCookieConfig.httpOnly = "false"
          servletContext.sessionCookieConfig.comment = "testcomment"
          servletContext.sessionCookieConfig.domain = "testdomain"
          servletContext.sessionCookieConfig.maxAge = 1000
          servletContext.sessionCookieConfig.path = "/testpath"
          
          println "sessionCookieConfig.name: ${servletContext.sessionCookieConfig.name}"
          println "sessionCookieConfig.secure: ${servletContext.sessionCookieConfig.secure}"
          println "sessionCookieConfig.httpOnly: ${servletContext.sessionCookieConfig.httpOnly}"
          println "sessionCookieConfig.comment: ${servletContext.sessionCookieConfig.comment}"
          println "sessionCookieConfig.domain: ${servletContext.sessionCookieConfig.domain}"
          println "sessionCookieConfig.maxAge: ${servletContext.sessionCookieConfig.maxAge}"
          println "sessionCookieConfig.path: ${servletContext.sessionCookieConfig.path}"

          servletContext.sessionCookieConfig.setName( "sugar" )
          servletContext.sessionCookieConfig.setSecure( true )
          servletContext.sessionCookieConfig.setHttpOnly( false )
          servletContext.sessionCookieConfig.setComment( null )
          servletContext.sessionCookieConfig.setDomain( null )
          servletContext.sessionCookieConfig.setMaxAge( 3600 )
          servletContext.sessionCookieConfig.setPath( "/" )
 
          println "sessionCookieConfig.name: ${servletContext.sessionCookieConfig.name}"
          println "sessionCookieConfig.secure: ${servletContext.sessionCookieConfig.secure}"
          println "sessionCookieConfig.httpOnly: ${servletContext.sessionCookieConfig.httpOnly}"
          println "sessionCookieConfig.comment: ${servletContext.sessionCookieConfig.comment}"
          println "sessionCookieConfig.domain: ${servletContext.sessionCookieConfig.domain}"
          println "sessionCookieConfig.maxAge: ${servletContext.sessionCookieConfig.maxAge}"
          println "sessionCookieConfig.path: ${servletContext.sessionCookieConfig.path}"
        }
      }
      catch( excp ){
        println excp
      }
    }

    def destroy = {
    }
}

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
    }

    def destroy = {
    }
}

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
    }
    def destroy = {
    }
}

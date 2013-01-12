import geb.*

class AssignToSession extends Page {
  static url = "index/assignToSession"
  static content = {
    key(wait:true) { $('#key').text() }
    val(wait:true) { $('#val').text() }
  }
}

class DumpSession extends Page {
  static at = { $('h1').text() == "Dump Session" }
  static url = "index/dumpSession"
  static content = {
    sessionData(wait:true) { $('span') }
  }
}

class StoreLargeException extends Page {
  static url = "index/storeLargeException"
}

class AssignToFlash extends Page {
  static url = "index/assignToFlash"
  static content = {
    key(wait:true) { $('#key').text() }
    val(wait:true) { $('#val').text() }
  }
}

class DumpFlash extends Page {
  static at = { $('h1').text() == "Dump Flash" }
  static url = "index/dumpFlash"
  static content = {
    sessionData(wait:true) { $('span') }
  }
}

class TestWebflow extends Page {
  static at = { $('h1').text() == "Flow Test" }
  static url = "index/test"
  static content = {
    next(required: false){ $('a',text:"Next") }
    previous(required: false){ $('a',text:"Previous") }
    cancel(required: false){ $('a',text:"Cancel") }
  }
}

class Login extends Page {
 static at = { $('title').text() == "Login Page" }
 static url = "login/auth"
 static content = {
    username { $('#username') }
    password { $('#password') }
    submit { $('#submit') }
 }
}

class RedirectTest extends Page{
  static url = "index/redirectTest"
}

class RedirectTarget extends Page{
  static at = { $('title').text() == "Redirect Target" }
  static url = "index/redirectTarget"
  static content = {
    flashMessage(wait:true) { $('#flashMessage').text() }
  }
}

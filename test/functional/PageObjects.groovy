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

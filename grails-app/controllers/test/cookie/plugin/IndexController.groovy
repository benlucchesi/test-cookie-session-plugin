package test.cookie.plugin
import grails.converters.JSON

class IndexController {

    def springSecurityService

    def whoami(){
      render springSecurityService.principal.username
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
}

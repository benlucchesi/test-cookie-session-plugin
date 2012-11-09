
package test.cookie.plugin
import org.springframework.webflow.execution.FlowExecution
import org.springframework.webflow.conversation.ConversationManager
import org.springframework.webflow.execution.repository.snapshot.FlowExecutionSnapshotFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.webflow.execution.FlowExecutionKey
import org.springframework.webflow.core.collection.SharedAttributeMap
import org.springframework.webflow.context.ExternalContextHolder

class CustomFlowExecutionRepository extends org.springframework.webflow.execution.repository.impl.DefaultFlowExecutionRepository
{
  def grailsApplication

  CustomFlowExecutionRepository(ConversationManager conversationManager, FlowExecutionSnapshotFactory snapshotFactory){
    super(conversationManager,snapshotFactory)
  }

   FlowExecution  getFlowExecution(FlowExecutionKey key){
      println "----------start getFlowExecution: ${key.toString()} ----------------"
  
      def fe = null
      try{
        fe = super.getFlowExecution(key)
      }
      catch( excp ){
        println "getFlowExecution exception ${excp}"
      }
      println "getFlowExecution value: ${fe}"

      try{
        SharedAttributeMap sessionMap = ExternalContextHolder.getExternalContext().getSessionMap();
        println "conversation container from sessionMap: " + sessionMap.get("webflowConversationContainer")
       def cc =  sessionMap.get("webflowConversationContainer")
       println "conversations: "
       cc.conversations.each{ 
          println it
       }
      }
      catch( excp ){
        println "conversation container from sessionMap: " + excp
        throw excp
      }
      
      println "----------end getFlowExecution: ${key.toString()} ----------------"
      return fe
   }

  void putFlowExecution(FlowExecution flowExecution){
    println "----------start putFlowExecution ----------------"

    super.putFlowExecution(flowExecution)
    //println "Response: ${RequestContextHolder.currentRequestAttributes().currentResponse}"
    //RequestContextHolder.currentRequestAttributes().currentResponse.getTargetResponse().saveSession()
    
    println "----------end putFlowExecution ----------------"
  }
}

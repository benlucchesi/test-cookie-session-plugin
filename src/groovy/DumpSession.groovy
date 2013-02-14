
package testapp

import com.granicus.grails.plugins.cookiesession.SessionPersistenceListener;
import com.granicus.grails.plugins.cookiesession.SerializableSession;
import groovy.util.logging.Log4j

@Log4j
public class DumpSession implements SessionPersistenceListener{

    public void afterSessionRestored( SerializableSession session ){

    }

    public void beforeSessionSaved( SerializableSession session ){
      // loop through the attributes and condense each exception to just its exception

      log.info "*********************************"
      log.info "Session before save:"
      for( String key : session.getValueNames() ){
        log.info "${key} : ${session.getAttribute(key)}"
      }
      log.info "*********************************"
    }
}

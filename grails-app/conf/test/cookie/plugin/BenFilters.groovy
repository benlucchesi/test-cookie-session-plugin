package test.cookie.plugin

import javax.servlet.http.Cookie

class BenFilters {

    def filters = {
        all(uri: '/**') {
            before = {

            }
            after = { Map model ->
              //response.getTargetResponse().saveSession();
            }
            afterView = { Exception e ->

            }
        }
    }
}

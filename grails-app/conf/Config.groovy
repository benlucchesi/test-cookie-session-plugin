// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}
    
    //debug  'org.codehaus.groovy.grails.plugins'             // plugins

    //debug 'org.eclipse.jetty'
    //debug 'org.eclipse.jetty.server.AbstractHttpConnection'


    trace 'com.granicus.grails.plugins.cookiesession.SessionRepositoryResponseWrapper'
    warn 'com.granicus.grails.plugins.cookiesession.SessionRepositoryRequestWrapper'

    //trace 'com.granicus.grails.plugins.cookiesession.ExceptionCondenser'
    //trace 'testapp.DumpSession'
    error 'com.granicus.grails.plugins.cookiesession.SecurityContextSessionPersistenceListener'
    trace 'com.granicus.grails.plugins.cookiesession.CookieSessionFilter'
    trace 'org.springframework.webflow'
          
    trace 'com.granicus.grails.plugins.cookiesession.CookieSessionRepository'

    error 'com.granicus.grails.plugins.cookiesession.JavaSessionSerializer'
    error 'com.granicus.grails.plugins.cookiesession.KryoSessionSerializer'
    error 'com.granicus.grails.plugins.cookiesession.SimpleGrantedAuthoritySerializer'
    error 'com.granicus.grails.plugins.cookiesession.UserSerializer'
    error 'com.granicus.grails.plugins.cookiesession.GrailsUserSerializer'
    error 'com.granicus.grails.plugins.cookiesession.UsernamePasswordAuthenticationTokenSerializer'
    error 'com.granicus.grails.plugins.cookiesession.GrantedAuthorityImplSerializer'

    //trace 'grails.app.test.cookie.plugin.IndexController'
    //trace 'org.springframework.webflow'

    //trace 'org.springframework.security.web.context.SecurityContextPersistenceFilter',
    //      'org.springframework.security.web.context.HttpSessionSecurityContextRepository'

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.codehaus.groovy.grails.plugins',             // plugins
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// test enabled true/false
// test encryptcookie  true/false
// test cryptoalgorithm /w different algorithms
grails.plugin.cookiesession.enabled = true
grails.plugin.cookiesession.encryptcookie = true
grails.plugin.cookiesession.cryptoalgorithm = "Blowfish" // DESEde,DES,AES,Blowfish
grails.plugin.cookiesession.secret = "this is a secret"
//grails.plugin.cookiesession.secret = "123456789"
//grails.plugin.cookiesession.secret = [1,2,3,4,5,6,7,8,9]
grails.plugin.cookiesession.cookiecount = 10
grails.plugin.cookiesession.maxcookiesize = 4000


grails.plugin.cookiesession.usesessioncookieconfig = true
grails.plugin.cookiesession.sessiontimeout = 7200 
grails.plugin.cookiesession.cookiename = 'oatmeal1'
grails.plugin.cookiesession.setsecure = false


grails.plugin.cookiesession.condenseexceptions = true
grails.plugin.cookiesession.serializer = 'kryo'
grails.plugin.cookiesession.springsecuritycompatibility = true 

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.useSessionFixationPrevention = false


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'test.cookie.plugin.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'test.cookie.plugin.UserRole'
grails.plugin.springsecurity.authority.className = 'test.cookie.plugin.Role'
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll']
]


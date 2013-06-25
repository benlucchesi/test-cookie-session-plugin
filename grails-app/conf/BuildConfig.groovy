grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.work.dir = 'target'
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {

    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.20'

        compile "org.grails:grails-webflow:$grailsVersion"

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0" // 2.2

        //test  'org.codehaus.geb:geb-spock:0.7.2'
        test  'org.gebish:geb-spock:0.9.0-RC-1' // 2.2

        test  'org.seleniumhq.selenium:selenium-chrome-driver:2.25.0'
        //test  'org.seleniumhq.selenium:selenium-chrome-driver:2.25.0'
        test  'org.seleniumhq.selenium:selenium-support:2.25.0'
        test  'org.seleniumhq.selenium:selenium-firefox-driver:2.25.0'
           
        test('org.seleniumhq.selenium:selenium-htmlunit-driver:2.25.0'){ 
          excludes 'xml-apis'
        }

        runtime 'com.esotericsoftware.kryo:kryo:2.20'
        runtime 'org.objenesis:objenesis:1.3'
        runtime 'de.javakaffee:kryo-serializers:0.22'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.1.6"

        compile ':webflow:2.0.0', {
          exclude 'grails-webflow'
        }

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        //runtime ":cookie-session:2.0.7"
        compile ":spring-security-core:1.2.7.3"

        build ":tomcat:$grailsVersion"
        //build ':jetty:2.0.2'

        //runtime ":database-migration:1.3.2"

        compile ':cache:1.0.1'

        //test ":geb:0.7.2"
        test ":geb:0.9.0-RC-1" // 2.2
        
        test(":spock:0.7") {
          exclude "spock-grails-support"
        } 
        //test ":spock:0.6"
    }
}

// uncomment during development and  assign path to local source. remember to comment out the compile statement above!
grails.plugin.location.'grails-cookie-session' = "../grails-cookie-session-v2"

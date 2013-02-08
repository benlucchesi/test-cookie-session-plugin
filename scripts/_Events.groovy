
eventConfigureTomcat = {tomcat ->
  tomcat.connector.setAttribute("maxHttpHeaderSize",262144)
}

eventConfigureJetty = {jetty ->
  jetty.connectors[0].requestHeaderSize = 262144
  jetty.connectors[0].responseHeaderSize = 262144
}

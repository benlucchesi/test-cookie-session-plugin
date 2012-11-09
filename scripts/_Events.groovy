

eventConfigureTomcat = {tomcat ->
  tomcat.connector.setAttribute("maxHttpHeaderSize",262144)
}


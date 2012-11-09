<html>
  <h1>Dump Session</h1>
  <g:each in="${sessionData}">
    ${it.key}: <span id="${it.key}">${it.value}</span><br>
  </g:each>
</html>

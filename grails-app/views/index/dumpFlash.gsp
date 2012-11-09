<html>
  <h1>Dump Flash</h1>
  <g:each in="${flashData}">
    ${it.key}: <span id="${it.key}">${it.value}</span><br>
  </g:each>
</html>


<h1>Flow Test</h1>

<h2>Flow Complete</h2>
<div id="flowScope">
  <ul>
    <g:each in="${flowScope}" var="it" status="i">
      <li><span id="${i+1}">${it}</span></li>
    </g:each>
  </ul>
</div>

<h3>Flow Entries</h3>
<div id="dbEntries">
  <ul>
    <g:each in="${dbEntries}" var="it" status="i">
      <li><span id="${i+1}">${it.message}</span></li>
    </g:each>
  </ul>
</div>

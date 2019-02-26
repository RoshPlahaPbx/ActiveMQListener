# ActiveMQListener

<h3> Listens to ActiveMQ messages over SSL ONLY! This does not support other connection types </h3>

<p> The only hard-coded variable in this repo is the queue name that should be listened to.
Place a config.properties inside the resources folder
Inside that configuration file, please provide following: </p>

<ul>
  <li>username (This is the username for activeMQ instance)</li>
  <li> password (This is the password for the activeMQ instance) </li>
  <li> activeMQBrokerUri (This is the ui. The format should be: ssl://DNS_HOST_NAME:PORT</li>
</ul>


<h4> Currently, connections to ActiveMQ will not used any certs and by default we do not verify the host name. </h4>

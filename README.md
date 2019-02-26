# ActiveMQListener
Listens to ActiveMQ messages over SSL ONLY! This does not support other connection types.

The only hard-coded variable in this repo is the queue name to that shoudl be listened to. 
Inside the configuration file, please provide following
> Username (This is the username for activeMQ instance)
> Password (This is the password for the activeMQ instance)
> activeMQBrokerUri (This is the ui. The format should be: ssl://<DNS_HOST_NAME>:<PORT>


Currently, connections to ActiveMQ will not used any certs and by default we do not verify the host name.

package com.ssl.activemq;

import org.apache.activemq.ActiveMQSslConnectionFactory;

import javax.jms.*;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Properties;


public class QueueMessageConsumer extends ActiveMQSslConnectionFactory implements MessageListener  {

    private String activeMqBrokerUri;
    private String username;
    private String password;
    private String destinationName;
    private String clientNameID = "ROSH_CLIENT_NAME"; // this can be any ID btw

    public static void main(String[] args) {

        QueueMessageConsumer queueMsgListener =
                new QueueMessageConsumer( "POSTERXXL.ORDER.IN");

        try {
            queueMsgListener.run();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public QueueMessageConsumer(String destinationQueueName) {
        super();
        this.destinationName = destinationQueueName;
        setCredentials();
    }

    public void run() throws JMSException {

        ActiveMQSslConnectionFactory connFactory = new ActiveMQSslConnectionFactory(activeMqBrokerUri);
        connFactory.setUserName(username);
        connFactory.setPassword(password);

        Connection connection = connFactory.createConnection();

        connection.setClientID(clientNameID);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(destinationName);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);

        System.out.println(String.format("QueueMessageConsumer Waiting for messages at %s %s", destinationName,
                this.activeMqBrokerUri));
    }

    @Override
    protected TrustManager[] createTrustManager() throws Exception {
        System.out.println("Doing connection stuff");
        return new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

            public void checkClientTrusted(final X509Certificate[] chain, final String
                    authType)
                    throws java.security.cert.CertificateException {
            }

            public void checkServerTrusted(final X509Certificate[] chain, final String
                    authType)
                    throws java.security.cert.CertificateException {
            }
        } };
    }

    private  void setCredentials() {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        try {
            if (inputStream != null) {
                try {
                    prop.load(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            System.out.println("Properties file is not found! " + e);
        }

        this.username = prop.getProperty("username");
        this.password = prop.getProperty("password");
        this.activeMqBrokerUri = prop.getProperty("activeMQBrokerUri") + "?verifyHostName=false";

    }

    @Override
    public void onMessage(Message message) {
        String msg;
        try {
            msg = String.format("QueueMessageConsumer Received message [ %s ]", ((TextMessage) message).getText());
            Thread.sleep(10000);// sleep for 10 seconds
            System.out.println(msg);
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

}
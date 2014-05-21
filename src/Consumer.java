import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.jfree.ui.RefineryUtilities;

/**
 * @author Venu
 *
 */
public class Consumer implements Runnable,MessageListener {
	 JFree jfreeChart = new JFree();
	
	/**
	 * 
	 * Run method is called when thread is started.
	 *
	 */
	@Override
	public void run() {
		
		//
		jfreeChart.intializeJfreeChart();
		
		Boolean value2 = true;
		//window will be sized to fit perferred size and layout by its sub-components
		jfreeChart.pack();
		
	    RefineryUtilities.centerFrameOnScreen(jfreeChart);
	    //make it visible true
	    jfreeChart.setVisible(true);
	    
		try{
			System.out.println("Moniter started");
			String url= Constants.ACTIVEMQ_CONNECTION_PROTOCOL+"://"+Constants.ACTIVEMQ_HOST+":"+Constants.ACTIVEMQ_PORT;
			
			// Creating a ConnectionFactory
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			
			// Creating a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();
       
			//Creating a session
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			//creating a queue
			Destination destination = session.createQueue(Constants.TOPIC);
			
			// Create a MessageConsumer from the Session to the Queue
			MessageConsumer consumer = session.createConsumer(destination);
       
			// Listen for arriving messages
			while(value2){
				consumer.setMessageListener(this);
			}
			
			//closing session
			session.close();
			//closing connection
			connection.close();
		}
		//catching exception
		catch(Exception e){
			System.out.println("Exception occured");
			e.printStackTrace();
		}
	}//end of run method

	/**
	 * 
	 * This method is called when message is received.
	 *@param message
	 */
	@Override
	public void onMessage(Message message) {		
		if (message instanceof TextMessage) {
			//retirvies message
            TextMessage textMessage = (TextMessage) message;
            try {
            	//split message retrived 
            	String [] stringRead=textMessage.getText().split(" ");
            	//sends cpu information and memory information 
				jfreeChart.updateJfreeChart(Double.parseDouble(stringRead[0]), Double.parseDouble(stringRead[1]));
			} 
          //catching sigar exception
            catch (JMSException e) {
				System.out.println("Exception occured");
				e.printStackTrace();
			}
        	//catching any other exception
            catch(Exception e){
    			System.out.println("Exception occured");
    			e.printStackTrace();
    		}
        }		
	}//end of onMessage function
}//end of class

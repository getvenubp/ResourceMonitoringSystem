import org.apache.activemq.ActiveMQConnectionFactory;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author Venu
 *
 */


public class Producer implements Runnable {
	
	/**
	 * 
	 * Run method is called when thread is started.
	 *
	 */
	@Override
	public void run() {
		Boolean value = true;
		try{ 
			System.out.println("Started Daemon process. Sending Messages.........");
			
			String url = Constants.ACTIVEMQ_CONNECTION_PROTOCOL+"://"+Constants.ACTIVEMQ_HOST+":"+Constants.ACTIVEMQ_PORT;
			
			// Creating a ConnectionFactory
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			
			// Creating a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			//Creating a session
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

			//creating a queue
			Destination destination = session.createQueue(Constants.TOPIC);

			//Creating a MessageProducer from the Session to the Queue
			MessageProducer producer = session.createProducer(destination);
 			 
			Sigar sigar = new Sigar();
			CpuPerc cpuInfo =null;
			
			while(value){
				//get the CPU information 
				cpuInfo= sigar.getCpuPerc();
				//get the memory information in percentage 
				Mem memory = sigar.getMem();
				//combines all cpu information and converts into percentage and converts into string
				String  msg = cpuInfo.getCombined()*100+ " "+ memory.getUsedPercent();
				//creates message 
				TextMessage message = session.createTextMessage(msg);
				//send message
				producer.send(message);
				}
			
			//closing session 
			session.close();
			//closing connection
			connection.close();
        }
		//catching sigar exception
        catch (SigarException se){
        	System.out.println("SigarException occured");
        	se.printStackTrace();
		}
		//catching any other exception
		catch(Exception e){
			System.out.println("Exception occured");
			e.printStackTrace();
		}	
	}//end of run method
}//end of class

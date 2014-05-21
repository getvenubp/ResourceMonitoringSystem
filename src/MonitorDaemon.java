import javax.jms.JMSException;
/**
 * @author Venu
 *
 */

public class MonitorDaemon {

/**
 * This class needs to be executed inorder produce message. It instantiates producer class and creates thread for it.
 *
 *@param args
 */
    public static void main(String[] args) throws JMSException {
    	
    	Producer moniterDaemon = new Producer();
    	Thread daemonThread = new Thread(moniterDaemon);
    	//starts thread
    	daemonThread.start();
      	
      }
}
import javax.jms.JMSException;
/**
 *  
 * @author Venu
 *
 */

public class MoniterGUI {
	

/**
 * This class need to be executed in order to plot graph and consume message. It instantiates consumer class and creates thread for it.
 *@param args
 */
    public static void main(String[] args) throws JMSException {
    	
    	Consumer moniter = new Consumer();
    	Thread thread = new Thread(moniter);
    	//starts thread
    	thread.start();
    }
}
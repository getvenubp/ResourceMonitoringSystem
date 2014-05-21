import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * @author Venu
 *
 */

  public class JFree extends ApplicationFrame implements ActionListener {
	  //defines number of charts
      public static final int CHART_COUNT = 2;
      
      private TimeSeriesCollection[] chart;
      
      private double[] maxValue = new double[CHART_COUNT];

      public JFree() {
          super("Monitor System");   
      }
      
      public void intializeJfreeChart(){
    	  //CombinedDomainXYPlot is used to plot mutilple subcharts.
    	  CombinedDomainXYPlot plotChart = new CombinedDomainXYPlot(new DateAxis("Time"));
    	  chart = new TimeSeriesCollection[CHART_COUNT];
           
    	  //sets maximum value for memory
    	  maxValue[0] = 100.0;
          TimeSeries timeSeriesMemory = new TimeSeries("Memory", Millisecond.class);
          chart[0] = new TimeSeriesCollection(timeSeriesMemory);
          //declares Y-axis label for memory
          NumberAxis rangeAxisMemory = new NumberAxis("Memory Utilization");
          rangeAxisMemory.setAutoRangeIncludesZero(false);
          //plotchart for memory
          XYPlot subchartMemory = new XYPlot(
          	  chart[0], null, rangeAxisMemory, new StandardXYItemRenderer()
          );
          //sets background and gridlinepaint for memory
          subchartMemory.setBackgroundPaint(Color.black);
          subchartMemory.setDomainGridlinePaint(Color.green);
          subchartMemory.setRangeGridlinePaint(Color.green);
          //adds memory chart to combined 
          plotChart.add(subchartMemory);
          
         //sets maximum value for CPU
          maxValue[1] = 100.0;
          TimeSeries timeSeriesCPU = new TimeSeries("CPU", Millisecond.class);
          chart[1] = new TimeSeriesCollection(timeSeriesCPU);
          //declares Y-axis label for CPU
          NumberAxis rangeAxisCPU = new NumberAxis("CPU Utilization");
          rangeAxisCPU.setAutoRangeIncludesZero(false);
          //plotchart for CPU
          XYPlot subChartCPU = new XYPlot(
          	  chart[1], null, rangeAxisCPU, new StandardXYItemRenderer()
          );
          //sets background and gridlinepaint for CPU
          subChartCPU.setBackgroundPaint(Color.black);
          subChartCPU.setDomainGridlinePaint(Color.green);
          subChartCPU.setRangeGridlinePaint(Color.green);
          //combines two sub charts 
          plotChart.add(subChartCPU);
          
          JFreeChart chart = new JFreeChart("Performance Index", plotChart);
          
          chart.setBorderPaint(Color.black);
          chart.setBorderVisible(true);
          chart.setBackgroundPaint(Color.white);
          
        //sets background and gridlinepaint for plot chart
          plotChart.setBackgroundPaint(Color.black);
          plotChart.setDomainGridlinePaint(Color.green);
          plotChart.setRangeGridlinePaint(Color.green);
    
          ValueAxis axis = plotChart.getDomainAxis();
          axis.setAutoRange(true);
          axis.setFixedAutoRange(60000.0);  // 60 seconds
          
          //create JPanel
          JPanel contentPanel = new JPanel(new BorderLayout());
          //creates ChartPanel and seets it to JPanel
          ChartPanel chartPanel = new ChartPanel(chart);
          contentPanel.add(chartPanel);

          JPanel buttonPanel = new JPanel(new FlowLayout());
          
          contentPanel.add(buttonPanel, BorderLayout.SOUTH);
          //sets dimension for panel
          chartPanel.setPreferredSize(new java.awt.Dimension(660, 540));
          chartPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
          setContentPane(contentPanel);
      }
      
      
      /**
       * 
       * This method is called by consumer to update jfree chart.
       *@param cpuUtilPer
       *@param memUsedPer
       */
             
      public void updateJfreeChart(double cpuUtilPer, double memUsedPer)
      {	 
    	  //updates chart for memory
    	  chart[0].getSeries(0).addOrUpdate(new Millisecond(), memUsedPer);
    	  //updates chart for CPU
    	  chart[1].getSeries(0).addOrUpdate(new Millisecond(), cpuUtilPer); 
      }
     
     /**
  	 * 
  	 * This method is called When the action event occurs.
  	 *@param ActionEvent
  	 */
      public void actionPerformed(final ActionEvent ae)
      {
          if (ae.getActionCommand().equals("ADD_ALL")) {
              for (int i = 0; i < CHART_COUNT; i++) {
            	  maxValue[i] = maxValue[i] * (0.70 + 0.3 * Math.random());
                  chart[i].getSeries(0).addOrUpdate(new Millisecond(), maxValue[i]);       
              }
          }
      }
}

Libaries
1) activeMQ(activemq-all-5.5.1.jar)
2) jfree(jfreechart-1.0.14.jar,jcommon-1.0.17.jar)
3) sigar(sigar.jar), sl4j(sl4j-api-1.5.5.jar,sl4j-simple-1.5.5.jar)


It is a Java Eclipse project folder and can be easily imported into Eclipse and run.
1) First run MonitorDaemon.java
2) MoniterGUI.java

In order to run from command line,
1)Compile java program
javac -cp [path to library files] project3/src/*.java
ex: javac -cp '.:/home/venu/project3/lib/*' project3/src/*.java
2) run the class file
java -cp [path to library files] [filename]
ex : java -cp '.:/home/venu/project3/lib/*' MonitorDaemon
java -cp '.:/home/venu/project3/lib/*' MonitorGUI
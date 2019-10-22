import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//System.out.println("INFO GlassFish10.0 javax.enterprise.system.tools.admin _ThreadID 19 _ThreadName Thread-4 Server shutdown initiated".hashCode());
		// TODO Auto-generated method stub
//		Pattern p = Pattern.compile("FINEST GlassFish10.0 com.sun.org.apache.commons.modeler.Registry _ThreadID 10 _ThreadName Thread4 ClassName com.sun.org.apache.commons.modeler.Registry MethodName      loadDescriptors Finding descriptor (.*)");
//		Matcher m = p.matcher("FINEST GlassFish10.0 com.sun.org.apache.commons.modeler.Registry _ThreadID 10 _ThreadName Thread4 ClassName com.sun.org.apache.commons.modeler.Registry MethodName               loadDescriptors  Finding descriptor com/sun/enterprise");
//		System.out.println(m.matches());
		
		String x = "service:jmx:rmi:///jndi/rmi://ZIT-PC-Qosmio.Zankl-IT.local:8686/jmxrmi";
		System.out.println( 
				x.matches("service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi")
		);
		
		String p1 = "cassinit 12484 : (.*) (.*) (.*)";
		String p2 = "cassinit 12484 : (.*)";
		
		String p3 = "NET 12471 ALERT 3 3 LTU368 priklon 0.000000 SEPARATOR 0.000000 (.*)";
		
		Pattern pa = Pattern.compile(p3);
		String line = "cassinit 12484 : Konfiguration fir proced";
		String line1="NET 12471 ALERT 3 3 LTU368 priklon 0.000000 SEPARATOR 0.000000";
		Matcher ma = pa.matcher(line1);
		System.out.println(ma.matches());
//		
//		String s = " aaaa";
//		System.out.println(s.matches("^\\S+"));
		
		String s = "[#|2008-";
		System.out.println(s.matches("\\[#\\|2008.*"));
		
		
		
		String line2 = "[#|2008-03-27T18:38:54.449+0100|INFO|GlassFish10.0|javax.enterprise.system.core|_ThreadID=10;_ThreadName=Thread-4;|Init service : com.sun.enterprise.v3.services.impl.LogManagerService@1eb2c1b|#]";
		System.out.println(line2.matches("\\[#\\|2008.*\\|.*\\|.*\\|(.*)\\|.*\\|.*\\|#\\]"));
		System.out.println(line2.matches("\\[#\\|2008.*\\|.*\\|.*\\|\\(.*\\)\\|.*\\|.*\\|#\\]"));
		
		
		String toChange = "aa (.*) (.*)(.*)(.*)";
		System.out.println(toChange+" => "+toChange.replaceAll("(\\(\\.\\*\\))+", "(.*)"));
		
//		String line3 = "sysClasspath for meshcms is \\n/home/fabrizio/Programs/glassfish-v3-preview2-b01/./glassfish/bin/../modules/glassfish-10.0-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/./javaee-5.0.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jaxws-api-2.0.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jaxb-api-2.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/ejb-api-3.0.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/transaction-api-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/persistence-api-1.0b.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/mail-1.4.1ea-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/stax-api-1.0-2.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jsp-api-2.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/servlet-api-2.5.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jsr250-api-1.0.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/deployment-api-1.2.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/connector-api-1.5.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jsf-api-1.2_02.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jstl-api-1.2.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jaxrpc-api-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/security-api-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/activation-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jms-api-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/management-api-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/jsr181-api-1.0-MR1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/./web/gf-web-connector-10.0-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/collections-2.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/common-ee-util-10.0-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/ant-10.0-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/webtier-extensions-10.0-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/jsf-impl-1.2_02.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/ant-launcher-1.7.0.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/ant-1.7.0.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/webtier-10.0-SNAPSHOT.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/logging-api-1.0.4.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/modeler-1.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/beanutils-1.6.1-20070314.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/jasper-jsr199-9.1.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/glassfish/modules/web/digester-1.5.jar /home/fabrizio/Programs/glassfish-v3-preview2-b01/./glassfish/bin/../modules/glassfish-10.0-SNAPSHOT.jar\\n";
//		
//		Pattern rule = Pattern.compile("(.*)(.*)(.*) InstanceEvent (.*)Init");
//		String rs = rule.pattern();
//		System.out.println("HERE");
//		if ( line3.matches(rs)){
//			System.out.println("Ok");
//
//			Matcher m = rule.matcher(line3);
//			if(m.find()){
//				System.out.println("Matched");
//				int groupC = m.groupCount();
//				ArrayList<String> result = new ArrayList<String>(groupC);
//				for ( int i = 0; i < groupC; ++i ){
//					result.add(m.group(i+1));
//				}
//
//				for( String ss : result ){
//					System.out.println(ss);
//				}
//			} else {
//				System.out.println("Not Matched");
//			}
//		}
		
		String line3_ = "createObjectName Registering j2eeType WebModule,name //localhost/subsonic,J2EEApplication none,J2EEServer none for null";
		
		String r3 = "createObjectName Registering j2eeType WebModule,name //localhost/(.*),J2EEApplication none,J2EEServer none for null";
		String line3 = "createObjectName Registering j2eeType WebModule,name //localhost/subsonic,J2EEApplication none,J2EEServer none for null";
		
		Pattern pattern = Pattern.compile("createObjectName Registering j2eeType WebModule,name //localhost/(.*),J2EEApplication none,J2EEServer none for null");
		System.out.println("myRes "+line3.equals(line3_)+" "+r3.equals(pattern.pattern()));
		
		
		Matcher m3 = pattern.matcher(line3);
		printMatchRes(m3);
		
		//Pattern componentPattern = Pattern.compile("^\\[#\\|.{28}\\|[A-Z]+\\|sun\\-appserver9\\.1\\|(.*)\\|_");
		Pattern componentPattern = Pattern.compile("^\\[#\\|.{28}\\|[A-Z]+\\|sun\\-appserver9\\.1\\|.*\\|_.*;\\|(.*)");
		//Pattern componentPattern = Pattern.compile("\\[#\\|.{28}|[A-Z]+\\|sun\\-appserver9\\.1|(.*)\\|_.*\\|.*");
		String e = "[#|2008-04-23T20:05:38.626+0200|FINEST|sun-appserver9.1|javax.enterprise.system.container.web|_ThreadID=32;_ThreadName=httpSSLWorkerThread-8080-0;ClassName=com.sun.web.server.   J2EEInstanceListener;MethodName=instanceEvent;_RequestID=617bb348-18c6-46b4-82a1-f471b23c67c9;|*** InstanceEvent: afterService|#]";
		Matcher m = componentPattern.matcher(e);
		printMatchRes(m);
		
		Pattern c = Pattern.compile("^\\[#\\|.{28}\\|[A-Z]+\\|GlassFish10\\.0\\|(.*)\\|_");
		
		String myS = "[#|2008-03-27T18:38:54.449+0100|INFO|GlassFish10.0|javax.enterprise.system.core|_ThreadID=10;_ThreadName=Thread-4;|Init service : com.sun.enterprise.v3.   services.impl.LogManagerService@1eb2c1b|#]";
		m = c.matcher(myS);
		printMatchRes(m);
		
		
		String ns = " bodyText ' Resource bundle base name. This is the bundle's fully-qualified resource name, which has the same form as a fully-qualified class name, that   is, it uses \".\" as the package component separator and does not have any file type such as \".class\" or \".properties\" suffix. '";
		boolean result = ns.matches("^(.*) bodyText (.*)");
		System.out.println(result);
		
		String ll = "[#|2008-04-22T22:45:10.942+0200|INFO|sun-appserver9.1|javax.enterprise.system.core|_ThreadID=10;_ThreadName=main;|Starting Sun Java System Application      Server 9.1_01 (build b09d-fcs) ...|#]";
		//Pattern myP = Pattern.compile("\\[#\\|2008.*\\|.*\\|.*\\|(.*)\\|.*\\|.*\\|#\\]");
		Pattern myP = Pattern.compile("\\[#\\|2008.*\\|.*\\|.*\\|.*\\|.*\\|(.*)\\|#\\]");
		m = myP.matcher(ll);
		System.out.println("AA");
		printMatchRes(m);
	}

	
	public static void printMatchRes ( Matcher m ){
		if(m.find()){
			
			int groupC = m.groupCount();
			//System.out.println("Groups "+groupC);
			StringBuffer result = new StringBuffer();
			for ( int i = 0; i < groupC; ++i ){
				result.append(m.group(i+1));
			}
			
			System.out.println( "result "+ result.toString() ); 
		}
	}
}

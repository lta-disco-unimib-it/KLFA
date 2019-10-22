package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.ComponentsDefinitionFactory;

import conf.ClassPath;
import conf.EnvironmentalSetter;

public class PreProcessor {

	private static interface ComponentsScriptCreator {
		
		public void create ( Collection<Component> component, File dest ) throws IOException;
	}
	
	private static abstract class ComponentsScriptCreatorAspect implements ComponentsScriptCreator {
		
		public abstract String getAspectName(); 
		
		public void create(Collection<Component> component, File dest) throws IOException {
			BufferedWriter bw = new BufferedWriter( new FileWriter (dest) );
			
			
		}
		
	}
	
	
	private static class ComponentsScriptCreatorAspectDR extends ComponentsScriptCreatorAspect {

		public String getAspectName() {
			return "bctLA";
		}
		
	}
	
	private static void prepareDefinition(File destinationPath,
			String[] ioPointcuts, String[] interactionPointcuts)
			throws IOException {
		PrintWriter definition = new PrintWriter(new BufferedWriter(
				new FileWriter(
						new File(destinationPath, "definitionforlog.xml"))));
		// HEADER
		definition.println("<aspectwerkz>");
		definition.println("  <system id=\"Log\">");
		definition.println("    <package name=\"log\">");
		// INTERACTIONLOGGINGASPECT
		definition.println("      <aspect class=\"InteractionLoggingAspect\">");
		for (int i = 0; i < interactionPointcuts.length; i++) {
			definition.print("        <pointcut name=\"");
			definition.print("pc" + i);
			definition.print("\" expression=\"execution(");
			definition.print(interactionPointcuts[i]);
			definition.println(")\" /> ");
		}
		// BEFORE
		definition
				.print("        <advice name=\"interactionLogEntry\" type=\"before\" bind-to=\"");
		for (int i = 0; i < interactionPointcuts.length; i++) {
			definition.print("pc" + i);
			if (i == interactionPointcuts.length - 1)
				break;
			definition.print(" || ");
		}
		definition.println("\" />");
		// AFTER
		definition
				.print("        <advice name=\"interactionLogExit\" type=\"after\" bind-to=\"");
		for (int i = 0; i < interactionPointcuts.length; i++) {
			definition.print("pc" + i);
			if (i == interactionPointcuts.length - 1)
				break;
			definition.print(" || ");
		}
		definition.println("\" />");
		definition.println("      </aspect>");
		// IOLOGGINGASPECT
		definition.println("      <aspect class=\"IOLoggingAspect\">");
		for (int i = 0; i < ioPointcuts.length; i++) {
			definition.print("        <pointcut name=\"");
			definition.print("pc" + i);
			definition.print("\" expression=\"execution(");
			definition.print(ioPointcuts[i]);
			definition.println(")\" /> ");
		}
		// BEFORE
		definition
				.print("        <advice name=\"ioLogEntry\" type=\"before\" bind-to=\"");
		for (int i = 0; i < ioPointcuts.length; i++) {
			definition.print("pc" + i);
			if (i == ioPointcuts.length - 1)
				break;
			definition.print(" || ");
		}
		definition.println("\" />");
		// AFTER
		definition
				.print("        <advice name=\"ioLogExit\" type=\"after\" bind-to=\"");
		for (int i = 0; i < ioPointcuts.length; i++) {
			definition.print("pc" + i);
			if (i == ioPointcuts.length - 1)
				break;
			definition.print(" || ");
		}
		definition.println("\" />");
		definition.println("      </aspect>");
		// FOOTER
		definition.println("    </package>");
		definition.println("  </system>");
		definition.println("</aspectwerkz>");
		definition.close();
	}

	private static void prepareBatchFile(File destinationPath,
			File[] classpath, String mainClass, String[] argv)
			throws IOException {
		PrintWriter batchFile = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(destinationPath,
						"runloggedexecution.bat"))));
		batchFile
				.write("aspectwerkz -Daspectwerkz.definition.file=definitionforlog.xml ");
		batchFile.write("-cp \"");
		try {
			batchFile.write( EnvironmentalSetter.getBctHome()
//					+ new File(PreProcessor.class.getResource("/").toURI())
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// adding path to the XML parser to the classpath
		//batchFile.write(";\"" + ClassPath.getXmlParserPath()+"\"" );
		
		for (int i = 0; i < classpath.length; i++)
			batchFile.write(";" + classpath[i].getAbsolutePath() );
		batchFile.write("\" " + mainClass + " ");
		for (int i = 0; i < argv.length; i++)
			batchFile.write(argv[i] + " ");
		batchFile.println();
		batchFile.close();
		
		//Write a batch file depending on current OS
		batchFile = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(destinationPath,
						"runloggedexecution.sh"))));
		batchFile
				.write("aspectwerkz -Daspectwerkz.definition.file=definitionforlog.xml ");
		try {
			batchFile.write("-cp \""
					+ EnvironmentalSetter.getBctHome()
//					+ new File(PreProcessor.class.getResource("/").toURI())
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// adding path to the XML parser to the classpath
		//batchFile.write(";\"" + ClassPath.getXmlParserPath()+"\"" );
		
		for (int i = 0; i < classpath.length; i++)
			batchFile.write( File.pathSeparator + classpath[i].getAbsolutePath() );
		batchFile.write("\"");
		batchFile.write(" " + mainClass + " ");
		for (int i = 0; i < argv.length; i++)
			batchFile.write(argv[i] + " ");
		batchFile.println();
		batchFile.close();
		
	}

	private static void prepareBatchFileOffline(File destinationPath,
			File[] classpath, String mainClass, String[] argv,
			String testMethod, String[] argvControl) throws IOException {
		
		//
		//creation of weaveapplication.bat
		//
		
		PrintWriter batchFile = new PrintWriter(new BufferedWriter(
				new FileWriter(
						new File(destinationPath, "weaveapplication.bat"))));
		batchFile.write("xcopy");
		try {
			batchFile.write(" \""
					+ EnvironmentalSetter.getBctHomeNoJAR());
					//+ EnvironmentalSetter.getBctHomeNoJAR());
					//+ new File(PreProcessor.class.getResource("/").toURI()));
		} catch (Exception e) {
		}
		batchFile.write("\\" + "log" + "\" ");
		for (int i = 0; i < classpath.length; i++)
			batchFile.write("\"" + classpath[i].getAbsolutePath());
		batchFile.write("\\" + "log" + "\"");
		batchFile.println();
		batchFile.write("aspectwerkz -offline definitionforlog.xml -cp \"");

		// adding path to the XML parser to the classpath
		//batchFile.write("\"" + ClassPath.getXmlParserPath()+"\"" );

		for (int i = 0; i < classpath.length; i++){
			if ( i!= 0 )
				batchFile.write(File.pathSeparator);
			batchFile.write(classpath[i].getAbsolutePath());
		}
		batchFile.write("\" ");
		
		//TODO: remove next 2 lines
		for (int i = 0; i < classpath.length; i++)
			batchFile.write("\"" + classpath[i].getAbsolutePath() + "\"");
		
		batchFile.println();
		batchFile.close();
		
		//
		//creation of weaveapplication.sh
		//
		
		batchFile = new PrintWriter(new BufferedWriter(
				new FileWriter(
						new File(destinationPath, "weaveapplication.sh"))));
		batchFile.write("cp -r ");
		try {
			batchFile.write(" "
					+ EnvironmentalSetter.getBctHomeNoJAR());
					//+ EnvironmentalSetter.getBctHomeNoJAR());
					//+ new File(PreProcessor.class.getResource("/").toURI()));
		} catch (Exception e) {
		}
		batchFile.write( File.separator + "log" + File.separator + " ");
		for (int i = 0; i < classpath.length; i++)
			batchFile.write( classpath[i].getAbsolutePath() );
		batchFile.write( File.separator + "log" );
		batchFile.println();
		batchFile.write("aspectwerkz -offline definitionforlog.xml -cp \"");

		// adding path to the XML parser to the classpath
		//batchFile.write("\"" + ClassPath.getXmlParserPath()+"\"" );

		
		for (int i = 0; i < classpath.length; i++){
			if ( i != 0 )
				batchFile.write(  File.pathSeparator );
			batchFile.write(  classpath[i].getAbsolutePath() );
		}
		batchFile.write("\" ");
		
		//TODO: remove next 2 lines
		for (int i = 0; i < classpath.length; i++)
			batchFile.write( classpath[i].getAbsolutePath()+" " );
		
		batchFile.println();
		batchFile.close();
		
		//creation of runloggedexecution.bat
		PrintWriter batchFile2 = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(destinationPath,
						"runloggedexecution.bat"))));
		batchFile2.write("java ");
		batchFile2.write("-cp \"");
		try {
			batchFile2.write(
					EnvironmentalSetter.getBctHome()
					//+ new File(PreProcessor.class.getResource("/").toURI())
					);
		} catch (Exception e) {
		}
		for (int i = 0; i < classpath.length; i++)
			batchFile2.write(";" + classpath[i].getAbsolutePath() );
		batchFile2
				.write(";%ASPECTWERKZ_HOME%/lib/aspectwerkz-2.0.jar;%ASPECTWERKZ_HOME%/lib/aspectwerkz-core-2.0.jar\" ");
		batchFile2.write(" ");
		batchFile2.write("-Daspectwerkz.definition.file=definitionforlog.xml");
		batchFile2.write(" ");
		batchFile2.write(" " + mainClass + " ");
		batchFile2.close();

		//creation of runloggedexecution.sh
		batchFile2 = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(destinationPath,
						"runloggedexecution.sh"))));
		batchFile2.write("java ");
		try {
			batchFile2.write("-cp \""
					+ EnvironmentalSetter.getBctHome()
					//+ new File(PreProcessor.class.getResource("/").toURI())
					);
		} catch (Exception e) {
		}
		for (int i = 0; i < classpath.length; i++)
			batchFile2.write( File.pathSeparator + classpath[i].getAbsolutePath() );
		batchFile2
				.write( File.pathSeparator+"$ASPECTWERKZ_HOME/lib/aspectwerkz-2.0.jar"+File.pathSeparator+"$ASPECTWERKZ_HOME/lib/aspectwerkz-core-2.0.jar\"");
		batchFile2.write(" ");
		batchFile2.write("-Daspectwerkz.definition.file=definitionforlog.xml");
		batchFile2.write(" ");
		batchFile2.write(" " + mainClass + " ");
		batchFile2.close();
	}

	public static void prepareForLog(File destinationPath, File[] classpath,
			String[] ioPointcuts, String[] interactionPointcuts,
			String mainClass, String[] argv) throws IOException,
			IllegalArgumentException {
		if (!destinationPath.isDirectory()) {
			throw new IllegalArgumentException(
					"The file argument must specify a directory");
		} else {
			prepareDefinition(destinationPath, ioPointcuts,
					interactionPointcuts);
			prepareBatchFile(destinationPath, classpath, mainClass, argv);
		}
	}

	public static void prepareForLogOffline(File destinationPath,
			File[] classpath, String[] ioPointcuts,
			String[] interactionPointcuts, String mainClass, String[] argv,
			String offline, String[] argvControl) throws IOException,
			IllegalArgumentException {
		if (!destinationPath.isDirectory())
			throw new IllegalArgumentException(
					"The file argument must specify a directory");
		else {
			prepareDefinition(destinationPath, ioPointcuts,
					interactionPointcuts);
			prepareBatchFileOffline(destinationPath, classpath, mainClass,
					argv, offline, argvControl);
		}
	}

	public static void main(String argv[]) throws Exception {
		int count = 0;
		String framework = null;
		String phase = null;
		String componentsFileName = null;
		String outputFileName = null;
		for ( int i = 0; i < argv.length; ++i ){
			String arg = argv[i];
			if ( arg.equals("-type") ){
				framework = argv[++i];
			}
			else if ( arg.equals("-phase") ){
				phase = argv[++i];
			} else if ( arg.equals("-output") ){
				outputFileName = argv[++i];
			} else {
				if ( i < argv.length -1 ){
					printUsage();
					System.exit(-1);
				} else{
					componentsFileName = arg;
				}
			}
		}
		
		if ( framework == null || ! framework.equals("aspectwerkz") && ! framework.equals("probe") ){
			printUsage();
			System.exit(-1);
		}
		
		if ( phase == null || ! phase.equals("DataRecording") && ! phase.equals("RuntimeChecking") ){
			printUsage();
			System.exit(-1);
		}
				
		if ( outputFileName == null || componentsFileName == null ){
			printUsage();
			System.exit(-1);
		}
		
		
		List<Component> components = ComponentsDefinitionFactory.getComponents(new File(componentsFileName));
		
		
		ComponentsScriptCreator creator = getCreator(phase,framework);
		
		creator.create(components, new File(outputFileName) );
		
		
		
//		if (argv.length < 9) {
//			System.out
//					.println("Usage: java tools.PreProcessor -io <pointcuts...> -int <pointcuts...> -dest <path> -cp <classpath> class [args...]");
//			System.out.println();
//			System.out
//					.println("-io: denote the point where the application is monitored for IO Invariants.");
//			System.out
//					.println("-int: denote the point where the application is monitored for Interaction Invariants.");
//			System.out
//					.println("NOTE: the previous aguments must follow the aspectwerkz pointcut specification; pointcuts must be declared between \" and separated by semicolons");
//			System.out
//					.println("-dest: denote the directory where the invariants and the script will be placed.");
//			//sosituita la voce cp con TARGET_HOME
//			System.out
//					.println("-TARGET_HOME: specify the classpath of the application that must be monitored");
//			System.out
//					.println("class: is the class that must be called to start the execution");
//			System.out.println("[args...]: optional command line arguments");
//			System.out.println();
//			//sosituita la voce cp con TARGET_HOME
//			System.out
//					.println("Example: java tools.PreProcessor -io \"* it.ping.*(..)\";\"* it.pong.*(..)\" -int \"* it.ping.*(..)\";\"* it.pong.*(..)\" -dest c:/out/ -TARGET_HOME c:/bin/ MyClass");
//		} else {
//			if (!argv[0].equals("-io"))
//				throw new IllegalArgumentException("First option must be -io");
//			else if (!argv[2].equals("-int"))
//				throw new IllegalArgumentException("Second option must be -int");
//			else if (!argv[4].equals("-dest"))
//				throw new IllegalArgumentException(
//						"Third argument must be -dest");
//			//sosituita la voce cp con TARGET_HOME
//			else if (!argv[6].equals("-TARGET_HOME"))
//				throw new IllegalArgumentException(
//						"Fourth argument must be -TARGET_HOME");
//
//			EnvironmentalSetter.setConfigurationValues();
//			
//			String[] ioPointcuts = argv[1].split(";");
//			String[] interactionPointcuts = argv[3].split(";");
//			String[] stringClasspath = argv[7].split(File.pathSeparator);
//			ArrayList classpath = new ArrayList();
//			for (int i = 0; i < stringClasspath.length; i++)
//				classpath.add(new File(stringClasspath[i]));
//			File destination = new File(argv[5]);
//			ArrayList args = new ArrayList();
//			for (int i = 9; i < argv.length; i++)
//				args.add(argv[i]);
//			//aggiunta la possibilita di specificare il tipo di weaving
//			if (argv.length == 9
//					|| (argv.length == 10 && argv[8].equals("-online"))) {
//				//last argument is the main class of the program
//				String mainClass = argv[argv.length-1];
//				prepareForLog(destination, (File[]) classpath
//						.toArray(new File[0]), ioPointcuts,
//						interactionPointcuts, mainClass, (String[]) args
//								.toArray(new String[0]));
//			} else if (argv.length == 10 && argv[8].equals("-offline")) {
//				prepareForLogOffline(destination, (File[]) classpath
//						.toArray(new File[0]), ioPointcuts,
//						interactionPointcuts, argv[9], (String[]) args
//								.toArray(new String[0]), argv[9], argv);
//			} else {
//				System.out
//						.println("error: you must specify only -online or -offline options");
//				System.out.println();
//			}
//		}
	}

	private static ComponentsScriptCreator getCreator(String phase, String framework) {
		ComponentsScriptCreator creator;
		
		creator = new ComponentsScriptCreatorAspectDR();
		
		return creator;
		
	}

	private static void printUsage() {
		String msg = "Usage :\n";
		msg += PreProcessor.class.getName()+" options <componetsDefinitionFile>\n";
		msg += "-type <scriptType>: can be aspectwerkz or probe\n";
		msg += "-phase <phase>: can be DataRecording or RuntimeCheck\n";
		msg += "-output <outputFile>: output script file\n";
	}
}
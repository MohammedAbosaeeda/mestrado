package keso.editor;

public interface KGE {
	public static final String WORLD = "World";
	public static final String NODE = "Node";
	public static final String DOMAIN = "Domain";
	public static final String PUBLICDOMAIN = "PublicDomain";
	public static final String ISR = "ISR";
	public static final String ALARM = "Alarm";
	public static final String IMPORT = "Import";
	public static final String SERVICE = "Service";
	public static final String COUNTER = "Counter";
	public static final String APPMODE = "Appmode";
	public static final String EVENT = "Event";
	public static final String NETWORK = "Network";
	public static final String OSEKOS = "OsekOS";
	public static final String TASK = "Task";
	public static final String RESOURCE = "Resource";
	
	public static final String DIR_CONFIGURATION = "";
	public static final String GUI_CONFIGURATION_FILE = System.getProperty("user.home") + "/.keso_gui.cfg";
	public static final String FILTER_CONFIGURATION_FILE_1 = System.getProperty("user.home") + "/keso_filter.cfg";
	public static final String PROPERTY_CONFIGURATION_FILE_1 = System.getProperty("user.home") + "/keso_property.cfg";
	public static final String DATA_CONFIGURATION_FILE_1 = System.getProperty("user.home") + "/keso_data.cfg";
	
	public static final String FILTER_CONFIGURATION_FILE_2 = DIR_CONFIGURATION + "keso_filter.cfg";
	public static final String PROPERTY_CONFIGURATION_FILE_2 = DIR_CONFIGURATION + "keso_property.cfg";
	public static final String DATA_CONFIGURATION_FILE_2 = DIR_CONFIGURATION + "keso_data.cfg";
	
	
	public static final String PROPERTY_INTEGER = "Integer";
	public static final String PROPERTY_STRING = "String";
	public static final String PROPERTY_INTEGERARRAY = "IntegerArray";
	public static final String PROPERTY_COMPLEXBOOLEAN = "ComplexBoolean";
	public static final String PROPERTY_COMPLEXUNQUOTEDSTRING = "ComplexUnquotedString";
	public static final String PROPERTY_BOOLEAN = "Boolean";

	public static final String KESOEDITOR_VERSION = "V1";
	
	public static final String DEFAULT_WORKBENCH_DIRECTORY = System.getProperty("user.dir") + "/kesoworkbench";
	
	public static final String COMPILER_DIRECTORY = "ConfigReader";
}

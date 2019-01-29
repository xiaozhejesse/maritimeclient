package ihpc.maritime.transportation.system.common;


public class SystemConstant {
	
//	public static int NUM_VESSEL = 10;
	
	public static String waterwayKBDir = "D:/KB/gridmatched_files/";
//  public static String waterwayKBDir = "D:/KB/gridmatched_files_25vessels/";
//	public static String waterwayKBDir = "/home/KB/gridmatched_files_all/";
	
	public static String kdeKBDir = "D:/KB/kde/";
	
//	public static ArrayList<GridEle> gridList = ShapefileLoader
//			.loadPredictionGrid("D:/maritime/vmimgrid0025/grid.shp");
//	public static ArrayList<GridEle> gridList = ShapefileLoader
//			.loadPredictionGrid("/home/xiaoz/vmimgrid0025/grid.shp");
	
	public static int ROW = 225;
	public static int COL = 469;
	public static int GRIDNUM = 105525;
	
	public static int LINEAR = 1;
	public static int MLNN = 2;
	public static int PF = 3;
	
	public static String STPForecastingResultFileDir = "D:/forecastingresult/stp/";
	public static String LTPForecastingResultFileDir = "D:/forecastingresult/ltp/ltphs03/";
	public static String LTPForecastingResultFile = "/result_ltphs03.csv";
//	public static String forecastingResultFileDir = "/home/xiaoz/forecastingresult/";

	
// forecasting error codes
	public static String NORMAL = "0";
	public static String INPUTERROR = "1";
	public static String EXECUTIONERROR = "2";
	
// cluster configuration file
//	public static String configFile = "/home/xiaoz/tomcat/apache-tomcat-8.5.20/webapps/maritime/WEB-INF/classes/akkacluster.cfg.xml";
	public static String configFile = "C:/project-workplace/projects-code/myeclipse2015/ForecastVMIMAKKADispatcher/src/akkacluster.cfg.xml";

}

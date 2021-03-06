package co.com.sc.nexura.superfinanciera.action.generic.services.trm.client;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterfaceProxy;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;

public class TCRMClient
{

	/**
	 * Valid from and valid to TCRM date format
	 */
	private final static String _DATE_RESPONSE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
	
	/**
	 * TCRM query date format
	 */
	private final static String _DATE_QUERY_FORMAT = "yyyy-MM-dd";
	
	/**
	 * TCRM query value format
	 */
	private final static String _VALUE_QUERY_FORMAT = "#0.00";

	/**
	 * TCRM date to query
	 */
	private static String _DATE_TO_QUERY = null;
	
	/**
	 * Web Service end point
	 */
	private final static String _WEB_SERVICE_URL = "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL";

	/**
	 * Main web Service client test method
	 * 
	 * @param args
	 * @return 
	 * @throws RemoteException
	 * @throws ParseException 
	 */
	public static String main(String[] args) throws RemoteException, ParseException
	{
		
		if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
			_DATE_TO_QUERY = args[0];
        } else {
        	_DATE_TO_QUERY = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
				
		//
		// Simple date format declaration
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_DATE_RESPONSE_FORMAT);
		
		//
		// Decimal value format declaration
		DecimalFormat decimalFormat = new DecimalFormat(_VALUE_QUERY_FORMAT);
		
		TCRMServicesInterfaceProxy proxy = new TCRMServicesInterfaceProxy(_WEB_SERVICE_URL);
				
		//
		// Gets the TCRM value for the given date
		DateFormat formatter = new SimpleDateFormat(_DATE_QUERY_FORMAT);
		Date date = (Date)formatter.parse(_DATE_TO_QUERY); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		TcrmResponse tcrmResponse = proxy.queryTCRM(calendar);
		
		System.out.println("Fecha: " + date.toString());
		System.out.println("Identificador: " + tcrmResponse.getId());
		System.out.println("TCRM Valida desde: " + simpleDateFormat.format(tcrmResponse.getValidityFrom().getTime()));
		System.out.println("TCRM Valida hasta: " + simpleDateFormat.format(tcrmResponse.getValidityTo().getTime()));
		System.out.println("Valor: " + decimalFormat.format(tcrmResponse.getValue()));
		System.out.println("Observaciones: " + tcrmResponse.getMessage());
		
		return tcrmResponse.getValue().toString();
		
	}

}

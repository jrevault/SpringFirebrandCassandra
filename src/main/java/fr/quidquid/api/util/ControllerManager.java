package fr.quidquid.api.util;

import fr.quidquid.api.pojo.Msg;
import flexjson.JSONSerializer;
import org.exolab.castor.xml.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ControllerManager {

  public static final Logger log = LoggerFactory.getLogger( ControllerManager.class );

  public static final String RESPONSE_MSG = "RESPONSE_MSG";

  /**
   * Registers data to send to the client.<br />
   * It just creates a Result object with type set to TYPE_DATA.
   *
   * @param request the HttpServletRequest
   * @param msg     the data to send to the client
   */
  public static void rsp( HttpServletRequest request, Object msg ) {
    if ( msg instanceof Msg == false ) {
      request.setAttribute(RESPONSE_MSG, new Msg( msg ) );
    }
    else {
      request.setAttribute(RESPONSE_MSG, msg );
    }
  }

  /**
   * @param request
   * @param response
   */
  public static void sendResult( HttpServletRequest request, HttpServletResponse response ) {

    try {
      Msg msg = ( Msg ) request.getAttribute(RESPONSE_MSG);
      if ( msg == null ) {
        msg = new Msg( "" );
      }

      // Lets get the end of the request URI in order to dispatch correctly
      String suffix = "xml";
      int index = request.getRequestURI().lastIndexOf( "." );
      if ( index != - 1 ) {
        suffix = request.getRequestURI().substring( index + 1 );
      }
      if ( suffix.startsWith( "xml" ) ) {
        ControllerManager.sendXMLResult(response, msg);
      }
      else {
        ControllerManager.sendJSONResult( response, msg );
      }
    }
    catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * Sends the json Result to the front
   */
  public static void sendJSONResult( HttpServletResponse response, Msg result ) throws Exception {
    PrintWriter out = response.getWriter();
    response.setContentType( "application/json" );
    response.setHeader( "Pragma", "no-cache" );
    response.addHeader( "Cache-Control", "post-check=0, pre-check=0" );
    response.setHeader( "Cache-Control", "no-store, no-cache, must-revalidate" );
    response.setStatus( result.getCode() );

    JSONSerializer serializer = new JSONSerializer();

    String outString = serializer.serialize( result );
    if ( log.isTraceEnabled() ) {
      log.trace( "\n-------------------------------\nJSON output :\n" + outString );
    }

    out.print( outString );
    out.flush();

  }

  /**
   * Sends the xml Result to the front
   */
  public static void sendXMLResult( HttpServletResponse response, Msg result ) throws Exception {
    PrintWriter out = response.getWriter();
    response.setContentType( "application/xml" );
    response.setHeader( "Cache-Control", "no-cache" );
    response.setStatus( result.getCode() );

    StringWriter content = new StringWriter();
    final Marshaller marshaller = new Marshaller( content );
    marshaller.setValidation( false );
    marshaller.setEncoding( "UTF-8" );
    marshaller.setSuppressXSIType( true );
    marshaller.marshal( result );

    String outString = content.toString();
    if ( log.isTraceEnabled() ) {
      log.trace( "\n-------------------------------\nXML output :\n" + outString );
    }

    out.print( outString );
    out.flush();
  }

}

package test;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Michał
 */
public class StartupClass {
 
  @SuppressWarnings("serial")
  public class CrunchifyExample extends HttpServlet
  {

      public void init() throws ServletException
      {
            System.out.println("----------");
            System.out.println("---------- CrunchifyExample Servlet Initialized successfully ----------");
            System.out.println("----------");
      }
  }
}

package server.http;

import server.http.servlet.UploadFile;
import server.config.Config;
import javax.servlet.MultipartConfigElement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import server.Main;
import server.http.servlet.CameraImage;
import server.http.servlet.GetPoint;
import server.http.servlet.GetTimeIDStatus;
import server.http.servlet.GetTrade;
import server.http.servlet.PlayNow;
import server.http.servlet.ScreenImage;
import server.http.servlet.SignIn;
import server.http.servlet.SignUp;
import server.http.servlet.UploadCameraImage;
import server.http.servlet.UploadScreenImage;
import server.http.servlet.UserData;
import server.http.servlet.UserDataSearch;
import server.http.servlet.allpay.TradeOrder;
import server.http.servlet.allpay.TradeResult;

public class HttpServer implements Runnable {

    public static Server SERVER = new Server(Config.HTTP_SERVER_PORT);

    @Override
    public void run() {

        SERVER = new Server(); // new ExecutorThreadPool(25, 50, 30, TimeUnit.SECONDS)
        ServerConnector c = new ServerConnector(SERVER);
        c.setIdleTimeout(1000 * 60 * 60);
//        c.setAcceptQueueSize(10);
        c.setPort(8080);
        SERVER.addConnector(c);

        WebAppContext wac = new WebAppContext();
        wac.setResourceBase(".");

        ServletContextHandler context = wac;

        ServletHolder sh = new ServletHolder(new UploadFile());
        sh.getRegistration().setMultipartConfig(new MultipartConfigElement(Main.PATH));

        ServletHolder sh2 = new ServletHolder(new UploadScreenImage());
        sh2.getRegistration().setMultipartConfig(new MultipartConfigElement(Main.PATH));

        ServletHolder sh3 = new ServletHolder(new UploadCameraImage());
        sh3.getRegistration().setMultipartConfig(new MultipartConfigElement(Main.PATH));

        context.addServlet(sh, "/UploadFile");
        context.addServlet(sh2, "/UploadScreenImage");
        context.addServlet(sh3, "/UploadCameraImage");
        context.addServlet(new ServletHolder(new PlayNow()), "/PlayNow");
        context.addServlet(new ServletHolder(new ScreenImage()), "/ScreenImage.jpg");
        context.addServlet(new ServletHolder(new CameraImage()), "/CameraImage.jpg");
        context.addServlet(new ServletHolder(new UserData()), "/UserData");
        context.addServlet(new ServletHolder(new UserDataSearch()), "/UserDataSearch");
        context.addServlet(new ServletHolder(new TradeResult()), "/TradeResult");
        context.addServlet(new ServletHolder(new TradeOrder()), "/TradeOrder");
        context.addServlet(new ServletHolder(new SignUp()), "/SignUp");
        context.addServlet(new ServletHolder(new SignIn()), "/SignIn");
        context.addServlet(new ServletHolder(new GetPoint()), "/GetPoint");
        context.addServlet(new ServletHolder(new GetTimeIDStatus()), "/GetTimeIDStatus");
        context.addServlet(new ServletHolder(new GetTrade()), "/GetTrade");
        
        HandlerList hl = new HandlerList();
        hl.addHandler(context);

        SERVER.setHandler(hl);

//        SERVER = new Server(8080); 
//        SERVER.setHandler(wac);
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        hl.addHandler(wac);
//        SERVER.setStopTimeout(3);
//        SelectChannelConnector connector0 = new SelectChannelConnector();
//        connector0.setPort(8080);
//        connector0.setMaxIdleTime(30000);
//        connector0.setRequestHeaderSize(8192);
//        WebAppContext wac = new WebAppContext();
//        wac.setResourceBase(".");
//        
//        ServletHolder sh = new ServletHolder(new UploadFile());
//        sh.getRegistration().setMultipartConfig(new MultipartConfigElement(Main.PATH));
//        
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.addServlet(sh, "/UploadFile");
//        context.addServlet(new ServletHolder(new DownloadFile()), "/DownloadFile");
//
//        HandlerList hl = new HandlerList();
//        hl.addHandler(context);
//        hl.addHandler(wac);
//        SERVER.setHandler(hl);
//        Resource resource = Resource.newClassPathResource("a.xml");
//
//        XmlConfiguration configuration;
//        try {
//            configuration = new XmlConfiguration(resource.getInputStream());
//            SERVER = (Server) configuration.configure();
//        } catch (Exception ex) {
//
//        }
    }

}

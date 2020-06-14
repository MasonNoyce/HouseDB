package mason.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.io.printhandler.PrintHandler;

public class HomeServlet extends HttpServlet{

    PrintHandler ph;
    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        ph = PrintHandler.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        ph.printResponse(resp);
    }
    
}
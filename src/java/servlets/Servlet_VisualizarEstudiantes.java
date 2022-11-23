/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import datos.SolicitudDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.RHException;

/**
 *
 * @author Sebastian
 */
public class Servlet_VisualizarEstudiantes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RHException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            SolicitudDAO solDAO = new SolicitudDAO();
            ArrayList<String> codes = solDAO.listarEstudianteCk(request.getSession().getAttribute("periodo")+"");
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Estudiantes de la Convocatoria</title>");
            out.println("<link href='css/styles_c.css' rel='stylesheet'>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Estudiantes de la Convocatoria</h1>");
            out.println("<FORM action='Servlet_Formulario' method='post'>");
            out.println("<select name='code'>");
            for(int i=0; i<codes.size();i++){
                out.println("<option value='"+codes.get(i)+"'>"+codes.get(i)+"</option>");
            }
            out.println("</select>");
            out.println("<input name='Formulario' type='hidden' value='Editar estado'>");
            out.println("<input type='submit' value='Revisar Documentos' />");
            out.println("</FORM>");
            out.println("<div class=\"container\">");
            out.println("<a href='Servlet_Menu'><input type='button' value='Regresar' class=\"cancelbtn\"></a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (RHException ex) {
            Logger.getLogger(Servlet_VisualizarEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (RHException ex) {
            Logger.getLogger(Servlet_VisualizarEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Facultad;
import datos.FacultadDAO;
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
public class Servlet_Facultad extends HttpServlet {

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
            FacultadDAO facDAO = new FacultadDAO();
            ArrayList<Facultad> facs = facDAO.listarFacultades();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Solicitudes por Facultad</title>");
            out.println("<link href='css/styles.css' rel='stylesheet'>");              
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Solicitudes por facultad </h1>");
            out.println("<FORM action='Servlet_MostrarResultados' method='post'>");
            out.println("<select name='fac'>");
            for(int i=0; i<facs.size();i++){
                out.println("<option value='"+facs.get(i).getK_idFacultad()+"'>"+facs.get(i).getNombreFacultad()+"</option>");
            }
            out.println("</select>");
            out.println("<input name='Formulario' type='hidden' value='Seleccionar Facultad'>");
            out.println("<input type='submit' value='Listar Facultad' />");
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
            Logger.getLogger(Servlet_Facultad.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Servlet_Facultad.class.getName()).log(Level.SEVERE, null, ex);
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

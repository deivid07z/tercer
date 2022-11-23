/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Documento;
import Negocio.Solicitud;
import datos.DocumentoDAO;
import datos.SolicitudDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class Servlet_UpdateEstado extends HttpServlet {

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
            throws ServletException, IOException, RHException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            SolicitudDAO solDAO = new SolicitudDAO();
            DocumentoDAO docDAO = new DocumentoDAO();
            String usuario = String.valueOf(ServiceLocator.getInstance().getUsuario()).substring(2);
            Solicitud solc = solDAO.buscarSolicitud(Long.parseLong(request.getParameter("code")),request.getSession().getAttribute("periodo")+"");
            ArrayList<Documento> lsDoc = solc.getListDocumento();
            Documento doc;
            for(int i=0;i<lsDoc.size();i++){
                doc = lsDoc.get(i);
                doc.setEstado(request.getParameter(""+doc.getFk_idCondicion()+doc.getPfk_idCategoria()+""));
                doc.setFk_codRevisor(Long.parseLong(usuario));
                docDAO.actualizarDocumento(doc);
            }
            solc.setEstado("PT");
            solDAO.actualizarSolicitud(solc);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href='css/styles.css' rel='stylesheet'>");
            out.println("<title>Revisión Convocatoria</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Datos ingresados al sistema satisfactoriamente.</h1>");
            out.println("<a href='Servlet_Menu'><input type='button' value='Regresar'></a>");
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
        } catch (RHException | SQLException ex) {
            Logger.getLogger(Servlet_UpdateEstado.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (RHException | SQLException ex) {
            Logger.getLogger(Servlet_UpdateEstado.class.getName()).log(Level.SEVERE, null, ex);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.CargarAleatorio;
import Negocio.CargarAleatorioRevisor;
import Negocio.CargarBaseDatos;
import Negocio.CargarBaseDatosRevisor;
import Negocio.CargarEstudiantes;
import Negocio.CargarRevisor;
import Negocio.Estudiante;
import Negocio.Revisor;
import datos.EstudianteDAO;
import datos.RegistroDAO;
import datos.RevisorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.CaException;
import util.Ingreso;
import util.RHException;

/**
 *
 * @author Sebastian
 */
public class Servlet_Login extends HttpServlet {

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
            throws ServletException, IOException, SQLException, RHException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            RegistroDAO registro = new RegistroDAO();
            CargarEstudiantes cargador;
            CargarRevisor cargadorR;
            Estudiante estd = null;
            Revisor revis = null;
            Ingreso lg = new Ingreso("administrador", "admin");
            lg.login();
            boolean esAdmon=false;
            String page = "";
            if (request.getParameter("tipo").equals("Estudiante")) {
                    if (registro.buscarUsuario(Long.parseLong(request.getParameter("user")))) {
                        cargador = new CargarBaseDatos();
                        if (lg.conectar("U_"+request.getParameter("user"), request.getParameter("password"))) {
                            lg.conectar("administrador", "admin");
                            Estudiante estudi = new Estudiante(Long.parseLong(request.getParameter("user")));
                            estd = cargador.cargarEstudiante(estudi);
                            lg.conectar("U_"+request.getParameter("user"), request.getParameter("password"));
                            request.getSession().setAttribute("usuario","Estudiante");
                        } else {
                            request.getSession().setAttribute("error", "Contraseña Incorrecta");
                            response.sendRedirect("formularioIngresar.jsp");
                        }
                    } else {
                        cargador = new CargarAleatorio();
                        Estudiante estudi = new Estudiante(Long.parseLong(request.getParameter("user")));
                        estd = cargador.cargarEstudiante(estudi);
                        registro.registrar(String.valueOf(estd.getK_codigo()), request.getParameter("password"), "Estudiante");
                        request.getSession().setAttribute("usuario","Estudiante");
                    }
                }else{
                    if(request.getParameter("tipo").equals("Revisor")){
                        if (registro.buscarUsuario(Long.parseLong(request.getParameter("user")))) {
                            cargadorR = new CargarBaseDatosRevisor();
                            if (lg.conectar("U_"+request.getParameter("user"), request.getParameter("password"))) {
                                lg.conectar("administrador", "admin");
                                Revisor revisor = new Revisor(Long.parseLong(request.getParameter("user")));
                                revis = cargadorR.cargarRevisor(revisor);
                                lg.conectar("U_"+request.getParameter("user"), request.getParameter("password"));
                                request.getSession().setAttribute("usuario","Revisor");
                            } else {
                                request.getSession().setAttribute("error", "Contraseña Incorrecta");
                                response.sendRedirect("formularioIngresar.jsp");
                            }
                        } else {
                            cargadorR = new CargarAleatorioRevisor();
                            Revisor revisor = new Revisor(Long.parseLong(request.getParameter("user")));
                            revis = cargadorR.cargarRevisor(revisor);
                            registro.registrar(String.valueOf(revis.getK_codRevisor()), request.getParameter("password"), "Revisor");
                            request.getSession().setAttribute("usuario","Revisor");
                        }
                    }else{
                        if(request.getParameter("tipo").equals("Administrador")){
                            if (lg.conectar(request.getParameter("user"),request.getParameter("password"))){
                                System.out.println("encontre");
                                esAdmon=true;
                                request.getSession().setAttribute("usuario","Administrador");
                           }else{
                                request.getSession().setAttribute("error", "Contraseña Incorrecta");
                                response.sendRedirect("formularioIngresar.jsp");
                               
                            }
                        }
                    }
                }
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Datos Personales</title>");
                out.println("<link href='css/styles_c.css' rel='stylesheet'>");
                out.println("</head>");
                out.println("<body>");
                if(esAdmon|| CaException.getInstance().getDetalle() != null){
                    if(esAdmon){
                        page="Servlet_Menu";
                    }
                    if (CaException.getInstance().getDetalle() != null) {
                        out.print(CaException.getInstance().getLastException());
                        CaException.getInstance().setDetalle(null);
                        request.getSession().setAttribute("error", "Contraseña Incorrecta");
                        page="formularioIngresar.jsp";
                    }
                    RequestDispatcher dd=request.getRequestDispatcher(page);
                    dd.forward(request, response);
                }                
                out.println("<h1>Datos personales</h1>");
                if (request.getParameter("tipo").equals("Estudiante")) {
                    out.println("<h2>Estudiante</h2>");
                    out.println("<div class=\"show\">");
                    out.println("Codigo: " + estd.getK_codigo() + "<br>");
                    out.println("<hr/>");
                    out.println("Primer Nombre: " + estd.getPrimerNombre() + "<br>");
                    out.println("<hr/>");
                    out.println("Segundo Nombre: " + estd.getSegundoNombre() + "<br>");
                    out.println("<hr/>");
                    out.println("Primer Apellido: " + estd.getPrimerApellido() + "<br>");
                    out.println("<hr/>");
                    out.println("Segundo Apellido: " + estd.getSegundoApellido() + "<br>");
                    out.println("<hr/>");
                    if (estd.getEstadoEstudiante().equals("MA")) {
                        out.println("Estado: Matriculado" + "<br>");
                    }
                    if (estd.getEstadoEstudiante().equals("NM")) {
                        out.println("Estado: No Matriculado" + "<br>");
                    }
                    out.println("<hr/>");
                    out.println("Indice matricula: " + estd.getIndiceMatricula() + "<br>");
                    out.println("<hr/>");
                    out.println("Correo Electronico: " + estd.getCorreoEst() + "<br>");
                    out.println("<hr/>");
                    out.println("</div>");
                }
                if (request.getParameter("tipo").equals("Revisor")) {
                    out.println("<h2>Revisor</h2>");
                    out.println("<div class=\"show\">");
                    out.println("<h3>Codigo: " + revis.getK_codRevisor() + "</h3>");
                    out.println("<hr/>");
                    out.println("<h3>Nombre: " + revis.getNombreRevisor() + "</h3>");
                    out.println("</div>");
                }
                if((estd!=null)&&(estd.getEstadoEstudiante().equals("NM"))){
                    out.println("<a href='formularioIngresar.jsp'><input type='button' value='No Matriculado'></a>");
                }else{
                    out.println("<div class=\"container\">");
                    out.println("<a href='Servlet_Menu'><input type='button' value='Acceder'></a>");
                    out.println("</div");
                }
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
            
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RHException ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RHException ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
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

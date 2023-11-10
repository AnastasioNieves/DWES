package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DatosNoCorrectosException;
import DAO.EmpleadoDAO;
import Model.Empleado;
import Model.Nomina;

@WebServlet(description = "Administra solicitudes para la tabla de empleados", urlPatterns = { "/empresa" })
public class EmpleadoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmpleadoController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opcion = request.getParameter("opcion");

        if (opcion.equals("index")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else if (opcion.equals("listarEmpleado")) {
            obtenerEmpleado(request, response);
        } else if (opcion.equals("listarSalario")) {
            String content = "/views/listarSalario.jsp";
            request.setAttribute("content", content);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } else if (opcion.equals("buscarEmpleado")) {
            String content = "/views/buscarEmpleado.jsp";
            request.setAttribute("content", content);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } else if (opcion.equals("editar")) {
            String dni = request.getParameter("dni");
            if (dni != null && !dni.isEmpty()) {
                // Obtener el empleado a editar
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                try {
                    Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
                    request.setAttribute("empleado", empleado);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            String content = "/views/editar.jsp";
            request.setAttribute("content", content);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } else if (opcion.equals("eliminar")) {
            eliminarEmpleado(request, response);
        } else if (opcion.equals("eliminarBusqueda")) {
            // Eliminar un empleado basado en criterios de búsqueda.
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            String dni = request.getParameter("dni");
            try {
                empleadoDAO.eliminar(dni);
                obtenerEmpleado(request, response);
                request.setAttribute("mensaje", "Empleado eliminado con éxito.");
                String content = "/views/buscarEmpleado.jsp";
                request.setAttribute("content", content);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException err) {
                err.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opcion = request.getParameter("opcion");

        if (opcion.equals("buscarSalario")) {
            String dniBusqueda = request.getParameter("dniBusqueda");
            if (dniBusqueda != null && !dniBusqueda.isEmpty()) {
                Nomina nomina = new Nomina();
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                try {
                    Empleado empleado = empleadoDAO.obtenerEmpleado(dniBusqueda);
                    double salario = nomina.sueldo(empleado);
                    request.setAttribute("empleado", empleado);
                    request.setAttribute("sueldo", salario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String content = "/views/listarSalario.jsp";
                request.setAttribute("content", content);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            }
        } else if (opcion.equals("buscarEmpleado")) {
            String tipoBusqueda = request.getParameter("tipoBusqueda");
            String valorBusqueda = request.getParameter("valorBusqueda");
            buscarEmpleadosPorCriterio(tipoBusqueda, valorBusqueda, request, response);
        } else if (opcion.equals("editar")) {
            Empleado empleado = new Empleado();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            empleado.setDni(request.getParameter("dni"));
            empleado.setNombre(request.getParameter("nombre"));
            empleado.setSexo(request.getParameter("sexo").charAt(0));
            try {
                empleado.setCategoria(Integer.parseInt(request.getParameter("categoria")));
            } catch (DatosNoCorrectosException e) {
                e.printStackTrace();
            }
            empleado.setAnyos(Double.parseDouble(request.getParameter("anyos")));

            try {
                if (empleadoDAO.editar(empleado)) {
                    obtenerEmpleado(request, response);
                    request.setAttribute("mensaje", "Empleado editado con éxito.");
                } else {
                    request.setAttribute("error", "Error al editar empleado.");
                }
                String content = "/views/listarEmpleado.jsp";
                request.setAttribute("content", content);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Empleado> obtenerEmpleado(HttpServletRequest request, HttpServletResponse response) {
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        List<Empleado> lista = new ArrayList<>();
        try {
            lista = empleadoDAO.obtenerEmpleados();
            request.setAttribute("lista", lista);
            String content = "/views/listarEmpleado.jsp";
            request.setAttribute("content", content);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Empleado> buscarEmpleadosPorCriterio(String tipoBusqueda, String valorBusqueda,
            HttpServletRequest request, HttpServletResponse response) {
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        List<Empleado> lista = new ArrayList<>();
        try {
            if (tipoBusqueda != null && valorBusqueda != null) {
                switch (tipoBusqueda) {
                    case "dni":
                        lista = empleadoDAO.buscarPorDNI(valorBusqueda);
                        break;
                    case "nombre":
                        lista = empleadoDAO.buscarPorNombre(valorBusqueda);
                        break;
                    case "sexo":
                        lista = empleadoDAO.buscarPorSexo(valorBusqueda);
                        break;
                    case "categoria":
                        lista = empleadoDAO.buscarPorCategoria(valorBusqueda);
                        break;
                    case "anyos":
                        lista = empleadoDAO.buscarPorAnyosTrabajados(valorBusqueda);
                        break;
                    default:
                        break;
                }
                request.setAttribute("lista", lista);
                String content = "/views/buscarEmpleado.jsp";
                request.setAttribute("content", content);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void eliminarEmpleado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        String dni = request.getParameter("dni");

        try {
            empleadoDAO.eliminar(dni);
            obtenerEmpleado(request, response);
            request.setAttribute("mensaje", "Empleado marcado como eliminado con éxito.");
            String content = "/views/listarEmpleado.jsp";
            request.setAttribute("content", content);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

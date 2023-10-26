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

import DAO.EmpleadoDAO;
import Model.Empleado;
import Model.Nomina;
/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = { "/empresa" })
public class EmpleadoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmpleadoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String opcion = request.getParameter("opcion");

		 if (opcion.equals("index")) {
		    RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
		    requestDispatcher.forward(request, response);
		        
		} else if (opcion.equals("mostarEmpleado")) {
			obtenerEmpleado(request, response);
			
		} else if (opcion.equals("mostrarSalario")) {
	        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarSalario.jsp");
	        requestDispatcher.forward(request, response);
	        
	    } else if (opcion.equals("editar")) {
			String dni = request.getParameter("dni");
			System.out.println("Editar por dni: " + dni);
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			Empleado e = new Empleado();
			try {
				e = empleadoDAO.obtenerEmpleado(dni);
				System.out.println(e);
				request.setAttribute("empleado", e);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/modificarEmpleado.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}

		} else if (opcion.equals("eliminar")) {
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			int dni = Integer.parseInt(request.getParameter("dni"));
			try {
				empleadoDAO.eliminar(dni);
				obtenerEmpleado(request, response);
				System.out.println("Registro eliminado satisfactoriamente...");
				request.setAttribute("mensaje", "Producto eliminado con exito.");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarEmpleados.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}

		}else if (opcion.equals("buscarEmpleados")) {
		    // Esta opción muestra la página de búsqueda de empleados
		    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarEmpleados.jsp");
		    requestDispatcher.forward(request, response);
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcion = request.getParameter("opcion");
		if (opcion.equals("buscarSalario")) {
		    // Código para buscar un empleado por DNI y calcular su salario
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

			    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarSalario.jsp");
			    requestDispatcher.forward(request, response);
		    }
		  }
		}

	public List<Empleado> obtenerEmpleado(HttpServletRequest request, HttpServletResponse response) {
		EmpleadoDAO empleadoDAO = new EmpleadoDAO();
		List<Empleado> lista = new ArrayList<>();
		try {
			lista = empleadoDAO.obtenerEmpleados();
			for (Empleado empleado : lista) {
				System.out.println(empleado);
			}

			request.setAttribute("lista", lista);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarEmpleados.jsp");
			requestDispatcher.forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

}

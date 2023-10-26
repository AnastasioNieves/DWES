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

		} else if (opcion.equals("mostrarEmpleados")) {
			List<Empleado> empleados = new ArrayList<>();
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			try {
				empleados = empleadoDAO.obtenerEmpleados();
				// Establecer la lista de empleados en el atributo "empleados" del objeto
				// request
				request.setAttribute("empleados", empleados);

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarEmpleados.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (opcion.equals("mostrarSalario")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarSalario.jsp");
			requestDispatcher.forward(request, response);

		} else if (opcion.equals("buscarEmpleados")) {
			// Esta opción muestra la página de búsqueda de empleados
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarEmpleados.jsp");
			requestDispatcher.forward(request, response);
		} else if (opcion.equals("editar")) {
			String dni = request.getParameter("dni");
			System.out.println("Editar por dni: " + dni);
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			Empleado e = new Empleado();
			try {
				e = empleadoDAO.obtenerEmpleadoDNI(dni);
				System.out.println(e);
				request.setAttribute("empleado", e);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/modificarEmpleado.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			} catch (DatosNoCorrectosException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}

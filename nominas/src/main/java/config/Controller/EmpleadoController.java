package config.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.DAO.DatosNoCorrectosException;
import config.DAO.EmpleadoDAO;
import config.Model.Empleado;
import config.Model.Nomina;

@Controller
@RequestMapping("/empresa")
public class EmpleadoController {

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/listarEmpleado")
    public String listarEmpleado(Model model) {
        List<Empleado> lista = obtenerEmpleado();
        model.addAttribute("lista", lista);
        return "/views/listarEmpleado";
    }

    @GetMapping("/listarSalario")
    public String listarSalario(Model model) {
        String content = "/views/listarSalario";
        model.addAttribute("content", content);
        return "/index";
    }

    @GetMapping("/buscarEmpleado")
    public String buscarEmpleado(Model model) {
        String content = "/views/buscarEmpleado";
        model.addAttribute("content", content);
        return "/index";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam String dni, Model model) {
        if (dni != null && !dni.isEmpty()) {
            Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
			model.addAttribute("empleado", empleado);
        }
        String content = "/views/editar";
        model.addAttribute("content", content);
        return "/index";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam String dni, Model model) {
        empleadoDAO.eliminar(dni);
		List<Empleado> lista = obtenerEmpleado();
		model.addAttribute("lista", lista);
		model.addAttribute("mensaje", "Empleado eliminado con éxito.");
		String content = "/views/buscarEmpleado";
		model.addAttribute("content", content);
        return "/index";
    }

    @PostMapping("/buscarSalario")
    public String buscarSalario(@RequestParam String dniBusqueda, Model model) {
        if (dniBusqueda != null && !dniBusqueda.isEmpty()) {
            Nomina nomina = new Nomina();
            try {
                Empleado empleado = empleadoDAO.obtenerEmpleado(dniBusqueda);
                double salario = nomina.sueldo(empleado);
                model.addAttribute("empleado", empleado);
                model.addAttribute("sueldo", salario);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String content = "/views/listarSalario";
            model.addAttribute("content", content);
        }
        return "/index";
    }

    @PostMapping("/buscarEmpleado")
    public String buscarEmpleado(@RequestParam String tipoBusqueda, @RequestParam String valorBusqueda, Model model) {
        List<Empleado> lista = buscarEmpleadosPorCriterio(tipoBusqueda, valorBusqueda);
        model.addAttribute("lista", lista);
        String content = "/views/buscarEmpleado";
        model.addAttribute("content", content);
        return "/index";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam String dni, @RequestParam String nombre, @RequestParam String sexo,
                         @RequestParam String categoria, @RequestParam String anyos, Model model) {
        Empleado empleado = new Empleado();
        empleado.setDni(dni);
        empleado.setNombre(nombre);
        empleado.setSexo(sexo.charAt(0));
        try {
            empleado.setCategoria(Integer.parseInt(categoria));
        } catch (DatosNoCorrectosException e) {
            e.printStackTrace();
        }
        empleado.setAnyos(Double.parseDouble(anyos));

        if (empleadoDAO.editar(empleado)) {
		    List<Empleado> lista = obtenerEmpleado();
		    model.addAttribute("lista", lista);
		    model.addAttribute("mensaje", "Empleado editado con éxito.");
		    String content = "/views/listarEmpleado";
		    model.addAttribute("content", content);
		} else {
		    model.addAttribute("error", "Error al editar empleado.");
		}
        return "/index";
    }

    private List<Empleado> obtenerEmpleado() {
        List<Empleado> lista = new ArrayList<>();
        lista = empleadoDAO.obtenerEmpleados();
        return lista;
    }

    private List<Empleado> buscarEmpleadosPorCriterio(String tipoBusqueda, String valorBusqueda) {
        List<Empleado> lista = new ArrayList<>();
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
		}
        return lista;
    }
}

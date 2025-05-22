package gm.rh.controlador;

import gm.rh.excepcion.RecursoNoEcontradoExcepcion;
import gm.rh.modelo.Empleado;
import gm.rh.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//http:locahost:8080/rh-app
@RequestMapping("rh-app")
@CrossOrigin(value="http://localhost:3000")
public class EmpleadoControlador {

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);


    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados(){
       List<Empleado> empleados= this.empleadoServicio.listarEmpleados();
        empleados.forEach(empleado->logger.info(empleado.toString()));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado){
        logger.info("Empleado a agregar: " + empleado);
        return this.empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{idEmpleado}")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable Integer idEmpleado){
        Empleado empleado = this.empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        if(empleado == null){
            throw new RecursoNoEcontradoExcepcion("No se encontro el empleado id: " + idEmpleado);
        }
        return ResponseEntity.ok(empleado);
    }
    @PutMapping("/empleados/{idEmpleado}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer idEmpleado,@RequestBody Empleado empleadoRecibido){
        Empleado empleado = this.empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        if( empleado== null){
            throw  new RecursoNoEcontradoExcepcion("No encontro el empleado a actulizar con el id: " + idEmpleado);
        }
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());
        this.empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{idEmpleado}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Integer idEmpleado){
        Empleado empleado = this.empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        if(empleado == null){
            throw new RecursoNoEcontradoExcepcion("El id recibido no existe: " + idEmpleado);
        }
        this.empleadoServicio.eliminarEmpleado(empleado);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}

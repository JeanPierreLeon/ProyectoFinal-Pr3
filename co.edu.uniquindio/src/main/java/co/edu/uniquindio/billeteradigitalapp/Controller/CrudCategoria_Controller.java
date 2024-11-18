package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Controller.Service.ICategoriaControllerService;
import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Categoria;

import java.util.List;


public class CrudCategoria_Controller implements ICategoriaControllerService {

    static ModelFactory modelFactory;

    public CrudCategoria_Controller() {modelFactory = ModelFactory.getInstance();
    }

    public List<Categoria> obtenerCategorias() {
        return modelFactory.obtenerCategorias();
    }

    public boolean crearCategoria(Categoria categoria) {return modelFactory.crearCategoria(categoria);
    }

    public boolean eliminarCategoria(String idCategoria) {
        return modelFactory.eliminarCategoria(idCategoria);
    }

    public boolean actualizarCategoria(Categoria categoria) {
        return modelFactory.actualizarCategoria(categoria);
    }
}


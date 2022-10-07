package controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import src.Articulo;
import src.ClsConexion;
import src.ClsUsuario;
import src.Venta;

public class ControladorPrincipal {

    public static final String nombre = "Julian";
    public static final String apellido = "arango";
    public static final String cedula = "1123";
    public static final String correoo = "1";
    public static final String contrasenaa = "1";
    public static final short edad = 18;
    ClsConexion conexion = new ClsConexion();

    public ControladorPrincipal() {
    }

    public int validarLoging(String correo, String contrasena) {
        int validador = 0;
        List<ClsUsuario> usuarios = new ArrayList<>();

        if (correoo.equals(correo) && contrasenaa.equals(contrasena)) {
            validador = 1;
        }
        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().executeQuery("select cedula, nombre, apellido, correo, contrasena, edad "
                    + "from Usuarios"));

            while (conexion.getResultadoDB().next()) {
                String cedula = conexion.getResultadoDB().getString("cedula");
                String nombre = conexion.getResultadoDB().getString("nombre");
                String apellido = conexion.getResultadoDB().getString("apellido");
                String corre = conexion.getResultadoDB().getString("correo");
                String contra = conexion.getResultadoDB().getString("contrasena");
                int edad = conexion.getResultadoDB().getInt("edad");
                ClsUsuario temp = new ClsUsuario(cedula, nombre, apellido, corre, contra, edad);
                usuarios.add(temp);
            }

            for (int i = 0; i < usuarios.size(); i++) {

                if (usuarios.get(i).getCorreo().equals(correo)) {

                    if (usuarios.get(i).getContrasena().equals(contrasena)) {

                        System.out.println(usuarios.get(i).getNombre());
                        conexion.desconectar();
                        validador = 2;
                    }
                }
            }
            conexion.desconectar();

        } catch (SQLException ex) {
            Logger.getLogger(ClsUsuario.class.getName()).log(Level.SEVERE, null, ex);
            conexion.desconectar();
        }
        return validador;
    }

    public boolean guardarVentaArticulo(String fecha,
            int unidadesVendidas, String nombreDelArticulo, float valorTotal) {
        Venta venta = new Venta(fecha,
                unidadesVendidas, nombreDelArticulo, valorTotal);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into VentasArticulos(fecha,"
                    + "unidadesVendidas, nombreDelArticulo, valorTotal)"
                    + "values('" + venta.getFecha() + "','"
                    + venta.getUnidadesVendidas() + "','"
                    + venta.getNombreDelArticulo() + "',"
                    + venta.getValorTotal() + ")");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscarVentaArticulo(String nombreDelArticulo) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select fecha," + "unidadesVendidas, "
                            + "nombreDelArticulo, "
                            + "valorTotal from VentasArticulos where "
                            + "nombreDelArticulo='" + nombreDelArticulo + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("fecha"));
                temp.add(conexion.getResultadoDB().getString("unidadesVendidas"));
                temp.add(conexion.getResultadoDB().getString("nombreDelArticulo"));
                temp.add(conexion.getResultadoDB().getInt("valorTotal") + "");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarVentaArticulo(String fecha,
            int unidadesVendidas, String nombreDelArticulo, float valorTotal) {
        Venta venta = new Venta(fecha,
                unidadesVendidas, nombreDelArticulo, valorTotal);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update VentasArticulos set fecha='" + venta.getFecha()
                    + "',unidadesVendidas='" + venta.getUnidadesVendidas()
                    + "',nombreDelArticulo='" + venta.getNombreDelArticulo()
                    + "', where valorTotal='" + venta.getValorTotal() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminarVentaArticulo(String codigo) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from VentasArticulos where codigo='" + codigo + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listarVentaArticulo() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Fecha de venta", "Unidades vendidas",
            "Nombre del articulo", "Valor total"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigo, fecha, "
                            + "unidadesVendidas, nombreDelArticulo, "
                            + "valorTotal from VentasArticulos "
                            + "order by nombreDelArticulo"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("fecha"),
                    conexion.getResultadoDB().getString("unidadesVendidas"),
                    conexion.getResultadoDB().getString("nombreDelArticulo"),
                    conexion.getResultadoDB().getInt("valorTotal")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;
    }

    //_______________________________________________________________________________________
    public boolean guardarArticulo(String codigo, String nombre,
            float precio, String descripcion, String categoria) {
        Articulo articulo = new Articulo(codigo, nombre,
                precio, descripcion, categoria);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into RegistrosArticulos(codigo,nombre,precio,descripcion,categoria) "
                    + "values('" + articulo.getCodigo() + "','"
                    + articulo.getNombre() + "','"
                    + articulo.getPrecio() + "',"
                    + articulo.getDescripcion() + ","
                    + articulo.getCategoria() + ")");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscarArticulo(String codigo) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigo,nombre,precio,descripcion,categoria "
                            + "from RegistrosArticulos where "
                            + "codigo='" + codigo + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("codigo"));
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getFloat("precio") + "");
                temp.add(conexion.getResultadoDB().getString("descripcion"));
                temp.add(conexion.getResultadoDB().getString("categoria"));
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarArticulo(String codigo, String nombre, float precio,
            String descripcion, String categoria) {
        Articulo articulo = new Articulo(codigo, nombre, precio, descripcion, categoria);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update RegistrosArticulos set nombre='" + articulo.getNombre()
                    + "',precio='" + articulo.getPrecio()
                    + "',descripcion='" + articulo.getDescripcion()
                    + "',categoria='" + articulo.getCategoria()
                    + "' where codigo='" + articulo.getCodigo() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminarArticulo(String codigo) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from RegistrosArticulos where codigo='" + codigo + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listarArticulo() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Codigo", "Nombre",
            "Precio", "Descripcion", "Categoria"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigo, nombre,"
                            + "precio, descripcion, categoria from RegistrosArticulos order by codigo"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("codigo"),
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getDouble("precio"),
                    conexion.getResultadoDB().getString("descripcion"),
                    conexion.getResultadoDB().getString("categoria")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;
    }

    //________________________________________________________________________________________
    public boolean guardarUsuario(String cedula, String nombre, String apellido,
            String correo, String contrasena, int edad) {
        ClsUsuario usuario = new ClsUsuario(cedula, nombre,
                apellido, correo, contrasena, edad);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into Usuarios(cedula,nombre,apellido,correo,contrasena,edad) "
                    + "values('" + usuario.getCedula() + "','"
                    + usuario.getNombre() + "','"
                    + usuario.getApellido() + "',"
                    + usuario.getCorreo() + ","
                    + usuario.getContrasena() + ","
                    + usuario.getEdad() + ")");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscarUsuario(String cedula) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula,nombre,apellido,correo,contrasena,edad "
                            + "from Usuarios where "
                            + "cedula='" + cedula + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("cedula"));
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("correo"));
                temp.add(conexion.getResultadoDB().getString("contrasena"));
                temp.add(conexion.getResultadoDB().getInt("edad") + "");

            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarUsuario(String cedula, String nombre, String apellido,
            String correo, String contrasena, int edad) {
        ClsUsuario usuario = new ClsUsuario(cedula, nombre,
                apellido, correo, contrasena, edad);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update Usuarios set nombre='" + usuario.getNombre()
                    + "',apellido='" + usuario.getApellido()
                    + "',correo='" + usuario.getCorreo()
                    + "',contrasena='" + usuario.getContrasena()
                    + "',edad='" + usuario.getEdad()
                    + "' where cedula='" + usuario.getCedula() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminarUsuario(String cedula) {
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("delete from Usuarios where cedula='" + cedula + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listarUsuario() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Cedula", "Nombre",
            "Apellido", "Correo", "Contrasena", "Edad"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula, nombre,"
                            + "apellido, correo, contrasena, edad from Usuarios order by cedula"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("cedula"),
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("apellido"),
                    conexion.getResultadoDB().getString("correo"),
                    conexion.getResultadoDB().getString("contrasena"),
                    conexion.getResultadoDB().getString("edad")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }
}

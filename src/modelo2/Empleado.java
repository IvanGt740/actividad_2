package modelo2;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;




import javax.swing.JOptionPane;

public class Empleado extends Persona {
    private String nit;
    private double sueldo;
    private double bono;
    private int id;
    private double total; // Cambiado a private

    Conexion cn;
    public Empleado() {}

    public Empleado(int id, String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id = id;
        
        this.nit = nit;
       // this.sueldo = sueldo;
       // this.bono = bono;
        calcularTotal(); // Calcula el total en el constructor
    }

    // Getters y Setters
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
        calcularTotal(); // Recalcula el total cuando se cambia el sueldo
    }

    public double getBono() {
        return bono;
    }

    public void setBono(double bono) {
        this.bono = bono;
        calcularTotal(); // Recalcula el total cuando se cambia el bono
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total; // Devuelve el total correcto
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // Método para calcular el total
    public void calcularTotal() {
        this.total = this.sueldo + this.bono;
    }

   /* @Override
    protected String[] crear() {
        try {
            String[] datos = new String[9];
            datos[0] = getNit();
            datos[1] = getNombres();
            datos[2] = getApellidos();
            datos[3] = getDireccion();
            datos[4] = getTelefono();
            datos[5] = getFecha_nacimiento();
            datos[6] = String.valueOf(getSueldo());
            datos[7] = String.valueOf(getBono());
            datos[8] = String.valueOf(getTotal()); // Asegúrate de que esto sea el total correcto
            return datos;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en Query", JOptionPane.ERROR_MESSAGE);
            return null;
        }
     }
    
    */
    
    @Override
    public void crear(){
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "INSERT INTO clientes(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento) VALUES (?,?,?,?,?,?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            int executar = parametro.executeUpdate();
            System.out.println("Ingreso exitoso... " + Integer.toString (executar));
            
            
            
            cn.cerrar_conexion();
            
        }catch(SQLException ex){
            
            System.out.println("Error en crear "+ ex.getMessage());
        }
        
    }
    
public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
    try{
        cn = new Conexion();
        cn.abrir_conexion();
        String query = "SELECT * FROM CLIENTES;";
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
       String encabezado[] = {"id","nit","nombres","apellidos","direccion","telefono","Fecha_Nacimiento"};
       tabla.setColumnIdentifiers(encabezado);
       String datos[] = new String[7];
       while (consulta.next()){
           datos[0] = consulta.getString("id_cliente");
           datos[1] = consulta.getString("codigo");
           datos[2] = consulta.getString("nombres");
           datos[3] = consulta.getString("apellidos");
           datos[4] = consulta.getString("direccion");
           datos[5] = consulta.getString("telefono");
           datos[6] = consulta.getString("fecha_nacimiento");
           tabla.addRow(datos);
       }
        
        cn.cerrar_conexion();
    }catch (SQLException ex){
        cn.cerrar_conexion();
        System.out.println("Error leer: " + ex.getMessage());
   
    }
    
    return tabla;
}

@Override
public void actualizar(){
    try{
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "UPDATE clientes SET codigo = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE id_cliente = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getId());
            
            int executar = parametro.executeUpdate();
            System.out.println("Modificación exitosa... " + Integer.toString (executar));
            
            cn.cerrar_conexion();
            
        }catch(SQLException ex){
            
            System.out.println("Error en actualizar: "+ ex.getMessage());
        }



}
@Override
public void borrar(){
    try{
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM clientes WHERE id_cliente = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            
            parametro.setInt(1, getId());
            
            int executar = parametro.executeUpdate();
            System.out.println("Eliminación exitosa... " + Integer.toString (executar));
            
            cn.cerrar_conexion();
            
        }catch(SQLException ex){
            
            System.out.println("Error en borrar: "+ ex.getMessage());
        }



}
}


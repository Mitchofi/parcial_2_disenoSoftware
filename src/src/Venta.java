package src;

/**
 *
 * @author Acer
 */
public class Venta {

    private String fecha;
    private int unidadesVendidas;
    private String nombreDelArticulo;
    private float valorTotal;

    /**
     *
     * @param fecha
     * @param unidadesVendidas
     * @param nombreDelArticulo
     * @param valorTotal
     */
    public Venta(String fecha, int unidadesVendidas, String nombreDelArticulo, float valorTotal) {
        this.fecha = fecha;
        this.unidadesVendidas = unidadesVendidas;
        this.nombreDelArticulo = nombreDelArticulo;
        this.valorTotal = valorTotal;
    }

    /**
     *
     * @return
     */
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    /**
     *
     * @param unidadesVendidas
     */
    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
    }

    /**
     *
     * @return
     */
    public String getNombreDelArticulo() {
        return nombreDelArticulo;
    }

    /**
     *
     * @param nombreDelArticulo
     */
    public void setNombreDelArticulo(String nombreDelArticulo) {
        this.nombreDelArticulo = nombreDelArticulo;
    }

    /**
     *
     * @return
     */
    public float getValorTotal() {
        return valorTotal;
    }

    /**
     *
     * @param valorTotal
     */
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

}

package util;

/**
 *
 * @author Acer
 * @param <T>
 */
public class Nodo<T> {

    private T dato;
    private Nodo<T> nodoSig;

    /**
     *
     * @param dato
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.nodoSig = null;
    }

    /**
     *
     * @return
     */
    public T getDato() {
        return dato;
    }

    /**
     *
     * @return
     */
    public Nodo<T> getNodoSig() {
        return nodoSig;
    }

    /**
     *
     * @param nodoSig
     */
    public void setNodoSig(Nodo<T> nodoSig) {
        this.nodoSig = nodoSig;
    }

}

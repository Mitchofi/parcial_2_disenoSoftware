package util;

/**
 *
 * @author Acer
 * @param <T>
 */
public class Lista<T> {

    private Nodo<T> primero;
    private int size;

    /**
     *
     */
    public Lista() {
        this.primero = null;
        this.size = 0;
    }

    /**
     *
     * @param dato
     */
    public void add(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (primero == null) {
            primero = nuevo;
        } else {
            Nodo<T> aux = primero;
            while (aux.getNodoSig() != null) {
                aux = aux.getNodoSig();
            }
            aux.setNodoSig(nuevo);
        }
        size++;
    }

    /**
     *
     * @param pos
     * @param dato
     */
    public void addPos(int pos, T dato) {
        if (pos < 0 || pos > size) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            Nodo<T> nuevo = new Nodo<>(dato);
            if (pos == 0) {
                if (primero == null) {
                    primero = nuevo;
                } else {
                    Nodo<T> aux = primero;
                    nuevo.setNodoSig(aux);
                    primero = nuevo;
                }
            } else {
                Nodo<T> aux = primero;
                for (int i = 0; i < pos; i++) {
                    aux = aux.getNodoSig();
                }
                if (aux == null) {
                    aux = nuevo;
                } else {
                    Nodo<T> aux2 = aux;
                    nuevo.setNodoSig(aux2);
                    aux = nuevo;
                }
            }
        }
        size++;
    }

    /**
     *
     * @param pos
     */
    public void delePos(int pos) {
        if (pos < 0 || pos > size) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            if (pos == 0) {
                primero = primero.getNodoSig();
            } else {
                Nodo<T> aux = primero;
                for (int i = 0; i < pos - 1; i++) {
                    aux = aux.getNodoSig();
                }
                Nodo<T> aux2 = aux.getNodoSig();
                aux.setNodoSig(aux2.getNodoSig());
            }
        }
        size--;
    }

    /**
     *
     * @param pos
     * @return
     */
    public T getDato(int pos) {
        T dato;
        if (pos < 0 || pos > size) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            Nodo<T> aux = primero;
            for (int i = 0; i < pos; i++) {
                aux = aux.getNodoSig();
            }
            dato = aux.getDato();
        }
        return dato;
    }

    /**
     *
     */
    public void clean() {
        primero = null;
        size = 0;
    }

    /**
     *
     * @return
     */
    public int size() {
        return size;
    }
}

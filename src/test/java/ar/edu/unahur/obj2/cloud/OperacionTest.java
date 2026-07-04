package ar.edu.unahur.obj2.cloud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OperacionTest {

    @Test
    void crearOperacionConValorNegativoLanzaExcepcionSinDeclarar() {
        Cluster cluster1 = new Cluster("C1", 100);
        assertThrows(CantidadInvalidaException.class, () -> new Asignacion(cluster1, -5));
        assertThrows(CantidadInvalidaException.class, () -> new Asignacion(cluster1, 0));
    }

    @Test
    void crearLiberacionConValorInvalidoLanzaExcepcion() {
        Cluster cluster1 = new Cluster("C1", 100);
        assertThrows(CantidadInvalidaException.class, () -> new Liberacion(cluster1, -5));
        assertThrows(CantidadInvalidaException.class, () -> new Liberacion(cluster1, 0));
    }

}

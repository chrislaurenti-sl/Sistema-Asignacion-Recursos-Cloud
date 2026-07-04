package ar.edu.unahur.obj2.cloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ClusterTest {

    @Test
    void asignarMasAlladeLimiteLanzaOverProvisioning() {
        Cluster cluster1 = new Cluster("C1", 50);
        Asignacion op = new Asignacion(cluster1, 260); // 50 - 260 = -210 < -200
        assertThrows(OverProvisioningException.class, op::ejecutar);
    }

    @Test
    void asignarCapacidadReduceVcpusDisponibles() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        cluster1.asignarCapacidad(30);
        assertEquals(70, cluster1.getVcpusDisponibles());
    }

    @Test
    void liberarCapacidadIncrementaVcpusDisponibles() {
        Cluster cluster1 = new Cluster("C1", 100);
        cluster1.liberarCapacidad(30);
        assertEquals(130, cluster1.getVcpusDisponibles());
    }

    @Test
    void asignarHastaElLimiteExactoNoLanzaExcepcion() throws Exception {
        Cluster cluster1 = new Cluster("C1", 0);
        cluster1.asignarCapacidad(200); // 0 - 200 = -200, límite exacto, no debe fallar
        assertEquals(-200, cluster1.getVcpusDisponibles());
    }

}
package ar.edu.unahur.obj2.cloud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandTest {

    
    @Test
    void deshacerAsignacionRestauraCapacidad() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        Asignacion op = new Asignacion(cluster1, 30);
        op.ejecutar();
        op.deshacer();
        assertEquals(100, cluster1.getVcpusDisponibles());
    }

    @Test
    void deshacerLiberacionRestauraCapacidad() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        Liberacion op = new Liberacion(cluster1, 30);
        op.ejecutar(); // 100 -> 130
        op.deshacer(); // vuelve a 100
        assertEquals(100, cluster1.getVcpusDisponibles());
    }

}

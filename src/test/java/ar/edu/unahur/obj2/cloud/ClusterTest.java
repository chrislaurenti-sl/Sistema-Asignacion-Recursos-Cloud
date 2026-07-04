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
    void deshacerLiberacionRestauraCapacidad() throws Exception {
        Cluster claude1 = new Cluster("C1", 100);
        Liberacion op = new Liberacion(claude1, 30);
        op.ejecutar(); // 100 -> 130
        op.deshacer(); // vuelve a 100
        assertEquals(100, claude1.getVcpusDisponibles());
    }

    @Test
    void planFallidoRevierteSoloOperacionesDeEsePlan() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        PlanDespliegue plan = new PlanDespliegue();
        plan.agregarOperacion(new Asignacion(cluster1, 50));
        plan.agregarOperacion(new Asignacion(cluster1, 260));

        assertThrows(OverProvisioningException.class, plan::ejecutar);
        assertEquals(100, cluster1.getVcpusDisponibles());
    }

    @Test
    void crearOperacionConValorNegativoLanzaExcepcionSinDeclarar() {
        Cluster cluster1 = new Cluster("C1", 100);
        assertThrows(CantidadInvalidaException.class, () -> new Asignacion(cluster1, -5));
        assertThrows(CantidadInvalidaException.class, () -> new Asignacion(cluster1, 0));
    }

    // Command
    @Test
    void deshacerAsignacionRestauraCapacidad() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        Asignacion op = new Asignacion(cluster1, 30);
        op.ejecutar();
        op.deshacer();
        assertEquals(100, cluster1.getVcpusDisponibles());
    }

    @Test
    void planExitosoVaciaOperacionesPendientes() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        PlanDespliegue plan = new PlanDespliegue();
        plan.agregarOperacion(new Asignacion(cluster1, 10));
        plan.agregarOperacion(new Liberacion(cluster1, 5));

        plan.ejecutar();

        assertEquals(95, cluster1.getVcpusDisponibles());
    }

    @Test
    void planificadorEjecutaOperacionYPlanPorLaMismaVia() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        PlanificadorDespliegues planificador = new PlanificadorDespliegues();

        planificador.ejecutar(new Liberacion(cluster1, 10));
        PlanDespliegue plan = new PlanDespliegue();
        plan.agregarOperacion(new Asignacion(cluster1, 5));
        planificador.ejecutar(plan);

        assertEquals(105, cluster1.getVcpusDisponibles());
    }

}

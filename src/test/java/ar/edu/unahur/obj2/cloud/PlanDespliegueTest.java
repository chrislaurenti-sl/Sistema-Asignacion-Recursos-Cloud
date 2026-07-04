package ar.edu.unahur.obj2.cloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PlanDespliegueTest {

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
    void planificadorTrataOperacionYPlanUniformemente() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        PlanificadorDespliegues planificador = new PlanificadorDespliegues();

        Operacion individual = new Liberacion(cluster1, 10);
        PlanDespliegue plan = new PlanDespliegue();
        plan.agregarOperacion(new Asignacion(cluster1, 5));

        planificador.ejecutar(individual);
        planificador.ejecutar(plan);

        assertEquals(105, cluster1.getVcpusDisponibles());
    }

    @Test
    void deshacerPlanYaEjecutadoConExitoRevierteTodo() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        PlanDespliegue plan = new PlanDespliegue();
        plan.agregarOperacion(new Asignacion(cluster1, 20));
        plan.agregarOperacion(new Liberacion(cluster1, 5));

        plan.ejecutar(); // 100 -> 115
        plan.deshacer(); // vuelve a 100

        assertEquals(100, cluster1.getVcpusDisponibles());
    }
}

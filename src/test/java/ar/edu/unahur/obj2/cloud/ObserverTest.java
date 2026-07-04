package ar.edu.unahur.obj2.cloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ObserverTest {

    @Test
    void auditoriaObserverRegistraElCambio() {
        Cluster cluster1 = new Cluster("C1", 100);
        AuditoriaObserver auditoria = new AuditoriaObserver();

        auditoria.actualizar(cluster1, -30); // no debe lanzar excepción
    }

    @Test
    void sreObserverRegistraLaVariacion() {
        Cluster cluster1 = new Cluster("C1", 100);
        SREObserver sre = new SREObserver();

        sre.actualizar(cluster1, 30);
    }

    @Test
    void alarmaObserverNoDisparaSiClusterEnZonaPositiva() {
        Cluster cluster1 = new Cluster("C1", 100);
        AlarmaObserver alarma = new AlarmaObserver();

        alarma.actualizar(cluster1, -30); // cluster en 70, no debería imprimir nada, pero no debe fallar
    }

    @Test
    void alarmaObserverDisparaSiClusterEnZonaNegativa() {
        Cluster cluster1 = new Cluster("C1", -20);
        AlarmaObserver alarma = new AlarmaObserver();

        alarma.actualizar(cluster1, 0);
    }

    // Clase de prueba (spy) que registra si fue notificado
    static class ObservadorSpy implements SistemaInteresado {
        Boolean notificado = false;
        Integer vecesNotificado = 0;

        @Override
        public void actualizar(Cluster cluster, Integer cantidad) {
            notificado = true;
            vecesNotificado++;
        }
    }

    @Test
    void asignarNotificaConCantidadNegativa() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        List<Integer> recibidos = new ArrayList<>();
        cluster1.suscribir((cluster, cantidad) -> recibidos.add(cantidad));

        cluster1.asignarCapacidad(30);

        assertEquals(List.of(-30), recibidos);
    }

    @Test
    void liberarNotificaConCantidadPositiva() {
        Cluster cluster1 = new Cluster("C1", 100);
        List<Integer> recibidos = new ArrayList<>();
        cluster1.suscribir((cluster, cantidad) -> recibidos.add(cantidad));

        cluster1.liberarCapacidad(30);

        assertEquals(List.of(30), recibidos);
    }

    @Test
    void desuscribirDejaDeNotificar() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        ObservadorSpy observer = new ObservadorSpy();

        cluster1.suscribir(observer);
        cluster1.asignarCapacidad(10);
        cluster1.desuscribir(observer);
        cluster1.asignarCapacidad(10);

        assertEquals(1, observer.vecesNotificado);
    }

    @Test
    void variosInteresadosSonNotificadosPorLaMismaOperacion() throws Exception {
        Cluster cluster1 = new Cluster("C1", 100);
        ObservadorSpy observer1 = new ObservadorSpy();
        ObservadorSpy observer2 = new ObservadorSpy();

        cluster1.suscribir(observer1);
        cluster1.suscribir(observer2);

        cluster1.asignarCapacidad(10);

        assertTrue(observer1.notificado);
        assertTrue(observer2.notificado);
    }

    @Test
    void clusterQuedaEnZonaDeOverprovisioningTrasAsignacion() throws Exception {
        Cluster cluster1 = new Cluster("C1", 10);

        cluster1.asignarCapacidad(30); // 10 - 30 = -20

        assertTrue(cluster1.getVcpusDisponibles() < 0);
    }
}

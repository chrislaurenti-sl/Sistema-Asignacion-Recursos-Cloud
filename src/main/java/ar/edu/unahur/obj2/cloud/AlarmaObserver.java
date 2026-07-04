package ar.edu.unahur.obj2.cloud;

public class AlarmaObserver implements SistemaInteresado {

    @Override
    public void actualizar(Cluster cluster, Integer cantidad) {
        if (cluster.getVcpusDisponibles() < 0) {
            System.out.println(
                    "ALERTA: Cluster " + cluster.getIdCluster() +
                            " sin capacidad disponible");
        }
    }

}

package ar.edu.unahur.obj2.cloud;

public class AuditoriaObserver implements SistemaInteresado{

    @Override
    public void actualizar(Cluster cluster, Integer cantidad) {
        System.out.println(
            "AUDITORIA: Cluster " + cluster.getIdCluster() +
            " cambio de vCPUs: " + cantidad);
    }

}

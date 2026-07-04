package ar.edu.unahur.obj2.cloud;

public class SREObserver implements SistemaInteresado {

    @Override
    public void actualizar(Cluster cluster, Integer cantidad) {
        System.out.println(
                "SRE ALERT: Cluster " + cluster.getIdCluster() +
                " variación: " + cantidad);
    }

}

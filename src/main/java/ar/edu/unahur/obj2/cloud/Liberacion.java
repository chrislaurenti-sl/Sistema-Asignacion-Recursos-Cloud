package ar.edu.unahur.obj2.cloud;

public class Liberacion extends Operacion{

    public Liberacion(Cluster cluster, Integer vcpus) {
        super(cluster, vcpus);
    }

    @Override
    public void ejecutar() throws OverProvisioningException {
        cluster.liberarCapacidad(vcpus);
    }

    @Override
    public void deshacer() throws OverProvisioningException {
        cluster.asignarCapacidad(vcpus);
    }

}

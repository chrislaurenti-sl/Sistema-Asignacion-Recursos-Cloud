package ar.edu.unahur.obj2.cloud;

public class Asignacion extends Operacion{

    public Asignacion(Cluster cluster, Integer vcpus) {
        super(cluster, vcpus);
    }

    @Override
    public void ejecutar() throws OverProvisioningException {
        cluster.asignarCapacidad(vcpus);
    }

    @Override
    public void deshacer() throws OverProvisioningException {
        cluster.liberarCapacidad(vcpus);
    }

}

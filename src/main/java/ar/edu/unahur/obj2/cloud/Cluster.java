package ar.edu.unahur.obj2.cloud;

public class Cluster {

    private String idCluster;
    private Integer vcpusDisponibles;

    private static final Integer LIMITE_OVERPROVISIONING = -200;

    
    public Cluster(String idCluster, Integer vcpusDisponibles) {
        this.idCluster = idCluster;
        this.vcpusDisponibles = vcpusDisponibles;
    }

    public String getIdCluster() {
        return idCluster;
    }

    public Integer getVcpusDisponibles() {
        return vcpusDisponibles;
    }

    public void asignarCapacidad(Integer vcpu) throws OverProvisioningException{
        if(vcpusDisponibles - vcpu < LIMITE_OVERPROVISIONING){
            throw new OverProvisioningException("La cantidad de vcpus asignada supera el limite establecido: -200");
        }
        vcpusDisponibles -= vcpu;
    }

    public void liberarCapacidad(Integer vcpu){
        vcpusDisponibles += vcpu;
    }
}

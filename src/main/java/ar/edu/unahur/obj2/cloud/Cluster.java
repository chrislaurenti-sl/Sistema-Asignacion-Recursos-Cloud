package ar.edu.unahur.obj2.cloud;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private String idCluster;
    private Integer vcpusDisponibles;

    private static final Integer LIMITE_OVERPROVISIONING = -200;

    private List<SistemaInteresado> observadores = new ArrayList<>();
    
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
        notificarInteresados(-vcpu);
    }

    public void liberarCapacidad(Integer vcpu){
        vcpusDisponibles += vcpu;
        notificarInteresados(+vcpu);
    }

    //Observer
    public void suscribir(SistemaInteresado sistema) {
        observadores.add(sistema);
    }

    public void desuscribir(SistemaInteresado sistema) {
        observadores.remove(sistema);
    }

    private void notificarInteresados(Integer cantidad) {
        for (SistemaInteresado s : observadores) {
            s.actualizar(this, cantidad);
        }
    }
}

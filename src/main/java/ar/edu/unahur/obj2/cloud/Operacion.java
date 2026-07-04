package ar.edu.unahur.obj2.cloud;

public abstract class Operacion implements IOperacion{

    protected Cluster cluster;
    protected Integer vcpus;


    public Operacion(Cluster cluster, Integer vcpus) {
        
        if(vcpus <=0){
            throw new CantidadInvalidaException("La cantidad de vcpus debe ser mayor a 0");
        }
        
        this.cluster = cluster;
        this.vcpus = vcpus;
    }

    

}

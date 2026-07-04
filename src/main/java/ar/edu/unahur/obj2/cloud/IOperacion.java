package ar.edu.unahur.obj2.cloud;

public interface IOperacion {

    void ejecutar() throws OverProvisioningException;

    void deshacer() throws OverProvisioningException;

}

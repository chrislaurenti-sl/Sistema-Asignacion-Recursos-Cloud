package ar.edu.unahur.obj2.cloud;

/**
 * PlanificadorDespliegues
 */
public class PlanificadorDespliegues {

    public void ejecutar(IOperacion operacion) throws OverProvisioningException {
        operacion.ejecutar();
    }

}

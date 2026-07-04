package ar.edu.unahur.obj2.cloud;

import java.util.ArrayList;
import java.util.List;

public class PlanDespliegue implements IOperacion{

    private List<IOperacion> operaciones = new ArrayList<>();
    private List<IOperacion> operacionesExitosas = new ArrayList<>();

    public void agregarOperacion(IOperacion operacion){
        operaciones.add(operacion);
    }

    @Override
    public void ejecutar() throws OverProvisioningException {
        operacionesExitosas.clear();
        try {
            for (IOperacion op : operaciones) {
                op.ejecutar();
                operacionesExitosas.add(op);
            }
        } catch (OverProvisioningException e) {
            deshacer();
            operaciones.clear();
            throw e;
        }
        operaciones.clear();
    }

    @Override
    public void deshacer() throws OverProvisioningException {
        for (int i = operacionesExitosas.size() - 1; i >= 0; i--) {
            operacionesExitosas.get(i).deshacer();
        }
        operacionesExitosas.clear();
    }

    

}

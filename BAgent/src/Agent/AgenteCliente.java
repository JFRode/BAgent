package Agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */
public class AgenteCliente extends Agent{
    @Override
    protected void setup(){
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
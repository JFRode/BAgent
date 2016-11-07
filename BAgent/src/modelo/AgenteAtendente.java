package modelo;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */
public class AgenteAtendente extends Agent {

    public AgenteAtendente() {
        this.getAID().setLocalName("Atendente");
    }
        
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                // TODO
            }
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                // TODO
            }
        });
    }
}
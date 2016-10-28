package Agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */
public class AgenteGerente extends Agent{
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour(this) {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }
}

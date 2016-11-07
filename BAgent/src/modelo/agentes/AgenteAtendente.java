package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */
public class AgenteAtendente extends Agent {

    public AgenteAtendente(String nomeAtendente) {
        this.getAID().setLocalName(nomeAtendente);
    }
        
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                // Possivelmente nao tera oneShot
            }
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Vá atender por favor!")) {
                        // TODO ir atender
                    }
                }
            }
        });
    }
}
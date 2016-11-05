package Model;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */
public class AgenteCliente extends Agent {
    
    
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Gerente", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology(""); // verificar se é necessário
                msg.setContent("Quero ser atendido!");
                myAgent.send(msg);
            }
        });
    }
}
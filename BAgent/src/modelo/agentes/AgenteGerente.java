package modelo.agentes;

import jade.core.AID;
import visao.JanelaSimulacao;
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
public class AgenteGerente extends Agent{

    public AgenteGerente() {
        this.getAID().setLocalName("Gerente");
    }
    
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour(this) {

            @Override
            public void action() {
                //  Bem possivel que gerente não tenha OneShotBehavior
            }
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Quero ser atendido!")) {
                        if ((JanelaSimulacao.listaClientes.size() == 7 || 
                                JanelaSimulacao.listaClientes.size() == 13 || 
                                JanelaSimulacao.listaClientes.size() == 18) &&
                                JanelaSimulacao.listaAtendentes.size() > 0) {
                            // Envia mensagem para o atendente primeiro da lista ir atender
                            ACLMessage msgParaCliente = new ACLMessage(ACLMessage.INFORM);
                            msgParaCliente.addReceiver(new AID(JanelaSimulacao.listaAtendentes.get(0).getAID().getLocalName(), AID.ISLOCALNAME));
                            msgParaCliente.setLanguage("Português");
                            msgParaCliente.setOntology("a"); // verificar se é necessário
                            msgParaCliente.setContent("Vá atender por favor!");
                            myAgent.send(msgParaCliente);
                        }
                    }
                }
            }
        });
    }
}

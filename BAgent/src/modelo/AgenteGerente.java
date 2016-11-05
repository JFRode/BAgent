package modelo;

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

    private int qtdClientes;
    private int qtdAtendentesDisponiveis;

    public AgenteGerente() {
        this.qtdClientes = 0;
        this.qtdAtendentesDisponiveis = 0;
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
                        if (JanelaSimulacao.listaClientes.size() == 7 || JanelaSimulacao.listaClientes.size() == 13 || JanelaSimulacao.listaClientes.size() == 18) {
                            // Envia mensagem para o atendente ir atender
                            ACLMessage msgParaCliente = new ACLMessage(ACLMessage.INFORM);
                            msgParaCliente.addReceiver(new AID("Atendente", AID.ISLOCALNAME));
                            msgParaCliente.setLanguage("Português");
                            msgParaCliente.setOntology("a"); // verificar se é necessário
                            msgParaCliente.setContent("Alguém vá atender por favor!");
                            myAgent.send(msgParaCliente);
                            qtdAtendentesDisponiveis--;
                        }
                    }
                }
            }
        });
    }
}

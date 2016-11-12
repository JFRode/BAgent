package modelo.agentes;

import jade.core.AID;
import visao.JanelaSimulacao;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Ailton Cardoso Junior Antonio Roque Falcão Junior Joao Felipe
 * Gonçalves
 */
public class AgenteGerente extends Agent {

    private int ultimaSenha;

    public AgenteGerente() {
        this.getAID().setLocalName("Gerente");
        this.ultimaSenha = 0;
    }

    @Override
    protected void setup() {

        addBehaviour(new CyclicBehaviour(this) {

            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Quero uma senha para atendimento")) {

                        msg.getSender().setLocalName(String.valueOf(ultimaSenha));
                        enviaMensagem(myAgent, String.valueOf(ultimaSenha), "Sua senha é " + ultimaSenha);
                        ultimaSenha++;

                        int coeficiente = (JanelaSimulacao.listaClientesEmEspera.size() / JanelaSimulacao.listaAtendentesEmAtendimento.size());
                        
                        if (coeficiente >= 6 && JanelaSimulacao.listaAtendentesDisponiveis.size() > 0) {
                            enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");
                            
                        }else if (coeficiente <= 3 && !JanelaSimulacao.listaAtendentesEmAtendimento.get(0).emAtendimento()) {
                            enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesEmAtendimento.get(0).getAID().getLocalName(), "Feche o caixa e aguarde ser chamado novamente.");
                        }
                    }
                }
            }
        });
    }

    public void enviaMensagem(Agent myAgent, String destino, String mensagem) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(destino, AID.ISLOCALNAME));
        msg.setLanguage("Português");
        msg.setOntology("a");
        msg.setContent(mensagem);
        myAgent.send(msg);
    }
}

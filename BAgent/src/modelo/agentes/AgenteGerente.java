package modelo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JLabel;
import visao.JanelaSimulacao;

/**
 *
 * @author Ailton Cardoso Junior Antonio Roque Falcão Junior Joao Felipe
 * Gonçalves
 */
public class AgenteGerente extends Agent {

    private int ultimaSenha;
    private JLabel imagemIcone;

    public AgenteGerente() {
        this.ultimaSenha = 0;
    }

    @Override
    protected void setup() {
        imagemIcone = (JLabel) getArguments()[0];
        addBehaviour(new CyclicBehaviour(this) {

            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Quero uma senha para atendimento.")) {
                        //System.out.println("Gerente recebe: " + content);
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Sua senha é Cliente-" + ultimaSenha);
                        ultimaSenha++;

                        if (contarAtendentes() == 0) {
                            enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");

                        } else {
                            /*int coeficiente = (JanelaSimulacao.listaClientesEmEspera.size() / JanelaSimulacao.listaAtendentesEmAtendimento.size());

                            if (coeficiente >= 6 && JanelaSimulacao.listaAtendentesDisponiveis.size() > 0) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");
                            } else if (coeficiente <= 3 && !JanelaSimulacao.listaAtendentesEmAtendimento.get(0).emAtendimento()) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesEmAtendimento.get(0).getAID().getLocalName(), "Feche o caixa e aguarde ser chamado novamente.");
                            }*/
                            
                            if(JanelaSimulacao.listaClientesEmEspera.size() > 6 && contarAtendentes() == 1) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");
                            }else if(JanelaSimulacao.listaClientesEmEspera.size() > 12 && contarAtendentes() == 2){
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");
                            }
                            if(JanelaSimulacao.listaClientesEmEspera.size() < 7 && contarAtendentes() > 1){
                                //enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesEmAtendimento.get(0).getAID().getLocalName(), "Feche o caixa e aguarde ser chamado novamente.");
                            }else if(JanelaSimulacao.listaClientesEmEspera.size() < 13 && contarAtendentes() > 2){
                                //enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesEmAtendimento.get(0).getAID().getLocalName(), "Feche o caixa e aguarde ser chamado novamente.");
                            }
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
        System.out.println(getLocalName() + " para " + destino + ": " + msg.getContent());
    }
    public int contarAtendentes(){
        int cont = 0;
        for (AgenteAtendente agenteAtendente : JanelaSimulacao.listaAtendentesEmAtendimento) {
            if(agenteAtendente != null){
                cont++;
            }
        }
        return cont;
    }
}

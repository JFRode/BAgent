package modelo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JLabel tipGerente;
    private JLabel tipClienteGerente;

    public AgenteGerente() {
        this.ultimaSenha = 0;
    }

    @Override
    protected void setup() {
        imagemIcone = (JLabel) getArguments()[0];
        tipGerente = (JLabel) getArguments()[1];
        tipClienteGerente = (JLabel) getArguments()[2];
        addBehaviour(new CyclicBehaviour(this) {

            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Quero uma senha para atendimento.")) {
                        tipClienteGerente.setVisible(true);
                        tipClienteGerente.setText("<html>" + content);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteGerente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tipClienteGerente.setVisible(false);
                        //System.out.println("Gerente recebe: " + content);
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Sua senha é Cliente-" + ultimaSenha);
                        tipGerente.setVisible(true);
                        tipGerente.setText("<html> Sua senha é " + ultimaSenha);
                        ultimaSenha++;
                        
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteGerente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tipGerente.setVisible(false);
                        if (contarAtendentes() == 0) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AgenteGerente.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");

                        } else {
                            if (JanelaSimulacao.listaClientesEmEspera.size() > 6 && contarAtendentes() == 1) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");
                            } else if (JanelaSimulacao.listaClientesEmEspera.size() > 12 && contarAtendentes() == 2) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesDisponiveis.get(0).getAID().getLocalName(), "Vá atender por favor!");
                            }
                            if (JanelaSimulacao.listaClientesEmEspera.size() < 7 && contarAtendentes() > 1) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesControleDeIntervalo.get(0).getAID().getLocalName(), "Feche o caixa e aguarde ser chamado novamente.");
                            } else if (JanelaSimulacao.listaClientesEmEspera.size() < 13 && contarAtendentes() > 2) {
                                enviaMensagem(myAgent, JanelaSimulacao.listaAtendentesControleDeIntervalo.get(0).getAID().getLocalName(), "Feche o caixa e aguarde ser chamado novamente.");
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

    public int contarAtendentes() {
        int cont = 0;
        for (AgenteAtendente agenteAtendente : JanelaSimulacao.listaAtendentesEmAtendimento) {
            if (agenteAtendente != null) {
                cont++;
            }
        }
        return cont;
    }
}

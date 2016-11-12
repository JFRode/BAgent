package modelo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.JLabel;
import visao.JanelaSimulacao;

/**
 *
 * @author Ailton Cardoso Junior Antonio Roque Falcão Junior Joao Felipe
 * Gonçalves
 */
public class AgenteCliente extends Agent {

    private int qtdBoletos;
    private AgenteCliente aThis;
    private JLabel imagemIcone;

    public AgenteCliente(String nomeCliente, int qtdBoletos) {
        this.getAID().setLocalName(nomeCliente);
        this.qtdBoletos = qtdBoletos;
        this.aThis = this;
    }

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Gerente", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("a"); // verificar se é necessário
                msg.setContent("Quero uma senha para atendimento");
                myAgent.send(msg);
            }
        });

        addBehaviour(new CyclicBehaviour(this) {

            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Sua senha é " + getLocalName())) {

                        JanelaSimulacao.listaClientesEmEspera.add(aThis);
                        imagemIcone.setVisible(true);

                    } else if (content.equalsIgnoreCase("Próximo! Senha " + getLocalName())) {

                        imagemIcone.setVisible(false);
                        JanelaSimulacao.listaClientesEmEspera.remove(aThis);
                        JanelaSimulacao.listaClientesEmAtendimento.add(aThis);
                        imagemIcone.setVisible(true);
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Tenho boletos para pagar.");

                    } else if (content.equalsIgnoreCase("Boleto pago com sucesso, deseja pagar outro boleto?")) {

                        qtdBoletos--;
                        if (qtdBoletos > 0) {
                            enviaMensagem(myAgent, msg.getSender().getLocalName(), "Sim, desejo pagar mais um boleto.");
                        }
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Não tenho mais boletos para pagar.");

                    } else if (content.equalsIgnoreCase("Obrigado, volte sempre!")) {
                        JanelaSimulacao.listaClientesEmAtendimento.remove(aThis);
                        imagemIcone.setVisible(false);
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

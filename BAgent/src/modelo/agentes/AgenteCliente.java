package modelo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;
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

    public AgenteCliente() {
        Random rand = new Random();
        this.qtdBoletos = rand.nextInt(5) + 1;
        this.aThis = this;
        JanelaSimulacao.listaClientesEmEspera.add(this);
    }

    @Override
    protected void setup() {
        imagemIcone = (JLabel) getArguments()[0];
        addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Gerente", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("a"); // verificar se é necessário
                msg.setContent("Quero uma senha para atendimento.");
                System.out.println(getLocalName() + ": " + msg.getContent());
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
                        imagemIcone.setVisible(true);
                        System.out.println(getLocalName() + " recebe: " + content);
                    } else if (content.equalsIgnoreCase("Próximo! Senha " + getLocalName())) {
                        System.out.println(getLocalName() + " recebe: " + content);
                        imagemIcone.setVisible(false);
                        JanelaSimulacao.listaIconesClientes.add(imagemIcone);   //  Cadeira passa a ser disponivel a outro cliente
                        JanelaSimulacao.listaClientesEmEspera.remove(aThis);
                        JanelaSimulacao.listaClientesEmAtendimento.add(aThis);

                        //  Identifica o balcao de atendimento e se dirige a ele
                        String[] balcaoDeAtendimento = msg.getSender().getLocalName().split("-");
                        imagemIcone = JanelaSimulacao.listaAtendentesEmAtendimento.get(Integer.parseInt(balcaoDeAtendimento[1])).getImagemIconeCadeiraDeAtendimento();

                        imagemIcone.setVisible(true);
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Tenho boletos para pagar.");
                        System.out.println(getLocalName() + ": Tenho " + qtdBoletos);

                    } else if (content.equalsIgnoreCase("Boleto pago com sucesso, deseja pagar outro boleto?")) {
                        System.out.println(getLocalName() + " Recebe: " + content);
                        qtdBoletos--;
                        if (qtdBoletos > 0) {
                            enviaMensagem(myAgent, msg.getSender().getLocalName(), "Sim, desejo pagar mais um boleto.");
                        } else {
                            enviaMensagem(myAgent, msg.getSender().getLocalName(), "Não tenho mais boletos para pagar.");
                        }
                    } else if (content.equalsIgnoreCase("Obrigado, volte sempre!")) {
                        System.out.println(getLocalName() + " Recebe: " + content);
                        JanelaSimulacao.listaClientesEmAtendimento.remove(aThis);
                        imagemIcone.setVisible(false);
                        block();
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
}

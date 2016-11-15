package modelo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;
import visao.JanelaSimulacao;

/**
 *
 * @author  Ailton Cardoso Junior
 *          Antonio Roque Falcão Junior 
 *          Joao Felipe Gonçalves
 */
public class AgenteCliente extends Agent {

    private int qtdBoletos;
    private AgenteCliente aThis;
    private JLabel imagemIcone;
    private Icon icone;

    public AgenteCliente() {
        Random rand = new Random();
        this.qtdBoletos = rand.nextInt(5) + 1;
        this.aThis = this;
        JanelaSimulacao.listaClientesEmEspera.add(this);
        icone = new javax.swing.ImageIcon(getClass().getResource("/rsc/clientes/clienteIcon_" + (rand.nextInt(10)+1) +".png"));
    }

    @Override
    protected void setup() {
        imagemIcone = (JLabel) getArguments()[0];
        imagemIcone.setIcon(icone);
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
                        //System.out.println(getLocalName() + " recebe: " + content);
                        aguardar(2000);
                    } else if (content.equalsIgnoreCase("Próximo! Senha " + getLocalName())) {
                        //System.out.println(getLocalName() + " recebe: " + content);
                        imagemIcone.setVisible(false);
                        JanelaSimulacao.listaIconesClientes.add(imagemIcone);   //  Cadeira passa a ser disponivel a outro cliente
                        //JanelaSimulacao.listaClientesEmEspera.remove(aThis);
                        JanelaSimulacao.listaClientesEmAtendimento.add(aThis);

                        //  Identifica o balcao de atendimento e se dirige a ele
                        String[] balcaoDeAtendimento = msg.getSender().getLocalName().split("-");
                        System.out.println("OLHA  AMERDA AQUI " + Integer.parseInt(balcaoDeAtendimento[1]));
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        imagemIcone = JanelaSimulacao.listaAtendentesEmAtendimento[Integer.parseInt(balcaoDeAtendimento[1])].getImagemIconeCadeiraDeAtendimento();
                        imagemIcone.setIcon(icone);
                        imagemIcone.setVisible(true);
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Tenho boletos para pagar.");
                        System.out.println("\n" + getLocalName() + ": Tenho " + qtdBoletos + "\n\n");

                    } else if (content.equalsIgnoreCase("Boleto pago com sucesso, deseja pagar outro boleto?")) {
                        //System.out.println(getLocalName() + " Recebe: " + content);
                        qtdBoletos--;
                        if (qtdBoletos > 0) {
                            enviaMensagem(myAgent, msg.getSender().getLocalName(), "Sim, desejo pagar mais um boleto.");
                        } else {
                            enviaMensagem(myAgent, msg.getSender().getLocalName(), "Não tenho mais boletos para pagar.");
                        }
                    } else if (content.equalsIgnoreCase("Obrigado, volte sempre!")) {
                        //System.out.println(getLocalName() + " Recebe: " + content);
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

    public void aguardar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

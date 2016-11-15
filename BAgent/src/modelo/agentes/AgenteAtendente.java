package modelo.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import visao.JanelaSimulacao;

/**
 *
 * @author Ailton Cardoso Junior Antonio Roque Falcão Junior Joao Felipe
 * Gonçalves
 */
public class AgenteAtendente extends Agent {

    private AgenteAtendente aThis;
    private JLabel imagemIcone;
    private JLabel imagemIconeCadeiraDeAtendimento;
    private JLabel imagemIconeEscritorio;
    private Random random;
    private String cliente;
    private boolean emAtendimento = false;

    public AgenteAtendente() {
        JanelaSimulacao.listaAtendentesDisponiveis.add(this);
        this.random = new Random();
        this.aThis = this;
    }

    @Override
    protected void setup() {
        imagemIcone = (JLabel) getArguments()[0];
        imagemIconeCadeiraDeAtendimento = (JLabel) getArguments()[1];
        imagemIconeEscritorio = (JLabel) getArguments()[2];

        addBehaviour(new CyclicBehaviour(this) {
            String[] divisor = getAID().getLocalName().split("-");
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Vá atender por favor!")) {
                        //System.out.println(getLocalName() + " Recebe: " + content);
                        //JanelaSimulacao.listaAtendentesEmAtendimento.add(aThis);
                        JanelaSimulacao.listaAtendentesControleDeIntervalo.add(aThis);
                        JanelaSimulacao.listaAtendentesEmAtendimento[Integer.valueOf(divisor[1])] = aThis;
                        JanelaSimulacao.listaAtendentesDisponiveis.remove(aThis);
                        imagemIconeEscritorio.setVisible(false);
                        imagemIcone.setVisible(true);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        proximoCliente(myAgent);

                    } else if (content.equalsIgnoreCase("Tenho boletos para pagar.") || content.equalsIgnoreCase("Sim, desejo pagar mais um boleto.")) {
                        //System.out.println(getLocalName() + " Recebe: " + content);
                        try {
                            Thread.sleep(random.nextInt(5) * 1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        enviaMensagem(myAgent, cliente, "Boleto pago com sucesso, deseja pagar outro boleto?");
                    } else if (content.equalsIgnoreCase("Não tenho mais boletos para pagar.")) {
                        //System.out.println(getLocalName() + " recebe: " + content);
                        enviaMensagem(myAgent, cliente, "Obrigado, volte sempre!");
                        emAtendimento = false;

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        proximoCliente(myAgent);

                    } else if (content.equalsIgnoreCase("Feche o caixa e aguarde ser chamado novamente.")) {
                        //System.out.println(getLocalName() + " recebe: " + content);
                        imagemIcone.setVisible(false);
                        imagemIconeEscritorio.setVisible(true);
                        JanelaSimulacao.listaAtendentesEmAtendimento[Integer.valueOf(divisor[1])] = null;
                        JanelaSimulacao.listaAtendentesControleDeIntervalo.remove(aThis);
                        JanelaSimulacao.listaAtendentesDisponiveis.add(aThis);
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

    public void proximoCliente(Agent myAgent) {
        if (!JanelaSimulacao.listaClientesEmEspera.isEmpty()) {
            cliente = JanelaSimulacao.listaClientesEmEspera.get(0).getAID().getLocalName();
            JanelaSimulacao.listaClientesEmEspera.remove(0);
            enviaMensagem(myAgent, cliente, "Próximo! Senha " + cliente);
            emAtendimento = true;
        }
    }

    public boolean emAtendimento() {
        return emAtendimento;
    }

    public JLabel getImagemIconeCadeiraDeAtendimento() {
        return imagemIconeCadeiraDeAtendimento;
    }
}

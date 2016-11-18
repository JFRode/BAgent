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
    private JLabel tipAtendente;
    private JLabel tipCliente;
    private JLabel tipGerente;
    private Random random;
    private String cliente;
    private boolean emAtendimento = false;
    private boolean descansar = false;

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
        tipAtendente = (JLabel) getArguments()[3];
        tipCliente = (JLabel) getArguments()[4];
        tipGerente = (JLabel) getArguments()[5];

        addBehaviour(new CyclicBehaviour(this) {
            String[] divisor = getAID().getLocalName().split("-");

            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Vá atender por favor!")) {
                        tipGerente.setVisible(true);
                        tipGerente.setText("<html>"+content);
                        JanelaSimulacao.listaAtendentesControleDeIntervalo.add(aThis);
                        JanelaSimulacao.listaAtendentesEmAtendimento[Integer.valueOf(divisor[1])] = aThis;
                        JanelaSimulacao.listaAtendentesDisponiveis.remove(aThis);
                        imagemIconeEscritorio.setVisible(false);
                        imagemIcone.setVisible(true);
                        aguardar(1000);
                        proximoCliente(myAgent);
                        tipGerente.setVisible(false);

                    } else if (content.equalsIgnoreCase("Feche o caixa e aguarde ser chamado novamente.")) {
                        tipGerente.setVisible(true);
                        tipGerente.setText("<html>"+content);
                        descansar = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tipGerente.setVisible(false);
                    } else if (content.equalsIgnoreCase("Tenho boletos para pagar.") || content.equalsIgnoreCase("Sim, desejo pagar mais um boleto.")) {
                        tipCliente.setVisible(true);
                        tipCliente.setText("<html>" + content);
                        aguardar(random.nextInt(5) * 1000);
                        tipCliente.setVisible(false);
                        enviaMensagem(myAgent, cliente, "Boleto pago com sucesso, deseja pagar outro boleto?");

                        tipAtendente.setVisible(true);
                        tipAtendente.setText("<html>Boleto pago com sucesso, deseja pagar outro boleto?");
                        aguardar(1500);
                        tipAtendente.setVisible(false);
                    } else if (content.equalsIgnoreCase("Não tenho mais boletos para pagar.")) {
                        emAtendimento = false;
                        tipCliente.setVisible(true);
                        tipCliente.setText("<html>" + content);
                        aguardar(1000);
                        tipCliente.setVisible(false);
                        aguardar(500);
                        tipAtendente.setVisible(true);
                        tipAtendente.setText("<html> Obrigado, volte sempre!");
                        aguardar(1000);
                        tipAtendente.setVisible(false);
                        enviaMensagem(myAgent, cliente, "Obrigado, volte sempre!");
                        if (descansar) {
                            descansar(divisor[1]);
                        } else {
                            proximoCliente(myAgent);
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

    public void proximoCliente(Agent myAgent) {
        if (!JanelaSimulacao.listaClientesEmEspera.isEmpty()) {
            cliente = JanelaSimulacao.listaClientesEmEspera.get(0).getAID().getLocalName();

            String cx[] = aThis.getLocalName().split("-");
            int numeroCx = Integer.parseInt(cx[1]);
            numeroCx += 1;
            String guiche = Integer.toString(numeroCx);

            String pass[] = cliente.split("-");
            int password = Integer.parseInt(pass[1]);
            password += 1;
            String senha = Integer.toString(password);

            JanelaSimulacao.painelSenha.setText(" " + senha + "|" + guiche);

            JanelaSimulacao.listaClientesEmEspera.remove(0);
            enviaMensagem(myAgent, cliente, "Próximo! Senha " + cliente);
            tipAtendente.setVisible(true);
            tipAtendente.setText("<html> Próximo! Senha " + password);
            aguardar(1000);
            tipAtendente.setVisible(false);
            emAtendimento = true;
        }
    }

    public void descansar(String atendente) {
        imagemIcone.setVisible(false);
        imagemIconeEscritorio.setVisible(true);
        JanelaSimulacao.listaAtendentesEmAtendimento[Integer.valueOf(atendente)] = null;
        JanelaSimulacao.listaAtendentesControleDeIntervalo.remove(aThis);
        JanelaSimulacao.listaAtendentesDisponiveis.add(aThis);
    }

    public boolean emAtendimento() {
        return emAtendimento;
    }

    public JLabel getImagemIconeCadeiraDeAtendimento() {
        return imagemIconeCadeiraDeAtendimento;
    }

    public void exibirMensagem(String mensagem) {

    }

    public void aguardar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

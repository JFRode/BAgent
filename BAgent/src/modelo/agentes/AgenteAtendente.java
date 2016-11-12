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
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */

public class AgenteAtendente extends Agent {
    
    private AgenteAtendente aThis;
    private JLabel imagemIcone;
    private JLabel imagemIconeCadeiraDeAtendimento;
    private Random random;
    private String cliente;
    private boolean emAtendimento = false;
    
    public AgenteAtendente() {
        this.aThis = this;
    }
    
    @Override
    protected void setup() {
        
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Vá atender por favor!")) {
                        
                        JanelaSimulacao.listaAtendentesEmAtendimento.add(aThis);
                        imagemIcone.setVisible(true);
                        proximoCliente(myAgent);
                        
                    } else if (content.equalsIgnoreCase("Tenho boletos para pagar.") || content.equalsIgnoreCase("Sim, desejo pagar mais um boleto.")) {
                        
                        try {
                            Thread.sleep(random.nextInt(3) * 1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        enviaMensagem(myAgent, cliente, "Boleto pago com sucesso, deseja pagar outro boleto?");
                        
                    } else if (content.equalsIgnoreCase("Não tenho mais boletos para pagar.")) {
                        
                        enviaMensagem(myAgent, cliente, "Obrigado, volte sempre!");
                        emAtendimento = false;
                        
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AgenteAtendente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        proximoCliente(myAgent);
                        
                    } else if (content.equalsIgnoreCase("Feche o caixa e aguarde ser chamado novamente.")) {
                        
                        imagemIcone.setVisible(false);
                        JanelaSimulacao.listaAtendentesEmAtendimento.remove(aThis);
                        JanelaSimulacao.listaAtendentesDisponiveis.add(aThis);
                        imagemIcone.setVisible(true);
                        
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
    
    public void proximoCliente(Agent myAgent) {
        cliente = JanelaSimulacao.listaClientesEmEspera.get(0).getAID().getLocalName();
        enviaMensagem(myAgent, cliente, "Próximo! Senha " + cliente);
        emAtendimento = true;
    }
    
    public boolean emAtendimento() {
        return emAtendimento;
    }
    
    public JLabel getImagemIconeCadeiraDeAtendimento() {
        return imagemIconeCadeiraDeAtendimento;
    }
}

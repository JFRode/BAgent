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
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
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
    protected void setup(){
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
                        JanelaSimulacao.listaClientes.add(aThis);
                        imagemIcone.setVisible(true);
                    } else if (content.equalsIgnoreCase("Próximo! Senha " + getLocalName())) {
                        // Pegar o sender e saber de que mesa esta falando
                        // setvisiblefalse
                        // novo ponteiro de posição passa a ser a cadeira da frente do atendente
                        enviaMensagem(myAgent, msg.getSender().getLocalName(), "Tenho boletos para pagar.");
                        qtdBoletos--;
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
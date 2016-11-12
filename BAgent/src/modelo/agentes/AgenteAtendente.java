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
public class AgenteAtendente extends Agent {

    private AgenteAtendente aThis;
    private JLabel imagemIcone;
    
    /*public AgenteAtendente(String nomeAtendente) {
        this.aThis = this;
        this.getAID().setLocalName(nomeAtendente);
    }*/
        
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour() {

            @Override
            public void action() {
                // Chamar da fila
                // Criar metodo para isto, que sera chamado aqui e quando receber uma mensagem do cliente que nao ha mais boletos
            }
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Vá atender por favor!")) {
                        JanelaSimulacao.listaAtendentesAtendendo.add(aThis);
                        imagemIcone.setVisible(true);
                        String proximoCliente = JanelaSimulacao.listaClientes.get(0).getAID().getLocalName();
                        enviaMensagem(myAgent, proximoCliente, "Próximo! Senha " + proximoCliente);
                    } else if (content.equalsIgnoreCase("Tenho boletos para pagar.")) {
                        
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
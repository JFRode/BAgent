package modelo;

import visao.JanelaSimulacao;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */
public class AgenteGerente extends Agent{

    private int qtdClientes;

    public AgenteGerente() {
        this.qtdClientes = 0;
        this.getAID().setLocalName("Gerente");
    }
    
    @Override
    protected void setup(){
        addBehaviour(new OneShotBehaviour(this) {

            @Override
            public void action() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("Quero ser atendido!")) {
                        if (JanelaSimulacao.listaClientes.size() >= 6) {
                            // Envia mensagem para o atendente ir atender
                        }
                        
                    }
                }
            }
        });
    }
}

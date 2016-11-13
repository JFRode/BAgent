package factory;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import modelo.agentes.AgenteAtendente;
import modelo.agentes.AgenteCliente;
import modelo.agentes.AgenteGerente;
import visao.JanelaSimulacao;

/**
 *
 * @author Ailton Cardoso Junior
 *         Antonio Roque Falcão Junior
 *         Joao Felipe Gonçalves
 */

public class AgentFactory {

    private ContainerController cc;
    private AgentController agentController;

    public AgentFactory() {
        //Parametros para abrir o jade
        String[] parametros = {"-gui", "-local-host", "127.0.0.1"};
        jade.Boot.main(parametros);

        criarNovaAgencia();
        criarClientes();
    }

    public void criarNovaAgencia() {
        //JADE runtime interface (singleton)
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        //Profile que tem os parametros para criar o container
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, "Agencia");
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        //Cria o container contido no profile
        cc = runtime.createAgentContainer(profile);
        try {
            agentController = cc.createNewAgent("Gerente", AgenteGerente.class.getName(), new Object[]{JanelaSimulacao.Gerente});
            agentController.start();
            for (int i = 0; i < 3; i++) {
                
                agentController = cc.createNewAgent("Atendente-" + (i), AgenteAtendente.class.getName(), new Object[]{
                    JanelaSimulacao.listaIconesAtendentes.get(i),
                    JanelaSimulacao.listaIconesAtendimento.get(i), 
                    JanelaSimulacao.listaIconesEscritorio.get(i)});
                agentController.start();
            }
        } catch (StaleProxyException ex) {
            Logger.getLogger(AgentFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void criarClientes() {
        new Thread() {
            @Override
            public void run() {
                int contCliente = 0;
                Random rand = new Random();
                while (true) {
                    try {
                        int numCliente = rand.nextInt(2) + 1;
                        for (int i = 1; i <= numCliente; i++) {
                            JLabel label = JanelaSimulacao.listaIconesClientes.get(rand.nextInt(JanelaSimulacao.listaIconesClientes.size()));
                            JanelaSimulacao.listaIconesClientes.remove(label);
                            agentController = cc.createNewAgent("Cliente-" + contCliente, AgenteCliente.class.getName(), new Object[]{label});
                            agentController.start();
                            contCliente++;
                        }
                        sleep(15000000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AgentFactory.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (StaleProxyException ex) {
                        Logger.getLogger(AgentFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

}

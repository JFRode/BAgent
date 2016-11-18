package visao;

import factory.AgentFactory;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.agentes.AgenteAtendente;
import modelo.agentes.AgenteCliente;

public class JanelaSimulacao extends javax.swing.JFrame {

    public static List<JLabel> listaIconesClientes;
    public static List<JLabel> listaIconesAtendentes;
    public static List<JLabel> listaIconesAtendimento;
    public static List<JLabel> listaIconesEscritorio;
    public static List<JLabel> listaTipBalcao;
    public static List<AgenteCliente> listaClientesEmEspera;
    public static List<AgenteCliente> listaClientesEmAtendimento;
    public static List<AgenteAtendente> listaAtendentesDisponiveis;
    public static List<AgenteAtendente> listaAtendentesControleDeIntervalo;
    public static AgenteAtendente[] listaAtendentesEmAtendimento;
    private AgentFactory agentFactory;

    public JanelaSimulacao() throws InterruptedException {
        initComponents();
        this.setSize(830, 680);
        painelSenha.setVisible(true);
        painelSenha.setText(" 0|0");

        this.PainelAtendentes.setOpaque(false);
        this.PainelAtendimento.setOpaque(false);
        this.PainelClientes.setOpaque(false);
        this.PainelEscritorio.setOpaque(false);
        this.listaClientesEmEspera = new ArrayList(18);
        this.listaClientesEmAtendimento = new ArrayList(3);
        this.listaAtendentesDisponiveis = new ArrayList(3);
        this.listaIconesClientes = new ArrayList(18);
        this.listaIconesAtendentes = new ArrayList(3);
        this.listaAtendentesEmAtendimento = new AgenteAtendente[3];
        this.listaIconesAtendimento = new ArrayList(3);
        this.listaIconesEscritorio = new ArrayList(3);
        this.listaAtendentesControleDeIntervalo = new ArrayList();
        this.listaTipBalcao = new ArrayList<>(6);

        int cont = 0;
        for (Component component : PainelClientes.getComponents()) {
            listaIconesClientes.add((JLabel) component);
            component.setName("Cliente" + (cont++));
        }
        cont = 0;
        for (Component component : PainelAtendentes.getComponents()) {
            JLabel icone = (JLabel) component;
            icone.setVisible(false);
            listaIconesAtendentes.add(icone);
            component.setName("Atendente" + (cont++));
        }
        cont = 0;
        for (Component component : PainelAtendimento.getComponents()) {
            listaIconesAtendimento.add((JLabel) component);
            component.setName("Atendimento" + (cont++));
        }
        cont = 0;
        for (Component component : PainelEscritorio.getComponents()) {
            listaIconesEscritorio.add((JLabel) component);
            component.setName("Escritorio" + (cont++));
        }
        listaTipBalcao.add(TipAtendente1);
        listaTipBalcao.add(TipAtendente2);
        listaTipBalcao.add(TipAtendente3);
        listaTipBalcao.add(TipCliente1);
        listaTipBalcao.add(TipCliente2);
        listaTipBalcao.add(TipCliente3);
        
        this.agentFactory = new AgentFactory();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TipGerente = new javax.swing.JLabel();
        TipClienteGerente = new javax.swing.JLabel();
        TipAtendente1 = new javax.swing.JLabel();
        painelSenha = new javax.swing.JLabel();
        TipCliente1 = new javax.swing.JLabel();
        TipAtendente2 = new javax.swing.JLabel();
        TipCliente2 = new javax.swing.JLabel();
        TipAtendente3 = new javax.swing.JLabel();
        TipCliente3 = new javax.swing.JLabel();
        Gerente = new javax.swing.JLabel();
        PainelClientes = new javax.swing.JPanel();
        Cliente1 = new javax.swing.JLabel();
        Cliente2 = new javax.swing.JLabel();
        Cliente3 = new javax.swing.JLabel();
        Cliente4 = new javax.swing.JLabel();
        Cliente5 = new javax.swing.JLabel();
        Cliente6 = new javax.swing.JLabel();
        Cliente7 = new javax.swing.JLabel();
        Cliente8 = new javax.swing.JLabel();
        Cliente9 = new javax.swing.JLabel();
        Cliente10 = new javax.swing.JLabel();
        Cliente11 = new javax.swing.JLabel();
        Cliente12 = new javax.swing.JLabel();
        Cliente13 = new javax.swing.JLabel();
        Cliente14 = new javax.swing.JLabel();
        Cliente15 = new javax.swing.JLabel();
        Cliente16 = new javax.swing.JLabel();
        Cliente17 = new javax.swing.JLabel();
        Cliente18 = new javax.swing.JLabel();
        PainelAtendentes = new javax.swing.JPanel();
        Atendente1 = new javax.swing.JLabel();
        Atendente2 = new javax.swing.JLabel();
        Atendente3 = new javax.swing.JLabel();
        PainelAtendimento = new javax.swing.JPanel();
        Cadeira1 = new javax.swing.JLabel();
        Cadeira2 = new javax.swing.JLabel();
        Cadeira3 = new javax.swing.JLabel();
        PainelEscritorio = new javax.swing.JPanel();
        Escritorio1 = new javax.swing.JLabel();
        Escritorio2 = new javax.swing.JLabel();
        Escritorio3 = new javax.swing.JLabel();
        labelBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BAgent - GitHub: /Ailtonjr   /AntonioFalcao   /JFRode");
        getContentPane().setLayout(null);

        TipGerente.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipGerente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipGerente);
        TipGerente.setBounds(520, 20, 90, 50);

        TipClienteGerente.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipClienteGerente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipClienteGerente);
        TipClienteGerente.setBounds(500, 280, 110, 40);

        TipAtendente1.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipAtendente1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipAtendente1);
        TipAtendente1.setBounds(30, 10, 130, 30);

        painelSenha.setBackground(new java.awt.Color(255, 0, 0));
        painelSenha.setFont(new java.awt.Font("Consolas", 1, 20)); // NOI18N
        painelSenha.setForeground(new java.awt.Color(255, 0, 0));
        painelSenha.setToolTipText("");
        getContentPane().add(painelSenha);
        painelSenha.setBounds(450, 30, 70, 40);

        TipCliente1.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipCliente1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipCliente1);
        TipCliente1.setBounds(40, 240, 110, 40);

        TipAtendente2.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipAtendente2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipAtendente2);
        TipAtendente2.setBounds(170, 10, 130, 30);

        TipCliente2.setBackground(new java.awt.Color(187, 187, 187));
        TipCliente2.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipCliente2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipCliente2);
        TipCliente2.setBounds(180, 240, 110, 40);

        TipAtendente3.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipAtendente3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipAtendente3);
        TipAtendente3.setBounds(310, 10, 130, 30);

        TipCliente3.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        TipCliente3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TipCliente3);
        TipCliente3.setBounds(320, 240, 110, 40);

        Gerente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/gerenteIcon.png"))); // NOI18N
        Gerente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(Gerente);
        Gerente.setBounds(520, 80, 60, 70);

        PainelClientes.setLayout(null);

        Cliente1.setName(""); // NOI18N
        PainelClientes.add(Cliente1);
        Cliente1.setBounds(10, 10, 70, 70);
        PainelClientes.add(Cliente2);
        Cliente2.setBounds(90, 10, 70, 70);
        PainelClientes.add(Cliente3);
        Cliente3.setBounds(170, 10, 70, 70);
        PainelClientes.add(Cliente4);
        Cliente4.setBounds(250, 10, 70, 70);
        PainelClientes.add(Cliente5);
        Cliente5.setBounds(330, 10, 70, 70);
        PainelClientes.add(Cliente6);
        Cliente6.setBounds(400, 10, 70, 70);
        PainelClientes.add(Cliente7);
        Cliente7.setBounds(10, 90, 70, 70);
        PainelClientes.add(Cliente8);
        Cliente8.setBounds(90, 90, 70, 70);
        PainelClientes.add(Cliente9);
        Cliente9.setBounds(170, 90, 70, 70);
        PainelClientes.add(Cliente10);
        Cliente10.setBounds(250, 90, 70, 70);
        PainelClientes.add(Cliente11);
        Cliente11.setBounds(330, 90, 70, 70);
        PainelClientes.add(Cliente12);
        Cliente12.setBounds(400, 90, 70, 70);
        PainelClientes.add(Cliente13);
        Cliente13.setBounds(10, 170, 70, 70);
        PainelClientes.add(Cliente14);
        Cliente14.setBounds(90, 170, 70, 70);
        PainelClientes.add(Cliente15);
        Cliente15.setBounds(170, 170, 70, 70);
        PainelClientes.add(Cliente16);
        Cliente16.setBounds(250, 170, 70, 70);
        PainelClientes.add(Cliente17);
        Cliente17.setBounds(330, 170, 70, 70);
        PainelClientes.add(Cliente18);
        Cliente18.setBounds(400, 170, 70, 70);

        getContentPane().add(PainelClientes);
        PainelClientes.setBounds(20, 320, 480, 270);

        PainelAtendentes.setLayout(null);

        Atendente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/atendenteIcon.png"))); // NOI18N
        PainelAtendentes.add(Atendente1);
        Atendente1.setBounds(6, 6, 72, 76);

        Atendente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/atendenteIcon.png"))); // NOI18N
        PainelAtendentes.add(Atendente2);
        Atendente2.setBounds(145, 6, 72, 76);

        Atendente3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/atendenteIcon.png"))); // NOI18N
        PainelAtendentes.add(Atendente3);
        Atendente3.setBounds(288, 6, 76, 78);

        getContentPane().add(PainelAtendentes);
        PainelAtendentes.setBounds(40, 30, 370, 90);

        PainelAtendimento.setLayout(null);
        PainelAtendimento.add(Cadeira1);
        Cadeira1.setBounds(6, 0, 72, 76);
        PainelAtendimento.add(Cadeira2);
        Cadeira2.setBounds(152, 0, 72, 76);
        PainelAtendimento.add(Cadeira3);
        Cadeira3.setBounds(294, 0, 72, 76);

        getContentPane().add(PainelAtendimento);
        PainelAtendimento.setBounds(40, 170, 380, 80);

        PainelEscritorio.setLayout(null);

        Escritorio1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/atendenteIcon_downesquerda.png"))); // NOI18N
        PainelEscritorio.add(Escritorio1);
        Escritorio1.setBounds(102, 0, 72, 76);

        Escritorio2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/atendenteIcon_topesquerda.png"))); // NOI18N
        PainelEscritorio.add(Escritorio2);
        Escritorio2.setBounds(102, 108, 72, 76);

        Escritorio3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/atendenteIcon_topdireita.png"))); // NOI18N
        PainelEscritorio.add(Escritorio3);
        Escritorio3.setBounds(6, 108, 72, 76);

        getContentPane().add(PainelEscritorio);
        PainelEscritorio.setBounds(620, 10, 180, 190);

        labelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/bancoModelo.png"))); // NOI18N
        getContentPane().add(labelBackground);
        labelBackground.setBounds(6, 6, 800, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JanelaSimulacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(JanelaSimulacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JanelaSimulacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JanelaSimulacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JanelaSimulacao().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JanelaSimulacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Atendente1;
    private javax.swing.JLabel Atendente2;
    private javax.swing.JLabel Atendente3;
    private javax.swing.JLabel Cadeira1;
    private javax.swing.JLabel Cadeira2;
    private javax.swing.JLabel Cadeira3;
    private javax.swing.JLabel Cliente1;
    private javax.swing.JLabel Cliente10;
    private javax.swing.JLabel Cliente11;
    private javax.swing.JLabel Cliente12;
    private javax.swing.JLabel Cliente13;
    private javax.swing.JLabel Cliente14;
    private javax.swing.JLabel Cliente15;
    private javax.swing.JLabel Cliente16;
    private javax.swing.JLabel Cliente17;
    private javax.swing.JLabel Cliente18;
    private javax.swing.JLabel Cliente2;
    private javax.swing.JLabel Cliente3;
    private javax.swing.JLabel Cliente4;
    private javax.swing.JLabel Cliente5;
    private javax.swing.JLabel Cliente6;
    private javax.swing.JLabel Cliente7;
    private javax.swing.JLabel Cliente8;
    private javax.swing.JLabel Cliente9;
    private javax.swing.JLabel Escritorio1;
    private javax.swing.JLabel Escritorio2;
    private javax.swing.JLabel Escritorio3;
    public static javax.swing.JLabel Gerente;
    private javax.swing.JPanel PainelAtendentes;
    private javax.swing.JPanel PainelAtendimento;
    private javax.swing.JPanel PainelClientes;
    private javax.swing.JPanel PainelEscritorio;
    public static javax.swing.JLabel TipAtendente1;
    public static javax.swing.JLabel TipAtendente2;
    public static javax.swing.JLabel TipAtendente3;
    public static javax.swing.JLabel TipCliente1;
    public static javax.swing.JLabel TipCliente2;
    public static javax.swing.JLabel TipCliente3;
    public static javax.swing.JLabel TipClienteGerente;
    public static javax.swing.JLabel TipGerente;
    private javax.swing.JLabel labelBackground;
    public static javax.swing.JLabel painelSenha;
    // End of variables declaration//GEN-END:variables
}

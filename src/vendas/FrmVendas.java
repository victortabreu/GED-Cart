/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendas;

import atos.FrmListaAtos;
import caixa.FrmLivroCaixa;
import caixa.ServicosSql;
import despesas.DespesasSql;
import despesas.FrmDespesas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import static principal.MenuPrincipal.carregador;

/**
 *
 * @author hugov
 */
public class FrmVendas extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmVendas
     */
    public FrmVendas() {

        initComponents();
        tabela.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabela.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela1.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabela1.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());

        tabela1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        limparCampos();

    }

    public boolean estaFechado(Object obj) {
        JInternalFrame[] ativos = carregador.getAllFrames();
        boolean fechado = true;
        int i = 0;
        while (i < ativos.length && fechado) {
            if (ativos[i] == obj) {
                fechado = false;
            }
            i++;
        }
        return fechado;
    }

    void limparCampos() {
        if (tabela.getSelectedRow() > -1) {
            tabela.removeRowSelectionInterval(tabela.getSelectedRow(), tabela.getSelectedRow());
        }
        if (tabela1.getSelectedRow() > -1) {
            tabela1.removeRowSelectionInterval(tabela1.getSelectedRow(), tabela1.getSelectedRow());
        }
        data.setDate(null);
        data2.setDate(null);
        buscar.setText("");
        ServicosSql.listar("");
        DespesasSql.listarDepesas("");
        calcular();
        calcularDespesas();
    }

    public static void calcularDespesas() {
        String dataF;
        String valorS;
        double valor = 0.0;
        double total = 0.0;
        for (int i = 0; i < vendas.FrmVendas.tabela1.getRowCount(); i++) {
            dataF = vendas.FrmVendas.tabela1.getValueAt(i, 0).toString();
            if (dataF.contains("-")) {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date dateF = null;
                try {
                    dateF = formato.parse(dataF);
                } catch (ParseException ex) {
                    Logger.getLogger(FrmVendas.class.getName()).log(Level.SEVERE, null, ex);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dataF = String.valueOf(sdf.format(dateF));

                vendas.FrmVendas.tabela1.setValueAt(dataF, i, 0);
            }

            valorS = vendas.FrmVendas.tabela1.getValueAt(i, 3).toString();
            valor = Double.parseDouble(valorS);
            vendas.FrmVendas.tabela1.setValueAt("R$ " + Math.rint(valor * 100) / 100, i, 3);

            total += valor;
        }
        totalDes.setText("R$ " + Math.rint(total * 100) / 100);
    }

    public void calcular() {
        String emolBruto;
        String quant;
        String recompeMG;
        String emolLiquido;
        String tfj1;
        int quantidade;
        double total = 0;
        double bruto;
        double recompe;
        double liquido;
        double tfj;
        double calcB = 0.0;
        double calcR = 0.0;
        double calcL = 0.0;
        double calcT = 0.0;
        double totalBruto = 0.0;
        double totalRecomp = 0.0;
        double totalLiquido = 0.0;
        double totalTfj = 0.0;
        double totalReceita = 0.0;
        String dataF;

        for (int i = 0; i < vendas.FrmVendas.tabela.getRowCount(); i++) {
            quant = vendas.FrmVendas.tabela.getValueAt(i, 4).toString();
            emolBruto = vendas.FrmVendas.tabela.getValueAt(i, 5).toString();
            recompeMG = vendas.FrmVendas.tabela.getValueAt(i, 6).toString();
            emolLiquido = vendas.FrmVendas.tabela.getValueAt(i, 7).toString();
            tfj1 = vendas.FrmVendas.tabela.getValueAt(i, 8).toString();
            dataF = vendas.FrmVendas.tabela.getValueAt(i, 0).toString();

            if (dataF.contains("-")) {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date dateF = null;
                try {
                    dateF = formato.parse(dataF);
                } catch (ParseException ex) {
                    Logger.getLogger(FrmVendas.class.getName()).log(Level.SEVERE, null, ex);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dataF = String.valueOf(sdf.format(dateF));

                vendas.FrmVendas.tabela.setValueAt(dataF, i, 0);
            }

            quantidade = Integer.parseInt(quant);
            bruto = Double.parseDouble(emolBruto);
            recompe = Double.parseDouble(recompeMG);
            liquido = Double.parseDouble(emolLiquido);
            tfj = Double.parseDouble(tfj1);

            calcB = bruto * quantidade;
            vendas.FrmVendas.tabela.setValueAt("R$ " + Math.rint(calcB * 100) / 100, i, 5);

            calcR = recompe * quantidade;
            vendas.FrmVendas.tabela.setValueAt("R$ " + Math.rint(calcR * 100) / 100, i, 6);

            calcL = liquido * quantidade;
            vendas.FrmVendas.tabela.setValueAt("R$ " + Math.rint(calcL * 100) / 100, i, 7);

            calcT = tfj * quantidade;
            vendas.FrmVendas.tabela.setValueAt("R$ " + Math.rint(calcT * 100) / 100, i, 8);

            total = calcR + calcL + calcT;
            vendas.FrmVendas.tabela.setValueAt("R$ " + Math.rint(total * 100) / 100, i, 9);

            totalBruto += calcB;
            totalRecomp += calcR;
            totalLiquido += calcL;
            totalTfj += calcT;
            totalReceita += total;
        }
        emolBrutoT.setText("R$ " + Math.rint(totalBruto * 100) / 100);
        recompeT.setText("R$ " + Math.rint(totalRecomp * 100) / 100);
        emolLiquidoT.setText("R$ " + Math.rint(totalLiquido * 100) / 100);
        tfjT.setText("R$ " + Math.rint(totalTfj * 100) / 100);
        receita.setText("R$ " + Math.rint(totalReceita * 100) / 100);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        buscar = new app.bolivia.swing.JCTextField();
        codigoL1 = new javax.swing.JLabel();
        eliminar = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
        buscF = new javax.swing.JButton();
        ventasH = new javax.swing.JButton();
        data = new com.toedter.calendar.JDateChooser();
        data2 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        insereDespesa = new javax.swing.JButton();
        insereDespesa1 = new javax.swing.JButton();
        insereDespesa2 = new javax.swing.JButton();
        data1 = new com.toedter.calendar.JDateChooser();
        data3 = new com.toedter.calendar.JDateChooser();
        buscF1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela1 = new javax.swing.JTable();
        emolBrutoT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        recompeT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        emolLiquidoT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfjT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        receita = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        totalDes = new javax.swing.JTextField();
        livroCaixa = new javax.swing.JButton();

        setClosable(true);
        setTitle("Receitas / Despesas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DATA", "ID RECEITA", "DOCUMENTO", "ATO", "QUANTIDADE", "EMOL. BRUTO", "RECOMPE", "EMOL. LÍQUIDO", "TFJ", "VALOR TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tabela);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "OPÇÕES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buscar.setBackground(new java.awt.Color(34, 102, 145));
        buscar.setBorder(null);
        buscar.setForeground(new java.awt.Color(255, 255, 255));
        buscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscar.setOpaque(false);
        buscar.setPhColor(new java.awt.Color(255, 255, 255));
        buscar.setPlaceholder("NÚMERO RECEITA");
        buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buscarMouseClicked(evt);
            }
        });
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                buscarKeyTyped(evt);
            }
        });
        jPanel4.add(buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 180, -1));

        codigoL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codigoL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/buscarL.png"))); // NOI18N
        jPanel4.add(codigoL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 250, 52));

        eliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT - Copia.png"))); // NOI18N
        eliminar.setText("Excluir receita");
        eliminar.setBorder(null);
        eliminar.setBorderPainted(false);
        eliminar.setContentAreaFilled(false);
        eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        eliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT1 - Copia.png"))); // NOI18N
        eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        jPanel4.add(eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        limpiar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        limpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/limpar - Copia.png"))); // NOI18N
        limpiar.setText("Limpar Campos");
        limpiar.setBorder(null);
        limpiar.setBorderPainted(false);
        limpiar.setContentAreaFilled(false);
        limpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        limpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        limpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/limpar1 - Copia.png"))); // NOI18N
        limpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });
        jPanel4.add(limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, -1, -1));

        buscF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buscF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/buscaF1.png"))); // NOI18N
        buscF.setToolTipText("Buscar");
        buscF.setBorder(null);
        buscF.setBorderPainted(false);
        buscF.setContentAreaFilled(false);
        buscF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buscF.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/buscaF2.png"))); // NOI18N
        buscF.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buscF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscFActionPerformed(evt);
            }
        });
        jPanel4.add(buscF, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, -1, -1));

        ventasH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ventasH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/vendasH.png"))); // NOI18N
        ventasH.setToolTipText("Buscar");
        ventasH.setBorder(null);
        ventasH.setBorderPainted(false);
        ventasH.setContentAreaFilled(false);
        ventasH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ventasH.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ventasH.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/vendasH1.png"))); // NOI18N
        ventasH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ventasH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventasHActionPerformed(evt);
            }
        });
        jPanel4.add(ventasH, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, -1, -1));

        data.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(data, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, 30));

        data2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(data2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 150, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Pesquisar por período:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel11.setText("De:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel12.setText("Até:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        insereDespesa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        insereDespesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT - Copia.png"))); // NOI18N
        insereDespesa.setText("Inserir Despesa");
        insereDespesa.setBorder(null);
        insereDespesa.setBorderPainted(false);
        insereDespesa.setContentAreaFilled(false);
        insereDespesa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        insereDespesa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        insereDespesa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT1 - Copia.png"))); // NOI18N
        insereDespesa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        insereDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insereDespesaActionPerformed(evt);
            }
        });
        jPanel4.add(insereDespesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        insereDespesa1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        insereDespesa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT - Copia.png"))); // NOI18N
        insereDespesa1.setText("Excluir Despesa");
        insereDespesa1.setBorder(null);
        insereDespesa1.setBorderPainted(false);
        insereDespesa1.setContentAreaFilled(false);
        insereDespesa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        insereDespesa1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        insereDespesa1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT1 - Copia.png"))); // NOI18N
        insereDespesa1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        insereDespesa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insereDespesa1ActionPerformed(evt);
            }
        });
        jPanel4.add(insereDespesa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, -1, -1));

        insereDespesa2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        insereDespesa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT - Copia.png"))); // NOI18N
        insereDespesa2.setText("Editar Despesa");
        insereDespesa2.setBorder(null);
        insereDespesa2.setBorderPainted(false);
        insereDespesa2.setContentAreaFilled(false);
        insereDespesa2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        insereDespesa2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        insereDespesa2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT1 - Copia.png"))); // NOI18N
        insereDespesa2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        insereDespesa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insereDespesa2ActionPerformed(evt);
            }
        });
        jPanel4.add(insereDespesa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, -1, -1));

        data1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(data1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 150, 30));

        data3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(data3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 150, 30));

        buscF1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buscF1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/buscaF1.png"))); // NOI18N
        buscF1.setToolTipText("Buscar");
        buscF1.setBorder(null);
        buscF1.setBorderPainted(false);
        buscF1.setContentAreaFilled(false);
        buscF1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscF1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buscF1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/buscaF2.png"))); // NOI18N
        buscF1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buscF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscF1ActionPerformed(evt);
            }
        });
        jPanel4.add(buscF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, -1, -1));

        tabela1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DATA", "ID DESPESA", "HISTÓRICO", "VALOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(tabela1);

        emolBrutoT.setBackground(new java.awt.Color(0, 102, 153));
        emolBrutoT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        emolBrutoT.setForeground(new java.awt.Color(255, 255, 255));
        emolBrutoT.setToolTipText("");
        emolBrutoT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        emolBrutoT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emolBrutoTActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Emol. Bruto");

        recompeT.setBackground(new java.awt.Color(0, 102, 153));
        recompeT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recompeT.setForeground(new java.awt.Color(255, 255, 255));
        recompeT.setToolTipText("");
        recompeT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        recompeT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recompeTActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Recompe");

        emolLiquidoT.setBackground(new java.awt.Color(0, 102, 153));
        emolLiquidoT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        emolLiquidoT.setForeground(new java.awt.Color(255, 255, 255));
        emolLiquidoT.setToolTipText("");
        emolLiquidoT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        emolLiquidoT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emolLiquidoTActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Emol. Líquido");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Total de receitas do período selecionado:");

        tfjT.setBackground(new java.awt.Color(0, 102, 153));
        tfjT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tfjT.setForeground(new java.awt.Color(255, 255, 255));
        tfjT.setToolTipText("");
        tfjT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfjT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfjTActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("TFJ");

        receita.setBackground(new java.awt.Color(0, 102, 153));
        receita.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        receita.setForeground(new java.awt.Color(255, 255, 255));
        receita.setToolTipText("");
        receita.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        receita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receitaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Receita");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Total de despesas do período selecionado:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Total Despesas");

        totalDes.setBackground(new java.awt.Color(0, 102, 153));
        totalDes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalDes.setForeground(new java.awt.Color(255, 255, 255));
        totalDes.setToolTipText("");
        totalDes.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        totalDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalDesActionPerformed(evt);
            }
        });

        livroCaixa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        livroCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT - Copia.png"))); // NOI18N
        livroCaixa.setText("Livro Caixa");
        livroCaixa.setBorder(null);
        livroCaixa.setBorderPainted(false);
        livroCaixa.setContentAreaFilled(false);
        livroCaixa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        livroCaixa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        livroCaixa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagarT1 - Copia.png"))); // NOI18N
        livroCaixa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        livroCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                livroCaixaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emolBrutoT, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(recompeT, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emolLiquidoT, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfjT, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(receita, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 42, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalDes, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(livroCaixa)
                        .addGap(281, 281, 281)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emolBrutoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(recompeT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(emolLiquidoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(tfjT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(receita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalDes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(livroCaixa))
                        .addGap(38, 38, 38))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarMouseClicked
        data.setDate(null);
        ServicosSql.listar("");
        calcular();
    }//GEN-LAST:event_buscarMouseClicked

    private void buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarKeyReleased
        ServicosSql.listar(buscar.getText());
        calcular();
    }//GEN-LAST:event_buscarKeyReleased

    private void buscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarKeyTyped

    }//GEN-LAST:event_buscarKeyTyped

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        if (tabela.getRowCount() > 0) {
            if (tabela.getSelectedRowCount() > 0) {
                if (JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta venda?", "Receitas", JOptionPane.YES_NO_OPTION, 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/erro.png"))) == JOptionPane.YES_OPTION) {
                    int linha = tabela.getSelectedRow();
                    String id = tabela.getValueAt(linha, 1).toString();
                    String ato = tabela.getValueAt(linha, 3).toString();
                    int elimina = ServicosSql.eliminar(id, ato);
                    if (elimina != 0) {
                        limparCampos();
                        JOptionPane.showMessageDialog(this, "Receita Excluída", "Receitas", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));

                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um Registro", "Receitas", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Não há vendas para excluir", "Vendas", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_eliminarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        limparCampos();
    }//GEN-LAST:event_limpiarActionPerformed

    private void buscFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscFActionPerformed
        if (data.getDate() == null) {
            ServicosSql.listar("");
            calcular();
        } else {
            //String formato = data.getDateFormatString();
            Date date = data.getDate();
            Date date2 = data2.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("YYY-MM-dd");
            ServicosSql.listarData(String.valueOf(sdf.format(date)), String.valueOf(sdf.format(date2)));
            calcular();
        }
    }//GEN-LAST:event_buscFActionPerformed

    private void ventasHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ventasHActionPerformed
        Date sistemaData = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("YYY-MM-dd");
        String dataH = formato.format(sistemaData);
        ServicosSql.listarDataH(dataH);
        calcular();
        data.setDate(null);
        data2.setDate(null);
    }//GEN-LAST:event_ventasHActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarActionPerformed

    private void emolBrutoTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emolBrutoTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emolBrutoTActionPerformed

    private void recompeTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recompeTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recompeTActionPerformed

    private void emolLiquidoTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emolLiquidoTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emolLiquidoTActionPerformed

    private void tfjTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfjTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfjTActionPerformed

    private void receitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receitaActionPerformed

    private void totalDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalDesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalDesActionPerformed
    despesas.FrmDespesas lista;
    private void insereDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insereDespesaActionPerformed
        if (estaFechado(lista)) {
            lista = new FrmDespesas();
            principal.MenuPrincipal.carregador.add(lista).setLocation(150, 10);

            lista.toFront();
            lista.setVisible(true);
        } else {
            lista.toFront();

        }
    }//GEN-LAST:event_insereDespesaActionPerformed
    caixa.FrmLivroCaixa lista1;
    private void livroCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_livroCaixaActionPerformed
        if (estaFechado(lista1)) {
            lista1 = new FrmLivroCaixa();
            principal.MenuPrincipal.carregador.add(lista1).setLocation(150, 10);

            lista1.toFront();
            lista1.setVisible(true);
        } else {
            lista.toFront();

        }
    }//GEN-LAST:event_livroCaixaActionPerformed

    private void insereDespesa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insereDespesa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_insereDespesa1ActionPerformed

    private void insereDespesa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insereDespesa2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_insereDespesa2ActionPerformed

    private void buscF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscF1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscF1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscF;
    private javax.swing.JButton buscF1;
    private app.bolivia.swing.JCTextField buscar;
    private javax.swing.JLabel codigoL1;
    private com.toedter.calendar.JDateChooser data;
    private com.toedter.calendar.JDateChooser data1;
    private com.toedter.calendar.JDateChooser data2;
    private com.toedter.calendar.JDateChooser data3;
    private javax.swing.JButton eliminar;
    private javax.swing.JTextField emolBrutoT;
    private javax.swing.JTextField emolLiquidoT;
    private javax.swing.JButton insereDespesa;
    private javax.swing.JButton insereDespesa1;
    private javax.swing.JButton insereDespesa2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton limpiar;
    private javax.swing.JButton livroCaixa;
    private javax.swing.JTextField receita;
    private javax.swing.JTextField recompeT;
    public static javax.swing.JTable tabela;
    public static javax.swing.JTable tabela1;
    private javax.swing.JTextField tfjT;
    private static javax.swing.JTextField totalDes;
    private javax.swing.JButton ventasH;
    // End of variables declaration//GEN-END:variables
}

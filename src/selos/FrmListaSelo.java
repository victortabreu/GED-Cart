/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selos;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hugov
 */
public class FrmListaSelo extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmListaProd
     */
    public FrmListaSelo() {
        initComponents();
        tabela.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabela.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        SelosSql.listarCat("");
        
        tipo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (tipo.getSelectedIndex() == 0) {
                   
                    SelosSql.listarCat("");
                }    
                 
                 if (tipo.getSelectedIndex() != 0) {
                   
                    SelosSql.listarCat(tipo.getSelectedItem().toString());
                }
                
            }
        });
    }  
    String quant = null;
    String valor = null;
    public void calcular() {
        double total = 0;
        double preco;
        int quantidade;
        double calc = 0.0;

        for (int i = 0; i < vendas.FrmCaixa.tabela.getRowCount(); i++) {
            valor = vendas.FrmCaixa.tabela.getValueAt(i, 4).toString();
            quant = vendas.FrmCaixa.tabela.getValueAt(i, 3).toString();
             if(valor.equals("----")){
                calc = 0;
                vendas.FrmCaixa.tabela.setValueAt("----", i, 5);
            }else{
                preco = Double.parseDouble(valor);
                quantidade = Integer.parseInt(quant);
                calc = preco * quantidade;
                total = total + calc;
                vendas.FrmCaixa.tabela.setValueAt(Math.rint(calc * 100) / 100, i, 5);
            }

        }
        vendas.FrmCaixa.total.setText("" + Math.rint(total * 100) / 100);

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
        tipo = new org.bolivia.combo.SComboBoxBlue();
        tipoL = new javax.swing.JLabel();
        enviar = new javax.swing.JButton();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(715, 400));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "TIPO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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
        buscar.setPlaceholder("CÓDIGO/NOME");
        buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarKeyReleased(evt);
            }
        });
        jPanel4.add(buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 180, -1));

        codigoL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codigoL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/buscarL.png"))); // NOI18N
        jPanel4.add(codigoL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 250, 52));

        tipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIPO ", "CERTIDÃO DE NASCIMENTO", "CERTIDÃO DE ÓBITO", "CERTIDÃO DE CASAMENTO", "ESCRITURA", "RECONHECIMENTO DE FIRMA", "AUTENTICAÇÃO" }));
        tipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 183, -1));

        tipoL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/tipopro.png"))); // NOI18N
        jPanel4.add(tipoL, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, -1, 52));

        enviar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        enviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/regis1.png"))); // NOI18N
        enviar.setText("Enviar Produto");
        enviar.setBorder(null);
        enviar.setBorderPainted(false);
        enviar.setContentAreaFilled(false);
        enviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        enviar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        enviar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/regis2.png"))); // NOI18N
        enviar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });
        jPanel4.add(enviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 100, 100));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarKeyReleased
         buscar.setText(buscar.getText().toUpperCase());
         SelosSql.listarCat(buscar.getText());
    }//GEN-LAST:event_buscarKeyReleased

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed
        if (tabela.getRowCount() > 0) {
            try {
                DefaultTableModel tabeladet = (DefaultTableModel) vendas.FrmCaixa.tabela.getModel();

                String[] dado = new String[6];

                int linha = tabela.getSelectedRow();

                if (linha == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione um Produto.", "Produtos", 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                } else {
                    String cod = tabela.getValueAt(linha, 0).toString();
                    String tipo = tabela.getValueAt(linha, 1).toString();
                    String nome = "-----";
                    
                    int c = 0;
                    int j = 0;
                    int d = 0;
                    int k = 0;
                    valor = JOptionPane.showInputDialog(this, "Valor:", "Produtos", JOptionPane.INFORMATION_MESSAGE);
                    while (!SelosSql.isNumber(quant) && quant != null) {
                        quant = JOptionPane.showInputDialog(this, "Somente valores númericos maiores que 0:",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }                    
                    if ((valor.equals("")) || (valor.equals("0"))) {
                       JOptionPane.showMessageDialog(this, "Insira um valor maior que 0");
                    }else {
                        for (int i = 0; i < vendas.FrmCaixa.tabela.getRowCount(); i++) {
                            Object com = vendas.FrmCaixa.tabela.getValueAt(i, 0);
                            Object valor1 = vendas.FrmCaixa.tabela.getValueAt(i, 4);
                            if (cod.equals(com)) {
                                j = i;
                                int valorT = Integer.parseInt(valor) + Integer.parseInt((String) valor1);
                                vendas.FrmCaixa.tabela.setValueAt(String.valueOf(valorT), i, 4);
                                c++;
                                calcular();
                                vendas.FrmCaixa.recebido.setText("");
                                vendas.FrmCaixa.troco.setText("");
                            }
                        }
                    }                    
                    
                    quant = JOptionPane.showInputDialog(this, "Quantidade:", "Produtos", JOptionPane.INFORMATION_MESSAGE);
                    while (!SelosSql.isNumber(quant) && quant != null) {
                        quant = JOptionPane.showInputDialog(this, "Somente valores númericos maiores que 0:",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if ((quant.equals("")) || (quant.equals("0"))) {
                       JOptionPane.showMessageDialog(this, "Insira um valor maior que 0");
                    } else {
                        for (int i = 0; i < vendas.FrmCaixa.tabela.getRowCount(); i++) {
                            Object com = vendas.FrmCaixa.tabela.getValueAt(i, 0);
                            Object quant1 = vendas.FrmCaixa.tabela.getValueAt(i, 4);
                            if (cod.equals(com)) {
                                j = i;
                                int quantT = Integer.parseInt(quant) + Integer.parseInt((String) quant1);
                                vendas.FrmCaixa.tabela.setValueAt(String.valueOf(quantT), i, 4);
                                c++;
                                calcular();
                                vendas.FrmCaixa.recebido.setText("");
                                vendas.FrmCaixa.troco.setText("");
                            }
                        }
                        if (c == 0) {

                            dado[0] = cod;
                            dado[1] = nome;
                            dado[2] = tipo;
                            dado[3] = quant;
                            dado[4] = valor;

                            tabeladet.addRow(dado);

                            vendas.FrmCaixa.tabela.setModel(tabeladet);
                            calcular();

                             vendas.FrmCaixa.recebido.setText("");
                             vendas.FrmCaixa.troco.setText("");
                             dispose();
                             
                        }
                    }
                }
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não tem registros.", "Produtos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_enviarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.bolivia.swing.JCTextField buscar;
    private javax.swing.JLabel codigoL1;
    private javax.swing.JButton enviar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tabela;
    private org.bolivia.combo.SComboBoxBlue tipo;
    private javax.swing.JLabel tipoL;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixa;

import vendas.*;
import atos.FrmListaAtos;
import documentos.FrmListaDoc;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import static principal.MenuPrincipal.carregador;
import selos.FrmListaSelo;

/**
 *
 * @author hugov
 */
public class FrmCaixa extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmCaixa
     */
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

    public FrmCaixa() {
        initComponents();
        FrmCaixa.tabela.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        FrmCaixa.tabela.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());

        FrmCaixa.tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        limparCampos();
    }

    public static String dataAtual() {
        Date data = new Date();
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/YYYY");
        return formatoData.format(data);

    }

    void limparCampos() {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        recebido.setText("");
        troco.setText("");
        total.setText("0.0");
        data.setText("");
        data.setText(dataAtual());
        VendasSql.numeros();
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
        jPanel2 = new javax.swing.JPanel();
        busca = new javax.swing.JButton();
        data = new app.bolivia.swing.JCTextField();
        recebido = new app.bolivia.swing.JCTextField();
        codigoL6 = new javax.swing.JLabel();
        troco = new app.bolivia.swing.JCTextField();
        codigoL7 = new javax.swing.JLabel();
        busca1 = new javax.swing.JButton();
        codigoL5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        calculo = new javax.swing.JButton();
        vender = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        total = new app.bolivia.swing.JCTextField();
        codigoL3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        numFac = new app.bolivia.swing.JCTextField();
        codigoL4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Caixa");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        busca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        busca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/Selos.png"))); // NOI18N
        busca.setBorder(null);
        busca.setContentAreaFilled(false);
        busca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        busca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        busca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/Selos1.png"))); // NOI18N
        busca.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        busca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaActionPerformed(evt);
            }
        });
        jPanel2.add(busca, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 110, 50));

        data.setEditable(false);
        data.setBackground(new java.awt.Color(34, 102, 145));
        data.setBorder(null);
        data.setForeground(new java.awt.Color(255, 255, 255));
        data.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        data.setOpaque(false);
        data.setPhColor(new java.awt.Color(255, 255, 255));
        data.setPlaceholder("DATA");
        jPanel2.add(data, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 100, -1));

        recebido.setBackground(new java.awt.Color(34, 102, 145));
        recebido.setBorder(null);
        recebido.setForeground(new java.awt.Color(255, 255, 255));
        recebido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        recebido.setOpaque(false);
        recebido.setPhColor(new java.awt.Color(255, 255, 255));
        recebido.setPlaceholder("RECIBIDO");
        jPanel2.add(recebido, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 130, -1));

        codigoL6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/recibi.png"))); // NOI18N
        codigoL6.setToolTipText("RECIBI");
        jPanel2.add(codigoL6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, 52));

        troco.setEditable(false);
        troco.setBackground(new java.awt.Color(34, 102, 145));
        troco.setBorder(null);
        troco.setForeground(new java.awt.Color(255, 255, 255));
        troco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        troco.setOpaque(false);
        troco.setPhColor(new java.awt.Color(255, 255, 255));
        troco.setPlaceholder("TROCO");
        jPanel2.add(troco, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 130, -1));

        codigoL7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/valores.png"))); // NOI18N
        codigoL7.setToolTipText("CAMBIO");
        jPanel2.add(codigoL7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, 52));

        busca1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        busca1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/produto1.png"))); // NOI18N
        busca1.setBorder(null);
        busca1.setContentAreaFilled(false);
        busca1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        busca1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        busca1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/produto.png"))); // NOI18N
        busca1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        busca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busca1ActionPerformed(evt);
            }
        });
        jPanel2.add(busca1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 120, -1));

        codigoL5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/data.png"))); // NOI18N
        jPanel2.add(codigoL5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 52));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "OPÇÕES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        calculo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        calculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/calculo1.png"))); // NOI18N
        calculo.setBorder(null);
        calculo.setContentAreaFilled(false);
        calculo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        calculo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        calculo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/calculo2.png"))); // NOI18N
        calculo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        calculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculoActionPerformed(evt);
            }
        });

        vender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        vender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/venda1.png"))); // NOI18N
        vender.setBorder(null);
        vender.setContentAreaFilled(false);
        vender.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vender.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        vender.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/venda2.png"))); // NOI18N
        vender.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        vender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venderActionPerformed(evt);
            }
        });

        excluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/elimina1.png"))); // NOI18N
        excluir.setBorder(null);
        excluir.setContentAreaFilled(false);
        excluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        excluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        excluir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/elimina2.png"))); // NOI18N
        excluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        cancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/cancela1.png"))); // NOI18N
        cancelar.setBorder(null);
        cancelar.setContentAreaFilled(false);
        cancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/cancela2.png"))); // NOI18N
        cancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calculo)
                    .addComponent(vender)
                    .addComponent(excluir)
                    .addComponent(cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vender)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(excluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "NOME", "TIPO", "QUANTIDADE", "VALOR UNIT.", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tabela);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 790, 200));

        total.setEditable(false);
        total.setBackground(new java.awt.Color(34, 102, 145));
        total.setBorder(null);
        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        total.setOpaque(false);
        total.setPhColor(new java.awt.Color(255, 255, 255));
        total.setPlaceholder("TOTAL");
        jPanel4.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 230, 80, 30));

        codigoL3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/total.png"))); // NOI18N
        jPanel4.add(codigoL3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 220, -1, 50));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/principal/logomenor.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Cartório do Registro Civil e Notas do Distrito de Riacho da Cruz");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TELEFONES:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("38 3621-3866");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("38 9 9998-6710");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        numFac.setEditable(false);
        numFac.setBackground(new java.awt.Color(34, 102, 145));
        numFac.setBorder(null);
        numFac.setForeground(new java.awt.Color(255, 255, 255));
        numFac.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        numFac.setOpaque(false);
        numFac.setPhColor(new java.awt.Color(255, 255, 255));
        numFac.setPlaceholder("FATURA");
        jPanel6.add(numFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 130, 30));

        codigoL4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/vendas/numFac.png"))); // NOI18N
        jPanel6.add(codigoL4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 50));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("NÚMERO DE VENTA");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));
        jLabel15.getAccessibleContext().setAccessibleName("NÚMERO DA VENDA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    atos.FrmListaAtos lista;
    private void buscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaActionPerformed
        if (estaFechado(lista)) {
            lista = new FrmListaAtos();
            principal.MenuPrincipal.carregador.add(lista);

            lista.toFront();
            lista.setVisible(true);
        } else {
            lista.toFront();

        }

    }//GEN-LAST:event_buscaActionPerformed

    private void calculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculoActionPerformed
        if (tabela.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Operação não realizada.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (recebido.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Insira um valor.", "Cobrança", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        } else {
            double recebi = Double.parseDouble(recebido.getText());
            double tot = Double.parseDouble(total.getText());

            if (recebi < tot) {
                JOptionPane.showMessageDialog(this, "Valor Inválido para essa venda", "Compra", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            } else {
                this.troco.setText(String.valueOf(recebi - tot));
            }
        }
    }//GEN-LAST:event_calculoActionPerformed
    int opc;
    private void venderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venderActionPerformed
        if (tabela.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Impossível realizar a venda.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < vendas.FrmCaixa.tabela.getRowCount(); i++) {
                Servicos s = new Servicos();
                s.setPrimaryKey(numFac.getText());
                s.setNumDoc_ser(vendas.FrmCaixa.tabela.getValueAt(0, 0).toString());
                s.setNumAto_ser(vendas.FrmCaixa.tabela.getValueAt(i, 0).toString());
                s.setTotalAto_ser(vendas.FrmCaixa.tabela.getValueAt(i, 6).toString());
                s.setData_ser(data.getText());
                opc = ServicosSql.registrar(s);
            }
            if (opc != 0) {
                limparCampos();
                JOptionPane.showMessageDialog(this, "Venda Efetuada.", "Venda", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));

            }
        }
    }//GEN-LAST:event_venderActionPerformed

    private void excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirActionPerformed
        if (tabela.getRowCount() > 0) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                modelo.removeRow(linha);
                FrmListaSelo lp = new FrmListaSelo();
                lp.calcular();
            } else {
                JOptionPane.showMessageDialog(this, "Selecionar uma Linha.", "Venda", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não há registro para excluir.", "Venda", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_excluirActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        limparCampos();
    }//GEN-LAST:event_cancelarActionPerformed
    documentos.FrmListaDoc lista1;
    private void busca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busca1ActionPerformed
        if (estaFechado(lista1)) {
            lista1 = new FrmListaDoc();
            principal.MenuPrincipal.carregador.add(lista1);

            lista1.toFront();
            lista1.setVisible(true);
        } else {
            lista1.toFront();

        }
    }//GEN-LAST:event_busca1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton busca;
    private javax.swing.JButton busca1;
    private javax.swing.JButton calculo;
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel codigoL3;
    private javax.swing.JLabel codigoL4;
    private javax.swing.JLabel codigoL5;
    private javax.swing.JLabel codigoL6;
    private javax.swing.JLabel codigoL7;
    private app.bolivia.swing.JCTextField data;
    private javax.swing.JButton excluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    public static app.bolivia.swing.JCTextField numFac;
    public static app.bolivia.swing.JCTextField recebido;
    public static javax.swing.JTable tabela;
    public static app.bolivia.swing.JCTextField total;
    public static app.bolivia.swing.JCTextField troco;
    private javax.swing.JButton vender;
    // End of variables declaration//GEN-END:variables
}

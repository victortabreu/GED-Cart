/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentos;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author hugov
 */
public class FrmDocumentos extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmProdutos
     */
    public FrmDocumentos() {
        initComponents();
        tabelaDocumentos.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabelaDocumentos.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        tabelaDocumentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        limparCampos();

        tabelaDocumentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tabelaDocumentos.getSelectedRow() != -1) {
                    atualizarDados();
                    selecionarRegistro = true;
                }
            }
        });
    }

    void redmensionarImagem() {
        
        if(imagem!=null){
            Double novaImgLargura = (double) imagem.getIconWidth();
            Double novaImgAltura = (double) imagem.getIconHeight();
            Double imgProporcao;
            Double imgLargura = (double) jLabelImagem.getWidth();
            Double imgAltura = (double) jLabelImagem.getHeight();
            if (novaImgLargura >= imgLargura) {
                imgProporcao = (novaImgAltura / novaImgLargura);//calcula a proporção
                novaImgLargura = (double) imgLargura;

                //--- altura deve <= ao parâmetro imgAltura e proporcional a largura ---
                novaImgAltura = (novaImgLargura * imgProporcao);

                //--- se altura for maior do que o parâmetro imgAltura, diminui-se a largura de ---
                //--- forma que a altura seja igual ao parâmetro imgAltura e proporcional a largura ---
                while (novaImgAltura > imgAltura) {
                    novaImgLargura = (double) (--imgLargura);
                    novaImgAltura = (novaImgLargura * imgProporcao);
                }
            } else if (novaImgAltura >= imgAltura) {
                imgProporcao = (novaImgLargura / novaImgAltura);//calcula a proporção
                novaImgAltura = (double) imgAltura;

                //--- se largura for maior do que o parâmetro imgLargura, diminui-se a altura de ---
                //--- forma que a largura seja igual ao parâmetro imglargura e proporcional a altura ---
                while (novaImgLargura > imgLargura) {
                    novaImgAltura = (double) (--imgAltura);
                    novaImgLargura = (novaImgAltura * imgProporcao);
                }
            }
            Image img = imagem.getImage().getScaledInstance(novaImgLargura.intValue(), novaImgAltura.intValue(), Image.SCALE_SMOOTH);
            jLabelImagem.setIcon(new ImageIcon(img));
        }
    }

    ImageIcon imagem;
    public String documento;
    void atualizarDados() {
        int linha = tabelaDocumentos.getSelectedRow();
        codigo.setText(tabelaDocumentos.getValueAt(linha, 0).toString());
        nome.setText(tabelaDocumentos.getValueAt(linha, 1).toString());
        tipo.setSelectedItem(tabelaDocumentos.getValueAt(linha, 2).toString());
        scan.setText(tabelaDocumentos.getValueAt(linha, 3).toString());
        caminho.setText("C:\\GedCart\\GedCartImagens/" + scan.getText());
        imagem = new ImageIcon(caminho.getText());
        redmensionarImagem();
        documento = caminho.getText();
    }
    Scanner scanner;
    void limparCampos() {
        if (tabelaDocumentos.getSelectedRow() > -1) {
            tabelaDocumentos.removeRowSelectionInterval(tabelaDocumentos.getSelectedRow(), tabelaDocumentos.getSelectedRow());
        }
        documento = caminho.getText();
        codigo.setText("");
        nome.setText("");
        tipo.setSelectedItem("TIPO");
        buscar.setText("");
        DocumentosSql.listarDocumentos("");
        DocumentosSql.gerarId();
        selecionarRegistro = false;
        caminho.setText("");
        ImageIcon im = new ImageIcon(getClass().getResource("/imagens/rescan-document.png"));
        Image i = im.getImage().getScaledInstance(500, 510, Image.SCALE_SMOOTH);
        jLabelImagem.setIcon(new ImageIcon(i));
        imagem = null;
        java.io.File pasta = new java.io.File("C:\\GedCart\\pasta.txt");
        try {
            scanner = new Scanner(pasta,"UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        nomePasta.setText(scanner.nextLine());
    }

    void selecionarLinha(String id) {
        for (int i = 0; i < tabelaDocumentos.getRowCount(); i++) {
            if (id.equals(tabelaDocumentos.getValueAt(i, 0))) {
                tabelaDocumentos.setRowSelectionInterval(i, i);
                break;
            }
        }

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
        codigo = new app.bolivia.swing.JCTextField();
        codigoL = new javax.swing.JLabel();
        nome = new app.bolivia.swing.JCTextField();
        nomeL = new javax.swing.JLabel();
        tipo = new org.bolivia.combo.SComboBoxBlue();
        tipoL = new javax.swing.JLabel();
        jButtonInserirScanner = new javax.swing.JButton();
        caminho = new javax.swing.JTextField();
        scan = new javax.swing.JTextField();
        pasta = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nomePasta = new javax.swing.JTextField();
        inserePasta = new javax.swing.JButton();
        uploadSelected = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDocumentos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        registrar = new javax.swing.JButton();
        atualizar = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        limpar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        buscar = new app.bolivia.swing.JCTextField();
        codigoL1 = new javax.swing.JLabel();
        jLabelImagem = new javax.swing.JLabel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(1280, 580));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "REGISTRO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        codigo.setEditable(false);
        codigo.setBackground(new java.awt.Color(34, 102, 145));
        codigo.setBorder(null);
        codigo.setForeground(new java.awt.Color(255, 255, 255));
        codigo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        codigo.setOpaque(false);
        codigo.setPhColor(new java.awt.Color(255, 255, 255));
        codigo.setPlaceholder("CÓDIGO");
        jPanel2.add(codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 80, -1));

        codigoL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/codigoL.png"))); // NOI18N
        jPanel2.add(codigoL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 52));

        nome.setBackground(new java.awt.Color(34, 102, 145));
        nome.setBorder(null);
        nome.setForeground(new java.awt.Color(255, 255, 255));
        nome.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nome.setOpaque(false);
        nome.setPhColor(new java.awt.Color(255, 255, 255));
        nome.setPlaceholder("NOME");
        nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nomeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nomeKeyTyped(evt);
            }
        });
        jPanel2.add(nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 180, -1));

        nomeL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/nomeLabel.png"))); // NOI18N
        jPanel2.add(nomeL, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, 52));

        tipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIPO", "CERTIDÃO DE NASCIMENTO", "CERTIDÃO DE ÓBITO", "CERTIDÃO DE CASAMENTO", "ESCRITURA", "PROCURAÇÃO", "RECONHECIMENTO DE FIRMA", "AUTENTICAÇÃO" }));
        tipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 183, -1));

        tipoL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/tipopro.png"))); // NOI18N
        tipoL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(tipoL, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, -1, 52));

        jButtonInserirScanner.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonInserirScanner.setText("INSERIR SCANNER");
        jButtonInserirScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirScannerActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonInserirScanner, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 180, 40));

        caminho.setEditable(false);
        jPanel2.add(caminho, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 280, -1));

        scan.setEditable(false);
        jPanel2.add(scan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 280, -1));

        pasta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pasta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/pasta.png"))); // NOI18N
        pasta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pastaMouseClicked(evt);
            }
        });
        jPanel2.add(pasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("PASTA DE UPLOAD");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        nomePasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomePastaActionPerformed(evt);
            }
        });
        jPanel2.add(nomePasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 340, -1));

        inserePasta.setText("Inserir Pasta");
        inserePasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserePastaActionPerformed(evt);
            }
        });
        jPanel2.add(inserePasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        uploadSelected.setText("Upload Selected");
        uploadSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadSelectedActionPerformed(evt);
            }
        });
        jPanel2.add(uploadSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, -1, -1));

        tabelaDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "NOME", "TIPO DO DOCUMENTO", "SCANNERS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDocumentos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tabelaDocumentos);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "OPÇÕES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        registrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/regis1.png"))); // NOI18N
        registrar.setText("Registrar");
        registrar.setBorder(null);
        registrar.setBorderPainted(false);
        registrar.setContentAreaFilled(false);
        registrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        registrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/regis2.png"))); // NOI18N
        registrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarActionPerformed(evt);
            }
        });

        atualizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/editar.png"))); // NOI18N
        atualizar.setBorder(null);
        atualizar.setBorderPainted(false);
        atualizar.setContentAreaFilled(false);
        atualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        atualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        atualizar.setLabel("Atualizar");
        atualizar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/editar1.png"))); // NOI18N
        atualizar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/editar1.png"))); // NOI18N
        atualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarActionPerformed(evt);
            }
        });

        excluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagar.png"))); // NOI18N
        excluir.setBorder(null);
        excluir.setBorderPainted(false);
        excluir.setContentAreaFilled(false);
        excluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        excluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        excluir.setLabel("Excluir");
        excluir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/apagar1.png"))); // NOI18N
        excluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        limpar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/limpar.png"))); // NOI18N
        limpar.setBorder(null);
        limpar.setBorderPainted(false);
        limpar.setContentAreaFilled(false);
        limpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        limpar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        limpar.setLabel("Limpar Campos");
        limpar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/limpar1.png"))); // NOI18N
        limpar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(atualizar)
                .addGap(28, 28, 28)
                .addComponent(excluir)
                .addGap(18, 18, 18)
                .addComponent(limpar)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(registrar)
            .addComponent(atualizar)
            .addComponent(excluir)
            .addComponent(limpar)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "BUSCAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel4.setMinimumSize(new java.awt.Dimension(600, 82));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buscar.setBackground(new java.awt.Color(34, 102, 145));
        buscar.setBorder(null);
        buscar.setForeground(new java.awt.Color(255, 255, 255));
        buscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscar.setOpaque(false);
        buscar.setPhColor(new java.awt.Color(255, 255, 255));
        buscar.setPlaceholder("CÓDIGO/NOME");
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
        });
        jPanel4.add(buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 180, -1));

        codigoL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codigoL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/buscarL.png"))); // NOI18N
        jPanel4.add(codigoL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 250, 52));

        jLabelImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImagem.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabelImagem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabelImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabelImagem.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabelImagemMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelImagemMouseMoved(evt);
            }
        });
        jLabelImagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelImagemMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelImagemMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelImagemMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomeKeyReleased

    }//GEN-LAST:event_nomeKeyReleased

    private void nomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomeKeyTyped

    }//GEN-LAST:event_nomeKeyTyped
    boolean selecionarRegistro = false;
    String nomeImagem;
    String texto = null;
    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarActionPerformed
        if (selecionarRegistro) {
            JOptionPane.showMessageDialog(this, "O código: " + this.codigo.getText() + "\n já está registrado.", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        } else {
            if (codigo.getText().equals("") || nome.getText().equals("") || tipo.getSelectedItem().equals("TIPO") || caminho.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Todos os campos\nsão obrigatórios.", "Usuarios", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            } else {
                
                //Transformar imagem em texto
                Tesseract tesseract = new Tesseract();
                try {
                    texto = tesseract.doOCR(new File(path));
                } catch (TesseractException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(texto);
                
                //Salvar documento
                documentos.Documentos doc = new Documentos();
                doc.setPrimaryKey(codigo.getText());
                doc.setNome(nome.getText());
                doc.setTipodoc(tipo.getSelectedItem().toString());
                doc.setTextoOCR(texto);
                nomeImagem = System.currentTimeMillis() + ".jpg";
                doc.setScan(nomeImagem);
                
                //Salvar imagem na pasta do sistema
                File file1 = new File("C:\\GedCart/");
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                File file = new File("C:\\GedCart\\GedCartImagens/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File novaImagem = new File("C:\\GedCart\\GedCartImagens/" + nomeImagem);
                BufferedImage bi = new BufferedImage(imagem.getIconWidth(), imagem.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = bi.createGraphics();
                g2d.drawImage(scan1, null, null);
                try {
                    ImageIO.write(bi, "JPG", novaImagem);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Não foi possível salvar a imagem.", "Documentos", 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                }
                
                int op = DocumentosSql.registrarDocumento(doc);
                
                //Documento inseridocom sucesso
                if (op != 0) {
                    String id = codigo.getText();
                    selecionarLinha(id);
                    JOptionPane.showMessageDialog(this, "Documento Inserido com Sucesso.", "Documentos", 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                    limparCampos();
                }

                Documentos document = new Documentos();
                document.setScan(nomeImagem);
                System.out.println(document.getScan());
                documento = "C:\\GedCart\\GedCartImagens/" + nomeImagem;
                System.out.println(documento);
                FileWriter arq = null;
                try {
                    arq = new FileWriter("C:\\GedCart\\upload.txt");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.println(document.getScan());
                gravarArq.println(documento);
                try {
                    arq.close();
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                FileWriter up = null;
                try {
                    up = new FileWriter("C:\\GedCart\\upload.bat");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter gravarUp = new PrintWriter(up);
                gravarUp.println("d:");
                gravarUp.println("cd Documentos\\NetBeansProjects\\GEDCart");
                gravarUp.println("gradle run");
                try {
                    up.close();
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    Desktop.getDesktop().open(new java.io.File("C:\\GedCart\\upload.bat"));
                    System.out.println("Chegou aqui");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }//GEN-LAST:event_registrarActionPerformed

    private void atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarActionPerformed
        if (tabelaDocumentos.getRowCount() > 0) {
            if (tabelaDocumentos.getSelectedRowCount() > 0) {
                if (codigo.getText().equals("") || nome.getText().equals("") || tipo.getSelectedItem().equals("TIPO")) {
                    JOptionPane.showMessageDialog(this, "Preecha os campos.", "Documentos", 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                } else if (JOptionPane.showConfirmDialog(this, "Deseja alterar o registro?", "Documentos", JOptionPane.YES_NO_OPTION, 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png"))) == JOptionPane.YES_OPTION) {
                    documentos.Documentos doc = new Documentos();
                    doc.setPrimaryKey(codigo.getText());
                    doc.setNome(nome.getText());
                    doc.setTipodoc(tipo.getSelectedItem().toString());
                    //doc.setScan(scan.getText());
                    int opc = DocumentosSql.atualizarDocumento(doc);
                    if (opc == 0) {
                        JOptionPane.showMessageDialog(this, "O documento não foi atualizado.", "Documentos", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                    }
                    if (opc != 0) {
                        String id = codigo.getText();
                        selecionarLinha(id);
                        JOptionPane.showMessageDialog(this, "Registro Atualizado.", "Documentos", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                        limparCampos();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um registro.", "Documentos", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Não há registro para alterar.", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_atualizarActionPerformed

    private void excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirActionPerformed
        if (tabelaDocumentos.getRowCount() > 0) {
            if (tabelaDocumentos.getSelectedRowCount() > 0) {
                if (JOptionPane.showConfirmDialog(this, "Deseja Excluir?", "Documentos", JOptionPane.YES_NO_OPTION, 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png"))) == JOptionPane.YES_OPTION) {
                    int linha = tabelaDocumentos.getSelectedRow();
                    String id = tabelaDocumentos.getValueAt(linha, 0).toString();
                    int elimina = DocumentosSql.eliminarDocumento(id);
                    File file = new File(caminho.getText());
                    file.delete();
                    if (elimina != 0) {
                        limparCampos();
                        JOptionPane.showMessageDialog(this, "Registro excluido.", "Documentos", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um registro.", "Documentos", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Não há registros para exclusão.", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_excluirActionPerformed

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparActionPerformed
        limparCampos();
    }//GEN-LAST:event_limparActionPerformed

    private void buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarMouseClicked
        limparCampos();
    }//GEN-LAST:event_buscarMouseClicked

    private void buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarKeyReleased
        buscar.setText(buscar.getText().toUpperCase());
        DocumentosSql.listarDocumentos(buscar.getText());
    }//GEN-LAST:event_buscarKeyReleased
    Image scan1;
    JFileChooser fc;
    ImageIcon icone1;
    String path;
    private void jButtonInserirScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirScannerActionPerformed

        fc = new JFileChooser();

        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getAbsolutePath();
                return name.endsWith(".jpg") | name.endsWith(".png") | name.endsWith(".bmp") | f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Imagem";
            }
        });

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getAbsolutePath();
            imagem = new ImageIcon(path);
            redmensionarImagem();

            scan1 = imagem.getImage();

            File file;
            file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            caminho.setText(fileName);
        }
    }//GEN-LAST:event_jButtonInserirScannerActionPerformed

    private void jLabelImagemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMousePressed

    }//GEN-LAST:event_jLabelImagemMousePressed

    private void jLabelImagemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMouseExited

    }//GEN-LAST:event_jLabelImagemMouseExited

    private void jLabelImagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMouseClicked
        if (caminho.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nenhuma imagem selecionada!", "Documentos", 0,
                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        } else {
            try {
                Desktop.getDesktop().open(new java.io.File(caminho.getText()));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível abrir a imagem.", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }
        }
    }//GEN-LAST:event_jLabelImagemMouseClicked

    private void jLabelImagemMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMouseMoved

    }//GEN-LAST:event_jLabelImagemMouseMoved

    private void jLabelImagemMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMouseDragged
        //        if(imagem!=null){
            //            Graphics g = getGraphics();
            //            g.setColor(Color.yellow);
            //            x = evt.getX();
            //            y = evt.getY();
            //            g.fillOval(x, y, 10, 10);
            //        }
//      
    }//GEN-LAST:event_jLabelImagemMouseDragged

    private void pastaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pastaMouseClicked
        try {
            Desktop.getDesktop().open(new java.io.File("C:\\GedCart\\GedCartImagens/"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Não foi possível abrir a pasta.", "Documentos", 0,
                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_pastaMouseClicked

    private void nomePastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomePastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomePastaActionPerformed

    private void inserePastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserePastaActionPerformed
                FileWriter pasta = null;
                try {
                    pasta = new FileWriter("C:\\GedCart\\pasta.txt");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter gravarPasta = new PrintWriter(pasta);
                gravarPasta.println(nomePasta.getText());
                try {
                    pasta.close();
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                File file = new File("D:\\Documentos\\NetBeansProjects\\GEDCart\\tokens\\StoredCredential");
                file.delete();
                JOptionPane.showMessageDialog(this, "Pasta atualizada com sucesso!", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                limparCampos();
    }//GEN-LAST:event_inserePastaActionPerformed

    private void uploadSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadSelectedActionPerformed
        FileWriter arq = null;
                try {
                    arq = new FileWriter("C:\\GedCart\\upload.txt");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.println(scan.getText());
                gravarArq.println(caminho.getText());
                try {
                    arq.close();
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }        
        
        FileWriter up = null;
                try {
                    up = new FileWriter("C:\\GedCart\\upload.bat");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter gravarUp = new PrintWriter(up);
                gravarUp.println("d:");
                gravarUp.println("cd Documentos\\NetBeansProjects\\GEDCart");
                gravarUp.println("gradle run");
                try {
                    up.close();
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Desktop.getDesktop().open(new java.io.File("C:\\GedCart\\upload.bat"));
                    System.out.println("Chegou aqui");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }

    }//GEN-LAST:event_uploadSelectedActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton atualizar;
    private app.bolivia.swing.JCTextField buscar;
    private javax.swing.JTextField caminho;
    public static app.bolivia.swing.JCTextField codigo;
    private javax.swing.JLabel codigoL;
    private javax.swing.JLabel codigoL1;
    private javax.swing.JButton excluir;
    private javax.swing.JButton inserePasta;
    private javax.swing.JButton jButtonInserirScanner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelImagem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limpar;
    private app.bolivia.swing.JCTextField nome;
    private javax.swing.JLabel nomeL;
    private javax.swing.JTextField nomePasta;
    private javax.swing.JLabel pasta;
    private javax.swing.JButton registrar;
    private javax.swing.JTextField scan;
    public static javax.swing.JTable tabelaDocumentos;
    private org.bolivia.combo.SComboBoxBlue tipo;
    private javax.swing.JLabel tipoL;
    private javax.swing.JButton uploadSelected;
    // End of variables declaration//GEN-END:variables
}

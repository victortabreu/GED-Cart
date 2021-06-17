/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static principal.MenuPrincipal.carregador;

/**
 *
 * @author hugov
 */
public class FrmDocumentos extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmProdutos
     */
    // Variáveis:
    public String documento;
    String raiz;
    String pastaDoSistema = "";
    String anterior = null;
    String nomeImagem;
    String texto;
    String path;
    String novaPasta = null;

    ImageIcon imagem;
    ImageIcon icone1;
    ImageIcon imagemAtualizada;
    Image scan1;

    Scanner scanner;

    BarraDeProgresso barra;

    boolean selecionarRegistro = false;

    int img;

    JFileChooser fc;

    FrmPessoas lista;

    String nomeAntigo;
    String tipoAntigo;
    String pessoasAntigo;
    String scannerTbl;

    //Funções e Métodos:
    public FrmDocumentos() {
        initComponents();
        tabelaDocumentos.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabelaDocumentos.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        tabelaDocumentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaPessoas.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabelaPessoas.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        tabelaPessoas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaPessoas2.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabelaPessoas2.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        tabelaPessoas2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPessoas2.setVisible(false);

        tabelaScan.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabelaScan.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        tabelaScan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaScan2.getTableHeader().setDefaultRenderer(new principal.EstiloTabelaHeader());
        tabelaScan2.setDefaultRenderer(Object.class, new principal.EstiloTabelaRenderer());
        tabelaScan2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaScan2.setVisible(false);

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

        tabelaScan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tabelaScan.getSelectedRow() != -1) {
                    atualizarDadosScan();
                    //selecionarRegistro = true;
                }
            }
        });

    }

    void atualizarDadosScan() {
        int linha = tabelaScan.getSelectedRow();
        imagem = new ImageIcon(tabelaScan.getValueAt(linha, 0).toString());
        redmensionarImagem();
    }

    void redmensionarImagem() {

        if (imagem != null) {
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
            Image imge = imagem.getImage().getScaledInstance(novaImgLargura.intValue(), novaImgAltura.intValue(), Image.SCALE_SMOOTH);
            jLabelImagem.setIcon(new ImageIcon(imge));
        }
    }

    void atualizarDados() {
        int linha = tabelaDocumentos.getSelectedRow();
        codigo.setText(tabelaDocumentos.getValueAt(linha, 0).toString());
        nome.setText(tabelaDocumentos.getValueAt(linha, 1).toString());
        tipo.setSelectedItem(tabelaDocumentos.getValueAt(linha, 2).toString());

        DefaultTableModel tabelaS = (DefaultTableModel) tabelaScan.getModel();
        String[] dado1 = new String[1];
        while (tabelaS.getRowCount() > 0) {
            tabelaS.removeRow(0);
        }
        String dividirScan = tabelaScan2.getValueAt(linha, 0).toString();
        for (String div : dividirScan.split("\\*", 0)) {
            dado1[0] = pastaDoSistema + "\\GedCartImagens/" + div;
            tabelaS.addRow(dado1);
        }

        tabelaScan.setModel(tabelaS);
        caminho.setText(tabelaScan.getValueAt(0, 0).toString());
        documento = caminho.getText();

        int k = 0;
        String dividirPessoas = tabelaPessoas2.getValueAt(linha, 0).toString();
        DefaultTableModel tabeladet = (DefaultTableModel) tabelaPessoas.getModel();
        String[] dado = new String[2];

        while (tabeladet.getRowCount() > 0) {
            tabeladet.removeRow(0);
        }
        for (String retval : dividirPessoas.split("\\*", 0)) {
            dado[k] = retval;
            if (k == 0) {
                k = 1;
            } else {
                k = 0;
                tabeladet.addRow(dado);
            }
        }
        tabelaPessoas.setModel(tabeladet);
        tabelaScan.addRowSelectionInterval(0, 0);
        scannerTbl = "";
        for (int i = 0; i < tabelaScan.getRowCount(); i++) {
            scannerTbl += tabelaScan.getValueAt(i, 0).toString();
        }
        nomeAntigo = nome.getText();
        tipoAntigo = tipo.getSelectedItem().toString();
        pessoasAntigo = tabelaPessoas2.getValueAt(linha, 0).toString();
    }

    final void limparCampos() {
        //Limpa todos os campos:
        if (tabelaDocumentos.getSelectedRow() > -1) {
            tabelaDocumentos.removeRowSelectionInterval(tabelaDocumentos.getSelectedRow(), tabelaDocumentos.getSelectedRow());
        }
        if (tabelaPessoas.getSelectedRow() > -1) {
            tabelaPessoas.removeRowSelectionInterval(tabelaPessoas.getSelectedRow(), tabelaPessoas.getSelectedRow());
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

        //Limpa a tabela de Pessoas e a tabela de Scanners:
        DefaultTableModel modelo = (DefaultTableModel) tabelaPessoas.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        DefaultTableModel modelo2 = (DefaultTableModel) tabelaScan.getModel();

        while (modelo2.getRowCount() > 0) {
            modelo2.removeRow(0);
        }

        //Define a pasta de upload:
        java.io.File pastaT = new java.io.File("pastaUp.txt");
        try {
            scanner = new Scanner(pastaT, "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        nomePasta.setText(scanner.nextLine());

        //Cria as pastas do sistema caso não existirem:
        pastasSystem();

        raiz = System.getProperty("user.dir");
        System.out.println(raiz);
    }

    JDialog dlg;
    int progresso = 0;

    void prog(String titulo) {
        progresso = 0;
        JFrame parentFrame = new JFrame();
        JLabel jl = new JLabel();
        jl.setText("Count : 0");

        dlg = new JDialog(parentFrame, "Progress Dialog", true);
        JProgressBar dpb = new JProgressBar(0, 100);
        dpb.setStringPainted(true);
        dlg.add(BorderLayout.CENTER, dpb);
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setSize(600, 70);
        dlg.setLocationRelativeTo(parentFrame);
        dlg.setTitle(titulo);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                dlg.setVisible(true);
            }
        });
        t.start();
        while (dpb.getValue() < 100 && progresso != 1) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                dpb.setValue(dpb.getValue() + 2);
                if (dpb.getValue() == 100) {
                    dpb.setValue(0);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(BarraDeProgresso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dlg.dispose();
    }

    void selecionarLinha(String id) {
        for (int i = 0; i < tabelaDocumentos.getRowCount(); i++) {
            if (id.equals(tabelaDocumentos.getValueAt(i, 0))) {
                tabelaDocumentos.setRowSelectionInterval(i, i);
                break;
            }
        }

    }

    void pastasSystem() {
        java.io.File rPastaS = new java.io.File("pastaSys.txt");

        try {
            scanner = new Scanner(rPastaS, "UTF-8");
        } catch (FileNotFoundException ex) {
            FileWriter pastaS = null;
            try {
                pastaS = new FileWriter("pastaSys.txt");
            } catch (IOException e) {
                Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, e);
            }
            PrintWriter gravarPasta = new PrintWriter(pastaS);
            gravarPasta.println("C:\\GedCart");
            pastaDoSistema = "C:\\GedCart";
            try {
                if (pastaS != null) {
                    pastaS.close();
                }
            } catch (IOException exe) {
                Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, exe);
            }
        }
        try {
            scanner = new Scanner(rPastaS, "UTF-8");
        } catch (FileNotFoundException ex1) {
            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex1);
        }
        pastaDoSistema = scanner.nextLine();
        scanner.close();

        System.out.println(pastaDoSistema);

        File file1 = new File(pastaDoSistema);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file = new File(pastaDoSistema + "\\GedCartImagens/");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(pastaDoSistema + "\\UserData/");
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }

    void salvaImagem(String nome, ImageIcon imagem) {

        File novaImagem = new File(pastaDoSistema + "\\GedCartImagens/" + nome);

        BufferedImage bi = new BufferedImage(imagem.getIconWidth(), imagem.getIconHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(imagem.getImage(), null, null);
        try {
            ImageIO.write(bi, "JPG", novaImagem);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar a imagem.", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }

    void upload() {
        FileWriter up = null;
        try {
            up = new FileWriter("upload.bat");
        } catch (IOException ex) {
            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter gravarUp = new PrintWriter(up);
        gravarUp.println("gradle run");
        try {
            if (up != null) {
                up.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Desktop.getDesktop().open(new java.io.File("upload.bat"));
            System.out.println("Chegou aqui");
        } catch (IOException ex) {
            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    void addScan() {
        fc = new JFileChooser();
        anterior = scan.getText();
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getAbsolutePath();
                return name.endsWith(".jpg") | name.endsWith(".JPG") | name.endsWith(".png") | name.endsWith(".PNG") | name.endsWith(".bmp") | f.isDirectory();
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

            DefaultTableModel tabelaS = (DefaultTableModel) tabelaScan.getModel();
            String[] dado1 = new String[1];
            dado1[0] = path;
            tabelaS.addRow(dado1);

            scan1 = imagem.getImage();
            nomeImagem = System.currentTimeMillis() + ".jpg";
            File file;
            file = fc.getSelectedFile();
            String fileName = file.getAbsolutePath();
            caminho.setText(fileName);
            scan.setText(nomeImagem);
            System.out.println(anterior + "\n" + scan.getText());
            imagemAtualizada = new ImageIcon(caminho.getText());
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
        pasta = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaPessoas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        rmPessoas = new javax.swing.JButton();
        addPessoas = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaPessoas2 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaScan = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaScan2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        addScanner = new javax.swing.JButton();
        rmScanner = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDocumentos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        registrar = new javax.swing.JButton();
        atualizar = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        limpar = new javax.swing.JButton();
        uploadSelected = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        buscar = new app.bolivia.swing.JCTextField();
        codigoL1 = new javax.swing.JLabel();
        jLabelImagem = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        inserePasta = new javax.swing.JButton();
        nomePasta = new javax.swing.JTextField();
        caminho = new javax.swing.JTextField();
        scan = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
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
        jPanel2.add(nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 180, -1));

        nomeL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/nomeLabel.png"))); // NOI18N
        jPanel2.add(nomeL, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, 52));

        tipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIPO", "CERTIDÃO DE NASCIMENTO", "CERTIDÃO DE ÓBITO", "CERTIDÃO DE CASAMENTO", "ESCRITURA", "PROCURAÇÃO", "RECONHECIMENTO DE FIRMA", "AUTENTICAÇÃO" }));
        tipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 330, -1));

        pasta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pasta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/pasta.png"))); // NOI18N
        pasta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pastaMouseClicked(evt);
            }
        });
        jPanel2.add(pasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, -1));

        tabelaPessoas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME", "DOCUMENTOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelaPessoas);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 280, 90));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("SCANNERS");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, -1, -1));

        rmPessoas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/menos.png"))); // NOI18N
        rmPessoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmPessoasActionPerformed(evt);
            }
        });
        jPanel2.add(rmPessoas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 30, -1));

        addPessoas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/mais.png"))); // NOI18N
        addPessoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPessoasActionPerformed(evt);
            }
        });
        jPanel2.add(addPessoas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 30, -1));

        tabelaPessoas2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME", "DOCUMENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tabelaPessoas2);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 250, 90));

        tabelaScan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CAMINHO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tabelaScan);

        jPanel2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 290, 90));

        tabelaScan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME", "OCR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tabelaScan2);
        if (tabelaScan2.getColumnModel().getColumnCount() > 0) {
            tabelaScan2.getColumnModel().getColumn(0).setResizable(false);
            tabelaScan2.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 230, 70));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("PESSOAS");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        addScanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/mais.png"))); // NOI18N
        addScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addScannerActionPerformed(evt);
            }
        });
        jPanel2.add(addScanner, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 30, -1));

        rmScanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/menos.png"))); // NOI18N
        rmScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmScannerActionPerformed(evt);
            }
        });
        jPanel2.add(rmScanner, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 30, -1));

        tabelaDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "NOME", "TIPO DO DOCUMENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDocumentos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabelaDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaDocumentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaDocumentos);
        if (tabelaDocumentos.getColumnModel().getColumnCount() > 0) {
            tabelaDocumentos.getColumnModel().getColumn(0).setMinWidth(10);
            tabelaDocumentos.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabelaDocumentos.getColumnModel().getColumn(0).setMaxWidth(100);
        }

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

        uploadSelected.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        uploadSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/up1.png"))); // NOI18N
        uploadSelected.setText("Upload");
        uploadSelected.setBorder(null);
        uploadSelected.setBorderPainted(false);
        uploadSelected.setContentAreaFilled(false);
        uploadSelected.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        uploadSelected.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadSelected.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/up2.png"))); // NOI18N
        uploadSelected.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        uploadSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadSelectedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(atualizar)
                .addGap(18, 18, 18)
                .addComponent(excluir)
                .addGap(18, 18, 18)
                .addComponent(uploadSelected)
                .addGap(18, 18, 18)
                .addComponent(limpar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registrar)
                    .addComponent(atualizar)
                    .addComponent(excluir)
                    .addComponent(limpar)
                    .addComponent(uploadSelected))
                .addContainerGap())
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
        buscar.setPlaceholder("CÓD/NOME/DOCUMENTO");
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
        jPanel4.add(buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 190, -1));

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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "BUSCAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel5.setMinimumSize(new java.awt.Dimension(600, 82));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "PASTA DE UPLOAD", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        inserePasta.setBackground(new java.awt.Color(34, 102, 145));
        inserePasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        inserePasta.setForeground(new java.awt.Color(255, 255, 255));
        inserePasta.setText("ALTERAR PASTA");
        inserePasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserePastaActionPerformed(evt);
            }
        });

        nomePasta.setEditable(false);
        nomePasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomePastaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomePasta)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(inserePasta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(nomePasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(inserePasta)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        caminho.setEditable(false);

        scan.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(scan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabelImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(caminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarActionPerformed
        img = 0;
        UIManager.put("nimbusOrange", new Color(0, 204, 0));
        //barra.dispose();
        Thread sa = new Thread(() -> {
            if (selecionarRegistro) {
                JOptionPane.showMessageDialog(this, "O código: " + FrmDocumentos.codigo.getText() + "\n já está registrado.", "Documentos", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));

            } else {
                if (codigo.getText().equals("") || nome.getText().equals("") || tipo.getSelectedItem().equals("TIPO") || caminho.getText().equals("") || tabelaScan.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Todos os campos\nsão obrigatórios.", "Usuarios", 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));

                } else {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            prog("Extraindo texto da imagem");
                        }
                    });
                    t.start();

                    //Transformar imagem em texto
                    texto = "";

                    for (int i = 0; i < tabelaScan.getRowCount(); i++) {
                        Tesseract tesseract = new Tesseract();
                        try {
                            texto += tesseract.doOCR(new File(tabelaScan.getValueAt(i, 0).toString())) + "\n";
                        } catch (TesseractException ex) {
                            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println(texto);
                    dlg.setTitle("Salvando documento");
                    //Salvar documento
                    documentos.Documentos doc = new Documentos();
                    doc.setPrimaryKey(codigo.getText());
                    doc.setNome(nome.getText());
                    doc.setTipodoc(tipo.getSelectedItem().toString());

                    String pessoas = "";
                    for (int i = 0; i < tabelaPessoas.getRowCount(); i++) {
                        pessoas += tabelaPessoas.getValueAt(i, 0).toString();
                        pessoas += "*" + tabelaPessoas.getValueAt(i, 1).toString() + "*";
                    }

                    doc.setPessoas(pessoas);
                    doc.setTextoOCR(texto);
                    dlg.setTitle("Salvando imagem na pasta do sistema");

                    //Salvar imagem na pasta do sistema
                    FileWriter arq = null;
                    try {
                        arq = new FileWriter("upload.txt");
                    } catch (IOException ex) {
                        Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PrintWriter gravarArq = new PrintWriter(arq);
                    gravarArq.println("1");

                    String stImagens = "";

                    for (int i = 0; i < tabelaScan.getRowCount(); i++) {
                        imagem = new ImageIcon(tabelaScan.getValueAt(i, 0).toString());
                        nomeImagem = System.currentTimeMillis() + ".jpg";
                        salvaImagem(nomeImagem, imagem);
                        stImagens += nomeImagem + "*";

                        String caminhoScan = tabelaScan.getValueAt(i, 0).toString();
                        gravarArq.println(nomeImagem);
                        gravarArq.println(caminhoScan);

                    }

                    try {
                        if (arq != null) {
                            arq.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    doc.setScan(stImagens);

                    int op = DocumentosSql.registrarDocumento(doc);
                    //Documento inseridocom sucesso
                    if (op != 0) {
//                       
                        progresso = 1;
                        JOptionPane.showMessageDialog(this, "Documento Inserido com Sucesso.", "Documentos", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));

                        limparCampos();
                        upload();
                    }
                }
            }

        });

        sa.start();

    }//GEN-LAST:event_registrarActionPerformed

    private void atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarActionPerformed
        String nomeNovo = nome.getText();
        String tipoNovo = tipo.getSelectedItem().toString();
        String pessoas = "";
        for (int i = 0; i < tabelaPessoas.getRowCount(); i++) {
            pessoas += tabelaPessoas.getValueAt(i, 0).toString();
            pessoas += "*" + tabelaPessoas.getValueAt(i, 1).toString() + "*";
        }
        String novas = "";
        for (int i = 0; i < tabelaScan.getRowCount(); i++) {
            novas += tabelaScan.getValueAt(i, 0).toString();
        }
        if (tabelaDocumentos.getRowCount() > 0) {
            if (tabelaDocumentos.getSelectedRowCount() > 0) {
                if (nomeNovo.equals(nomeAntigo) && tipoNovo.equals(tipoAntigo) && pessoas.equals(pessoasAntigo) && novas.equals(scannerTbl)) {
                    JOptionPane.showMessageDialog(this, "Nada foi alterado.", "Documentos", 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                } else {
                    if (codigo.getText().equals("") || nome.getText().equals("") || tipo.getSelectedItem().equals("TIPO")) {
                        JOptionPane.showMessageDialog(this, "Preecha os campos em branco.", "Documentos", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                    } else if (JOptionPane.showConfirmDialog(this, "Deseja alterar o registro?", "Documentos", JOptionPane.YES_NO_OPTION, 0,
                            new ImageIcon(getClass().getResource("/imagens/usuarios/info.png"))) == JOptionPane.YES_OPTION) {
                        documentos.Documentos doc = new Documentos();

                        texto = "";
                        String stImagens = "";

                        if (novas.equals(scannerTbl)) {
                            int linha = tabelaDocumentos.getSelectedRow();
                            texto = tabelaScan2.getValueAt(linha, 1).toString();
                            stImagens = tabelaScan2.getValueAt(linha, 0).toString();
                            System.out.println(texto);
                        } else {
                            for (int i = 0; i < tabelaScan.getRowCount(); i++) {
                                Tesseract tesseract = new Tesseract();
                                try {
                                    texto += tesseract.doOCR(new File(tabelaScan.getValueAt(i, 0).toString())) + "\n";
                                } catch (TesseractException ex) {
                                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            System.out.println(texto);

                            FileWriter arq = null;
                            try {
                                arq = new FileWriter("upload.txt");
                            } catch (IOException ex) {
                                Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            PrintWriter gravarArq = new PrintWriter(arq);
                            gravarArq.println("2");

                            int select = tabelaDocumentos.getSelectedRow();
                            String nomes = "";
                            nomes += tabelaScan2.getValueAt(select, 0).toString();
                            for (String div : nomes.split("\\*", 0)) {
                                gravarArq.println(div);
                            }

                            gravarArq.println("Novas");

                            for (int i = 0; i < tabelaScan.getRowCount(); i++) {
                                imagem = new ImageIcon(tabelaScan.getValueAt(i, 0).toString());
                                nomeImagem = System.currentTimeMillis() + ".jpg";
                                salvaImagem(nomeImagem, imagem);
                                stImagens += nomeImagem + "*";

                                String caminhoScan = pastaDoSistema + "\\GedCartImagens\\" + nomeImagem;
                                gravarArq.println(nomeImagem);
                                gravarArq.println(caminhoScan);

                            }

                            try {
                                if (arq != null) {
                                    arq.close();
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        pessoas = "";
                        for (int i = 0; i < tabelaPessoas.getRowCount(); i++) {
                            pessoas += tabelaPessoas.getValueAt(i, 0).toString();
                            pessoas += "*" + tabelaPessoas.getValueAt(i, 1).toString() + "*";
                        }

                        doc.setPessoas(pessoas);
                        doc.setPrimaryKey(codigo.getText());
                        doc.setNome(nome.getText());
                        doc.setTipodoc(tipo.getSelectedItem().toString());
                        doc.setScan(stImagens);
                        doc.setTextoOCR(texto);

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
                            if (!novas.equals(scannerTbl)) {
                                upload();
                            }
                            limparCampos();
                        }
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
                    String excluirScan = tabelaScan2.getValueAt(linha, 0).toString();
                    String id = tabelaDocumentos.getValueAt(linha, 0).toString();
                    int elimina = DocumentosSql.eliminarDocumento(id);

                    if (elimina != 0) {
                        limparCampos();
                        JOptionPane.showMessageDialog(this, "Registro excluido.", "Documentos", 0,
                                new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));

                        FileWriter arq = null;
                        try {
                            arq = new FileWriter("upload.txt");
                        } catch (IOException ex) {
                            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PrintWriter gravarArq = new PrintWriter(arq);
                        gravarArq.println("3");

                        for (String div : excluirScan.split("\\*", 0)) {
                            gravarArq.println(div);
                            File file = new File(pastaDoSistema + "\\GedCartImagens\\" + div);
                            file.delete();
                        }
                        try {
                            if (arq != null) {
                                arq.close();
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        upload();
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

    private void jLabelImagemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMousePressed

    }//GEN-LAST:event_jLabelImagemMousePressed

    private void jLabelImagemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMouseExited

    }//GEN-LAST:event_jLabelImagemMouseExited

    private void jLabelImagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImagemMouseClicked
        if (imagem == null) {
            addScan();
        } else {
            try {
                if (img != 1) {
                    Desktop.getDesktop().open(new java.io.File(caminho.getText()));
                }
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
    String pastaSys;
    File antiga;
    private void pastaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pastaMouseClicked
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Escolher pasta de salvamento");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    Thread t2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            prog("Copiando arquivos para a nova pasta");
                        }
                    });
                    t2.start();

                    pastaSys = fc.getSelectedFile().getAbsolutePath();
                    System.out.println(pastaSys);
                    FileWriter pastaS = null;
                    try {
                        pastaS = new FileWriter("pastaSys.txt");
                    } catch (IOException ex) {
                        Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PrintWriter gravarPasta = new PrintWriter(pastaS);
                    gravarPasta.println(pastaSys);
                    try {
                        if (pastaS != null) {
                            pastaS.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!pastaDoSistema.equals(pastaSys)) {
                        File srcDir = new File(pastaDoSistema);
                        File dstDir = new File(pastaSys);
                        try {
                            copyDirectory(new File(srcDir, "\\GedCartImagens"), new File(dstDir, "\\GedCartImagens"));
                        } catch (IOException ex) {
                            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        try {
//                            copyDirectory(new File(srcDir, "\\DadosDoUsuario"), new File(dstDir, "\\DadosDoUsuario"));
//                        } catch (IOException ex) {
//                            Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        antiga = new File(pastaDoSistema + "\\GedCartImagens");
                        pastaDoSistema = pastaSys;
                    }
                    String[] entries = antiga.list();
                    for (String s : entries) {
                        File currentFile = new File(antiga.getPath(), s);
                        currentFile.delete();
                    }
                    antiga.delete();
                    progresso = 1;
                }
            }
        });
        t1.start();

//        try {
//            Desktop.getDesktop().open(new java.io.File("C:\\GedCart\\GedCartImagens/"));
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(this, "Não foi possível abrir a pasta.", "Documentos", 0,
//                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
//        }
    }//GEN-LAST:event_pastaMouseClicked

    private void nomePastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomePastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomePastaActionPerformed

    private void inserePastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserePastaActionPerformed
        try {
            novaPasta = JOptionPane.showInputDialog(this, "Nova Pasta:", "Alterar Pasta", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException headlessException) {
            JOptionPane.showMessageDialog(this, "Nome de pasta inválido", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
        String n = nomePasta.getText();
        if (n.equals(novaPasta)) {
            JOptionPane.showMessageDialog(this, "Essa pasta já está sendo usada", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
        if (novaPasta.equals("") || novaPasta == null) {
            JOptionPane.showMessageDialog(this, "Nome de pasta inválido", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        } else {
            if (!n.equals(novaPasta)) {
                nomePasta.setText(novaPasta);
                FileWriter pastaU = null;
                try {
                    pastaU = new FileWriter("pastaUp.txt");
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrintWriter gravarPasta = new PrintWriter(pastaU);
                gravarPasta.println(nomePasta.getText());
                try {
                    if (pastaU != null) {
                        pastaU.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                File file = new File("D:\\Documentos\\NetBeansProjects\\GEDCart\\tokens\\StoredCredential");
                file.delete();
                JOptionPane.showMessageDialog(this, "Pasta atualizada com sucesso!", "Documentos", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
                limparCampos();
            }
        }
    }//GEN-LAST:event_inserePastaActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarActionPerformed

    private void tabelaDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDocumentosMouseClicked
        anterior = scan.getText();
    }//GEN-LAST:event_tabelaDocumentosMouseClicked

    private void uploadSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadSelectedActionPerformed
        if (caminho.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Selecione um registro!", "Documentos", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        } else {
            FileWriter arq = null;
            try {
                arq = new FileWriter("upload.txt");
            } catch (IOException ex) {
                Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(scan.getText());
            gravarArq.println(caminho.getText());
            try {
                if (arq != null) {
                    arq.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FrmDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }

            upload();
        }
    }//GEN-LAST:event_uploadSelectedActionPerformed

    private void addPessoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPessoasActionPerformed
        if (estaFechado(lista)) {
            lista = new FrmPessoas();
            principal.MenuPrincipal.carregador.add(lista).setLocation(150, 10);

            lista.toFront();
            lista.setVisible(true);
        } else {
            lista.toFront();
        }
    }//GEN-LAST:event_addPessoasActionPerformed

    private void rmPessoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmPessoasActionPerformed
        if (tabelaPessoas.getRowCount() > 0) {
            DefaultTableModel modelo = (DefaultTableModel) tabelaPessoas.getModel();
            int linha = tabelaPessoas.getSelectedRow();
            if (linha >= 0) {
                modelo.removeRow(linha);
            } else {
                JOptionPane.showMessageDialog(this, "Selecionar uma Linha.", "Venda", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não há pessoas para excluir.", "Venda", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_rmPessoasActionPerformed

    private void addScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addScannerActionPerformed
        addScan();
    }//GEN-LAST:event_addScannerActionPerformed

    private void rmScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmScannerActionPerformed
        if (tabelaScan.getRowCount() > 0) {
            DefaultTableModel modelo = (DefaultTableModel) tabelaScan.getModel();
            int linha = tabelaScan.getSelectedRow();
            if (linha >= 0) {
                modelo.removeRow(linha);
                if (tabelaScan.getRowCount() == 0) {
                    ImageIcon im = new ImageIcon(getClass().getResource("/imagens/rescan-document.png"));
                    Image i = im.getImage().getScaledInstance(500, 510, Image.SCALE_SMOOTH);
                    jLabelImagem.setIcon(new ImageIcon(i));
                    imagem = null;
                }
                if (tabelaScan.getRowCount() > 0) {
                    tabelaScan.addRowSelectionInterval(0, 0);
                    int a = tabelaScan.getRowCount();
                    System.out.println(a);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecionar uma Linha.", "Venda", 0,
                        new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não há scanners para excluir.", "Venda", 0,
                    new ImageIcon(getClass().getResource("/imagens/usuarios/info.png")));
        }
    }//GEN-LAST:event_rmScannerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPessoas;
    private javax.swing.JButton addScanner;
    private javax.swing.JButton atualizar;
    private app.bolivia.swing.JCTextField buscar;
    private javax.swing.JTextField caminho;
    public static app.bolivia.swing.JCTextField codigo;
    private javax.swing.JLabel codigoL;
    private javax.swing.JLabel codigoL1;
    private javax.swing.JButton excluir;
    private javax.swing.JButton inserePasta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelImagem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton limpar;
    private app.bolivia.swing.JCTextField nome;
    private javax.swing.JLabel nomeL;
    private javax.swing.JTextField nomePasta;
    private javax.swing.JLabel pasta;
    private javax.swing.JButton registrar;
    private javax.swing.JButton rmPessoas;
    private javax.swing.JButton rmScanner;
    private javax.swing.JTextField scan;
    public static javax.swing.JTable tabelaDocumentos;
    public static javax.swing.JTable tabelaPessoas;
    public static javax.swing.JTable tabelaPessoas2;
    public static javax.swing.JTable tabelaScan;
    public static javax.swing.JTable tabelaScan2;
    private org.bolivia.combo.SComboBoxBlue tipo;
    private javax.swing.JButton uploadSelected;
    // End of variables declaration//GEN-END:variables
}

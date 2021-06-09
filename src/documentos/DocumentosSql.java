/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentos;

import static documentos.FrmDocumentos.tabelaPessoas2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import principal.Conectar;
import principal.GerarCodigos;

/**
 *
 * @author Victor
 */
public class DocumentosSql {

    static Conectar cc = new Conectar();
    static Connection cn = cc.conexao();
    static PreparedStatement ps;

    public static void listarDocumentos(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) documentos.FrmDocumentos.tabelaDocumentos.getModel();
        DefaultTableModel modelo2 = (DefaultTableModel) documentos.FrmDocumentos.tabelaPessoas2.getModel();
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        while (modelo2.getRowCount() > 0) {
            modelo2.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Documentos.LISTAR_DOC;
        } else {
            sql = "SELECT * FROM documentos WHERE (codigo_doc like'" + busca + "%' or nome_doc like'%" + busca + "%' or textoOCR like'%" + busca + "%') "
                    + " order by codigo_doc";

        }
        String dados[] = new String[5];
        String dados2[] = new String[2];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("codigo_doc");
                dados[1] = rs.getString("nome_doc");
                dados[2] = rs.getString("tipo_doc");
                dados[3] = rs.getString("scan");
                dados[4] = rs.getString("textoOCR");
                dados2[0] = rs.getString("pessoas_doc");
                modelo.addRow(dados);
                modelo2.addRow(dados2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listarCat(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) documentos.FrmListaDoc.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Documentos.LISTAR_DOC;
        } else {
            sql = "SELECT * FROM documentos WHERE (tipo_doc like'" + busca + "%' or nome_doc like'%" + busca + "%' or textoOCR like'%" + busca + "%') "
                    + " order by codigo_doc";

        }
        String dados[] = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("codigo_doc");
                dados[1] = rs.getString("nome_doc");
                dados[2] = rs.getString("tipo_doc");
                dados[3] = rs.getString("scan");
                dados[4] = rs.getString("textoOCR");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int registrarDocumento(Documentos uc) {
        int rsu = 0;
        String sql = Documentos.REGISTRAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getPrimaryKey());
            ps.setString(2, uc.getNome());
            ps.setString(3, uc.getTipodoc());
            ps.setString(4, uc.getPessoas());
            ps.setString(5, uc.getScan());
            ps.setString(6, uc.getTextoOCR());
            //ps.setBytes(4, uc.getScan());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static void gerarId() {
        int j;
        int cont = 1;
        String num = "";
        String c = "";
        String SQL = "SELECT MAX(codigo_doc) FROM documentos";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                c = rs.getString(1);
            }

            if (c == null) {
                documentos.FrmDocumentos.codigo.setText("DOC0001");
            } else {
                char r1 = c.charAt(3);
                char r2 = c.charAt(4);
                char r3 = c.charAt(5);
                char r4 = c.charAt(6);
                String r;
                r = "" + r1 + r2 + r3 + r4;
                j = Integer.parseInt(r);
                //System.out.println(j);
                GerarCodigos gen = new GerarCodigos();
                gen.gerar(j);
                documentos.FrmDocumentos.codigo.setText("DOC" + gen.serie());

            }

        } catch (SQLException ex) {
            Logger.getLogger(DocumentosSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int atualizarDocumento(Documentos uc) {
        int rsu = 0;
        String sql = Documentos.ATUALIZAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getNome());
            ps.setString(2, uc.getTipodoc());
            ps.setString(3, uc.getScan());
            ps.setString(4, uc.getTextoOCR());
            ps.setString(5, uc.getPrimaryKey());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            print(ex);
        }
        System.out.println(sql);
        return rsu;
    }

    public static int eliminarDocumento(String id) {
        int rsu = 0;
        String sql = Documentos.ELIMINAR;

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static int eliminaTodos() {
        int rsu = 0;
        String sql = Documentos.ELIMINAR_TUDO;
        try {
            ps = cn.prepareStatement(sql);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static boolean isNumber(String n) {
        try {
            if (Integer.parseInt(n) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static void print(SQLException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

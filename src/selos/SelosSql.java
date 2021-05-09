/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selos;

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
public class SelosSql {
    static Conectar cc = new Conectar();
    static Connection cn = cc.conexao();
    static PreparedStatement ps;

    public static void listarSelos(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) selos.FrmListaSelo.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Selos.LISTAR_SELO;
        } else {
            sql = "SELECT * FROM selos WHERE (codigo_selo like'" + busca + "%' or tipo_selo like'%" + busca + "%' or textoOCR like'%" + busca + "%') "
                    + " order by codigo_selo";

        }
         String dados[] = new String[2];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("codigo_selo");
                dados[1] = rs.getString("tipo_selo");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Selos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listarCat(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) selos.FrmListaSelo.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Selos.LISTAR_SELO;
        } else {
            sql = "SELECT * FROM selos WHERE (codigo_selo like'" + busca + "%' or tipo_selo like'%" + busca + "%') "
                    + " order by codigo_selo";

        }
         String dados[] = new String[2];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("codigo_selo");
                dados[1] = rs.getString("tipo_selo");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Selos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int registrarSelo(Selos uc) {
        int rsu = 0;
        String sql = Selos.REGISTRAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getPrimaryKey());
            ps.setString(2, uc.getTipoSelo());
            //ps.setBytes(4, uc.getScan());
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

//    public static void gerarId() {
//        int j;
//        int cont = 1;
//        String num = "";
//        String c = "";
//        String SQL = "SELECT MAX(codigo_doc) FROM selos";
//
//        try {
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(SQL);
//            while (rs.next()) {
//                c = rs.getString(1);
//            }
//
//            if (c == null) {
//                selos.FrmListaSelo.codigo.setText("DOC0001");
//            } else {
//                char r1 = c.charAt(3);
//                char r2 = c.charAt(4);
//                char r3 = c.charAt(5);
//                char r4 = c.charAt(6);
//                String r = "";
//                r = "" + r1 + r2 + r3 + r4;
//                j = Integer.parseInt(r);
//                GerarCodigos gen = new GerarCodigos();
//                gen.gerar(j);
//                selos.FrmSelos.codigo.setText("DOC" + gen.serie());
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(SelosSql.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public static int atualizarDocumento(Selos uc) {
//        int rsu = 0;
//        String sql = Selos.ATUALIZAR;
//        try {
//            ps = cn.prepareStatement(sql);
//            ps.setString(1, uc.getNome());
//            ps.setString(2, uc.getTipodoc());
//            ps.setString(3, uc.getPrimaryKey());
//            rsu = ps.executeUpdate();
//        } catch (SQLException ex) {
//            print(ex);
//        }
//        System.out.println(sql);
//        return rsu;
//    }
//
//    public static int eliminarDocumento(String id) {
//        int rsu = 0;
//        String sql = Selos.ELIMINAR;
//
//        try {
//            ps = cn.prepareStatement(sql);
//            ps.setString(1, id);
//            rsu = ps.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        System.out.println(sql);
//        return rsu;
//    }
//
//    public static int eliminaTodos() {
//        int rsu = 0;
//        String sql = Selos.ELIMINAR_TUDO;
//        try {
//            ps = cn.prepareStatement(sql);
//            rsu = ps.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        System.out.println(sql);
//        return rsu;
//    }

    private static void print(SQLException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

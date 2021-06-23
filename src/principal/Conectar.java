/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javax.swing.JOptionPane;

/**
 *
 * @author Victor
 */
public class Conectar {

    Connection conect = null;

    public Connection conexao() {

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            conect = DriverManager.getConnection("jdbc:derby:GEDCartBD");

//            Class.forName("com.mysql.jdbc.Driver");
//            conect = DriverManager.getConnection("jdbc:mysql://localhost/gedcart", "root", "");
//            Statement st = conect.createStatement();
            
//             String sql = "delete from documentos";
//             st.execute(sql);
//            st.execute(sql);
//            UUID uuid = UUID.randomUUID();
//            String myRandom = uuid.toString();
//            System.out.println(myRandom.substring(0, 20));
//            String sql = "INSERT INTO documentos (codigo_doc, nome_doc, tipo_doc, pessoas_doc, scan, textoOCR) VALUES"
//                    + "('DOC0001', '" + myRandom + "', '" + myRandom + "', '" + myRandom + "', '" + myRandom + "', '" + myRandom + "')";
//            st.execute(sql);

//            for (int i = 0; i < 10000; i++) {
//                UUID uuid = UUID.randomUUID();
//                String myRandom = uuid.toString();
//                System.out.println(myRandom.substring(0, 20));
//
//                String sql = "INSERT INTO documentos (codigo_doc, nome_doc, tipo_doc, pessoas_doc, scan, textoOCR) VALUES"
//                        + "('" + i + "', '" + myRandom + "', '" + myRandom + "', '" + myRandom + "', '" + myRandom + "', '" + myRandom + "')";
//                st.execute(sql);
//            }

            System.out.println("Realizado com sucesso");

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }

        return conect;
    }

}

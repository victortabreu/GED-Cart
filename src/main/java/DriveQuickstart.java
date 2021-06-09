// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// [START drive_quickstart]
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriveQuickstart {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    static void lista(String nome, String id){
        FileWriter up = null;

        try {
            up = new FileWriter("C:\\GedCart\\ids.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(DriveQuickstart.class.getName()).log(Level.SEVERE, null, ex);
        }

            PrintWriter gravarUp = new PrintWriter(up, true);
            gravarUp.println(nome);
            gravarUp.println(id);

        try {
            up.close();
        } catch (IOException ex) {
            Logger.getLogger(DriveQuickstart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        String caminho;
        String nome;
        String idPasta;
        String anterior;
        String nomeAtt;
        String caminhoAtt;
        java.io.File arquivo = new java.io.File("C:\\GedCart\\upload.txt");
        Scanner scanner = new Scanner(arquivo,"UTF-8");
        nome = scanner.nextLine();

        java.io.File pasta = new java.io.File("C:\\GedCart\\pasta.txt");
        Scanner scan = new Scanner(pasta,"UTF-8");
        idPasta = scan.nextLine();        
        
        if(nome.equals("1")){
            java.io.File att = new java.io.File("C:\\GedCart\\atualizar.txt");
            Scanner sc1 = new Scanner(att,"UTF-8");
            anterior = sc1.nextLine(); 
            nomeAtt = sc1.nextLine();
            caminhoAtt = sc1.nextLine();
           
            java.io.File lista = new java.io.File("C:\\GedCart\\ids.txt");
            Scanner sc2 = new Scanner(lista,"UTF-8");
            String linha;
            
            while (sc2.hasNextLine()) {
               linha = sc2.nextLine();
                if(linha.equals(anterior)){
                    String apagaID = sc2.nextLine();
                    service.files().delete(idPasta +"/"+ apagaID);
                }
            }
           
            String folderId = idPasta;
            File fileMetadata = new File();
            fileMetadata.setName(nomeAtt);
            fileMetadata.setParents(Collections.singletonList(folderId));
            java.io.File filePath = new java.io.File(caminhoAtt);
            FileContent mediaContent = new FileContent("image/jpeg", filePath);
            File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
            System.out.println("File ID: " + file.getId());
            lista(nome,file.getId());           

        }else{
            caminho = scanner.nextLine();           

            String folderId = idPasta;
            File fileMetadata = new File();
            fileMetadata.setName(nome);
            fileMetadata.setParents(Collections.singletonList(folderId));
            java.io.File filePath = new java.io.File(caminho);
            FileContent mediaContent = new FileContent("image/jpeg", filePath);
            File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
            System.out.println("File ID: " + file.getId());
            lista(nome,file.getId());
        }
    }
}
// [END drive_quickstart]

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
package documentos;
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
import com.google.api.services.drive.model.FileList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DriveG {
    
    public DriveG() throws IOException, GeneralSecurityException, InterruptedException{
        executa();
    }

    private static final String APPLICATION_NAME = "GEDCart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    
    
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveG.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

    public static String lista = "";
    public static String nome;
    public static int a = 0;

    public static void executa() throws IOException, GeneralSecurityException, InterruptedException {
        // Build a new authorized API client service.

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String caminho;
        String modo;
        String idPasta;

        java.io.File arquivo = new java.io.File("upload.txt");
        Scanner scanner = new Scanner(arquivo, "UTF-8");
        modo = scanner.nextLine();

        java.io.File pasta = new java.io.File("pastaUp.txt");
        Scanner scan = new Scanner(pasta, "UTF-8");
        idPasta = scan.nextLine();

        int op = Integer.parseInt(modo);
        String pageToken = null;

        switch (op) {
            case 1:
                System.out.println("FAZENDO UPLOAD DOS ARQUIVOS:\n");
                while (scanner.hasNextLine()) {
                    nome = scanner.nextLine();
                    caminho = scanner.nextLine();

                    String folderId = idPasta;
                    File fileMetadata = new File();
                    fileMetadata.setName(nome);
                    fileMetadata.setParents(Collections.singletonList(folderId));
                    java.io.File filePath = new java.io.File(caminho);
                    FileContent mediaContent = new FileContent("image/jpeg", filePath);
                    File file;
                    try {
                        file = service.files().create(fileMetadata, mediaContent)
                                .setFields("id, parents")
                                .execute();
                        System.out.println("ARQUIVO ENVIADO:" + nome + " ID: " + file.getId());
                    } catch (IOException iOException) {
                        System.out.println("ARQUIVO NAO EXISTE");
                    }
                }
                for (int i = 0; i < 2; ++i) {
                    System.out.println();
                }
                break;

            case 2:
                System.out.println("ATUALIZANDO OS ARQUIVOS:\n");
                int i = 0;
                while (i == 0) {
                    nome = scanner.nextLine();
                    if (nome.equals("Novas")) {
                        i = 1;
                    } else {
                        do {
                            FileList result = service.files().list()
                                    .setQ("'" + idPasta + "' in parents")
                                    .setSpaces("drive")
                                    .setFields("nextPageToken, files(id, name)")
                                    .setPageToken(pageToken)
                                    .execute();
                            result.getFiles().forEach((file) -> {
                                if (file.getName().equals(nome)) {
                                    try {
                                        service.files().delete(file.getId()).execute();
                                        System.out.println("EXCLUIDO DO DRIVE: "
                                                + file.getName() + " ID: " + file.getId());
                                    } catch (IOException ex) {
                                        Logger.getLogger(DriveG.class.getName()).log(Level.SEVERE, null, ex);
                                        System.out.println("ARQUIVO NAO EXISTE");
                                    }
                                }
                            });
                            pageToken = result.getNextPageToken();
                        } while (pageToken != null);
                    }
                }

                while (scanner.hasNextLine()) {
                    nome = scanner.nextLine();
                    caminho = scanner.nextLine();

                    String folderId = idPasta;
                    File fileMetadata = new File();
                    fileMetadata.setName(nome);
                    fileMetadata.setParents(Collections.singletonList(folderId));
                    java.io.File filePath = new java.io.File(caminho);
                    FileContent mediaContent = new FileContent("image/jpeg", filePath);
                    File file;
                    try {
                        file = service.files().create(fileMetadata, mediaContent)
                                .setFields("id, parents")
                                .execute();
                        System.out.println("ARQUIVO ATUALIZADO:" + nome + " ID: " + file.getId());
                    } catch (IOException iOException) {
                        System.out.println("ARQUIVO NAO EXISTE");
                    }

                }
                for (int j = 0; j < 2; ++j) {
                    System.out.println();
                }
                break;

            case 3:
                System.out.println("EXCLUINDO OS ARQUIVOS:\n");
                while (scanner.hasNextLine()) {
                    nome = scanner.nextLine();
                    do {
                        FileList result = service.files().list()
                                .setQ("'" + idPasta + "' in parents")
                                .setSpaces("drive")
                                .setFields("nextPageToken, files(id, name)")
                                .setPageToken(pageToken)
                                .execute();
                        result.getFiles().forEach((file) -> {
                            if (file.getName().equals(nome)) {
                                try {
                                    service.files().delete(file.getId()).execute();
                                    System.out.println("EXCLUIDO DO DRIVE: "
                                            + file.getName() + " ID: " + file.getId());
                                } catch (IOException ex) {
                                    Logger.getLogger(DriveG.class.getName()).log(Level.SEVERE, null, ex);
                                    System.out.println("ARQUIVO NAO EXISTE");
                                }
                            }
                        });
                        pageToken = result.getNextPageToken();
                    } while (pageToken != null);
                }
                for (int j = 0; j < 2; ++j) {
                    System.out.println();
                }
                break;

            case 4:
                System.out.println("FAZENDO UPLOAD DOS ARQUIVOS:\n");
                while (scanner.hasNextLine()) {
                    a = 0;
                    nome = scanner.nextLine();
                    caminho = scanner.nextLine();

                    do {
                        FileList result = service.files().list()
                                .setQ("'" + idPasta + "' in parents")
                                .setSpaces("drive")
                                .setFields("nextPageToken, files(id, name)")
                                .setPageToken(pageToken)
                                .execute();
                        result.getFiles().forEach((file) -> {
                            if (file.getName().equals(nome)) {
                                a++;
                            }
                        });
                        pageToken = result.getNextPageToken();
                    } while (pageToken != null);
                    if (a == 0) {
                        String folderId = idPasta;
                        File fileMetadata = new File();
                        fileMetadata.setName(nome);
                        fileMetadata.setParents(Collections.singletonList(folderId));
                        java.io.File filePath = new java.io.File(caminho);
                        FileContent mediaContent = new FileContent("image/jpeg", filePath);
                        File file;
                        try {
                            file = service.files().create(fileMetadata, mediaContent)
                                    .setFields("id, parents")
                                    .execute();
                            System.out.println("ARQUIVO ENVIADO:" + nome + " ID: " + file.getId());
                        } catch (IOException iOException) {
                            System.out.println("ARQUIVO NAO EXISTE");
                        }
                    } else {
                        System.out.println("ARQUIVO JA FOI ENVIADO ANTERIORMENTE: ");
                    }
                }
                break;
            default:

        }
        System.out.println("\n------- OPERACAO REALIZADA COM SUCESSO ------- 100%");

    }
}
// [END drive_quickstart]

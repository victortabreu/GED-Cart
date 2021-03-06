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

public class DriveQuickstart {

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
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

    public static String lista = "";
    public static String nome;
    public static int a = 0;
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static void main(String... args) throws IOException, GeneralSecurityException, InterruptedException {
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

        for (int i = 0; i < 3; ++i) {
            System.out.println();
        }

        switch (op) {
            case 1:
                System.out.println(ANSI_BLACK + ANSI_CYAN_BACKGROUND + "FAZENDO UPLOAD DOS ARQUIVOS:\n\n" + ANSI_RESET);
                while (scanner.hasNextLine()) {
                    nome = scanner.nextLine();
                    caminho = scanner.nextLine();

                    String folderId = idPasta;
                    File fileMetadata = new File();
                    fileMetadata.setName(nome);
                    fileMetadata.setParents(Collections.singletonList(folderId));
                    java.io.File filePath = new java.io.File(caminho);
                    FileContent mediaContent = new FileContent("image/jpeg", filePath);
                    File file = null;
                    try {
                        file = service.files().create(fileMetadata, mediaContent)
                                .setFields("id, parents")
                                .execute();
                        System.out.println(ANSI_BLACK + ANSI_GREEN_BACKGROUND + "ARQUIVO ENVIADO:" + nome + " ID: " + file.getId() + ANSI_RESET);
                    } catch (IOException iOException) {
                        System.out.println(ANSI_RED_BACKGROUND + "ARQUIVO NAO EXISTE" + ANSI_RESET);
                    }
                }
                for (int i = 0; i < 2; ++i) {
                    System.out.println();
                }
                break;

            case 2:
                System.out.println(ANSI_BLACK + ANSI_CYAN_BACKGROUND + "ATUALIZANDO OS ARQUIVOS:\n\n" + ANSI_RESET);
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
                                        System.out.println(ANSI_RED_BACKGROUND + "EXCLUIDO DO DRIVE: "
                                                + file.getName() + " ID: " + file.getId() + ANSI_RESET);
                                    } catch (IOException ex) {
                                        Logger.getLogger(DriveQuickstart.class.getName()).log(Level.SEVERE, null, ex);
                                        System.out.println(ANSI_RED_BACKGROUND + "ARQUIVO NAO EXISTE" + ANSI_RESET);
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
                    File file = null;
                    try {
                        file = service.files().create(fileMetadata, mediaContent)
                                .setFields("id, parents")
                                .execute();
                        System.out.println(ANSI_BLACK + ANSI_GREEN_BACKGROUND + "ARQUIVO ATUALIZADO:" + nome + " ID: " + file.getId() + ANSI_RESET);
                    } catch (IOException iOException) {
                        System.out.println(ANSI_RED_BACKGROUND + "ARQUIVO NAO EXISTE" + ANSI_RESET);
                    }

                }
                for (int j = 0; j < 2; ++j) {
                    System.out.println();
                }
                break;

            case 3:
                System.out.println(ANSI_RED_BACKGROUND + "EXCLUINDO OS ARQUIVOS:\n\n" + ANSI_RESET);
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
                                    System.out.println(ANSI_RED_BACKGROUND + "EXCLUIDO DO DRIVE: "
                                            + file.getName() + " ID: " + file.getId() + ANSI_RESET);
                                } catch (IOException ex) {
                                    Logger.getLogger(DriveQuickstart.class.getName()).log(Level.SEVERE, null, ex);
                                    System.out.println(ANSI_RED_BACKGROUND + "ARQUIVO NAO EXISTE" + ANSI_RESET);
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
                System.out.println(ANSI_BLACK + ANSI_CYAN_BACKGROUND + "FAZENDO UPLOAD DOS ARQUIVOS:\n\n" + ANSI_RESET);
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
                            System.out.println(ANSI_BLACK + ANSI_GREEN_BACKGROUND + "ARQUIVO ENVIADO:" + nome + " ID: " + file.getId() + ANSI_RESET);
                        } catch (IOException iOException) {
                            System.out.println(ANSI_RED_BACKGROUND + "ARQUIVO NAO EXISTE" + ANSI_RESET);
                        }
                    } else {
                        System.out.println(ANSI_BLACK + ANSI_YELLOW_BACKGROUND + "ARQUIVO JA FOI ENVIADO ANTERIORMENTE: " + nome + ANSI_RESET);
                    }
                }
                for (int j = 0; j < 2; ++j) {
                    System.out.println();
                }
                break;
            default:

        }
        System.out.println(ANSI_BLACK + ANSI_GREEN_BACKGROUND + "------- OPERACAO REALIZADA COM SUCESSO -------" + ANSI_RESET + " 100%\n");
        System.out.println(ANSI_BLUE_BACKGROUND + "---------- APERTE ENTER PARA SAIR ----------" + ANSI_RESET);
        System.out.println(ANSI_BLUE_BACKGROUND + "---------- APERTE ENTER PARA SAIR ----------" + ANSI_RESET);
        System.out.println(ANSI_BLUE_BACKGROUND + "---------- APERTE ENTER PARA SAIR ----------" + ANSI_RESET + "\n");
        Scanner sc = new Scanner(System.in);
        String fim = sc.nextLine();
        System.out.println(fim);

    }
}
// [END drive_quickstart]

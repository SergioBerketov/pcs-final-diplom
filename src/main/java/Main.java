import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final int PORT = 8989;

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

            out.println("Сервер подключен!" + "\n" + "Введите Ваш запрос в поле 'word'");
            System.out.println("Connected.");

            String word = in.readLine();

            List<PageEntry> processedRequest = engine.search(word);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            gson.toJson(processedRequest);

            out.println(gson);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
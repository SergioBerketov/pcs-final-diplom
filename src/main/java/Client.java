import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8989;

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            String answer = in.readLine();
            System.out.println(answer);
            String word = "бизнес";
            out.println(word);

            String newAnswer = in.readLine();

            List<String> listOfWords = new ArrayList<>();
            listOfWords.add(newAnswer);

            StringBuilder builder = new StringBuilder();

            for (String s : listOfWords) {
                builder.append(s);
            }
            System.out.println(builder.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
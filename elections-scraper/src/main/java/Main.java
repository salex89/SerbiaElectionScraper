import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by aleksandar on 4/23/16.
 * Arguments:
 * 0 - Output File
 * 1 - Consumer Key
 * 2 - Consumer Secret
 * 3 - Token
 * 4 - Token Secret
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        String consumerKey = args[1];
        String consumerSecret = args[2];
        String token = args[3];
        String tokenSecret = args[4];

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);
        Client client = initClient(consumerKey, consumerSecret, token, tokenSecret, queue);
        File outputFile = new File(args[0]);
        PrintWriter printWriter
                = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        client.connect();
        executor.submit(() -> {
            while (!client.isDone()) {
                try {
                    String msg = queue.poll(100, TimeUnit.MILLISECONDS);
                    if (msg != null) {
                        printWriter.println(msg);
                        System.out.println(msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        waitForExit();
        client.stop();
        executor.shutdown();
        try {
            executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } finally {
            printWriter.close();
        }
    }

    private static Client initClient(String consumerKey, String consumerSecret, String token, String tokenSecret, BlockingQueue<String> queue) {
        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, tokenSecret);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Lists.newArrayList("#izbori2016", "#ИЗБОРИ2016"));
        return new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();
    }

    private static void waitForExit() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().contentEquals("exit")) {
        }
    }
}

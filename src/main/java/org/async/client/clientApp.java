package org.async.client;

import commons.receiver;
import commons.getConnection;
import com.rabbitmq.client.*;
import java.util.Scanner;


public class clientApp {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Worker Name: ");
        String QUEUE_NAME = sc.next();
        Connection connection = getConnection.get();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("RECEIVING!!");
        receiver rec = new receiver();
        rec.recData(QUEUE_NAME, rec.getDeliverCallbackClient(QUEUE_NAME, channel), channel);

    }
}

package org.async.master;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import commons.receiver;
import commons.sender;
import commons.getConnection;
import java.io.File;
import java.util.Scanner;
public class masterApp
{
    public static void main( String[] args ) throws Exception {
        Scanner sc = new Scanner(System.in);
        receiver rec = new receiver();
        sender sender = new sender();
        String QUEUE_NAME_MASTER = "quem";
        String QUEUE_NAME_WORKER;
        Connection connection = getConnection.get();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_MASTER, false, false, false, null);
        // Receiving Output from Client
        rec.recData(QUEUE_NAME_MASTER, rec.getDeliverCallbackMaster(QUEUE_NAME_MASTER, channel), channel);
        System.out.println("Enter Worker Name to send: ");
        QUEUE_NAME_WORKER = sc.next();
        while (!QUEUE_NAME_MASTER.equals("Exit")) {
            File file = new File("d:\\black\\Desktop\\test.py");
            byte[] data = sender.setDataForClient("test.py", "python test.py", file);
            sender.sendData(data, QUEUE_NAME_WORKER, channel);
            System.out.println("RECEIVING OUTPUT FROM- " + QUEUE_NAME_WORKER);
            System.out.println("Enter Worker Name to send: ");
            QUEUE_NAME_WORKER = sc.next();
        }


    }
}

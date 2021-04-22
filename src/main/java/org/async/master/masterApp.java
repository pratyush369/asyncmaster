package org.async.master;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import commons.*;
import java.io.File;
import java.util.Scanner;
public class masterApp
{
    public static void main( String[] args ) throws Exception {
        Scanner sc = new Scanner(System.in);
        receiver rec = new receiver();
        sender sender = new sender();
        getFile getfile = new getFile();
        String command = "";
        String QUEUE_NAME_MASTER = "quem";
        String QUEUE_NAME_WORKER;
        Connection connection = getConnection.get();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_MASTER, false, false, false, null);
        // Receiving Output from Client
        rec.recData(QUEUE_NAME_MASTER, rec.getDeliverCallbackMaster(QUEUE_NAME_MASTER, channel), channel);
        System.out.println("Enter Worker Name to send: ");
        QUEUE_NAME_WORKER = sc.nextLine();
        while (!QUEUE_NAME_WORKER.equals("Exit")) {
            File file = getfile.choose();
            System.out.println("Enter Command to run: ");
            command = sc.nextLine();
            System.out.println("Entered Command: " + command);
            byte[] data = sender.setDataForClient(file.getName(), command, file);
            sender.sendData(data, QUEUE_NAME_WORKER, channel);
            System.out.println("RECEIVING OUTPUT FROM- " + QUEUE_NAME_WORKER);
            //System.out.println("Enter Worker Name to send: ");
            QUEUE_NAME_WORKER = sc.nextLine();
        }
//

    }
}

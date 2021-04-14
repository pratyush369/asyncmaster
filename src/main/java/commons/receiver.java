package commons;

import com.rabbitmq.client.*;
import org.async.client.execEngine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import java.io.File;


public class receiver extends execEngine {

    public void recData(String QUEUE_NAME, DeliverCallback deliverCallback, Channel channel) throws Exception {
        System.out.println(" [*] Waiting for messages.");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
    /**
     * Used in Master
     */
    public DeliverCallback getDeliverCallbackMaster(String QUEUE_NAME, Channel channel){
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                byte[] message = delivery.getBody();
                commonAck commonAck = SerializationUtils.deserialize(message);
                System.out.println(" [x] Received  ");
                String worker = commonAck.getWorkerName();
                System.out.println("Worker Name: " + worker);
                String output = commonAck.getOutput();
                System.out.println("Output:\n" + output);
                long timeTaken = commonAck.getTimeTaken();
                System.out.println("Time Taken: " + timeTaken);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    recData(QUEUE_NAME, getDeliverCallbackMaster(QUEUE_NAME, channel), channel);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        };
        return deliverCallback;
    }
    /**
     * Used in Client
     */
    public DeliverCallback getDeliverCallbackClient(String QUEUE_NAME, Channel channel) {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                byte[] message = delivery.getBody();
                System.out.println(" [x] Received '" + message + "'");
                commandSend commandSend = SerializationUtils.deserialize(message);
                File newFile = new File(commandSend.getFileName());
                System.out.println(newFile.getAbsolutePath());
                FileUtils.writeByteArrayToFile(newFile,commandSend.getFile());
                sender sender = new sender();
                byte[] msg = execute(commandSend.getCommand(),QUEUE_NAME);
                sender.sendData(msg,"quem",channel);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    recData(QUEUE_NAME, getDeliverCallbackClient(QUEUE_NAME,channel), channel);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        };
        return deliverCallback;
    }


}

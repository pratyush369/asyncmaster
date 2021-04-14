package commons;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.commons.lang3.SerializationUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class sender {
    public byte[] setDataForClient(String fname, String command, File src) throws IOException {
        commandSend newsend = new commandSend();
        newsend.setCommand(command);
        newsend.setFileName(fname);
        newsend.setFile(Files.readAllBytes(src.toPath()));
        return SerializationUtils.serialize(newsend);
    }
    public byte[] setDataForMaster (String output, long timeTaken, String workerName){
        commonAck commonAck = new commonAck();
        commonAck.setOutput(output);
        commonAck.setTimeTaken(timeTaken);
        commonAck.setWorkerName(workerName);
        return SerializationUtils.serialize(commonAck);
    }
    public void sendData(byte[] message, String QUEUE_NAME, Channel channel) throws Exception{
        Connection connection = getConnection.get();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message);

    }
}
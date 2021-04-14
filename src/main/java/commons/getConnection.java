package commons;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class getConnection {
    static ConnectionFactory factory = new ConnectionFactory();
    static Connection connection;
    public void getConnection(){
        factory.setHost("localhost");
    }
    public static Connection get() throws IOException, TimeoutException {
        if (connection == null || !connection.isOpen()){
            connection = factory.newConnection();
        }
        return connection;
    }
}

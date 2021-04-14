package commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class commonAck implements Serializable {
    private String workerName;
    private String output;
    private long timeTaken;
}

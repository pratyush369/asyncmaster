package commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class commandSend implements Serializable {
    private byte[] file;
    private String command;
    private String fileName;

}

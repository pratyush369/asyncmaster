package commons;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class getFile extends Component {
    private static JFileChooser fileChooser = new JFileChooser();
    public File choose(){
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile;
        }
        else{
            return null;
        }
    }


}

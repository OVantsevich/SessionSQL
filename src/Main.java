import java.awt.EventQueue;
import java.awt.Frame;

public class Main {
    public static void main(String[] args) throws Exception {
    	EventQueue.invokeLater(() -> { 
    		Frame frame = new WorkingField();
		});
       
    }

}
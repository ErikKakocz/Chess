package game;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class GameViewController {

	
	GameViewController(){
		
		
	}
        
        public void onClick(ActionEvent e){
            Button b=(Button)e.getSource();
            System.out.println(b.getAccessibleHelp());
        
        
        }
}

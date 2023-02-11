package memory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MemoryKarte extends Button {

	private int bildID;
	private ImageView bildVorne, bildHinten;
	private int bildPos;
	private Boolean umgedreht;
	private Boolean nochImSpiel;

	class KartenListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			if (nochImSpiel == false)
				return;
			if (umgedreht == false) {
				setGraphic(bildVorne);
				umgedreht = true;
			}

		}

	}

	public MemoryKarte(String vorne, int bildID) {
			
		bildVorne = new ImageView(vorne);
		bildHinten = new ImageView("grafiken/back.jpg");
		
		setGraphic(bildHinten);

		this.bildID = bildID;
		umgedreht = false;
		nochImSpiel = true;

		setOnAction(new KartenListener());

	}

	public void rueckseiteZeigen(boolean rausnehmen) {
		if (rausnehmen == true) {
			setGraphic(new ImageView("grafiken/aufgedeckt.jpg"));
			nochImSpiel = false;
		} else {
			setGraphic(bildHinten);
			umgedreht = false;
		}
	}

	public int getBildID() {
		return bildID;
	}

	public int getBildPos() {
		return bildPos;
	}

	public void setBildPos(int bildPos) {
		this.bildPos = bildPos;
	}

}

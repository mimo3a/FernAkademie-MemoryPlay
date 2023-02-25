package memory;

import java.util.Collections;

import java.util.Arrays;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class MemoryFeld {

	private MemoryKarte[] karten;
	private String[] bilder = { "grafiken/apfel.jpg", "grafiken/birne.jpg", "grafiken/blume.jpg", "grafiken/blume2.jpg",
			"grafiken/ente.jpg", "grafiken/fisch.jpg", "grafiken/fuchs.jpg", "grafiken/igel.jpg",
			"grafiken/kaenguruh.jpg", "grafiken/katze.jpg", "grafiken/kuh.jpg", "grafiken/maus1.jpg",
			"grafiken/maus2.jpg", "grafiken/maus3.jpg", "grafiken/melone.jpg", "grafiken/pilz.jpg",
			"grafiken/ronny.jpg", "grafiken/schmetterling.jpg", "grafiken/sonne.jpg", "grafiken/wolke.jpg",
			"grafiken/maus4.jpg" };

//	points of human and computer
	private int menschPunkte, computerPunkte;
	private Label menschPunktLabel, computerPunktLabel, zugLabel;
//	amount of flipped cards
	private int umgedrehtKarten;
//	for the actualy flipped pairs
	private MemoryKarte[] paar;
//	actualy player
	private int spieler;
//	computer saves here where lies the opposit one
	private int[][] gemerkteKarten;
//	for the timer
	private Timeline timer, schimmelnTimer;
//	for current feld
	private FlowPane myfeld;
	private Button schummelnButton;

	class TimerHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			karteSchliessen();
		}
	}

	class SchummelnHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			schummeln();

		}

	}

	class SchummelnTimer implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {

			for (int i = 0; i < karten.length; i++) {
				if (karten[i].isNocnInSpiel())
					karten[i].setGraphic(new ImageView("grafiken/back.jpg"));

			}

		}
	}

	public MemoryFeld() {

		karten = new MemoryKarte[42];
		paar = new MemoryKarte[2];
		gemerkteKarten = new int[2][21];

		menschPunkte = 0;
		computerPunkte = 0;
		umgedrehtKarten = 0;
//		human begins
		spieler = 0;
//		no saves carts
		for (int ausen = 0; ausen < 2; ausen++) {
			for (int innen = 0; innen < 21; innen++) {
				gemerkteKarten[ausen][innen] = -1;
			}
		}

	}

	public FlowPane initGUI(FlowPane feld) {

		kartenZeichen(feld);
		this.myfeld = feld;
		menschPunktLabel = new Label();
		computerPunktLabel = new Label();
		zugLabel = new Label("Mensch");
		menschPunktLabel.setText(Integer.toString(menschPunkte));
		computerPunktLabel.setText(Integer.toString(computerPunkte));
		schummelnButton = new Button("Schummeln");
		schummelnButton.setOnAction(new SchummelnHandler());

		GridPane tempGrid = new GridPane();
		tempGrid.add(new Label("Mensch :"), 0, 0);
		tempGrid.add(menschPunktLabel, 1, 0);
		tempGrid.add(new Label("Computer :"), 0, 1);
		tempGrid.add(computerPunktLabel, 1, 1);
		tempGrid.add(new Label("Es zieht: "), 0, 2);
		tempGrid.add(zugLabel, 1, 2);
		tempGrid.add(schummelnButton, 1, 3);
		feld.getChildren().add(tempGrid);

		return feld;

	}

	private void kartenZeichen(FlowPane feld) {

//			a new card generate
		int count = 0;
		for (int i = 0; i <= 41; i++) {

			karten[i] = new MemoryKarte(bilder[count], count, this);

			if ((i + 1) % 2 == 0)
				count++;

		}

//		mix the cards in array
		Collections.shuffle(Arrays.asList(karten));

//			put the cards at the playing field
		for (int i = 0; i <= 41; i++) {
			feld.getChildren().add(karten[i]);

//				put the position of card
			karten[i].setBildPos(i);

		}
		this.myfeld = feld;
	}

	public void karteOeffnen(MemoryKarte karte) {

		int kartenID, kartenPos;
		paar[umgedrehtKarten] = karte;
		kartenID = karte.getBildID();
		kartenPos = karte.getBildPos();

		if (gemerkteKarten[0][kartenID] == -1)
			gemerkteKarten[0][kartenID] = kartenPos;
		else if (gemerkteKarten[0][kartenID] != kartenPos)
			gemerkteKarten[1][kartenID] = kartenPos;
		umgedrehtKarten++;

		if (umgedrehtKarten == 2) {
			paarPruefen(kartenID);
//			karteSchliessen();			
			timer = new Timeline(new KeyFrame(Duration.millis(500), new TimerHandler()));
			timer.play();
		}
		if (computerPunkte + menschPunkte == 21) {
//			Platform.exit();
			endeAlertZeichen();

		}

	}

	private void endeAlertZeichen() {
		Alert endeAlert = new Alert(AlertType.INFORMATION, "", new ButtonType("beenden", ButtonData.NO),
				new ButtonType("neu Spiel", ButtonData.YES));

		if (computerPunkte > menschPunkte)
			endeAlert.setHeaderText("Computer hat Gewonnen !\n" + " Er hat " + computerPunkte + "punkten");
		else
			endeAlert.setHeaderText("Sie haben Gewonnen !\n" + "Sie haben " + menschPunkte + "punkten");

		endeAlert.setTitle("Das Spiel is beendet");

		endeAlert.showAndWait();

		if (endeAlert.getResult().getButtonData() == ButtonData.NO)
			Platform.exit();
		if (endeAlert.getResult().getButtonData() == ButtonData.YES)
			neuSpiel();

	}

	private void neuSpiel() {

		menschPunkte = 0;
		computerPunkte = 0;
		umgedrehtKarten = 0;

//		human begins
		spieler = 0;

//		no saves carts
		for (int ausen = 0; ausen < 2; ausen++) {
			for (int innen = 0; innen < 21; innen++) {
				gemerkteKarten[ausen][innen] = -1;
			}
		}
		myfeld.getChildren().clear();
		initGUI(myfeld);

	}

	private void paarPruefen(int kartenID) {

		if (paar[0].getBildID() == paar[1].getBildID()) {
			paarGefunden();
			gemerkteKarten[0][kartenID] = -2;
			gemerkteKarten[1][kartenID] = -2;
		}

	}

//	the mothod add the punkte when a pair is finded
	private void paarGefunden() {
		if (spieler == 0) {
			menschPunkte++;
			menschPunktLabel.setText(Integer.toString(menschPunkte));
		} else {
			computerPunkte++;
			computerPunktLabel.setText(Integer.toString(computerPunkte));
		}
	}

//	wer zieht
	private void werZieht() {
		if (spieler == 0)
			zugLabel.setText("Mensch");
		else
			zugLabel.setText("Computer");
	}

//	the mothod flips die carte back and removes them from the play
	private void karteSchliessen() {

		boolean raus = false;
		if (paar[0].getBildID() == paar[1].getBildID()) {
			raus = true;
		}
		paar[0].rueckseiteZeigen(raus);
		paar[1].rueckseiteZeigen(raus);

		umgedrehtKarten = 0;
		if (raus == false) {

			spielerWechseln();

		} else if (spieler == 1)

			computerZug();
	}

	private void spielerWechseln() {

		if (spieler == 0) {
			spieler = 1;
			computerZug();
		} else
			spieler = 0;
		werZieht();
	}

	private void computerZug() {

		int spielStarke = 1;

		int kartenZahlen = 0;
		int zufall = 0;
		boolean treffer = false;

		if ((int) (Math.random() * spielStarke) == 0) {
			// search a pair. in same position in different dementions must be two cards
			while ((kartenZahlen < 21) && (treffer == false)) {
				// the value must not be -1 or -2
				if ((gemerkteKarten[0][kartenZahlen]) >= 0 && (gemerkteKarten[1][kartenZahlen]) >= 0) {
					treffer = true;
//					karten[gemerkteKarten[0][kartenZahlen]].fire();
//					karten[gemerkteKarten[1][kartenZahlen]].fire();
					karten[gemerkteKarten[0][kartenZahlen]].vorderseiteZeigen();
					karteOeffnen(karten[gemerkteKarten[0][kartenZahlen]]);
					karten[gemerkteKarten[1][kartenZahlen]].vorderseiteZeigen();
					karteOeffnen(karten[gemerkteKarten[1][kartenZahlen]]);

				}
				kartenZahlen++;
			}
		}
		if (treffer == false) {
			do {
				zufall = (int) (Math.random() * karten.length);
			} while (karten[zufall].isNocnInSpiel() == false);
//			karten[zufall].fire();
			karten[zufall].vorderseiteZeigen();
			karteOeffnen(karten[zufall]);

			do {
				zufall = (int) (Math.random() * karten.length);
			} while ((karten[zufall].isNocnInSpiel() == false) || (karten[zufall].isUmgedreht() == true));
//			karten[zufall].fire();
			karten[zufall].vorderseiteZeigen();
			karteOeffnen(karten[zufall]);

		}

	}

	public boolean zugErlaubt() {
		boolean erlaubt = true;
//		turn of computer? 
		if (spieler == 1)
			erlaubt = false;
//		two cards are turned jet?
		if (umgedrehtKarten == 2)
			erlaubt = false;

		return erlaubt;
	}

	private void schummeln() {

		for (int i = 0; i < karten.length; i++) {
			if (karten[i].isNocnInSpiel()) {
				karten[i].setGraphic(karten[i].getBildVorne());
			}
		}
		schimmelnTimer = new Timeline(new KeyFrame(Duration.millis(2000), new SchummelnTimer()));
		schimmelnTimer.play();

	}

}

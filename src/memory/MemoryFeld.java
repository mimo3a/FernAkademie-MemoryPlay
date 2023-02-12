package memory;

import java.awt.PageAttributes;

import javax.management.loading.PrivateClassLoader;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

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
	private Label menschPunktLabel, computerPunktLabel;
//	amount of flipped cards
	private int umgedrehtKarten;
//	for the actualy flipped pairs
	private MemoryKarte[] paar;
//	actualy player
	private int spieler;
//	computer saves here where lies the opposit one
	private int[][] gemerkteKarten;

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
		menschPunktLabel = new Label();
		computerPunktLabel = new Label();
		menschPunktLabel.setText(Integer.toString(menschPunkte));
		computerPunktLabel.setText(Integer.toString(computerPunkte));

		GridPane tempGrid = new GridPane();
		tempGrid.add(new Label("Mensch :"), 0, 0);
		tempGrid.add(menschPunktLabel, 1, 0);
		tempGrid.add(new Label("Computer :"), 0, 1);
		tempGrid.add(computerPunktLabel, 1, 1);
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

//			put the cards at the playing field

		for (int i = 0; i <= 41; i++) {
			feld.getChildren().add(karten[i]);

//				put the position of card

			karten[i].setBildPos(i);

		}
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
			karteSchliessen();
		}
		if (computerPunkte + menschPunkte == 21) {
			Platform.exit();
		}

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

//	the mothod flips die carte back and removes them from the play
	private void karteSchliessen() {
		boolean raus = false;
		if (paar[0].getBildID() == paar[1].getBildID()) {
			raus = true;
		}
		paar[0].rueckseiteZeigen(raus);
		paar[1].rueckseiteZeigen(raus);

		umgedrehtKarten = 0;
		if (raus = false)
			spielerWechseln();
		else if (spieler == 1)
			computerZug();
	}

	private void spielerWechseln() {
		if (spieler == 0) {
			spieler = 1;
			computerZug();
		} else
			spieler = 0;
	}
	
	private void computerZug() {
//		something is missing hier
	}

}

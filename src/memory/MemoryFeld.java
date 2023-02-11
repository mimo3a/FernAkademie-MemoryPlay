package memory;

import javax.management.loading.PrivateClassLoader;

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

			karten[i] = new MemoryKarte(bilder[count], count);

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

}

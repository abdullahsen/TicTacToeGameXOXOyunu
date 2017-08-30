import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class XOX extends Application {

	private char whoseTurn = 'X';
	private Cell[][] cell = new Cell[3][3];
	private Label lblStatus = new Label("Oynama sırası X'de");

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		GridPane pane = new GridPane();

		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				pane.add(cell[i][k] = new Cell(), k, i);
			}
		}

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(lblStatus);

		Scene scene = new Scene(borderPane, 450, 450);
		arg0.setTitle("XOX Oyunu");
		arg0.setScene(scene);
		arg0.show();

	}

	public boolean isFull() {
		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 3; k++)
				if (cell[i][k].getToken() == ' ')
					return false;
		return true;
	}

	public boolean isWon(char token) {
		for (int i = 0; i < 3; i++)
			if (cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token) {
				return true;
			}
		for (int k = 0; k < 3; k++)
			if (cell[0][k].getToken() == token && cell[1][k].getToken() == token && cell[2][k].getToken() == token) {
				return true;
			}
		if (cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token) {
			return true;
		}
		if (cell[0][2].getToken() == token && cell[1][1].getToken() == token && cell[2][0].getToken() == token) {
			return true;
		}
		return false;
	}

	public class Cell extends Pane {
		private char token = ' ';

		public Cell() {
			setStyle("-fx-border-color: black");
			this.setPrefSize(2000, 2000);
			this.setOnMouseClicked(e -> handleMouseClick());
		}

		public char getToken() {
			return token;

		}

		public void setToken(char c) {
			token = c;
			if (token == 'X') {
				Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
				line1.endXProperty().bind(this.widthProperty().subtract(10));
				line1.endYProperty().bind(this.heightProperty().subtract(10));
				Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
				line2.endXProperty().bind(this.widthProperty().subtract(10));
				line2.startYProperty().bind(this.heightProperty().subtract(10));
				line1.setStroke(Color.BLUE);
				line2.setStroke(Color.BLUE);
				line1.setStrokeWidth(8);
				line2.setStrokeWidth(8);
				this.getChildren().addAll(line1, line2);
			} else if (token == 'O') {
				Ellipse elips = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
						this.getHeight() / 2 - 10);
				elips.centerXProperty().bind(this.widthProperty().divide(2));
				elips.centerYProperty().bind(this.heightProperty().divide(2));
				elips.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
				elips.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
				elips.setStroke(Color.RED);
				elips.setFill(Color.RED);
				getChildren().add(elips);
			}
		}

		private void handleMouseClick() {
			if (token == ' ' && whoseTurn != ' ') {
				setToken(whoseTurn);

				if (isWon(whoseTurn)) {
					lblStatus.setText(whoseTurn + "  Kazandı! Oyun Bitti!");
					whoseTurn = ' ';
					
				}
				
				else if( isFull()){
					lblStatus.setText("  Tahta Doldu! Oyun Bitti!");
					whoseTurn = ' ';
				}
				else {
					whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
					lblStatus.setText( "Oynama sırası ->" +whoseTurn );
				}
			}
		}
	}
}

package reversi;

public class ReversiMenu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GameModel model = new GameModel();
		ConsoleView view = new ConsoleView(model);
		Player p1 =new Player();
		Player p2 =new Player();
		Controller controller = new Controller(model, view, p1, p2);
		controller.beginGame();
		
		
	}

}

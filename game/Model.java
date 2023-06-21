package game;

import interfaces.IModel;
import players.HumanPlayer;
import util.GameSettings;
import util.InputUtil;

/**
 * This class represents the state of the game.
 * It has been partially implemented, but needs to be completed by you.
 *
 * @author <S2202209>
 */
public class Model implements IModel
{
	// A reference to the game settings from which you can retrieve the number
	// of rows and columns the board has and how long the win streak is.
	private GameSettings settings;
	private int[][] board;
	private boolean exit_move;
	public int turn;

	// The default constructor.
	public Model()
	{
		// You probably won't need this.
	}

	// A constructor that takes another instance of the same type as its parameter.
	// This is called a copy constructor.
	public Model(IModel model)
	{
		// You may (or may not) find this useful for advanced tasks.
	}

	// Called when a new game is started on an empty board.
	public void initNewGame(GameSettings settings) {
		this.settings = settings;
		this.board = new int[settings.nrRows][settings.nrCols];
		this.turn = 1;
		this.exit_move = false;

		for (int i=0; i<settings.nrRows; i++){
			for(int j=0; j<settings.nrCols; j++){
				board[i][j] = 0;
			}
		}

		// This method still needs to be extended.
	}

	// Called when a game state should be loaded from the given file.
	public void initSavedGame(String fileName)
	{
		// This is an advanced feature. If not attempting it, you can ignore this method.
	}

	// Returns whether or not the passed in move is valid at this time.
	public boolean isMoveValid(int move)
	{
		boolean valid = false;

		if (move == -1){
			valid = true;
		} else if ((move < settings.nrCols && move > -1)&& (board[0][move]==0)){
			valid = true;
		}
		return valid;
	}

	// Actions the given move if it is valid. Otherwise, does nothing.
	public void makeMove(int move)
	{
		if (isMoveValid(move)== true){
			if (move == -1){
				exit_move = true;
			} else {
				for (int i = settings.nrRows-1; i>-1; i--){
					if (board[i][move] == 0) {
						board[i][move] = getActivePlayer();
						turnFunc();
						break;
					}
				}
			}
		}
	}

	// Returns one of the following codes to indicate the game's current status.
	// IModel.java in the "interfaces" package defines constants you can use for this.
	// 0 = Game in progress
	// 1 = Player 1 has won
	// 2 = Player 2 has won
	// 3 = Tie (board is full and there is no winner)
	public byte getGameStatus() {

		int i = 0;
		for (int j = 0; j < settings.nrCols; j++) {
			if (board[0][j] != 0) {
				i+=1;
			}
		}

		if (i == settings.nrCols) {
			return IModel.GAME_STATUS_TIE;
		} else if ((exit_move || vertical(2) || horizontal(2) || leftdiagonal(2) || rightdiagonal(2)) && turn == 1) {
			return IModel.GAME_STATUS_WIN_2;
		} else if ((exit_move || vertical(1) || horizontal(1) || leftdiagonal(1) || rightdiagonal(1)) && turn == 2) {
			return IModel.GAME_STATUS_WIN_1;
		} else {
			return IModel.GAME_STATUS_ONGOING;
		}

	}
	// Returns the number of the player whose turn it is.
	public byte getActivePlayer()
	{
		return (byte) turn;
	}

	// Returns the owner of the piece in the given row and column on the board.
	// Return 1 or 2 for players 1 and 2 respectively or 0 for empty cells.
	public byte getPieceIn(int row, int column)
	{
		// Assuming all cells are empty for now.
		return (byte) this.board[row][column];
	}

	// Returns a reference to the game settings, from which you can retrieve the
	// number of rows and columns the board has and how long the win streak is.
	public GameSettings getGameSettings()
	{
		return settings;
	}

	// =========================================================================
	// ================================ HELPERS ================================
	// =========================================================================

	// You may find it useful to define some helper methods here.

	public void turnFunc (){
		if (turn == 1){
			turn = 2;
		} else {
			turn = 1;
		}
	}

	/*private byte checkwin(){
		int player = getActivePlayer();
		vertical(player);
	}*/

	public boolean vertical(int player) {
		int num = 0;
		boolean p_win = false;

		for (int i=0; i < settings.nrCols;i++){
			for (int j =0; j < settings.nrRows;j++){
				if (board[j][i] == player){
					num+=1;
				} else {
					num = 0;
				}
				if (num == settings.minStreakLength){
					p_win = true;
					break;
					}
				}

			} return p_win;
		}

	public boolean horizontal(int player) {
		int num = 0;
		boolean p_win = false;

		for (int i=0; i < settings.nrRows;i++){
			for (int j =0; j < settings.nrCols;j++){
				if (board[i][j] == player){
					num+=1;
				} else {
					num = 0;
				}
				if (num == settings.minStreakLength){
					p_win = true;
					break;
				}
			}

		} return p_win;
	}

	public boolean rightdiagonal(int player){
		int num = 0;
		boolean p_win = false;



		for (int row = 0; row < this.board.length- (getGameSettings().minStreakLength-1); row++){

			for (int col = 0; col < this.board[row].length- (getGameSettings().minStreakLength-1); col++){

					for (int i = 0; i < settings.minStreakLength; i++){
						if (board[row+i][col+i] == player) {
							num += 1;
						} else {
							num = 0;
						} if (num == settings.minStreakLength){
							p_win = true;
							//break;
						}
					}
				}
			}

		 return p_win;
	}

	public boolean leftdiagonal(int player){
		int num = 0;
		boolean p_win = false;



		for (int row = 0; row < this.board.length- (settings.minStreakLength-1); row++){

			for (int col = settings.minStreakLength-1; col < this.board[row].length; col++){

				for (int i = 0; i < settings.minStreakLength; i++){
					if (board[row+i][col-i] == player) {
						num += 1;
					} else {
						num = 0;
					} if (num >= settings.minStreakLength){
						p_win = true;
						//break;
					}
				}
			}
		}

		return p_win;
	}
}

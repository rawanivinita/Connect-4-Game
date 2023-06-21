package players;

import interfaces.IModel;
import interfaces.IPlayer;
import util.GameSettings;
import util.InputUtil;


/**
 * Implementing this player is an intermediate task.
 * See assignment instructions for what to do.
 * If not attempting it, just upload the file as it is.
 *
 * @author <S2202209>
 */
public class RoundRobinPlayer implements IPlayer
{
	// A reference to the model, which you can use to get information about
	// the state of the game. Do not use this model to make any moves!
	private IModel model;
	private GameSettings settings;
	int column_index = 0;
	// The constructor is called when the player is selected from the game menu.
	public RoundRobinPlayer()
	{
		// You can leave this empty.
	}
	
	// This method is called when a new game is started or loaded.
	// You can use it to perform any setup that may be required before
	// the player is asked to make a move. The second argument tells
	// you if you are playing as player 1 or player 2.
	public void prepareForGameStart(IModel model, byte playerId)
	{
			this.model = model;
			this.settings = model.getGameSettings();
	}

	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int chooseMove()
	{
		int move = 0;
		for (int j = this.column_index; j < settings.nrCols; j++) {
			if (model.isMoveValid(j)) {
				this.column_index = j+1;
				move = j;
				break;
			}
		}
		if (this.column_index == settings.nrCols) {
			this.column_index = 0;
		}

		return move;
	}
}

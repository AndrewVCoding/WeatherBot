/**
 * Handles the logic behind fulfilling commands received by the chatbot
 * 
 * @author Duntorah
 *
 */
public class BotCommands
{
	WeatherData	DATA;
	String		NAME;

	public BotCommands(String name)
	{
		NAME = name;
	}

	/**
	 * Determines what information to display based on the command given
	 * 
	 * @param message
	 * @return
	 */
	public String parseCommand(String message)
	{
		String[] commands = message.split(" ");

		if (commands[1].equalsIgnoreCase("help"))
			return "help";
		else
		{
			if (commands[1].matches("[IM]") && commands[2].matches("[0-9]{5}"))
				return "weather";
			if (commands[1].equals("temperature"))
				return "temperature";
			if (commands[1].matches("ISS"))
				return "ISS";
			else
				return "unknown";
		}
	}

	/**
	 * Returns true if the given String is the name of the bot
	 * 
	 * @param message
	 * @return
	 */
	public boolean isCommand(String message)
	{
		String[] msg = message.split(" ");

		if (msg[0].equalsIgnoreCase(NAME))
			return true;
		else
			return false;
	}
}

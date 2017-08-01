import java.io.IOException;

import org.jibble.pircbot.PircBot;

/**
 * The bot class itself
 * 
 * @author andrew
 *
 */
public class WeatherBot extends PircBot
{
	private String		NAME;
	private BotCommands	COMMANDS;
	private APIclass	DATA;
	private String		CHANNEL;

	public WeatherBot(String name)
	{
		// Set the name of the chatbot to Weatherbot
		NAME = name;
		this.setName(NAME);

		// Instantiate the commands object
		COMMANDS = new BotCommands(NAME);
		DATA = new APIclass();
	}

	/**
	 * Receives messages from the IRC channel and if they start with WEATHERBOT, then it parses it
	 * as a command and responds accordingly by sending a message
	 */
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		// Split the message up into individual words
		String[] content = message.split(" ");
		String command = "";

		// If message is a command directed at weatherbot, parse the information in the command to
		// determine the query
		if (COMMANDS.isCommand(message))
		{
			command = COMMANDS.parseCommand(message);

			System.out.println("COMMAND: " + command);

			// UNKNOWN COMMAND
			if (command.equalsIgnoreCase("unknown"))
			{
				sendMessage(CHANNEL, sender + ": Sorry, but I didn't recognise that command.");
			}
			else
			{
				// Retrieve data from the api
				try
				{
					// HELP
					if (command.equalsIgnoreCase("help"))
						help(channel);

					// WEATHER
					if (command.equalsIgnoreCase("weather"))
						giveWeather(channel, content, sender);

					// TEMPERATURE
					if (command.equalsIgnoreCase("temperature"))
						giveTemperature(content, channel, sender);
					// ISS
					if (command.equalsIgnoreCase("iss"))
						giveISS(channel, sender);
				}
				catch (IOException e)
				{
					sendMessage(CHANNEL, sender + ": Sorry, but the weather is currently unavailable.");
				}
			}
		}
	}

	/**
	 * Display the commands the bot can respond to
	 * 
	 * @param channel
	 */
	public void help(String channel)
	{
		sendMessage(channel, "Commands: <I/M> specifies Imperial or Metric units");
		sendMessage(channel, "          " + NAME.toUpperCase() + " <I/M> <zipcode>             : Gives a general description of the weather");
		sendMessage(channel, "          " + NAME.toUpperCase() + " temperature <I/M> <zipcode> : Gives the temerature");
		sendMessage(channel,
				"          " + NAME.toUpperCase() + " ISS                         : Gives the current position of the International Space Station");
	}

	/**
	 * Displays the weather
	 * 
	 * @param channel
	 * @param content
	 * @param sender
	 * @throws IOException
	 */
	public void giveWeather(String channel, String[] content, String sender) throws IOException
	{
		DATA.fetchWeather(content[2]);
		String description = DATA.getWeather(content[1]);
		sendMessage(channel, sender + ": " + description);
	}

	/**
	 * Displays the temperature
	 * 
	 * @param content
	 * @param channel
	 * @param sender
	 * @throws IOException
	 */
	public void giveTemperature(String[] content, String channel, String sender) throws IOException
	{
		DATA.fetchWeather(content[3]);
		sendMessage(channel, sender + ": The current temperature is " + DATA.getTemp(content[2]));
	}

	public void giveISS(String channel, String sender) throws IOException
	{
		DATA.fetchISS();
		sendMessage(channel, sender + ": The current position of the ISS is " + DATA.getPos());
	}

	/**
	 * Message to send when joining the channel to let users know about the bot
	 * 
	 * @param channel
	 */
	public void welcome(String channel)
	{
		CHANNEL = channel;
		sendMessage(channel, "Hi, I'm " + NAME + "! To see how to give me commands, type \"" + NAME + " help\"");
	}

}

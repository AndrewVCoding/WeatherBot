import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

/**
 * The driver class for the WeatherBot
 * 
 * @author andrew
 *
 */
public class BotDriver
{
	public static void main(String[] args)
	{
		// Instantiate the bot
		WeatherBot weatherBot = new WeatherBot("WeatherBot");

		// set debugging output
		weatherBot.setVerbose(true);

		// Set the bot to automatically choose a new nickname if the default is taken
		weatherBot.setAutoNickChange(true);

		// Connect to the IRC server.
		try
		{
			weatherBot.connect("irc.freenode.net");
		}
		// If the nickname is already in use
		catch (NickAlreadyInUseException e)
		{
			System.out.println("Nickname already in use");
		}
		catch (IOException e)
		{
			System.out.println("Could not connect");
		}
		catch (IrcException e)
		{
			System.out.println("Could not connect");
		}

		// Join the #pircbot channel
		String channel = "#pircbot";
		weatherBot.joinChannel(channel);
		weatherBot.welcome(channel);
	}
}

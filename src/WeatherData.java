/**
 * The top level class to be populated with weather data from openweather api as a json
 * 
 * @author Duntorah
 *
 */
public class WeatherData
{
	public Coord		coord;
	public Weather[]	weather;
	public String		base;
	public Main			main;
	public double		visibility;
	public Wind			wind;
	public Clouds		clouds;
	public int			dt;
	public Sys			sys;
	public int			id;
	public String		name;
	public int			cod;

	/**
	 * Returns the temperature given either 'C' or 'F'
	 * "XX.XX degrees <farenheit/celsius>
	 * 
	 * @param units
	 * @return
	 */
	public String getTemp(String units)
	{
		String temperature = "";
		String degrees = "";
		double temp = main.temp;

		// if farenheit
		if (units.equalsIgnoreCase("I"))
		{
			degrees = "degrees farenheit";
			temp = ((main.temp * 9) / 5) - 459.67;
		}
		// if celsius
		if (units.equalsIgnoreCase("M"))
		{
			degrees = "degrees celsius";
			temp = main.temp - 273.15;
		}

		System.out.println(temp);
		return String.format("%.2f " + degrees, temp);
	}

	/**
	 * Returns a broad description of the current weather
	 * 
	 * Current weather for <name> on <date>: <description> and <temp>, with a high of <temp_high>,a
	 * low of <temp_low> and humidity of <humidity>%
	 * 
	 * @param units
	 * @return
	 */
	public String getWeather(String units)
	{
		String degrees = "";
		String temp = "";
		double windSpeed = wind.speed;
		String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(dt * 1000));

		// Convert to Imperial units
		if (units.equalsIgnoreCase("I"))
		{
			// Convert all temperature values
			degrees = "degrees farenheit";
			temp = String.format("%.2f", (((main.temp * 9) / 5) - 459.67));

			// convert Windspeed from m/s to mph
			windSpeed = windSpeed * 2.23694;
		}

		// Convert to Metric
		if (units.equalsIgnoreCase("M"))
		{
			// Convert all temperature values
			degrees = "degrees celsius";
			temp = String.format("%.2f", main.temp - 273.15);

			// Wind speed is metric by default
		}

		// Put all the information together
		return "Current weather for " + name + " on " + date + ": " + weather[0].description + " and " + temp + " " + degrees
				+ ", with a humidity of " + main.humidity + "%";
	}
}

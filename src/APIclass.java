
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIclass
{
	private String		APP_ID;
	private String		ZIP_CODE;
	private WeatherData	WEATHER_DATA;
	private ISS			ISS_DATA;

	public APIclass()
	{
		APP_ID = "9146a1be7f62f3737023afe868fbbdf0";
	}

	/**
	 * fetches data from openWeather using the Gson library and the provided zipcode
	 * 
	 * @param zipcode
	 * @throws IOException
	 */
	public void fetchWeather(String zipcode) throws IOException
	{
		ZIP_CODE = zipcode;

		// get the url for the weather data object
		String address = "http://api.openweathermap.org/data/2.5/weather?zip=" + ZIP_CODE + ",us&appid=" + APP_ID;
		System.out.println(address);
		URL url = new URL(address);

		// create an input stream from the url
		InputStreamReader reader = new InputStreamReader(url.openStream());

		// create a gson builder for the weather data
		Gson gson = new GsonBuilder().create();

		WEATHER_DATA = gson.fromJson(reader, WeatherData.class);
	}

	/**
	 * Retrieves the position of the ISS
	 * 
	 * @throws IOException
	 */
	public void fetchISS() throws IOException
	{

		// Get the urk for the ISS position
		URL url = new URL("http://api.open-notify.org/iss-now.json");

		// create an input stream from the url
		InputStreamReader reader = new InputStreamReader(url.openStream());

		// create a gson builder for the weather data
		Gson gson = new GsonBuilder().create();

		ISS_DATA = gson.fromJson(reader, ISS.class);
	}

	/**
	 * Retrieve unit correct temperature
	 * 
	 * @param units
	 * @return
	 * @throws IOException
	 */
	public String getTemp(String units) throws IOException
	{
		return WEATHER_DATA.getTemp(units);
	}

	/**
	 * Retrieve unit correct description of the weather
	 */
	public String getWeather(String units) throws IOException
	{
		return WEATHER_DATA.getWeather(units);
	}

	public String getPos()
	{
		return "LATITUDE: " + ISS_DATA.iss_position.latitude + "    LONGITUDE: " + ISS_DATA.iss_position.longitude;
	}
}

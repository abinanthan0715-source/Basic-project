async function getWeather() {
    const city = document.getElementById("cityInput").value;

    if (city === "") {
        alert("Please enter a city name");
        return;
    }

    const apiKey = "e2b18903d9ee7ef357119743e89e663d";
    const apiUrl = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric`;

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error("City not found");
        }

        const data = await response.json();

        document.getElementById("cityName").textContent = data.name;
        document.getElementById("temperature").textContent = `Temperature: ${data.main.temp} °C`;
        document.getElementById("description").textContent = `Condition: ${data.weather[0].description}`;
        document.getElementById("humidity").textContent = `Humidity: ${data.main.humidity} %`;
        document.getElementById("windSpeed").textContent = `Wind Speed: ${data.wind.speed} m/s`;
        document.getElementById("tempmax").textContent = `temp_max: ${data.main.temp_max} C`;

        const iconCode = data.weather[0].icon;
        const iconUrl = `https://openweathermap.org/img/wn/${iconCode}@2x.png`;
        document.getElementById("weatherIcon").src = iconUrl;

    } catch (error) {
        alert(error.message);
    }
}
setInterval(getWeather, 30000);
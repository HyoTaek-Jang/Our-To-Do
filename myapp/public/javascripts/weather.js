const COORDS = "coords";
const API_KEY = "0acc42e3154c1df24d2fedeaeddae0e2";
const todayTemp = document.querySelector(".today-temp");
const todayIcon = document.querySelector(".today-icon");
const tmrTemp = document.querySelector(".tmr-temp");
const tmrIcon = document.querySelector(".tmr-icon");

// 날씨 정보 https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
// https://openweathermap.org/api/one-call-api#history
function getWeather(lat, lon) {
  fetch(`https://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${lon}&
    exclude=&appid=${API_KEY}&units=metric`)
    .then(function (response) {
      return response.json();
    })
    .then(function (json) {
      console.log(json);
      const weatherData = {
        currentTemp: json.current.temp,
        currentWeather: json.current.weather[0].icon,
        tmrTempMax: json.daily[0].temp.max,
        tmrTempMin: json.daily[0].temp.min,
        tmrWeather: json.daily[0].weather[0].icon,
      };

      todayTemp.innerHTML = `${weatherData.currentTemp}`;
      tmrTemp.innerHTML = `${Math.round(weatherData.tmrTempMax)} / ${Math.round(
        weatherData.tmrTempMin
      )}`;

      console.log(weatherData);

      askForWeatherIcon(todayIcon, weatherData.currentWeather);
      askForWeatherIcon(tmrIcon, weatherData.tmrWeather);
    });
}

function askForWeatherIcon(targetIcon, weatherId) {
  targetIcon.classList.add(`W${weatherId}`);
}

// current.temp
// current.weather.id
// 2xx thunderstorm 3xx drizzle 5xx rain 6xx snow 7 atmosphere 800 clear 80x cluods
// daily.0.temp.max , min
// daily.0.weather.0.id
function saveCurrentCoordsc(Obj) {
  localStorage.setItem(COORDS, JSON.stringify(Obj));
}

function handleGeoSucces(position) {
  console.log("succes for get Geo");
  console.log(position);

  const latitude = position.coords.latitude;
  const longitude = position.coords.longitude;

  const coordsObj = {
    latitude,
    longitude,
  };

  saveCurrentCoordsc(coordsObj);
  getWeather(latitude, longitude);
}

function handleGeoError() {
  console.log("Error for get Geo");
}

function askForCoords() {
  navigator.geolocation.getCurrentPosition(handleGeoSucces, handleGeoError);
}

function loadCoords() {
  const currentCoords = localStorage.getItem(COORDS);

  if (currentCoords == null) {
    askForCoords();
  } else {
    const parseCoords = JSON.parse(currentCoords);
    const lat = parseCoords.latitude;
    const lon = parseCoords.longitude;

    getWeather(lat, lon);
  }
}

function init() {
  loadCoords();
}

init();

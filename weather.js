const  COORDS = "coords";
const API_KEY = '0acc42e3154c1df24d2fedeaeddae0e2';
const todayTemp = document.querySelector(".today-temp");
const todayIcon = document.querySelector(".today-icon");
const tmrTemp = document.querySelector(".tmr-temp");
const tmrIcon = document.querySelector(".tmr-icon");


function getWeather(lat,lon){
    fetch(`https://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${lon}&
    exclude={part}&appid=${API_KEY}&units=metric`)
        .then(function(response){
            return response.json();
        })
        .then(function(json){
            const weatherData = {
                currentTemp : json.current.temp,
                currentWeather : json.current.weather[0].id,
                tmrTempMax : json.daily[0].temp.max,
                tmrTempMin : json.daily[0].temp.min,
                tmrWeather : json.daily[0].weather[0].id
                
            };

            todayTemp.innerHTML = `${weatherData.currentTemp}`;
            tmrTemp.innerHTML = `${Math.round(weatherData.tmrTempMax)} / ${Math.round(weatherData.tmrTempMin)}`;

            console.log(weatherData);

            askForWeatherIcon(todayIcon,weatherData.tmrWeather);
            askForWeatherIcon(tmrIcon,weatherData.tmrWeather);


        })
    
}

function askForWeatherIcon(targetIcon ,weatherId){
  
    if(200 <= weatherId && weatherId < 300){
        targetIcon.classList.add('thunder');
    }
    else if(300 <= weatherId && weatherId < 400){
        targetIcon.classList.add('drizzle');
    }
    else if(500 <= weatherId && weatherId < 600){
        targetIcon.classList.add('rain');
    }
    else if(600 <= weatherId && weatherId < 700){
        targetIcon.classList.add('snow');
    }
    else if(700 <= weatherId && weatherId < 800){
        targetIcon.classList.add('atmosphere');
    }
    else if(800 == weatherId){
        targetIcon.classList.add('clear');
    }
    else{
        targetIcon.classList.add('clouds');
    }
  
  
}

// current.temp
// current.weather.id
// 2xx thunderstorm 3xx drizzle 5xx rain 6xx snow 7 atmosphere 800 clear 80x cluods
// daily.0.temp.max , min
// daily.0.weather.0.id
function saveCurrentCoordsc(Obj){
    localStorage.setItem(COORDS, JSON.stringify(Obj));
}

function handleGeoSucces(position){
    console.log("succes for get Geo");
    console.log(position);

    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    const coordsObj = {
        latitude,
        longitude
    };

    saveCurrentCoordsc(coordsObj);
    getWeather(latitude,longitude);

}

function handleGeoError(){
    console.log("Error for get Geo");
}

function askForCoords(){
    navigator.geolocation.getCurrentPosition(handleGeoSucces, handleGeoError);
   
}

function loadCoords(){
    const currentCoords = localStorage.getItem(COORDS);

    if(currentCoords == null){
        askForCoords();
    }
    else{
        const parseCoords = JSON.parse(currentCoords);
        const lat =parseCoords.latitude;
        const lon = parseCoords.longitude;

        getWeather(lat,lon);
    }
}

function init(){
    loadCoords();
}

init();
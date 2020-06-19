const  COORDS = "coords";

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
        // api 작동
        console.log("success save coords");
    }
}

function init(){
    loadCoords();
}

init();
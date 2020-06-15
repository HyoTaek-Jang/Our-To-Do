const todoFormBox = document.querySelector(".toDo-input-value");
const todoInput = todoFormBox.querySelector("input");
const parentBox = document.querySelector(".l_contentBox");
const todoForm = document.querySelector(".todoForm");

const CONTENTS = input.value;

const toggle = document.querySelectorAll(".toggle");

const toggleTD = document.querySelector(".toDo-CB");
const toggleBM = document.querySelector(".bookmark-CB");


function loadtoggle(){
    toggle.forEach(function(e){
        e.addEventListener('click', handleToggle);
    });
}

function handleToggle(event){

    console.log(event.target.parentNode.className);

    if(event.target.parentNode.className == 'toDo-header'){
        if(event.target.innerHTML == 'SHOW'){
            event.target.innerHTML = 'HIDE';
            toggleTD.classList.remove(SHOWING_CN);
    
        }
        else{
            event.target.innerHTML = 'SHOW';
            toggleTD.classList.add(SHOWING_CN);
        
        }

    }
    else{
        if(event.target.innerHTML == 'SHOW'){
            event.target.innerHTML = 'HIDE';
            toggleBM.classList.remove(SHOWING_CN);
    
        }
        else{
            event.target.innerHTML = 'SHOW';
            toggleBM.classList.add(SHOWING_CN);
        
        }
    }

}


let toDos = [];


function handleDel(event){
    const target = event.target;
    const removeT = target.parentNode;
    parentBox.removeChild(removeT);

    const newToDos = toDos.filter(function(toDo){
        return parseInt(removeT.id) !== toDo.id;
    })

    toDos = newToDos
    localStorage.setItem("toDos", JSON.stringify(toDos));



}

function handleTodo(event){
    event.preventDefault();
    makeBox(todoInput.value);
    todoInput.value = "";
    localStorage.setItem("toDos", JSON.stringify(toDos));
}

function paintToDos(parasedTodos){
    parasedTodos.forEach(function(toDo){
        makeBox(toDo.text);
    })
}

function loadTodos(){
    const currentTodos = localStorage.getItem("toDos");
    if(currentTodos !== null){
        const parasedTodos = JSON.parse(currentTodos);
        paintToDos(parasedTodos);
    }
}

function loadText(){
    todoForm.addEventListener("submit", handleTodo);
}

function makeBox(text){
    const CONTENTBOX_TAG = document.createElement("div");
    const CONTENT_TAG = document.createElement("div");
    const P_TAG = document.createElement("P");
    const DELETE_TAG = document.createElement("div");

    parentBox.appendChild(CONTENTBOX_TAG);
    CONTENTBOX_TAG.appendChild(CONTENT_TAG);
    CONTENT_TAG.appendChild(P_TAG);
    CONTENTBOX_TAG.appendChild(DELETE_TAG); 

    const newId = toDos.length + 1;

    
    CONTENTBOX_TAG.classList.add('l_contentBox-contents','flex');
    CONTENTBOX_TAG.id= newId;
    CONTENT_TAG.classList.add('toDo-content');
    DELETE_TAG.classList.add('toDo-del');   
    
    DELETE_TAG.addEventListener('click', handleDel);


    P_TAG.innerHTML=text;


    const toDosObj = {
        text : text,
        id : newId
    }

    toDos.push(toDosObj);

}


function init(){
    loadtoggle();
    loadTodos();
    loadText();

}

init();

// del 기능 구현
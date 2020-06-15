## let, const, var
- const : constant 상수, 변하지 않음 + 재선언 불가
- 항상 에러를 읽는 것이 중요하다.
- let : 바껴도 됨 ㅇㅇ + 재선언 불가 재할당 가능
- var : 바껴도 됨 근데 그냥 let 쓰래 + var은 한번 더 선언했는데도 오류도 안나옴 ㅋㅎ
- 왜그럴까?
- 모든 필요한 변수는 대부분 const 꼭 필요하면 let
- 호이스팅 : 선언문 끌어올림 ㅇㅋ?
- 호이스팅 할떄 let은 선언문 이전에 참조하면 referenceError뜸
- var은 선언이랑 초기화 같이함 let은 분리 작업, 선언문까지 와야지 초기화가 됨
- 정리하면 기본적으로 const사용, 재할당 필요하면 let 

## Data Types on JS
- 주석처리는 //
- 여러줄은 /**/
- string : ""로 감싸버려~ 따움표 안에는 싹다 스트링, 안감싸고 asdf하면 저건 그냥 변수야, 심지어 이모지도 됨 덜덜더러덜더 "6666"은 숫자가 아닌 글자여~
- boolean : true = 1 or false = 0
- number : 23423423 okay?
- float : floating number ex) 55.11111

## organizing data with array
- 배열!!!!!!!!!! 느낌 알쥬?
- 명명법 - camel -> dyasOfWeek
- array : [] 안에 넣으면 돼
- ex) const daysOfWeek = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
- 다른 데이터 타입 넣어도 돼 변수도 ㅇㅇ
- 원하는 데이터 타입 꺼내고 싶음 ex) daysOfWeek[2] -> 3번째 꺼내는거

## Organizing data with Objects
- Object : {} 컬리브라켓
- != array
- ex) const TaekInfo = {
    name : "Taek",
    age : 33,
    gender : "male",
    isHandsome : true,
    favMovie : ["along the god", "LOTR"]
}
- 즉 오브젝트는 변수와 그 값을 함께 갖는 느낌
- console.log(TaekInfo.name); 이런 식의 호출
- TaekInfo 가 const 지만 그 안에 속성 값은 바꿀 수 있음. 와우!
- object를 array 안에 넣을 수 있음.

## js function
- function sayHello(인자값){
    console.log("hello!", 인자값)
}

sayHello(); //실행

- argument 인자 괄호안에 넣는거! 함수에 그 값을 넘겨주는거제

## more function
- ` : 백틱 // 새로운 스트링 방식?
- ex) consloe.log(`Hello $(name) you are $(age) years old`);

- const greetNiclolas = sayHello("Nicolas", 14)
- 여기서 greetNicloas는 sayHello의 함수를 호출하는 게 아니라 리턴 값을 받음

- consloe.log같은 거 만들려면
- ex) const cal = {
    plus : function(a,b){
        return a+b;
    }
}

곱하기 나누기 뺴기 더하기 제곱(a**b)

## DOM (document object model)
- js로 html 접근하기
- id로 접근 : document.getElementById
- ex) const title = document.getElementById("title");
- title.innerHTML = "" -> 값을 바꾸는듯 ㅇㅇ
- !!!!!!!!!! document.queraySelector 선택자 잡기 제일 훌륭한듯

## 조건문
- if(조건){
    blcok
} else{
    block
}
- === 같은지 확인 조건

## DOM if else Function practice
- event 궁금할면 html javascript dom event mdn 검색
- mdn에 모든게 있대 ㅇㅇㅇ
- 별게 다있네 window.addEventListener("offline" ~), onlice etc
- 선택자.classList -> 굿
- classList.add(~)
- classLsit.remove(~)
- classList.contains(~) -> 있나 없나 확인 true false 반환
- classList.toggle(~) -> ~ 있으면 지우고 없으면 넣어줌 
## 21년 2월 1일

# OURTODO 기획

- 기존에 웹 학습을 통해 만든 momentum 펄서널라이즈 버전이 있다.
- todo, bookmark, 날씨, 시간이 작동하는.
- 이번 웹 서버를 공부하고 서버와 연동시켜서 나 홀로 사용하는 것이 아닌 필요한 분이 계시다면 같이 쓰고싶다 라는 생각에서 시작된 리모델링 프로젝트

- 기능과 페이지를 다듬고 서버에 올릴 생각.

### 프론트엔드 요구사항

    - js 정리. 제이쿼리로 써도 오케이. ajax를 활용한 로그인, 회원가입 구현. - first
    - 반응형 구현. - 어느정도 ok
    - 날씨 정확도 개선.
    - 더 다양한 테마 생성
    - 디자인을 일단 기존 디자인 유지해서 가져가는거로. - 완료
    - localStroge에서 sql로 이동- first
    - 길이 넘어가면 hover시 보여주거나 그렇게 ㅇㅇ
    - 초기화랑 로그아웃버튼은 날씨 위에 넣는게 날듯 - 완료
    - 로그인 페이지 - ok
    - 인덱스 페이지 - ok

### 백엔드 요구사항

    - mysql 연동 - first
    - 도메인 구입 후, ec2랑 연결하여 호스팅.(가비아, ZeroSSL or cerbot ) - first
    - 세션, 쿠키, 보안, ssl쪽 학습 - first
    - 로그인 유지 기능 - first
    - https로 구현. - first
    - restful하고 mvc패턴을 적극활용. - first
    - 로그데이터 보관 - first

### 추가기능

    - 로그인하면 명연 뙁 띄우주는? 당일 처음 들어가면

- 일단 이정도 요구사항을 가지고 개발 진행.
- 이미 프론트가 나와있기에 우선 프론드 요구사항 해결하고 백으로 넘어가는 방향

### 21년 2월 2일

- 프론트 개발상황

  1. 기존 창화면에 따라 크기가 변함을 고정시킴. 네이버처럼
  2. 그에 맞춰 요소 css 조정.
  3. 초기화랑 로그아웃버튼은 날씨 위에 넣는게 날듯
  4. 초기화랑 로그아웃 버튼 위치 생성
  5. 클리어 기능 이동 완료
  6. 파비콘 제작 및 삽입
  7. 인덱스 페이지 제작, css 분리
  8. login페이지 제작, 체크박스로 자동로그인

- 스크롤 크기를 유동적으로 키우고 싶은데 별 방법을 써도 안되서 결국 미디어 쿼리로 듬성듬성 해놨음.. 역시 프론트 어려워

- 오늘의 느낀점 : 프론트 진짜 개어렵다.

### 21년 2월 3일

- mvc로 이동중!
- 앞으로 restful로 만들기
- main 화면 수정 필요 - 이전엔 메인에서 사용자 이름 받음. 지금은 로그인해서 그 데이터를 넣어주는 형식
- ERD 제작
- https://advenoh.tistory.com/31 너무 좋은 글이다 진짜

- 하... 또 떳다

```
0|www    |   code: 'ER_NOT_SUPPORTED_AUTH_MODE',
0|www    |   errno: 1251,
0|www    |   sqlMessage: 'Client does not support authentication protocol requested by server; consider upgrading MySQL client',
0|www    |   sqlState: '08004',
0|www    |   fatal: true
0|www    | }
```

- 썩을 보안오류...
- 다 안먹혀... 저번 방법도..
- 아 시ㅣㅣㅣㅣㅣㅣㅣ부레 진짜 이건 아니지
- 왜 환경변수가 안먹혀서 이렇게 된거였다고?
- 이건 아니지 진짜
- 아ㅏㅏㅏㅏㅏㅏㅏㅏ사람 죽이네 아오
- 왜 환경변수가 안먹었지
- ㅋ 항상 그렇듯 내가 잘못한거.
- app.js에서 dotenv를 안켜줌 와 근데 환경변수 안키 오류가 너무 내가 익숙한 오류였어.......

- 배운점 : 폼태그 안에 버튼이 눌리면 서밋마냥 폼 실행시킴
- ajax에서 에러 발생시키기. res를 보낼때 status를 500으로 보냄
- 트라이 캐치에서 reject는 캐치로 잡힌다!

## 21년 2월 6일

- register 에러 고침
- 내가 UserStorage에서 reject로 띄운다는 에러가 안띄워짐 그냥 아이디가 없어도 undefined로 나옴 그래서 if(err)을 if(data[0]==unde~)로 바꿔서 해결

## 21년 2월 7일

- 비밀번호를 해시와 솔트를 활용해서 보안을 높임
- 해시는 단방향 암호화고
- 레인보우 테이블로 찾을 수 가 있기에 솔트를 통해 다시 암호화를 한다. 이 작업을 반복하여 비밀번호를 암호화를 하고 사용자가 비밀번호를 입력하면 똑같은 과정을 거쳐서 해시 값으로 비교 후, 로그인 처리를 한다.

```js
  hashPsw(psw) {
    return new Promise((resolve, reject) => {
      const hashPsw = crypto.randomBytes(64, (err, buf) => {
        crypto.pbkdf2(
          this.body.password,
          buf.toString("base64"),
          12367,
          64,
          "sha512",
          (err, key) => {
            resolve({
              sort: buf.toString("base64"),
              hashPsw: key.toString("base64"),
            });
          }
        );
      });
    });
  }
```

- pbkdf2 메소드 => 단방향 암호화 메소드. 인자는 순서대로 비번, salt, 반복 횟수, 비번 길이, 해시 아록리즘 순서.
- 솔트를 랜덤값으로 지정, 그렇기에 디비에 솔트도 같이 넣어야함.

## 21년 2월 8일

- 세션을 활용하여 로그인, 로그아웃 기능 구현함

```javascript
const session = require("express-session");
const MySQLStore = require("express-mysql-session")(session);

const dbStore = new MySQLStore({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PSWORD,
  database: process.env.DB_DATABASE,
  port: 3306,
});

module.exports = dbStore;
```

- 저 port를 그냥 환경변수 포트로 둬서 3000번이 들어갔다... 그래서 계속 에러가 뜬거였어!!!!!!!!!!!!ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
- 역시나 항상 그렇듯 내가 잘못한거지..

- 앞으로 세션을 활용하여 더 구현할것.

  - 자동로그인
  - 자동로그인 안하면 브라우저 종료시 로그아웃
  - 혹은 2시간 지나면 로그아웃..

- 로그아웃 기능 구현 완료
- destroy를 활용

```javascript
this.body.session.destroy(() => {
  this.body.session;
});
```

- 앞으로 해야할거
  - 1. 어떻게 자동로그인을 구현할 것인지
  - 2. 자동로그인 안하면 언제 로그인 끊고 세션 관리 할 것인지

## 21년 2월 9일

- ec2 서버에 띄움
- mysql 문제가 계속 발생해서 dotenv를 제대로 못받나했는데
- 내가 dotenv를 깃에 안올리고 커밋해서 우분투에는 dotenv가 없었음!!
- 해결!

### 1차 테스트 배포

- 요구사항 : 투두 수정이 가능하게

## 21년 2월 11일

- passport 모듈 적용 시도.

1. app.js에 passport와 passportConfi = require("./passport") 선언

   - 위처럼 ./~하면 ./passport/index.js를 호출함.

2. 세션 선언 다음에 passport.initialize()와 session()선언

- initialize()는 요청(req)에 passport 설정을 심고, session은 req.session에 passport 정보를 저장함.

3. passport는 req객체에 isAuthenticated 메서드를 추가함. 로그인 중이면 저 메소드는 트루를 반환, 아니면 펄스를 반환.

- 이미 로그인 구현 다 했기에 passport는 패스 하고 자동로그인 구현을!
- 세션은 현재 브라우저가 꺼지면 로그아웃이 됨.
- 자동 로그인 구현을 위해 https://isme2n.github.io/devlog/2017/06/13/security-remember-me/ 참고
- db 재구성 완료.
- 쿠키를 추가하는 과정에서 어떻게 json을 넣지 고민했는데. 쿠키는 문자열만 들어갈 수 있다고 한다!
- secure쓰면 https에서만 쿠키가 돌아감.
- maxAge로 1달짜리 쿠키 만들고 재접하면 새롭게 쿠키 갱신하는거로 1달동안 접속안하면 빠잉!

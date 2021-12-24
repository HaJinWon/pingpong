# Git 사용법 & CI

---

---

### Git 설치 ( v2.9.5 )

1. **의존성 라이브러리 설치 (/root 디렉토리에서)**
   
    ```java
    yum -y install curl-devel
    yum -y install expat-devel
    yum -y install gettext-devel
    yum -y install openssl-devel
    yum -y install zlib-devel
    yum -y install perl-devel
    yum -y install asciidoc
    yum -y install xmlto
    ```
    
    → 한줄로 입력 가능
    
    yum -y install 파일1  파일2  파일3 ... 
    
   
   
1. **git 파일 다운로드**
   
    ```bash
    wget [https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz](https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz)
    tar xvfz git-2.9.5.tar.gz
    ```
    
    - wget으로 git을 받은 후 압축 해제
    
      
    
2. **디렉토리 이동**
   
    ```bash
    cd git-2.9.5
    ```
    
    - 압축 푼 디렉토리로 이동
    
      
    
3. **설치 경로 지정**
   
    ```bash
    ./configure --prefix=/usr/local/douzone/git
    ```
    
    - git의 설치경로를 /usr/local/douzone/git으로 지정
    
      
    
4. **빌드**
   
    ```bash
    make all doc html info
    ```
    
    - 컴파일을 위한 빌드 작업
    
      
    
5. **설치**
   
    ```bash
    make install
    ```
    
    - 컴파일 작업
    
    - /usr/local/douzone 밑에 git 디렉토리 확인
    
      
    
    ```bash
    /usr/local/douzone/git/bin/git --version
    ```
    
    - /usr/local/douzone/git/bin/ 디렉토리의 git 실행파일을 실행하여 버전 확인
    
    - 위의 경로가 아니라 그냥 git —version  입력시 리눅스 설치될 때 기본적으로 깔려있는 낮은 버전이 출력됨
    
    - 우리는 새로 설치한 git 버전을 써야함
    
      
    
6. **환경 변수 설정**
   
    ```bash
    vi /etc/profile
    ```
    
    - /etc/profile 맨 밑줄에 아래 소스 추가
      
        ```bash
        #Git
        export PATH=/usr/local/douzone/git/bin:$PATH
        ```
        
    
    ---
    
    ```bash
    source /etc/profile
    ```
    
    - profile 파일 업데이트
    
      
    
    ```bash
    echo $PATH
    ```
    
    - PATH가 정상적으로 추가되었는지 확인
    
      
    
    ```bash
    git --version
   ```
   
   - 버전 확인하면 새로 설치한 버전으로 설정됨
   
     
   
8.  Git 환경 설정

```bash
git config --global [user.name](http://user.name/) "자신의 유저네임"
git config --global [user.](http://user.name/)email "자신의 이메일주소"
```



9. Git 사용

```bash
cd
mkdir centos-practices
cd centos-practices
```

- /root 디렉토리 밑에 centos-practices 생성

  

```bash
git init
```

- 해당 프로젝트(centos-practices 디렉토리)를 share하기 위한 초기화 작업

  

```bash
echo "# centos-practices" > [README.md](http://readme.md/)
```

- 테스트 파일 생성

  

```bash
git add -A
```

- add는 커밋할 파일을 staging 시킨다

- eclipse에서 git staging 하는 것과 같음

- -A 옵션은 현재 디렉토리의 모든 파일을 staging 시킨다

  

```bash
git commit -m "first commit"
```

- 커밋 메시지와 함께 커밋됨

  

```bash
git branch -M main
```

- main 이름의 branch를 생성한다
- banch 이름은 자유롭게 설정

```bash
git remote add origin [https://github.com/soottoppi/centos-practices.git](https://github.com/soottoppi/centos-practices.git)
```

- origin이라는 이름의 리모트 저장소를 만들어 자신의 Repositories로 연결

  

```bash
git push -u origin main
```

- origin에 연결된 Repository의 main branch로 push

  

```bash
git clone [자신의 Repositories주소](https://github.com/douzone-busan-bitacademy/javastudy.git)
```

- 현재 디렉토리에 해당 주소의 Repo를 가져온다

- ex) git clone [https://github.com/douzone-busan-bitacademy/javastudy.git](https://github.com/douzone-busan-bitacademy/javastudy.git)

  

```bash
 mvn clean package
```

- 서버가 외부 프로젝트를 build 작업하는 과정

- maven이 깔려있지 않을 경우 수행되지 않음

  

---

### CI 과정

1. 개인이 작업한 각 작업물(예시로 myproject라 정함)을 github에 저장
2. 서버는 GIthub의 주소를 통해 프로젝트 가져오기( ***1. clone***)
3. 서버는 외부의 프로젝트라는 것을 pom.xml을 통해 알 수 있다
4. 쉘 입력창에서 mvn clean package를 통해 ***2. build*** 작업 진행
5. 작업 프로젝트안에 build 항목 밑으로 가져온 myproject가 생긴다
6. myproject 밑에 war파일과 jar 파일이 생성된다
7. war는 Tomcat manager로 ***3. 배포***된다
8. 위의 일련의 작업들(1 + 2 + 3)을 자동으로 관리하는 툴을 CI Tool이라고 부른다
9. 해당 툴에는 github 주소 + 배포 위치를 지정하면 자동으로 빌드와 배포작업을 해준다
10. 관련 툴로 JenKins 와 Gitlab이 있다
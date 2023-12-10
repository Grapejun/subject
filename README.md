#  SSUVOL_봉사활동 조회 및 추천서비스

<프로젝트 아키텍처>

<img width="577" alt="image" src="https://github.com/Grapejun/subject/assets/118108487/c249442d-a050-49d2-a2ac-5f331bab8849">



<봉사활동 조회>

사용자에게 봉사활동 필터링 조건을 받아 검색하고 보여주는 서비스.


<img width="575" alt="image" src="https://github.com/Grapejun/subject/assets/118108487/cc26015a-af10-4429-b1b9-c984f6e884f3">

<봉사활동 추천>

사용자의 봉사활동 조건과 총 봉사 시간을 받아서 일정순으로 봉사활동 리스트를 추천해주는 서비스. 

<img width="416" alt="봉사추천" src="https://github.com/Grapejun/subject/assets/118108487/6ff9c02f-c814-45d9-8fe9-f2e1066445e7">

특징


1. 사용자의 가용시간에 맞춰 각 봉사활동의 수행 날짜까지 스케쥴링 후 리스트 업.
   
2. 추천된 봉사 프로그램이 마음에 들지 않을 경우 다시 추천받을 수 있음.         
 - 봉사 조건에 따라 필터링 후, 프로그램을 무작위로 셔플하고 봉사활동 시간이 총 봉사 시간을 넘지 않도록 선택하는 로직 



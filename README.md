# Java Convenience Store Precourse

## 기능 요구 사항
- 파일 로더
  - [ ] production.md, promotion.md 파일 불러오기
- 오브젝트 로더
  - [ ] 파일 로더에서 데이터셋 문자열 가져오기 기능 구현
  - [ ] 상품 데이터셋 Parser 기능 구현
  - [ ] 파싱된 데이터셋을 상품 객체로 변환
  - [ ] 프로모션 데이터셋 Parser 기능 구현
  - [ ] 파싱된 데이터셋을 프로모션 객체로 변환 
- 상품
  - [ ] 데이터셋 파싱하여 상품 Object로 변환
- 프로모션
  - **프로모션 적용 조건**
    - [ ] 오늘 날짜가 프로모션 기간 내에 있을 때만 할인이 적용.
    - [ ] 프로모션 형태: N개 구매 시 1개 무료 증정 (예: 1+1, 2+1).
    - [ ] 동일 상품에 중복 프로모션 적용 불가.
  - **재고 차감 우선순위**
      - [ ] 프로모션 재고 우선 차감, 부족 시 일반 재고 사용.
  - **혜택 안내**
      - [ ] 프로모션 조건에 미치지 못하는 수량 구매 시 추가 혜택을 받을 수 있는 안내문 출력
      - [ ] 프로모션 재고 부족 시, 일부 수량을 정가로 결제하는 안내문 출력
- 멤버십
    - [ ] 프로모션 미적용 금액의 30% 할인을 제공
    - [ ] 멤버십 할인 최대 한도는 8,000원
- 주문
  - [ ] 구매자가 입력한 상품 가격과 수량에 따라 총 구매액(상품별 가격 × 수량)을 산출
- 결제
  - [ ] 주문에서 프로모션 및 멤버십 할인을 반영하여 최종 결제 금액을 계산
  - **재고 관리**
    - [ ] 각 상품의 재고를 확인하여 결제 가능 여부를 확인합니다.
    - [ ] 결제 시, 구매된 수량만큼 해당 상품의 재고에서 차감합니다.
- 영수증
    - [ ] **구매 상품 내역**: 상품명, 수량, 가격.
    - [ ] **증정 상품 내역**: 프로모션을 통한 무료 증정 상품 목록.
    - [ ] **금액 정보**:
        - [ ] 총 구매액: 구매한 상품의 총 금액.
        - [ ] 행사 할인: 프로모션에 의해 할인된 금액.
        - [ ] 멤버십 할인: 멤버십을 통한 추가 할인 금액.
        - [ ] 내실돈: 최종 결제 금액.
    - [ ] 영수증 포맷팅 설정
- 어플리케이션 입력
    - [ ] 구매할 상품과 수량을 입력 받는다. 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분
    - [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
      - Y: 증정 받을 수 있는 상품을 추가한다.
      - N: 증정 받을 수 있는 상품을 추가하지 않는다.
    - [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
      - Y: 일부 수량에 대해 정가로 결제한다.
      - N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.
    - [ ] 멤버십 할인 적용 여부를 입력 받는다.
      - Y: 멤버십 할인을 적용한다.
      - N: 멤버십 할인을 적용하지 않는다.
    - [ ] 추가 구매 여부를 입력 받는다.
      - Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
      - N: 구매를 종료한다.
    - [ ] 결제 후 추가 구매 여부를 선택한다.
- 어플리케이션 출력
    - [ ] 추가 구매 여부를 확인하기 위해 안내 문구를 출력한다. 
    - [ ] 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다.
    - [ ] 만약 재고가 0개라면 재고 없음을 출력한다.
    - [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력한다.
    - [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
    - [ ] 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력한다.
    - [ ] 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
    - [ ] 사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
      - [ ] 구매할 상품과 수량 형식이 올바르지 않은 경우: [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
      - [ ] 존재하지 않는 상품을 입력한 경우: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
      - [ ] 구매 수량이 재고 수량을 초과한 경우: [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
      - [ ] 기타 잘못된 입력의 경우: [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.
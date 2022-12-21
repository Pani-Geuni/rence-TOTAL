/**
 * @author 김예은, 전판근
 */
$(() => {

  // /** * 예약 가능 여부 버튼 클릭 ** */ // session 확인 필요
  // $('#office_check_available').click(() => {
  //   // 예약 타입 선택 O
  //   if ($('.type-border-txt').prop('check') == true) {
  //     if ($('.time-input:eq(0)').val() != '' && $('.time-input:eq(1)').val() != '') {
  //       // 예약 가능 확인 로직
  //       const query = location.search;
  //       const param = new URLSearchParams(query);
  //       const backoffice_no = param.get('backoffice_no');
  //       const room_no = $('#type-choice-value').attr('room_no');

  //       const reserve_stime = $('.time-input').val();
  //       const reserve_etime = `${checkout.getFullYear()}/${checkout.getMonth() + 1}/${checkout.getDate()}`;

  //       // 로딩 화면
  //       $('.popup-background:eq(1)').removeClass('blind');
  //       $('#spinner-section').removeClass('blind');

  //       $.ajax({
  //         url: '/office/office_reserve_check',
  //         type: 'GET',
  //         dataType: 'JSON',
  //         data: {
  //           backoffice_no,
  //           room_no,
  //           reserve_stime,
  //           reserve_etime,
  //         },

  //         success(res) {
  //           // 로딩 화면 닫기
  //           $('.popup-background:eq(1)').addClass('blind');
  //           $('#spinner-section').addClass('blind');

  //           if (res.result == '1') {
  //             $('#office_go_reserve').removeClass('blind');
  //             $('#office_check_available').addClass('blind');
  //           } else {
  //             $('.fixed-popup').removeClass('blind');
  //             $('.using-time-fail-txt').html('해당 기간에 이미 예약이 존재하여<br><br>예약할 수 없습니다.');
  //           }
  //         },

  //         error() {
  //           // 로딩 화면 닫기
  //           $('.popup-background:eq(1)').addClass('blind');
  //           $('#spinner-section').addClass('blind');
  //         },
  //       });
  //     }
  //     // 예약 타입 선택 O, 체크인 or 체크아웃 시간 X
  //     else {
  //       $('.fixed-popup').removeClass('blind');
  //       $('.using-time-fail-txt').html('체크인 시간과 개월 수를<br><br>모두 선택하여 주십시오.');
  //     }
  //   }
  //   // 예약 타입 선택 X
  //   else {
  //     $('.fixed-popup').removeClass('blind');
  //     $('.using-time-fail-txt').html('예약 타입을 선택하여 주십시오.');
  //   }
  // });
});

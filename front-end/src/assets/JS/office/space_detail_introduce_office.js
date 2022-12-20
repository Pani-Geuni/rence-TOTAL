/**
 * @author 김예은, 전판근
 */
$(() => {

  // /** ***************************** */
  // /** * 예약 가능 여부 버튼 클릭 ** */
  // /** ***************************** */
  // $('#check_available').click(() => {
  //   if (is_login2 != null) {
  //     // 예약 타입 선택 O
  //     if ($('.type-border-txt').prop('check') == true) {
  //       if ($('.time-input:eq(0)').val() != '' && $('.time-input:eq(1)').val() != '') {
  //         if ($('.time-input:eq(0)').val() > $('.time-input:eq(1)').val()) {
  //           $('.fixed-popup').removeClass('blind');
  //           $('.using-time-fail-txt:eq(0)').html('체크인 시간이 체크아웃 시간보다<br><br>빠를 수는 없습니다.');
  //         } else if ($('.time-input:eq(0)').val() == $('.time-input:eq(1)').val()) {
  //           $('.fixed-popup').removeClass('blind');
  //           $('.using-time-fail-txt:eq(0)').html('체크인 시간과 체크아웃 시간이<br><br>같을 수는 없습니다.');
  //         } else {
  //           // 예약 가능 확인 로직
  //           const query = location.search;
  //           const param = new URLSearchParams(query);
  //           const backoffice_no = param.get('backoffice_no');
  //           const room_no = $('#type-choice-value').attr('room_no');
  //           const reserve_date = $('.time-input:eq(0)').val();

  //           // 로딩 화면
  //           $('.popup-background:eq(1)').removeClass('blind');
  //           $('#spinner-section').removeClass('blind');

  //           $.ajax({
  //             url: '/office/reserve_check',
  //             type: 'GET',
  //             dataType: 'json',
  //             data: {
  //               backoffice_no,
  //               room_no,
  //               reserve_date,
  //             },

  //             success(res) {
  //               // 로딩 화면 닫기
  //               $('.popup-background:eq(1)').addClass('blind');
  //               $('#spinner-section').addClass('blind');

  //               const { reserve_list } = res;
  //               let pickerDate = '';
  //               const pick_time_list = [];	// 예약 선택 시간
  //               let temp_stime = '';
  //               const now = new Date();
  //               const year = now.getFullYear();
  //               let month = now.getMonth() + 1;
  //               if (month < 10) month = `0${month}`;
  //               let date = now.getDate();
  //               if (date < 10) date = `0${date}`;

  //               const today = `${year}/${month}/${date}`;
  //               const now_hours = now.getHours();

  //               const check_reserve_time = [];

  //               // 선택 날짜 요일 구하기
  //               pickerDate = $('.type-border-txt.time-input').val().trim();
  //               const dayOfWeek = getDayOfWeek(pickerDate);

  //               $('#check_available').addClass('blind');
  //               $('#go_reserve').removeClass('blind');

  //               let running_stime = '';
  //               let running_etime = '';

  //               $('.running-time-li-wrap li').each(function (index, item) {
  //                 if (index === dayOfWeek) {
  //                   const running_time = $(this).children('span').text().trim();

  //                   if (running_time !== '휴무') {
  //                     const split_time = running_time.split(' ~ ');

  //                     running_stime = parseInt(split_time[0].split(':')[0]);
  //                     running_etime = parseInt(split_time[1].split(':')[0]);

  //                     $('#reserve-time-boundary').css('display', 'flex');
  //                     const running_time_list = [];

  //                     temp_stime = '';

  //                     if (pickerDate == today) {
  //                       if (running_stime <= now_hours) {
  //                         temp_stime = now_hours + 1;
  //                       } else {
  //                         temp_stime = running_stime;
  //                       }
  //                     } else if (pickerDate > today) {
  //                       temp_stime = running_stime;
  //                     }

  //                     for (let t = temp_stime; t < running_etime; t++) {
  //                       if (!reserve_list.includes(t)) {
  //                         running_time_list.push(t);
  //                       }
  //                     }

  //                     // 운영 시간 display 표시
  //                     $('.time-boundary-list li').each(function (index, item) {
  //                       // 이전 운영 시간 display 초기화
  //                       $(this).css('display', 'none');
  //                       $(this).removeAttr('display');

  //                       if (running_time_list.includes(index)) {
  //                         $(this).css('display', 'flex');
  //                         $(this).attr('display', 'selected');
  //                       }
  //                     });
  //                   } else {
  //                     $('.time-boundary-list li').each(function (index, item) {
  //                       $(this).css('display', 'none');
  //                       $(this).removeAttr('display');
  //                     });
  //                   }
  //                 }
  //               });
  //               // END runnint-time-li-wrap li each
  //             },
  //             error() {
  //               // 로딩 화면 닫기
  //               $('.popup-background:eq(1)').addClass('blind');
  //               $('#spinner-section').addClass('blind');
  //             },
  //           });
  //         }
  //       }
  //       // 예약 타입 선택 O, 체크인 or 체크아웃 시간 X
  //       else {
  //         $('.fixed-popup').removeClass('blind');
  //         $('.using-time-fail-txt:eq(0)').html('체크인 시간과 체크아웃 시간을<br><br>모두 선택하여 주십시오.');
  //       }
  //     }
  //     // 예약 타입 선택 X
  //     else {
  //       $('.fixed-popup').removeClass('blind');
  //       $('.using-time-fail-txt:eq(0)').html('예약 타입을 선택하여 주십시오.');
  //     }
  //   } else {
  //     $('.popup-background:eq(1)').removeClass('blind');
  //     $('#common-alert-popup').removeClass('blind');
  //     $('.common-alert-txt').text('로그인 후 이용가능한 기능입니다.');
  //   }
  // });

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

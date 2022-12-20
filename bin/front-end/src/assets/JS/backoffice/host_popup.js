/**
* @author : 전판근, 김예은
*/
$(() => {
  $('#join-btn').click(() => {
    $('.popup-background:eq(0)').addClass('blind');
    $('#join-section').addClass('blind');
  });

  $('#success-alert-btn').click(() => {
    $('.popup-background:eq(0)').addClass('blind');
    $('#success-alert-popup').addClass('blind');
  });
  $('#fail-alert-btn').click(() => {
    $('.popup-background:eq(0)').addClass('blind');
    $('#fail-alert-popup').addClass('blind');
  });

  $('#logout-closeBtn').click(() => {
    $('#logout-popup').addClass('blind');
    $('.popup-background:eq(0)').addClass('blind');
  });

  /** **** ******* **** */
  /** **** 공간 추가 **** */
  /** **** ******* **** */

  // 공간 가격 정규표현식 사용
  $('#input-price-name').on('keyup keydown', function () {
    const check = /^[0-9]+$/;

    if (!check.test($('#input-price-name').val().trim())) {
      $(this).siblings('.r-input-warning:eq(0)').removeClass('blind');
    } else {
      $(this).siblings('.r-input-warning:eq(0)').addClass('blind');
    }
  });

  // 인풋 창 클릭 시 경고 테두리 초기화
  $('.room-input').click(function () {
    $(this).removeClass('null-input-border');
  });

  // 공간 가격 정규표현식 사용
  $('#m-input-price-name').on('keyup keydown', function () {
    const check = /^[0-9]+$/;

    if (!check.test($('#m-input-price-name').val().trim())) {
      $(this).siblings('.r-input-warning:eq(1)').removeClass('blind');
    } else {
      $(this).siblings('.r-input-warning:eq(1)').addClass('blind');
    }
  });
});

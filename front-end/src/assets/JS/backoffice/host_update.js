/**
 * @author : 전판근, 최진실
 */

$(function () {

  /** 공용 알러트 창닫기 버튼 */
  $('#common-alert-btn').click(() => {
    $('.popup-background:eq(1)').addClass('blind');
    $('#common-alert-popup').addClass('blind');
  });

  $('input, textarea').click(function () {
    if ($(this).hasClass('null-input-border')) {
      $(this).removeClass('null-input-border');
    }
  });

  // 빈 항목 팝업 닫기
  $('#empty-fail-alert-btn').click(() => {
    $('#fail-alert-popup').addClass('blind');
    $('.popup-background:eq(0)').addClass('blind');
  });

  // 휴무일 체크시 timepicker block
  $('#sun_dayoff').click(function () {
    if (this.checked) {
      $('#sun_stime').attr('disabled', true);
      $('#sun_etime').attr('disabled', true);
    } else {
      $('#sun_stime').attr('disabled', false);
      $('#sun_etime').attr('disabled', false);
    }
  });

  /** 공간 타입 체크 박스 - 변경 불가 */
  $('#type_checkbox_office').attr('disabled', true);
  $('#type_checkbox_desk').attr('disabled', true);
  $('#type_checkbox_meeting_room').attr('disabled', true);
});

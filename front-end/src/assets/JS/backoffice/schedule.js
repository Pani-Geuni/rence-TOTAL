/**
 * @author : 전판근, 김예은, 최진실
 */

$(function () {

  $("input:radio[name='set_schedule']:eq(0)").click(function () {
    $(".off-type-warning:eq(0)").removeClass('blind');
    $(".off-type-warning:eq(1)").addClass('blind');
  })

  $("input:radio[name='set_schedule']:eq(1)").click(function () {
    $(".off-type-warning:eq(0)").addClass('blind');
    $(".off-type-warning:eq(1)").removeClass('blind');
  })

  // *************
  // 전체 선택 체크박스
  // *************
  $('#select-all-room').click(function () {
    if ($(this).is(':checked')) {
      $(this).attr('checked', true);
    } else {
      $(this).attr('checked', false);
    }
  });

  $('#select-all-room').change(function () {
    if ($(this).is(':checked')) {
      $("input:checkbox[name='select-room']").each(function (index) {
        if ($(this).is(':disabled')) {
          console.log(index)
          $(this).prop('checked', false);
        } else {
          $(this).prop('checked', true);
        }
      });

    } else {
      $("input:checkbox[name='select-room']").prop('checked', false);
    }
  });

  // 팝업
  $("#radio-check-closeBtn").click(function () {
    $("#radio-check-popup").addClass("blind");
    $(".popup-background:eq(0)").addClass("blind");
  });

  $("#time-input-closeBtn").click(function () {
    $("#time-input-popup").addClass("blind");
    $(".popup-background:eq(0)").addClass("blind");
  });

  $("#no-reservation-closeBtn").click(function () {
    $("#no-reservation-popup").addClass("blind");
    $(".popup-background:eq(0)").addClass("blind");
  });


  // ************************
  // 예약자 리스트 전체 선택 체크박스
  // ************************
  $('#select-all-reservation').click(function () {
    if ($(this).is(':checked')) {
      $(this).attr('checked', true);
    } else {
      $(this).attr('checked', false);
    }
  });

  $('#select-all-reservation').change(function () {
    if ($(this).is(':checked')) {
      $("input:checkbox[name='reserve_no']").prop('checked', true);
    } else {
      $("input:checkbox[name='reserve_no']").prop('checked', false);
    }
  });
});

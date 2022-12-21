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
});

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

  var scroll_flag = true;
  // 스케줄 - 스크롤을 이용한 페이징
  $(".schedule-ct").scroll(function () {
    if (Math.ceil($(this).scrollTop() + $(this).innerHeight()) >= $(this).prop('scrollHeight')) {
      if ($(".ct-body-row").length - 1 < Number($("#maxCnt").attr("maxCnt")) && scroll_flag) {
        //로딩 화면
        $(".popup-background:eq(1)").removeClass("blind");
        $("#spinner-section").removeClass("blind");

        let query = location.search;
        let param = new URLSearchParams(query);
        let backoffice_no = param.get('backoffice_no');

        let sDateTime = $(".time-input:eq(0)").val().split(' ');
        let eDateTime = $(".time-input:eq(1)").val().split(' ');

        let not_sdate = sDateTime[0];
        let not_stime = sDateTime[1];
        let not_edate = eDateTime[0];
        let not_etime = eDateTime[1];
        let off_type = $("input:radio[name='set_schedule']:checked").val();

        var page = $("#maxCnt").attr("nowCnt");

        scroll_flag = false;
        $.ajax({
          url: "/backoffice/schedule_research_paging",
          type: "GET",
          dataType: 'json',
          data: {
            backoffice_no: backoffice_no,
            not_sdate: not_sdate,
            not_edate: not_edate,
            not_stime: not_stime,
            not_etime: not_etime,
            off_type: off_type,
            page: Number(page) + 1
          },
          success: function (res) {
            scroll_flag = true;

            //로딩 화면 닫기
            $(".popup-background:eq(1)").addClass("blind");
            $("#spinner-section").addClass("blind");

            var now = $("#maxCnt").attr("nowCnt");
            $("#maxCnt").attr("nowCnt", Number(now) + 1);

            for (var i = 0; i < res.cnt; i++) {
              var row = $($(".ct-body-row")[0]).clone();
              row.removeClass("blind");

              row.find("#room_no").attr("room_no", res.sc_vos[i].room_no);

              let kor_room_name = "";
              switch (res.sc_vos[i].room_type) {
                case 'desk':
                  kor_room_name = "데스크";
                  break;
                case 'meeting_04':
                  kor_room_name = "미팅룸 (4인)";
                  break;
                case 'meeting_06':
                  kor_room_name = "미팅룸 (6인)";
                  break;
                case 'meeting_10':
                  kor_room_name = "미팅룸 (10인)";
                  break;
                case 'office':
                  kor_room_name = "오피스";
                  break;

                default:
                  break;
              }
              row.find(".room_type").text(kor_room_name);

              row.find(".room_name").text(res.sc_vos[i].room_name);

              if (res.sc_vos[i].reserve_is === 'X') {
                row.find(".reserve_is").append('<img src="/static/IMG/dash-board/ico-close.svg" />');
              } else {
                row.find(".reserve_is").append('<img src="/static/IMG/dash-board/ico-circle.svg" />');
              }

              row.find(".reserve_cnt").text(res.sc_vos[i].reserve_cnt + "명");
              row.find(".reserve_cnt").attr("reserve_cnt", res.sc_vos[i].reserve_cnt);

              $(".schedule-ct").append(row);
            }
          },
          error: function () {
            scroll_flag = true;

            //로딩 화면 닫기
            $(".popup-background:eq(1)").addClass("blind");
            $("#spinner-section").addClass("blind");
          }
        });
      }
    }
  });

  var scroll_flag2 = true;
  // 예약자 - 스크롤을 이용한 페이징
  $(".reservation-ct").scroll(function () {
    if (Math.ceil($(this).scrollTop() + $(this).innerHeight()) >= $(this).prop('scrollHeight')) {
      if ($(".ct-body-row").length < Number($("#maxCnt").attr("maxCnt")) && scroll_flag2) {
        //로딩 화면
        $(".popup-background:eq(1)").removeClass("blind");
        $("#spinner-section").removeClass("blind");

        let backoffice_no = $.cookie('backoffice_no');

        let room_no = window.location.href.split("&room_no=")[1].split("&")[0];

        let not_sdate = window.location.href.split("&not_sdate=")[1].split("&")[0];
        let not_stime = window.location.href.split("&not_stime=")[1].split("&")[0];
        let not_edate = window.location.href.split("&not_edate=")[1].split("&")[0];
        let not_etime = window.location.href.split("&not_etime=")[1].split("&")[0];
        let off_type = window.location.href.split("&off_type=")[1].split("&")[0];

        var page = $("#maxCnt").attr("nowCnt");

        scroll_flag2 = false;
        $.ajax({
          url: "/backoffice/reservation_paging",
          type: "GET",
          dataType: 'json',
          data: {
            backoffice_no: backoffice_no,
            room_no: room_no,
            not_sdate: not_sdate,
            not_edate: not_edate,
            not_stime: not_stime,
            not_etime: not_etime,
            off_type: off_type,
            page: Number(page) + 1
          },
          success: function (res) {
            scroll_flag2 = true;

            //로딩 화면 닫기
            $(".popup-background:eq(1)").addClass("blind");
            $("#spinner-section").addClass("blind");

            var now = $("#maxCnt").attr("nowCnt");
            $("#maxCnt").attr("nowCnt", Number(now) + 1);

            for (var i = 0; i < res.cnt; i++) {
              var row = $($(".ct-body-row")[0]).clone();

              row.find(".ct-body-cell:eq(0)>input:eq(0)").attr("room_no", res.rv_vos[i].room_no);
              row.find(".ct-body-cell:eq(0)>input:eq(1)").attr("backoffice_no", res.rv_vos[i].backoffice_no);
              row.find(".ct-body-cell:eq(0)>input:eq(2)").attr("reserve_no", res.rv_vos[i].reserve_no);

              row.find(".ct-body-cell:eq(1)>span").text(res.rv_vos[i].user_name);
              row.find(".ct-body-cell:eq(2)>span").text(res.rv_vos[i].user_email);
              row.find(".ct-body-cell:eq(3)>span").text(res.rv_vos[i].user_tel);
              row.find(".ct-body-cell:eq(4)>span").text(res.rv_vos[i].reserve_stime + " ~ " + res.rv_vos[i].reserve_etime);

              $(".reservation-ct").append(row);
            }
          },
          error: function () {
            scroll_flag2 = true;

            //로딩 화면 닫기
            $(".popup-background:eq(1)").addClass("blind");
            $("#spinner-section").addClass("blind");
          }
        });
      }
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

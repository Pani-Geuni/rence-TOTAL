/* eslint-disable linebreak-style */
/* eslint-disable no-useless-concat */
/* eslint-disable no-use-before-define */
/* eslint-disable no-plusplus */
/* eslint-disable no-tabs */
/* eslint-disable indent */
/* eslint-disable camelcase */
/**
 * @author : 전판근, 김예은
 */

// ******************
// 호스트 신청에 필요한 JS
// ******************

import $ from 'jquery';

$(() => {
	/** 공용 알러트 창닫기 버튼 */
	$('#common-alert-btn').click(() => {
		$('.popup-background:eq(1)').addClass('blind');
		$('#common-alert-popup').addClass('blind');
	});

	$('input, textarea').click(() => {
		if ($(this).hasClass('null-input-border')) {
			$(this).removeClass('null-input-border');
		}
	});

	// 빈 항목 팝업 닫기
	$('#empty-fail-alert-btn').click(() => {
		$('#fail-alert-popup').addClass('blind');
		$('.popup-background:eq(0)').addClass('blind');
	});

	$('#backoffice_info').on('keydown keyup', () => {
		$('.b_info_txt_length').text($(this).val().length);

		if ($(this).val().length > 500) {
			$(this).val($(this).val().substring(0, 500));
		}
	});
});

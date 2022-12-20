/* eslint-disable linebreak-style */
/* eslint-disable camelcase */
/* eslint-disable indent */
/* eslint-disable no-tabs */
/* eslint-disable no-restricted-globals */

/**
* @author : 전판근
*/
import $ from 'jquery';

$(() => {
	/** *** *********** **** */
	/** *** 슬라이드 이미지 **** */
	/** *** *********** **** */
	let test = 1;
	$('.prev').addClass('hide');

	if ($('.img').length === 1) {
		$('.next').addClass('hide');
	} else {
		let position = 0;
		$('.next').on('click', () => {
			if (test < $('.img').length) {
				position += 960;
				$('.container').css('transform', `translateX(-${position}px)`);
				test += 1;

				if (test === $('.img').length) {
					$('.next').addClass('hide');
				}
				if (test === 2) {
					$('.prev').removeClass('hide');
				}
			}
		});

		$('.prev').on('click', () => {
			if (test !== 1) {
				position -= 960;
				$('.container').css('transform', `translateX(-${position}px)`);
				test -= 1;

				if (test === $('.img').length - 1) {
					$('.next').removeClass('hide');
				}
				if (test === 1) {
					$('.prev').addClass('hide');
				}
			}
		});
	}
});

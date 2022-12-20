<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/label-has-for -->
<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable camelcase -->
<template>
  <section class="bodyWrap">
    <div class="applyWrap">
      <div class="titleWrap">
        <h1>호스트 신청하기</h1>
      </div>

      <!-- <form action="insertOK" id="insertForm" method="POST" enctype="multipart/form-data"> -->
      <div id="insertForm">
        <div class="inputWrap">
          <p>사업자 이름</p>
          <input type="text" v-model="owner_name" id="owner_name" name="owner_name" placeholder="사업자 이름을 입력하세요"
            v-on:keydown.enter.prevent />
        </div>
        <div class="inputWrap">
          <p>사업자 등록 번호</p>
          <div class="check_wrap">
            <input type="text" v-model="backoffice_id" id="backoffice_id" name="backoffice_id"
              placeholder="사업자 등록 번호를 입력하세요 (- 포함)" @keyup="keypressBackofficeId" @keydown="keypressBackofficeId" />
            <span class="warning-text blind">형식에 맞지 않습니다.</span>
          </div>
        </div>
        <div class="inputWrap">
          <p>상호명</p>
          <input type="text" v-model="backoffice_name" id="backoffice_name" name="backoffice_name"
            placeholder="상호명을 입력해 주세요" />
        </div>
        <div class="inputWrap">
          <p>사업체 명</p>
          <input type="text" v-model="company_name" id="company_name" name="company_name" placeholder="사업체 명을 입력해 주세요"
            v-on:keydown.enter.prevent />
        </div>
        <div class="inputWrap">
          <p>사업자 전화번호</p>
          <div class="check_wrap">
            <input type="tel" v-model="backoffice_tel" id="backoffice_tel" name="backoffice_tel"
              placeholder="사업자 전화번호를 입력해 주세요. (- 포함)" @keyup="keypressBackofficeTel" @keydown="keypressBackofficeTel" />
            <span class="warning-text blind">형식에 맞지 않습니다.</span>
          </div>
        </div>
        <div class="inputWrap email">
          <p>사업자 이메일</p>
          <div>
            <input type="email" v-model="backoffice_email" id="backoffice_email" name="backoffice_email"
              placeholder="사업자 이메일을 입력해 주세요" />
            <input @click="sendMail" type="button" id="btn-certification" value="인증번호 발송">
          </div>
        </div>
        <div class="inputWrap email">
          <p>사업자 이메일 확인</p>
          <div>
            <input type="text" v-model="auth_code" id="auth_code" name="auth_code" placeholder="인증 번호를 입력하세요" />
            <input @click="authOK" type="button" id="btn-check-certification" value="인증번호 확인">
          </div>
        </div>
        <div class="inputWrap location">
          <p>사업장 위치</p>
          <div class="input-location">
            <div>
              <input type="text" v-model="zipcode" id="zipcode" name="zipcode" placeholder="우편번호" readonly />
              <input id="find-zipcode" @click="execDaumPostcode" type="button" value="우편번호 찾기" />
            </div>

            <input type="text" v-model="roadname_address" id="roadname_address" name="roadname_address"
              placeholder="도로명주소" readonly /> <br />
            <input type="text" v-model="number_address" id="number_address" name="number_address" placeholder="지번주소"
              readonly /> <br />
            <input type="text" v-model="detail_address" id="detail_address" name="detail_address" placeholder="상세주소"
              v-on:keydown.enter.prevent />
          </div>
          <!-- END input-location -->
        </div>
        <!-- END inputWrap location  -->

        <div class="inputWrap">
          <p>사업체 태그</p>
          <div class="hashTag_area">
            <div class="hashTag-group">
              <input type="hidden" value="" name="backoffice_tag" id="real-input-tag" v-on:keydown.enter.prevent />
            </div>
            <input type="text" v-model="tagValue" id="backoffice_tag" placeholder="사업체의 태그를 입력해 주세요 (최대 10글자)"
              maxlength=10 @keyup.enter="createHashTag" @keyup.space="createHashTag" v-on:click.stop />
            <ul id="tag-list" v-if="margin_tag_list.length !== 0">
              <li class='tag-item' v-for="tag in margin_tag_list" :key="tag">{{ tag
              }}
                <span @click="deleteTag" class='del-btn' :tag="tag" :idx="counter">x</span>
              </li>
            </ul>
          </div>
        </div>

        <div class="inputWrap info">
          <p>사업체 소개</p>
          <div class="check_wrap">
            <textarea v-model="backoffice_info" id="backoffice_info" name="backoffice_info"
              placeholder="공간 소개를 입력해 주세요"></textarea>
            <div class="b_info_txt_length_wrap">
              <span class="b_info_txt_length">0</span>
              <span>&nbsp;/ 500</span>
            </div>
          </div>
        </div>

        <div class="inputWrap backoffice_type">
          <p>공간 타입</p>
          <div class="option-group-column">
            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="type_checkbox_desk" class="checkbox" v-model="backoffice_type" value="desk"
                  @click="clickTypeCheckbox" readonly />
                <label>데스크</label>
              </div>
              <!-- END option-item -->
              <div class="option-item">
                <input type="checkbox" id="type_checkbox_meeting_room" class="checkbox" v-model="backoffice_type"
                  value="meeting_room" @click="clickTypeCheckbox" readonly />
                <label>회의실</label>
              </div>
              <!-- END option-item -->
              <div class="option-item">
                <input type="checkbox" id="type_checkbox_office" class="checkbox" v-model="backoffice_type"
                  value="office" @click="clickTypeCheckbox" readonly />
                <label>오피스</label>
              </div>
              <!-- END option-item -->
            </div><!-- END option-group-row -->
          </div>
          <!-- END option-group-column -->
        </div>
        <!-- END inputWrap backoffice_type -->

        <div class="inputWrap option">
          <p>공간 옵션</p>
          <div class="option-group-column">
            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="chair-desk" class="checkbox" v-model="backoffice_option" value="chair-desk"
                  readonly /> <label>의자/테이블</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="internet-wifi" class="checkbox" v-model="backoffice_option"
                  value="internet-wifi" /> <label>인터넷/Wi-Fi</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="restroom" class="checkbox" v-model="backoffice_option" value="restroom" />
                <label>내부 화장실</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="no-smoking" class="checkbox" v-model="backoffice_option"
                  value="no-smoking" />
                <label>금연</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="smoking-room" class="checkbox" v-model="backoffice_option"
                  value="smoking-room" /> <label>흡연실</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="terrace-rooftop" class="checkbox" v-model="backoffice_option"
                  value="terrace-rooftop" /> <label>테라스/루프탑</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="tv-projector" class="checkbox" v-model="backoffice_option"
                  value="tv-projector" /> <label>TV/프로젝터</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="whiteboard" class="checkbox" v-model="backoffice_option"
                  value="whiteboard" />
                <label>화이트보드</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="sound-microphone" class="checkbox" v-model="backoffice_option"
                  value="sound-microphone" /> <label>음향/마이크</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="parking" class="checkbox" v-model="backoffice_option" value="parking" />
                <label>주차</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="pc-laptop" class="checkbox" v-model="backoffice_option" value="pc-laptop" />
                <label>PC/노트북</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="printer" class="checkbox" v-model="backoffice_option" value="printer" />
                <label>복사/인쇄기</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="lounge" class="checkbox" v-model="backoffice_option" value="lounge" />
                <label>공용 라운지</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="kitchen" class="checkbox" v-model="backoffice_option" value="kitchen" />
                <label>공용 주방</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="water-purifier" class="checkbox" v-model="backoffice_option"
                  value="water-purifier" /> <label>정수기</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="can-food" class="checkbox" v-model="backoffice_option" value="can-food" />
                <label>음식물 반입 가능</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="can-drink" class="checkbox" v-model="backoffice_option" value="can-drink" />
                <label>주류 반입 가능</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="air-conditioner" class="checkbox" v-model="backoffice_option"
                  value="air-conditioner" /> <label>에어컨</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="fitting-room" class="checkbox" v-model="backoffice_option"
                  value="fitting-room" /> <label>탈의실</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="shower" class="checkbox" v-model="backoffice_option" value="shower" />
                <label>샤워시설</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="body-mirror" class="checkbox" v-model="backoffice_option"
                  value="body-mirror" />
                <label>전신거울</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="door-lock" class="checkbox" v-model="backoffice_option" value="door-lock" />
                <label>도어락</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="outlet-multitap" class="checkbox" v-model="backoffice_option"
                  value="outlet-multitap" /> <label>콘센트/멀티탭</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="personal-locker" class="checkbox" v-model="backoffice_option"
                  value="personal-locker" /> <label>개인락커</label>
              </div>
            </div>
            <!-- END option-group row -->
            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="fax" class="checkbox" v-model="backoffice_option" value="fax" />
                <label>팩스</label>
              </div>
            </div>
            <!-- END option-group row -->
          </div>
          <!-- END option-group column -->
        </div>
        <!-- END inputWrap option -->

        <div class="inputWrap facilities">
          <p>주변 시설</p>
          <div class="option-group-column">
            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="public-parking" class="checkbox" v-model="backoffice_around"
                  value="public-parking" /> <label>공영주차장</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="paid-parking" class="checkbox" v-model="backoffice_around"
                  value="paid-parking" /> <label>유료주차장</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="pharmacy" class="checkbox" v-model="backoffice_around" value="pharmacy" />
                <label>약국</label>
              </div>
            </div>
            <!-- END option-group row -->

            <div class="option-group-row">
              <div class="option-item">
                <input type="checkbox" id="hospital" class="checkbox" v-model="backoffice_around" value="hospital" />
                <label>병원</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="convenience-store" class="checkbox" v-model="backoffice_around"
                  value="convenience-store" /> <label>편의점</label>
              </div>

              <div class="option-item">
                <input type="checkbox" id="cafe" class="checkbox" v-model="backoffice_around" value="cafe" />
                <label>카페</label>
              </div>
            </div>
            <!-- END option-group row -->
          </div>
          <!-- END option-group column -->
        </div>
        <!-- END inputWrap facilities -->

        <div class="inputWrap time">
          <p>운영 시간</p>

          <div class="custom-table">
            <div class="custom-table-heading">
              <div class="table-head-cell">
                <p>요일</p>
              </div>
              <div class="table-head-cell">
                <p>오픈 시간</p>
              </div>
              <div class="table-head-cell">
                <p>마감 시간</p>
              </div>
              <div class="table-head-cell">
                <p>휴무일 체크</p>
              </div>
            </div>
            <!-- END custom table heading -->

            <div class="custom-table-body">
              <div class="table-body-row">
                <div class="table-body-cell">일</div>
                <div class="table-body-cell">
                  <!-- <input type="text" id="sun_stime" class="time-picker" name="sun_stime" readonly /> -->
                  <Datepicker v-model="sunStime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="sun_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <!-- <input type="text" id="sun_etime" class="time-picker" name="sun_etime" readonly /> -->
                  <Datepicker v-model="sunEtime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="sun_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="sun_dayoff" class="checkbox" name="sun_dayoff" v-model="sun_dayoff"
                    true-value="T" false-value="F" />
                </div>
              </div>
              <!-- END table body row -->

              <div class="table-body-row">
                <div class="table-body-cell">월</div>
                <div class="table-body-cell">
                  <!-- <input type="text" id="mon_stime" class="time-picker" name="mon_stime" readonly /> -->
                  <Datepicker v-model="monStime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="mon_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <Datepicker v-model="monEtime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="mon_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="mon_dayoff" class="checkbox" name="mon_dayoff" v-model="mon_dayoff"
                    true-value="T" false-value="F" />
                </div>
              </div>
              <!-- END table body row -->

              <div class="table-body-row">
                <div class="table-body-cell">화</div>
                <div class="table-body-cell">
                  <Datepicker v-model="tueStime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="tue_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <Datepicker v-model="tueEtime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="tue_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="tue_dayoff" class="checkbox" name="tue_dayoff" v-model="tue_dayoff"
                    true-value="T" false-value="F" />
                </div>
              </div>
              <!-- END table body row -->

              <div class="table-body-row">
                <div class="table-body-cell">수</div>
                <div class="table-body-cell">
                  <Datepicker v-model="wedStime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="wed_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <Datepicker v-model="wedEtime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="wed_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="wed_dayoff" class="checkbox" name="wed_dayoff" v-model="wed_dayoff"
                    true-value="T" false-value="F" />
                </div>
              </div>
              <!-- END table body row -->

              <div class="table-body-row">
                <div class="table-body-cell">목</div>
                <div class="table-body-cell">
                  <Datepicker v-model="thuStime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="thu_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <Datepicker v-model="thuEtime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="thu_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="thu_dayoff" class="checkbox" name="thu_dayoff" v-model="thu_dayoff"
                    true-value="T" false-value="F" />
                </div>
              </div>
              <!-- END table body row -->

              <div class="table-body-row">
                <div class="table-body-cell">금</div>
                <div class="table-body-cell">
                  <Datepicker v-model="friStime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="fri_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <Datepicker v-model="friEtime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="fri_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="fri_dayoff" class="checkbox" name="fri_dayoff" v-model="fri_dayoff"
                    true-value="T" false-value="F" />
                </div>
              </div>
              <!-- END table body row -->

              <div class="table-body-row">
                <div class="table-body-cell">토</div>
                <div class="table-body-cell">
                  <Datepicker v-model="satStime" id="sat_stime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="sat_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <Datepicker v-model="satEtime" id="sat_etime" time-picker :start-time="startTime" mode-height="160"
                    minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                    :disabled="sat_dayoff === 'T'"></Datepicker>
                </div>
                <div class="table-body-cell">
                  <input type="checkbox" id="sat_dayoff" class="checkbox" name="sat_dayoff" v-model="sat_dayoff"
                    true-value="T" false-value="F" @change="disableTimepicker" />
                </div>
              </div>
              <!-- END table body row -->
            </div>
            <!-- END custom table body -->
          </div>
          <!-- END custom table -->
        </div>
        <!-- END inputWrap time -->

        <div class="inputWrap">
          <p>공간 사진</p>

          <div class="filebox">
            <input class="upload-name" value="" placeholder="첨부파일(.jpg/.jpeg/.png/.jfif)" />
            <label for="multipartFile_room">파일찾기</label>
            <input type="file" id="multipartFile_room" @change="uploadImage" name="multipartFile_room"
              accept=".jpg, .jpeg, .png, .jfif" multiple="multiple" />
          </div>
          <input type="file" id="multipartFile_host" name="multipartFile_host" style="display: none;" />
        </div>
        <!-- END inputWrap image -->

        <div class="submit">
          <input @click="submit" type="button" id="submit" class="submit-application" value="호스트 신청하기">
          <input type="submit" id="real-submit" class="submit-application" value="호스트 신청하기" style="display:none;">
        </div>
      </div>
      <!-- </form> -->
    </div>
    <!-- END applyWrap -->
  </section>
  <!-- END bodyWrap -->
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/backoffice/host-insert.scss';
@import '@/assets/CSS/backoffice/custom-timepicker.css';

.mx-input-wrapper {
  width: 100px;
}
</style>

<script>
import $ from 'jquery';
import '@vuepic/vue-datepicker/src/VueDatePicker/style/main.scss';
import { ref } from 'vue';
// import '@/assets/JS/backoffice/host_insert';
import axios from 'axios';

export default {
  name: 'InsertView',

  setup() {
    const time = ref();
    const startTime = ref({
      hours: 9,
      minutes: 0,
    });

    const sunStime = ref();
    const sunEtime = ref();
    const monStime = ref();
    const monEtime = ref();
    const tueStime = ref();
    const tueEtime = ref();
    const wedStime = ref();
    const wedEtime = ref();
    const thuStime = ref();
    const thuEtime = ref();
    const friStime = ref();
    const friEtime = ref();
    const satStime = ref();
    const satEtime = ref();

    return {
      time,
      startTime,
      sunStime,
      sunEtime,
      monStime,
      monEtime,
      tueStime,
      tueEtime,
      wedStime,
      wedEtime,
      thuStime,
      thuEtime,
      friStime,
      friEtime,
      satStime,
      satEtime,
    };
  },

  data() {
    return {
      owner_name: '',
      backoffice_id: '',
      backoffice_name: '',
      company_name: '',
      backoffice_tel: '',
      backoffice_email: $('#backoffice_email').val(),
      auth_code: '',
      mail_flag: true,

      zipcode: '',
      roadname: '',
      roadname_address: '',
      number_address: '',
      detail_address: '',

      tagValue: '',
      tag: {},
      counter: 0,
      margin_tag_list: [],
      backoffice_tag: '',

      backoffice_type: [],
      backoffice_info: '',
      backoffice_option: [],
      backoffice_around: [],

      sun_dayoff: 'F',
      mon_dayoff: 'F',
      tue_dayoff: 'F',
      wed_dayoff: 'F',
      thu_dayoff: 'F',
      fri_dayoff: 'F',
      sat_dayoff: 'F',

      img_name: '',

      submit_flag: true,
    };
  },

  methods: {

    // 사업자 번호 형식 체크
    keypressBackofficeId() {
      /* 사업자등록번호 */
      const regExp = /^([0-9]{3})-?([0-9]{2})-?([0-9]{5})$/;
      if (!regExp.test($('#backoffice_id').val().trim())) {
        $('.warning-text:eq(0)').removeClass('blind');
      } else {
        $('.warning-text:eq(0)').addClass('blind');
      }
    },

    // 전화번호 형식 확인
    keypressBackofficeTel() {
      /* 유전 전화 + 휴대폰 번호 */
      const telExp = /^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;

      /* 대표전화번호 1588 등 */
      const telExp2 = /^(1544|1566|1577|1588|1644|1688)-?([0-9]{4})$/;

      if (!telExp.test($('#backoffice_tel').val().trim()) && !telExp2.test($('#backoffice_tel').val().trim())) {
        $('.warning-text:eq(1)').removeClass('blind');
      } else {
        $('.warning-text:eq(1)').addClass('blind');
      }
    },

    // 이메일 확인
    sendMail() {
      if (!$('#btn-certification').prop('check')) {
        if (this.backoffice_email.length > 0) {
          const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

          if (email.test(this.backoffice_email)) {
            if (this.mail_flag) {
              // 로딩 화면
              $('.popup-background:eq(1)').removeClass('blind');
              $('#spinner-section').removeClass('blind');
              this.mail_flag = false;

              // console.log($('#backoffice_email').val().trim(), this.mail_flag);

              const params = new URLSearchParams();
              params.append('backoffice_email', this.backoffice_email);
              const url = `http://localhost:8800/backoffice/auth?${params}`;
              axios.get(url)
                .then((res) => {
                  this.mail_flag = true;
                  console.log('success');
                  console.log(res);

                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  // 이메일 중복 성공
                  if (res.data.result === '1') {
                    $('#btn-certification').prop('check', true);
                    this.timer();
                    $('#backoffice_email').attr('readonly', true);
                    $('#backoffice_email').addClass('readOnly');

                    $('.popup-background:eq(1)').removeClass('blind');
                    $('#common-alert-popup').removeClass('blind');
                    $('.common-alert-txt').html('이메일로 인증번호를 발송하였습니다.<br> 2분 내로 인증번호 인증을 완료해주세요.<br> 2분 초과 시 이메일 재인증이 필요합니다!');
                  } else if (res.data.result === '3') {
                    $('.popup-background:eq(1)').removeClass('blind');
                    $('#common-alert-popup').removeClass('blind');
                    $('.common-alert-txt').html('해당 이메일은 인증번호 발송 후<br> 2분이 되지 않았습니다.<br> 잠시만 기다려주세요!');
                  } else {
                    $('.popup-background:eq(1)').removeClass('blind');
                    $('#common-alert-popup').removeClass('blind');
                    $('.common-alert-txt').text('이미 존재하는 이메일입니다.');
                  }
                })
                .catch(() => {
                  this.mail_flag = true;

                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
                });
            }
          }
        } else {
          $('#backoffice_email').addClass('null-input-border');
        }
      }
    },

    authOK() {
      if (!$('#btn-check-certification').prop('check')) {
        if ($('#auth_code').val().trim().length > 0) {
          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          console.log(this.backoffice_email);
          console.log(this.auth_code);

          const params = new URLSearchParams();
          params.append('backoffice_email', this.backoffice_email);
          params.append('auth_code', this.auth_code);
          axios.post('http://localhost:8800/backoffice/authOK', params).then((res) => {
            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            // 이메일 중복 성공
            if (res.data.result === '1') {
              $('#btn-check-certification').prop('check', true);
              $('#btn-check-certification').val('인증완료');
              $('#auth_code').attr('readonly', true);
              $('#auth_code').addClass('readOnly');

              this.timer('true');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('인증완료 되었습니다.');
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('인증에 실패하였습니다.');
            }
          }).catch(() => {
            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
        } else {
          $('#auth_code').addClass('null-input-border');
        }
      }
    },

    addTag(value) {
      this.tag[this.counter] = value;
      this.counter += 1;
    },

    marginTag() {
      this.margin_tag_list = [];

      return Object.values(this.tag).filter((word) => word !== '');
    },

    toStringTag(list) {
      this.backoffice_tag = '';

      // eslint-disable-next-line no-plusplus
      for (let i = 0; i < list.length; i++) {
        if (i !== (list.length - 1)) {
          this.backoffice_tag += (`${list[i]},`);
        } else {
          this.backoffice_tag += list[i];
        }
      }

      $('#real-input-tag').val(this.backoffice_tag);
    },

    createHashTag(e) {
      const targetTag = e.target.value;

      if ((targetTag !== '' || targetTag !== ' ') && targetTag.length !== 0) {
        const result = Object.values(this.tag).filter((word) => word === targetTag);

        // 해시태그 중복 확인
        if (result.length === 0 && this.margin_tag_list.length < 5) {
          this.addTag(targetTag.trim());
          this.margin_tag_list = this.marginTag();
          this.toStringTag(this.margin_tag_list);
          this.tagValue = '';
        } else if (this.margin_tag_list.length >= 5) {
          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('해시태그는 최대 5개 입니다.');
          this.tagValue = '';
        } else if (this.margin_tag_list.includes(targetTag)) {
          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('중복된 해시태그 입니다.');
          this.tagValue = '';
        }
      }
      e.preventDefault();
    },

    deleteTag(e) {
      // const index = e.target.getAttribute('idx');
      const index = e.target.getAttribute('tag');
      for (let i = 0; i < this.margin_tag_list.length; i++) {
        if (index === this.margin_tag_list[i]) {
          const key = Object.keys(this.tag).filter((key) => this.tag[key] === index)[0];
          delete this.tag[key];
        }
      }
      // console.log('index :', index);

      // this.tag.splice(index, 1);
      console.log(this.tag);
      // this.tag[index] = '';
      this.margin_tag_list = this.marginTag();
      this.toStringTag(this.margin_tag_list);
      // console.log(this.tag.indexOf(index));
    },

    timer(check) {
      let minute = 1;
      let seconds = 60;

      if (check === 'true') {
        clearInterval(this.time);
        $('#btn-certification').val('인증 완료');
        return;
      }

      this.time = setInterval(() => {
        seconds -= 1;

        // eslint-disable-next-line no-useless-concat
        if (seconds <= 9) $('#btn-certification').val(`0${minute} : ` + `0${seconds}`);
        else $('#btn-certification').val(`0${minute} : ${seconds}`);

        if (seconds === 0) {
          if (minute !== 0) {
            minute = 1 - minute;
            seconds = 60;
          } else {
            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').html('이메일 인증 시간을 초과했습니다.<br>다시 시도해주세요.');

            $('#btn-certification').prop('check', false);
            $('#btn-certification').val('이메일 입력');
            $('#backoffice_email').val('');
            $('#backoffice_email').attr('readonly', false);
            $('#backoffice_email').removeClass('readOnly');

            $('#btn-check-certification').prop('check', false);
            $('#btn-check-certification').val('인증번호 확인');
            $('#auth_code').val('');
            $('#auth_code').attr('readonly', false);
            $('#auth_code').removeClass('readOnly');

            clearInterval(this.time);
          }
        }
      }, 1000);
    },

    disableTimepicker(event) {
      console.log(event.target.checked);
      const { checked } = event.target;

      if (checked === true) {
        $('#sat_stime').attr('disabled', true);
        $('#sat_etime').find('.dp__pointer').attr('disabled', true);
      }
    },

    // ************
    // DAUM ZIPCODE
    // ************
    execDaumPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          const roadAddr = data.roadAddress; // 도로명 주소 변수
          // eslint-disable-next-line camelcase
          const auto_roadAddr = data.autoRoadAddress; // 도로명 주소 변수
          let extraRoadAddr = ''; // 참고 항목 변수

          // 법정동명이 있을 경우 추가한다. (법정리는 제외)
          // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
          if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          // 건물명이 있고, 공동주택일 경우 추가한다.
          if (data.buildingName !== '' && data.apartment === 'Y') {
            extraRoadAddr += (extraRoadAddr !== '' ? `, ${data.buildingName}` : data.buildingName);
          }

          console.log(roadAddr);

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          // $('#zipcode').val(data.zonecode);
          this.zipcode = data.zonecode;

          if (roadAddr.length > 0) {
            // $('#roadname_address').val(roadAddr);
            this.roadname_address = roadAddr;
          } else {
            // $('#roadname_address').val(auto_roadAddr);
            // eslint-disable-next-line camelcase
            this.roadname_address = auto_roadAddr;
          }

          // $('#number_address').val(data.jibunAddress);
          this.number_address = data.jibunAddress;
        },
      }).open();
    },

    // **************
    // TAG
    // **************

    // 운영 시간 formatter
    timeFormatter: (time) => {
      if (time === undefined) {
        return '09:00';
      }

      let h = time.hours;
      const m = time.minutes;

      let t = '';
      if (h < 10) {
        h = `0${h}`;
      }

      t = `${h}:${m}0`;

      return t;
    },

    // 사진 업로드
    uploadImage() {
      this.img_name = '';
      const { length } = $('#multipartFile_room').get(0).files;

      if (length < 11) {
        for (let i = 0; i < length; i++) {
          const { type } = $('#multipartFile_room').get(0).files[i];
          if (type === 'image/jpeg' || type === 'image/jpg' || type === 'image/png') {
            this.img_name += $('#multipartFile_room').get(0).files[i].name;
            if (i !== length - 1) {
              this.img_name += ', ';
            }
          } else {
            // file 선택 값 초기화를 위한 코드 (타입을 바꿨다 돌아옴)
            $('#multipartFile_room').attr('type', 'radio');
            $('#multipartFile_room').attr('type', 'file');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('jpg, jpeg, png 확장자만 선택가능합니다.');
          }
        }

        $('.upload-name').val(this.img_name);
      } else {
        // file 선택 값 초기화를 위한 코드 (타입을 바꿨다 돌아옴)
        $('#multipartFile_room').attr('type', 'radio');
        $('#multipartFile_room').attr('type', 'file');

        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('최대 10개의 이미지 선택이 가능합니다.');
      }
    },

    clickTypeCheckbox(e) {
      const checkType = e.target.id;

      if (checkType === 'type_checkbox_desk' || checkType === 'type_checkbox_meeting_room') {
        $('#type_checkbox_office').attr('disabled', true);
        $('#type_checkbox_office').siblings('label').css('text-decoration', 'line-through');

        if (!$('#type_checkbox_desk').is(':checked') && !$('#type_checkbox_meeting_room').is(':checked')) {
          $('#type_checkbox_office').attr('disabled', false);
          $('#type_checkbox_office').siblings('label').css('text-decoration', 'none');
        }
      } else if (checkType === 'type_checkbox_office') {
        $('#type_checkbox_desk').attr('disabled', true);
        $('#type_checkbox_desk').siblings('label').css('text-decoration', 'line-through');
        $('#type_checkbox_meeting_room').attr('disabled', true);
        $('#type_checkbox_meeting_room').siblings('label').css('text-decoration', 'line-through');

        if (!$('#type_checkbox_office').is(':checked')) {
          $('#type_checkbox_desk').attr('disabled', false);
          $('#type_checkbox_desk').siblings('label').css('text-decoration', 'none');
          $('#type_checkbox_meeting_room').attr('disabled', false);
          $('#type_checkbox_meeting_room').siblings('label').css('text-decoration', 'none');
        }
      }
    },

    // ******************
    // 신청
    // ******************
    submit() {
      console.log('---------------');
      console.log($('#multipartFile_room').get(0).files);
      console.log('---------------');
      const sun_stime = this.timeFormatter(this.sunStime);
      const sun_etime = this.timeFormatter(this.sunEtime);
      const mon_stime = this.timeFormatter(this.monStime);
      const mon_etime = this.timeFormatter(this.monEtime);
      const tue_stime = this.timeFormatter(this.tueStime);
      const tue_etime = this.timeFormatter(this.tueEtime);
      const wed_stime = this.timeFormatter(this.wedStime);
      const wed_etime = this.timeFormatter(this.wedEtime);
      const thu_stime = this.timeFormatter(this.thuStime);
      const thu_etime = this.timeFormatter(this.thuEtime);
      const fri_stime = this.timeFormatter(this.friStime);
      const fri_etime = this.timeFormatter(this.friEtime);
      const sat_stime = this.timeFormatter(this.satStime);
      const sat_etime = this.timeFormatter(this.satEtime);

      console.log(this.owner_name);
      console.log(this.backoffice_id);
      console.log(this.backoffice_name);
      console.log(this.company_name);
      console.log(this.backoffice_tel);
      console.log(this.backoffice_email);
      console.log(this.zipcode);
      console.log(this.roadname_address);
      console.log(this.number_address);
      console.log(this.detail_address);
      console.log(this.backoffice_info);

      console.log(this.backoffice_tag);
      console.log(this.backoffice_info);

      let stringBackofficeType = '';
      for (let i = 0; i < this.backoffice_type.length; i++) {
        if (i !== this.backoffice_type.length - 1) {
          stringBackofficeType += (`${this.backoffice_type[i]},`);
        } else {
          stringBackofficeType += this.backoffice_type[i];
        }
      }

      let stringBackofficeOption = '';
      for (let i = 0; i < this.backoffice_option.length; i++) {
        if (i !== this.backoffice_option.length - 1) {
          stringBackofficeOption += (`${this.backoffice_option[i]},`);
        } else {
          stringBackofficeOption += this.backoffice_option[i];
        }
      }

      let stringBackofficeAround = '';
      for (let i = 0; i < this.backoffice_around.length; i++) {
        if (i !== this.backoffice_around.length - 1) {
          stringBackofficeAround += (`${this.backoffice_around[i]},`);
        } else {
          stringBackofficeAround += this.backoffice_around[i];
        }
      }

      console.log(this.backoffice_type);
      console.log(stringBackofficeType);
      console.log(this.backoffice_option);
      console.log(stringBackofficeOption);
      console.log(this.backoffice_around);
      console.log(stringBackofficeAround);

      console.log(sun_stime, sun_etime, this.sun_dayoff);
      console.log(mon_stime, mon_etime, this.mon_dayoff);
      console.log(tue_stime, tue_etime, this.tue_dayoff);
      console.log(wed_stime, wed_etime, this.wed_dayoff);
      console.log(thu_stime, thu_etime, this.thu_dayoff);
      console.log(fri_stime, fri_etime, this.fri_dayoff);
      console.log(sat_stime, sat_etime, this.sat_dayoff);

      console.log(this.img_name);

      // 1. 필수 input / textarea 입력되었는지 확인
      if (
        $('#owner_name').val().trim().length > 0 && $('#backoffice_id').val().trim().length > 0
        && $('#backoffice_name').val().trim().length > 0 && $('#company_name').val().trim().length > 0
        && $('#backoffice_tel').val().trim().length > 0 && $('#backoffice_email').val().trim().length > 0
        && $('#auth_code').val().trim().length > 0 && $('#zipcode').val().trim().length > 0
        && $('#backoffice_info').val().trim().length > 0
      ) {
        // 2. 정규표현식을 모두 만족하는지 확인
        if ($('.warning-text:eq(0)').hasClass('blind') && $('.warning-text:eq(1)').hasClass('blind')) {
          // 3. 이메일 인증 완료 되었는지 확인
          if ($('#btn-certification').prop('check') && $('#btn-check-certification').prop('check')) {
            // 4. 공간 타입을 선택했는지 확인
            const desk_checked = $('#type_checkbox_desk').is(':checked');
            const meeting_room_checked = $('#type_checkbox_meeting_room').is(':checked');
            const office_checked = $('#type_checkbox_office').is(':checked');

            if (desk_checked || meeting_room_checked || office_checked) {
              if (this.submit_flag) {
                this.submit_flag = false;

                const imageTag = $('#multipartFile_room').get(0).files;
                const formData = new FormData();

                formData.append('owner_name', this.owner_name);
                formData.append('backoffice_id', this.backoffice_id);
                formData.append('backoffice_name', this.backoffice_name);
                formData.append('company_name', this.company_name);
                formData.append('backoffice_tel', this.backoffice_tel);
                formData.append('backoffice_email', this.backoffice_email);

                formData.append('zipcode', this.zipcode);
                formData.append('roadname_address', this.roadname_address);
                formData.append('number_address', this.number_address);
                formData.append('detail_address', this.detail_address);
                formData.append('backoffice_tag', this.backoffice_tag);
                formData.append('backoffice_info', this.backoffice_info);
                formData.append('backoffice_option', this.backoffice_option);
                formData.append('backoffice_around', this.backoffice_around);
                formData.append('backoffice_image', this.img_name);
                formData.append('backoffice_type', stringBackofficeType);

                formData.append('mon_stime', mon_stime);
                formData.append('mon_etime', mon_etime);
                formData.append('tue_stime', tue_stime);
                formData.append('tue_etime', tue_etime);
                formData.append('wed_stime', wed_stime);
                formData.append('wed_etime', wed_etime);
                formData.append('thu_stime', thu_stime);
                formData.append('thu_etime', thu_etime);
                formData.append('fri_stime', fri_stime);
                formData.append('fri_etime', fri_etime);
                formData.append('sat_stime', sat_stime);
                formData.append('sat_etime', sat_etime);
                formData.append('sun_stime', sun_stime);
                formData.append('sun_etime', sun_etime);

                formData.append('mon_dayoff', this.mon_dayoff);
                formData.append('tue_dayoff', this.tue_dayoff);
                formData.append('wed_dayoff', this.wed_dayoff);
                formData.append('thu_dayoff', this.thu_dayoff);
                formData.append('fri_dayoff', this.fri_dayoff);
                formData.append('sat_dayoff', this.sat_dayoff);
                formData.append('sun_dayoff', this.sun_dayoff);

                for (let i = 0; i < imageTag.length; i++) {
                  console.log(imageTag[i]);
                  formData.append('multipartFile_room', imageTag[i]);
                }

                formData.append('multipartFile_host', '');

                // console.log('@@@@@@@');
                // for (let key of formData.entries()) {
                //   console.log(`${key}`);
                // }
                // console.log('@@@@@@@');

                axios.post('http://localhost:8800/backoffice/insertOK', formData, {
                  headers: {
                    'Content-Type': 'multipart/form-data',
                  },
                })
                  .then((res) => {
                    if (res.data.result === '1') {
                      console.log('가입 성공');
                      this.$router.replace('/backoffice/landing');
                    } else {
                      console.log('가입 실패');
                    }
                  });
              }
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('공간 타입을 선택해주세요.');
            }
          } else {
            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('이메일 인증을 완료해주세요.');
          }
        }
      } else {
        if ($('#owner_name').val().trim().length === 0) {
          $('#owner_name').addClass('null-input-border');
        }
        if ($('#backoffice_id').val().trim().length === 0) {
          $('#backoffice_id').addClass('null-input-border');
        }
        if ($('#backoffice_name').val().trim().length === 0) {
          $('#backoffice_name').addClass('null-input-border');
        }
        if ($('#company_name').val().trim().length === 0) {
          $('#company_name').addClass('null-input-border');
        }
        if ($('#backoffice_tel').val().trim().length === 0) {
          $('#backoffice_tel').addClass('null-input-border');
        }
        if ($('#backoffice_email').val().trim().length === 0) {
          $('#backoffice_email').addClass('null-input-border');
        }
        if ($('#auth_code').val().trim().length === 0) {
          $('#auth_code').addClass('null-input-border');
        }
        if ($('#zipcode').val().trim().length === 0) {
          $('#zipcode').addClass('null-input-border');
          $('#roadname_address').addClass('null-input-border');
          $('#number_address').addClass('null-input-border');
        }
        if ($('#backoffice_info').val().trim().length === 0) {
          $('#backoffice_info').addClass('null-input-border');
        }
      }
    },
  },
};
</script>

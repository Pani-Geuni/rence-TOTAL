<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable vuejs-accessibility/label-has-for -->
<!-- eslint-disable max-len -->
<template>
  <div class="host-update" th:fragment="dashboard_content">
    <section class="board-contentWrap host-update">
      <div class="titleSection">
        <h1>호스트 정보 변경</h1>
      </div>
      <!-- END titleSection -->
      <div id="updateForm">
        <div class="host-update-form">
          <input type="hidden" id="backoffice_no" name="backoffice_no" v-model="backoffice_no" />
          <div class="inputWrap info">
            <p>사업체 태그</p>
            <div class="hashTag-area">
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
          <!-- 공간 태그 -->
          <div class="inputWrap info">
            <p>사업체 소개</p>
            <div class="check_wrap">
              <textarea id="backoffice_info" name="backoffice_info" v-model="backoffice_info"
                placeholder="공간 소개를 입력해 주세요"> </textarea>
              <div class="b_info_txt_length_wrap">
                <span class="b_info_txt_length">0</span>
                <span>&nbsp;/ 500</span>
              </div>
            </div>
          </div>

          <div class="inputWrap backoffice_type">
            <p>공간 타입</p>
            <div class="option-group-column">
              <div class="option-group-row"
                th:with="type = ${vo.backoffice_type}, isExist = ${vo.backoffice_type != null}">
                <div class="option-item">
                  <input type="checkbox" id="type_checkbox_desk" v-model="backoffice_type" class="checkbox"
                    name="backoffice_type" value="desk" @click="clickTypeCheckbox" readonly />
                  <label>데스크</label>
                </div>
                <!-- END option-item -->
                <div class="option-item">
                  <input type="checkbox" id="type_checkbox_meeting_room" class="checkbox" v-model="backoffice_type"
                    name="backoffice_type" value="meeting_room" @click="clickTypeCheckbox" readonly />
                  <label>회의실</label>
                </div>
                <!-- END option-item -->
                <div class="option-item">
                  <input type="checkbox" id="type_checkbox_office" class="checkbox" name="backoffice_type"
                    v-model="backoffice_type" value="office" @click="clickTypeCheckbox" readonly />
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
            <div class="option-group-column"
              th:with="tag = ${vo.backoffice_option}, isExist = ${vo.backoffice_option != null}">
              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="chair-desk" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="chair-desk" readonly /> <label>의자/테이블</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="internet-wifi" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="internet-wifi" /> <label>인터넷/Wi-Fi</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="restroom" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="restroom" />
                  <label>내부 화장실</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="no-smoking" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="no-smoking" />
                  <label>금연</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="smoking-room" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="smoking-room" /> <label>흡연실</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="terrace-rooftop" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="terrace-rooftop" /> <label>테라스/루프탑</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="tv-projector" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="tv-projector" /> <label>TV/프로젝터</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="whiteboard" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="whiteboard" />
                  <label>화이트보드</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="sound-microphone" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="sound-microphone" /> <label>음향/마이크</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="parking" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="parking" />
                  <label>주차</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="pc-laptop" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="pc-laptop" />
                  <label>PC/노트북</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="printer" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="printer" />
                  <label>복사/인쇄기</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="lounge" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="lounge" />
                  <label>공용 라운지</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="kitchen" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="kitchen" />
                  <label>공용 주방</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="water-purifier" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="water-purifier" /> <label>정수기</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="can-food" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="can-food" />
                  <label>음식물 반입 가능</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="can-drink" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="can-drink" />
                  <label>주류 반입 가능</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="air-conditioner" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="air-conditioner" /> <label>에어컨</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="fitting-room" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="fitting-room" /> <label>탈의실</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="shower" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="shower" />
                  <label>샤워시설</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="body-mirror" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="body-mirror" />
                  <label>전신거울</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="door-lock" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="door-lock" />
                  <label>도어락</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="outlet-multitap" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="outlet-multitap" /> <label>콘센트/멀티탭</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="personal-locker" class="checkbox" name="backoffice_option"
                    v-model="backoffice_option" value="personal-locker" /> <label>개인락커</label>
                </div>
              </div>
              <!-- END option-group row -->
              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="fax" class="checkbox" name="backoffice_option" v-model="backoffice_option"
                    value="fax" />
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
            <div class="option-group-column"
              th:with="around = ${vo.backoffice_around} , isExist = ${vo.backoffice_around != null}">
              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="public-parking" class="checkbox" name="backoffice_around"
                    v-model="backoffice_around" value="public-parking" /> <label>공영주차장</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="paid-parking" class="checkbox" name="backoffice_around"
                    v-model="backoffice_around" value="paid-parking" /> <label>유료주차장</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="pharmacy" class="checkbox" name="backoffice_around"
                    v-model="backoffice_around" value="pharmacy" />
                  <label>약국</label>
                </div>
              </div>
              <!-- END option-group row -->

              <div class="option-group-row">
                <div class="option-item">
                  <input type="checkbox" id="hospital" class="checkbox" name="backoffice_around"
                    v-model="backoffice_around" value="hospital" />
                  <label>병원</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="convenience-store" class="checkbox" name="backoffice_around"
                    v-model="backoffice_around" value="convenience-store" /> <label>편의점</label>
                </div>

                <div class="option-item">
                  <input type="checkbox" id="cafe" class="checkbox" name="backoffice_around" v-model="backoffice_around"
                    value="cafe" />
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
            <div class="custom-table update">
              <div class="custom-table-heading update">
                <div class="table-head-cell update">
                  <p>요일</p>
                </div>
                <div class="table-head-cell update">
                  <p>오픈 시간</p>
                </div>
                <div class="table-head-cell update">
                  <p>마감 시간</p>
                </div>
                <div class="table-head-cell update">
                  <p>휴무일 체크</p>
                </div>
              </div>
              <!-- END custom table heading -->

              <div class="custom-table-body update">
                <div class="table-body-row update">
                  <div class="table-body-cell update">일</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="sunStime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="sun_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="sunEtime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="sun_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <input type="checkbox" id="sun_dayoff" class="checkbox" name="sun_dayoff" v-model="sun_dayoff"
                      true-value="T" false-value="F" />
                  </div>
                </div>
                <!-- END table body row -->

                <div class="table-body-row update">
                  <div class="table-body-cell update">월</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="monStime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="mon_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="monEtime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="mon_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <input type="checkbox" id="mon_dayoff" class="checkbox" name="mon_dayoff" v-model="mon_dayoff"
                      true-value="T" false-value="F" />
                  </div>
                </div>
                <!-- END table body row -->

                <div class="table-body-row update">
                  <div class="table-body-cell update">화</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="tueStime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="tue_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="tueEtime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="tue_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <input type="checkbox" id="tue_dayoff" class="checkbox" name="tue_dayoff" v-model="tue_dayoff"
                      true-value="T" false-value="F" />
                  </div>
                </div>
                <!-- END table body row -->

                <div class="table-body-row update">
                  <div class="table-body-cell update">수</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="wedStime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="wed_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="wedEtime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="wed_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <input type="checkbox" id="wed_dayoff" class="checkbox" name="wed_dayoff" v-model="wed_dayoff"
                      true-value="T" false-value="F" />
                  </div>
                </div>
                <!-- END table body row -->

                <div class="table-body-row update">
                  <div class="table-body-cell update">목</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="thuStime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="thu_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="thuEtime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="thu_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <input type="checkbox" id="thu_dayoff" class="checkbox" name="thu_dayoff" v-model="thu_dayoff"
                      true-value="T" false-value="F" />
                  </div>
                </div>
                <!-- END table body row -->

                <div class="table-body-row update">
                  <div class="table-body-cell update">금</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="friStime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="fri_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="friEtime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="fri_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <input type="checkbox" id="fri_dayoff" class="checkbox" name="fri_dayoff" v-model="fri_dayoff"
                      true-value="T" false-value="F" />
                  </div>
                </div>
                <!-- END table body row -->

                <div class="table-body-row update">
                  <div class="table-body-cell update">토</div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="satStime" id="sat_stime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="sat_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
                    <Datepicker v-model="satEtime" id="sat_etime" time-picker :start-time="startTime" mode-height="160"
                      minutes-increment="60" minutes-grid-increment="60" placeholder="09:00"
                      :disabled="sat_dayoff === 'T'"></Datepicker>
                  </div>
                  <div class="table-body-cell update">
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
              <input class="upload-name" v-model="vo.backoffice_image" placeholder="첨부파일(.jpg/.jpeg/.png/.jfif)" />
              <label for="multipartFile_room">파일찾기</label>
              <input type="file" id="multipartFile_room" @change="uploadImage" name="multipartFile_room"
                accept=".jpg, .jpeg, .png, .jfif" multiple="multiple" readonly />
            </div>
            <input type="file" id="multipartFile_host" name="multipartFile_host" style="display: none;" />
          </div>
          <!-- END inputWrap image -->

          <div class="submit">
            <input @click="submit" type="button" id="submit" class="submit-application" value="정보변경하기" />
            <input type="submit" id="real-submit" class="submit-application" value="정보변경하기" style="display:none;" />
          </div>
        </div>
      </div>
      <!-- END host-update-form -->
    </section>

  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/dash-board/dash-host-update.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';
import { ref } from 'vue';

export default {
  name: 'UpdateHostView',

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
      backoffice_no: this.$cookies.get('backoffice_no'),

      vo: [],
      ovo: [],

      backoffice_type: [],
      backoffice_option: [],
      backoffice_around: [],
      backoffice_info: '',

      tagValue: '',
      tag: {},
      counter: 0,
      margin_tag_list: [],
      backoffice_tag: '',
      img_name: '',

      sun_dayoff: 'F',
      mon_dayoff: 'F',
      tue_dayoff: 'F',
      wed_dayoff: 'F',
      thu_dayoff: 'F',
      fri_dayoff: 'F',
      sat_dayoff: 'F',
    };
  },

  methods: {
    getHostInfo() {
      console.log(this.backoffice_no);
      axios.get('http://localhost:8800/backoffice/dash/update_host', {
        params: {
          backoffice_no: this.backoffice_no,
        },
      })
        .then((res) => {
          console.log(res.data);
          this.vo = res.data.vo;
          this.ovo = res.data.ovo;
          this.backoffice_tag = res.data.backoffice_tag;
          this.backoffice_info = res.data.vo.backoffice_info;
          this.backoffice_type = res.data.vo.backoffice_type.split(',');
          this.backoffice_option = res.data.vo.backoffice_option.split(',');
          this.backoffice_around = res.data.vo.backoffice_around.split(',');

          // 해시태그
          let arr = [];
          if (this.backoffice_tag !== undefined) {
            arr = this.backoffice_tag.split(',');
          }

          this.margin_tag_list = arr;
          // eslint-disable-next-line no-plusplus
          for (let i = 0; i < arr.length; i++) {
            this.tagValue = arr[i];

            this.addTag(this.tagValue);
            this.margin_tag_list = this.marginTag();
            this.tagValue = '';
          }

          if (arr[0] === '') {
            arr.pop();
          }

          // 운영 시간
          this.sun_dayoff = res.data.ovo.sun_dayoff;
          this.mon_dayoff = res.data.ovo.mon_dayoff;
          this.tue_dayoff = res.data.ovo.tue_dayoff;
          this.wed_dayoff = res.data.ovo.wed_dayoff;
          this.thu_dayoff = res.data.ovo.thu_dayoff;
          this.fri_dayoff = res.data.ovo.fri_dayoff;
          this.sat_dayoff = res.data.ovo.sat_dayoff;

          this.img_name = res.data.vo.backoffice_image;
        });
    },

    addTag(value) {
      this.tag[this.counter] = value;
      this.counter += 1;
    },

    marginTag() {
      // this.margin_tag_list = [];

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
      // eslint-disable-next-line no-plusplus
      for (let i = 0; i < this.margin_tag_list.length; i++) {
        if (index === this.margin_tag_list[i]) {
          const key = Object.keys(this.tag).filter((key) => this.tag[key] === index)[0];
          delete this.tag[key];
        }
      }
      console.log(this.tag);
      this.margin_tag_list = this.marginTag();
      this.toStringTag(this.margin_tag_list);;
    },

    clickTypeCheckbox(e) {
      const checkType = e.target.id;

      if (checkType === 'type_checkbox_desk' || checkType === 'type_checkbox_meeting_room') {
        if (this.backoffice_type.includes('office')) {
          this.backoffice_type = [];
          if (this.backoffice_type.includes('desk')) {
            this.backoffice_type.push('desk');
          }
          if (this.backoffice_type.includes('meeting_room')) {
            this.backoffice_type.push('meeting_room');
          }
        }
        $('#type_checkbox_office').attr('disabled', true);
        $('#type_checkbox_office').siblings('label').css('text-decoration', 'line-through');

        if (!$('#type_checkbox_desk').is(':checked') && !$('#type_checkbox_meeting_room').is(':checked')) {
          $('#type_checkbox_office').attr('disabled', false);
          $('#type_checkbox_office').siblings('label').css('text-decoration', 'none');
        }
      } else if (checkType === 'type_checkbox_office') {
        if (this.backoffice_type.includes('desk')
          || this.backoffice_type.includes('meeting_room')) {
          this.backoffice_type = ['office'];
        }

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

    submit() {
      console.log('update submit');
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

      const imageTag = $('#multipartFile_room').get(0).files;
      const formData = new FormData();

      formData.append('backoffice_no', this.backoffice_no);
      formData.append('backoffice_tag', this.backoffice_tag);
      formData.append('backoffice_info', this.backoffice_info);
      formData.append('backoffice_type', stringBackofficeType);
      formData.append('backoffice_option', stringBackofficeOption);
      formData.append('backoffice_around', stringBackofficeAround);

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

      formData.append('backoffice_image', this.img_name);
      // for (let i = 0; i < imageTag.length; i++) {
      //   console.log(this.imageTag[i]);
      //   formData.append('multipartFile_room', imageTag[i]);
      // }

      console.log(this.backoffice_tag);
      console.log(this.backoffice_info);
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
      console.log(this.backoffice_image);

      axios.post('http://localhost:8800/backoffice/dash/updateOK_host', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
        .then((res) => {
          if (res.data.result === '1') {
            console.log('변경 성공');
            this.$router.replace(`/backoffice/dash/main?backoffice_no=${this.$cookies.get('backoffice_no')}`);
          } else {
            console.log('변경 실패');
          }
        });
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.getHostInfo();
    });
  },
};
</script>

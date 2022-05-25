// 월간 데이터 파싱
var _monthlyLength = $('#monthly_data_length').val(); // 데이터 길이
var _monthlyLocalDateArr = []; // 월간 날짜
var _monthlyMdnIncArr = []; // 누적 모돈 수 배열
var _monthlyEkpIncArr = []; // 누적 출하두 수 배열
var _monthlyTtlIncArr = []; // 누적 데이터 건 수 배열

var _reMtMdnIncArr = []; // 증감 모돈 수 배열
var _reMtEkpIncArr = []; // 증감 출하두 수 배열
var _reMtTtlIncArr = []; // 증감 데이터 건 수 배열

var _reMtMdnIncStrParsing = []; // 증감 모돈 수 배열
var _reMtEkpIncStrParsing = []; // 증감 출하두 수 배열
var _reMtTtlIncStrParsing = []; // 증감 데이터 건 수 배열

//차트 데이터 셋
monthDataInit();
function monthDataInit(){
    //초기화
    _monthlyLength = $('#monthly_data_length').val(); // 데이터 길이
    _monthlyLocalDateArr = []; // 월간 날짜
    _monthlyMdnIncArr = []; // 누적 모돈 수 배열

    // 누적 데이터 배열 그래프 수치 반복 초기화
    for (var i = 0; i < _monthlyLength; i++) {
    	var dateConv = new Date($('#monthly_local_date' + i).text());
    	_monthlyLocalDateArr[i] = dateFormat(dateConv, "monthly");
    	_monthlyMdnIncArr[i] = $('#monthly_modon_increment' + i).text();
    	_monthlyEkpIncArr[i] = $('#monthly_ekape_increment' + i).text();
    	_monthlyTtlIncArr[i] = $('#monthly_total_increment' + i).text();

        _reMtMdnIncArr[i] = $('#re_monthly_modon_increment'+i).text();
        _reMtEkpIncArr[i] = $('#re_monthly_ekape_increment'+i).text();
        _reMtTtlIncArr[i] = $('#re_monthly_total_increment'+i).text();

        _reMtMdnIncStrParsing[i] = _monthlyMdnIncArr[i] + _reMtMdnIncArr[i];
        _reMtEkpIncStrParsing[i] = _monthlyEkpIncArr[i] + _reMtEkpIncArr[i];
        _reMtTtlIncStrParsing[i] = _monthlyTtlIncArr[i] + _reMtTtlIncArr[i];
    }


}

//차트 구현부
monthMdnChartDraw(); //월간 누적 모돈 수 차트 구현
monthEkpChartDraw(); //월간 누적 출하두 수 차트 구현
monthTtlChartDraw(); //월간 누적 데이터 건 수 차트 구현

//월간 누적 모돈 수
function monthMdnChartDraw(){
    const dataMdnInc = {
        labels: _monthlyLocalDateArr,
        datasets: [{
            label:"월간 누적 모돈 수",
            data: _monthlyMdnIncArr,
            backgroundColor:'rgb(244, 143, 177,0.5)',
            borderColor:'rgb(249, 81, 81 )',
            fill:'start',
        }]
    };

    const configMdnInc = {
      type: 'line',
      data: dataMdnInc,
      options: {
        responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reMtMdnIncStrParsing[tooltipItem.parsed.x].replace(_reMtMdnIncArr[tooltipItem.parsed.x], "");
                        nwc = numberWithCommas(Math.floor(nwc));
                        var i = _reMtMdnIncStrParsing[tooltipItem.parsed.x].replace(_reMtMdnIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reMtMdnIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_monthlyMdnIncArr[tooltipItem.parsed.x], nwc);
                        return i;
                    }
                }
            },
            filler:{
            propagate: false,
            },
          },
       },
    };

    var contextMdnInc = document
        .getElementById('month_modon_increment')
        .getContext('2d');
    var myChart = new Chart(contextMdnInc, configMdnInc);
}




//월간 출하두수 표
function monthEkpChartDraw(){
    const dataEkpInc = {
    	labels: _monthlyLocalDateArr,
    	datasets: [{
    		label:"월간 누적 출하두 수",
    		data: _monthlyEkpIncArr,
    		backgroundColor:'rgb(100, 208, 255,0.5)',
    		borderColor:'rgb(72, 72, 255 )',
    		fill:'start',
    	}]
    };

    const configEkpInc = {
      type: 'line',
      data: dataEkpInc,
      options: {
        responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reMtEkpIncStrParsing[tooltipItem.parsed.x].replace(_reMtEkpIncArr[tooltipItem.parsed.x], "");
                        nwc = numberWithCommas(Math.floor(nwc));
                        var i = _reMtEkpIncStrParsing[tooltipItem.parsed.x].replace(_reMtEkpIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reMtEkpIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_monthlyEkpIncArr[tooltipItem.parsed.x], nwc);
                        return i;
                    }
                }
            },
        	filler:{
    		propagate: false,
    		},
          },
       },
    };
    var contextEkpInc = document
    	.getElementById('month_ekape_increment')
    	.getContext('2d');
    var myChart = new Chart(contextEkpInc, configEkpInc);
}


//월간 누적 데이터 건 수 표
function monthTtlChartDraw(){
    const dataTtlInc = {
    	labels: _monthlyLocalDateArr,
    	datasets: [{
    		label:"월간 누적 데이터 건 수",
    		data: _monthlyTtlIncArr,
    		backgroundColor:'rgb(255, 196, 147,0.5)',
    		borderColor:'rgb(255, 160, 99 )',
    		fill:'start',
    	}]
    };

    const configTtlInc = {
      type: 'line',
      data: dataTtlInc,
      options: {
        responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reMtTtlIncStrParsing[tooltipItem.parsed.x].replace(_reMtTtlIncArr[tooltipItem.parsed.x], "");
                        nwc = numberWithCommas(Math.floor(nwc));
                        var i = _reMtTtlIncStrParsing[tooltipItem.parsed.x].replace(_reMtTtlIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reMtTtlIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_monthlyTtlIncArr[tooltipItem.parsed.x], nwc);
                        return i;
                    }
                }
            },
        	filler:{
    		propagate: false,
    		},
          },
       },
    };

    var contextTtlInc = document
    	.getElementById('month_total_increment')
    	.getContext('2d');
    var myChart = new Chart(contextTtlInc, configTtlInc);
}


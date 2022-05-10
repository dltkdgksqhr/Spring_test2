// 일간 데이터 파싱
var _dailyLength = $('#daily_data_length').val(); // 데이터 길이
var _dailyLocalDateArr = []; // 일간 날짜
var _dailyMdnIncArr = []; // 누적 모돈 수 배열
var _dailyEkpIncArr = []; // 누적 출하두 수 배열
var _dailyTtlIncArr = []; // 누적 데이터 건 수 배열
var _reDaMdnIncArr = []; // 증감 모돈 수 배열
var _reDaEkpIncArr = []; // 증감 출하두 수 배열
var _reDaTtlIncArr = []; // 증감 데이터 건 수 배열

var _reDaMdnIncStrParsing = []; // 증감 모돈 수 배열
var _reDaEkpIncStrParsing = []; // 증감 출하두 수 배열
var _reDaTtlIncStrParsing = []; // 증감 데이터 건 수 배열

var _dailyMdnIncAvg, _dailyEkpIncAvg, _dailyTtlIncAvg; // 증감 평균값
_dailyMdnIncAvg = _dailyEkpIncAvg = _dailyTtlIncAvg = 0;

for(var i=0; i<_dailyLength; i++){ // 누적 데이터 배열 그래프 수치 반복 초기화
    var dateConv = new Date($('#date_daily_inc'+i).text());
    _dailyLocalDateArr[i] = dateFormat(dateConv, "day");
    _dailyMdnIncArr[i] = $('#daily_modon_increment'+i).text();
    _dailyEkpIncArr[i] = $('#daily_ekape_increment'+i).text();
    _dailyTtlIncArr[i] = $('#daily_total_increment'+i).text();

    _reDaMdnIncArr[i] = $('#re_daily_modon_increment'+i).text();
    _reDaEkpIncArr[i] = $('#re_daily_ekape_increment'+i).text();
    _reDaTtlIncArr[i] = $('#re_daily_total_increment'+i).text();

    _reDaMdnIncStrParsing[i] = _dailyMdnIncArr[i] + _reDaMdnIncArr[i];
    _reDaEkpIncStrParsing[i] = _dailyEkpIncArr[i] + _reDaEkpIncArr[i];
    _reDaTtlIncStrParsing[i] = _dailyTtlIncArr[i] + _reDaTtlIncArr[i];
    // 증감치 평균값 구하기
    _dailyMdnIncAvg += parseInt($('#re_daily_modon_increment'+i).text());
    _dailyEkpIncAvg += parseInt($('#re_daily_ekape_increment'+i).text());
    _dailyTtlIncAvg += parseInt($('#re_daily_total_increment'+i).text());
}
const labelsDay = _dailyLocalDateArr;
//일간 누적 모돈 수 차트 구현
const dataDay = {
    labels: labelsDay,
    datasets: [{
        label: '일간 누적 모돈 수',
        data: _dailyMdnIncArr,
        borderColor: 'rgb(255, 0, 0)',
        backgroundColor: 'rgb(255, 0, 0)',
    }]
};

const configDay = {
    type: 'bar',
    data: dataDay,
    options: {
        responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reDaMdnIncStrParsing[tooltipItem.parsed.x].replace(_reDaMdnIncArr[tooltipItem.parsed.x], "");

                        nwc = numberWithCommas(Math.floor(nwc));
                        i = _reDaMdnIncStrParsing[tooltipItem.parsed.x]
                            .replace(_reDaMdnIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reDaMdnIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_dailyMdnIncArr[tooltipItem.parsed.x], nwc);
                        return i;
                    }
                }
            },
            legend: {
                position: 'top',
            },
        },
        scales: {
            y: {
                axis: 'y',
                min: Math.floor(_dailyMdnIncArr[0] * (incAvg(_dailyMdnIncAvg) / incAvg(_dailyMdnIncAvg) / incAvg(_dailyMdnIncAvg))) * incAvg(_dailyMdnIncAvg),
            }
        },
    },
};

var contextDayMdnInc = document
    .getElementById('daily_modon_increment')
    .getContext('2d');
var myChart = new Chart(contextDayMdnInc, configDay);

//일간 누적 출하두 수 차트 구현
const dataEkpDay = {
    labels: labelsDay,
    datasets: [{
        label: '일간 누적 출하두 수',
        data: _dailyEkpIncArr,
        borderColor: 'rgb(0, 0,0)',
        backgroundColor: 'rgb(0, 0, 255)',
    }]
};

const configEkpDay = {
    type: 'bar',
    data: dataEkpDay,
    options: {
        responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reDaEkpIncStrParsing[tooltipItem.parsed.x].replace(_reDaEkpIncArr[tooltipItem.parsed.x], "");
                        nwc = numberWithCommas(Math.floor(nwc));
                        var i = _reDaEkpIncStrParsing[tooltipItem.parsed.x].replace(_reDaEkpIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reDaEkpIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_dailyEkpIncArr[tooltipItem.parsed.x], nwc);
                        return i;
                    }
                }
            },
            legend: {
            position: 'top',
            },
        },
        scales: {
            y: {
                axis: 'y',
                min: Math.floor(_dailyEkpIncArr[0] * (incAvg(_dailyEkpIncAvg) / incAvg(_dailyEkpIncAvg) / incAvg(_dailyEkpIncAvg))) * incAvg(_dailyEkpIncAvg)
            }
        }
    },
};

var contextDayEkpInc = document
    .getElementById('daily_ekape_increment')
    .getContext('2d');
var myChart = new Chart(contextDayEkpInc, configEkpDay);



//일간 누적 데이터 건 수 차트 구현
const dataTtlDay = {
    labels: labelsDay,
    datasets: [{
        label: '일간 누적 데이터 건 수',
        data: _dailyTtlIncArr,
        borderColor: 'rgb(0, 0, 0)',
        backgroundColor: 'rgb(255, 128, 0)',
    }]
};

const configTtlDay = {
    type: 'bar',
    data: dataTtlDay,
    options: {
        responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reDaTtlIncStrParsing[tooltipItem.parsed.x].replace(_reDaTtlIncArr[tooltipItem.parsed.x], "");
                        nwc = numberWithCommas(Math.floor(nwc));
                        var i = _reDaTtlIncStrParsing[tooltipItem.parsed.x].replace(_reDaTtlIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reDaTtlIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_dailyTtlIncArr[tooltipItem.parsed.x], nwc);
                        return i;
                    }
                }
            },
            legend: {
                position: 'top',
            },
        },
        scales: {
            y: {
                axis: 'y',
                min: Math.floor(_dailyTtlIncArr[0] * (incAvg(_dailyTtlIncAvg) / incAvg(_dailyTtlIncAvg) / incAvg(_dailyTtlIncAvg))) * incAvg(_dailyTtlIncAvg)
            }
        }
    },
};

var contextDayTtlInc = document
    .getElementById('daily_total_increment')
    .getContext('2d');
var myChart = new Chart(contextDayTtlInc, configTtlDay);


function incAvg(incAvg){
    var avg = incAvg / _dailyLength;
    var result;
    if(avg > 99 && avg < 1000){
        result = 1000;
    }else if(avg > 999 && avg < 10000){
        result = 10000;
    }else if(avg > 9999 && avg < 100000){
        result = 100000;
    }else if(avg > 99999 && avg < 1000000){
        result = 1000000;
    }else{
        result = avg * 10;
    }
    return result;
}
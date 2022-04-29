//날짜 포맷 수정 yyyy-mm
function dateFormat(date) {
    let day = date.getMonth() + 1;
    month = month >= 10 ? month : '0' + month;
    return date.getFullYear() + '-' + month;
}

// 월간 데이터 파싱
var _monthlyLength = $('#monthly_data_length').val(); // 데이터 길이
var _monthlyLocalDateArr = []; // 월간 날짜
var _monthlyMdnIncArr = []; // 누적 모돈 수 배열
var _monthlyEkpIncArr = []; // 누적 출하두 수 배열
var _monthlyTtlIncArr = []; // 누적 데이터 건 수 배열
// 누적 데이터 배열 그래프 수치 반복 초기화
for(var i=0; i<_monthlyLength; i++){
    var dateConv = new Date($('#monthly_local_date'+i).text());
    _monthlyLocalDateArr[i] = dateFormat(dateConv);
    _monthlyMdnIncArr[i] = $('#monthly_modon_increment'+i).text();
    _monthlyEkpIncArr[i] = $('#monthly_ekape_increment'+i).text();
    _monthlyTtlIncArr[i] = $('#monthly_total_increment'+i).text();
}

//_monthlyLocalDateArr[2] = "2022-05";
//_monthlyLocalDateArr[3] = "2022-06";
//_monthlyLocalDateArr[4] = "2022-07";
//_monthlyLocalDateArr[5] = "2022-08";
//_monthlyLocalDateArr[6] = "2022-09";
//_monthlyLocalDateArr[7] = "2022-10";
//
//_monthlyMdnIncArr[2] = 2700000;
//_monthlyMdnIncArr[3] = 2701000;
//_monthlyMdnIncArr[4] = 2710000;
//_monthlyMdnIncArr[5] = 2720000;
//_monthlyMdnIncArr[6] = 2730000;
//_monthlyMdnIncArr[7] = 2750000;
//월간 누적 모돈 수 차트 구현
const dataMdnInc = {
    labels: _monthlyLocalDateArr,
    datasets: [{
            label: 'Dataset',
            data: _monthlyMdnIncArr,
            borderColor: chartColors.red,
            fill: false,
            stepped: true,
        }]
};
const configMdnInc = {
    type: 'line',
    data: dataMdnInc,
    options: {
        responsive: true,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
            title: {
                display: true,
                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
            }
        }
    }
};

var contextMdnInc = document
    .getElementById('month_modon_increment')
    .getContext('2d');
var myChart = new Chart(contextMdnInc, configMdnInc);

//월간 누적 출하두 수 차트 구현
const dataEkpInc = {
    labels: _monthlyLocalDateArr,
    datasets: [{
            label: 'Dataset',
            data: _monthlyEkpIncArr,
            borderColor: chartColors.blue,
            fill: false,
            stepped: true,
        }]
};
const configEkpInc = {
    type: 'line',
    data: dataEkpInc,
    options: {
        responsive: true,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
            title: {
                display: true,
                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
            }
        }
    }
};

var contextEkpInc = document
    .getElementById('month_ekape_increment')
    .getContext('2d');
var myChart = new Chart(contextEkpInc, configEkpInc);

//월간 누적 데이터 건 수 차트 구현
const dataTtlInc = {
    labels: _monthlyLocalDateArr,
    datasets: [{
            label: 'Dataset',
            data: _monthlyTtlIncArr,
            borderColor: chartColors.green,
            fill: false,
            stepped: true,
        }]
};
const configTtlInc = {
    type: 'line',
    data: dataTtlInc,
    options: {
        responsive: true,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
            title: {
                display: true,
                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
            }
        }
    }
};

var contextTtlInc = document
    .getElementById('month_total_increment')
    .getContext('2d');
var myChart = new Chart(contextTtlInc, configTtlInc);




//
//var monthly_modon_increment = new Chart(monthlyMdnInclineChart, {
//    type: 'line',
//    data: {
//        labels: labels,
//        datasets: [{
//          label: 'Dataset',
//          data: Utils.numbers({count: 6, min: -100, max: 100}),
//          borderColor: Utils.CHART_COLORS.red,
//          fill: false,
//          stepped: true,
//        }]
//    },
//  options: {
//    responsive: true,
//    interaction: {
//      intersect: false,
//      axis: 'x'
//    },
//    plugins: {
//      title: {
//        display: true,
//        text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
//      }
//    }
//  }
//});
//월간 누적 출하두 수
//var _dailyMdnIncArr = [];
//for(var i=0; i<10; i++){
//    _dailyMdnIncArr[i] = $('#re_daily_ekape_increment'+i).text();
//}
//
//var lineChart = document.getElementById('month_ekape_increment').getContext('2d');
//var week_total_increment = new Chart(lineChart, {
//    type: 'line',
//    data: {
//        labels: _dateDailyMdnIncArr,
//        datasets: [{
//            label: '# of Votes',
//            data: _dailyMdnIncArr,
//            backgroundColor: 'rgba(255, 99, 132, 0.2)',
//            borderColor: 'rgba(255, 99, 132, 1)',
//            borderWidth: 1
//        }]
//    },
//    options: {
//		responsive:false,
//        scales: {
//            y: {
//                beginAtZero: true
//            }
//        }
//    }
//});
////월간 누적 데이터 건수
//var _dailyMdnIncArr = [];
//for(var i=0; i<10; i++){
//    _dailyMdnIncArr[i] = $('#re_daily_total_increment'+i).text();
//}
//
//var lineChart = document.getElementById('month_total_increment').getContext('2d');
//var week_total_increment = new Chart(lineChart, {
//    type: 'line',
//    data: {
//        labels: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'],
//        datasets: [{
//            label: '# of Votes',
//            data: _dailyMdnIncArr,
//            backgroundColor: 'rgba(255, 99, 132, 0.2)',
//            borderColor: 'rgba(255, 99, 132, 1)',
//            borderWidth: 1
//        }]
//    },
//    options: {
//		responsive:false,
//        scales: {
//            y: {
//                beginAtZero: true
//            }
//        }
//    }
//});
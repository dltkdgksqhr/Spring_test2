// 월간 데이터 파싱
var _monthlyLength = $('#monthly_data_length').val(); // 데이터 길이
var _monthlyLocalDateArr = []; // 월간 날짜
var _monthlyMdnIncArr = []; // 누적 모돈 수 배열
var _monthlyEkpIncArr = []; // 누적 출하두 수 배열
var _monthlyTtlIncArr = []; // 누적 데이터 건 수 배열
// 누적 데이터 배열 그래프 수치 반복 초기화
for(var i=0; i<_monthlyLength; i++){
    var dateConv = new Date($('#monthly_local_date'+i).text());
    _monthlyLocalDateArr[i] = dateFormat(dateConv, "monthly");
    _monthlyMdnIncArr[i] = $('#monthly_modon_increment'+i).text();
    _monthlyEkpIncArr[i] = $('#monthly_ekape_increment'+i).text();
    _monthlyTtlIncArr[i] = $('#monthly_total_increment'+i).text();
}
//월간 누적 모돈 수 차트 구현
const dataMdnInc = {
    labels: _monthlyLocalDateArr,
    datasets: [{
            label: '월간 누적 모돈수 표',
            data: _monthlyMdnIncArr,
            backgroundColor:'rgb(255, 0, 0)',
            borderColor: 'rgb(255, 0, 0)',
            fill: false,
            stepped: true,
        }]
};
const configMdnInc = {
    type: 'line',
    data: dataMdnInc,
    options: {
        responsive: false,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
//            title: {
//                display: true,
//                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
//            }
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
            label: '월간 누적 출하두수 표',
            data: _monthlyEkpIncArr,
             backgroundColor:'rgb(0, 0, 255)',
            borderColor: 'rgb(0, 0, 255)',
            fill: false,
            stepped: true,
        }]
};
const configEkpInc = {
    type: 'line',
    data: dataEkpInc,
    options: {
        responsive: false,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
//            title: {
//                display: true,
////                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
//            }
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
            label: '월간 누적 데이터건수 표',
            data: _monthlyTtlIncArr,
            backgroundColor: 'rgb(0, 255, 0)',
            borderColor: 'rgb(0, 255, 0)',
            fill: false,
            stepped: true,
        }]
};
const configTtlInc = {
    type: 'line',
    data: dataTtlInc,
    options: {
        responsive: false,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
//            title: {
//                display: true,
////                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
//            }
        }
    }
};

var contextTtlInc = document
    .getElementById('month_total_increment')
    .getContext('2d');
var myChart = new Chart(contextTtlInc, configTtlInc);

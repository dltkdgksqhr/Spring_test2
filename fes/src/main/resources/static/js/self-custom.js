

var chartArea = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(chartArea, {
    type: 'bar',
    data: {
        labels: ['전전일', '전일'],
        datasets: [{
            label: '# of Votes',
            data: [12, 19],
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});


var dateTest = new Date($('#date_daily_inc0').text());
function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let sevenDays = date.getDay();
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;
    return month+'-'+day+'('+week[sevenDays]+')';
}

console.log(dateFormat(dateTest))

var _dailyMdnIncArr = [];
var _dateDailyMdnIncArr = [];
for(var i=0; i<8; i++){
    _dailyMdnIncArr[i] = $('#daily_modon_increment'+i).text();
    var tmp = new Date($('#date_daily_inc'+i).text());
    _dateDailyMdnIncArr[i] = dateFormat(tmp);
}

var _beforeNnowDailyMdnIncArr = [];
_beforeNnowDailyMdnIncArr[0] = $('#date_daily_inc5').text();
_beforeNnowDailyMdnIncArr[1] = $('#date_daily_inc6').text();

var lineChart = document.getElementById('myChart2').getContext('2d');
var myChart2 = new Chart(lineChart, {
    type: 'line',
    data: {
        labels: _dateDailyMdnIncArr,
        datasets: [{
            label: '# of Votes',
            data: _dailyMdnIncArr,
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

//
//const config = {
//  type: 'line',
//  data: data,
//  options: {
//    responsive: true,
//    plugins: {
//      title: {
//        display: true,
//        text: (ctx) => 'Point Style: ' + ctx.chart.data.datasets[0].pointStyle,
//      }
//    }
//  }
//};
//
//const data = {
//  labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6'],
//  datasets: [
//    {
//      label: 'Dataset',
//      data: Utils.numbers({count: 6, min: -100, max: 100}),
//      borderColor: Utils.CHART_COLORS.red,
//      backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
//      pointStyle: 'circle',
//      pointRadius: 10,
//      pointHoverRadius: 15
//    }
//  ]
//};
//

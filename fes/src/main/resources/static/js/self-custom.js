

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

var _dailyMdnIncArr = [];
//_dailyMdnIncArr[0] = $('#daily_modon_increment'+0).text();
for(var i=0; i<10; i++){
    _dailyMdnIncArr[i] = $('#daily_modon_increment'+i).text();
}

var lineChart = document.getElementById('myChart2').getContext('2d');
var myChart2 = new Chart(lineChart, {
    type: 'line',
    data: {
        labels: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'],
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

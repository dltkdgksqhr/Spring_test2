//월간 누적 모돈 수
var _monthlyMdnIncArr = [];
for(var i=0; i<$('#monthly_data_length').value(); i++){
    _monthlyMdnIncArr[i] = $('#monthly_modon_increment'+i).text();
}
var monthlyMdnInclineChart = document.getElementById('month_modon_increment').getContext('2d');
var monthly_modon_increment = new Chart(monthlyMdnInclineChart, {
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
		responsive:false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
//월간 누적 출하두 수
var _dailyMdnIncArr = [];
for(var i=0; i<10; i++){
    _dailyMdnIncArr[i] = $('#re_daily_ekape_increment'+i).text();
}

var lineChart = document.getElementById('month_ekape_increment').getContext('2d');
var week_total_increment = new Chart(lineChart, {
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
		responsive:false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
//월간 누적 데이터 건수
var _dailyMdnIncArr = [];
for(var i=0; i<10; i++){
    _dailyMdnIncArr[i] = $('#re_daily_total_increment'+i).text();
}

var lineChart = document.getElementById('month_total_increment').getContext('2d');
var week_total_increment = new Chart(lineChart, {
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
		responsive:false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
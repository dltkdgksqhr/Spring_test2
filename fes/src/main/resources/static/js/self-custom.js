
//주간 누적 모돈 수
var _dailyMdnIncArr = [];
for (var i = 0; i < 10; i++) {
	_dailyMdnIncArr[i] = $('#daily_modon_increment' + i).text();
}
var _dailyMdnIncArr2 = [];
for (var i = 0; i < 10; i++) {
	_dailyMdnIncArr2[i] = $('#date_daily_inc' + i).text();
}
/*alert(_dailyMdnIncArr[i]);*/

var lineChart = document.getElementById('week_modon_increment').getContext('2d');
var lineChart2 = new Chart(lineChart, {
	type: 'line',
	data: {
		labels: ['1', '2', '3', '4', '5', '6', '7'],
		datasets: [{
			label: '# of Votes',
			data: _dailyMdnIncArr,
			backgroundColor: 'rgba(255, 99, 132, 0.2)',
			borderColor: 'rgba(255, 99, 132, 1)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		scales: {
			yAxes: {
				beginAtZero: true
			}
		}
	}
});
//주간 누적 출하두 수 
var _dailyMdnIncArr = [];
//_dailyMdnIncArr[0] = $('#daily_modon_increment'+0).text();
for (var i = 0; i < 10; i++) {
	_dailyMdnIncArr[i] = $('#daily_ekape_increment' + i).text();
}

var lineChart = document.getElementById('week_ekape_increment').getContext('2d');
var week_ekape_increment = new Chart(lineChart, {
	type: 'line',
	data: {
		labels: ['1', '2', '3', '4', '5', '6', '7'],
		datasets: [{
			label: '# of Votes',
			data: _dailyMdnIncArr,
			backgroundColor: 'rgba(255, 99, 132, 0.2)',
			borderColor: 'rgba(255, 99, 132, 1)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		scales: {
			y: {
				beginAtZero: true
			}
		}
	}
});
//주간 누적 데이터 건 수
var _dailyMdnIncArr = [];
for (var i = 0; i < 10; i++) {
	_dailyMdnIncArr[i] = $('#daily_total_increment' + i).text();
}

var lineChart = document.getElementById('week_total_increment').getContext('2d');
var week_total_increment = new Chart(lineChart, {
	type: 'line',
	data: {
		labels: ['1', '2', '3', '4', '5', '6', '7'],
		datasets: [{
			label: '# of Votes',
			data: _dailyMdnIncArr,
			backgroundColor: 'rgba(255, 99, 132, 0.2)',
			borderColor: 'rgba(255, 99, 132, 1)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		scales: {
			y: {
				beginAtZero: true
			}
		}
	}
});
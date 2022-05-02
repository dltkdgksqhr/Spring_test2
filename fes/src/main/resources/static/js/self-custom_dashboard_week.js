// 주간 데이터 파싱
var _weekLength = $('#week_data_length').val(); // 데이터 길이
var _weekLocalDateArr = []; // 월간 날짜
var _weekMdnIncArr = []; // 누적 모돈 수 배열
var _weekEkpIncArr = []; // 누적 출하두 수 배열
var _weekTtlIncArr = []; // 누적 데이터 건 수 배열
// 누적 데이터 배열 그래프 수치 반복 초기화
for(var i=0; i<_weekLength; i++){
    var dateConv = new Date($('#week_local_date'+i).text());
    _weekLocalDateArr[i] = dateFormat(dateConv, "week");
    _weekMdnIncArr[i] = $('#week_modon_increment'+i).text();
    _weekEkpIncArr[i] = $('#week_ekape_increment'+i).text();
    _weekTtlIncArr[i] = $('#week_total_increment'+i).text();
}

//주간 누적 모돈 수 차트 구현
var lineChart = document.getElementById('week_modon_increment').getContext('2d');
var lineChart2 = new Chart(lineChart, {
	type: 'line',
	data: {
		labels: _weekLocalDateArr,
		datasets: [{
			label: '주간 누적 모돈수 표',
			data: _weekMdnIncArr,
			backgroundColor: 'rgb(255, 0, 0)',
			borderColor: 'rgb(255, 0, 0)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		scales: {
			y: {
			}
		}
	}
});

//주간 누적 출하두 수 차트 구현
var lineChart = document.getElementById('week_ekape_increment').getContext('2d');
var lineChart2 = new Chart(lineChart, {
	type: 'line',
	data: {
		labels: _weekLocalDateArr,
		datasets: [{
			label: '주간 누적 출하두수 표',
			data: _weekEkpIncArr,
			backgroundColor: 'rgb(0, 0, 255)',
			borderColor: 'rgb(0, 0, 255)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		scales: {
			y: {
			}
		}
	}
});
//주간 누적 데이터 건 수 차트 구현
var lineChart = document.getElementById('week_total_increment').getContext('2d');
var lineChart2 = new Chart(lineChart, {
	type: 'line',
	data: {
		labels: _weekLocalDateArr,
		datasets: [{
			label: '주간 누적 데이터건수 표',
			data: _weekTtlIncArr,
			backgroundColor: 'rgb(0, 255, 0)',
			borderColor: 'rgb(0, 255, 0)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		scales: {
			y: {
			}
		}
	}
});
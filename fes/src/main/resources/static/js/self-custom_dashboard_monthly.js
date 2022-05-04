// 월간 데이터 파싱
var _monthlyLength = $('#monthly_data_length').val(); // 데이터 길이
var _monthlyLocalDateArr = []; // 월간 날짜
var _monthlyMdnIncArr = []; // 누적 모돈 수 배열
var _monthlyEkpIncArr = []; // 누적 출하두 수 배열
var _monthlyTtlIncArr = []; // 누적 데이터 건 수 배열
// 누적 데이터 배열 그래프 수치 반복 초기화
for (var i = 0; i < _monthlyLength; i++) {
	var dateConv = new Date($('#monthly_local_date' + i).text());
	_monthlyLocalDateArr[i] = dateFormat(dateConv, "monthly");
	_monthlyMdnIncArr[i] = $('#monthly_modon_increment' + i).text();
	_monthlyEkpIncArr[i] = $('#monthly_ekape_increment' + i).text();
	_monthlyTtlIncArr[i] = $('#monthly_total_increment' + i).text();
}


//월간 누적 모돈 수
const dataMdnInc = {
	labels: _monthlyLocalDateArr,
	datasets: [{
		data: _monthlyMdnIncArr,
		backgroundColor:[chartColors.red,chartColors.orange,chartColors.yellow,chartColors.green,chartColors.blue,chartColors.purple,chartColors.brown],
		borderColor:'rgb(0,0,0)',
	}]
};

const configMdnInc = {
  type: 'pie',
  data: dataMdnInc,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: '월간 누적 모돈수 표'
      }
    }
  },
};
var contextMdnInc = document
	.getElementById('month_modon_increment')
	.getContext('2d');
var myChart = new Chart(contextMdnInc, configMdnInc);



//월간 누적 출하두수
const dataEkpInc = {
	labels: _monthlyLocalDateArr,
	datasets: [{
		data: _monthlyEkpIncArr,
		backgroundColor:[chartColors.red,chartColors.orange,chartColors.yellow,chartColors.green,chartColors.blue,chartColors.purple,chartColors.grey],
		borderColor:'rgb(0,0,0)',
	}]
};

const configEkpInc = {
  type: 'pie',
  data: dataEkpInc,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: '월간 누적 출하두수 표'
      }
    }
  },
};
var contextEkpInc = document
	.getElementById('month_ekape_increment')
	.getContext('2d');
var myChart = new Chart(contextEkpInc, configEkpInc);

//월간 누적 데이터건수 
const dataTtlInc = {
	labels: _monthlyLocalDateArr,
	datasets: [{
		data: _monthlyTtlIncArr,
		backgroundColor:[chartColors.red,chartColors.orange,chartColors.yellow,chartColors.green,chartColors.blue,chartColors.purple,chartColors.grey],
		borderColor:'rgb(0,0,0)',
	}]
};

const configTtlInc = {
  type: 'pie',
  data: dataTtlInc,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: '월간 누적 데이터건 수 표'
      }
    }
  },
};
var contextTtlInc = document
	.getElementById('month_total_increment')
	.getContext('2d');
var myChart = new Chart(contextTtlInc, configTtlInc);

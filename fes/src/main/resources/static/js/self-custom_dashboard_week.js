// 주간 데이터 파싱
var _weekLength = $('#week_data_length').val(); // 데이터 길이
var _weekLocalDateArr = []; // 월간 날짜
var _weekMdnIncArr = []; // 누적 모돈 수 배열
var _weekEkpIncArr = []; // 누적 출하두 수 배열
var _weekTtlIncArr = []; // 누적 데이터 건 수 배열

var _reWeMdnIncArr = []; // 증감 모돈 수 배열
var _reWeEkpIncArr = []; // 증감 출하두 수 배열
var _reWeTtlIncArr = []; // 증감 데이터 건 수 배열

var _reWeMdnIncStrParsing = []; // 증감 모돈 수 배열
var _reWeEkpIncStrParsing = []; // 증감 출하두 수 배열
var _reWeTtlIncStrParsing = []; // 증감 데이터 건 수 배열

// 누적 데이터 배열 그래프 수치 반복 초기화
for(var i=0; i<_weekLength; i++){
    var dateConv = new Date($('#week_local_date'+i).text());
    _weekLocalDateArr[i] = dateFormat(dateConv, "week");
    _weekMdnIncArr[i] = $('#week_modon_increment'+i).text();
    _weekEkpIncArr[i] = $('#week_ekape_increment'+i).text();
    _weekTtlIncArr[i] = $('#week_total_increment'+i).text();

    _reWeMdnIncArr[i] = $('#re_week_modon_increment'+i).text();
    _reWeEkpIncArr[i] = $('#re_week_ekape_increment'+i).text();
    _reWeTtlIncArr[i] = $('#re_week_total_increment'+i).text();

    _reWeMdnIncStrParsing[i] = _weekMdnIncArr[i] + _reWeMdnIncArr[i];
    _reWeEkpIncStrParsing[i] = _weekEkpIncArr[i] + _reWeEkpIncArr[i];
    _reWeTtlIncStrParsing[i] = _weekTtlIncArr[i] + _reWeTtlIncArr[i];
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
			borderColor: 'rgb(0, 0, 0)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
		        plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem, data) { //그래프 콤마
                                var a = "(+전일 증감치 : ";
                                var b = ")";
                                var nwc = _reWeMdnIncStrParsing[tooltipItem.parsed.x].replace(_reWeMdnIncArr[tooltipItem.parsed.x], "");

                                nwc = numberWithCommas(Math.floor(nwc));
                                i = _reWeMdnIncStrParsing[tooltipItem.parsed.x]
                                    .replace(_reWeMdnIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reWeMdnIncArr[tooltipItem.parsed.x])) + b);
                                i = i.replace(_weekMdnIncArr[tooltipItem.parsed.x], nwc);
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
			borderColor: 'rgb(0, 0, 0)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reWeEkpIncStrParsing[tooltipItem.parsed.x].replace(_reWeEkpIncArr[tooltipItem.parsed.x], "");

                        nwc = numberWithCommas(Math.floor(nwc));
                        i = _reWeEkpIncStrParsing[tooltipItem.parsed.x]
                            .replace(_reWeEkpIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reWeEkpIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_weekEkpIncArr[tooltipItem.parsed.x], nwc);
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
			backgroundColor: 'rgb(255, 128, 0)',
			borderColor: 'rgb(0, 0, 0)',
			borderWidth: 1
		}]
	},
	options: {
		responsive: false,
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem, data) { //그래프 콤마
                        var a = "(+전일 증감치 : ";
                        var b = ")";
                        var nwc = _reWeTtlIncStrParsing[tooltipItem.parsed.x].replace(_reWeTtlIncArr[tooltipItem.parsed.x], "");

                        nwc = numberWithCommas(Math.floor(nwc));
                        i = _reWeTtlIncStrParsing[tooltipItem.parsed.x]
                            .replace(_reWeTtlIncArr[tooltipItem.parsed.x], a + numberWithCommas(Math.floor(_reWeTtlIncArr[tooltipItem.parsed.x])) + b);
                        i = i.replace(_weekTtlIncArr[tooltipItem.parsed.x], nwc);
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
			}
		}
	}
});
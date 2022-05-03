	$('#chartSearch').on(
			"click",
			function() {
				var gc= $('#GraphChoice').val();		
				var startDate = $('#FirstDate').val();
				var endDate = $('#SecondDate').val();
				if (startDate === "") {
					alert("시작 날짜를 선택해주세요.");
					return false;
				} else if (endDate === "") {
					alert("종료 날짜를 선택해주세요.");
					return false;
				}
				location.href = '/fes/search?gcCondition=' + gc
						+ '&startCondition=' + startDate + '&endCondition='
						+ endDate;
			});

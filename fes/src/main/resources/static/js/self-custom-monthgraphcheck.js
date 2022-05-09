	$('#chartSearchM').on("click",function(){
    var gc = $(this).prev().prev().prev().prev().val();
    var startDate = $(this).prev().prev().prev().val();
    var endDate = $(this).prev().val();
    chartSearch(gc, startDate, endDate);
});
function chartSearch(gc, startDate, endDate){
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
}
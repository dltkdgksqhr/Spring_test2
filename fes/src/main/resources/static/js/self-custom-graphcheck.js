$('#chartSearchD').on("click",function() {
    var gc = $(this).prev().prev().prev().prev().val();
    var startDate = $(this).prev().prev().prev().val();;
    var endDate = $(this).prev().val();;
    chartSearch(gc, startDate, endDate);
});

//날짜조정
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; 
var yyyy = today.getFullYear();
if(dd<10){
  dd='0'+dd
} 
if(mm<10){
  mm='0'+mm
} 
today = yyyy+'-'+mm+'-'+dd;
document.getElementsByClassName('firstdate')[0].setAttribute("min", "2022-03-25");
document.getElementsByClassName('firstdate')[0].setAttribute("max", today);
document.getElementsByClassName("seconddate")[0].setAttribute("min", "2022-03-25");
document.getElementsByClassName("seconddate")[0].setAttribute("max", today);

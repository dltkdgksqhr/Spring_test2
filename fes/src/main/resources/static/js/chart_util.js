var _dmiTotalDataTop = $('#dmiTotal').text();
var _deiTotalDataTop = $('#deiTotal').text();
var _dtiTotalDataTop = $('#dtiTotal').text();

_dmiTotalDataTop = Math.floor(_dmiTotalDataTop);
_deiTotalDataTop = Math.floor(_deiTotalDataTop);
_dtiTotalDataTop = Math.floor(_dtiTotalDataTop);

console.log(_dmiTotalDataTop);
console.log(_deiTotalDataTop);
console.log(_dtiTotalDataTop);
totalDataTop(_dmiTotalDataTop, "dmiTotal");
totalDataTop(_deiTotalDataTop, "deiTotal");
totalDataTop(_dtiTotalDataTop, "dtiTotal");

function totalDataTop(data, selectorStr){
    $({ val : 0 }).animate({ val : data }, {
      duration: 1500,
      step: function() {
        var num = numberWithCommas(Math.floor(this.val));
        $("#"+selectorStr).text(num);
      },
      complete: function() {
        var num = numberWithCommas(Math.floor(this.val));
        $("#"+selectorStr).text(num);
      }
    });
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

var _____WB$wombat$assign$function_____ = function(name) {return (self._wb_wombat && self._wb_wombat.local_init && self._wb_wombat.local_init(name)) || self[name]; };
if (!self.__WB_pmw) { self.__WB_pmw = function(obj) { this.__WB_source = obj; return this; } }
{
    let window = _____WB$wombat$assign$function_____("window");
    let self = _____WB$wombat$assign$function_____("self");
    let document = _____WB$wombat$assign$function_____("document");
    let location = _____WB$wombat$assign$function_____("location");
    let top = _____WB$wombat$assign$function_____("top");
    let parent = _____WB$wombat$assign$function_____("parent");
    let frames = _____WB$wombat$assign$function_____("frames");
    let opener = _____WB$wombat$assign$function_____("opener");

    //'use strict';

    window.chartColors = {
        red: 'rgb(255, 0, 0)',
        orange: 'rgb(255, 128, 0)',
        yellow: 'rgb(255, 255, 0)',
        green: 'rgb(0, 255, 0)',
        blue: 'rgb(0, 0, 255)',
        purple: 'rgb(155, 0, 255)',
        brown:'rgb(133,78,20)'
    };

    (function(global) {
        var MONTHS = [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'July',
            'August',
            'September',
            'October',
            'November',
            'December'
        ];

        var COLORS = [
            '#4dc9f6',
            '#f67019',
            '#f53794',
            '#537bc4',
            '#acc236',
            '#166a8f',
            '#00a950',
            '#58595b',
            '#8549ba'
        ];

        var Samples = global.Samples || (global.Samples = {});
        var Color = global.Color;

        Samples.utils = {
            // Adapted from http://indiegamr.com/generate-repeatable-random-numbers-in-js/
            srand: function(seed) {
                this._seed = seed;
            },

            rand: function(min, max) {
                var seed = this._seed;
                min = min === undefined ? 0 : min;
                max = max === undefined ? 1 : max;
                this._seed = (seed * 9301 + 49297) % 233280;
                return min + (this._seed / 233280) * (max - min);
            },

            numbers: function(config) {
                var cfg = config || {};
                var min = cfg.min || 0;
                var max = cfg.max || 1;
                var from = cfg.from || [];
                var count = cfg.count || 8;
                var decimals = cfg.decimals || 8;
                var continuity = cfg.continuity || 1;
                var dfactor = Math.pow(10, decimals) || 0;
                var data = [];
                var i, value;

                for (i = 0; i < count; ++i) {
                    value = (from[i] || 0) + this.rand(min, max);
                    if (this.rand() <= continuity) {
                        data.push(Math.round(dfactor * value) / dfactor);
                    } else {
                        data.push(null);
                    }
                }

                return data;
            },

            labels: function(config) {
                var cfg = config || {};
                var min = cfg.min || 0;
                var max = cfg.max || 100;
                var count = cfg.count || 8;
                var step = (max - min) / count;
                var decimals = cfg.decimals || 8;
                var dfactor = Math.pow(10, decimals) || 0;
                var prefix = cfg.prefix || '';
                var values = [];
                var i;

                for (i = min; i < max; i += step) {
                    values.push(prefix + Math.round(dfactor * i) / dfactor);
                }

                return values;
            },

            months: function(config) {
                var cfg = config || {};
                var count = cfg.count || 12;
                var section = cfg.section;
                var values = [];
                var i, value;

                for (i = 0; i < count; ++i) {
                    value = MONTHS[Math.ceil(i) % 12];
                    values.push(value.substring(0, section));
                }

                return values;
            },

            color: function(index) {
                return COLORS[index % COLORS.length];
            },

            transparentize: function(color, opacity) {
                var alpha = opacity === undefined ? 0.5 : 1 - opacity;
                return Color(color).alpha(alpha).rgbString();
            }
        };

    // DEPRECATED
    window.randomScalingFactor = function() {
        return Math.round(Samples.utils.rand(-100, 100));
    };

    // INITIALIZATION

    Samples.utils.srand(Date.now());

    // Google Analytics
    /* eslint-disable */
    if (document.location.hostname.match(/^(www\.)?chartjs\.org$/)) {
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//web.archive.org/web/20210317115424/https://www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-28909194-3', 'auto');
        ga('send', 'pageview');
    }
    /* eslint-enable */

    }(this));


}

/*
2022-05-02
theo
*/
//?????? ?????? ??????
function dateFormat(date, typeStr) {
    let result = "";
    if(typeStr === "monthly"){ // ?????? : yyyy-mm
        let month = date.getMonth() + 1;
        month = month >= 10 ? month : '0' + month;
        result = date.getFullYear() + '-' + month;
    }else if(typeStr === "day"){  // ?????? : mm-dd
        let day = date.getDate();
        let month = date.getMonth() + 1;
        day = day >= 10 ? day : '0' + day;
        month = month >= 10 ? month : '0' + month;
        result = month+'-'+day;


    }else if(typeStr === "week"){ // ?????? : mm-dd
		let day = date.getDate();
   		let month = date.getMonth() + 1;
  		day = day >= 10 ? day : '0' + day;
   		month = month >= 10 ? month : '0' + month;
    	result = month+'-'+day;
}
    return result;
}


function chartSearch(gc, startDate, endDate){
    if (startDate === "") {
        alert("?????? ????????? ??????????????????.");
        return false;
    } else if (endDate === "") {
        alert("?????? ????????? ??????????????????.");
        return false;
    }
    location.href = '/fes/search?gcCondition=' + gc
            + '&startCondition=' + startDate + '&endCondition='
            + endDate;
}
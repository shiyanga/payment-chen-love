$.fn.extend({
    spaceDatePicker: function (space) {
        /*if (space && typeof(space) == 'object') {
         space = $.extend( {}, $.spaceDatePicker.defaults, space );
         }

         this.each(function() {
         new $.spaceDatePicker(this, space, arg );
         });
         return;*/

        var $picker = $(this);
        $picker.datepicker({
            format: DATE_FORMAT,
            todayBtn: "linked",
            clearBtn: true,
            language: "ch",
            inline: true,
            multidate: true,
            todayHighlight: true
        }).on('changeMonth', function (ev) {
            setTimeout(function () {
                console.log('changeMonth:' + JSON.stringify(ev));
                console.log('dateRange:' + JSON.stringify(getMinMaxDateRange($picker)));
                addCustomInformation($picker, space, getMinMaxDateRange($picker));
            }, 0);
        }).on('changeDate', function (ev) {
            setTimeout(function () {
                // alert(ev.ctrlKey);
                console.log('changeDate:' + JSON.stringify(ev));
                console.log('dateRange:' + JSON.stringify(getMinMaxDateRange($picker)));
                addCustomInformation($picker, space, getMinMaxDateRange($picker));

                var dates = $picker.data('datepicker').getDates();
                //按shift且选中两天，则选中这个区间中所以日期
                if (shifted && dates && dates.length == 2) {
                    var selectedDates = [];
                    for (var iterDate = dates[0]; iterDate <= dates[1]; iterDate = iterDate.addDays(1)) {
                        selectedDates.push(iterDate.Format(DATE_FORMAT));
                    }

                    // ECMAScript6才支持该操作
                    //$picker.data('datepicker').update(...selectedDates);

                    $picker.data('datepicker').update.apply($picker.data('datepicker'), selectedDates);
                    shifted = false;
                }
            }, 0);

        }).on('show', function (ev) {
            //alert('sfs');
        });
        //$picker.find('table.table-condensed').css('width:500px;');
        console.log('dateRange:' + JSON.stringify(getMinMaxDateRange($picker)));
        addCustomInformation($picker, space, getMinMaxDateRange($picker));

        /*$('#btn').click(function () {
         var value = $picker.data('datepicker').getDates();
         console.log(JSON.stringify(value));
         });*/
        return this;

    },
    selectedDates: function () {
        //var value = $("#datepicker").data('date');//.datepicker("getDate");
        return $(this).data('datepicker').getDates();
        //console.log(JSON.stringify(value));
    },
    updateSpaceInfo: function (space) {
        addCustomInformation($(this), space, getMinMaxDateRange($(this)));
    }
});

/*$.spaceDatePicker = function (elmt, options, arg) {
 if (options && typeof(options) == 'string') {
 if (options == 'updateSpaceInfo') {
 updateSpaceInfo( arg );
 }
 }

 function updateSpaceInfo(arg)
 {
 addCustomInformation( $(this), space, getMinMaxDateRange( $(this)));
 }
 };

 $.spaceDatePicker.defaults = {
 };*/


var DATE_FORMAT = "yyyy-MM-dd";

/*function getSelectedDates() {
 //var value = $("#datepicker").data('date');//.datepicker("getDate");
 var value = $(this).data('datepicker').getDates();
 console.log(JSON.stringify(value));
 }*/
var shifted = false;


/*$(document).on('keyup', function (e) {
 if (e.shiftKey) {
 shifted = false;
 }
 });*/
$(document).on('keyup keydown', function (e) {
    shifted = e.shiftKey;
});


function addSpaceInfo(spaces) {
    //.append('<div>¥334</div>');//find("a").attr('data-custom', 110);
    console.log('space datas: ' + JSON.stringify(spaces));
    $(".datepicker-days td").filter(function () {
        var date = $(this).text();
        return /\d/.test(date);
    }).each(function (index, element) {
        var spaceItem = spaces[index];
        if (spaceItem) {
            //spaceItem.bookableQuantity = 2;
            var bookable = spaceItem.bookableQuantity ? spaceItem.bookableQuantity + '间' : '0间';
            var date = getDateNumberFromTd($(element));
            /*$(element).text();
             if ($(element).children().length >1) {
             date = $(element).find('div:eq(0)').text();
             }*/
            $(element).text('');
            $(element).append('<div style="line-height:150%;color: deepskyblue;">' + date + '</div><div class="" style="line-height:150%;">¥' + spaceItem.price + '</div><div class="" style="line-height:150%;">' + bookable + '</div>');
            // $(element).append('&nbsp;¥' + spaceItem.originalPrice + '&nbsp;' + bookable );
            // $(element).attr('data-custom',110);
//                    $(element).css('width:50px;height:80px;')
        }
    })
}

function format2PickerData(result, startDate, endDate) {
    var dst = [];
    for (var currentDate = startDate; currentDate <= endDate; currentDate = currentDate.addDays(1)) {
        var hit = false;
        for (var j = 0; j < result.length; ++j) {
            var item = result[j];
            var checkInDate = new Date(item.formtedCheckIn);
            if (!item.formtedCheckIn || checkInDate.setHours(0, 0, 0, 0) < currentDate.setHours(0, 0, 0, 0)) {
                //这种情况继续匹配
            } else if (checkInDate.setHours(0, 0, 0, 0) > currentDate.setHours(0, 0, 0, 0)) {
                //这种情况填个空的
                dst.push({
                    "id": 0,
                    "checkIn": 0,
                    "originalPrice": 0,
                    "price": 0,
                    "bookableQuantity": 0,
                    "totalQuantity": 0,
                    "formtedCheckIn": currentDate.Format(DATE_FORMAT)
                });
                hit = true;
                break;
            } else if (item.formtedCheckIn === currentDate.Format(DATE_FORMAT)) {
                //精准匹配
                dst.push({
                    "id": item.id,
                    "checkIn": item.checkIn,
                    "originalPrice": item.originalPrice,
                    "price": item.price,
                    "bookableQuantity": item.bookableQuantity,
                    "totalQuantity": item.totalQuantity,
                    "formtedCheckIn": item.formtedCheckIn
                });
                hit = true;
                break;
            }
        }
        if (!hit) {
            //这种情况填个空的
            dst.push({
                "id": 0,
                "checkIn": 0,
                "originalPrice": 0,
                "price": 0,
                "bookableQuantity": 0,
                "totalQuantity": 0,
                "formtedCheckIn": currentDate.Format(DATE_FORMAT)
            });
        }
    }
    return dst;

}

function addCustomInformation($picker, space, dateRange) {
    //console.log(JSON.stringify($picker));
    //console.log(JSON.stringify(now));
    //console.log('start:' + startDate.Format(DATE_FORMAT) + ',end:' + endDate.Format(DATE_FORMAT));
    $.ajax({
        "url": space.basePath + "/product/availability/query?spaceId=" + space.spaceId + "&datePar=" + dateRange.minDate + ":" + dateRange.maxDate,
        "type": "get",
        "success": function (response) {
            if (response == null || '0000' != response.resultCode) {
                $.growl.error({title: "操作失败：", message: response.resultMessage});
            } else {
                //console.log(JSON.stringify(response));
                var pickerData = format2PickerData(response.result, new Date(dateRange.minDate), new Date(dateRange.maxDate));
                addSpaceInfo(pickerData);

            }
        }
    });
}

function getMinMaxDateRange($picker) {
    var months = {
        "一月": "01",
        "二月": "02",
        "三月": "03",
        "四月": "04",
        "五月": "05",
        "六月": "06",
        "七月": "07",
        "八月": "08",
        "九月": "09",
        "十月": "10",
        "十一月": "11",
        "十二月": "12"
    };
    var $dayTable = $picker.find('.datepicker-days table.table-condensed');

    var minDateEmlt = $dayTable.find('tbody tr:first').children('td:first');
    var minDateStr = getDateNumberFromTd(minDateEmlt);
    var isPrevMonth = false;
    if (minDateEmlt.hasClass('old')) {
        isPrevMonth = true;
    }

    var maxDateElmt = $dayTable.find('tbody tr:last').children('td:last');
    var maxDateStr = digitTo2CharStr(+getDateNumberFromTd(maxDateElmt));
    var isNextMonth = false;
    if (maxDateElmt.hasClass('new')) {
        isNextMonth = true;
    }

    var monthYear = $dayTable.find('thead th.datepicker-switch').text().split(' ');
    //var yearMonthStr = monthYear[1] + '-' + months[monthYear[0]];
    var currMonth = months[monthYear[0]];

    var prevMonthVal = '';
    var minDateMinusYear = 0;
    if((+currMonth - 1) > 0) {
        prevMonthVal = (+currMonth - 1);
    }else{
        prevMonthVal = 12;
        minDateMinusYear = -1;
    }

    var nextMonthVal = '';
    var maxDatePlusYear = 0;
    if((+currMonth + 1) <= 12) {
        nextMonthVal = (+currMonth + 1);
    }else{
        nextMonthVal = 1;
        maxDatePlusYear = 1;
    }

    var prevMonth = digitTo2CharStr(prevMonthVal);
    var nextMonth = digitTo2CharStr(nextMonthVal);
    return {
        minDate: (+monthYear[1] + minDateMinusYear) + '-' + (isPrevMonth ? prevMonth : currMonth) + '-' + minDateStr,
        maxDate: (+monthYear[1] + maxDatePlusYear)+ '-' + (isNextMonth ? nextMonth : currMonth) + '-' + maxDateStr
    };

}

function getDateNumberFromTd($elmt) {
    if ($elmt.children().length > 1) {
        return $elmt.find('div:eq(0)').text();
    }
    return $elmt.text();
    /*return $elmt.clone()    //clone the element
     .children() //select all the children
     .remove()   //remove all the children
     .end()  //again go back to selected element
     .text();*/
}

function digitTo2CharStr(digit) {
    return ("0" + digit).slice(-2);
}



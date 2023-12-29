$(document).ready(function() {
    $.fullCalendar.locale('ja');
    // FullCalendar initialization
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month'
        },
         events : {
              // url : '/api/event/all' //ここにRestControllerを呼び出すurlを記載
            },
            //イベントの色を変える
            eventColor : '#FFFFFF',
            eventRender : function(event, element) {
              element.css("font-size", "0.9em");
              element.css("padding", "5px");
              //タグをhtmlタグとして認識するようにする *2
              element.find('span.fc-title').html(
              element.find('span.fc-title').text());
            }
            // Other options...
    });
});
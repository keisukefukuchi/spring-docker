document.addEventListener('DOMContentLoaded', function() {
    let calendarEl = document.getElementById('calendar');
    let calendar = new FullCalendar.Calendar(calendarEl, {
        locale: 'ja',
        height: "auto",
        headerToolbar: {
        	start: "prev",
        	center: "title",
        	end: "next"
        },
        initialView: 'dayGridMonth',
        dateClick: function(info) {
            // Spring Bootにデータを送信するためのAjaxリクエスト
            sendDataToSpringBoot(info.dateStr);
        },
        buttonText: {month: '月'},
        buttonHints: {
        	prev: '前の$0',
        	next: '次の$0',
        },
        dayCellContent: function(arg){
            // 日を消す
            return arg.date.getDate();
        },
        views: {
        	dayGridMonth: {
        		titleFormat: { month: "long" }
            }
        },
    });
    calendar.render();

    function sendDataToSpringBoot(selectedDate) {
        // Ajaxリクエストを作成
        let xhr = new XMLHttpRequest();
        let url = '/expense?selectedDate=' + encodeURIComponent(selectedDate);
        // リクエストをオープン
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        // レスポンスが返ってきたときの処理
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // レスポンスが成功の場合の処理
                console.log('データが正常に送信されました');
            }
        };
        // リクエストを送信
        xhr.send();
    }
});
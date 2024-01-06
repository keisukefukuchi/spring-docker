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
        // リクエストデータを作成
        let url = '/expense?selectedDate=' + encodeURIComponent(selectedDate);
        // GETリクエストを送信
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                })
                .then(data => {
                    // レスポンスのHTMLを現在のページに挿入
                    document.body.innerHTML = data;
                })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                });
    }
});
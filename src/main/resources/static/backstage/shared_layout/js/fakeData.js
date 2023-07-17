// const { Chart } = await import('chart.umd.min.js');

(async function () {
    const data = [
        { date: '12號', count: 10 },
        { date: '13號', count: 1 },
        { date: '14號', count: 3 },
        { date: '15號', count: 2 },
        { date: '16號', count: 6 },
        { date: '17號', count: 2 },
        { date: '18號', count: 1 },
    ];

    new Chart(
        document.getElementById('new-member'),
        {
            type: 'bar',
            data: {
                labels: data.map(row => row.date),
                datasets: [
                    {
                        label: '註冊人數',
                        data: data.map(row => row.count)
                    }
                ]
            }
        }
    );
})();


(async function () {

    const dummyViewCount = [
        { date: '12號', viewCount: 26 },
        { date: '13號', viewCount: 32 },
        { date: '14號', viewCount: 41 },
        { date: '15號', viewCount: 23 },
        { date: '16號', viewCount: 18 },
        { date: '17號', viewCount: 59 },
        { date: '18號', viewCount: 3 },
    ];

    const data2 = {
        labels: dummyViewCount.map(row => row.date),
        datasets: [
            {
                label: '一周單日流量',
                data: dummyViewCount.map(row => row.viewCount),
            }

        ]
    };

    new Chart(
        document.getElementById('view-count'),
        {
            type: 'line',
            data: data2
        }
    );

})();
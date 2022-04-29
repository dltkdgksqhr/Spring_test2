const data = {
    labels: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6'],
    datasets: [
        {
            label: 'Dataset',
            data: Samples.utils.numbers({count: 6, min: -100, max: 100}),
            borderColor: chartColors.red,
            fill: false,
            stepped: true,
        }
    ]
};

const config = {
    type: 'line',
    data: data,
    options: {
        responsive: true,
        interaction: {
            intersect: false,
            axis: 'x'
        },
        plugins: {
            title: {
                display: true,
                text: (ctx) => 'Step ' + ctx.chart.data.datasets[0].stepped + ' Interpolation',
            }
        }
    }
};

var context = document
    .getElementById('myChart')
    .getContext('2d');
var myChart = new Chart(context, config);
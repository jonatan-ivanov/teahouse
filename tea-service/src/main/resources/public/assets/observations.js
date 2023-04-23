$(document).ready(() => {
    new EventSource('/sse').onmessage = onMessage;
});

function onMessage(event) {
    $('#observation-container').append(createBadge(JSON.parse(event.data)));
}

function createBadge(observation) {
    console.log(observation);
    return $('<span></span>')
        .addClass('badge my-1')
        .addClass(observation.error ? 'text-bg-danger' : 'text-bg-success')
        .addClass(getOpacity(observation.duration))
        .append($('<h1></h1>').text(observation.duration));
}

function getOpacity(duration) {
    if (duration <= 25) return 'opacity-50';
    else if (duration < 100) return 'opacity-75';
    else return 'opacity-100';
}

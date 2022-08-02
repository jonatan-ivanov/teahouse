$(document).ready(() => $('#steep-it-button').click(fetchAndRenderTea));

function fetchAndRenderTea() {
    const name = $('#tealeaf-selector').val();
    const size = $('#water-selector').val();

    $('#steep-it-button-spinner').removeAttr('hidden');
    $('#steep-it-button').prop('disabled', true);
    fetch(`/tea/${name}?size=${size}`)
        .then(response => response.json())
        .then(tea => renderTea(name, size, tea))
        .catch(error => console.error(error));
}

function renderTea(name, size, tea) {
    console.log(tea);

    $('#tea-header').text(`${name} - ${size}`);

    $('#steeping-time').text(`Steeping time: ${tea.steepingTime}`);
    $('#water-amount').text(`Amount of water: ${tea.water.amount}`);
    $('#water-temperature').text(`Temperature of water: ${tea.water.temperature}`);

    $('#tealeaves-name').text(`Name of tealeaves: ${tea.tealeaf.name}`);
    $('#tealeaves-type').text(`Type of tealeaves: ${tea.tealeaf.type}`);
    $('#tealeaves-amount').text(`Amount of tealeaves: ${tea.tealeaf.amount}`);

    $('#steep-it-button').prop('disabled', false);
    $('#steep-it-button-spinner').attr('hidden', true);
    $('#tea-card').removeAttr('hidden');
}

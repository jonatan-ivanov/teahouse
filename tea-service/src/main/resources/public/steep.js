$(document).ready(() => {
    $('#steep-it-button').click(fetchAndRenderData);
    $('#tealeaf-selector').keypress(event => onEnterPressed(event, fetchAndRenderData));
    $('#water-selector').keypress(event => onEnterPressed(event, fetchAndRenderData));

});

function onEnterPressed(event, callback) {
    const keycode = event.keyCode ? event.keyCode : event.which;
    if (keycode == '13') {
        callback();
    }
}

function fetchAndRenderData() {
    const name = $('#tealeaf-selector').val();
    const size = $('#water-selector').val();

    $('#steep-it-button-spinner').removeAttr('hidden');
    $('#steep-it-button').prop('disabled', true);
    fetch(`/tea/${name}?size=${size}`)
        .then(renderData)
        .catch(renderError);
}

async function renderData(response) {
    if (response.ok) {
        renderTea(await response.json());
    }
    else {
        renderErrorResponse(await response.json());
    }
}

function renderTea(tea) {
    console.log(tea);

    $('#tea-header').text(`${tea.water.amount} ${tea.tealeaf.name}`);

    $('#steeping-time').text(`Steeping time: ${tea.steepingTime}`);
    $('#water-amount').text(`Amount of water: ${tea.water.amount}`);
    $('#water-temperature').text(`Temperature of water: ${tea.water.temperature}`);

    $('#tealeaves-name').text(`Name of tealeaves: ${tea.tealeaf.name}`);
    $('#tealeaves-type').text(`Type of tealeaves: ${tea.tealeaf.type}`);
    $('#tealeaves-amount').text(`Amount of tealeaves: ${tea.tealeaf.amount}`);

    $('#steep-it-button').prop('disabled', false);
    $('#steep-it-button-spinner').attr('hidden', true);

    $('#alert-container').attr('hidden', true);
    $('#tea-card-container').removeAttr('hidden');
}

function renderErrorResponse(response) {
    renderErrorMessage(response.error);
}

function renderError(error) {
    renderErrorMessage(`Error while calling the backend: ${error}`);
}

function renderErrorMessage(errorMessage) {
    console.error(errorMessage);

    $('#steep-it-button').prop('disabled', false);
    $('#steep-it-button-spinner').attr('hidden', true);
    $('#tea-card-container').attr('hidden', true);
    $('#alert-container')
        .text(errorMessage)
        .removeAttr('hidden');
}

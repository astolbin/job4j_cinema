$(function(){
    hall.init();
});

hall = {
    rowsCount: 4,
    cellsCount: 4,
    init: function() {
        $('#payBtn').addClass('disabled');

        hall.showCells();
        hall.getSessionTickets(1, function(tickets) {
            hall.showTickets(tickets);
        });

        setInterval(function() {
            hall.getSessionTickets(1, function(tickets) {
                hall.showTickets(tickets);
            });
        }, 10000);

        hall.onCellSelect();

        hall.onPayClick();
    },

    showCells: function() {
        for (var j = 1; j <= hall.cellsCount; j++) {
            $('#cinema-hall-table thead tr').append('<th>' + j + '</th>');
        }

        for (var i = 1; i <= hall.rowsCount; i++) {
            var tr = '<th>' + i + '</th>';

            for (var j = 1; j <= hall.cellsCount; j++) {
                var id = 'place-' + i + '-' + j;
                var name = 'place[' + i + '][' + j + ']';
                var data = 'data-row="' + i + '" data-cell="' + j + '"';
                tr += '<td><input type="radio" name="'+name+'" id="'+id+'" '+data+'> '
                    + '<label for="'+id+'">Ряд ' + i + ', Место ' + j + '</label></td>';
            }
            tr = '<tr>' + tr + '</tr>';
            $('#cinema-hall-table tbody').append(tr);
        }
    },

    getSessionTickets: function(sessionId, callback) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cinema/hall-tickets',
            dataType: 'json',
            data: {session_id: sessionId}
        }).done(function (tickets) {
            callback(tickets);
        }).fail(function (err) {
            console.log(err);
        });
    },

    showTickets: function(tickets) {
        var selectedRow = Cookies.get('row');
        var selectedCell = Cookies.get('cell');
        $('#cinema-hall-table input').each(function() {
            var placeId = 'place-' + selectedRow + '-' + selectedCell;
            if ($(this).data('row') == selectedRow && $(this).data('cell') == selectedCell) {
                $(this).prop('checked', true);
                $('#payBtn').removeClass('disabled');
            } else {
                $(this).prop('checked', false);
                $(this).prop('disabled', false);
            }
        });
        for (var ticket of tickets) {
            var placeId = '#place-' + ticket.row + '-' + ticket.cell;
            $(placeId).prop('checked', true);
            $(placeId).prop('disabled', true);

            if (ticket.row == selectedRow && ticket.cell == selectedCell) {
                Cookies.set('row', 0);
                Cookies.set('cell', 0);
                alert('Место № ' + ticket.cell + ' в ' + ticket.row + ' ряду уже выкуплено.');
            }
        }
    },

    onCellSelect: function() {
        $('#cinema-hall-table input[type=radio]').change(function() {
            var radioClicked = $(this);
            Cookies.set('row', radioClicked.data('row'));
            Cookies.set('cell', radioClicked.data('cell'));
            $('#cinema-hall-table input[type=radio]').each(function() {
                if ($(this).attr('disabled') !== 'disabled' && $(this).attr('id') !== radioClicked.attr('id')) {
                    $(this).prop('checked', false);
                }
            });
            $('#payBtn').removeClass('disabled');
        });
    },

    onPayClick: function() {
        $('#payBtn').click(function(e) {
            e.preventDefault();

            if (Cookies.get('row') == undefined || Cookies.get('row') == 0 || Cookies.get('cell') == 0) {
                alert('Вы не выбрали место в зале');
                return;
            }

            window.location.href = 'payment.html';
        });
    }
}
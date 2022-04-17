$(function() {
    $('#row').text(Cookies.get('row'));
    $('#cell').text(Cookies.get('cell'));

    $('#btnBack').click(function() {
        window.location.href = '/cinema/';
    });

    $('#formPayment').submit(function(e) {
        e.preventDefault();
        $('#formPayment input').prop('disabled', true);

        var ticket = JSON.stringify({
            session: 1,
            account: 0,
            row: Cookies.get('row'),
            cell: Cookies.get('cell')
        });
        var account = JSON.stringify({
            username: $('#username').val(),
            phone: $('#phone').val(),
            email: $('#email').val()
        });

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/cinema/ticket',
            dataType: 'json',
            data: {ticket, account}
        }).done(function (result) {
            Cookies.set('row', 0);
            Cookies.set('cell', 0);
            alert(result.message);
            window.location.href = '/cinema/';
        }).fail(function (err) {
            console.log(err);
        });

        return false;
    });
});
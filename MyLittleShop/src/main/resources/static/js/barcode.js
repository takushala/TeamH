var dt;
$.get("/api/item/barcodes", function (data) {
    dt = data;
    // data contains your html
});


function order_by_occurrence(arr) {

    var counts = {};
    arr.forEach(function(value){
        if(!counts[value]) {
            counts[value] = 0;
        }
        counts[value]++;
    });

    return Object.keys(counts).sort(function(curKey,nextKey) {
        return counts[curKey] < counts[nextKey];
    });
}

function load_quagga(){
    $(".formValidate").validate();
    $(".quantityValidate").rules("add", {
        required:true,
        digits:true
    });

    var cartTable = $('#cart-table').DataTable({
        ordering: false,
        searching: false,
        paging: false,
        ajax: {
            type: "GET",
            url: '/api/item/shoppingCart',
            dataSrc: ''
        },
        columns: [
            {data: 'item.code'},
            {data: 'item.name'},
            {data: 'item.price'},
            {data: 'quantity'}],
        aoColumnDefs: [
            {
                "aTargets": [4],
                "mData": "item.code",
                "mRender": function (data, type, full) {
                    return '<button type="button" class="btn btn-default glyphicon glyphicon-pencil" data-toggle="modal" data-target="#edit' + data + '"></button>' +
                        '<button type="button" class="btn btn-default glyphicon glyphicon-trash" data-toggle="modal" data-target="#delete' + data + '"></button>' +
                        '<div id="edit' + data + '" class="modal fade" role="dialog">\n' +
                        '                        <div class="modal-dialog">\n' +
                        '\n' +
                        '                            <div class="modal-content">\n' +
                        '                                <div class="modal-header">\n' +
                        '                                    <button type="button" class="close" data-dismiss="modal">&times;</button>\n' +
                        '                                    <h4 class="modal-title" >Edit item, barcode:' + data + ' </h4>\n' +
                        '                                </div>\n' +
                        '                                <div class="modal-body">\n' +
                        '                                    <form class="formValidate" action="/api/item/shoppingCart/edit' + data + '" method="post">\n' +
                        '                                        <div class="form-group">\n' +
                        '                                            <label>Quantity</label>\n' +
                        '                                            <input type="number" class="form-control quantityValidate" required="required" name ="quantity">\n' +
                        '                                        </div>\n' +
                        '\n' +
                        '                                        <button type="submit" class="btn btn-default">Submit</button>\n' +
                        '                                    </form>\n' +
                        '                                </div>\n' +
                        '                                <div class="modal-footer">\n' +
                        '                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                    \n' +
                        '                    <div id="delete' + data + '" class="modal fade" role="dialog">\n' +
                        '                        <div class="modal-dialog">\n' +
                        '\n' +
                        '                            <div class="modal-content">\n' +
                        '                                <div class="modal-header">\n' +
                        '                                    <button type="button" class="close" data-dismiss="modal">&times;</button>\n' +
                        '                                    <h4 class="modal-title" >Do you really want to delete this item? </h4>\n' +
                        '                                </div>\n' +
                        '                                <div class="modal-body">\n' +
                        '                                    <form action="/api/item/shoppingCart/delete' + data + '" method="post">\n' +
                        '                                        <button type="submit" class="btn btn-default">Submit</button>\n' +
                        '                                    </form>\n' +
                        '                                </div>\n' +
                        '                                <div class="modal-footer">\n' +
                        '                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '\n' +
                        '                        </div>\n' +
                        '                    </div>';

                }
            }
        ]


    });


    if ($('#barcode-scanner').length > 0 && navigator.mediaDevices && typeof navigator.mediaDevices.getUserMedia === 'function') {

        var last_result = [];
        if (Quagga.initialized == undefined) {
            Quagga.onDetected(function(result) {
                var last_code = result.codeResult.code;
                last_result.push(last_code);
                if (last_result.length > 10) {
                    code = order_by_occurrence(last_result)[0];
                    last_result = [];
                    //Quagga.stop();
                    if(dt.indexOf(code)>=0) {
                        $.ajax({
                            type: "POST",
                            url: '/api/item/addCart',
                            data: {"barcode": code},
                            dataType: 'json'
                        }).done(function (result) {
                            alert(result.status);
                            if (result.status == "ok") {
                                cartTable.ajax.reload();
                            }
                        });
                    }

                }
            });
        }

        Quagga.init({
            inputStream : {
                name : "Live",
                type : "LiveStream",
                numOfWorkers: navigator.hardwareConcurrency,
                target: document.querySelector('#barcode-scanner')
            },
            decoder: {
                readers : ['code_128_reader']
            }
        },function(err) {
            if (err) { console.log(err); return }
            Quagga.initialized = true;
            Quagga.start();
        });

    }
};

$(document).ready(load_quagga);

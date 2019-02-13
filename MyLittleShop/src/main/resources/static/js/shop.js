$(document).ready(function() {
    $('#additem').validate({ // initialize the plugin
        rules: {
            itemquantity: {
                required: true,
                digits: true
            }
        }
    });

    $('#inventory-list').DataTable({
        initComplete: function () {
            this.api().columns([0, 1, 2, 3, 4, 5]).every(function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo($(column.footer()).empty())
                    .on('change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search(val ? '^' + val + '$' : '', true, false)
                            .draw();
                    });

                column.data().unique().sort().each(function (d, j) {
                    select.append('<option value="' + d + '">' + d + '</option>')
                });
            });
        }
    });
    var table1 =$('#import-list').DataTable({
        initComplete: function () {
            this.api().columns().every(function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo($(column.footer()).empty())
                    .on('change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search(val ? '^' + val + '$' : '', true, false)
                            .draw();
                    });

                column.data().unique().sort().each(function (d, j) {
                    select.append('<option value="' + d + '">' + d + '</option>')
                });
            });
        }
    });
    var table =$('#sale-list').DataTable({
        initComplete: function () {
            this.api().columns().every(function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo($(column.footer()).empty())
                    .on('change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search(val ? '^' + val + '$' : '', true, false)
                            .draw();
                    });

                column.data().unique().sort().each(function (d, j) {
                    select.append('<option value="' + d + '">' + d + '</option>')
                });
            });
        }
    });
    $('#min').keyup( function() {
        table.draw();
        table1.draw();
    } );
    $('#max').keyup( function() {
        table.draw();
        table1.draw();
    } );

    $('.inventory').show();
    $('.filter').hide();
    $('.import').hide();
    $('.sale').hide();
    $('.1st').on('click', function () {
        console.log("button clicked");
        $('.inventory').show();
        $('.filter').hide();
        $('.import').hide();
        $('.sale').hide();
    });
    $('.2nd').click(function () {
        $('.inventory').hide();
        $('.filter').show();
        $('.import').show();
        $('.sale').hide();
    });
    $('.3rd').click(function () {
        $('.inventory').hide();
        $('.filter').show();
        $('.import').hide();
        $('.sale').show();
    });


});

$.fn.dataTableExt.afnFiltering.push(
    function( oSettings, aData, iDataIndex ) {
        var min = document.getElementById('min').value;
        var max = document.getElementById('max').value;
        var iStartDateCol = 7;

        min=min.substring(0,4) + min.substring(5,7)+ min.substring(8,10);
        max=max.substring(0,4) + max.substring(5,7)+ max.substring(8,10);
        var version = $.trim(aData[iStartDateCol]);
        version=version.substring(0,4) + version.substring(5,7)+ version.substring(8,10);


        if ( min === "" && max === "" )
        {
            return true;
        }
        else if ( min === "" && version <= max )
        {
            return true;
        }
        else if ( min <= version && "" === max )
        {
            return true;
        }
        else if ( min <= version && version <= max )
        {
            return true;
        }
        return false;
    }
);

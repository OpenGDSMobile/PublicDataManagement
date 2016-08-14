/**
 * Created by intruder on 16. 8. 7.
 */
var contextRoot = contextRoot;


function collectedListRequest() {
    var numTrue=0;
    $.ajax({
        url : contextRoot + 'api/Collected',
        type : 'GET',
        success : function (evt){
            $.each(evt, function(key, value) {
                var indexTableTag = $('#managementTable > tbody:last');
                indexTableTag.append('<tr>' +
                    '<td>' + (value.name) + '</td>' +
                    '<td>' + (value.provider) + '</td>' +
                    '<td>' + (value.url) + '</td>' +
                    '<td>' + (value.ep) + '</td>' +
                    '<td>' + (value.time) + '</td>' +
                    '<td>' + (value.keys) + '</td>' +
                    '<td>' + (value.status) + '</td>' +
                    '<td>' + ' <button type="button" style="margin-right:5px;" class="btn btn-xs" ' +
                    '          data-header="Edit Collect Information" data-text="Edit"' +
                    '          data-name="'+ value.name +'"  data-toggle="modal" data-target="#infoModal">Edit</button>' +
                              '<button type="button" class="btn btn-xs btn-danger btn-del" ' +
                    '          data-name="'+ value.name +'">Del</button> </td>' +
                    '</tr>');
                if (value.status == true) {
                    numTrue += 1;
                }
            });
        },
        error : function (evt) {
            humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                addnCls : 'humane-libnotify-error'
            });
            console.log(evt);
        }
    });

}

$(function() {
    collectedListRequest();
    /*******************************/
    $(document).on('click', '.btn-del', function(evt) {
        var name = $(evt.target).data("name");
        $.ajax({
            url : contextRoot + "api/Collected/" + name,
            type : 'DELETE',
            success : function (){
                humane.log('Complete ' + text + 'Delete Public Data');
                location.reload(true);
            },
            error : function (evt){
                humane.log('Error Delete Public Data. Please, confirm database connect.',{
                    addnCls : 'humane-libnotify-error'
                });
                console.log(evt);
            }
        });

    });

    $('#saveBtn').click(function(){
        if ( isNaN($('#modalTime').val()) == true ) {
            humane.log('Time value is not number.',{
                addnCls : 'humane-libnotify-error'
            });
            return -1;
        }


        var text = $(this).text();
        var type = 'PUT';
        var requestData = {
            name : $('#modalName').val(),
            provider : $('#modalProvider').val(),
            url : $('#modalUrl').val(),
            ep : $('#modalEp').val(),
            time : $('#modalTime').val(),
            keys : $('#modalKeys').val(),
            comment : $('#modalComment').val(),
            status : $('#modalStatus').val()
        };
        if (text != 'Edit') {
            type = 'POST';
        }
        $.ajax({
            url : contextRoot + "api/Collected",
            data : JSON.stringify(requestData),
            contentType : 'application/json',
            type: type,
            success : function(evt){
                console.log(evt);
                humane.log('Complete ' + text + 'Collected Public Data');
            },
            error: function(evt){
                console.log(evt);
            }
        });
        $('#infoModal').modal('hide');

        location.reload(true);
    });


    $('#infoModal').on('show.bs.modal', function(event){
        var button = $(event.relatedTarget);
        var header = button.data('header');
        var btnText = button.data('text');
        var name = (typeof (button.data('name')) !== 'undefined') ? button.data('name') : null;
        var modal = $(this);
        modal.find('.modal-title').text(header);
        modal.find('.btn-save').text(btnText);
        console.log(name);
        if (name !== null) {
            $.ajax({
                url : contextRoot + "api/Collected/" + name,
                type : 'GET',
                success : function (evt){
                    console.log(evt);
                    modal.find('#modalName').val(evt.name);
                    modal.find('#modalProvider').val(evt.provider);
                    modal.find('#modalUrl').val(evt.url);
                    modal.find('#modalEp').val(evt.ep);
                    modal.find('#modalTime').val(evt.time);
                    modal.find('#modalKeys').val(evt.keys);
                    modal.find('#modalComment').val(evt.comment);
                    modal.find('#modalStatus').val(evt.status.toString());
                },
                error : function (evt) {
                    humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                        addnCls : 'humane-libnotify-error'
                    });
                    console.log(evt);
                }
            });
        }
    });

});

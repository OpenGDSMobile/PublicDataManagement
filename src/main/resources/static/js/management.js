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
                    '<td> <div class="radio">' +
                    '<label><iput type="radio" name="managementRadio"/>' +
                    '</label></div> </td>' +
                    '<td>' + (value.name) + '</td>' +
                    '<td>' + (value.provider) + '</td>' +
                    '<td>' + (value.url) + '</td>' +
                    '<td>' + (value.ep) + '</td>' +
                    '<td>' + (value.time) + '</td>' +
                    '<td>' + (value.status) + '</td>' +
                    '</tr>');
                if (value.status == true) {
                    numTrue += 1;
                }
            });
            /*humane.log('Complete Loading Collected Public Data');*/
        },
        error : function (evt) {
            humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                addnCls : 'humane-libnotify-error'
            });

        }
    });

}

$(function() {
    collectedListRequest();
});

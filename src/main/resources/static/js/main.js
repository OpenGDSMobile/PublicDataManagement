/**
 * Created by intruder on 16. 7. 25.
 */
var contextRoot = contextRoot;




function collectedListRequest() {
    var numTrue=0;
    $.ajax({
        url : contextRoot + 'api/Collected',
        type : 'GET',
        success : function (evt){
            $.each(evt, function(key, value) {
                var indexTableTag = $('#indexTable > tbody:last');
                indexTableTag.append('<tr>' +
                    '<td>' + (key + 1) + '</td>' +
                    '<td>' + (value.name) + '</td>' +
                    '<td>' + (value.provider) + '</td>' +
                    '<td>' + (value.url) + '</td>' +
                    /*'<td>' + (value.ep) + '</td>' +*/
                    '<td>' + (value.time) + '</td>' +
                    '<td>' + (value.comment) + '</td>' +
                    '<td>' + (value.status) + '</td>' +
                    '</tr>');
                if (value.status == true) {
                    numTrue += 1;
                }
            });
            $('#totalCollected').text(evt.length);
            $('#trueCollected').text(numTrue);
            $('#falseCollected').text((evt.length-numTrue));
            /******* C3 추가...*************/
            humane.log('Complete Loading Collected Public Data');
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

/**
 * Created by intruder on 16. 8. 14.
 */
function syntaxHighlight(json) {
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}

$(function(){

    $.ajax({
        url: contextRoot + 'api/Collected',
        type : 'GET',
        success : function(evt){
            var selectObj = $('#collectName');
            $.each(evt, function(key, value){
                selectObj.append('<option value=' + value.name +
                '>' + value.name + '</option>');
            });
            selectObj.selectpicker('refresh');
        },
        error : function(evt){
            humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                addnCls : 'humane-libnotify-error'
            });
            console.log(evt);
        }
    });

    $('#collectName').on('changed.bs.select', function (e){
        var selectedText = $(this).find("option:selected").text();

        $.ajax({
            url : contextRoot + 'api/MongoDB/' + selectedText + '/saveTime',
            type  : 'GET',
            success : function (evt){
                var selectObj = $('#collectDate');
                selectObj.empty();
                $.each(evt, function(key, value){
                    var t = value.saveTime;
                    var stringTime = t.substr(0, 4) + '/' + t.substr(4,2) + '/' + t.substr(6,2) + '  '
                                    + t.substr(8,2) + ':' + t.substr(10,2);
                    selectObj.append('<option value="' + selectedText + ',' +value.saveTime + '">' +
                                        stringTime +'</option>');
                });
                selectObj.attr('disabled', false);
                selectObj.selectpicker('refresh');
            },
            error : function(evt){
                humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                    addnCls : 'humane-libnotify-error'
                });
                console.log(evt);
            }
        })
    });
    $('#collectDate').on('changed.bs.select', function (){
        var selectedVal = $(this).find("option:selected").val().split(',');
        var jsonData = {
            queryType : '=',
            field : 'saveTime',
            value : selectedVal[1]
        };

        $.ajax({
            url : contextRoot + 'api/MongoDB/query/' + selectedVal[0],
            data : jsonData,
            type : 'GET',
            success : function (evt){

                var resultTag = $('#jsonResult');
                var JsonStr = JSON.stringify(evt, undefined, 4);
                var resultStr = syntaxHighlight(JsonStr);
                resultTag.empty();
                resultTag.html(resultStr);
            }
        })
    });
});
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

    $('#collectName').on('hidden.bs.select', function (e){
        var selectedText = $(this).find("option:selected").text();
        $('#collectEndTime').empty();
        $('#collectKey').empty();
        $('#collectValue').empty();

        $.ajax({
            url : contextRoot + 'api/MongoDB/' + selectedText + '/saveTime',
            type  : 'GET',
            success : function (evt){
                var selectObj = $('#collectStartTime');
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
        });

        $.ajax({
            url : contextRoot + 'api/MongoDB/selectOne/' + selectedText,
            type : 'GET',
            success : function (evt){
                var selectObj = $('#collectDataKey');
                for(key in evt) {
                    selectObj.append('<option value="' + key + '">' + key + '</option>');
                }
                selectObj.attr('disabled', false);
                selectObj.selectpicker('refresh');
                $('#collectDataKey').off('hidden.bs.select');
                $('#collectDataKey').on('hidden.bs.select', function () {
                    var selectedVal = $(this).find("option:selected").val();
                    var selectObj = $('#collectKey');
                    var selectObj1 = $('#collectValue');
                    var resultTag = $('#jsonResult');
                    var obj = evt[selectedVal][0];
                    selectObj.empty();
                    selectObj1.empty();
                    resultTag.empty();
                    if (typeof(obj) === 'undefined'){
                        humane.log('Not Visualization Key',{
                            addnCls : 'humane-libnotify-error'
                        });
                        return -1;
                    }

                    for(key in obj) {
                        selectObj.append('<option value="' + key + '">' + key + '</option>');
                        selectObj1.append('<option value="' + key + '">' + key + '</option>');
                    }
                    selectObj.attr('disabled', false);
                    selectObj.selectpicker('refresh');
                    selectObj1.attr('disabled', false);
                    selectObj1.selectpicker('refresh');

                    var JsonStr = JSON.stringify(obj, undefined, 4);
                    var resultStr = syntaxHighlight(JsonStr);
                    resultTag.html(resultStr + '<br> ...');
                });
            },
            error: function(evt){
                humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                    addnCls : 'humane-libnotify-error'
                });
                console.log(evt);
            }
        });
    });

    $('#collectStartTime').on('hidden.bs.select', function (){
       var selectedVal = $(this).find("option:selected").val().split(',');
       $.ajax({
           url : contextRoot + 'api/MongoDB/' + selectedVal[0] + '/saveTime/gt/' + selectedVal[1],
           type : 'GET',
           success : function (evt){
                var selectObj = $('#collectEndTime');
                selectObj.empty();
                $.each(evt, function(key, value){
                    var t = value.saveTime;
                    var stringTime = t.substr(0, 4) + '/' + t.substr(4,2) + '/' + t.substr(6,2) + '  '
                                    + t.substr(8,2) + ':' + t.substr(10,2);
                    selectObj.append('<option value="' + selectedVal[0] + ',' + selectedVal[1] + ',' +value.saveTime + '">' +
                                        stringTime +'</option>');
                });
                selectObj.attr('disabled', false);
                selectObj.selectpicker('refresh');
           }
       });
    });
});
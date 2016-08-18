var mapObj;
var geoServerAddr = 'http://113.198.80.9/geoserver/';
var workspace = 'OpenGDSMobile';

function geoDataSelectEvt(){
    var selected = $(this).find("option:selected");
    var selectVal = selected.val();
    var selectGroup = selected.parent().attr('label');
    console.log(selectVal + ' '+ selectGroup);
    var mapObjs = mapObj.getMapObj().getLayers().getArray();
    if(mapObjs.length != 1) {
        for (var i=1; i<mapObjs.length; i++){
            mapObj.removeLayer(mapObjs[i].get('title'));
        }
    }

    if (selectGroup == 'GeoServer') {
        var serviceStr = 'wfs?service=WFS&version=1.1.0&request=GetFeature&' +
                         'typeNames=' + workspace + ':' +selectVal + '&outputFormat=json&srsname=EPSG:3857';
        var requestAddr = geoServerAddr + serviceStr;

        $.ajax({
            url : requestAddr,
            type : 'GET',
            dataType : 'json',
            success : function (evt){
                mapObj.addGeoJSONLayer(evt, 'polygon', selectVal, {
                   attrKey : 'sig_kor_nm',
                    fillColor: '#FF00FF'
                });
            }
        })
    }


}


$(function(){
    mapObj = new openGDSMobile.MapVis('visMap');

    $.ajax({
        url : contextRoot + 'api/GeoServer/' + workspace,
        type : 'GET',
        success : function(evt){
            var selectObj = $('#selectGeoServer');
            $.each(evt.names, function(key, value){
                selectObj.append('<option value=' + value +
                    '>' + value + '</option>');
            });
            $('#geoBasedName').selectpicker('refresh');
        },
        error : function(evt){
            humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                addnCls : 'humane-libnotify-error'
            });
            console.log(evt);
        }

    });

    $.ajax({
        url: contextRoot + 'api/Collected',
        type : 'GET',
        success : function(evt){
            var selectObj = $('#selectPublicData');
            $.each(evt, function(key, value){
                selectObj.append('<option value=' + value.name +
                    '>' + value.name + '</option>');
            });
            $('#geoBasedName').selectpicker('refresh');
        },
        error : function(evt){
            humane.log('Error Loading Collected Public Data. Please, confirm database connect.',{
                addnCls : 'humane-libnotify-error'
            });
            console.log(evt);
        }
    });

    $('#geoBasedName').on('changed.bs.select', geoDataSelectEvt);


});
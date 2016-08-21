var mapObj;
var geoServerAddr = 'http://113.198.80.9/geoserver/';
var workspace = 'OpenGDSMobile';
var publicData = null;

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
    var lat = $('#latValue');
    var long = $('#longValue');
    var coord = $('#coordValue');
    lat.empty();
    long.empty();
    if (selectGroup == 'GeoServer') {
        lat.selectpicker('hide');
        long.selectpicker('hide');
        coord.selectpicker('hide');
        var serviceStr = 'wfs?service=WFS&version=1.1.0&request=GetFeature&' +
                         'typeNames=' + workspace + ':' +selectVal + '&outputFormat=json&srsname=EPSG:3857';
        var requestAddr = geoServerAddr + serviceStr;

        $.ajax({
            url : requestAddr,
            type : 'GET',
            dataType : 'json',
            success : function (evt){
                console.log(evt);
                mapObj.addGeoJSONLayer(evt, 'polygon', selectVal, {
                   /*attrKey : 'sig_kor_nm',*/
                   fillColor: '#FF00FF'
                });
            }
        })
    } else if (selectGroup == 'Public Data') {
        lat.selectpicker('show');
        long.selectpicker('show');
        coord.selectpicker('show');
        $.ajax({
            url: contextRoot + 'api/MongoDB/selectOne/' + selectVal,
            type: 'GET',
            success: function (evt) {
                console.log(evt);
                for(key in evt['row'][0]) {
                    lat.append('<option value="' + key + '">' + key + '</option>');
                    long.append('<option value="' + key + '">' + key + '</option>');
                    /*console.log(evt[key].length);*/
                }

                lat.selectpicker('refresh');
                long.selectpicker('refresh');

                publicData = evt['row'];
            },
            error: function (evt){

            }
        });
    }
}

function publicDataVisEvt(){
    /*
    $(this).removeClass('btn-danger');
    $(this).addClass('btn-success');
    $(this).selectpicker('refresh');
    */
    var lat = $('#latValue').find('option:selected').val();
    var long = $('#longValue').find('option:selected').val();
    var coord = $('#coordValue').find('option:selected').val();


    if (lat != '' && long != '' && coord != '') {

        for(var key in publicData){
            for(var subKey in publicData[key]){
                if (subKey == lat || subKey == long) {
                    publicData[key][subKey] = Number(publicData[key][subKey]);
                }
            }
        }
        var geoJsonData = GeoJSON.parse(publicData, {Point: [lat, long]});
/*
        var epsg4919 = new ol.proj.Projection({
            code : 'EPSG:4919',
            extent : [-557746.21, -973.45, -557746.21, 973.45],
            units : 'm'
        });
        ol.proj.addProjection(epsg4919);
        console.log(ol.proj.get('EPSG:4919'));
*/

        var test = new ol.layer.Vector({
            title: 'test',
            source : new ol.source.Vector({
                features : (new ol.format.GeoJSON()).readFeatures(geoJsonData, {
                    dataProjection: coord,
                    featureProjection: ol.proj.get('EPSG:3857')
                })
            })
        });
        mapObj.getMapObj().addLayer(test);
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

    $('.add-values').on('changed.bs.select', publicDataVisEvt);

});
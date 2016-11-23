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
            var geoData = 0, json = 0, xml = 0;
            $.each(evt, function(key, value) {
                var indexTableTag = $('#indexTable > tbody:last');
                if ("GeoData" == value.type) {
                    geoData += 1;
                } else if ("JSON" == value.type) {
                    json += 1;
                } else if ("XML" == value.type) {
                    xml += 1;
                }
                indexTableTag.append('<tr>' +
                    '<td>' + (key + 1) + '</td>' +
                    '<td>' + (value.name) + '</td>' +
                    '<td>' + (value.provider) + '</td>' +
                    '<td>' + (value.url) + '</td>' +
                    /*'<td>' + (value.ep) + '</td>' +*/
                    '<td>' + (value.time) + '</td>' +
                    '<td>' + (value.comment) + '</td>' +
                    '<td>' + (value.status) + '</td>' +
                    '<td>' + (value.type) + '</td>' +
                    '</tr>');
                if (value.status == true) {
                    numTrue += 1;
                }
            });
            /*
            $('#totalCollected').text(evt.length);
            $('#trueCollected').text(numTrue);
            $('#falseCollected').text((evt.length-numTrue));
*/
            /******* C3 추가...*************/
            c3.generate({
                bindto: '#dataStat',
                data: {
                    // iris data from R
                    columns: [
                        ['data1', numTrue],
                        ['data2', (evt.length-numTrue)],
                    ],
                    names: {
                        data1: 'Running',
                        data2: 'Not running'
                    },
                    colors : {
                        data1 :'#4672FF',
                        data2 :'#D95116'
                    },
                    type : 'donut',
                    labels : true
                },
                donut : {
                    title : 'Status of open data',
                    label: {
                        format : function (value, ratio, id){
                            return d3.format('')(value);
                        }
                    }
                }
            });
            c3.generate({
                bindto: '#dataTypeStat',
                data: {
                    columns: [
                        ['data1', geoData],
                        ['data2', json],
                        ['data3', xml]
                    ],
                    names: {
                        data1: 'GeoData',
                        data2: 'JSON',
                        data3: 'XML'
                    },
                    colors : {
                        data1 :'#0BE85D',
                        data2 :'#4672FF',
                        data3 :'#FFE701'
                    },
                    type : 'donut',
                    labels : true
                },
                donut : {
                    title : 'Type of request',
                    label: {
                        format : function (value, ratio, id){
                            return d3.format('')(value);
                        }
                    }
                }
            });
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

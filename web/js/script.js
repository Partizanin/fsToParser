/**
 * Created by Partizanin on 22.08.2016.
 */
$(window).load(function fill() {

    loader("show");
    window.setTimeout(function () {
        loader("hide");
    }, 2000);


    filmsLoad();

});

function loader(action) {

    if (action == "show") {
        $("#loader-wrapper").show();
    } else {/*hide*/
        $("#loader-wrapper").hide();
    }
}

function callToServer(request) {
    loader("show");
    var myData = {"operationCall": request};
    var defer = $.Deferred();
    //noinspection JSUnresolvedFunction
    $.ajax({
        type: "GET",
        url: "/Servlet",
        data: {jsonData: JSON.stringify(myData)},
        contentType: "application/json; charset=utf-8",
        dataType: "json",

        //if received a response from the server
        success: function (jsonData) {

        },

        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        },

        complete: function () {
            loader("hide");
        }

    }).done(function (data) {
        //noinspection JSUnresolvedFunction
        defer.resolve(data);
    }).fail(function (xhr, status, errorThrown) {
        alert("Sorry, there was a problem!");
        console.log("Error: " + errorThrown);
        console.log("Status: " + status);
        console.dir(xhr);
    });

    return defer.promise();
}

function filmsLoad() {
    callToServer("load").then(function (jsonData) {

        $.each(jsonData, function (index, value) {

            var imgSrc = "http://img.dotua.org/fsua_items/cover/00/42/03/2/00420358.jpg"/*jsonData.imgSrc*/;
            var filmName = jsonData[index].name;
            var filmNameOriginal = jsonData[index].originalName;
            var genre = jsonData[index].genres;
            var vote_value = jsonData[index].views;

            var div = $('<div class="film" >');

            div.append(
                $('<img>', {
                    src: imgSrc
                }),

                $('</br>'),

                $('<span>', {
                    text: filmName
                }),

                $('<div>', {
                    id: "title-origin",
                    text: filmNameOriginal
                }),

                $('<td>', {
                    text: "Жанр"
                }),

                $('<span>', {
                    text: genre
                }),
                $('<div>', {
                    id: "vote_value",
                    text: vote_value
                })
            );

            $('body').append(div);
        });
    });
}
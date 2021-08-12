/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


const startLoad = () => {
    document.getElementById("overplay").style.top = "0";
    document.getElementById("loading").style.top = "0";
};

const endLoad = () => {
    document.getElementById("overplay").style.top = "200%";
    document.getElementById("loading").style.top = "200%";
};

function uploadImg() {
    startLoad();
    var file = document.getElementById('file');
    var form = new FormData();
    form.append('image', file.files[0]);



    var settings = {
        url: "https://api.imgbb.com/1/upload?key=67e5142af77eecd6de14bf32ae20fcac",
        method: "POST",
        timeout: 0,
        processData: false,
        mimeType: "multipart/form-data",
        contentType: false,
        data: form
    };

    $.ajax(settings).done(function (response) {
        var objAPI = JSON.parse(response);
        var img = objAPI.data.url;
        document.getElementById('img').setAttribute("src", img);
        document.getElementById('urlHidden').setAttribute("value", img);
        endLoad();
    });
}
document.write("<script src='./js/layer.mobile-v2.0/layer_mobile/layer.js'></script>");

function getUrlParams(name) {
    var url_string =window.location.href;
    var url = new URL(url_string);
    var c = url.searchParams.get(name);
    return c;
}
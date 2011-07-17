

function getParameter( name ) {
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}

/** Convert string {abc: [{id:'20090121133930169',x:725.5,y:286,w:60,h:37}] } to json object. */
function getJSON(str) {
	return JSON.parse(str);
};
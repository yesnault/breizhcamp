<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
    html {
        background: url('<c:url value='/static/BreizhCamp.png'/>') no-repeat top center;
    }
    body {
        margin-top:350px;
        margin-left:10px;
        margin-right:10px;
        margin-bottom:70px;
    }
    fieldset {
        padding:10px;
    }
    .baspage {
        margin-bottom:35px;
    }
    h2 {
        font-family: 'Sonsie One', cursive;
        margin-top:50px;
        text-align: center;
        font-size:60px;
    }
    h3 {
        font-family: 'Sonsie One', cursive;
        text-align: center;
        font-size:30px;
    }

    .table-striped tbody tr:nth-child(odd) td.favorite {
        background-color: #f6ef2b;
    }

    .table-striped tbody tr td.favorite {
        background-color: #f6ef2b;
    }

    .table-striped tbody tr:hover  td.favorite {
        background-color: #f6ef2b;
    }
</style>

<script type="text/javascript">
    // Gestion du konami code.
    jQuery(function(){
        var kKeys = [];
        function Kpress(e){
            kKeys.push(e.keyCode);
            if (kKeys.toString().indexOf("38,38,40,40,37,39,37,39,66,65") >= 0) {
                jQuery(this).unbind('keydown', Kpress);
                kExec();
                     }
                 }
        jQuery(document).keydown(Kpress);
    });
    function kExec(){
        $('html').css({"background":"url('<c:url value='/static/BreizhCamp_invert.png'/>') no-repeat top center"});
    }
</script>

<script type='text/javascript' charset='utf-8'>
        function setActive(id){
             document.getElementById(id).className='active';
        }
</script>

<script type='text/javascript' charset='utf-8'>

    function initDatabase() {
        if (typeof(DEMODB) !== "undefined") {
            return;
        }
        try {
            if (!window.openDatabase) {
                alert('Databases are not supported in this browser.');
            } else {
                var shortName = 'BREIZHCAMPDB';
                var version = '1.0';
                var displayName = 'Breizhcamp Database';
                var maxSize = 100000; //  bytes
                DEMODB = openDatabase(shortName, version, displayName, maxSize);
                createTables();
            }
        } catch(e) {

            if (e == 2) {
                // Version number mismatch.
                console.log("Invalid database version.");
            } else {
                console.log("Unknown error "+e+".");
            }
            return;
        }
    }
    function createTables(){
        DEMODB.transaction(
            function (transaction) {
                transaction.executeSql('CREATE TABLE IF NOT EXISTS FAVORITES_TALKS(id INTEGER NOT NULL PRIMARY KEY);', [], nullDataHandler, errorHandler);
            }
        );
    }

    function isFavoriteTalk(id, dataSelectHandler){
        initDatabase();
        DEMODB.transaction(
            function (transaction) {
                transaction.executeSql("SELECT * FROM FAVORITES_TALKS WHERE id = ?;", [id],
                    dataSelectHandler, errorHandler);
            }
        );
    }

    function addFavoriteTalk(id) {
        initDatabase();
        DEMODB.transaction(
            function (transaction) {
                transaction.executeSql("INSERT INTO FAVORITES_TALKS(id) VALUES (?)", [id]);
            }
        );
    }

    function rmFavoriteTalk(id) {
        initDatabase();
        DEMODB.transaction(
            function (transaction) {
                transaction.executeSql("DELETE FROM FAVORITES_TALKS WHERE id = ?", [id]);
            }
        );
    }

    function errorHandler(transaction, error){
     	if (error.code==1){
     		// DB Table already exists
     	} else {
        	// Error is a human-readable string.
    	    console.log('Oops.  Error was '+error.message+' (Code '+error.code+')');
     	}
        return false;
    }


    function nullDataHandler(){
    	console.log("SQL Query Succeeded");
    }
</script>


<!-- Onglets -->
<ul class="nav  nav-tabs">
    <li id="programme"><a href="/index.htm">Programme</a></li>
    <li id="speakers"><a href="/speakers.htm">Speakers</a></li>
    <li id="github"><a href="https://github.com/BreizhJUG/breizhcamp" target="_blank">GitHub</a></li>
    <li id="breizhjug"><a href="http://www.breizhjug.org/" target="_blank">BreizhJug</a></li>
    <li id="contact"><a href="/contact.htm">Contact</a></li>
</ul>

<!-- Liens de navigation -->
<div style="position: fixed;top:2px;right:10px">
    <a href="<c:url value='/index.htm'/>">Home</a>&nbsp;&nbsp;
    <a href="<c:url value='/crud/room/index.htm'/>">Admin</a>
</div>

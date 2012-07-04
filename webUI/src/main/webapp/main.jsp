


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Welcome to Alert-eu Project!</title>


<%  String Uname = request.getParameter("username");
    String mail = request.getParameter("email");
    String uuid = request.getParameter("uuid");
%>
<style>
body {
	margin: 10px auto;
	min-width: 1024px;
	min-height: 800px;
	width: expression(document.body.clientWidth <       1024 ?       "1024px" :    

		  document.body.clientWidth >       1024 ?       "100%" :       "auto"
		);
	font-size: 20px;
	font-family: Arial, Helvetica, sans-serif;
	bgcolor: #C0C0C0;
	align: center;
}

div.top {
	margin: 1px auto;
	width: 100%;
	height: 100px;
	border-width: 5px;
	border-style: double;
	border-color: #990000;
	float: left;
}

div.tabs {
	position: absolute;
	left: 250px;
	top: 128px;
	width: 1030px;
	height: 40px;
	align: justify;
	vertical-align: middle;
}

.middle {
	vertical-align: middle;
}

.button {
	float: left;
	bgcolor: #990000;
	height: 40px;
	width: 206px;
	font-size: 15px;
	font-weight: bold;
	color: #0041D9;
	vertical-align: baseline;
	text-align: center;
}

div.frm {
	position: absolute;
	top: 150px;
	width: 100%
}



div.out {
	position: absolute;
	left: 20px;
	top: 128px;
	width: 200px;
	height: 40px;
	align: justify;
	vertical-align: middle;
	bgcolor: #DDDDDD;
	border-width: 1px;
	border-style: ridge;
	border-color: #FFFF33;
}
</style>



<script type="text/javascript">
	function open_win()

	{

		window.open("laus:8080/war/Panteon.html");

	}
function loadNotification(){
	var username="<%=Uname%>"; 
	var useremail="<%=mail%>"; 
	var uuid="<%=uuid%>";
 post_to_url('/alert-acton/loginform.html', {'username':username , 'email':useremail, 'uid':uuid});
}

function post_to_url(path, params, method) {
    method = method || "post"; //Set method to post by default, if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

	form.setAttribute("target", "_blank");
    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}

</script>



</head>

<body>

	<div class=top>

		<span style="position: absolute; left: 10px; vertical-align: middle;">

			<img src="img/logo.png" alt="logo.png">



		</span> <span
			style="position: absolute; right: 10px; vertical-align: middle;">

			<img src="img/7th_logo.png" alt="7th_logo.png">

		</span> <span
			style="position: absolute; margin-left: 30%; margin-right: 20%; vertical-align: middle;">

			<img src="img/slogan.png" alt="slogan.png">

		</span>

	</div>





	<div class=out>

		<a>Hello <%=Uname%></a> <input type=button
			onclick="javascript:window.location='index.jsp';" value="Logout" />

	</div>

	<div id="tab01" class=tabs>

		<span><input class=button type=button onclick=this.reload();
			value="Search" /> </span> <span><input class=button type=button
			onclick=this.reload(); value="Visualization" /> </span> <span><input
			class=button type=button onclick=this.reload();
			value="Recommendation Service" /> </span> <span><input class=button
			type=button onclick="loadNotification()"; value="Notification" /></span> <span><input
			class=button type=button onclick="open_win()" ; value="Panteon" /></span>

	</div>



	<div id="tabCon01" class="frm">

		<ul style="position: absolute;	top: 0px;	width: 100%;	display: block;	visibility: hidden;">

			<iframe src="laus:8080/hound/index.html" width=1100px height=640px></iframe>

		</ul>

		<ul style="position: absolute;	top: 0px;	width: 100%;	display: block;	visibility: hidden;">

			<iframe src="laus:8080/AlertVizDemo/index.html" width=1000px height=650px></iframe>

		</ul>

		<ul style="position: absolute;	top: 0px;	width: 100%;	display: block;	visibility: hidden;">

		<a href="http://192.168.1.101:9090/socrates-ui/search">Recommendation Service</a>

		</ul>

	</div>

	<script type="text/javascript">
		function tabAction(parNode, parCon) {

			var tabs = document.getElementById(parNode).getElementsByTagName(

			"span");

			var tabLength = tabs.length;

			var tabCon = document.getElementById(parCon).getElementsByTagName(

			"ul");

			var tabConLength = tabCon.length;

			function initTab() {

				for ( var i = 0; i < tabLength; i++) {

					tabs[i].className = "tabs";

				}

				tabs[tabLength - 1].className = "frm";

			}

			function initTabCon() {

				for ( var i = 0; i < tabConLength; i++) {

					tabCon[i].style.visibility = "hidden";

				}

			}

			for ( var i = 0; i < tabLength; i++) {

				tabs[i].theI = i;

				tabs[i].onclick = function() {

					var theI = this.theI;

					initTab();

					initTabCon();

					//this.className = "actived";

					tabCon[theI].style.display = "block";

					tabCon[theI].style.visibility = "visible";

					if (this.previousSibling) {

						var preObj = this.previousSibling;

						if (this.previousSibling.nodeType == 1) {

							preObj.className = "frm";

						} else if (this.previousSibling.nodeType == 3

						&& this.previousSibling.previousSibling) {

							preObj = this.previousSibling.previousSibling;

							preObj.className = "frm";

						}

					}

					this.childNodes[0].blur();

				}

			}

		}

		tabAction("tab01", "tabCon01");
	</script>







</body>

</html>


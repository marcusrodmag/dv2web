<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DV 2 WEB - Reprodução de Vídeo</title>
</head>
<body>
	<p>
		<img alt="DV2WEB" src="style/img/logo.png">
	</p>
	<p>Exibição de seu vídeo convertido para o Padrão WEB</p>
	<object id="NSPlay" width="250" height="200"
		classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95"
		codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701">
		<param name="AudioStream" value="-1">
		<param name="AutoSize" value="0">
		<param name="AutoStart" value="-1">
		<param name="AnimationAtStart" value="0">
		<param name="AllowScan" value="-1">
		<param name="AllowChangeDisplaySize" value="-1">
		<param name="AutoRewind" value="0">
		<param name="Balance" value="0">
		<param name="BaseURL" value="">
		<param name="BufferingTime" value="5">
		<param name="CaptioningID" value="">
		<param name="ClickToPlay" value="-1">
		<param name="CursorType" value="0">
		<param name="CurrentPosition" value="-1">
		<param name="CurrentMarker" value="0">
		<param name="DefaultFrame" value="">
		<param name="DisplayBackColor" value="0">
		<param name="DisplayForeColor" value="16777215">
		<param name="DisplayMode" value="0">
		<param name="DisplaySize" value="4">
		<param name="Enabled" value="-1">
		<param name="EnableContextMenu" value="-1">
		<param name="EnablePositionControls" value="-1">
		<param name="EnableFullScreenControls" value="0">
		<param name="EnableTracker" value="-1">
		<param name="Filename" value="${videosrc}">
		<param name="InvokeURLs" value="-1">
		<param name="Language" value="-1">
		<param name="Mute" value="0">
		<param name="PlayCount" value="1">
		<param name="PreviewMode" value="0">
		<param name="Rate" value="1">
		<param name="SAMILang" value="">
		<param name="SAMIStyle" value="">
		<param name="SAMIFileName" value="">
		<param name="SelectionStart" value="-1">
		<param name="SelectionEnd" value="-1">
		<param name="SendOpenStateChangeEvents" value="-1">
		<param name="SendWarningEvents" value="-1">
		<param name="SendErrorEvents" value="-1">
		<param name="SendKeyboardEvents" value="0">
		<param name="SendMouseClickEvents" value="0">
		<param name="SendMouseMoveEvents" value="0">
		<param name="SendPlayStateChangeEvents" value="-1">
		<param name="ShowCaptioning" value="0">
		<param name="ShowControls" value="-1">
		<param name="ShowAudioControls" value="-1">
		<param name="ShowDisplay" value="0">
		<param name="ShowGotoBar" value="0">
		<param name="ShowPositionControls" value="0">
		<param name="ShowStatusBar" value="-1">
		<param name="ShowTracker" value="0">
		<param name="TransparentAtStart" value="0">
		<param name="VideoBorderWidth" value="0">
		<param name="VideoBorderColor" value="0">
		<param name="VideoBorder3D" value="0">
		<param name="Volume" value="-600">
		<param name="WindowlessVideo" value="0">
		<embed type="application/x-mplayer2"
			pluginspage="http://www.microsoft.com/Windows/Downloads/Contents/Products/MediaPlayer/"
			name="MediaPlayer"
			src="${videosrc}"
			showcontrols="1" showpositioncontrols="0" showaudiocontrols="1"
			showtracker="0" showdisplay="0" showstatusbar="1" showgotobar="0"
			showcaptioning="0" autostart="1" autorewind="0" animationatstart="0"
			transparentatstart="0" allowchangedisplaysize="0" allowscan="0"
			enablecontextmenu="0" clicktoplay="0" width="640" height="480"></embed>
	</object>
	<p>
		<a href="index.jsp">Enviar outro arquivo</a>
	</p>
</body>
</html>
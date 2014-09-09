<html>
<head>
<title>Game Uploading Form</title>
</head>
<body>
<h3>Game Upload:</h3>
Select a file to upload: <br />
<form action="upload" method="POST" enctype="multipart/form-data">
<input type="file" name="file" size="35" />
<br />
<input type="submit" value="Upload File" />
</form>

<p>The file needs to be a CSV file. The first row is the headers.
<br />Headers:
<br />Field,Date,Time,duration,Age Group,Home Team,Visiting Team
<br />The Field must match the Filed name in the system.
<br />If one of the Teams is a VA team, it must match the Team name in the system.
<br />The Age Group must match the Age Group in the system.
<br />The date format must match MM/DD/YYYY
<br />The time must me 24H Military time.
<br />The duration must be in the format 1h 30m

</body>
</html>
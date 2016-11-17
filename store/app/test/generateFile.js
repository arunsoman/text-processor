var fs = require("fs");
var os = require("os");

var filepath="testFile"
fs.closeSync( fs.openSync(filepath, 'w'));


var wstream = fs.createWriteStream(filepath);

wstream.write("testing ");
var msisdn=9000000000,number=144044100,inc=0;
var i=0;
var random =Math.random();
while(i< 1000){
inc=i>100?(i/100)%8:i%8;;
msisdn+= inc;
number+= inc+Math.ceil(Math.random()*100);
random=Math.ceil((inc)*random)
wstream.write("91"+msisdn+",4970,2,"+number+os.EOL);
if(number%2==0)
wstream.write("91"+msisdn+",7,36,1,"+number+os.EOL);
if(number%5==0){
wstream.write("91"+msisdn+",7,37,1,"+random+os.EOL);
}
var milliseconds = Math.ceil((new Date).getTime()+random*100);
if(number%3){
wstream.write("91"+msisdn+",682,1,1,1,4,"+milliseconds+","+random+os.EOL);
}
if(number%5)
wstream.write("91"+msisdn+",682,1,1,1,4,"+milliseconds+","+random+os.EOL);

i++;
}
 

wstream.end();
